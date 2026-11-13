package it.caleido.thip.base.connettori.utils;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.Child;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.persist.TableManager;
import com.thera.thermfw.security.Authorizable;
import com.thera.thermfw.security.Conflictable;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.partner.NazionePrimrose;
import it.thera.thip.cs.EntitaAzienda;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 13/11/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    13/11/2026  DSSOF3   Prima stesura
 */

public abstract class YPsnDatiEcommerceSeriePO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable, Child {

	private static YPsnDatiEcommerceSerie cInstance;
	
	protected Integer iIdRiga = Integer.valueOf(0);
	
	protected Proxy iNazione = new Proxy(NazionePrimrose.class);
	
	protected Proxy iConfigurazione = new Proxy(YPsnDatiEcommerce.class);
	
	protected Proxy iSerie = new Proxy(Serie.class);

	public YPsnDatiEcommerceSeriePO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YPsnDatiEcommerceSerie) Factory.createObject(YPsnDatiEcommerceSerie.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YPsnDatiEcommerceSerie elementWithKey(String key, int lockType) throws SQLException {
		return (YPsnDatiEcommerceSerie) PersistentObject.elementWithKey(YPsnDatiEcommerceSerie.class, key, lockType);
	}

	public Integer getIdRiga() {
		return iIdRiga;
	}

	public void setIdRiga(Integer idRiga) {
		if (iIdRiga == null ? idRiga != null : !iIdRiga.equals(idRiga)) {
			iIdRiga = idRiga;
			setDirty();
			setOnDB(false);
		}
	}

	public String getIdAzienda() {
		return iAzienda.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		String oldKey = getIdAzienda();
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldKey, idAzienda))
			setOnDB(false);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		if (iConfigurazione != null)
			iConfigurazione.setKey(idAzienda);
		if (iSerie != null)
			iSerie.setKey(KeyHelper.replaceTokenObjectKey(iSerie.getKey(), 1, idAzienda));
	}

	public String getFatherKey() {
		return getIdAzienda();
	}

	public void setFatherKey(String key) {
		setIdAziendaInternal(key);
		setDirty();
		setOnDB(false);
	}

	public void setFather(PersistentObject father) {
		iConfigurazione.setObject(father);
		if (father != null)
			setIdAziendaInternal(father.getKey());
	}

	public String getOrderByClause() {
		return YPsnDatiEcommerceSerieTM.ID_RIGA;
	}

	public void setNazione(NazionePrimrose nazione) {
		iNazione.setObject(nazione);
		setDirty();
	}

	public NazionePrimrose getNazione() {
		return (NazionePrimrose) iNazione.getObject();
	}

	public void setNazioneKey(String key) {
		setRNazione(key);
	}

	public String getNazioneKey() {
		return getRNazione();
	}

	public void setRNazione(String nazione) {
		iNazione.setKey(nazione == null || nazione.trim().length() == 0 ? null : nazione.trim());
		setDirty();
	}

	public String getRNazione() {
		String key = iNazione.getKey();
		return key == null || key.trim().length() == 0 ? null : key.trim();
	}

	public void setSerie(Serie value) {
		if (value != null)
			setIdAzienda(KeyHelper.getTokenObjectKey(value.getKey(), 1));
		iSerie.setObject(value);
		if (value == null)
			setIdAziendaInternal(getIdAzienda());
		setDirty();
	}

	public Serie getSerie() {
		return (Serie) iSerie.getObject();
	}

	public void setSerieKey(String key) {
		if (key != null)
			setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		iSerie.setKey(key);
		if (key == null)
			setIdAziendaInternal(getIdAzienda());
		setDirty();
	}

	public String getSerieKey() {
		return iSerie.getKey();
	}

	public void setRNumeratore(String value) {
		iSerie.setKey(KeyHelper.replaceTokenObjectKey(iSerie.getKey(), 2, value));
		setDirty();
	}

	public String getRNumeratore() {
		return KeyHelper.getTokenObjectKey(iSerie.getKey(), 2);
	}

	public void setRSerie(String value) {
		iSerie.setKey(KeyHelper.replaceTokenObjectKey(iSerie.getKey(), 3, value));
		setDirty();
	}

	public String getRSerie() {
		return KeyHelper.getTokenObjectKey(iSerie.getKey(), 3);
	}

	public void setKey(String key) {
		setIdAzienda(KeyHelper.getTokenObjectKey(key, 1));
		setIdRiga(KeyHelper.stringToIntegerObj(KeyHelper.getTokenObjectKey(key, 2)));
	}

	public String getKey() {
		return KeyHelper.buildObjectKey(new Object[] { getIdAzienda(), getIdRiga() });
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YPsnDatiEcommerceSeriePO other = (YPsnDatiEcommerceSeriePO) obj;
		iIdRiga = other.iIdRiga;
		iNazione.setEqual(other.iNazione);
		iConfigurazione.setEqual(other.iConfigurazione);
		iSerie.setEqual(other.iSerie);

	}

	@SuppressWarnings({ "rawtypes" })
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	protected TableManager getTableManager() throws SQLException {
		return YPsnDatiEcommerceSerieTM.getInstance();
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}
}
