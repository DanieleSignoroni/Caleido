package it.caleido.thip.base.connettori.utils;

import java.sql.SQLException;

import com.thera.thermfw.base.SystemParam;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.TableManager;

import it.thera.thip.cs.DatiComuniEstesiTTM;

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

public class YPsnDatiEcommerceSerieTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";
	
	public static final String ID_RIGA = "ID_RIGA";
	
	public static final String R_NAZIONE = "R_NAZIONE";
	
	public static final String R_NUMERATORE = "R_NUMERATORE";
	
	public static final String R_SERIE = "R_SERIE";
	
	public static final String STATO = "STATO";
	
	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";
	
	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";
	
	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";
	
	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";
	
	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YPSN_ECOM_SERIE";
	
	private static TableManager cInstance;

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null)
			cInstance = (TableManager) Factory.createObject(YPsnDatiEcommerceSerieTM.class);
		return cInstance;
	}

	public YPsnDatiEcommerceSerieTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(YPsnDatiEcommerceSerie.class.getName());
		configure();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("IdRiga", ID_RIGA);
		addAttribute("RNazione", R_NAZIONE);
		addAttribute("RNumeratore", R_NUMERATORE);
		addAttribute("RSerie", R_SERIE);
		
		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		
		setKeys(ID_AZIENDA + ", " + ID_RIGA);
		
		setTimestampColumn(TIMESTAMP_AGG);
		
		((DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}
}
