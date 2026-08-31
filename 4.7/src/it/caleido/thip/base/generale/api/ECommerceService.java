package it.caleido.thip.base.generale.api;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.gui.cnr.OpenType;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.rs.errors.ErrorUtils;
import com.thera.thermfw.rs.errors.PantheraApiException;
import com.thera.thermfw.type.EnumType;

import it.caleido.thip.base.articolo.YArticolo;
import it.caleido.thip.base.articolo.YArticoloDatiTecnici;
import it.caleido.thip.datiTecnici.configuratore.YModelloTermostato;
import it.caleido.thip.datiTecnici.configuratore.YModelloTermostatoTM;
import it.caleido.thip.vendite.generaleVE.YModificaConfigurazioneRigaVendita;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.Cliente;
import it.thera.thip.base.generale.NumeratoreHandler;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.GestoreMacroConfigurazione;
import it.thera.thip.datiTecnici.configuratore.MacroConfigurazione;
import it.thera.thip.datiTecnici.configuratore.SchemaCfg;
import it.thera.thip.datiTecnici.configuratore.SezioneConfigurazione;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfgTM;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;
import it.thera.thip.vendite.offerteCliente.OffertaCliente;
import it.thera.thip.vendite.offerteCliente.OffertaClienteRigaPrm;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject riceviConfigurazione(String body) {
		JSONObject response = new JSONObject();
		Status status = Status.OK;
		Collection<ErrorMessage> errors = new ArrayList<>();

		boolean confCreated = false;
		String keyConfCreated = null;
		BigDecimal prezzo = null;

		try {
			JSONObject bodyAsJSON = new JSONObject(body);

			errors = validaDatiRicezioneConfigurazione(bodyAsJSON);
			if(!errors.isEmpty()) {
				return buildResponse(Status.BAD_REQUEST, errors);
			}

			Articolo articolo = (Articolo) Articolo.elementWithKey(Articolo.class, KeyHelper.buildObjectKey(new String[] {
					Azienda.getAziendaCorrente(),
					bodyAsJSON.getString("IdArticolo")
			}), PersistentObject.NO_LOCK);

			String sintesiConfigGUI = costruisciSintesiConfigurazioneGUI(bodyAsJSON, articolo.getSchemaCfg(), articolo);
			if(sintesiConfigGUI != null) {
				BODataCollector boDCC = createDataCollector("Configurazione");

				int rc = boDCC.initSecurityServices(OpenType.NEW, true, true, true);

				if (rc != BODataCollector.OK) {
					errors.addAll(boDCC.getErrorList().getErrors());
					return buildResponse(Status.BAD_REQUEST, errors);
				}

				Configurazione conf = YModificaConfigurazioneRigaVendita.leggiConfigurazione(articolo.getIdAzienda(), articolo.getIdArticolo(), sintesiConfigGUI);
				if(conf == null) {
					conf = (Configurazione) boDCC.getBo();
					conf.setIdAzienda(Azienda.getAziendaCorrente());
					conf.setIdArticolo(articolo.getIdArticolo());
					conf.setSchemaCfg(articolo.getSchemaCfg());

					Hashtable newVvv = conf.getVariabiliValoriValue(sintesiConfigGUI);
					conf.setSintesiConfig(conf.getFormattedSintesiConfigFinal(newVvv));
					conf.setStatoSezioneCfg(DatiComuniEstesi.VALIDO);
					conf.getDescrizione().setDescrizione(".");
					conf.getDescrizione().setDescrizioneRidotta(".");
					conf.setIdConfigurazione(new Integer(0));
					conf.setOnDB(false);

					SezioneConfigurazione sezConferma = conf.getSezioneCfg(Configurazione.ID_SEZ_CONFERMA);
					if(sezConferma != null)
						conf.setIdSezioneCfg(sezConferma.getIdSezioneCfg());

					boDCC.setForceableErrorForced(true);
					boDCC.setBo(conf);

					rc = boDCC.save();

					if(rc == BODataCollector.ERROR
							&& boDCC.getErrorList().getErrors().size() == 1
							&& ((ErrorMessage)boDCC.getErrorList().getErrors().get(0)).getId().equals("THIP_BS051")) { //..Esiste gia'
						conf = conf.getConfigurazioneEquivalente();
						rc = BODataCollector.OK;
					}else {

						if (rc != BODataCollector.OK) {
							errors.addAll(boDCC.getErrorList().getErrors());
							return buildResponse(Status.BAD_REQUEST, errors);
						}else {
							confCreated = true;
							keyConfCreated = KeyHelper.formatKeyString(boDCC.getBo().getKey());
						}
					}
				}

				//Passo alla creazione della riga off per avere i prezzi
				if(conf != null) {
					ConnectionManager.pushConnection();
					OffertaCliente off = null;
					try {
						off = creaOffertaCliente("OFFERTE_CLI", "IT", "O01", bodyAsJSON.getString("IdCliente"));
						if(off != null) {
							rc = off.save();
							if(rc > 0) {
								OffertaClienteRigaPrm riga = creaOffertaClienteRigaPrm(off, articolo, conf);
								if(riga != null) {
									rc = riga.save();
									if(rc > 0) {
										prezzo = riga.getPrezzo();
									}
								}
							}
						}
						off.retrieve();
						rc = off.delete();
						if(rc > 0) {
							NumeratoreHandler numeratore = off.getNumeratoreHandler();
							NumeratoreHandler.ripristinaProgressivo(numeratore.getIdAzienda(), numeratore.getIdNumeratore(),
									numeratore.getIdSerie(), numeratore.getAnno(), numeratore.getNumero().intValue());
							ConnectionManager.commit();
						}
					}catch (Exception e) {
						if(off != null) {
							NumeratoreHandler numeratore = off.getNumeratoreHandler();
							NumeratoreHandler.ripristinaProgressivo(numeratore.getIdAzienda(), numeratore.getIdNumeratore(),
									numeratore.getIdSerie(), numeratore.getAnno(), numeratore.getNumero().intValue());
						}
					}finally {
						ConnectionManager.popConnection();
					}
				}
			}

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
		if(confCreated) {
			response.getJSONObject("response").put("confCreated", confCreated);
			response.getJSONObject("response").put("keyConfCreated", keyConfCreated);
		}
		if(prezzo != null) {
			response.getJSONObject("response").put("prezzo", prezzo);
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector validaDatiRicezioneConfigurazione(JSONObject bodyAsJSON) throws NoSuchElementException, NoSuchFieldException {
		Vector errors = new Vector();
		BODataCollector boDCArt = createDataCollector("Articolo");
		BODataCollector boDCCLI = createDataCollector("ClienteVendita");

		if (!bodyAsJSON.has("IdArticolo")) {
			ErrorMessage err = new ErrorMessage("BAS0000000");
			aggiungiComponenteInErrore("IdArticolo", "Articolo", boDCArt, err);
			errors.add(err);
		}

		if (!bodyAsJSON.has("Variabili")) {
			ErrorMessage err = new ErrorMessage("BAS0000078", "Indicare le variabili di configurazione");
			errors.add(err);
		}

		if (!bodyAsJSON.has("IdCliente")) {
			ErrorMessage err = new ErrorMessage("BAS0000000");
			aggiungiComponenteInErrore("IdCliente", "ClienteVendita", boDCCLI, err);
			errors.add(err);
		}

		int rc = boDCArt.initSecurityServices(OpenType.UPDATE, true, true, true);

		if (rc != BODataCollector.OK) {
			errors.addAll(boDCArt.getErrorList().getErrors());
		}

		rc = boDCArt.retrieve(
				KeyHelper.buildObjectKey(new String[] {
						Azienda.getAziendaCorrente(),
						bodyAsJSON.getString("IdArticolo")
				}));

		if (rc != BODataCollector.OK) {
			errors.addAll(boDCArt.getErrorList().getErrors());
		}

		rc = boDCCLI.retrieve(
				KeyHelper.buildObjectKey(new String[] {
						Azienda.getAziendaCorrente(),
						bodyAsJSON.getString("IdCliente")
				}));

		if (rc != BODataCollector.OK) {
			errors.addAll(boDCCLI.getErrorList().getErrors());
		}

		Articolo articolo = (Articolo) boDCArt.getBo();

		if (articolo.getSchemaCfg() == null) {
			ErrorMessage err = new ErrorMessage("BAS0000000");
			aggiungiComponenteInErrore("IdSchemaCfg", "Articolo", boDCArt, err);
			errors.add(err);
		}
		return errors;
	}

	public OffertaCliente creaOffertaCliente(String idNumeratore, String idSerie, String idCau, String idCliente) {
		OffertaCliente offerta = (OffertaCliente) Factory.createObject(OffertaCliente.class);
		offerta.setIdAzienda(Azienda.getAziendaCorrente());
		offerta.getNumeratoreHandler().setDataDocumento(TimeUtils.getCurrentDate());
		offerta.getNumeratoreHandler().setIdNumeratore(idNumeratore);
		offerta.getNumeratoreHandler().setIdSerie(idSerie);
		offerta.setIdCau(idCau);
		offerta.setIdCliente(idCliente);
		offerta.setTipoIntestatarioOfferta(OffertaCliente.TIPO_INTESTATARIO_CLIENTE);
		offerta.completaBO();
		return offerta;
	}

	public OffertaClienteRigaPrm creaOffertaClienteRigaPrm(OffertaCliente offerta, Articolo articolo, Configurazione conf) {
		OffertaClienteRigaPrm riga = (OffertaClienteRigaPrm) Factory.createObject(OffertaClienteRigaPrm.class);
		riga.setTestata(offerta);
		riga.setIdAzienda(Azienda.getAziendaCorrente());
		riga.setIdCauRig(offerta.getCausale().getIdCausaleRigaOffertaVen());
		riga.setArticolo(articolo);
		riga.setConfigurazione(conf);
		riga.setIdUMRif(articolo.getIdUMRiferimento());
		riga.cambiaArticolo(articolo, conf, true);
		riga.getQuantitaOffertaVen().setQuantitaInUMPrm(BigDecimal.ONE);
		riga.getQuantitaOffertaVen().setQuantitaInUMRif(BigDecimal.ONE);
		return riga;
	}

	public String costruisciSintesiConfigurazioneGUI(JSONObject bodyAsJSON, SchemaCfg schemaCfg, Articolo articolo) throws PantheraApiException {
		StringBuilder sintesi = new StringBuilder();
		JSONArray variables = bodyAsJSON.getJSONArray("Variabili");
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
			ValoreVariabileCfg valoreVarCfg = null;
			if(idVariabile.equals("TERMOSTATO")) {
				//..Ricerca su tabella THIPPERS.YMODELLO_TERMOSTATO con valore passato dall'e-commerce
				valoreVarCfg = ricercaModelloTermostato(bodyAsJSON, schemaCfg, valore);
			}else {
				int dimcarcodcfg = variabileCfg.getDimCarCodCfg();
				if (dimcarcodcfg >= 0 & dimcarcodcfg > VariabileSchemaCfg.MIN_DIMCARCODCFG) {
					valoreVarCfg = valoreVariabileSchemaConfigurazione(variabileCfg, idVariabile, valore);
				}else {
					valoreVarCfg = valoreVariabileSchemaConfigurazionePVAL(variabileCfg, idVariabile, valore);
				}
			}
			if(valoreVarCfg == null) {
				String c = KeyHelper.buildObjectKey(new String[] {variabileCfg.getKey(), valore});
				throw new PantheraApiException(Status.BAD_REQUEST, new ErrorMessage("BAS0000004", new String[] {c}));
			}

			sintesi.append(idVariabile).append(PersistentObject.KEY_SEPARATOR).append(valoreVarCfg.getPrimoValore()).append(PersistentObject.KEY_SEPARATOR).append(valoreVarCfg.getSequenzaValore());

			// Aggiungo il separatore solo se non sono sull'ultimo elemento
			if (i < variables.length() - 1) {
				sintesi.append(PersistentObject.KEY_SEPARATOR);
			}
		}

		boolean sepAdded = false;

		//..Ora le parti parametrizzate (fisse)

		Configurazione confTempo = (Configurazione) Factory.createObject(Configurazione.class);
		confTempo.setSintesiConfig(sintesi.toString());

		MacroConfigurazione ABILITAZ_COLORE = macroSchemaConfigurazione(schemaCfg, "ABILITAZ_COLORE");
		if(ABILITAZ_COLORE != null) {
			GestoreMacroConfigurazione gestore = (GestoreMacroConfigurazione) Factory.createObject(GestoreMacroConfigurazione.class);
			gestore.esegue(ABILITAZ_COLORE, confTempo);

			sintesi.setLength(0);
			sintesi.append(confTempo.getSintesiConfig());
		}

		VariabileSchemaCfg OPERAZ_ELETTRIF = variabileSchemaConfigurazione(schemaCfg, "OPERAZ.ELETTRIF");
		if(OPERAZ_ELETTRIF != null) {
			if(!sepAdded) {
				sintesi.append(PersistentObject.KEY_SEPARATOR);
				sepAdded = true;
			}
			ValoreVariabileCfg valoreVarCfg = valoreVariabileSchemaConfigurazione(OPERAZ_ELETTRIF, OPERAZ_ELETTRIF.getIdVariabileConfig(), "S");
			if(valoreVarCfg != null) {
				if(!sintesi.toString().contains(OPERAZ_ELETTRIF.getIdVariabileConfig())) {
					sepAdded = false;
				}
				aggiungiOSostituisciVariabile(sintesi, OPERAZ_ELETTRIF.getIdVariabileConfig(), valoreVarCfg.getPrimoValore(), String.valueOf(valoreVarCfg.getSequenzaValore()));
				if(!sepAdded) {
					sintesi.append(PersistentObject.KEY_SEPARATOR);
					sepAdded = true;
				}
			}
		}

		VariabileSchemaCfg POSIZ_TERMOST = variabileSchemaConfigurazione(schemaCfg, "POSIZ_TERMOST");
		if(POSIZ_TERMOST != null) {
			if(articolo instanceof YArticolo
					&& articolo.getArticoloDatiTecnici() instanceof YArticoloDatiTecnici
					&& ((YArticoloDatiTecnici)articolo.getArticoloDatiTecnici()).getDestroOSinistro() != '-') {
				char destroOSinistro =((YArticoloDatiTecnici) articolo.getArticoloDatiTecnici()).getDestroOSinistro();

				EnumType etDxOrSx = EnumType.getEnumTypeInstance("PosizTermostato", EnumType.class);

				if (destroOSinistro != '-') {
					if(!sepAdded) {
						sintesi.append(PersistentObject.KEY_SEPARATOR);
						sepAdded = true;
					}
					//Così e non (etDxOrSx.descriptionFromValue) perche' l'enumerato e' stato codificato male
					String posizione = (String) etDxOrSx.getAttValueDescriptions().get(Integer.valueOf(String.valueOf(destroOSinistro)));
					ValoreVariabileCfg valoreVarCfg = valoreVariabileSchemaConfigurazionePVAL(POSIZ_TERMOST, POSIZ_TERMOST.getIdVariabileConfig(), posizione);
					if(valoreVarCfg != null) {
						if(!sintesi.toString().contains(POSIZ_TERMOST.getIdVariabileConfig())) {
							sepAdded = false;
						}
						aggiungiOSostituisciVariabile(sintesi, POSIZ_TERMOST.getIdVariabileConfig(), valoreVarCfg.getPrimoValore(), String.valueOf(valoreVarCfg.getSequenzaValore()));
						if(!sepAdded) {
							sintesi.append(PersistentObject.KEY_SEPARATOR);
							sepAdded = true;
						}
					}
				}
			}
		}

		//Potenza Watt <
		if(articolo instanceof YArticolo
				&& articolo.getArticoloDatiTecnici() instanceof YArticoloDatiTecnici
				&& ((YArticoloDatiTecnici)articolo.getArticoloDatiTecnici()).getVariabilePotenza() != null) {
			ValoreVariabileCfg POTENZA_WATT = ((YArticoloDatiTecnici)articolo.getArticoloDatiTecnici()).getPotenzaWatt();
			if(POTENZA_WATT != null) {
				if(!sepAdded) {
					sintesi.append(PersistentObject.KEY_SEPARATOR);
					sepAdded = true;
				}
				sintesi.append(POTENZA_WATT.getIdVariabileConfig()).append(PersistentObject.KEY_SEPARATOR).append(POTENZA_WATT.getPrimoValore()).append(PersistentObject.KEY_SEPARATOR).append(POTENZA_WATT.getSequenzaValore());
			}
		}
		//Potenza Watt >


		return sintesi.toString();
	}

	@SuppressWarnings("rawtypes")
	public ValoreVariabileCfg ricercaModelloTermostato(JSONObject bodyAsJSON, SchemaCfg schemaCfg, String valore) {
		try {
			Cliente cliente = (Cliente) Cliente.elementWithKey(Cliente.class, KeyHelper.buildObjectKey(new String[] {
					Azienda.getAziendaCorrente(), (String) bodyAsJSON.get("IdCliente")
			}), PersistentObject.NO_LOCK);
			if(cliente != null) {
				String idNazione = cliente.getIdNazione();

				String where = " "+YModelloTermostatoTM.ID_AZIENDA+" = '"+Azienda.getAziendaCorrente()+"' ";
				where += " AND "+YModelloTermostatoTM.DESCRIZIONE+" = '"+valore+"' ";
				where += " AND "+YModelloTermostatoTM.ID_SCHEMA_CFG+" = '"+schemaCfg.getIdSchemaCfg()+"' ";

				String whereNazi = " AND "+YModelloTermostatoTM.IDNAZIONE+" = '"+idNazione+"' ";

				Vector v = YModelloTermostato.retrieveList(YModelloTermostato.class, where + whereNazi, "", false);
				if(v.isEmpty()) {
					v = YModelloTermostato.retrieveList(YModelloTermostato.class, where, "", false);
				}
				if(!v.isEmpty()) {
					YModelloTermostato modello = (YModelloTermostato) v.get(0);
					return (ValoreVariabileCfg) ValoreVariabileCfg.elementWithKey(ValoreVariabileCfg.class, KeyHelper.buildObjectKey(new String[] {
							modello.getSchemaconfigurazioneKey(), modello.getIdVariabileConfig(), String.valueOf(modello.getSequenzaValore())
					}),PersistentObject.NO_LOCK);
				}

			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	public void aggiungiOSostituisciVariabile(
			StringBuilder sintesi,
			String idVariabile,
			String valore,
			String sequenza) {

		String separator = PersistentObject.KEY_SEPARATOR;

		String[] elementi = sintesi.toString().split(
				java.util.regex.Pattern.quote(separator),
				-1
				);

		StringBuilder nuovaSintesi = new StringBuilder();
		boolean sostituita = false;

		/*
		 * La sintesi è composta da terne:
		 *
		 * VARIABILE + sep + VALORE + sep + SEQUENZA
		 *
		 * Esempio:
		 * FASCIA + sep + Fascia C + sep + 4 +
		 * sep +
		 * COLORE + sep + Grigio 9022 Pearl Gr + sep + 32
		 */
		for (int i = 0; i + 2 < elementi.length; i += 3) {

			String variabileCorrente = elementi[i];
			String valoreCorrente = elementi[i + 1];
			String sequenzaCorrente = elementi[i + 2];

			if (variabileCorrente.equals(idVariabile)) {
				valoreCorrente = valore;
				sequenzaCorrente = sequenza;
				sostituita = true;
			}

			if (nuovaSintesi.length() > 0) {
				nuovaSintesi.append(separator);
			}

			nuovaSintesi
			.append(variabileCorrente)
			.append(separator)
			.append(valoreCorrente)
			.append(separator)
			.append(sequenzaCorrente);
		}

		// Se la variabile non era presente, aggiungo l'intera terna
		if (!sostituita) {

			if (nuovaSintesi.length() > 0) {
				nuovaSintesi.append(separator);
			}

			nuovaSintesi
			.append(idVariabile)
			.append(separator)
			.append(valore)
			.append(separator)
			.append(sequenza);
		}

		sintesi.setLength(0);
		sintesi.append(nuovaSintesi);
	}

	public MacroConfigurazione macroSchemaConfigurazione(SchemaCfg schemaCfg, String idMacro) {
		try {
			return (MacroConfigurazione) MacroConfigurazione.elementWithKey(MacroConfigurazione.class, KeyHelper.buildObjectKey(new String[] {
					schemaCfg.getKey(), idMacro
			}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
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

	@SuppressWarnings("rawtypes")
	public ValoreVariabileCfg valoreVariabileSchemaConfigurazione(VariabileSchemaCfg variabileCfg, String idVariabile, String carCodCfg) {
		try {
			String where = ValoreVariabileCfgTM.ID_AZIENDA+"='"+variabileCfg.getIdAzienda()+"'";
			where += "AND " + ValoreVariabileCfgTM.ID_SCHEMA_CFG+"='"+variabileCfg.getIdSchemaCfg()+"'";
			where += "AND " + ValoreVariabileCfgTM.ID_VARIABILE_CFG+"='"+idVariabile+"'";
			where += "AND " + ValoreVariabileCfgTM.CAR_COD_CFG+"='"+carCodCfg+"'";
			Vector v = ValoreVariabileCfg.retrieveList(ValoreVariabileCfg.class, where, "", false);
			if(!v.isEmpty()) {
				return (ValoreVariabileCfg) v.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public ValoreVariabileCfg valoreVariabileSchemaConfigurazionePVAL(VariabileSchemaCfg variabileCfg, String idVariabile, String primoValore) {
		try {
			String where = ValoreVariabileCfgTM.ID_AZIENDA+"='"+variabileCfg.getIdAzienda()+"'";
			where += "AND " + ValoreVariabileCfgTM.ID_SCHEMA_CFG+"='"+variabileCfg.getIdSchemaCfg()+"'";
			where += "AND " + ValoreVariabileCfgTM.ID_VARIABILE_CFG+"='"+idVariabile+"'";
			where += "AND " + ValoreVariabileCfgTM.PRIMO_VALORE+"='"+primoValore+"'";
			Vector v = ValoreVariabileCfg.retrieveList(ValoreVariabileCfg.class, where, "", false);
			if(!v.isEmpty()) {
				return (ValoreVariabileCfg) v.get(0);
			}
		} catch (Exception e) {
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
