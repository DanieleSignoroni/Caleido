package it.softre.thip.acquisti.uds;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.batch.BatchOptions;
import com.thera.thermfw.batch.BatchService;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.ErrorList;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;

import it.softre.thip.acquisti.uds.web.YUdsAcqRigDataCollector;
import it.softre.thip.base.uds.YTipoUds;
import it.softre.thip.uds.YRptUdsAcq;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.generale.ParametroPsn;
import it.thera.thip.base.profilo.UtenteAzienda;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Classe per i parametri della form YUdsAcquistoSemplificata.
 */

public class YUdsAcquistoSemplificata implements BusinessObject{

	protected String iIdAzienda;

	protected String iIdArticolo;

	protected String iIdAnnoOrd;

	protected String iIdNumeroOrd;

	protected String iNumeroRitorno;

	protected String iQuantita;

	protected String iIdUds;
	
	protected String chiaveUdsCreata;

	protected Proxy iRelArticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

	protected Proxy iRelUdsAcquisto = new Proxy(it.softre.thip.acquisti.uds.YUdsAcquisto.class);

	protected Proxy iRelOrdineEsecutivo = new Proxy(it.thera.thip.produzione.ordese.OrdineEsecutivo.class);
	
	public String getChiaveUdsCreata() {
		return chiaveUdsCreata;
	}

	public void setChiaveUdsCreata(String chiaveUdsCreata) {
		this.chiaveUdsCreata = chiaveUdsCreata;
	}

	public YUdsAcquistoSemplificata() {
		this.setIdAzienda(Azienda.getAziendaCorrente());
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
	}

	public String getIdArticolo() {
		return iIdArticolo;
	}

	public void setIdArticolo(String iIdArticolo) {
		this.iIdArticolo = iIdArticolo;
	}

	public String getNumeroRitorno() {
		return iNumeroRitorno;
	}

	public void setNumeroRitorno(String iNumeroRitorno) {
		this.iNumeroRitorno = iNumeroRitorno;
	}

	public String getQuantita() {
		return iQuantita;
	}

	public void setQuantita(String iQuantita) {
		this.iQuantita = iQuantita;
	}

	public String getIdAnnoOrd() {
		return iIdAnnoOrd;
	}

	public void setIdAnnoOrd(String iIdAnnoOrd) {
		this.iIdAnnoOrd = iIdAnnoOrd;
	}

	public String getIdNumeroOrd() {
		return iIdNumeroOrd;
	}

	public void setIdNumeroOrd(String iIdNumeroOrd) {
		this.iIdNumeroOrd = iIdNumeroOrd;
	}


	public String getIdUds() {
		return iIdUds;
	}

	public void setIdUds(String iIdUds) {
		this.iIdUds = iIdUds;
	}

	public void setRelUdsAcquisto(YUdsAcquisto parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 1);
		}
		setIdAzienda(idAzienda);
		this.iRelUdsAcquisto.setObject(parent);
		setOnDB(false);
	}

	public YUdsAcquisto setRelUdsAcquisto() {
		return (YUdsAcquisto)iRelUdsAcquisto.getObject();
	}

	public void setRelUdsAcquistoKey(String key) {
		iRelUdsAcquisto.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAzienda(idAzienda);
		setOnDB(false);
	}

	public String setRelUdsAcquistoKey() {
		return iRelUdsAcquisto.getKey();
	}

	@SuppressWarnings("unused")
	public void setRelArticolo(Articolo relarticolo) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relarticolo != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 1);
		}
		String rArticolo = getIdArticolo();
		if (relarticolo != null) {
			rArticolo = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 2);
		}
		this.iRelArticolo.setObject(relarticolo);
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Articolo getRelArticolo() {
		return (Articolo)iRelArticolo.getObject();
	}

	public void setRelArticoloKey(String key) {
		String oldObjectKey = getKey();
		iRelArticolo.setKey(key);
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelArticoloKey() {
		return iRelArticolo.getKey();
	}

	public OrdineEsecutivo getOrdineEsecutivo() {
		return (OrdineEsecutivo)iRelOrdineEsecutivo.getObject();
	}

	public void setOrdineEsecutivoKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineEsecutivo.setKey(key);
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public void setOrdineEsecutivo(OrdineEsecutivo iRelOrdineEsecutivo) {
		String oldObjectKey = getKey();
		if (iRelOrdineEsecutivo != null) {
		}
		this.iRelOrdineEsecutivo.setObject(iRelOrdineEsecutivo);
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineEsecutivoKey() {
		return iRelOrdineEsecutivo.getKey();
	}

	public OrdineEsecutivo checkOrdineEsecutivo(){
		OrdineEsecutivo ord = null;
		if(this.getNumeroRitorno() != null) {
			try {
				String idAnnoOrd = "";
				String idNumeroOrd = "";
				String key = "";
				if(this.getNumeroRitorno().length() == 9) {
					ResultSet rs = null;
					String stmt = " SELECT ID_ANNO_ORD,ID_NUMERO_ORD FROM THIP.ORD_ESEC_ATV "
							+ "WHERE NUM_RITORNO = '" + this.getNumeroRitorno() +"'";
					CachedStatement cs = new CachedStatement(stmt);
					rs = cs.executeQuery();
					if(rs.next()) {
						idAnnoOrd = rs.getString("ID_ANNO_ORD") != null ? rs.getString("ID_ANNO_ORD") : "";
						idNumeroOrd = rs.getString("ID_NUMERO_ORD") != null ? rs.getString("ID_NUMERO_ORD") : "";
						key = Azienda.getAziendaCorrente() + KeyHelper.KEY_SEPARATOR + idAnnoOrd + KeyHelper.KEY_SEPARATOR + idNumeroOrd;
						ord = (OrdineEsecutivo)
								OrdineEsecutivo.elementWithKey(OrdineEsecutivo.class, key, PersistentObject.NO_LOCK);
					}
				}else if(this.getNumeroRitorno().length() == 15) {
					idAnnoOrd = this.getNumeroRitorno().substring(0, 4) + "  ";
					idNumeroOrd = this.getNumeroRitorno().substring(5, 7) + " " + this.getNumeroRitorno().substring(8, 15);
					key = Azienda.getAziendaCorrente() + KeyHelper.KEY_SEPARATOR + idAnnoOrd + KeyHelper.KEY_SEPARATOR + idNumeroOrd;
					ord = (OrdineEsecutivo)
							OrdineEsecutivo.elementWithKey(OrdineEsecutivo.class, key, PersistentObject.NO_LOCK);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ord;
	}

	@Override
	public void setEqual(Copyable obj) throws CopyException {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		OrdineEsecutivo ordEsec = checkOrdineEsecutivo();
		if(ordEsec == null) {
			ErrorMessage em = new ErrorMessage("YSOFTRE001","Il numero inserito non equivale a nessun ordine esecutivo");
			if(em != null) {
				errors.add(em);
			}
		}else {
			this.setOrdineEsecutivo(ordEsec);
		}
		return errors;
	}

	@Override
	public int save() throws SQLException {
		return 0;
	}

	@Override
	public int save(boolean force) throws SQLException {
		return 0;
	}

	@Override
	public boolean retrieve(int lockType) throws SQLException {
		return false;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public void setKey(String key) {

	}

	@Override
	public void setOnDB(boolean onDB) {

	}

	@Override
	public void unlock() throws SQLException {

	}

	@Override
	public void setObjQueryTimeout(int seconds) {

	}

	public ErrorList nuovaUds() {
		ErrorList errori = new ErrorList();
		try {
			String idTipoUds = ParametroPsn.getValoreParametroPsn("YUdsAcquistoSemplificata", "TipoUds");
			if(idTipoUds != null) {
				YTipoUds tipoUds = (YTipoUds)
						YTipoUds.elementWithKey(YTipoUds.class,
								KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(),idTipoUds.trim()}),PersistentObject.NO_LOCK);
				if(tipoUds != null) {
					if(this.getQuantita() != null && this.getIdArticolo() != null) {
						YUdsAcquisto uds = (YUdsAcquisto) Factory.createObject(YUdsAcquisto.class);
						uds.setIdTipoContenitore(idTipoUds);
						uds.setIdAzienda(Azienda.getAziendaCorrente());
						uds.setPesoContenitore(tipoUds.getPeso() != null ? tipoUds.getPeso() : null);
						uds.setAltezza(tipoUds.getAltezza() != null ? tipoUds.getAltezza() : null);
						uds.setVolume(tipoUds.getVolume() != null ? tipoUds.getVolume() : null);
						uds.setLarghezza(tipoUds.getLarghezza() != null ? tipoUds.getLarghezza() : null);
						uds.setLunghezza(tipoUds.getLunghezza() != null ? tipoUds.getLunghezza() : null);
						if(uds.save() >= 0) {
							this.setIdUds(uds.getIdUds());
							errori = confermaRiga();
							this.setChiaveUdsCreata(uds.getKey());
						}
					}
				}
			}else {
				errori.addError(new ErrorMessage("YSOFTRE001","Il parametro YUdsAcquistoSemplificata e' vuoto, riempirlo per poter utilizzare la funzione"));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return errori;
	}

	@SuppressWarnings("unchecked")
	public ErrorList confermaRiga() {
		ErrorList errori = new ErrorList();
		try {
			if(this.getIdUds() != null && this.getQuantita() != null && this.getIdArticolo() != null) {
				YUdsAcqRigDataCollector boDC = (YUdsAcqRigDataCollector) Factory.createObject(YUdsAcqRigDataCollector.class);
				boDC.setAutoCommit(true);
				boDC.initialize(Factory.getName("YUdsAcqRig", Factory.CLASS_HDR), true, PersistentObject.OPTIMISTIC_LOCK);
				YUdsAcqRig riga = getUdsAcquistoRigaDaOrdEsec();
				boDC.setBo(riga);
				boDC.setNuovoDocumento(true);
				boDC.loadAttValue();
				if(boDC.save() != YUdsAcqRigDataCollector.OK)
					errori.getErrors().addAll(boDC.getErrorList().getErrors());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return errori;
	}

	public YUdsAcqRig getUdsAcquistoRigaDaOrdEsec() {
		YUdsAcqRig riga = null;
		try {
			OrdineEsecutivo ordEsec = this.getOrdineEsecutivo();
			YUdsAcquisto uds = (YUdsAcquisto)
					YUdsAcquisto.elementWithKey(YUdsAcquisto.class, 
							KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), this.getIdUds()}), PersistentObject.NO_LOCK);
			riga = (YUdsAcqRig) Factory.createObject(YUdsAcqRig.class);
			if(uds != null) {
				BigDecimal qtaBD = new BigDecimal(this.getQuantita().replace(",", "."));
				riga.setIdAzienda(Azienda.getAziendaCorrente());
				riga.setFather(uds);
				riga.setRArticolo(this.getIdArticolo());
				riga.setConfigurazione(ordEsec.getConfigurazione());
				riga.setRVersione(ordEsec.getIdVersione());
				riga.setQtaPrm(qtaBD);
				riga.setRAnnoOrdPrd(ordEsec.getIdAnnoOrdine());
				riga.setRNumOrdPrd(ordEsec.getIdNumeroOrdine());
				riga.setRAnnoOrdVen(ordEsec.getAnnoOrdineCliente() != null ? ordEsec.getAnnoOrdineCliente() : "");
				riga.setRNumeroOrdVen(ordEsec.getNumeroOrdineCliente() != null ? ordEsec.getNumeroOrdineCliente() : "");
				riga.setRRigaOrdVen(ordEsec.getRigaOrdineCliente() != null ? ordEsec.getRigaOrdineCliente() : null);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return riga;
	}

	@SuppressWarnings("unused")
	public ErrorList chiudiUds() {
		ErrorList errori = new ErrorList();
		try {
			int i = 0;
			if(this.getIdUds() != null) {
				YUdsAcquisto uds = (YUdsAcquisto)
						YUdsAcquisto.elementWithKey(YUdsAcquisto.class, 
								KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), this.getIdUds()}), PersistentObject.NO_LOCK);
				if(uds != null) {
					YRptUdsAcq rpt = (YRptUdsAcq) Factory.createObject(YRptUdsAcq.class);
					YEtichettUdsAcqBatch batch = (YEtichettUdsAcqBatch) Factory.createObject(YEtichettUdsAcqBatch.class);	
					BatchOptions batchOptions = (BatchOptions) Factory.createObject(BatchOptions.class);
					boolean ok = batchOptions.initDefaultValues(YEtichettUdsAcqBatch.class, "YEtichettUdsAcq", "RUN");
					batch.setBatchJob(batchOptions.getBatchJob());
					batch.setScheduledJob(batchOptions.getScheduledJob());
					batch.getBatchJob().setDescription("Stampa etichette uds");
					batch.getBatchJob().setBatchQueueId("DefQueue");
					batch.getBatchJob().setUserId(UtenteAzienda.getUtenteAziendaConnesso().getUtenteTherm().getId());
					batch.getBatchJob().setUserDescription("Stampa etichette uds");
					batch.setExecutePrint(true);
					//batch.getBatchJob().setACBatchJobId(String.valueOf(i++));
					if(batch.save() >= 0) {
						ConnectionManager.commit();
						rpt.setIdAzienda(Azienda.getAziendaCorrente());
						rpt.setIdUds(this.getIdUds());
						rpt.setBatchJobId(batchOptions.getBatchJob().getBatchJobId());
						rpt.setReportNr(1);
						rpt.setRigaJobId(i++);
						if(rpt.save() >= 0) {
							BatchService.submitJob(batch.getBatchJob());
							ConnectionManager.commit();
						}
					}
				}
			}	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return errori;
	}

}
