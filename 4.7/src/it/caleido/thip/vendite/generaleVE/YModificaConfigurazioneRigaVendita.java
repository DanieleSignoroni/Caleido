package it.caleido.thip.vendite.generaleVE;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.ApiInfo;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.BaseComponentsCollection;
import com.thera.thermfw.common.BusinessObjectAdapter;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Database;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.Proxy;
import com.thera.thermfw.security.Authorizable;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.cs.SaveConWarning;
import it.thera.thip.cs.ThipException;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneProxyEnh;
import it.thera.thip.datiTecnici.configuratore.ConfigurazioneTM;
import it.thera.thip.datiTecnici.configuratore.SezioneConfigurazione;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;
import it.thera.thip.vendite.offerteCliente.OffertaClienteRigaPrm;
import it.thera.thip.vendite.ordineVE.OrdineVenditaRigaPrm;

/**
 * Business object che risiede dietro la form 'it/caleido/thip/vendite/generaleVE/YModificaConfigurazioneRigaVendita.jsp'.<br></br>
 * Permette a partire da una riga (Offerta,Ordine) di cambiare la configurazione.<br></br>
 * 
 * Cambiare significa sceglierne tra una esistente o crearne una nuova.<br>
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 04/02/2025
 * <br><br>
 * <b>71811    DSSOF3    04/02/2025</b>
 * <p></p>
 */

public class YModificaConfigurazioneRigaVendita extends BusinessObjectAdapter implements Authorizable,SaveConWarning {

	public static final String STMT_SELECT_EXISTING_CONFIG = "SELECT * FROM "+ConfigurazioneTM.TABLE_NAME+" CFG "
			+ "WHERE CFG."+ConfigurazioneTM.ID_AZIENDA+" = ? AND CFG."+ConfigurazioneTM.R_ARTICOLO+" = ? AND CFG."+ConfigurazioneTM.SINTESI_CONFIG+" LIKE ? ";
	public static CachedStatement cSelectExistingConfiguration = new CachedStatement(STMT_SELECT_EXISTING_CONFIG);

	public static final String YCLD_00001 = "YCLD_00001";

	protected String iIdAzienda;

	protected String iClassName;
	protected String iChiaviSelezionati;

	protected String iSintesiConfigurazione;

	protected ConfigurazioneProxyEnh iConfigurazione = new ConfigurazioneProxyEnh(Configurazione.class);
	protected Proxy iArticolo = new Proxy(Articolo.class);

	@SuppressWarnings("rawtypes")
	private List warnigns = new ArrayList();

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

	public String getSintesiConfigurazione() {
		return iSintesiConfigurazione;
	}

	public void setSintesiConfigurazione(String iSinstesiConfigurazione) {
		this.iSintesiConfigurazione = iSinstesiConfigurazione;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = super.checkAll(components);
		Configurazione configurazione = getConfigurazione();
		String sintesiConfigGUI = getSintesiConfigurazioneGUI();
		if(sintesiConfigGUI.equals(configurazione.getSintesiConfig())) {
			errors.add(new ErrorMessage("BAS0000078","La nuvoa configurazione e' uguale a quella di partenza"));
		}
		if(errors.isEmpty()) {

			try {
				Configurazione existingConf = leggiConfigurazione(getIdAzienda(), getIdArticolo(), sintesiConfigGUI);
				if(existingConf == null) {
					errors.add(new ErrorMessage(YCLD_00001));
				}else {
					//gia' esiste la sostituisco e basta
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return errors;
	}

	@Override
	public int save(boolean force) throws SQLException {
		int rc = 0;
		Configurazione configurazione = getConfigurazione();
		String sintesiConfigGUI = getSintesiConfigurazioneGUI();
		//Non dovrebbe arrivare mai qui ma se le trovo uguali allora exc
		if(sintesiConfigGUI.equals(configurazione.getSintesiConfig())) {
			throw new ThipException("La nuvoa configurazione e' uguale a quella di partenza");
		}
		Configurazione destination = null;
		try {
			//Controllo se a db esiste gia' una configurazione fatta come quella in GUI
			Configurazione existingConf = leggiConfigurazione(getIdAzienda(), getIdArticolo(), sintesiConfigGUI);
			if(existingConf == null) {
				//Se non esiste la creo, copiando quella di partenza e settandogli la nuova stringa sintesi
				destination = (Configurazione) Factory.createObject(Configurazione.class);
				try {
					destination.setEqual(getConfigurazione());
				} catch (CopyException e) {
					throw new ThipException(e.getMessage());
				}
				destination.setIdConfigurazione(new Integer(0));
				destination.setSintesiConfig(sintesiConfigGUI);
				rc = destination.save();
			}else {
				//Se esiste setto quella alla riga
				destination = existingConf;
			}
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		//Qui poi prendo la riga selezionata e gli cambio la conf, facendo fare la save al boDC 
		List<PersistentObject> righe = getRigheSelezionate();
		for(PersistentObject riga : righe) {
			BODataCollector boDC = createDataCollector(getClassName());
			if(riga instanceof OffertaClienteRigaPrm) {
				((OffertaClienteRigaPrm) riga).setConfigurazione(destination);
				boDC.setBo(((OffertaClienteRigaPrm) riga));
			}else if(riga instanceof OrdineVenditaRigaPrm) {
				((OrdineVenditaRigaPrm) riga).setConfigurazione(destination);
				boDC.setBo(((OrdineVenditaRigaPrm) riga));
			}
			boDC.setAutoCommit(false);
			rc = boDC.save();
			if(rc == BODataCollector.OK)
				rc = 1;
			//warnigns.addAll(boDC.getErrorList().getErrors()); mi dava problemi poi nel mostrare la riga alla fine
		}
		return rc;
	}

	/**
	 * Ritorna la stringa di sintesi configurazione costruita nella form.<br>
	 * @return
	 */
	protected String getSintesiConfigurazioneGUI() {
		return buildSortedSintesiConfig(sinstesiConfigurazioneGuiMap());
	}

	/**
	 * Ritorna la mappa con combinazione variabile/valore della configurazione costruita nella form.<br>
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected HashMap sinstesiConfigurazioneGuiMap() {
		Configurazione configurazione = getConfigurazione();
		HashMap sintesiGUIMap = new HashMap();
		List sezioniList = null;
		if(getSintesiConfigurazione() != null && getSintesiConfigurazione().contains(ColonneFiltri.SEP)) {
			sezioniList = Arrays.asList(getSintesiConfigurazione().split(ColonneFiltri.SEP));
		}else {
			sezioniList = new ArrayList();
			sezioniList.add(getSintesiConfigurazione());
		}
		Iterator iterSezioniGUI = sezioniList.iterator();
		Hashtable valoriGUI = new Hashtable();

		while(iterSezioniGUI.hasNext()) {
			Hashtable valoriVarGUI = configurazione.getVariabiliValoriValue((String) iterSezioniGUI.next());
			valoriGUI.putAll(valoriVarGUI);
		}


		for(int j=0; j<configurazione.getSchemaCfg().getSezioni().size(); j++){
			SezioneConfigurazione sezione = (SezioneConfigurazione) configurazione.getSchemaCfg().getSezioni().get(j);
			for(int i = 0; i < sezione.getVariabili().size() ; i++) {
				String str = "";
				VariabileSchemaCfg variabileCfg = (VariabileSchemaCfg)sezione.getVariabili().get(i);
				String valueValore = (String)valoriGUI.get(variabileCfg);
				if(valueValore != null){
					str = variabileCfg.getIdVariabileConfig() + PersistentObject.KEY_SEPARATOR + valueValore;
				}
				sintesiGUIMap.put(variabileCfg, str);
			}
		}
		return sintesiGUIMap;
	}

	protected static Configurazione leggiConfigurazione(String idAzienda,String idArticolo,String sintesiConfigurazione) throws SQLException{
		ResultSet rs = null;
		Configurazione conf = null;
		try{
			PreparedStatement ps = cSelectExistingConfiguration.getStatement();
			Database db = ConnectionManager.getCurrentDatabase();
			db.setString(ps, 1, idAzienda);
			db.setString(ps, 2, idArticolo);
			db.setString(ps, 3, sintesiConfigurazione);
			rs = ps.executeQuery();
			if (rs.next()){
				conf = Configurazione.elementWithKey(KeyHelper.buildObjectKey(new String[] {
						rs.getString(ConfigurazioneTM.ID_AZIENDA),
						rs.getString(ConfigurazioneTM.ID_CONFIG)
				}), PersistentObject.NO_LOCK);
			}
		}
		finally{
			try{
				if(rs != null)
					rs.close();
			}
			catch(SQLException e){
				e.printStackTrace(Trace.excStream);
			}
		}
		return conf;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static String buildSortedSintesiConfig(HashMap sintesiMap) {
		List tmp = new ArrayList(sintesiMap.keySet());
		Collections.sort(tmp, new Comparator() {
			public int compare(Object o1, Object o2) {
				VariabileSchemaCfg v1 = (VariabileSchemaCfg) o1;
				VariabileSchemaCfg v2 = (VariabileSchemaCfg) o2;
				if (v1.getIdSezioneCfg().equals(v2.getIdSezioneCfg())) {
					Short s1 = new Short(v1.getSeqVisual());
					Short s2 = new Short(v2.getSeqVisual());
					return s1.compareTo(s2);
				}
				else
					return v1.getIdSezioneCfg().compareTo(v2.getIdSezioneCfg());
			}
		});
		String ret = "";
		Iterator iter = tmp.iterator();
		while (iter.hasNext()) {
			String str = (String) sintesiMap.get(iter.next());
			if (str != null && !str.equals("")) {
				if (!ret.equals(""))
					ret += KeyHelper.KEY_SEPARATOR;
				ret += str;
			}
		}
		return ret;
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

	protected BODataCollector createDataCollector(String classname) {
		try {
			ClassADCollection hdr = ClassADCollectionManager.collectionWithName(classname);
			return createDataCollector(hdr);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected BODataCollector createDataCollector(ClassADCollection classDescriptor) {
		BODataCollector dataCollector = null;
		String collectorName = classDescriptor.getBODataCollector();
		if (collectorName != null) {
			dataCollector = (BODataCollector) Factory.createObject(collectorName);
		} else {
			dataCollector = new BODataCollector();
		}

		//PJ - ApiInfo - inizio
		ApiInfo info = new ApiInfo();
		info.doNotAddNullComponentsToGroup = true;
		dataCollector.setApiInfo(info);
		dataCollector.initialize(classDescriptor.getClassName(), true);

		return dataCollector;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getWarningList() {
		return warnigns;
	}
}
