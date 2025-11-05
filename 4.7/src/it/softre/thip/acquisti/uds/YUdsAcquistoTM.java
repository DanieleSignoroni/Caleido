package it.softre.thip.acquisti.uds;

import com.thera.thermfw.persist.*;
import java.sql.*;
import com.thera.thermfw.base.*;
import it.thera.thip.cs.*;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura.
 */

public class YUdsAcquistoTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String ALTEZZA = "ALTEZZA";

	public static final String ID_FORNITORE = "ID_FORNITORE";

	public static final String ID_TIPO_CONTENITORE = "ID_TIPO_CONTENITORE";

	public static final String ID_UDS = "ID_UDS";

	public static final String LARGHEZZA = "LARGHEZZA";

	public static final String LUNGHEZZA = "LUNGHEZZA";

	public static final String NOTE = "NOTE";

	public static final String PESO_CONTENITORE = "PESO_CONTENITORE";

	public static final String PESO_LORDO = "PESO_LORDO";

	public static final String PESO_NETTO = "PESO_NETTO";

	public static final String R_MAGAZZINO = "R_MAGAZZINO";

	public static final String R_UBICAZIONE = "R_UBICAZIONE";

	public static final String STATO_EVASIONE = "STATO_EVASIONE";

	public static final String R_ID_UDS_PADRE = "R_ID_UDS_PADRE";

	public static final String R_ANNO_DOC_ACQ = "R_ANNO_DOC_ACQ";

	public static final String R_ANNO_ORD_ACQ = "R_ANNO_ORD_ACQ";

	public static final String R_NUM_DOC_ACQ = "R_NUM_DOC_ACQ";

	public static final String R_NUM_ORD_ACQ = "R_NUM_ORD_ACQ";

	public static final String VOLUME = "VOLUME";

	public static final String DATA_UDS = "DATA_UDS";

	public static final String STATO_ST_PACKING = "STATO_ST_PACKING";

	public static final String STATO_ST_ETI = "STATO_ST_ETI";

	public static final String ID_SCHEMA_CFG = "R_SCHEMA_CFG";
	public static final String ID_VARIABILE_CFG = "R_VARIABILE_CFG";
	public static final String SEQ_VALORE = "SEQ_VALORE";

	public static final String TABLE_NAME = SystemParam.getSchema("SOFTRE") + "YUDS_ACQ_TES";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.softre.thip.acquisti.uds.YUdsAcquisto.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager)Factory.createObject(YUdsAcquistoTM.class);
		}
		return cInstance;
	}

	public YUdsAcquistoTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("Altezza", ALTEZZA);
		addAttribute("IdUds", ID_UDS);
		addAttribute("Larghezza", LARGHEZZA);
		addAttribute("Lunghezza", LUNGHEZZA);
		addAttribute("Note", NOTE);
		addAttribute("PesoContenitore", PESO_CONTENITORE);
		addAttribute("PesoLordo", PESO_LORDO);
		addAttribute("PesoNetto", PESO_NETTO);
		addAttribute("StatoEvasione", STATO_EVASIONE);
		addAttribute("RIdUdsPadre", R_ID_UDS_PADRE);
		addAttribute("Volume", VOLUME);
		addAttribute("DataUds", DATA_UDS);
		addAttribute("StatoStPacking", STATO_ST_PACKING);
		addAttribute("StatoStEti", STATO_ST_ETI);
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdFornitore", ID_FORNITORE);
		addAttribute("RMagazzino", R_MAGAZZINO);
		addAttribute("RUbicazione", R_UBICAZIONE);
		addAttribute("IdTipoContenitore", ID_TIPO_CONTENITORE);
		addAttribute("RAnnoDocAcq", R_ANNO_DOC_ACQ);
		addAttribute("RNumDocAcq", R_NUM_DOC_ACQ);
		addAttribute("RAnnoOrdAcq", R_ANNO_ORD_ACQ);
		addAttribute("RNumOrdAcq", R_NUM_ORD_ACQ);
		
		addAttribute("IdSchemaCfg" , ID_SCHEMA_CFG);
	    addAttribute("IdVariabileCfg" , ID_VARIABILE_CFG);
	    addAttribute("SeqValore" , SEQ_VALORE);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA + "," + ID_UDS);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM)getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure();
	}

}