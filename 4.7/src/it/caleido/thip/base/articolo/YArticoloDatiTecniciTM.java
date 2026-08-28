package it.caleido.thip.base.articolo;

import java.sql.*;
import it.thera.thip.base.articolo.*;
import com.thera.thermfw.base.*;

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
 */
public class YArticoloDatiTecniciTM extends ArticoloDatiTecniciTM {

	public static final String POTENZA_WATT_IDRAULICI = "POTENZA_WATT_IDRAULICI";

	public static final String POTENZA_WATT_ELETTRICI = "POTENZA_WATT_ELETTRICI";

	public static final String ORIZZONTALE_O_VERTICALE = "ORIZZONTALE_O_VERTICALE";

	public static final String DESTRO_O_SINISTRO = "DESTRO_O_SINISTRO";

	public static final String NUMERO_ELEMENTI = "NUMERO_ELEMENTI";

	public static final String R_MODELLO = "R_MODELLO";

	public static final String R_MODELLO_TERMOSTATO = "R_MODELLO_TERMOSTATO";

	public static final String R_FINITURA = "R_FINITURA";

	public static final String ID_SCHEMA_CFG_POTENZAW = "R_SCHEMA_CFG_POTENZAW";
	public static final String ID_VARIABILE_CONFIG_POTENZAW = "R_VARIABILE_CONFIG_POTENZAW";
	public static final String SEQUENZA_VALORE_POTENZAW = "R_SEQUENZA_VALORE_POTENZAW";

	public static final String TABLE_NAME_EXT = SystemParam.getSchema("THIPPERS") + "YARTICOLI";

	private static final String CLASS_NAME = it.caleido.thip.base.articolo.YArticoloDatiTecnici.class.getName();

	public YArticoloDatiTecniciTM() throws SQLException {
		super();
	}

	protected void initialize() throws SQLException {
		super.initialize();
		setObjClassName(CLASS_NAME);
	}

	protected void initializeRelation() throws SQLException {
		super.initializeRelation();
		linkTable(TABLE_NAME_EXT);
		addAttributeOnTable("PotenzaWattIdraulici", POTENZA_WATT_IDRAULICI, "getIntegerObject", TABLE_NAME_EXT);
		addAttributeOnTable("PotenzaWattElettrici", POTENZA_WATT_ELETTRICI, "getIntegerObject", TABLE_NAME_EXT);
		addAttributeOnTable("OrizzontaleOVerticale", ORIZZONTALE_O_VERTICALE, TABLE_NAME_EXT);
		addAttributeOnTable("DestroOSinistro", DESTRO_O_SINISTRO, TABLE_NAME_EXT);
		addAttributeOnTable("NumeroElementi", NUMERO_ELEMENTI, "getShortObject", TABLE_NAME_EXT);
		addAttributeOnTable("IdModello", R_MODELLO, TABLE_NAME_EXT);
		addAttributeOnTable("IdModelloTermostato", R_MODELLO_TERMOSTATO, TABLE_NAME_EXT);
		addAttributeOnTable("IdFinitura", R_FINITURA, TABLE_NAME_EXT);
		
		addAttribute("IdSchemaCfgPW", ID_SCHEMA_CFG_POTENZAW);
		addAttribute("IdVariabilePW", ID_VARIABILE_CONFIG_POTENZAW);
		addAttribute("SequenzaValorePW", SEQUENZA_VALORE_POTENZAW, "getShortObject");
	}

}

