package it.caleido.thip.acquisti.uds;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;

import it.softre.thip.acquisti.uds.YUdsAcqRig;
import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.acquisti.generaleAC.CausaleOrdineTestataAcq;
import it.thera.thip.acquisti.ordineAC.OrdineAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaPrm;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaSec;
import it.thera.thip.acquisti.ordineAC.web.OrdineAcquistoDataCollector;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloVersione;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWeb;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWrapper;
import it.thera.thip.base.comuniVenAcq.web.DatiArticoloRigaAcquisto;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.base.generale.Numeratore;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.generale.UnitaMisura;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.cs.ThipException;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneTM;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 06/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    06/11/2025  DSSOF3   Prima stesura
 */

public class YCaleidoEvasioneUdsAcquisto extends BusinessObjectAdapter {

	protected String iIdAzienda;

	protected Date iDataDocumento;

	protected Date iDataRifIntestatario;

	protected String iNumeroRifIntestatario;

	protected String iChiaviSelezionati;

	protected Proxy iCauOrdAcqTes = new Proxy(it.thera.thip.acquisti.generaleAC.CausaleOrdineTestataAcq.class);

	protected Proxy iSerieOrdAcq = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iFornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

	protected Proxy iNumeratoreOrdAcq = new Proxy(Numeratore.class);

	public YCaleidoEvasioneUdsAcquisto(){
		setIdAzienda(Azienda.getAziendaCorrente());
		setDataDocumento(TimeUtils.getCurrentDate());
		setIdNumeratoreOrdAcq("ORD_ACQ");
	}

	public String getIdNumeratoreOrdAcq() {
		return KeyHelper.getTokenObjectKey(iNumeratoreOrdAcq.getKey(), 2);
	}

	public void setIdNumeratoreOrdAcq(String iRIdNumeratoreSerie) {
		iSerieOrdAcq.setKey(KeyHelper.replaceTokenObjectKey(iSerieOrdAcq.getKey(),2, iRIdNumeratoreSerie));
		iNumeratoreOrdAcq.setKey(KeyHelper.replaceTokenObjectKey(iNumeratoreOrdAcq.getKey(),2, iRIdNumeratoreSerie));
	}

	public void setNumeratoreOrdAcqKey(String key) {
		iNumeratoreOrdAcq.setKey(key);
	}

	public String getNumeratoreOrdAcqKey() {
		return iNumeratoreOrdAcq.getKey();
	}

	public void setNumeratoreOrdAcq(Numeratore numRcvFsc) {
		iNumeratoreOrdAcq.setObject(numRcvFsc);
	}

	public Numeratore getNumeratoreOrdAcq() {
		return (Numeratore)iNumeratoreOrdAcq.getObject();
	}

	public CausaleOrdineTestataAcq getCausaleOrdAcq() {
		return (CausaleOrdineTestataAcq)iCauOrdAcqTes.getObject();
	}

	public void setCausaleOrdAcq(CausaleOrdineTestataAcq causaleOrdineTestataAcq) {
		this.iCauOrdAcqTes.setObject(causaleOrdineTestataAcq);
	}

	public String getIdCauOrdAcqTes() {
		return KeyHelper.getTokenObjectKey(iCauOrdAcqTes.getKey(), 2);
	}

	public void setIdCauOrdAcqTes(String iRCauOrdAcqTes) {
		iCauOrdAcqTes.setKey(KeyHelper.replaceTokenObjectKey(iCauOrdAcqTes.getKey(), 2, iRCauOrdAcqTes));
	}

	public void setIdSerieOrdAcq(String iIdSerie) {
		iSerieOrdAcq.setKey(KeyHelper.replaceTokenObjectKey(iSerieOrdAcq.getKey(),3, iIdSerie));
	}

	public String getIdSerieOrdAcq() {
		return KeyHelper.getTokenObjectKey(iSerieOrdAcq.getKey(), 3);
	}

	public void setSerieOrdAcq(Serie serie) {
		this.iSerieOrdAcq.setObject(serie);
		setOnDB(false);
	}

	public Serie getSerieOrdAcq() {
		return (Serie) iSerieOrdAcq.getObject();
	}

	public void setSerieOrdAcqKey(String key) {
		iSerieOrdAcq.setKey(key);
		setOnDB(false);
	}

	public String getSerieOrdAcqKey() {
		return iSerieOrdAcq.getKey();
	}

	public void setFornitore(FornitoreAcquisto fornitore){
		this.iFornitore.setObject(fornitore);
	}

	public FornitoreAcquisto getFornitore(){
		return (FornitoreAcquisto)iFornitore.getObject();
	}

	public void setFornitoreKey(String key){
		iFornitore.setKey(key);
	}

	public String getFornitoreKey(){
		return iFornitore.getKey();
	}

	public void setIdFornitore(String idFornitore){
		String key = iFornitore.getKey();
		iFornitore.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idFornitore));
	}

	public String getIdFornitore(){
		String key = iFornitore.getKey();
		String objIdFornitore = KeyHelper.getTokenObjectKey(key,2);
		return objIdFornitore;
	}

	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iFornitore.getKey();
		iFornitore.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iCauOrdAcqTes.getKey();
		iCauOrdAcqTes.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		iSerieOrdAcq.setKey(KeyHelper.replaceTokenObjectKey(iSerieOrdAcq.getKey(), 1, idAzienda));
		iNumeratoreOrdAcq.setKey(KeyHelper.replaceTokenObjectKey(iNumeratoreOrdAcq.getKey(), 1, idAzienda));
	}

	public Date getDataRifIntestatario() {
		return iDataRifIntestatario;
	}

	public void setDataRifIntestatario(Date iDataRifIntestatario) {
		this.iDataRifIntestatario = iDataRifIntestatario;
	}

	public String getNumeroRifIntestatario() {
		return iNumeroRifIntestatario;
	}

	public void setNumeroRifIntestatario(String iNumeroRifIntestatario) {
		this.iNumeroRifIntestatario = iNumeroRifIntestatario;
	}

	public Date getDataDocumento() {
		return iDataDocumento;
	}

	public void setDataDocumento(Date iDataDocumento) {
		this.iDataDocumento = iDataDocumento;
	}

	public String getChiaviSelezionati() {
		return iChiaviSelezionati;
	}

	public void setChiaviSelezionati(String iChiaviSelezionati) {
		this.iChiaviSelezionati = iChiaviSelezionati;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = super.checkAll(components);
		java.util.Iterator lstChiaviSelectedIte = getListChiaviSelezionati().iterator();
		while (lstChiaviSelectedIte.hasNext()) {
			String nextKey = (String)lstChiaviSelectedIte.next();
			try {
				YUdsAcquisto udsAcqTes = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, nextKey, 0);
				if(udsAcqTes != null && (udsAcqTes.getSeqValore() == null || udsAcqTes.getSeqValore().compareTo(0) == 0)) {
					errors.add(new ErrorMessage("BAS0000078","Per l'UDS "+udsAcqTes.getIdUds()+" non e' stato selezionato il colore"));
				}	
				if(udsAcqTes != null && udsAcqTes.getOrdineAcquisto() != null) {
					errors.add(new ErrorMessage("BAS0000078","L'UDS "+udsAcqTes.getIdUds()+" ha gia' un ordine di acquisto collegato "+udsAcqTes.getOrdineAcquisto().getNumeroDocumentoFormattato()));
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return errors;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int save(boolean force) throws SQLException {
		OrdineAcquisto ordAcq = null;
		OrdineAcquistoDataCollector docBODC = (OrdineAcquistoDataCollector) Factory.createObject(OrdineAcquistoDataCollector.class);
		docBODC.setAutoCommit(false);
		docBODC.initialize(Factory.getName("OrdineAcquisto", Factory.CLASS_HDR), true, PersistentObject.OPTIMISTIC_LOCK);
		ordAcq = creaOrdineAcquisto();
		docBODC.setBo(ordAcq);
		int rc = docBODC.save();
		if(rc == OrdineAcquistoDataCollector.OK) {
			Map<YUdsAcqRig, String> righeUdsDaAggiornare = new HashMap<YUdsAcqRig, String>();
			java.util.Iterator lstChiaviSelectedIte = getListChiaviSelezionati().iterator();
			while (lstChiaviSelectedIte.hasNext()) {
				String nextKey = (String)lstChiaviSelectedIte.next();
				YUdsAcquisto udsAcqTes = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, nextKey, 0);
				Iterator iterRigheUdsAcqTes = udsAcqTes.getRigheUDSAcquisti().iterator();
				while(iterRigheUdsAcqTes.hasNext()) {
					YUdsAcqRig udsAcqRig = (YUdsAcqRig) iterRigheUdsAcqTes.next();

					Articolo verniciato = YRecuperoVerniciatoEvasioneUds.getInstance().trovaVerniciatoTramiteGrezzo(udsAcqRig.getRelarticolo());
					if(verniciato != null) {
						OrdineAcquistoRigaPrm ordAcqRigPrm = creaOrdineAcquistoRigaPrm(ordAcq, udsAcqRig, verniciato, udsAcqTes.getValore().getCarCodCfg());
						if(ordAcqRigPrm != null) {
							ordAcqRigPrm.setGeneraRigheSecondarie(false);
							OrdineAcquistoRigaSec ordAcqRigSec = creaOrdineAcquistoRigaSec(ordAcq, ordAcqRigPrm, udsAcqRig);
							if(ordAcqRigSec != null) {
								ordAcqRigPrm.getRigheSecondarie().add(ordAcqRigSec);
							}

							ordAcqRigPrm.setSalvaTestata(false);

							DocumentoDataCollector docBODCRigaPrm = creaOrdineAcquistoRigaPrmBODC(ordAcqRigPrm);
							int rcRig = docBODCRigaPrm.save();
							if(rcRig == BODataCollector.OK) {
								righeUdsDaAggiornare.put(udsAcqRig, ordAcqRigSec.getKey());
							}else{
								throw new ThipException(docBODCRigaPrm.getErrorList().getErrors());
							}
						}
					}else {
						throw new ThipException(new ErrorMessage("BAS0000078","Per l'articolo "+udsAcqRig.getRArticolo()+" non e' stato trovato il verniciato"));
					}
				}
				for (Map.Entry<YUdsAcqRig, String> entry : righeUdsDaAggiornare.entrySet()) {
					YUdsAcqRig udsAcqRig = entry.getKey();
					String[] vals = KeyHelper.unpackObjectKey(entry.getValue());

					udsAcqRig.setRAnnoOrdAcq(vals[1]);
					udsAcqRig.setRNumOrdAcq(vals[2]);
					udsAcqRig.setRRigaOrdAcq(KeyHelper.stringToIntegerObj(vals[3]));
					udsAcqRig.setRRigaDetOrdAcq(KeyHelper.stringToIntegerObj(vals[4]));

					udsAcqRig.save();
				}
				aggiornaRiferimentiOrdineAcquistoTestataUds(udsAcqTes, ordAcq);
				udsAcqTes.save();
			}
			docBODC.setAutoCommit(true);
			rc = docBODC.save();
			if(rc == OrdineAcquistoDataCollector.OK) {
				ConnectionManager.commit();
				setChiaviSelezionati(ordAcq.getKey());
			}else {
				throw new ThipException(docBODC.getErrorList().getErrors());
			}
		}else {
			throw new ThipException(docBODC.getErrorList().getErrors());
		}
		return 1;
	}

	public DocumentoDataCollector creaOrdineAcquistoRigaPrmBODC(OrdineAcquistoRigaPrm oar) {
		try {
			String className = "OrdineAcquistoRigaPrm";
			ClassADCollection cadc = ClassADCollectionManager.collectionWithName(className);
			String collectorName = cadc.getBODataCollector();
			if (collectorName == null) {
				collectorName = DocumentoDataCollector.class.getName();
			}
			DocumentoDataCollector bo = (DocumentoDataCollector) Factory.createObject(collectorName);
			bo.initialize(className);
			bo.setAutoCommit(false);
			bo.setBo(oar);
			bo.loadAttValue();
			bo.impostaSecondoCausale();
			return bo;
		}catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getListChiaviSelezionati() {
		List listKeys = new ArrayList();
		String listaKeys = getChiaviSelezionati();
		if(listaKeys == null || listaKeys.equals(""))
			return listKeys;
		java.util.StringTokenizer st = new java.util.StringTokenizer(listaKeys, ColonneFiltri.LISTA_SEP);
		while (st.hasMoreTokens()) {
			String stt = st.nextToken();
			listKeys.add(stt);
		}
		return listKeys;
	}


	public void aggiornaRiferimentiOrdineAcquistoTestataUds(YUdsAcquisto udsAcqTes, OrdineAcquisto ordAcq) {
		udsAcqTes.setOrdineAcquisto(ordAcq);
	}

	public void aggiornaRiferimentiOrdineAcquistoRigaUds(YUdsAcqRig udsAcqRig, OrdineAcquistoRigaPrm ordAcqRig) {
		udsAcqRig.setOrdineAcquistoRiga(ordAcqRig);
		if(ordAcqRig.getDettaglioRigaDocumento() == null) {
			udsAcqRig.setRRigaOrdAcq(ordAcqRig.getDettaglioRigaDocumento());
		}
	}

	public OrdineAcquistoRigaSec creaOrdineAcquistoRigaSec(OrdineAcquisto ordAcq, OrdineAcquistoRigaPrm rigaPrm, YUdsAcqRig udsAcqRig) {
		DatiArticoloRigaAcquisto datiArticolo = getDatiArticoloAcquisto(
				udsAcqRig.getRArticolo(), 
				(udsAcqRig.getIdConfigurazione() != null ? udsAcqRig.toString() : ""),
				ordAcq.getKey(), 
				(udsAcqRig.getRVersione() != null ? udsAcqRig.getRVersione().toString() : ""));
		OrdineAcquistoRigaSec rigaSec = (OrdineAcquistoRigaSec) Factory.createObject(OrdineAcquistoRigaSec.class);
		rigaSec.setIdAzienda(Azienda.getAziendaCorrente());
		rigaSec.setTestata(ordAcq);
		rigaSec.setFather(rigaPrm);
		rigaSec.setIdCauRig(rigaPrm.getCausaleRiga().getIdCausale());
		rigaSec.completaBO();
		rigaSec.setIdMagazzino(rigaPrm.getIdMagazzino());
		rigaSec.setIdArticolo(udsAcqRig.getRArticolo());
		UnitaMisura um = rigaPrm.getArticolo().getUMDefaultVendita();
		rigaSec.setUMRif(um);
		rigaSec.setConfigurazione(getConfigurazione((udsAcqRig.getIdConfigurazione() != null ? udsAcqRig.toString() : ""), udsAcqRig.getRArticolo()));
		rigaSec.setIdUMRif(datiArticolo.getIdUMRiferimentoDefault());
		rigaSec.setIdUMPrm(datiArticolo.getIdUMPrimaria());
		rigaSec.setQtaInUMPrmMag(udsAcqRig.getQtaPrm());
		rigaSec.setQtaInUMRif(udsAcqRig.getQtaPrm());
		rigaSec.setQtaInUMSecMag(udsAcqRig.getQtaPrm());
		rigaSec.setIdEsternoConfig(udsAcqRig.getRConfig());
		rigaSec.setIdVersioneRcs(udsAcqRig.getRVersione());
		return rigaSec;
	}

	protected OrdineAcquisto creaOrdineAcquisto() {
		OrdineAcquisto ordAcq = (OrdineAcquisto) Factory.createObject(OrdineAcquisto.class);
		ordAcq.setIdAzienda(Azienda.getAziendaCorrente());
		ordAcq.getNumeratoreHandler().setIdNumeratore(getIdNumeratoreOrdAcq());
		ordAcq.getNumeratoreHandler().setIdAzienda(Azienda.getAziendaCorrente());
		ordAcq.getNumeratoreHandler().setIdSerie(getIdSerieOrdAcq());
		ordAcq.getNumeratoreHandler().setDataDocumento(getDataDocumento());
		ordAcq.setCausale(getCausaleOrdAcq());
		ordAcq.setFornitore(getFornitore());
		ordAcq.setDataOrdineIntestatario(getDataRifIntestatario());
		String numeroRifIntestatario = getNumeroRifIntestatario();
		if(numeroRifIntestatario != null && numeroRifIntestatario.length() > 15) {
			numeroRifIntestatario = numeroRifIntestatario.substring(0,15);
		}
		ordAcq.setNumeroOrdineIntestatario(numeroRifIntestatario);
		ordAcq.setIdMagazzino(ordAcq.getCausale().getIdMagazzino());
		ordAcq.completaBO();
		return ordAcq;
	}

	public OrdineAcquistoRigaPrm creaOrdineAcquistoRigaPrm(OrdineAcquisto ordAcq, YUdsAcqRig udsAcqRig, Articolo articolo, String idEsternoConfig) throws SQLException {
		OrdineAcquistoRigaPrm ordAcqRig = (OrdineAcquistoRigaPrm)Factory.createObject(OrdineAcquistoRigaPrm.class);
		Configurazione conf = getConfigurazione(idEsternoConfig, articolo.getIdArticolo());
		if(conf != null) {
			DatiArticoloRigaAcquisto datiArticolo = getDatiArticoloAcquisto(
					articolo.getIdArticolo(), 
					conf.getIdConfigurazione().toString(),
					ordAcq.getKey(), 
					(udsAcqRig.getRVersione() != null ? udsAcqRig.getRVersione().toString() : ""));
			ordAcqRig.setIdAzienda(Azienda.getAziendaCorrente());
			ordAcqRig.setTestata(ordAcq);
			ordAcqRig.setIdCauRig(ordAcq.getCausale().getCausaleRigaKey());
			ordAcqRig.completaBO();
			ordAcqRig.setArticolo(articolo);
			ordAcqRig.setUMRif(datiArticolo.getUMRiferimentoDefault());
			ordAcqRig.setIdUMPrm(articolo.getIdUMPrmMag());
			BigDecimal qtaInUmRif = articolo.convertiUM(udsAcqRig.getQtaPrm(),ordAcqRig.getUMPrm(),ordAcqRig.getUMRif(),ordAcqRig.getArticoloVersRichiesta());
			ordAcqRig.setQtaInUMRif(qtaInUmRif);
			ordAcqRig.setQtaInUMPrmMag(udsAcqRig.getQtaPrm());
			ordAcqRig.setQtaInUMSecMag(udsAcqRig.getQtaSec() != null ? udsAcqRig.getQtaSec() : BigDecimal.ZERO);
			ordAcqRig.setConfigurazione(conf);
			ordAcqRig.setIdVersioneRcs(udsAcqRig.getRVersione());
			ordAcqRig.setAssoggettamentoIVA(ordAcq.getAssoggettamentoIVA());
			//.in dubbio se questi siano giusti cosi'...vediamo
			/*if(udsAcqRig.getRelordprd() != null) {
				OrdineEsecutivo oe = udsAcqRig.getRelordprd();
				ordAcqRig.setCliente(oe.getCliente());
				ordAcqRig.setAnnoOrdineCliente(oe.getAnnoOrdineCliente());
				ordAcqRig.setNumeroOrdineCliente(oe.getNumeroOrdineCliente());
				ordAcqRig.setRigaOrdineCliente(oe.getRigaOrdineCliente());
			}*/
		}
		return ordAcqRig;
	}

	public DatiArticoloRigaAcquisto getDatiArticoloAcquisto(String idArt,String idConf, String chiaveDoc, String idVers) {
		DatiArticoloRigaAcquisto datiArticolo = (DatiArticoloRigaAcquisto) Factory.createObject(DatiArticoloRigaAcquisto.class);
		try {
			String key = KeyHelper.buildObjectKey(
					new String[] {
							Azienda.getAziendaCorrente(),
							idArt
					}
					);
			Articolo artFinito = (Articolo)
					Articolo.elementWithKey(Articolo.class, key, 0);
			datiArticolo.setArticolo(artFinito);
			datiArticolo.setParIdConfigEsterno(idConf);
			datiArticolo.setParIdVersione(idVers);
			datiArticolo.setChiaveDocumento(new String[] {chiaveDoc});
			datiArticolo.impostaDatiArticolo();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return datiArticolo;
	}

	@SuppressWarnings("rawtypes")
	public Configurazione getConfigurazione(String idConfigEsterno, String idArticolo) {
		Configurazione conf = null;
		try {
			String where =
					ConfigurazioneTM.ID_AZIENDA + "='" + Azienda.getAziendaCorrente() + "' AND " +
							ConfigurazioneTM.COD_CONFIG + "='" + idConfigEsterno + "' AND " +
							ConfigurazioneTM.R_ARTICOLO + "='" + idArticolo + "'";
			Vector v = Configurazione.retrieveList(where, "", false);
			conf = v.isEmpty() ? null : (Configurazione)v.elementAt(0);
		}
		catch (Exception ex) {
			Trace.print("Exception in DatiArticolo.impostaNuovaConfigurazione()");
			ex.printStackTrace();
		}
		return conf;
	}


	public void ricalcolaQta(OrdineAcquistoRigaPrm riga) {
		try {
			UnitaMisura umRif = getUnitaMisura(riga.getIdUMRif());
			UnitaMisura umPrm = getUnitaMisura(riga.getIdUMPrm());
			UnitaMisura umSec = getUnitaMisura(riga.getIdUMSec());
			UnitaMisura umOrigine = UnitaMisura.getUM(riga.getIdUMRif());
			Articolo articolo = (Articolo) riga.getArticolo();
			BigDecimal quant = riga.getQtaInUMRif() != null ? riga.getQtaInUMRif() : new BigDecimal(0);
			String idVersione = riga.getIdVersioneSal() != null ? riga.getIdVersioneSal().toString() : "";
			ArticoloVersione articoloVersione = getArticoloVersione(Azienda.getAziendaCorrente() ,articolo.getIdArticolo(), idVersione); 
			String dominio = "V";
			String siglaUMOrigin = "R";
			boolean rRicalcoloQta = true;
			String rIdUMDestinazione = riga.getIdUMPrm();
			String rSiglaUMDestinazione = "P";
			String rFlagRigaLotto = "R";
			String selectedRow = null;
			CalcoloQuantitaWeb cqw =  CalcoloQuantitaWrapper.get().calcolaQuantita(
					articolo, 
					articoloVersione, 
					quant, 
					siglaUMOrigin, 
					dominio, 
					umOrigine, umRif, umPrm, umSec, 
					rRicalcoloQta, 
					rIdUMDestinazione, rSiglaUMDestinazione, rFlagRigaLotto, 
					selectedRow);
			if(cqw != null && cqw.getQuantRigaUMPrmMag() != null) {
				String qtaPrmMagStr = cqw.getQuantRigaUMPrmMag().replace(",", ".");
				BigDecimal qtaPrmMag = new BigDecimal(qtaPrmMagStr);
				if(qtaPrmMag != null)
					riga.getQtaAttesaEvasione().setQuantitaInUMRif(qtaPrmMag); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ricalcolaQta(OrdineAcquistoRigaSec riga) {
		try {
			UnitaMisura umRif = getUnitaMisura(riga.getIdUMRif());
			UnitaMisura umPrm = getUnitaMisura(riga.getIdUMPrm());
			UnitaMisura umSec = getUnitaMisura(riga.getIdUMSec());
			UnitaMisura umOrigine = UnitaMisura.getUM(riga.getIdUMRif());
			Articolo articolo = (Articolo) riga.getArticolo();
			BigDecimal quant = riga.getQtaInUMRif() != null ? riga.getQtaInUMRif() : new BigDecimal(0);
			String idVersione = riga.getIdVersioneSal() != null ? riga.getIdVersioneSal().toString() : "";
			ArticoloVersione articoloVersione = getArticoloVersione(Azienda.getAziendaCorrente() ,articolo.getIdArticolo(), idVersione); 
			String dominio = "V";
			String siglaUMOrigin = "R";
			boolean rRicalcoloQta = true;
			String rIdUMDestinazione = riga.getIdUMPrm();
			String rSiglaUMDestinazione = "P";
			String rFlagRigaLotto = "R";
			String selectedRow = null;
			CalcoloQuantitaWeb cqw =  CalcoloQuantitaWrapper.get().calcolaQuantita(
					articolo, 
					articoloVersione, 
					quant, 
					siglaUMOrigin, 
					dominio, 
					umOrigine, umRif, umPrm, umSec, 
					rRicalcoloQta, 
					rIdUMDestinazione, rSiglaUMDestinazione, rFlagRigaLotto, 
					selectedRow);
			String qtaPrmMagStr = cqw.getQuantRigaUMPrmMag().replace(",", ".");
			BigDecimal qtaPrmMag = new BigDecimal(qtaPrmMagStr);
			if(qtaPrmMag != null)
				riga.getQtaAttesaEvasione().setQuantitaInUMRif(qtaPrmMag); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * DSSOF3	Metodo copiato dallo standard per prendere la versione dell'articolo.
	 * @param idAzienda
	 * @param idArticolo
	 * @param idVersione
	 * @return
	 */
	public ArticoloVersione getArticoloVersione(String idAzienda, String idArticolo, String idVersione) {
		ArticoloVersione av = null;
		String azienda = Azienda.getAziendaCorrente();
		if (idAzienda != null) {
			azienda = idAzienda;
		}
		String key = KeyHelper.buildObjectKey(new String[] {azienda, idArticolo, idVersione});
		try {
			av = ArticoloVersione.elementWithKey(key, PersistentObject.NO_LOCK);
		}
		catch(Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
		return av;
	}

	/**
	 * DSSOF3	Metodo copiato dallo standard per prendere l'unità di misura.
	 * @param idUM
	 * @return
	 */
	public UnitaMisura getUnitaMisura(String idUM) {
		UnitaMisura um = null;
		if(idUM == null || idUM.equals(""))
			return um;
		try {
			um = UnitaMisura.getUM(idUM);
		}
		catch(Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
		return um;
	}

}