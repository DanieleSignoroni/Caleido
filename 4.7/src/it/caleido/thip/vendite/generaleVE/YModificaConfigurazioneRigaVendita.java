package it.caleido.thip.vendite.generaleVE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneProxyEnh;
import it.thera.thip.vendite.offerteCliente.OffertaClienteRigaPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 04/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    04/02/2025</b>
 * <p></p>
 */

public class YModificaConfigurazioneRigaVendita extends BusinessObjectAdapter implements Authorizable {

	protected String iIdAzienda;

	protected String iClassName;
	protected String iChiaviSelezionati;

	protected ConfigurazioneProxyEnh iConfigurazione = new ConfigurazioneProxyEnh(Configurazione.class);
	protected Proxy iArticolo = new Proxy(Articolo.class);

	public YModificaConfigurazioneRigaVendita() {
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
		iConfigurazione.setKey(KeyHelper.replaceTokenObjectKey(iConfigurazione.getKey(), 1, idAzienda));
		iArticolo.setKey(KeyHelper.replaceTokenObjectKey(iArticolo.getKey(), 1, idAzienda));
	}

	public String getIdEsternoConfig() {
		return iConfigurazione.getIdEsternoConfig();
	}

	public void setIdEsternoConfig(String idEsternoConfig) {
		iConfigurazione.setIdEsternoConfig(idEsternoConfig);
	}

	public Configurazione getConfigurazione() {
		return (Configurazione)iConfigurazione.getObject();
	}

	public void setConfigurazione(Configurazione iConfigurazione) {
		this.iConfigurazione.setObject(iConfigurazione);
	}

	public String getConfigurazioneKey() {
		return iConfigurazione.getKey();
	}

	public void setConfigurazioneKey(String key) {
		iConfigurazione.setKey(key);
	}

	public Integer getIdConfigurazione() {
		String key = iConfigurazione.getKey();
		Integer rConfigurazione =
				KeyHelper.stringToIntegerObj(
						KeyHelper.getTokenObjectKey(key,2)
						);
		return rConfigurazione;
	}

	public void setIdConfigurazione(Integer rConfigurazione) {
		String key = iConfigurazione.getKey();
		iConfigurazione.setKey(
				KeyHelper.replaceTokenObjectKey(key , 2, rConfigurazione)
				);
	}

	public Articolo getArticolo() {
		return (Articolo)iArticolo.getObject();
	}

	public void setArticolo(Articolo iArticolo) {
		this.iArticolo.setObject(iArticolo);
	}

	public String getArticoloKey() {
		return iArticolo.getKey();
	}

	public void setArticoloKey(String key) {
		iArticolo.setKey(key);
	}

	public String getIdArticolo() {
		String key = iArticolo.getKey();
		String rArticolo = KeyHelper.getTokenObjectKey(key,2);
		return rArticolo;
	}

	public void setIdArticolo(String rArticolo) {
		String key = iArticolo.getKey();
		iArticolo.setKey(KeyHelper.replaceTokenObjectKey(key , 2, rArticolo));
		iConfigurazione.setIdArticolo(rArticolo);
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
}
