package it.caleido.thip.base.articolo;

import java.sql.SQLException;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.*;
import it.caleido.thip.datiTecnici.configuratore.YModelloTermostato;
import it.thera.thip.base.articolo.*;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;

/**
 * <p>
 * Company: Softre Solutions<br>
 * Author: Giovanni Lumini<br>
 * Date: 28/01/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72321    28/01/2026  GLSOF3   Prima stesura
 * 72356    23/02/2026  GLSOF3   Set a 2 dei campi OrizzontaleOVerticale e DestroSinistro
 * 72588    24/07/2026  GLSOF3   Correzione package YModelloTermostato
 */
public class YArticoloDatiTecnici extends ArticoloDatiTecnici {

	protected Integer iPotenzaWattIdraulici;

	protected Integer iPotenzaWattElettrici;

	protected char iOrizzontaleOVerticale = '2';

	protected char iDestroOSinistro = '2';

	protected Short iNumeroElementi;

	protected Proxy iModello = new Proxy(it.caleido.thip.base.articolo.YModello.class);

	protected Proxy iModellotermostato = new Proxy(it.caleido.thip.datiTecnici.configuratore.YModelloTermostato.class);

	protected Proxy iFinitura = new Proxy(it.caleido.thip.base.articolo.YFinitura.class);

	protected Proxy iPotenzaWatt = new Proxy(it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg.class);

	public YArticoloDatiTecnici() {
		setOrizzontaleOVerticale('2');
		setDestroOSinistro('2');
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setPotenzaWattIdraulici(Integer potenzaWattIdraulici) {
		this.iPotenzaWattIdraulici = potenzaWattIdraulici;
		setDirty();
	}

	public Integer getPotenzaWattIdraulici() {
		return iPotenzaWattIdraulici;
	}

	public void setPotenzaWattElettrici(Integer potenzaWattElettrici) {
		this.iPotenzaWattElettrici = potenzaWattElettrici;
		setDirty();
	}

	public Integer getPotenzaWattElettrici() {
		return iPotenzaWattElettrici;
	}

	public void setOrizzontaleOVerticale(char orizzontaleOVerticale) {
		this.iOrizzontaleOVerticale = orizzontaleOVerticale;
		setDirty();
	}

	public char getOrizzontaleOVerticale() {
		return iOrizzontaleOVerticale;
	}

	public void setDestroOSinistro(char destroOSinistro) {
		this.iDestroOSinistro = destroOSinistro;
		setDirty();
	}

	public char getDestroOSinistro() {
		return iDestroOSinistro;
	}

	public void setNumeroElementi(Short numeroElementi) {
		this.iNumeroElementi = numeroElementi;
		setDirty();
	}

	public Short getNumeroElementi() {
		return iNumeroElementi;
	}

	public void setModello(YModello modello) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (modello != null) {
			idAzienda = KeyHelper.getTokenObjectKey(modello.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iModello.setObject(modello);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YModello getModello() {
		return (YModello)iModello.getObject();
	}

	public void setModelloKey(String key) {
		String oldObjectKey = getKey();
		iModello.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getModelloKey() {
		return iModello.getKey();
	}

	public void setIdModello(String idModello) {
		String key = iModello.getKey();
		iModello.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idModello));
		setDirty();
	}

	public String getIdModello() {
		String key = iModello.getKey();
		String objIdModello = KeyHelper.getTokenObjectKey(key,2);
		return objIdModello;
	}

	public void setModellotermostato(YModelloTermostato modellotermostato) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (modellotermostato != null) {
			idAzienda = KeyHelper.getTokenObjectKey(modellotermostato.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iModellotermostato.setObject(modellotermostato);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YModelloTermostato getModellotermostato() {
		return (YModelloTermostato)iModellotermostato.getObject();
	}

	public void setModellotermostatoKey(String key) {
		String oldObjectKey = getKey();
		iModellotermostato.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getModellotermostatoKey() {
		return iModellotermostato.getKey();
	}

	public void setIdModelloTermostato(String idModelloTermostato) {
		String key = iModellotermostato.getKey();
		iModellotermostato.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idModelloTermostato));
		setDirty();
	}

	public String getIdModelloTermostato() {
		String key = iModellotermostato.getKey();
		String objIdModelloTermostato = KeyHelper.getTokenObjectKey(key,2);
		return objIdModelloTermostato;
	}

	public void setFinitura(YFinitura finitura) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (finitura != null) {
			idAzienda = KeyHelper.getTokenObjectKey(finitura.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iFinitura.setObject(finitura);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YFinitura getFinitura() {
		return (YFinitura)iFinitura.getObject();
	}

	public void setFinituraKey(String key) {
		String oldObjectKey = getKey();
		iFinitura.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getFinituraKey() {
		return iFinitura.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		super.setIdAzienda(idAzienda);
		setIdAziendaInternal(idAzienda);

	}

	public void setIdFinitura(String idFinitura) {
		String key = iFinitura.getKey();
		iFinitura.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idFinitura));
		setDirty();
	}

	public String getIdFinitura() {
		String key = iFinitura.getKey();
		String objIdFinitura = KeyHelper.getTokenObjectKey(key,2);
		return objIdFinitura;
	}

	public void setColore(ValoreVariabileCfg colore) {
		String oldObjectKey = getKey();
		String idAzienda = getIdAzienda();
		if (colore != null) {
			idAzienda = KeyHelper.getTokenObjectKey(colore.getKey(), 1);
		}
		setIdAziendaInternal(idAzienda);
		this.iPotenzaWatt.setObject(colore);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public ValoreVariabileCfg getColore() {
		return (ValoreVariabileCfg)iPotenzaWatt.getObject();
	}

	public void setColoreKey(String key) {
		String oldObjectKey = getKey();
		iPotenzaWatt.setKey(key);
		String idAzienda = KeyHelper.getTokenObjectKey(key, 1);
		setIdAziendaInternal(idAzienda);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getColoreKey() {
		return iPotenzaWatt.getKey();
	}

	public void setIdSchemaCfgPW(String idSchemaCfgColore) {
		String key = iPotenzaWatt.getKey();
		iPotenzaWatt.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idSchemaCfgColore));
		setDirty();
	}

	public String getIdSchemaCfgPW() {
		//			String key = iPotenzaWatt.getKey();
		//			String objIdSchemaCfgColore = KeyHelper.getTokenObjectKey(key,2);
		//			return objIdSchemaCfgColore;
		return getIdSchemaCfg();
	}

	public void setIdVariabilePW(String idVariabileConfigColore) {
		String key = iPotenzaWatt.getKey();
		iPotenzaWatt.setKey(KeyHelper.replaceTokenObjectKey(key , 3, idVariabileConfigColore));
		setDirty();
	}

	public String getIdVariabilePW() {
		String key = iPotenzaWatt.getKey();
		String objIdVariabileConfigColore = KeyHelper.getTokenObjectKey(key,3);
		return objIdVariabileConfigColore;
	}

	public void setSequenzaValorePW(Short sequenzaValoreColore) {
		String key = iPotenzaWatt.getKey();
		iPotenzaWatt.setKey(KeyHelper.replaceTokenObjectKey(key , 4, sequenzaValoreColore));
		setPotenzaWattElettrici(Integer.valueOf(sequenzaValoreColore));
		setDirty();
	}

	public Short getSequenzaValorePW() {
		String key = iPotenzaWatt.getKey();
		String objSequenzaValoreColore = KeyHelper.getTokenObjectKey(key,4);
		return KeyHelper.stringToShortObj(objSequenzaValoreColore);
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YArticoloDatiTecnici yArticoloDatiTecnici = (YArticoloDatiTecnici)obj;
		iModello.setEqual(yArticoloDatiTecnici.iModello);
		iModellotermostato.setEqual(yArticoloDatiTecnici.iModellotermostato);
		iFinitura.setEqual(yArticoloDatiTecnici.iFinitura);
	}

	protected void setIdAziendaInternal(String idAzienda) {
		if(iModello != null) {
			String key1 = iModello.getKey();
			iModello.setKey(KeyHelper.replaceTokenObjectKey(key1, 1, idAzienda));
		}
		if(iModellotermostato != null) {
			String key2 = iModellotermostato.getKey();
			iModellotermostato.setKey(KeyHelper.replaceTokenObjectKey(key2, 1, idAzienda));
		}
		if(iFinitura != null) {
			String key3 = iFinitura.getKey();
			iFinitura.setKey(KeyHelper.replaceTokenObjectKey(key3, 1, idAzienda));
		}
		if(iPotenzaWatt != null) {
			String key4 = iPotenzaWatt.getKey();
			iPotenzaWatt.setKey(KeyHelper.replaceTokenObjectKey(key4, 1, idAzienda));
		}
	}

	public VariabileSchemaCfg getVariabilePotenza() {
		try {
			return (VariabileSchemaCfg) VariabileSchemaCfg.elementWithKey(VariabileSchemaCfg.class, KeyHelper.buildObjectKey(new String[] {
					getIdAzienda(),  getIdSchemaCfg(), "POTENZA"
			}), PersistentObject.NO_LOCK);
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return null;
	}

}

