package it.caleido.thip.vendite.generaleVE;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.persist.ErrorCodes;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Entity;
import com.thera.thermfw.security.Security;

import it.thera.thip.base.agentiProvv.Agente;
import it.thera.thip.base.articolo.ArticoloDatiIdent;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.cliente.Sconto;
import it.thera.thip.base.comuniVenAcq.StatoEvasione;
import it.thera.thip.base.comuniVenAcq.TipoRiga;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.cs.DatiComuniEstesi;
import it.thera.thip.vendite.offerteCliente.OffertaClienteRigaPrm;
import it.thera.thip.vendite.offerteCliente.OffertaClienteRigaSec;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaSec;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 03/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    03/02/2025</b>
 * <p></p>
 */

public class YModificaScontiProvvigioniRigheVendita extends BusinessObjectAdapter implements Authorizable {

	protected String iIdAzienda;

	protected BigDecimal iScontoArticolo1;
	protected BigDecimal iScontoArticolo2;
	protected BigDecimal iMaggiorazione;

	protected BigDecimal iProvvigione2Agente;

	protected char iFlagRiservatoUtente1 = '-';
	protected String iAlfanumRiservatoUtente1;
	protected Proxy iSubagente = new Proxy(Agente.class);

	protected BigDecimal iProvvigione2Subagente;

	protected Proxy iSconto = new Proxy(Sconto.class);
	protected Proxy iAgente = new Proxy(Agente.class);

	protected String iClassName;
	protected String iChiaviSelezionati;

	public YModificaScontiProvvigioniRigheVendita() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iSconto.setKey(KeyHelper.replaceTokenObjectKey(iSconto.getKey(), 1, idAzienda));
		iAgente.setKey(KeyHelper.replaceTokenObjectKey(iAgente.getKey(), 1, idAzienda));
		iSubagente.setKey(KeyHelper.replaceTokenObjectKey(iSubagente.getKey(), 1, idAzienda));
	}

	public String getClassName() {
		return iClassName;
	}

	public void setClassName(String iClassName) {
		this.iClassName = iClassName;
	}

	public String getChiaviSelezionati() {
		return iChiaviSelezionati;
	}

	public void setChiaviSelezionati(String iChiaviSelezionati) {
		this.iChiaviSelezionati = iChiaviSelezionati;
	}

	public void setScontoArticolo1(BigDecimal iScontoArticolo1) {
		this.iScontoArticolo1 = iScontoArticolo1;
	}

	public BigDecimal getScontoArticolo1() {
		return iScontoArticolo1;
	}

	public void setScontoArticolo2(BigDecimal iScontoArticolo2) {
		this.iScontoArticolo2 = iScontoArticolo2;
	}

	public BigDecimal getScontoArticolo2() {
		return iScontoArticolo2;
	}

	public void setMaggiorazione(BigDecimal iMaggiorazione) {
		this.iMaggiorazione = iMaggiorazione;
	}

	public BigDecimal getMaggiorazione() {
		return iMaggiorazione;
	}

	public Sconto getSconto() {
		return (Sconto)iSconto.getObject();
	}

	public void setSconto(Sconto iSconto) {
		this.iSconto.setObject(iSconto);
	}

	public String getScontoKey() {
		return iSconto.getKey();
	}

	public void setScontoKey(String key) {
		iSconto.setKey(key);
	}

	public String getIdSconto() {
		String key = iSconto.getKey();
		String rSconto = KeyHelper.getTokenObjectKey(key,2);
		return rSconto;
	}


	public void setIdSconto(String rSconto) {
		String key = iSconto.getKey();
		iSconto.setKey(
				KeyHelper.replaceTokenObjectKey(key , 2, rSconto)
				);
	}

	public Agente getAgente() {
		return (Agente)iAgente.getObject();
	}

	public void setAgente(Agente iAgente) {
		this.iAgente.setObject(iAgente);
	}

	public String getAgenteKey() {
		return iAgente.getKey();
	}

	public void setAgenteKey(String key) {
		iAgente.setKey(key);
	}

	public String getIdAgente() {
		String key = iAgente.getKey();
		String rAgente = KeyHelper.getTokenObjectKey(key,2);
		return rAgente;
	}

	public void setIdAgente(String rAgente) {
		String key = iAgente.getKey();
		iAgente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rAgente));
	}

	public void setProvvigione2Agente(BigDecimal iProvvigione2Agente) {
		this.iProvvigione2Agente = iProvvigione2Agente;
	}

	public BigDecimal getProvvigione2Agente() {
		return iProvvigione2Agente;
	}

	public char getFlagRiservatoUtente1() {
		return iFlagRiservatoUtente1;
	}

	public void setFlagRiservatoUtente1(char flagRiservatoUtente1) {
		this.iFlagRiservatoUtente1 = flagRiservatoUtente1;
	}

	public String getAlfanumRiservatoUtente1() {
		return iAlfanumRiservatoUtente1;
	}

	public void setAlfanumRiservatoUtente1(String alfanumRiservatoUtente1) {
		this.iAlfanumRiservatoUtente1 = alfanumRiservatoUtente1;
	}

	public Agente getSubagente() {
		return (Agente)iSubagente.getObject();
	}

	public void setSubagente(Agente iSubagente) {
		this.iSubagente.setObject(iSubagente);
	}

	public String getSubagenteKey() {
		return iSubagente.getKey();
	}


	public void setSubagenteKey(String key) {
		iSubagente.setKey(key);
	}

	public String getIdSubagente() {
		String key = iSubagente.getKey();
		String rSubagente = KeyHelper.getTokenObjectKey(key,2);
		return rSubagente;
	}

	public void setIdSubagente(String rSubagente) {
		String key = iSubagente.getKey();
		iSubagente.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rSubagente));
	}

	public BigDecimal getProvvigione2Subagente() {
		return iProvvigione2Subagente;
	}

	public void setProvvigione2Subagente(BigDecimal iProvvigione2Subagente) {
		this.iProvvigione2Subagente = iProvvigione2Subagente;
	}

	@Override
	public int save(boolean force) throws SQLException {
		int rc = 0;
		List<PersistentObject> righe = getRigheSelezionate();
		for(PersistentObject riga : righe) {
			if(riga instanceof OffertaClienteRigaPrm) {
				rc += aggiornaRigaOffertaCliente((OffertaClienteRigaPrm) riga);
			}else if(riga instanceof OrdineVenditaRigaPrm) {
				rc += aggiornaRigaOrdineVendita((OrdineVenditaRigaPrm) riga);
			}
		}
		return rc;
	}

	@SuppressWarnings("rawtypes")
	protected int aggiornaRigaOffertaCliente(OffertaClienteRigaPrm riga) throws SQLException {
		int rc = ErrorCodes.NO_ROWS_UPDATED;
		if(riga.getStato() == DatiComuniEstesi.VALIDO && riga.getStatoEvasione() == StatoEvasione.INEVASO && riga.getTipoRiga() == TipoRiga.MERCE) {
			if(riga.getArticolo().getTipoParte() == ArticoloDatiIdent.KIT_GEST || riga.getArticolo().getTipoParte() == ArticoloDatiIdent.KIT_NON_GEST) {
				Iterator iterRigheSec = riga.getRigheSecondarie().iterator();
				while(iterRigheSec.hasNext()) {
					OffertaClienteRigaSec rigaSec = (OffertaClienteRigaSec) iterRigheSec.next();
					if(rigaSec.getTipoRiga() == TipoRiga.MERCE) {
						rigaSec.setSconto(getSconto());
						rigaSec.setScontoArticolo1(getScontoArticolo1());
						rigaSec.setScontoArticolo2(getScontoArticolo2());
						rigaSec.setMaggiorazione(getMaggiorazione());
						rigaSec.setAgente(getAgente());
						rigaSec.setSubagente(getSubagente());
						rigaSec.setProvvigione2Agente(getProvvigione2Agente());
						rigaSec.setProvvigione2Subagente(getProvvigione2Subagente());
					}
				}
			}else {
				riga.setSconto(getSconto());
				riga.setScontoArticolo1(getScontoArticolo1());
				riga.setScontoArticolo2(getScontoArticolo2());
				riga.setMaggiorazione(getMaggiorazione());
				//riga.setAlfanumRiservatoUtente1(getAlfanumRiservatoUtente1());
				//riga.setFlagRiservatoUtente1(getFlagRiservatoUtente1());
			}
			riga.setAgente(getAgente());
			riga.setSubagente(getSubagente());
			riga.setProvvigione2Agente(getProvvigione2Agente());
			riga.setProvvigione2Subagente(getProvvigione2Subagente());
			
			rc = riga.save();

		}else {
			return rc;
		}
		return rc;
	}

	@SuppressWarnings("rawtypes")
	protected int aggiornaRigaOrdineVendita(OrdineVenditaRigaPrm riga) throws SQLException {
		int rc = ErrorCodes.NO_ROWS_UPDATED;
		if(riga.getStato() == DatiComuniEstesi.VALIDO && riga.getStatoEvasione() == StatoEvasione.INEVASO && riga.getTipoRiga() == TipoRiga.MERCE) {
			if(riga.getArticolo().getTipoParte() == ArticoloDatiIdent.KIT_GEST || riga.getArticolo().getTipoParte() == ArticoloDatiIdent.KIT_NON_GEST) {
				Iterator iterRigheSec = riga.getRigheSecondarie().iterator();
				while(iterRigheSec.hasNext()) {
					OrdineVenditaRigaSec rigaSec = (OrdineVenditaRigaSec) iterRigheSec.next();
					if(rigaSec.getTipoRiga() == TipoRiga.MERCE) {
						rigaSec.setSconto(getSconto());
						rigaSec.setScontoArticolo1(getScontoArticolo1());
						rigaSec.setScontoArticolo2(getScontoArticolo2());
						rigaSec.setMaggiorazione(getMaggiorazione());
						rigaSec.setAgente(getAgente());
						rigaSec.setSubagente(getSubagente());
						rigaSec.setProvvigione2Agente(getProvvigione2Agente());
						rigaSec.setProvvigione2Subagente(getProvvigione2Subagente());
					}
				}
			}else {
				riga.setSconto(getSconto());
				riga.setScontoArticolo1(getScontoArticolo1());
				riga.setScontoArticolo2(getScontoArticolo2());
				riga.setMaggiorazione(getMaggiorazione());
				riga.setAlfanumRiservatoUtente1(getAlfanumRiservatoUtente1());
				riga.setFlagRiservatoUtente1(getFlagRiservatoUtente1());
			}
			riga.setAgente(getAgente());
			riga.setSubagente(getSubagente());
			riga.setProvvigione2Agente(getProvvigione2Agente());
			riga.setProvvigione2Subagente(getProvvigione2Subagente());

			rc = riga.save();

		}else {
			return rc;
		}
		return rc;
	}

	public List<PersistentObject> getRigheSelezionate(){ 
		List<PersistentObject> rows = new ArrayList<PersistentObject>();
		Class<?> destination = null;
		if(getClassName().contains("OrdineVenditaRigaPrm")) {
			destination = OrdineVenditaRigaPrm.class;
		}else if(getClassName().contains("OffertaClienteRigaPrm")) {
			destination = OffertaClienteRigaPrm.class;
		}
		List<String> keys = Arrays.asList(getChiaviSelezionati().split(ColonneFiltri.LISTA_SEP));
		for(String key : keys) {
			try {
				rows.add(PersistentObject.elementWithKey(destination, key, PersistentObject.NO_LOCK));
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return rows;
	}

	public static boolean isAutorizzatoTask(String className, String taskId) {
		try{
			ClassADCollection cadc = ClassADCollectionManager.collectionWithName(className);
			Class<?> classObj = Class.forName(cadc.getBOClassName());
			if (classObj != null) {
				Entity entityObj = Entity.findEntity(classObj);
				if (entityObj != null)
					return Security.validate(entityObj.getId(),taskId);
			}
		}catch(Exception ex1){
			ex1.printStackTrace(Trace.excStream);
		}
		return false;
	}
}
