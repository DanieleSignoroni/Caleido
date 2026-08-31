package it.caleido.thip.base.connettori.utils;

import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObject;
import com.thera.thermfw.common.Deletable;
import com.thera.thermfw.persist.Cacheable;
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
import it.thera.thip.base.cliente.AssoggettamentoIVAPrimrose;
import it.thera.thip.base.generale.Serie;
import it.thera.thip.base.partner.ValutaPrimrose;
import it.thera.thip.cs.EntitaAzienda;
import it.thera.thip.vendite.generaleVE.CausaleOffertaCliente;
import it.thera.thip.vendite.generaleVE.CausaleRigaOffertaCliente;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 31/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    31/08/2026  DSSOF3   Prima stesura
 */

public abstract class YPsnDatiEcommercePO extends EntitaAzienda implements BusinessObject, Authorizable, Deletable, Conflictable, Cacheable {

	private static YPsnDatiEcommerce cInstance;

	protected Proxy iRelserieoffven = new Proxy(it.thera.thip.base.generale.Serie.class);

	protected Proxy iRelassogiva = new Proxy(it.thera.thip.base.cliente.AssoggettamentoIVAPrimrose.class);

	protected Proxy iRelcauoffrig = new Proxy(it.thera.thip.vendite.generaleVE.CausaleRigaOffertaCliente.class);

	protected Proxy iRelcauoffrigscmerce = new Proxy(it.thera.thip.vendite.generaleVE.CausaleRigaOffertaCliente.class);

	protected Proxy iRelcauofftes = new Proxy(it.thera.thip.vendite.generaleVE.CausaleOffertaCliente.class);

	protected Proxy iRelvaluta = new Proxy(it.thera.thip.base.partner.ValutaPrimrose.class);

	@SuppressWarnings("rawtypes")
	public static Vector retrieveList(String where, String orderBy, boolean optimistic)
			throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		if (cInstance == null)
			cInstance = (YPsnDatiEcommerce) Factory.createObject(YPsnDatiEcommerce.class);
		return PersistentObject.retrieveList(cInstance, where, orderBy, optimistic);
	}

	public static YPsnDatiEcommerce elementWithKey(String key, int lockType) throws SQLException {
		return (YPsnDatiEcommerce) PersistentObject.elementWithKey(YPsnDatiEcommerce.class, key, lockType);
	}

	public YPsnDatiEcommercePO() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setRelserieoffven(Serie relserieoffven) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relserieoffven != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relserieoffven.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelserieoffven.setObject(relserieoffven);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public Serie getRelserieoffven() {
		return (Serie) iRelserieoffven.getObject();
	}

	public void setRelserieoffvenKey(String key) {
		String oldObjectKey = getKey();
		iRelserieoffven.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelserieoffvenKey() {
		return iRelserieoffven.getKey();
	}

	public void setRNumeratoreOffVen(String rNumeratoreOffVen) {
		String key = iRelserieoffven.getKey();
		iRelserieoffven.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rNumeratoreOffVen));
		setDirty();
	}

	public String getRNumeratoreOffVen() {
		String key = iRelserieoffven.getKey();
		String objRNumeratoreOffVen = KeyHelper.getTokenObjectKey(key, 2);
		return objRNumeratoreOffVen;

	}

	public void setRSerieOffVen(String rSerieOffVen) {
		String key = iRelserieoffven.getKey();
		iRelserieoffven.setKey(KeyHelper.replaceTokenObjectKey(key, 3, rSerieOffVen));
		setDirty();
	}

	public String getRSerieOffVen() {
		String key = iRelserieoffven.getKey();
		String objRSerieOffVen = KeyHelper.getTokenObjectKey(key, 3);
		return objRSerieOffVen;
	}

	public void setRelassogiva(AssoggettamentoIVAPrimrose relassogiva) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relassogiva != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relassogiva.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelassogiva.setObject(relassogiva);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public AssoggettamentoIVAPrimrose getRelassogiva() {
		return (AssoggettamentoIVAPrimrose) iRelassogiva.getObject();
	}

	public void setRelassogivaKey(String key) {
		String oldObjectKey = getKey();
		iRelassogiva.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelassogivaKey() {
		return iRelassogiva.getKey();
	}

	public void setRAssogIva(String rAssogIva) {
		String key = iRelassogiva.getKey();
		iRelassogiva.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rAssogIva));
		setDirty();
	}

	public String getRAssogIva() {
		String key = iRelassogiva.getKey();
		String objRAssogIva = KeyHelper.getTokenObjectKey(key, 2);
		return objRAssogIva;
	}

	public void setRelcauoffrig(CausaleRigaOffertaCliente relcauoffrig) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relcauoffrig != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relcauoffrig.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelcauoffrig.setObject(relcauoffrig);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public CausaleRigaOffertaCliente getRelcauoffrig() {
		return (CausaleRigaOffertaCliente) iRelcauoffrig.getObject();
	}

	public void setRelcauoffrigKey(String key) {
		String oldObjectKey = getKey();
		iRelcauoffrig.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelcauoffrigKey() {
		return iRelcauoffrig.getKey();
	}

	public void setRCauOffRig(String rCauOffRig) {
		String key = iRelcauoffrig.getKey();
		iRelcauoffrig.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rCauOffRig));
		setDirty();
	}

	public String getRCauOffRig() {
		String key = iRelcauoffrig.getKey();
		String objRCauOffRig = KeyHelper.getTokenObjectKey(key, 2);
		return objRCauOffRig;
	}

	public void setRelcauoffrigscmerce(CausaleRigaOffertaCliente relcauoffrigscmerce) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relcauoffrigscmerce != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relcauoffrigscmerce.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelcauoffrigscmerce.setObject(relcauoffrigscmerce);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public CausaleRigaOffertaCliente getRelcauoffrigscmerce() {
		return (CausaleRigaOffertaCliente) iRelcauoffrigscmerce.getObject();
	}

	public void setRelcauoffrigscmerceKey(String key) {
		String oldObjectKey = getKey();
		iRelcauoffrigscmerce.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelcauoffrigscmerceKey() {
		return iRelcauoffrigscmerce.getKey();
	}

	public void setRCauRigOffScMerce(String rCauRigOffScMerce) {
		String key = iRelcauoffrigscmerce.getKey();
		iRelcauoffrigscmerce.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rCauRigOffScMerce));
		setDirty();
	}

	public String getRCauRigOffScMerce() {
		String key = iRelcauoffrigscmerce.getKey();
		String objRCauRigOffScMerce = KeyHelper.getTokenObjectKey(key, 2);
		return objRCauRigOffScMerce;
	}

	public void setRelcauofftes(CausaleOffertaCliente relcauofftes) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (relcauofftes != null) {
			idAzienda = KeyHelper.getTokenObjectKey(relcauofftes.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iRelcauofftes.setObject(relcauofftes);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public CausaleOffertaCliente getRelcauofftes() {
		return (CausaleOffertaCliente) iRelcauofftes.getObject();
	}

	public void setRelcauofftesKey(String key) {
		String oldObjectKey = getKey();
		iRelcauofftes.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getRelcauofftesKey() {
		return iRelcauofftes.getKey();
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

	public void setRCauOffTes(String rCauOffTes) {
		String key = iRelcauofftes.getKey();
		iRelcauofftes.setKey(KeyHelper.replaceTokenObjectKey(key, 2, rCauOffTes));
		setDirty();
	}

	public String getRCauOffTes() {
		String key = iRelcauofftes.getKey();
		String objRCauOffTes = KeyHelper.getTokenObjectKey(key, 2);
		return objRCauOffTes;
	}

	public void setRelvaluta(ValutaPrimrose relvaluta) {
		this.iRelvaluta.setObject(relvaluta);
		setDirty();
	}

	public ValutaPrimrose getRelvaluta() {
		return (ValutaPrimrose) iRelvaluta.getObject();
	}

	public void setRelvalutaKey(String key) {
		iRelvaluta.setKey(key);
		setDirty();
	}

	public String getRelvalutaKey() {
		return iRelvaluta.getKey();
	}

	public void setRValuta(String rValuta) {
		iRelvaluta.setKey(rValuta);
		setDirty();
	}

	public String getRValuta() {
		String key = iRelvaluta.getKey();
		return key;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YPsnDatiEcommercePO yPsnDatiEcommercePO = (YPsnDatiEcommercePO) obj;
		iRelserieoffven.setEqual(yPsnDatiEcommercePO.iRelserieoffven);
		iRelassogiva.setEqual(yPsnDatiEcommercePO.iRelassogiva);
		iRelcauoffrig.setEqual(yPsnDatiEcommercePO.iRelcauoffrig);
		iRelcauoffrigscmerce.setEqual(yPsnDatiEcommercePO.iRelcauoffrigscmerce);
		iRelcauofftes.setEqual(yPsnDatiEcommercePO.iRelcauofftes);
		iRelvaluta.setEqual(yPsnDatiEcommercePO.iRelvaluta);
	}

	@SuppressWarnings("rawtypes")
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = new Vector();
		components.runAllChecks(errors);
		return errors;
	}

	public void setKey(String key) {
		setIdAzienda(key);
	}

	public String getKey() {
		return getIdAzienda();
	}

	public boolean isDeletable() {
		return checkDelete() == null;
	}

	public String toString() {
		return getClass().getName() + " [" + KeyHelper.formatKeyString(getKey()) + "]";
	}

	protected TableManager getTableManager() throws SQLException {
		return YPsnDatiEcommerceTM.getInstance();
	}

	protected void setIdAziendaInternal(String idAzienda) {
		iAzienda.setKey(idAzienda);
		String key2 = iRelserieoffven.getKey();
		iRelserieoffven.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		String key3 = iRelassogiva.getKey();
		iRelassogiva.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		String key4 = iRelcauoffrig.getKey();
		iRelcauoffrig.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		String key5 = iRelcauoffrigscmerce.getKey();
		iRelcauoffrigscmerce.setKey(KeyHelper.replaceTokenObjectKey(key5, 1, idAzienda));
		String key6 = iRelcauofftes.getKey();
		iRelcauofftes.setKey(KeyHelper.replaceTokenObjectKey(key6, 1, idAzienda));
	}

}