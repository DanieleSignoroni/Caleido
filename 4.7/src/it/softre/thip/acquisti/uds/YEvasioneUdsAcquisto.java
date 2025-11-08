package it.softre.thip.acquisti.uds;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.common.ErrorList;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.DB2NetDatabase;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.SQLServerJTDSNoUnicodeDatabase;

import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.acquisti.documentoAC.web.DocumentoAcquistoDataCollector;
import it.thera.thip.acquisti.generaleAC.CausaleDocumentoTestataAcq;
import it.thera.thip.acquisti.ordineAC.OrdineAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaPrm;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloVersione;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.comuniVenAcq.GestoreCalcoloCosti;
import it.thera.thip.base.comuniVenAcq.OrdineTestata;
import it.thera.thip.base.comuniVenAcq.TipoCostoRiferimento;
import it.thera.thip.base.comuniVenAcq.TipoRiga;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWeb;
import it.thera.thip.base.comuniVenAcq.web.CalcoloQuantitaWrapper;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.base.generale.Numeratore;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.generale.UnitaMisura;
import it.thera.thip.base.prezziExtra.DocOrdRigaPrezziExtra;
import it.thera.thip.vendite.generaleVE.PersDatiVen;
import it.thera.thip.vendite.ordineVE.GestoreEvasioneVendita;
import it.thera.thip.vendite.prezziExtra.DocRigaPrezziExtraVendita;
import it.thera.thip.vendite.prezziExtra.OrdineRigaPrezziExtraVendita;

/**
 * 
 * @author DSSOF3	
 *	70687	DSSOF3	Classe per l'evasione di un uds acquisto, contiene i parametri della form.
 *					E contiene inoltre i metodi per eseguire l'evasione.
 */

public class YEvasioneUdsAcquisto extends BusinessObjectAdapter {

	protected String iIdAzienda;

	protected String iRSerie;

	protected Date iDataDocumento;

	protected String iRCauDocTes;

	protected String iRFornitore;

	protected Date iDataRifIntestatario;

	protected String iRIdNumeratore;

	protected String iNumeroRifIntestatario;

	protected Proxy iRelRCauDocTesAcq = new Proxy(it.thera.thip.acquisti.generaleAC.CausaleDocumentoTestataAcq.class);

	protected Proxy iRelSerie = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iRelFornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

	protected Proxy iRelNumeratore = new Proxy(Numeratore.class);

	public String getRIdNumeratore() {
		return KeyHelper.getTokenObjectKey(iRelNumeratore.getKey(), 2);
	}

	public void setRIdNumeratore(String iRIdNumeratoreSerie) {
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(),2, iRIdNumeratoreSerie));
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(),1, Azienda.getAziendaCorrente()));
		iRelNumeratore.setKey(KeyHelper.replaceTokenObjectKey(iRelNumeratore.getKey(),2, iRIdNumeratoreSerie));
		iRelNumeratore.setKey(KeyHelper.replaceTokenObjectKey(iRelNumeratore.getKey(),1, Azienda.getAziendaCorrente()));
	}

	public void setNumeratoreKey(String key) {
		iRelNumeratore.setKey(key);
	}

	public String getNumeratoreKey() {
		return iRelNumeratore.getKey();
	}

	public void setNumeratore(Numeratore numRcvFsc) {
		iRelNumeratore.setObject(numRcvFsc);
	}

	public Numeratore getNumeratore() {
		return (Numeratore)iRelNumeratore.getObject();
	}

	public void setRSerie(String iIdSerie) {
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(),3, iIdSerie));
	}

	public YEvasioneUdsAcquisto(){
		setIdAzienda(Azienda.getAziendaCorrente());
		setRIdNumeratore("DOC_ACQ");
	}
	public String getIdAzienda() {
		return iIdAzienda;
	}

	public void setIdAzienda(String iIdAzienda) {
		this.iIdAzienda = iIdAzienda;
		setIdAziendaInternal(iIdAzienda);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		String key1 = iRelFornitore.getKey();
		iRelFornitore.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		String key2 = iRelRCauDocTesAcq.getKey();
		iRelRCauDocTesAcq.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		iRelSerie.setKey(KeyHelper.replaceTokenObjectKey(iRelSerie.getKey(), 1, idAzienda));
		iRelNumeratore.setKey(KeyHelper.replaceTokenObjectKey(iRelNumeratore.getKey(), 1, idAzienda));
	}

	public String getRSerie() {
		return KeyHelper.getTokenObjectKey(iRelSerie.getKey(), 3);
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
	public String getRCauDocTes() {
		return iRCauDocTes;
	}

	public void setRCauDocTes(String iRCauDocTes) {
		this.iRCauDocTes = iRCauDocTes;
	}

	public String getRFornitore() {
		String key = iRelFornitore.getKey();
		String objRCliente = KeyHelper.getTokenObjectKey(key,2);
		return objRCliente;
	}
	public void setRFornitore(String iRFornitore) {
		String key = iRelFornitore.getKey();
		iRelFornitore.setKey(KeyHelper.replaceTokenObjectKey(key , 2, iRFornitore));
	}

	public CausaleDocumentoTestataAcq getCausale() {
		return (CausaleDocumentoTestataAcq)iRelRCauDocTesAcq.getObject();
	}

	public void setCausale(CausaleDocumentoTestataAcq iCausale) {
		this.iRelRCauDocTesAcq.setObject(iCausale);
	}

	public void setSerie(Serie serie) {
		this.iRelSerie.setObject(serie);
		setOnDB(false);
	}

	public Serie getSerie() {
		return (Serie) iRelSerie.getObject();
	}

	public void setSerieKey(String key) {
		iRelSerie.setKey(key);
		setOnDB(false);
	}

	public String getSerieKey() {
		return iRelSerie.getKey();
	}

	public void setFornitore(FornitoreAcquisto fornitore){
		iRelFornitore.setObject(fornitore);
		setOnDB(false);
	}

	public FornitoreAcquisto getFornitore(){
		return (FornitoreAcquisto) iRelFornitore.getObject();
	}

	public void setFornitoreKey(String fornitoreKey){
		iRelFornitore.setKey(fornitoreKey);
		setOnDB(false);
	}

	public String getFornitoreKey(){
		return iRelFornitore.getKey();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		java.util.Vector errors = new java.util.Vector();
		components.runAllChecks(errors);
		ErrorMessage em = checkSerieCausale();
		if(em != null)
			errors.add(em);
		return errors;
	}

	protected ErrorMessage checkSerieCausale() {
		ErrorMessage em = null;
		try {
			CausaleDocumentoTestataAcq cau = (CausaleDocumentoTestataAcq)
					CausaleDocumentoTestataAcq.elementWithKey(CausaleDocumentoTestataAcq.class,
							KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(),this.getRCauDocTes()}), 0);
			if(cau != null) {
				if(cau.getTipiGestione().getTPGestioneRiferimentoFor().getTipoGestione() == '1'
						&& (this.getDataRifIntestatario() == null || this.getNumeroRifIntestatario() == null)){
					em = new ErrorMessage("YSOFTRE003","Data e numero riferimento sono obbligatori");
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return em;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object evasioneUdsAcquisto(String[] chiaviSel, Date data, String idCausale, String idSerie, String idCliente, Date dataRifIntestatarioDocAcq, String numeroRifIntestatario) {
		ErrorList errori = new ErrorList();
		String chiave = "";
		Object ogg = new Object[] {errori,chiave};
		boolean commit = false;
		try {
			DocumentoAcquistoDataCollector docBODC = (DocumentoAcquistoDataCollector) Factory.createObject(DocumentoAcquistoDataCollector.class);
			docBODC.setAutoCommit(false);
			docBODC.initialize(Factory.getName("DocumentoAcquisto", Factory.CLASS_HDR), true, PersistentObject.OPTIMISTIC_LOCK);
			DocumentoAcquisto docAcqTes = creaDocumentoAcquisto(data, idCausale, idSerie, idCliente, dataRifIntestatarioDocAcq, numeroRifIntestatario);
			docBODC.setBo(docAcqTes);
			docBODC.loadAttValue();
			int rc = docBODC.save();
			if(rc == DocumentoAcquistoDataCollector.OK) {
				if(docAcqTes.save() >= 0) {
					commit = true;
				}
			}
			else {
				errori.getErrors().addAll(docBODC.getErrorList().getErrors());
			}
			if(rc == DocumentoAcquistoDataCollector.OK) {
				docAcqTes.retrieve();
				chiave = docAcqTes.getKey();
				for(int i = 0; i < chiaviSel.length ; i++ ) {
					YUdsAcquisto udsAcqTes = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, chiaviSel[i], 0);
					Iterator iterRigheUdsAcqTes = udsAcqTes.getRigheUDSAcquisti().iterator();
					while(iterRigheUdsAcqTes.hasNext()) {
						YUdsAcqRig udsAcqRig = (YUdsAcqRig) iterRigheUdsAcqTes.next();
						DocumentoAcqRigaPrm docAcqRigPrm = creaDocumentoAcquistoRigaPrm(docAcqTes, udsAcqRig);
						aggiornaAttributiDaRigaOrdine(docAcqRigPrm, udsAcqRig);
						aggiornaRiferimentiDocumentoAcquistoRigaUds(udsAcqRig, docAcqRigPrm);
						if(docAcqRigPrm.save() < 0) {
							commit = false;
						}
						udsAcqRig.rendiDefinitivaUdsAcquisto();
						if(udsAcqRig.save() < 0) {
							commit = false;
						}
					}
					aggiornaRiferimentiDocumentoAcquistoTestataUds(udsAcqTes, docAcqTes);
					udsAcqTes.rendiDefinitivaUdsAcquisto();
					if(udsAcqTes.save() < 0) {
						commit = false;
					}
				}
				if(commit) {
					ConnectionManager.commit();
				}else {
					ConnectionManager.rollback();
				}
			}
		}catch(Exception e ) {
			e.printStackTrace(Trace.excStream);
		}
		ogg = new Object[]{errori,chiave};
		return ogg;
	}

	/**
	 * DSSOF3	70687	Creazione documento acquisto testata.
	 * @param data
	 * @param idCausale
	 * @param idSerie
	 * @param idFornitore
	 * @param dataRifIntestatario
	 * @param numeroRifIntestatario
	 * @return
	 */
	public static DocumentoAcquisto creaDocumentoAcquisto(Date data, String idCausale, String idSerie, String idFornitore, Date dataRifIntestatario, String numeroRifIntestatario) {
		DocumentoAcquisto docAcqTes = (DocumentoAcquisto)Factory.createObject(DocumentoAcquisto.class);
		docAcqTes.setIdAzienda(Azienda.getAziendaCorrente());
		docAcqTes.setIdCau(idCausale);
		docAcqTes.setIdFornitore(idFornitore);
		docAcqTes.getNumeratoreHandler().setIdAzienda(Azienda.getAziendaCorrente());
		docAcqTes.getNumeratoreHandler().setIdSerie(idSerie);
		docAcqTes.getNumeratoreHandler().setDataDocumento(data);
		docAcqTes.setDataRifIntestatario(dataRifIntestatario);
		docAcqTes.setNumeroRifIntestatario(numeroRifIntestatario);
		docAcqTes.setIdMagazzino(docAcqTes.getCausale().getIdMagazzino());
		docAcqTes.completaBO();
		return docAcqTes;
	}

	/**
	 * DSSOF3	04/10/2022	Creazione documento acquisto riga.
	 * @param docAcqTes
	 * @param udsAcqRig
	 * @return
	 */
	public static DocumentoAcqRigaPrm creaDocumentoAcquistoRigaPrm(DocumentoAcquisto docAcqTes, YUdsAcqRig udsAcqRig) {
		DocumentoAcqRigaPrm docAcqRig = (DocumentoAcqRigaPrm)Factory.createObject(DocumentoAcqRigaPrm.class);
		docAcqRig.setIdAzienda(Azienda.getAziendaCorrente());
		docAcqRig.setTestata(docAcqTes);
		docAcqRig.setIdCauRig(docAcqTes.getCausale().getCausaleRigaKey());
		docAcqRig.completaBO();
		docAcqRig.setIdArticolo(udsAcqRig.getRArticolo());
		docAcqRig.cambiaArticolo(docAcqRig.getArticolo(), docAcqRig.getConfigurazione(), false);
		UnitaMisura um = docAcqRig.getArticolo().getUMDefaultVendita();
		docAcqRig.setUMRif(um);
		docAcqRig.setQtaInUMAcq(udsAcqRig.getQtaPrm());
		docAcqRig.setQtaInUMPrm(udsAcqRig.getQtaPrm());
		docAcqRig.setIdEsternoConfig(udsAcqRig.getRConfig());
		docAcqRig.setIdVersioneRcs(udsAcqRig.getRVersione());
		ricalcolaQta(docAcqRig);
		return docAcqRig;
	}

	/**
	 * DSSOF3	04/10/2022	Metodo copiato dallo STD per eseguire il ricalcolo qta in base all'UM.
	 * @param riga
	 */
	public static void ricalcolaQta(DocumentoAcqRigaPrm riga) {
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
			String qtaPrmMagStr = cqw.getQuantRigaUMPrmMag().replace(",", ".");
			BigDecimal qtaPrmMag = new BigDecimal(qtaPrmMagStr);
			riga.getQtaAttesaEvasione().setQuantitaInUMRif(qtaPrmMag);
		} catch (Exception e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	/**
	 * DSSOF3	Metodo copiato dallo standard per prendere la versione dell'articolo.
	 * @param idAzienda
	 * @param idArticolo
	 * @param idVersione
	 * @return
	 */
	public static ArticoloVersione getArticoloVersione(String idAzienda, String idArticolo, String idVersione) {
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
	public static UnitaMisura getUnitaMisura(String idUM) {
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
	public static void aggiornaAttributiDaRigaOrdine(DocumentoAcqRigaPrm docAcqRigPrm, YUdsAcqRig udsAcqRig) {
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
								ex.printStackTrace(Trace.excStream);
							}
							rigaPrezzi.setRigaOrdine(rigaOrdine);
							rigaPrezzi.setIdDetRigaOrdine(rigaOrdine.getDettaglioRigaDocumento());
						}
					}
					if (docAcqRigPrm.getRigaOrdine() != null) {
						OrdineAcquisto ordAcq = (OrdineAcquisto)docAcqRigPrm.getRigaOrdine().getTestata();
						if (ordAcq.getTipoEvasioneOrdine() == OrdineTestata.SALDO_AUTOMATICO) {
							docAcqRigPrm.setRigaSaldata(true);
						}
					}
					docAcqRigPrm.setPrezzoNetto(rigaOrdine.getPrezzoNetto());
				}
			}
		}catch(Exception e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	/**
	 * DSSOF3	70687	04/10/2022	Aggiorno i riferimenti al DocumentoAcquisto nella riga uds acquisto.
	 * @param udsAcqRig
	 * @param docAcqRigPrm
	 */
	public static void aggiornaRiferimentiDocumentoAcquistoRigaUds(YUdsAcqRig udsAcqRig, DocumentoAcqRigaPrm docAcqRigPrm) {
		udsAcqRig.setRAnnoDocAcq(docAcqRigPrm.getTestata().getAnnoDocumento());
		udsAcqRig.setDocumentoAcquisto((DocumentoAcquisto) docAcqRigPrm.getTestata());
		udsAcqRig.setRNumDocAcq(docAcqRigPrm.getTestata().getNumeroDocumento());
		udsAcqRig.setRRigaDocAcq(docAcqRigPrm.getSequenzaRiga());
		if(docAcqRigPrm.getDettaglioRigaDocumento() == null) {
			udsAcqRig.setRRigaDetDocAcq(docAcqRigPrm.getDettaglioRigaDocumento());
		}
	}

	/**
	 * DSSOF3	70687	04/10/2022	Aggiorno i riferimenti al DocumentoAcquisto in testata uds acquisto.
	 * @param udsAcqTes
	 * @param docAcqTes
	 */
	public static void aggiornaRiferimentiDocumentoAcquistoTestataUds(YUdsAcquisto udsAcqTes, DocumentoAcquisto docAcqTes) {
		udsAcqTes.setRAnnoDocAcq(docAcqTes.getAnnoDocumento());
		udsAcqTes.setRNumDocAcq(docAcqTes.getNumeroDocumento());
	}

	/**
	 * DSSOF3	70687	04/10/2022	Metodino per formattare la data da stringa a java.sql.Date 
	 * @param data
	 * @return
	 */
	public static Date getDataDocumentoFormattata(String data) {
		java.sql.Date date = null;
		try {
			SimpleDateFormat formatoWeb = new SimpleDateFormat("dd/MM/yyyy");
			//SimpleDateFormat formatoSql = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoDB2 = new SimpleDateFormat("yyyy-MM-dd");
			String dataOk = "";
			if(ConnectionManager.getCurrentDatabase() instanceof SQLServerJTDSNoUnicodeDatabase) 
				dataOk = formatoDB2.format(formatoWeb.parse(data));
			else if(ConnectionManager.getCurrentDatabase() instanceof DB2NetDatabase) 
				dataOk = formatoDB2.format(formatoWeb.parse(data));
			date = java.sql.Date.valueOf(dataOk);
		} catch (ParseException e) {
			e.printStackTrace(Trace.excStream);
		}
		return date;
	}
}
