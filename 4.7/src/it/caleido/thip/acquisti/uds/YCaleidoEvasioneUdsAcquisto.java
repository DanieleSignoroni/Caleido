package it.caleido.thip.acquisti.uds;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;

import it.softre.thip.acquisti.uds.YUdsAcqRig;
import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaSec;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.acquisti.generaleAC.CausaleOrdineTestataAcq;
import it.thera.thip.acquisti.ordineAC.OrdineAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaPrm;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaSec;
import it.thera.thip.acquisti.ordineAC.web.OrdineAcquistoDataCollector;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloVersione;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.GestoreCalcoloCosti;
import it.thera.thip.base.comuniVenAcq.OrdineTestata;
import it.thera.thip.base.comuniVenAcq.TipoCostoRiferimento;
import it.thera.thip.base.comuniVenAcq.TipoRiga;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWeb;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWrapper;
import it.thera.thip.base.comuniVenAcq.web.DatiArticoloRigaAcquisto;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.base.generale.Numeratore;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.generale.UnitaMisura;
import it.thera.thip.base.prezziExtra.DocOrdRigaPrezziExtra;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.cs.ThipException;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneTM;
import it.thera.thip.vendite.generaleVE.PersDatiVen;
import it.thera.thip.vendite.ordineVE.GestoreEvasioneVendita;
import it.thera.thip.vendite.prezziExtra.DocRigaPrezziExtraVendita;
import it.thera.thip.vendite.prezziExtra.OrdineRigaPrezziExtraVendita;

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
							aggiornaRiferimentiOrdineAcquistoRigaUds(udsAcqRig, ordAcqRigPrm);
							ordAcqRigPrm.setGeneraRigheSecondarie(false);
							udsAcqRig.rendiDefinitivaUdsAcquisto();
							OrdineAcquistoRigaSec ordAcqRigSec = creaOrdineAcquistoRigaSec(ordAcq, ordAcqRigPrm, udsAcqRig);
							if(ordAcqRigSec != null) {
								ordAcqRigPrm.getRigheSecondarie().add(ordAcqRigSec);
							}
							udsAcqRig.save();
							ordAcq.getRighe().add(ordAcqRigPrm);
						}
					}else {
						throw new ThipException(new ErrorMessage("BAS0000078","Per l'articolo "+udsAcqRig.getRArticolo()+" non e' stato trovato il verniciato"));
					}
				}
				aggiornaRiferimentiOrdineAcquistoTestataUds(udsAcqTes, ordAcq);
				udsAcqTes.rendiDefinitivaUdsAcquisto();
				udsAcqTes.save();
			}
			docBODC.setAutoCommit(true);
			rc = docBODC.save();
			if(rc == OrdineAcquistoDataCollector.OK) {
				setKey(ordAcq.getKey());
			}else {
				throw new ThipException(docBODC.getErrorList().getErrors());
			}
		}else {
			throw new ThipException(docBODC.getErrorList().getErrors());
		}
		return 1;
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
		udsAcqTes.setRAnnoOrdAcq(ordAcq.getAnnoDocumento());
		udsAcqTes.setRNumOrdAcq(ordAcq.getNumeroDocumento());
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
		ordAcqRig.setIdAzienda(Azienda.getAziendaCorrente());
		ordAcqRig.setTestata(ordAcq);
		ordAcqRig.setIdCauRig(ordAcq.getCausale().getCausaleRigaKey());
		ordAcqRig.completaBO();
		ordAcqRig.setArticolo(articolo);
		UnitaMisura um = ordAcqRig.getArticolo().getUMDefaultVendita();
		ordAcqRig.setUMRif(um);
		ordAcqRig.setQtaInUMPrmMag(udsAcqRig.getQtaPrm());
		ordAcqRig.setQtaInUMRif(udsAcqRig.getQtaPrm());
		ordAcqRig.setQtaInUMSecMag(udsAcqRig.getQtaPrm());
		ordAcqRig.setConfigurazione(getConfigurazione(idEsternoConfig, ordAcqRig.getIdArticolo()));
		ordAcqRig.setIdUMRif(articolo.getArticoloDatiAcquisto().getIdUMPrimaria());
		ordAcqRig.setIdUMPrm(articolo.getIdUMPrmMag());
		ordAcqRig.setIdVersioneRcs(udsAcqRig.getRVersione());
		ricalcolaQta(ordAcqRig);
		ordAcqRig.setAssoggettamentoIVA(ordAcq.getAssoggettamentoIVA());
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
							ConfigurazioneTM.ID_CONFIG + "='" + idConfigEsterno + "' AND " +
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

	/**
	 * DSSOF3	04/10/2022	Metodo copiato dallo STD per eseguire il ricalcolo qta in base all'UM.
	 * @param riga
	 */
	public void ricalcolaQta(DocumentoAcqRigaPrm riga) {
		try {
			UnitaMisura umRif = getUnitaMisura(riga.getIdUMRif());
			UnitaMisura umPrm = getUnitaMisura(riga.getIdUMPrm());
			UnitaMisura umSec = getUnitaMisura(riga.getIdUMSec());
			UnitaMisura umOrigine = UnitaMisura.getUM(riga.getIdUMRif());
			Articolo articolo = (Articolo) riga.getArticolo();
			BigDecimal quant = riga.getQtaInUMPrm() != null ? riga.getQtaInUMPrm() : new BigDecimal(0);
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
			if(cqw != null) {
				String qtaPrmMagStr = cqw.getQuantRigaUMPrmMag().replace(",", ".");
				BigDecimal qtaPrmMag = new BigDecimal(qtaPrmMagStr);
				if(qtaPrmMag != null)
					riga.getQtaAttesaEvasione().setQuantitaInUMRif(qtaPrmMag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			if(cqw != null) {
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

	public void ricalcolaQta(DocumentoAcqRigaSec riga) {
		try {
			UnitaMisura umRif = getUnitaMisura(riga.getIdUMRif());
			UnitaMisura umPrm = getUnitaMisura(riga.getIdUMPrm());
			UnitaMisura umSec = getUnitaMisura(riga.getIdUMSec());
			UnitaMisura umOrigine = UnitaMisura.getUM(riga.getIdUMRif());
			Articolo articolo = (Articolo) riga.getArticolo();
			BigDecimal quant = riga.getQtaInUMAcq() != null ? riga.getQtaInUMAcq() : new BigDecimal(0);
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

	/**
	 * DSSOF3	70687	Se sulla riga acquisto è presente la riga ordine acuisto aggiorno gli attributi
	 * 					della riga documento acquisto tramite l'ordine.
	 * @param docAcqRigPrm
	 * @param udsAcqRig
	 */
	public void aggiornaAttributiDaRigaOrdine(DocumentoAcqRigaPrm docAcqRigPrm, YUdsAcqRig udsAcqRig) {
		try {
			if(udsAcqRig.getRAnnoOrdAcq() != null &&  udsAcqRig.getRNumOrdAcq() != null &&  udsAcqRig.getRRigaOrdAcq().toString() != null) {
				OrdineAcquistoRigaPrm rigaOrdine = (OrdineAcquistoRigaPrm) OrdineAcquistoRigaPrm.elementWithKey(OrdineAcquistoRigaPrm.class,
						KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(), udsAcqRig.getRAnnoOrdAcq(), udsAcqRig.getRNumOrdAcq(), udsAcqRig.getRRigaOrdAcq().toString()}), 0);
				if (rigaOrdine != null) {
					docAcqRigPrm.setRigaOrdine(rigaOrdine);
					docAcqRigPrm.setRRigaOrd(rigaOrdine.getNumeroRigaDocumento());
					docAcqRigPrm.setSpecializzazioneRiga(rigaOrdine.getSpecializzazioneRiga());

					docAcqRigPrm.setSequenzaRiga(rigaOrdine.getSequenzaRiga());
					docAcqRigPrm.setTipoRiga(rigaOrdine.getTipoRiga());
					if (rigaOrdine.getTipoRiga() == TipoRiga.OMAGGIO) {
						docAcqRigPrm.setServizioCalcDatiAcquisto(false);
					}
					docAcqRigPrm.setMagazzino(rigaOrdine.getMagazzino());
					docAcqRigPrm.setArticolo(rigaOrdine.getArticolo());
					docAcqRigPrm.setArticoloVersSaldi(rigaOrdine.getArticoloVersSaldi());
					docAcqRigPrm.setArticoloVersRichiesta(rigaOrdine.getArticoloVersRichiesta());
					docAcqRigPrm.setConfigurazione(rigaOrdine.getConfigurazione());
					docAcqRigPrm.setDescrizioneArticolo(rigaOrdine.getDescrizioneArticolo());
					docAcqRigPrm.setDescrizioneExtArticolo(rigaOrdine.getDescrizioneExtArticolo());
					String nota = docAcqRigPrm.getNota();
					if (nota != null && !nota.equals("")) {
						if (rigaOrdine.getNota() != null && !rigaOrdine.getNota().equals("")) {
							nota = rigaOrdine.getNota()  + " " + nota;
						}
					} else {
						nota = rigaOrdine.getNota();
					}
					if (nota != null && nota.length() > 250) {
						nota = nota.substring(0, 250);
					}
					docAcqRigPrm.setNota(nota);
					docAcqRigPrm.setDocumentoMM(rigaOrdine.getDocumentoMM());
					docAcqRigPrm.setSpesa(rigaOrdine.getSpesa());
					docAcqRigPrm.setImportoPercentualeSpesa(rigaOrdine.getImportoPercentualeSpesa());
					docAcqRigPrm.setSpesaPercentuale(rigaOrdine.isSpesaPercentuale());
					docAcqRigPrm.setUMRifKey(rigaOrdine.getUMRifKey());
					docAcqRigPrm.setUMPrmKey(rigaOrdine.getUMPrmKey());
					docAcqRigPrm.setUMSecKey(rigaOrdine.getUMSecKey());
					docAcqRigPrm.setRicalcoloQtaFattoreConv(rigaOrdine.isRicalcoloQtaFattoreConv());
					docAcqRigPrm.setCoefficienteImpiego(rigaOrdine.getCoefficienteImpiego());
					docAcqRigPrm.setBloccoRicalcoloQtaComp(rigaOrdine.isBloccoRicalcoloQtaComp());
					docAcqRigPrm.setTipoCostoRiferimento(rigaOrdine.getTipoCostoRiferimento());
					docAcqRigPrm.setDataConsegnaRichiesta(rigaOrdine.getDataConsegnaRichiesta());
					docAcqRigPrm.setDataConsegnaConfermata(rigaOrdine.getDataConsegnaConfermata());
					docAcqRigPrm.setSettConsegnaRichiesta(rigaOrdine.getSettConsegnaRichiesta());
					docAcqRigPrm.setSettConsegnaConfermata(rigaOrdine.getSettConsegnaConfermata());
					docAcqRigPrm.setIdListino(rigaOrdine.getIdListino());
					BigDecimal bd = GestoreEvasioneVendita.getBigDecimalZero();
					bd = rigaOrdine.getPrezzo() == null ? GestoreEvasioneVendita.getBigDecimalZero() : rigaOrdine.getPrezzo();
					docAcqRigPrm.setPrezzo(bd);
					bd = rigaOrdine.getPrezzoExtra() == null ? GestoreEvasioneVendita.getBigDecimalZero() : rigaOrdine.getPrezzoExtra();
					docAcqRigPrm.setPrezzoExtra(bd);
					bd = rigaOrdine.getPrezzoListino() == null ? GestoreEvasioneVendita.getBigDecimalZero() : rigaOrdine.getPrezzoListino();
					docAcqRigPrm.setPrezzoListino(bd);
					bd = rigaOrdine.getPrezzoExtraListino() == null ? GestoreEvasioneVendita.getBigDecimalZero() : rigaOrdine.getPrezzoExtraListino();
					docAcqRigPrm.setPrezzoExtraListino(bd);
					docAcqRigPrm.setRiferimentoUMPrezzo(rigaOrdine.getRiferimentoUMPrezzo());
					docAcqRigPrm.setTipoPrezzo(rigaOrdine.getTipoPrezzo());
					docAcqRigPrm.setBloccoRclPrzScnFatt(rigaOrdine.isBloccoRclPrzScnFatt());
					docAcqRigPrm.setProvenienzaPrezzo(rigaOrdine.getProvenienzaPrezzo());
					docAcqRigPrm.setTipoRigaListino(rigaOrdine.getTipoRigaListino());
					docAcqRigPrm.setAssoggettamentoIVA(rigaOrdine.getAssoggettamentoIVA());
					docAcqRigPrm.setScontoArticolo1(rigaOrdine.getScontoArticolo1());
					docAcqRigPrm.setScontoArticolo2(rigaOrdine.getScontoArticolo2());
					docAcqRigPrm.setMaggiorazione(rigaOrdine.getMaggiorazione());
					docAcqRigPrm.setSconto(rigaOrdine.getSconto());
					docAcqRigPrm.setPrcScontoIntestatario(rigaOrdine.getPrcScontoIntestatario());
					docAcqRigPrm.setPrcScontoModalita(rigaOrdine.getPrcScontoModalita());
					docAcqRigPrm.setScontoModalita(rigaOrdine.getScontoModalita());
					docAcqRigPrm.setCommessa(rigaOrdine.getCommessa());
					docAcqRigPrm.setCentroCosto(rigaOrdine.getCentroCosto());
					if (rigaOrdine.getDatiCA() != null){
						docAcqRigPrm.getDatiCA().setVoceSpesaCA(rigaOrdine.getDatiCA().getVoceSpesaCA());
						docAcqRigPrm.getDatiCA().setVoceCA4(rigaOrdine.getDatiCA().getVoceCA4());
						docAcqRigPrm.getDatiCA().setVoceCA5(rigaOrdine.getDatiCA().getVoceCA5());
						docAcqRigPrm.getDatiCA().setVoceCA6(rigaOrdine.getDatiCA().getVoceCA6());
						docAcqRigPrm.getDatiCA().setVoceCA7(rigaOrdine.getDatiCA().getVoceCA7());
						docAcqRigPrm.getDatiCA().setVoceCA8(rigaOrdine.getDatiCA().getVoceCA8());
					}
					docAcqRigPrm.setGruppoContiAnalitica(rigaOrdine.getGruppoContiAnalitica());
					docAcqRigPrm.setPriorita(rigaOrdine.getPriorita());
					docAcqRigPrm.setStatoInvioEDI(rigaOrdine.getStatoInvioEDI());
					docAcqRigPrm.setStatoInvioDatawarehouse(rigaOrdine.getStatoInvioDatawarehouse());
					docAcqRigPrm.setStatoInvioLogistica(rigaOrdine.getStatoInvioLogistica());
					docAcqRigPrm.setStatoInvioContAnalitica(rigaOrdine.getStatoInvioContAnalitica());
					docAcqRigPrm.setNonFatturare(rigaOrdine.isNonFatturare());
					docAcqRigPrm.setFlagRiservatoUtente1(rigaOrdine.getFlagRiservatoUtente1());
					docAcqRigPrm.setFlagRiservatoUtente2(rigaOrdine.getFlagRiservatoUtente2());
					docAcqRigPrm.setFlagRiservatoUtente3(rigaOrdine.getFlagRiservatoUtente3());
					docAcqRigPrm.setFlagRiservatoUtente4(rigaOrdine.getFlagRiservatoUtente4());
					docAcqRigPrm.setFlagRiservatoUtente5(rigaOrdine.getFlagRiservatoUtente5());
					docAcqRigPrm.setAlfanumRiservatoUtente1(rigaOrdine.getAlfanumRiservatoUtente1());
					docAcqRigPrm.setAlfanumRiservatoUtente2(rigaOrdine.getAlfanumRiservatoUtente2());
					docAcqRigPrm.setNumeroRiservatoUtente1(rigaOrdine.getNumeroRiservatoUtente1());
					docAcqRigPrm.setNumeroRiservatoUtente2(rigaOrdine.getNumeroRiservatoUtente2());
					docAcqRigPrm.setCostoUnitario(rigaOrdine.getCostoUnitario());
					PersDatiVen pdv = PersDatiVen.getCurrentPersDatiVen();
					if (pdv.getTipoCostoRiferimento() == TipoCostoRiferimento.COSTO_ULTIMO){
						GestoreCalcoloCosti gesCalcoloCosti = (GestoreCalcoloCosti)Factory.createObject(GestoreCalcoloCosti.class);
						gesCalcoloCosti.initialize(rigaOrdine.getIdAzienda(), rigaOrdine.getIdArticolo(), rigaOrdine.getIdVersioneSal(),
								rigaOrdine.getIdConfigurazione(), rigaOrdine.getIdMagazzino());
						gesCalcoloCosti.impostaCostoUnitario();
						docAcqRigPrm.setCostoUnitario(gesCalcoloCosti.getCostoUnitario());
					}
					if (rigaOrdine.getRigaPrezziExtra() != null) {
						docAcqRigPrm.istanziaRigaPrezziExtra();
						if (docAcqRigPrm.getRigaPrezziExtra() != null) {
							DocRigaPrezziExtraVendita rigaPrezzi = (DocRigaPrezziExtraVendita)docAcqRigPrm.getRigaPrezziExtra();
							DocOrdRigaPrezziExtra rigaPrezziOrd = rigaOrdine.getRigaPrezziExtra();
							rigaPrezzi.setAnnoContrattoMateriaPrima(rigaPrezziOrd.getAnnoContrattoMateriaPrima());
							rigaPrezzi.setIdAzienda(rigaPrezziOrd.getIdAzienda());
							rigaPrezzi.setIdRigaCondizione(rigaPrezziOrd.getIdRigaCondizione());
							rigaPrezzi.setIdSchemaPrezzo(rigaPrezziOrd.getIdSchemaPrezzo());
							rigaPrezzi.setNumContrattoMateriaPrima(rigaPrezziOrd.getNumContrattoMateriaPrima());
							rigaPrezzi.setRAnnoCantiere(((OrdineRigaPrezziExtraVendita)rigaPrezziOrd).getRAnnoCantiere());
							rigaPrezzi.setRNumeroCantiere(((OrdineRigaPrezziExtraVendita)rigaPrezziOrd).getRNumeroCantiere());
							rigaPrezzi.setRRigaCantiere(((OrdineRigaPrezziExtraVendita)rigaPrezziOrd).getRRigaCantiere());
							rigaPrezzi.setPrezzoRiferimento(((OrdineRigaPrezziExtraVendita)rigaPrezziOrd).getPrezzoRiferimento());
							try {
								rigaPrezzi.getPrezziExtra().setEqual(rigaPrezziOrd.getPrezziExtra());
							}
							catch (Exception ex) {
								ex.printStackTrace();
							}
							rigaPrezzi.setRigaOrdine(rigaOrdine);
							rigaPrezzi.setIdDetRigaOrdine(rigaOrdine.getDettaglioRigaDocumento());
						}
					}
					if (docAcqRigPrm.getRigaOrdine() != null) {
						OrdineAcquisto ordVen = (OrdineAcquisto)docAcqRigPrm.getRigaOrdine().getTestata();
						if (ordVen.getTipoEvasioneOrdine() == OrdineTestata.SALDO_AUTOMATICO) {
							docAcqRigPrm.setRigaSaldata(true);
						}
					}
					docAcqRigPrm.setPrezzoNetto(rigaOrdine.getPrezzoNetto());
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * DSSOF3	70687	04/10/2022	Aggiorno i riferimenti al DocumentoAcquisto nella riga uds acquisto.
	 * @param udsAcqRig
	 * @param docAcqRigPrm
	 */
	public void aggiornaRiferimentiDocumentoAcquistoRigaUds(YUdsAcqRig udsAcqRig, DocumentoAcqRigaPrm docAcqRigPrm) {
		udsAcqRig.setRAnnoDocAcq(docAcqRigPrm.getTestata().getAnnoDocumento());
		udsAcqRig.setDocumentoAcquisto((DocumentoAcquisto) docAcqRigPrm.getTestata());
		udsAcqRig.setRNumDocAcq(docAcqRigPrm.getTestata().getNumeroDocumento());
		udsAcqRig.setRRigaDocAcq(docAcqRigPrm.getSequenzaRiga());
		if(docAcqRigPrm.getDettaglioRigaDocumento() == null) {
			udsAcqRig.setRRigaDetDocAcq(docAcqRigPrm.getDettaglioRigaDocumento());
		}
	}

	public void aggiornaRiferimentiOrdineAcquistoRigaUds(YUdsAcqRig udsAcqRig, OrdineAcquistoRigaPrm ordAcqRig) {
		udsAcqRig.setRAnnoOrdAcq(ordAcqRig.getTestata().getAnnoDocumento());
		udsAcqRig.setRNumOrdAcq(ordAcqRig.getTestata().getNumeroDocumento());
		udsAcqRig.setRRigaOrdAcq(ordAcqRig.getSequenzaRiga());
		if(ordAcqRig.getDettaglioRigaDocumento() == null) {
			udsAcqRig.setRRigaOrdAcq(ordAcqRig.getDettaglioRigaDocumento());
		}
	}

	/**
	 * DSSOF3	70687	04/10/2022	Aggiorno i riferimenti al DocumentoAcquisto in testata uds acquisto.
	 * @param udsAcqTes
	 * @param docAcqTes
	 */
	public void aggiornaRiferimentiDocumentoAcquistoTestataUds(YUdsAcquisto udsAcqTes, DocumentoAcquisto docAcqTes) {
		udsAcqTes.setRAnnoDocAcq(docAcqTes.getAnnoDocumento());
		udsAcqTes.setRNumDocAcq(docAcqTes.getNumeroDocumento());
	}
}
