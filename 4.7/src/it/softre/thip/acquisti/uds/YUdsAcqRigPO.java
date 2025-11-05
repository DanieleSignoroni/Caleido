package it.softre.thip.acquisti.uds;

import com.thera.thermfw.persist.*;
import java.sql.*;
import java.util.*;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaSec;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.acquisti.generaleAC.CausaleOrdineRigaAcq;
import it.thera.thip.acquisti.ordineAC.OrdineAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRiga;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.articolo.ArticoloVersione;
import it.thera.thip.magazzino.generalemag.Lotto;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.vendite.ordineVE.OrdineVendita;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRiga;

import java.math.*;
import it.thera.thip.cs.*;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneProxyEnh;

import com.thera.thermfw.common.*;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura.
 */

public abstract class YUdsAcqRigPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Child, Conflictable {

	private static YUdsAcqRig cInstance;

	protected BigDecimal iAltezza;

	protected String iFornitore;

	protected Short iIdRigaAttivita;

	protected Integer iIdRigaUds;

	protected BigDecimal iLarghezza;

	protected BigDecimal iLunghezza;

	protected String iNote;

	protected BigDecimal iPesoLordo;

	protected BigDecimal iPesoNetto;

	protected BigDecimal iQtaAcq;

	protected BigDecimal iQtaPrm;

	protected BigDecimal iQtaSec;

	protected String iRAnnoDocAcq;

	protected String iRAnnoOrdAcq;

	protected String iRCauOrdAcqRig;

	protected String iRConfig;

	protected String iRNumDocAcq;

	protected String iRNumOrdAcq;

	protected Integer iRRigaDetDocAcq;

	protected Integer iRRigaDetOrdAcq;
	
	protected Integer iRRigaOrdVen;

	protected Integer iRRigaDocAcq;
	
	protected Integer iRRigaOrdAcq;
	
	protected String iRAnnoOrdVen;

	protected String iRNumeroOrdVen;
	
	protected BigDecimal iVolume;
	
	protected String iUdsPadre;

	protected char iStatoEvasione = '0';

	protected Proxy iRelarticolo = new Proxy(it.thera.thip.base.articolo.Articolo.class);

	protected Proxy iRelversione = new Proxy(it.thera.thip.base.articolo.ArticoloVersione.class);

	protected Proxy iRellotto = new Proxy(it.thera.thip.magazzino.generalemag.Lotto.class);

	protected Proxy iRelordprd = new Proxy(it.thera.thip.produzione.ordese.OrdineEsecutivo.class);

	protected Proxy iRelOrdineAcquisto = new Proxy(it.thera.thip.acquisti.ordineAC.OrdineAcquisto.class);

	protected Proxy iRelOrdineAcquistoRiga = new Proxy(it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaPrm.class);

	protected Proxy iRelOrdineAcquistoRigaSec = new Proxy(it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaSec.class);

	protected Proxy iRelDocumentoAcquisto = new Proxy(it.thera.thip.acquisti.documentoAC.DocumentoAcquisto.class);

	protected Proxy iRelDocumentoAcqRiga = new Proxy(it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm.class);

	protected Proxy iRelDocumentoAcqRigaSec = new Proxy(it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaSec.class);

	protected Proxy iRelCausaleRigaOrdAcq = new Proxy(it.thera.thip.acquisti.generaleAC.CausaleOrdineRigaAcq.class);
	
	protected Proxy iRelFornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);
	
	protected Proxy iRelOrdineVendita = new Proxy(it.thera.thip.vendite.ordineVE.OrdineVendita.class);
	
	protected Proxy iRelOrdineVenditaRiga = new Proxy(it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm.class);
	
	protected ConfigurazioneProxyEnh iRelConfigurazione = new ConfigurazioneProxyEnh(Configurazione.class);
	
	protected Proxy iParent = new Proxy(it.softre.thip.acquisti.uds.YUdsAcquisto.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YUdsAcqRig)Factory.createObject(YUdsAcqRig.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YUdsAcqRig elementWithKey(String key, int lockType) throws SQLException {
		return (YUdsAcqRig)PersistentObject.elementWithKey(YUdsAcqRig.class, key, lockType);
	}

	public YUdsAcqRigPO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setAltezza(BigDecimal altezza) {
		this.iAltezza = altezza;
		setDirty();
	}

	public BigDecimal getAltezza() {
		return iAltezza;
	}

	public void setFornitore(String fornitore) {
		this.iFornitore = fornitore;
		setDirty();
	}

	public String getFornitore() {
		return iFornitore;
	}

	public void setIdRigaAttivita(Short idRigaAttivita) {
		this.iIdRigaAttivita = idRigaAttivita;
		setDirty();
	}

	public Short getIdRigaAttivita() {
		return iIdRigaAttivita;
	}

	public void setIdRigaUds(Integer idRigaUds) {
		this.iIdRigaUds = idRigaUds;
		setDirty();
		setOnDB(false);
	}

	public Integer getIdRigaUds() {
		return iIdRigaUds;
	}

	public void setLarghezza(BigDecimal larghezza) {
		this.iLarghezza = larghezza;
		setDirty();
	}

	public BigDecimal getLarghezza() {
		return iLarghezza;
	}

	public void setLunghezza(BigDecimal lunghezza) {
		this.iLunghezza = lunghezza;
		setDirty();
	}

	public BigDecimal getLunghezza() {
		return iLunghezza;
	}

	public void setNote(String note) {
		this.iNote = note;
		setDirty();
	}

	public String getNote() {
		return iNote;
	}

	public void setPesoLordo(BigDecimal pesoLordo) {
		this.iPesoLordo = pesoLordo;
		setDirty();
	}
	
	public BigDecimal getPesoLordo() {
		return iPesoLordo;
	}


	public void setPesoNetto(BigDecimal pesoNetto) {
		this.iPesoNetto = pesoNetto;
		setDirty();
	}

	public BigDecimal getPesoNetto() {
		return iPesoNetto;
	}

	public void setQtaAcq(BigDecimal qtaAcq) {
		this.iQtaAcq = qtaAcq;
		setDirty();
	}

	public BigDecimal getQtaAcq() {
		return iQtaAcq;
	}

	public void setQtaPrm(BigDecimal qtaPrm) {
		this.iQtaPrm = qtaPrm;
		setDirty();
	}

	public BigDecimal getQtaPrm() {
		return iQtaPrm;
	}

	public void setQtaSec(BigDecimal qtaSec) {
		this.iQtaSec = qtaSec;
		setDirty();
	}

	public BigDecimal getQtaSec() {
		return iQtaSec;
	}

	public void setRAnnoDocAcq(String rAnnoDocAcq) {
		this.iRAnnoDocAcq = rAnnoDocAcq;
		setDirty();
	}

	public String getRAnnoDocAcq() {
		return iRAnnoDocAcq;
	}

	public void setRAnnoOrdAcq(String rAnnoOrdAcq) {
		this.iRAnnoOrdAcq = rAnnoOrdAcq;
		setDirty();
	}

	public String getRAnnoOrdAcq() {
		return iRAnnoOrdAcq;
	}

	public void setRCauOrdAcqRig(String rCauOrdAcqRig) {
		this.iRCauOrdAcqRig = rCauOrdAcqRig;
		setDirty();
	}

	public String getRCauOrdAcqRig() {
		return iRCauOrdAcqRig;
	}

	public void setRConfig(String rConfig) {
		this.iRelConfigurazione.setIdEsternoConfig(rConfig);
		setDirty();
	}

	public String getRConfig() {
		return iRelConfigurazione.getIdEsternoConfig();
	}

	public void setRNumDocAcq(String rNumDocAcq) {
		this.iRNumDocAcq = rNumDocAcq;
		setDirty();
	}

	public String getRNumDocAcq() {
		return iRNumDocAcq;
	}

	public void setRNumOrdAcq(String rNumOrdAcq) {
		this.iRNumOrdAcq = rNumOrdAcq;
		setDirty();
	}

	public String getRNumOrdAcq() {
		return iRNumOrdAcq;
	}

	public void setRRigaDetDocAcq(Integer rRigaDetDocAcq) {
		this.iRRigaDetDocAcq = rRigaDetDocAcq;
		setDirty();
	}
	
	public Integer getRRigaOrdVen() {
		return iRRigaOrdVen;
	}

	public void setRRigaOrdVen(Integer iRRigaOrdVen) {
		this.iRRigaOrdVen = iRRigaOrdVen;
	}

	public Integer getRRigaDetDocAcq() {
		return iRRigaDetDocAcq;
	}

	public void setRRigaDetOrdAcq(Integer rRigaDetOrdAcq) {
		this.iRRigaDetOrdAcq = rRigaDetOrdAcq;
		setDirty();
	}

	public Integer getRRigaDetOrdAcq() {
		return iRRigaDetOrdAcq;
	}

	public void setRRigaDocAcq(Integer rRigaDocAcq) {
		this.iRRigaDocAcq = rRigaDocAcq;
		setDirty();
	}

	public Integer getRRigaDocAcq() {
		return iRRigaDocAcq;
	}

	public void setRRigaOrdAcq(Integer rRigaOrdAcq) {
		this.iRRigaOrdAcq = rRigaOrdAcq;
		setDirty();
	}

	public Integer getRRigaOrdAcq() {
		return iRRigaOrdAcq;
	}
	
	public void setVolume(BigDecimal volume) {
		this.iVolume = volume;
		setDirty();
	}

	public BigDecimal getVolume() {
		return iVolume;
	}

	public void setRelarticolo(Articolo relarticolo) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relarticolo != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String rArticolo = getRArticolo();
		if (relarticolo != null) {
			rArticolo = KeyHelper.getTokenObjectKey(relarticolo.getKey(), 2);
		}
		setRArticoloInternal(rArticolo);
		this.iRelarticolo.setObject(relarticolo);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Articolo getRelarticolo() {
		return (Articolo)iRelarticolo.getObject();
	}

	public void setRelarticoloKey(String key) {
		String oldObjectKey = getKey();
		iRelarticolo.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String rArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setRArticoloInternal(rArticolo);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelarticoloKey() {
		return iRelarticolo.getKey();
	}

	public void setRelversione(ArticoloVersione relversione) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relversione != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relversione.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String rArticolo = getRArticolo();
		if (relversione != null) {
			rArticolo = KeyHelper.getTokenObjectKey(relversione.getKey(), 2);
		}
		setRArticoloInternal(rArticolo);
		this.iRelversione.setObject(relversione);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public ArticoloVersione getRelversione() {
		return (ArticoloVersione)iRelversione.getObject();
	}
	
	public void setRelversioneKey(String key) {
		String oldObjectKey = getKey();
		iRelversione.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String rArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setRArticoloInternal(rArticolo);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelversioneKey() {
		return iRelversione.getKey();
	}

	public void setRVersione(Integer rVersione) {
		String key = iRelversione.getKey();
		iRelversione.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rVersione));
		setDirty();
	}

	public Integer getRVersione() {
		String key = iRelversione.getKey();
		String objRVersione = KeyHelper.getTokenObjectKey(key,3);
		return KeyHelper.stringToIntegerObj(objRVersione);
	}

	public void setRellotto(Lotto rellotto) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (rellotto != null) {
			idAzienda = KeyHelper.getTokenObjectKey(rellotto.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String rArticolo = getRArticolo();
		if (rellotto != null) {
			rArticolo = KeyHelper.getTokenObjectKey(rellotto.getKey(), 2);
		}
		setRArticoloInternal(rArticolo);
		this.iRellotto.setObject(rellotto);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Lotto getRellotto() {
		return (Lotto)iRellotto.getObject();
	}

	public void setRellottoKey(String key) {
		String oldObjectKey = getKey();
		iRellotto.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String rArticolo = KeyHelper.getTokenObjectKey(key, 2);
		setRArticoloInternal(rArticolo);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}
	
	public String getRellottoKey() {
		return iRellotto.getKey();
	}

	public void setRArticolo(String rArticolo) {
		setRArticoloInternal(rArticolo);
		setDirty();
	}

	public String getRArticolo() {
		String key = iRelarticolo.getKey();
		String objRArticolo = KeyHelper.getTokenObjectKey(key,2);
		return objRArticolo;
	}

	public void setRLotto(String rLotto) {
		String key = iRellotto.getKey();
		iRellotto.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rLotto));
		setDirty();
	}

	public String getRLotto() {
		String key = iRellotto.getKey();
		String objRLotto = KeyHelper.getTokenObjectKey(key,3);
		return objRLotto;
	}

	public void setRelordprd(OrdineEsecutivo relordprd) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relordprd != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relordprd.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelordprd.setObject(relordprd);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineEsecutivo getRelordprd() {
		return (OrdineEsecutivo)iRelordprd.getObject();
	}

	public void setRelordprdKey(String key) {
		String oldObjectKey = getKey();
		iRelordprd.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelordprdKey() {
		return iRelordprd.getKey();
	}

	public void setRAnnoOrdPrd(String rAnnoOrdPrd) {
		String key = iRelordprd.getKey();
		iRelordprd.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rAnnoOrdPrd));
		setDirty();
	}

	public String getRAnnoOrdPrd() {
		String key = iRelordprd.getKey();
		String objRAnnoOrdPrd = KeyHelper.getTokenObjectKey(key,2);
		return objRAnnoOrdPrd;

	}

	public void setRNumOrdPrd(String rNumOrdPrd) {
		String key = iRelordprd.getKey();
		iRelordprd.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rNumOrdPrd));
		setDirty();
	}

	public String getRNumOrdPrd() {
		String key = iRelordprd.getKey();
		String objRNumOrdPrd = KeyHelper.getTokenObjectKey(key,3);
		return objRNumOrdPrd;
	}

	public void setParent(YUdsAcquisto parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iParent.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public YUdsAcquisto getParent() {
		return (YUdsAcquisto)iParent.getObject();
	}

	public void setParentKey(String key) {
		iParent.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getParentKey() {
		return iParent.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setIdUds(String idUds) {
		String key = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idUds));
		setDirty();
		setOnDB(false);
	}

	public String getIdUds() {
		String key = iParent.getKey();
		String objIdUds = KeyHelper.getTokenObjectKey(key,2);
		return objIdUds;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YUdsAcqRigPO yUdsAcqRigPO = (YUdsAcqRigPO)obj;
		iRelarticolo.setEqual(yUdsAcqRigPO.iRelarticolo);
		iRelversione.setEqual(yUdsAcqRigPO.iRelversione);
		iRellotto.setEqual(yUdsAcqRigPO.iRellotto);
		iRelordprd.setEqual(yUdsAcqRigPO.iRelordprd);
		iParent.setEqual(yUdsAcqRigPO.iParent);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdUds(KeyHelper.getTokenObjectKey(key, 2));
		setIdRigaUds(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 3)));
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idUds = getIdUds();
		Integer idRigaUds = getIdRigaUds();
		Object[] keyParts = {idAzienda, idUds, idRigaUds};
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String getFatherKey() {
		return getParentKey();
	}

	public void setFatherKey(String key) {
		setParentKey(key);
	}

	public void setFather(PersistentObject father) {
		iParent.setObject(father);
	}

	public String getOrderByClause() {
		return "";
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YUdsAcqRigTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iRelarticolo.getKey();
		iRelarticolo.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iRelversione.getKey();
		iRelversione.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iRellotto.getKey();
		iRellotto.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iRelordprd.getKey();
		iRelordprd.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
		String key6 = iParent.getKey();
		iParent.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
	}

	protected void setRArticoloInternal(String rArticolo) {
		String key1 = iRelarticolo.getKey();
		iRelarticolo.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, rArticolo));
		String key2 = iRelversione.getKey();
		iRelversione.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, rArticolo));
		String key3 = iRellotto.getKey();
		iRellotto.setKey(KeyHelper.replaceTokenObjectKey(key3, 2, rArticolo));
	}

	public void setOrdineAcquisto(OrdineAcquisto ordineAcquisto) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (ordineAcquisto != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ordineAcquisto.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelOrdineAcquisto.setObject(ordineAcquisto);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineAcquisto getOrdineAcquisto() {
		return (OrdineAcquisto)iRelOrdineAcquisto.getObject();
	}

	public void setOrdineAcquistoKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineAcquisto.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineAcquistoKey() {
		return iRelOrdineAcquisto.getKey();
	}

	public void setOrdineAcquistoRiga(OrdineAcquistoRiga ordineAcquistoRiga) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (ordineAcquistoRiga != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ordineAcquistoRiga.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelOrdineAcquistoRiga.setObject(ordineAcquistoRiga);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineAcquistoRiga getOrdineAcquistoRiga() {
		return (OrdineAcquistoRiga)iRelOrdineAcquistoRiga.getObject();
	}

	public void setOrdineAcquistoRigaKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineAcquistoRiga.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineAcquistoRigaKey() {
		return iRelOrdineAcquistoRiga.getKey();
	}

	public void setOrdineAcquistoRigaSec(OrdineAcquistoRiga ordineAcquistoRigaSec) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (ordineAcquistoRigaSec != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ordineAcquistoRigaSec.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelOrdineAcquistoRigaSec.setObject(ordineAcquistoRigaSec);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineAcquistoRiga getOrdineAcquistoRigaSec() {
		return (OrdineAcquistoRiga) iRelOrdineAcquistoRigaSec.getObject();
	}

	public void setOrdineAcquistoRigaSecKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineAcquistoRigaSec.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineAcquistoRigaSecKey() {
		return iRelOrdineAcquistoRigaSec.getKey();
	}

	public DocumentoAcquisto getDocumentoAcquisto(){
		return (DocumentoAcquisto)iRelDocumentoAcquisto.getObject();
	}

	public void setDocumentoAcquisto(DocumentoAcquisto documentoAcquisto){
		iRelDocumentoAcquisto.setObject(documentoAcquisto);
	}

	public String getDocumentoAcquistoKey(){
		return iRelDocumentoAcquisto.getKey();
	}

	public void setDocumentoAcquistoKey(String key){
		iRelDocumentoAcquisto.setKey(key);
		setDirty();
	}

	public DocumentoAcqRigaPrm getDocumentoAcquistoRiga() {
		return (DocumentoAcqRigaPrm) iRelDocumentoAcqRiga.getObject();
	}

	public void setDocumentoAcquistoRiga(DocumentoAcqRigaPrm documentoAcquistoRiga) {
		iRelDocumentoAcqRiga.setObject(documentoAcquistoRiga);
	}

	public String getDocumentoAcquistoRigaKey() {
		return iRelDocumentoAcqRiga.getKey();
	}

	public void setDocumentoAcquistoRigaKey(String key) {
		iRelDocumentoAcqRiga.setKey(key);
		setDirty();
	}
	
	public DocumentoAcqRigaSec getDocumentoAcquistoRigaSec() {
		return (DocumentoAcqRigaSec) iRelDocumentoAcqRigaSec.getObject();
	}

	public void setDocumentoAcquistoRigaSec(DocumentoAcqRigaSec documentoAcquistoRiga) {
		iRelDocumentoAcqRigaSec.setObject(documentoAcquistoRiga);
	}

	public String getDocumentoAcquistoRigaSecKey() {
		return iRelDocumentoAcqRigaSec.getKey();
	}

	public void setDocumentoAcquistoRigaSecKey(String key) {
		iRelDocumentoAcqRigaSec.setKey(key);
		setDirty();
	}

	public CausaleOrdineRigaAcq getCausaleRiga()
	{
		return (CausaleOrdineRigaAcq) iRelCausaleRigaOrdAcq.getObject();
	}

	public void setCausaleRiga(CausaleOrdineRigaAcq iCausaleRiga)
	{
		this.iRelCausaleRigaOrdAcq.setObject(iCausaleRiga);
		setDirty();
	}

	public Proxy getiRelFornitore() {
		return iRelFornitore;
	}

	public void setiRelFornitore(Proxy iRelFornitore) {
		this.iRelFornitore = iRelFornitore;
	}
	
	public void setStatoEvasione(char statoEvasione) {
		this.iStatoEvasione = statoEvasione;
		setDirty();
	}

	public char getStatoEvasione() {
		return iStatoEvasione;
	}
	
	public void setUdsPadre(String udsPadre) {
		this.iUdsPadre = udsPadre;
		setDirty();
	}

	public String getUdsPadre() {
		return iUdsPadre;
	}

	public Configurazione getConfigurazione() {
		return (Configurazione)iRelConfigurazione.getObject();
	}

	public void setConfigurazione(Configurazione iRelConfigurazione) {
		this.iRelConfigurazione.setObject(iRelConfigurazione);
		setDirty();
	}

	public String getConfigurazioneKey() {
		return iRelConfigurazione.getKey();
	}


	public void setConfigurazioneKey(String key) {
		iRelConfigurazione.setKey(key);
		setDirty();
	}

	public Integer getIdConfigurazione() {
		String key = iRelConfigurazione.getKey();
		Integer rConfigurazione =
				KeyHelper.stringToIntegerObj(
						KeyHelper.getTokenObjectKey(key,2)
						);
		return rConfigurazione;
	}

	public void setIdConfigurazione(Integer rConfigurazione) {
		String key = iRelConfigurazione.getKey();
		iRelConfigurazione.setKey(
				KeyHelper.replaceTokenObjectKey(key , 2, rConfigurazione)
				);
		setDirty();
	}
	
	public String getRAnnoOrdVen() {
		return iRAnnoOrdVen;
	}

	public void setRAnnoOrdVen(String iRAnnoOrdVen) {
		this.iRAnnoOrdVen = iRAnnoOrdVen;
	}

	public String getRNumeroOrdVen() {
		return iRNumeroOrdVen;
	}

	public void setRNumeroOrdVen(String iRNumeroOrdVen) {
		this.iRNumeroOrdVen = iRNumeroOrdVen;
	}
	
	public void setOrdineVendita(OrdineVendita ordineVendita) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (ordineVendita != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ordineVendita.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelOrdineVendita.setObject(ordineVendita);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineVendita getOrdineVendita() {
		return (OrdineVendita)iRelOrdineVendita.getObject();
	}

	public void setOrdineVenditaKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineVendita.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineVenditaKey() {
		return iRelOrdineVendita.getKey();
	}
	
	public void setOrdineVenditaRiga(OrdineVenditaRiga ordineVenditaRiga) {
		String oldObjectKey = getKey();
		String idAzienda = null;
		if (ordineVenditaRiga != null) {
			idAzienda = KeyHelper.getTokenObjectKey(ordineVenditaRiga.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelOrdineVenditaRiga.setObject(ordineVenditaRiga);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public OrdineVenditaRiga getOrdineVenditaRiga() {
		return (OrdineVenditaRiga)iRelOrdineVenditaRiga.getObject();
	}

	public void setOrdineVenditaRigaKey(String key) {
		String oldObjectKey = getKey();
		iRelOrdineVenditaRiga.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getOrdineVenditaRigaKey() {
		return iRelOrdineVenditaRiga.getKey();
	}

}

