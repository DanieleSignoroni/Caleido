package it.caleido.thip.base.connettori.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.ConnectionManager;
import com.thera.thermfw.persist.Database;

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

public class YPsnDatiEcommerceSerie extends YPsnDatiEcommerceSeriePO {

	public static final String QUERY_SAVE = "SELECT COALESCE(MAX(ID_RIGA)+1,1) AS MAXI_VAL "
			+ "FROM THIPPERS.YPSN_ECOM_SERIE WHERE ID_AZIENDA = ? ";
	public static CachedStatement querySave = new CachedStatement(QUERY_SAVE);

	public ErrorMessage checkDelete() {
		return null;
	}

	public int save() throws SQLException {
		if (!isOnDB()) {
			ResultSet rs = null;
			int value = 1;
			Database db = ConnectionManager.getCurrentDatabase();
			synchronized(querySave){
				PreparedStatement ps = querySave.getStatement();
				db.setString(ps, 1, getIdAzienda());
				rs = ps.executeQuery();
				if (rs.next())
					value = rs.getInt("MAXI_VAL");
				rs.close();
				setIdRiga(value);
			}
		}
		return super.save();
	}

	@SuppressWarnings("rawtypes")
	public static YPsnDatiEcommerceSerie findForNazione(String idAzienda, String nazione) throws SQLException {
		if (idAzienda == null || idAzienda.trim().length() == 0)
			throw new IllegalArgumentException("Azienda obbligatoria");
		try {
			Vector rows = retrieveList(
					YPsnDatiEcommerceSerieTM.ID_AZIENDA + " = '" + idAzienda.replace("'", "''") + "'", null, true);
			return selectForNazione(rows, nazione);
		} catch (ClassNotFoundException ex) {
			throw new SQLException("Classe configurazione non disponibile", ex);
		} catch (InstantiationException ex) {
			throw new SQLException("Impossibile creare la configurazione", ex);
		} catch (IllegalAccessException ex) {
			throw new SQLException("Configurazione non accessibile", ex);
		}
	}

	@SuppressWarnings("rawtypes")
	static YPsnDatiEcommerceSerie selectForNazione(Vector rows, String nazione) throws SQLException {
		String requested = normalizeNazione(nazione);
		YPsnDatiEcommerceSerie specific = null;
		YPsnDatiEcommerceSerie generic = null;
		for (Object object : rows) {
			YPsnDatiEcommerceSerie row = (YPsnDatiEcommerceSerie) object;

			String country = normalizeNazione(row.getRNazione());
			if (country == null) {
				if (generic != null)
					throw new SQLException("Configurazione generica duplicata: YPsnDatiEcommerceSerie");
				generic = row;
			} else if (country.equals(requested)) {
				if (specific != null)
					throw new SQLException("Configurazione nazione duplicata: YPsnDatiEcommerceSerie");
				specific = row;
			}
		}
		return specific != null ? specific : generic;
	}

	private static String normalizeNazione(String value) {
		return value == null || value.trim().length() == 0 ? null : value.trim();
	}
}
