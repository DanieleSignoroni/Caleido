package it.caleido.thip.base.generale.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.gui.cnr.OpenType;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.rs.errors.ErrorUtils;
import com.thera.thermfw.rs.errors.PantheraApiException;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.datiTecnici.configuratore.SchemaCfg;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 28/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    28/08/2026  DSSOF3   Prima stesura
 */

public class ECommerceService {

	static ECommerceService service;

	public static ECommerceService getECommerceService() {
		if(service == null)
			service = new ECommerceService();
		return service;
	}

	@SuppressWarnings("unchecked")
	public JSONObject riceviConfigurazione(String body) {
		JSONObject response = new JSONObject();
		JSONObject result = new JSONObject();
		Status status = Status.OK;
		Collection<ErrorMessage> errors = new ArrayList<>();

		try {
			JSONObject bodyAsJSON = new JSONObject(body);

			BODataCollector boDCArt = createDataCollector("Articolo");

			if (!bodyAsJSON.has("IdArticolo")) {
				ErrorMessage err = new ErrorMessage("BAS0000000");
				aggiungiComponenteInErrore("IdArticolo", "Articolo", boDCArt, err);
				errors.add(err);

				return buildResponse(Status.BAD_REQUEST, errors);
			}

			if (!bodyAsJSON.has("confVariables")) {
				ErrorMessage err = new ErrorMessage("BAS0000078", "Indicare le variabili di configurazione");
				errors.add(err);

				return buildResponse(Status.BAD_REQUEST, errors);
			}

			int rc = boDCArt.initSecurityServices(OpenType.UPDATE, true, true, true);

			if (rc != BODataCollector.OK) {
				errors.addAll(boDCArt.getErrorList().getErrors());
				return buildResponse(Status.BAD_REQUEST, errors);
			}

			rc = boDCArt.retrieve(
					KeyHelper.buildObjectKey(new String[] {
							Azienda.getAziendaCorrente(),
							bodyAsJSON.getString("IdArticolo")
					}));

			if (rc != BODataCollector.OK) {
				errors.addAll(boDCArt.getErrorList().getErrors());
				return buildResponse(Status.BAD_REQUEST, errors);
			}

			Articolo articolo = (Articolo) boDCArt.getBo();

			if (articolo.getSchemaCfg() == null) {
				ErrorMessage err = new ErrorMessage("BAS0000000");
				aggiungiComponenteInErrore("IdSchemaCfg", "Articolo", boDCArt, err);
				errors.add(err);

				return buildResponse(Status.BAD_REQUEST, errors);
			}

			String sintesiConfigGUI = costruisciSintesiConfigurazioneGUI(bodyAsJSON, articolo.getSchemaCfg());


		}catch (Exception e) {
			if(e instanceof PantheraApiException) {
				PantheraApiException pae = (PantheraApiException) e;
				return buildResponse((Status) pae.getHTTPStatus(), pae.getErrors());
			}
			errors.add(new ErrorMessage("BAS0000078","Eccezione non gestita: "+e.getMessage()));
			e.printStackTrace(Trace.excStream);
			status = Status.INTERNAL_SERVER_ERROR;
		}

		response = buildResponse(status, errors);
		return response;
	}

	public String costruisciSintesiConfigurazioneGUI(JSONObject bodyAsJSON, SchemaCfg schemaCfg) throws PantheraApiException {
		StringBuilder sintesi = new StringBuilder();
		JSONArray variables = bodyAsJSON.getJSONArray("confVariables");
		for (int i = 0; i < variables.length(); i++) {
			JSONObject variabile = variables.getJSONObject(i);

			if (variabile.length() != 1) {
				throw new IllegalArgumentException(
						"La variabile di configurazione deve contenere una sola coppia chiave-valore"
						);
			}

			String idVariabile = variabile.keys().next();
			String valore = variabile.getString(idVariabile);

			VariabileSchemaCfg variabileCfg = variabileSchemaConfigurazione(schemaCfg, idVariabile);
			if(variabileCfg == null) {
				String c = KeyHelper.buildObjectKey(new String[] {schemaCfg.getKey(), idVariabile});
				throw new PantheraApiException(Status.BAD_REQUEST, new ErrorMessage("BAS0000004", new String[] {c}));
			}
			ValoreVariabileCfg valoreVarCfg = valoreVariabileSchemaConfigurazione(variabileCfg, valore);
			if(valoreVarCfg == null) {
				String c = KeyHelper.buildObjectKey(new String[] {variabileCfg.getKey(), valore});
				throw new PantheraApiException(Status.BAD_REQUEST, new ErrorMessage("BAS0000004", new String[] {c}));
			}

			sintesi.append(idVariabile).append(PersistentObject.KEY_SEPARATOR).append(valore);

			// Aggiungo il separatore solo se non sono sull'ultimo elemento
			if (i < variables.length() - 1) {
				sintesi.append(ColonneFiltri.SEP);
			}
		}
		
		//..Ora le parti parametrizzate (fisse)

		return sintesi.toString();
	}

	public VariabileSchemaCfg variabileSchemaConfigurazione(SchemaCfg schemaCfg, String idVariabile) {
		try {
			return (VariabileSchemaCfg) VariabileSchemaCfg.elementWithKey(VariabileSchemaCfg.class, KeyHelper.buildObjectKey(new String[] {
					schemaCfg.getKey(), idVariabile
			}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public ValoreVariabileCfg valoreVariabileSchemaConfigurazione(VariabileSchemaCfg variabileCfg, String idValore) {
		try {
			return (ValoreVariabileCfg) ValoreVariabileCfg.elementWithKey(ValoreVariabileCfg.class, KeyHelper.buildObjectKey(new String[] {
					variabileCfg.getKey(), idValore
			}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public JSONObject buildResponse(
			Status status,
			Collection<ErrorMessage> errors) {

		JSONObject result = new JSONObject();
		result.put("errors", ErrorUtils.getInstance().toJSON(errors));

		JSONObject response = new JSONObject();
		response.put("response", result);
		response.put("status", status);

		return response;
	}

	/**
	 * Si occupa di aggiungere il BaseComponent all'errore così da costruire il testo in maniera completa.
	 * @param attributeName
	 * @param className
	 * @param bodc
	 * @param errorMessage
	 * @throws NoSuchElementException
	 * @throws NoSuchFieldException
	 */
	public void aggiungiComponenteInErrore(String attributeName, String className, BODataCollector bodc, ErrorMessage errorMessage) throws NoSuchElementException, NoSuchFieldException {
		ClassADCollection cad = ClassADCollectionManager.collectionWithName(className);
		String label = cad.getAttribute(attributeName).getAttributeNameNLS();
		errorMessage.addComponent(attributeName, label, bodc.getComponent(attributeName));
	}

	protected BODataCollector createDataCollector(String classname) {
		try {
			ClassADCollection hdr = ClassADCollectionManager.collectionWithName(classname);
			return createDataCollector(hdr);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BODataCollector createDataCollector(ClassADCollection classDescriptor) {
		BODataCollector dataCollector = null;
		String collectorName = classDescriptor.getBODataCollector();
		if (collectorName != null) {
			dataCollector = (BODataCollector) Factory.createObject(collectorName);
		} else {
			dataCollector = new BODataCollector();
		}
		dataCollector.initialize(classDescriptor.getClassName(), true);
		return dataCollector;
	}
}
