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
 * Date: 31/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72630    31/08/2026  DSSOF3   Prima stesura
 */

public class YPsnDatiEcommerceTM extends TableManager {

	public static final String ID_AZIENDA = "ID_AZIENDA";

	public static final String STATO = "STATO";

	public static final String R_UTENTE_CRZ = "R_UTENTE_CRZ";

	public static final String TIMESTAMP_CRZ = "TIMESTAMP_CRZ";

	public static final String R_UTENTE_AGG = "R_UTENTE_AGG";

	public static final String TIMESTAMP_AGG = "TIMESTAMP_AGG";

	public static final String R_NUMERATORE_OFF_VEN = "R_NUMERATORE_OFF_VEN";

	public static final String R_SERIE_OFF_VEN = "R_SERIE_OFF_VEN";

	public static final String R_VALUTA = "R_VALUTA";

	public static final String R_ASSOG_IVA = "R_ASSOG_IVA";

	public static final String R_CAU_OFF_TES = "R_CAU_OFF_TES";

	public static final String R_CAU_OFF_RIG = "R_CAU_OFF_RIG";

	public static final String R_CAU_RIG_OFF_SC_MERCE = "R_CAU_RIG_OFF_SC_MERCE";

	public static final String TABLE_NAME = SystemParam.getSchema("THIPPERS") + "YPSN_DATI_ECOMMERCE";

	private static TableManager cInstance;

	private static final String CLASS_NAME = it.caleido.thip.base.connettori.utils.YPsnDatiEcommerce.class.getName();

	public synchronized static TableManager getInstance() throws SQLException {
		if (cInstance == null) {
			cInstance = (TableManager) Factory.createObject(YPsnDatiEcommerceTM.class);
		}
		return cInstance;
	}

	public YPsnDatiEcommerceTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		setTableName(TABLE_NAME);
		setObjClassName(CLASS_NAME);
		init();
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		addAttribute("IdAzienda", ID_AZIENDA);
		addAttribute("RNumeratoreOffVen", R_NUMERATORE_OFF_VEN);
		addAttribute("RSerieOffVen", R_SERIE_OFF_VEN);
		addAttribute("RAssogIva", R_ASSOG_IVA);
		addAttribute("RCauOffRig", R_CAU_OFF_RIG);
		addAttribute("RCauRigOffScMerce", R_CAU_RIG_OFF_SC_MERCE);
		addAttribute("RCauOffTes", R_CAU_OFF_TES);
		addAttribute("RValuta", R_VALUTA);

		addComponent("DatiComuniEstesi", DatiComuniEstesiTTM.class);
		setKeys(ID_AZIENDA);

		setTimestampColumn("TIMESTAMP_AGG");
		((it.thera.thip.cs.DatiComuniEstesiTTM) getTransientTableManager("DatiComuniEstesi")).setExcludedColums();
	}

	private void init() throws SQLException {
		configure(ID_AZIENDA + ", " + R_NUMERATORE_OFF_VEN + ", " + R_SERIE_OFF_VEN + ", " + R_ASSOG_IVA + ", "
				+ R_CAU_OFF_RIG + ", " + R_CAU_RIG_OFF_SC_MERCE + ", " + R_CAU_OFF_TES + ", " + R_VALUTA + ", " + STATO
				+ ", " + R_UTENTE_CRZ + ", " + TIMESTAMP_CRZ + ", " + R_UTENTE_AGG + ", " + TIMESTAMP_AGG);
	}

}