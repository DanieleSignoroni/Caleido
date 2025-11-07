package it.softre.thip.acquisti.uds;

import com.thera.thermfw.persist.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import it.thera.thip.base.fornitore.FornitoreAcquisto;
import it.thera.thip.logisticaLight.Ubicazione;
import it.softre.thip.acquisti.documentoAC.YDocumentoAcquisto;
import it.softre.thip.base.uds.YTipoUds;

import java.math.*;
import it.thera.thip.cs.*;
import it.thera.thip.datiTecnici.configuratore.SchemaCfg;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;

import com.thera.thermfw.common.*;

import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquisto;
import it.thera.thip.base.azienda.Azienda;
import com.thera.thermfw.security.*;
import com.thera.thermfw.type.DateType;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura.
 */

public abstract class YUdsAcquistoPO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable {

	private static YUdsAcquisto cInstance;

	protected BigDecimal iAltezza;

	protected String iIdUds;

	protected BigDecimal iLarghezza;

	protected BigDecimal iLunghezza;

	protected String iNote;

	protected BigDecimal iPesoContenitore;

	protected BigDecimal iPesoLordo;

	protected BigDecimal iPesoNetto;

	protected char iStatoEvasione = '0';

	protected String iRIdUdsPadre;

	protected String iRAnnoDocAcq;

	protected String iRNumDocAcq;

	protected String iRNumOrdAcq;

	protected String iRAnnoOrdAcq;

	protected BigDecimal iVolume;

	protected java.sql.Date iDataUds;

	protected char iStatoStPacking = '0';

	protected char iStatoStEti = '0';

	protected Proxy iRelfornitore = new Proxy(it.thera.thip.base.fornitore.FornitoreAcquisto.class);

	protected Proxy iRelubicazione = new Proxy(it.thera.thip.logisticaLight.Ubicazione.class);

	protected Proxy iReltipouds = new Proxy(it.softre.thip.base.uds.YTipoUds.class);

	protected Proxy iRelUdsPadre = new Proxy(it.softre.thip.acquisti.uds.YUdsAcquisto.class);

	protected Proxy iRelDocumentoAcquisto = new Proxy(it.thera.thip.acquisti.documentoAC.DocumentoAcquisto.class);

	protected Proxy iRelOrdineAcquisto = new Proxy(it.thera.thip.acquisti.ordineAC.OrdineAcquisto.class);

	protected OneToMany iRigheUDSAcquisti = new OneToMany(it.softre.thip.acquisti.uds.YUdsAcqRig.class, this, 3, false);

	protected Proxy iSchemaCfg = new Proxy(it.thera.thip.datiTecnici.configuratore.SchemaCfg.class);
	protected Proxy iValore = new Proxy(it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg.class);
	protected Proxy iVariabile = new Proxy(it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YUdsAcquisto)Factory.createObject(YUdsAcquisto.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YUdsAcquisto elementWithKey(String key, int lockType) throws SQLException {
		return (YUdsAcquisto)PersistentObject.elementWithKey(YUdsAcquisto.class, key, lockType);
	}

	public YUdsAcquistoPO() {
		setStatoEvasione('0');
		setStatoStPacking('0');
		setStatoStEti('0');
		setIdAzienda(Azienda.getAziendaCorrente());
		setIdSchemaCfg("COLORE");
		setIdVariabileCfg("COLORE");
	}

	public void setAltezza(BigDecimal altezza) {
		this.iAltezza = altezza;
		setDirty();
	}

	public BigDecimal getAltezza() {
		return iAltezza;
	}

	public void setIdUds(String idUds) {
		this.iIdUds = idUds;
		setDirty();
		setOnDB(false);
		iRigheUDSAcquisti.setFatherKeyChanged();
	}

	public String getIdUds() {
		return iIdUds;
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

	public void setPesoContenitore(BigDecimal pesoContenitore) {
		this.iPesoContenitore = pesoContenitore;
		setDirty();
	}

	public BigDecimal getPesoContenitore() {
		return iPesoContenitore;
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

	public void setStatoEvasione(char statoEvasione) {
		this.iStatoEvasione = statoEvasione;
		setDirty();
	}

	public char getStatoEvasione() {
		return iStatoEvasione;
	}

	public String getRIdUdsPadre() {
		return iRIdUdsPadre;
	}

	public void setRIdUdsPadre(String iRIdUdsPadre) {
		this.iRIdUdsPadre = iRIdUdsPadre;
	}

	public void setVolume(BigDecimal volume) {
		this.iVolume = volume;
		setDirty();
	}

	public BigDecimal getVolume() {
		return iVolume;
	}

	public void setDataUds(java.sql.Date dataUds) {
		this.iDataUds = dataUds;
		setDirty();
	}

	public java.sql.Date getDataUds() {
		return iDataUds;
	}

	public void setStatoStPacking(char statoStPacking) {
		this.iStatoStPacking = statoStPacking;
		setDirty();
	}

	public char getStatoStPacking() {
		return iStatoStPacking;
	}

	public void setStatoStEti(char statoStEti) {
		this.iStatoStEti = statoStEti;
		setDirty();
	}

	public char getStatoStEti() {
		return iStatoStEti;
	}

	public void setRelfornitore(FornitoreAcquisto relfornitore) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relfornitore != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relfornitore.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelfornitore.setObject(relfornitore);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public FornitoreAcquisto getRelfornitore() {
		return (FornitoreAcquisto)iRelfornitore.getObject();
	}

	public void setRelfornitoreKey(String key) {
		String oldObjectKey = getKey();
		iRelfornitore.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public String getRelfornitoreKey() {
		return iRelfornitore.getKey();
	}

	public void setIdFornitore(String idFornitore) {
		String key = iRelfornitore.getKey();
		iRelfornitore.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idFornitore));
		setDirty();
	}

	public String getIdFornitore() {
		String key = iRelfornitore.getKey();
		String objIdFornitore = KeyHelper.getTokenObjectKey(key,2);
		return objIdFornitore;
	}

	public void setRelubicazione(Ubicazione relubicazione) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relubicazione != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relubicazione.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelubicazione.setObject(relubicazione);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public Ubicazione getRelubicazione() {
		return (Ubicazione)iRelubicazione.getObject();
	}

	public void setRelubicazioneKey(String key) {
		String oldObjectKey = getKey();
		iRelubicazione.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public String getRelubicazioneKey() {
		return iRelubicazione.getKey();
	}

	public void setRMagazzino(String rMagazzino) {
		String key = iRelubicazione.getKey();
		iRelubicazione.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rMagazzino));
		setDirty();
	}

	public String getRMagazzino() {
		String key = iRelubicazione.getKey();
		String objRMagazzino = KeyHelper.getTokenObjectKey(key,2);
		return objRMagazzino;

	}

	public void setRUbicazione(String rUbicazione) {
		String key = iRelubicazione.getKey();
		iRelubicazione.setKey(KeyHelper.replaceTokenObjectKey(key , 3, rUbicazione));
		setDirty();
	}

	public String getRUbicazione() {
		String key = iRelubicazione.getKey();
		String objRUbicazione = KeyHelper.getTokenObjectKey(key,3);
		return objRUbicazione;
	}

	public void setReltipouds(YTipoUds reltipouds) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (reltipouds != null) {
			idAzienda = KeyHelper.getTokenObjectKey(reltipouds.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iReltipouds.setObject(reltipouds);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public YTipoUds getReltipouds() {
		return (YTipoUds)iReltipouds.getObject();
	}

	public void setReltipoudsKey(String key) {
		String oldObjectKey = getKey();
		iReltipouds.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
			iRigheUDSAcquisti.setFatherKeyChanged();
		}
	}

	public String getReltipoudsKey() {
		return iReltipouds.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
		iRigheUDSAcquisti.setFatherKeyChanged();
	}

	public String getIdAzienda() {
		String key = iAzienda.getKey();
		return key;
	}

	public void setIdTipoContenitore(String idTipoContenitore) {
		String key = iReltipouds.getKey();
		iReltipouds.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idTipoContenitore));
		setDirty();
	}

	public String getIdTipoContenitore() {
		String key = iReltipouds.getKey();
		String objIdTipoContenitore = KeyHelper.getTokenObjectKey(key,2);
		return objIdTipoContenitore;
	}

	@SuppressWarnings("rawtypes")
	public List getRigheUDSAcquisti() {
		return getRigheUDSAcquistiInternal();
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YUdsAcquistoPO yUdsAcquistoPO = (YUdsAcquistoPO)obj;
		if (yUdsAcquistoPO.iDataUds != null)
			iDataUds = (java.sql.Date)yUdsAcquistoPO.iDataUds.clone();
		iRelfornitore.setEqual(yUdsAcquistoPO.iRelfornitore);
		iRelubicazione.setEqual(yUdsAcquistoPO.iRelubicazione);
		iReltipouds.setEqual(yUdsAcquistoPO.iReltipouds);
		iRigheUDSAcquisti.setEqual(yUdsAcquistoPO.iRigheUDSAcquisti);
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
	}

	public String getKey() {
		String idAzienda = getIdAzienda();
		String idUds = getIdUds();
		Object[] keyParts = {idAzienda, idUds};
		return KeyHelper.buildObjectKey(keyParts);
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public int saveOwnedObjects(int rc) throws SQLException {
		rc = iRigheUDSAcquisti.save(rc);
		return rc;
	}

	public int deleteOwnedObjects() throws SQLException {
		return getRigheUDSAcquistiInternal().delete();
	}

	public boolean initializeOwnedObjects(boolean result) {
		result = iRigheUDSAcquisti.initialize(result);
		return result;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YUdsAcquistoTM.getInstance();
	}

	protected OneToMany getRigheUDSAcquistiInternal() {
		if (iRigheUDSAcquisti.isNew())
			iRigheUDSAcquisti.retrieve();
		return iRigheUDSAcquisti;
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iRelfornitore.getKey();
		iRelfornitore.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key4 = iRelubicazione.getKey();
		iRelubicazione.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iReltipouds.getKey();
		iReltipouds.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
		String key6 = iSchemaCfg.getKey();
		iSchemaCfg.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
		String key7 = iVariabile.getKey();
		iVariabile.setKey(KeyHelper.replaceTokenObjectKey(key7, 1, idAzienda));
		String key8 = iValore.getKey();
		iValore.setKey(KeyHelper.replaceTokenObjectKey(key8, 1, idAzienda));
	}

	public void setRelUdsPadre(YUdsAcquisto parent) {
		String idAzienda = getIdAzienda();
		if (parent != null) {
			idAzienda = KeyHelper.getTokenObjectKey(parent.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelUdsPadre.setObject(parent);
		setDirty();
		setOnDB(false);
	}

	public YUdsAcquisto getRelUdsPadre() {
		return (YUdsAcquisto)iRelUdsPadre.getObject();
	}

	public void setRelUdsPadreKey(String key) {
		iRelUdsPadre.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		setOnDB(false);
	}

	public String getRelUdsPadreKey() {
		return iRelUdsPadre.getKey();
	}

	public void setRAnnoDocAcq(String rAnnoDocAcq) {
		this.iRAnnoDocAcq = rAnnoDocAcq;
		setDirty();
	}

	public String getRAnnoDocAcq() {
		return iRAnnoDocAcq;
	}

	public void setRNumDocAcq(String rNumDocAcq) {
		this.iRNumDocAcq = rNumDocAcq;
		setDirty();
	}

	public String getRNumDocAcq() {
		return iRNumDocAcq;
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

	public YDocumentoAcquisto getDocumentoAcquistoObj() throws SQLException {
		YDocumentoAcquisto doc = null;
		String key = Azienda.getAziendaCorrente() + KEY_SEPARATOR + this.getRAnnoDocAcq() + KEY_SEPARATOR + this.getRNumDocAcq();
		doc = (YDocumentoAcquisto) YDocumentoAcquisto.elementWithKey(YDocumentoAcquisto.class, key, NO_LOCK);
		return doc;
	}

	public String getRNumOrdAcq() {
		String key = iRelOrdineAcquisto.getKey();
	    String idNumeroOrd = KeyHelper.getTokenObjectKey(key,3);
	  	return idNumeroOrd;
	}

	public void setRNumOrdAcq(String iRNumOrdAcq) {
		String key = iRelOrdineAcquisto.getKey();
		iRelOrdineAcquisto.setKey(KeyHelper.replaceTokenObjectKey(key , 3, iRNumOrdAcq));
	    setDirty();
	}

	public String getRAnnoOrdAcq() {
		String key = iRelOrdineAcquisto.getKey();
	    String idAnnoOrd = KeyHelper.getTokenObjectKey(key,2);
	  	return idAnnoOrd;
	}

	public void setRAnnoOrdAcq(String iRAnnoOrdAcq) {
		String key = iRelOrdineAcquisto.getKey();
		iRelOrdineAcquisto.setKey(KeyHelper.replaceTokenObjectKey(key , 2, iRAnnoOrdAcq));
	    setDirty();
	}

	public OrdineAcquisto getOrdineAcquisto(){
		return (OrdineAcquisto)iRelOrdineAcquisto.getObject();
	}

	public void setOrdineAcquisto(OrdineAcquisto OrdineAcquisto){
		iRelOrdineAcquisto.setObject(OrdineAcquisto);
		setDirty();
	}

	public String getOrdineAcquistoKey(){
		return iRelOrdineAcquisto.getKey();
	}

	public void setOrdineAcquistoKey(String key){
		iRelOrdineAcquisto.setKey(key);
		setDirty();
	}

	public void setSchemaCfg(SchemaCfg schemaCfg) {
		String idAzienda = null;
		if (schemaCfg != null) {
			idAzienda = KeyHelper.getTokenObjectKey(schemaCfg.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = null;
		if (schemaCfg != null) {
			idSchemaCfg = KeyHelper.getTokenObjectKey(schemaCfg.getKey(), 2);
		}
		setIdSchemaCfgInternal(idSchemaCfg);
		this.iSchemaCfg.setObject(schemaCfg);
		setDirty();
	}

	public SchemaCfg getSchemaCfg() {
		return (SchemaCfg)iSchemaCfg.getObject();
	}

	public void setSchemaCfgKey(String key) {
		iSchemaCfg.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = KeyHelper.getTokenObjectKey(key, 2);
		setIdSchemaCfgInternal(idSchemaCfg);
		setDirty();
	}

	public String getSchemaCfgKey() {
		return iSchemaCfg.getKey();
	}

	public void setIdSchemaCfg(String idSchemaCfg) {
		setIdSchemaCfgInternal(idSchemaCfg);
		setDirty();
	}

	public String getIdSchemaCfg() {
		String key = iSchemaCfg.getKey();
		String objIdSchemaCfg = KeyHelper.getTokenObjectKey(key,2);
		return objIdSchemaCfg;
	}

	public void setValore(ValoreVariabileCfg valore) {
		String idAzienda = null;
		if (valore != null) {
			idAzienda = KeyHelper.getTokenObjectKey(valore.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = null;
		if (valore != null) {
			idSchemaCfg = KeyHelper.getTokenObjectKey(valore.getKey(), 2);
		}
		setIdSchemaCfgInternal(idSchemaCfg);
		String idVariabileCfg = null;
		if (valore != null) {
			idVariabileCfg = KeyHelper.getTokenObjectKey(valore.getKey(), 3);
		}
		setIdVariabileCfgInternal(idVariabileCfg);
		this.iValore.setObject(valore);
		setDirty();
	}

	public ValoreVariabileCfg getValore() {
		return (ValoreVariabileCfg)iValore.getObject();
	}

	public void setValoreKey(String key) {
		iValore.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = KeyHelper.getTokenObjectKey(key, 2);
		setIdSchemaCfgInternal(idSchemaCfg);
		String idVariabileCfg = KeyHelper.getTokenObjectKey(key, 3);
		setIdVariabileCfgInternal(idVariabileCfg);
		setDirty();
	}

	public String getValoreKey() {
		return iValore.getKey();
	}

	public void setSeqValore(Integer seqValore) {
		String key = iValore.getKey();
		Integer seqValoreTmp = new Integer(seqValore != null ? seqValore : 0);
		iValore.setKey(KeyHelper.replaceTokenObjectKey(key , 4, seqValoreTmp));
		setDirty();
	}

	public Integer getSeqValore() {
		String key = iValore.getKey();
		String objSeqValore = KeyHelper.getTokenObjectKey(key,4);
		return KeyHelper.stringToInt(objSeqValore);
	}

	public void setVariabile(VariabileSchemaCfg variabile) {
		String idAzienda = null;
		if (variabile != null) {
			idAzienda = KeyHelper.getTokenObjectKey(variabile.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = null;
		if (variabile != null) {
			idSchemaCfg = KeyHelper.getTokenObjectKey(variabile.getKey(), 2);
		}
		setIdSchemaCfgInternal(idSchemaCfg);
		String idVariabileCfg = null;
		if (variabile != null) {
			idVariabileCfg = KeyHelper.getTokenObjectKey(variabile.getKey(), 3);
		}
		setIdVariabileCfgInternal(idVariabileCfg);
		this.iVariabile.setObject(variabile);
		setDirty();
	}

	public VariabileSchemaCfg getVariabile() {
		return (VariabileSchemaCfg)iVariabile.getObject();
	}

	public void setVariabileKey(String key) {
		iVariabile.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		String idSchemaCfg = KeyHelper.getTokenObjectKey(key, 2);
		setIdSchemaCfgInternal(idSchemaCfg);
		String idVariabileCfg = KeyHelper.getTokenObjectKey(key, 3);
		setIdVariabileCfgInternal(idVariabileCfg);
		setDirty();
	}

	public String getVariabileKey() {
		return iVariabile.getKey();
	}

	public void setIdVariabileCfg(String idVariabileCfg) {
		setIdVariabileCfgInternal(idVariabileCfg);
		setDirty();
	}

	public String getIdVariabileCfg() {
		String key = iVariabile.getKey();
		String objIdVariabileCfg = KeyHelper.getTokenObjectKey(key,3);
		return objIdVariabileCfg;
	}

	protected void setIdSchemaCfgInternal(String idSchemaCfg) {
		String key1 = iSchemaCfg.getKey();
		iSchemaCfg.setKey(KeyHelper.replaceTokenObjectKey(key1, 2, idSchemaCfg));
		String key2 = iVariabile.getKey();
		iVariabile.setKey(KeyHelper.replaceTokenObjectKey(key2, 2, idSchemaCfg));
		String key3 = iValore.getKey();
		iValore.setKey(KeyHelper.replaceTokenObjectKey(key3, 2, idSchemaCfg));
	}

	protected void setIdVariabileCfgInternal(String idVariabileCfg) {
		String key1 = iVariabile.getKey();
		iVariabile.setKey(KeyHelper.replaceTokenObjectKey(key1, 3, idVariabileCfg));
		String key2 = iValore.getKey();
		iValore.setKey(KeyHelper.replaceTokenObjectKey(key2, 3, idVariabileCfg));
	}

	@Override
	public String getAltreInfoHeader() {
		String altreInfo = "";
		if(isOnDB()) {
			Date dataDocumento = getDataUds();
			String data = dataDocumento.toString();

			if(dataDocumento != null) {
				DateType type = Factory.newObject(DateType.class);
				data = type.objectToString(dataDocumento);
				data = type.format(data);
			}
			String ragSoc = "";
			if(getRelfornitore() != null) {
				ragSoc = getRelfornitore().getRagioneSociale();
			}
			altreInfo = ragSoc + " (" + data + ")";
			if(getRelUdsPadre() != null) {
				altreInfo += " - Pallet " + getRIdUdsPadre();
			}
		}
		return altreInfo;
	}

}