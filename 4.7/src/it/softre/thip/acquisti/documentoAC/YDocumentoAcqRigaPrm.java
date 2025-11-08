package it.softre.thip.acquisti.documentoAC;

//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.thera.thermfw.base.Trace;
//import com.thera.thermfw.common.ErrorMessage;
//import com.thera.thermfw.persist.CachedStatement;
//import com.thera.thermfw.persist.ConnectionManager;
//import com.thera.thermfw.persist.Database;
//
//import it.softre.thip.acquisti.uds.YUdsAcqRigTM;
import it.thera.thip.acquisti.documentoAC.DocumentoAcqRigaPrm;
//import it.thera.thip.base.azienda.Azienda;

/**
 * 
 * @author DSSOF3
 *	70687	DSSOF3	04/10/2022	Cancellazione dei riferimenti al DocumentoAcquisto sulla tabella SOFTRE.YUDS_ACQ_RIG
 */

public class YDocumentoAcqRigaPrm extends DocumentoAcqRigaPrm{

	//	protected static final String UPDATE_YUDS_ACQ_RIG = "UPDATE " + YUdsAcqRigTM.TABLE_NAME +
	//			" SET " + YUdsAcqRigTM.R_ANNO_DOC_ACQ + " = null, " +
	//			YUdsAcqRigTM.R_NUM_DOC_ACQ + " = null, " +
	//			YUdsAcqRigTM.R_RIGA_DOC_ACQ + " = null, " +
	//			YUdsAcqRigTM.R_RIGA_DET_DOC_ACQ + " = null " +
	//			"WHERE " + YUdsAcqRigTM.ID_AZIENDA + " = ? " +
	//			"AND " + YUdsAcqRigTM.R_ANNO_DOC_ACQ + " = ? " +
	//			"AND " + YUdsAcqRigTM.R_NUM_DOC_ACQ + " = ? " +
	//			"AND " + YUdsAcqRigTM.R_RIGA_DOC_ACQ + " = ? ";
	//
	//	protected static final String UPDATE_STATO_EVASIONE_YUDS_ACQ_RIG = "UPDATE " + YUdsAcqRigTM.TABLE_NAME +
	//			" SET " + YUdsAcqRigTM.STATO_EVASIONE + " = '0' " +
	//			"WHERE " + YUdsAcqRigTM.ID_UDS + " = ? " +
	//			"AND " + YUdsAcqRigTM.ID_RIGA_UDS + "= ? " +
	//			"AND " + YUdsAcqRigTM.ID_AZIENDA + " = ?" ;
	//
	//	public static CachedStatement cUpdateYUdsAcqRig = new CachedStatement(UPDATE_YUDS_ACQ_RIG);
	//
	//	public static CachedStatement cUpdateStatoEvasioneYUdsAcqRig = new CachedStatement(UPDATE_STATO_EVASIONE_YUDS_ACQ_RIG);
	//
	//	public ErrorMessage checkDelete(){
	//		ErrorMessage err = super.checkDelete();
	//		if (err == null) {
	//			gestioneUdsCollegate();
	//		}
	//		return err;
	//	}
	//	
	//	public void gestioneUdsCollegate() {
	//		try {
	//			String idUds = getIdUdsAcquisto(this.getAnnoDocumento(), this.getNumeroDocumento());
	//			String idRigaUds = getIdRigaUdsAcquisto(this.getAnnoDocumento(), this.getNumeroDocumento(), this.getNumeroRigaDocumento().toString());
	//			aggiornaStatoEvasioneYUdsAcqRig(idUds, idRigaUds);
	//			eliminaRiferimentiDocAcqYUdsAcqRig();
	//		}catch (Exception e) {
	//			e.printStackTrace(Trace.excStream);
	//		}
	//	}
	//	
	//	/**
	//	 * DSSOF3	70687	Cancellazione riferimenti documento acquisto riga, per la riga uds acquisto.
	//	 */
	//	public void eliminaRiferimentiDocAcqYUdsAcqRig() {
	//		try {
	//			PreparedStatement ps = cUpdateYUdsAcqRig.getStatement();
	//			Database db = ConnectionManager.getCurrentDatabase();
	//			db.setString(ps, 1, getIdAzienda());
	//			db.setString(ps, 2, getAnnoDocumento());
	//			db.setString(ps, 3, getNumeroDocumento());
	//			ps.setInt(4, getNumeroRigaDocumento().intValue());
	//			int ret = ps.executeUpdate();
	//			if (ret < 0) {
	//				Trace.excStream.println("Errore in fase di update della tabella SOFTRE.YUDS_ACQ_RIG, procedura cancellazione riferimenti");
	//			}
	//
	//		} catch (Exception ex) {
	//			ex.printStackTrace(Trace.excStream);
	//		}
	//	}
	//	
	//	/**
	//	 * DSSOF3	70687	In seguito alla cancellazione dei riferimenti, riporto lo stato a 0.
	//	 * @param idUds
	//	 * @param riga
	//	 */
	//	public void aggiornaStatoEvasioneYUdsAcqRig(String idUds, String riga) {
	//		try {
	//			if(idUds != null) {
	//				PreparedStatement ps = cUpdateStatoEvasioneYUdsAcqRig.getStatement();
	//				Database db = ConnectionManager.getCurrentDatabase();
	//				db.setString(ps, 1, idUds);
	//				db.setString(ps, 2, riga);
	//				db.setString(ps, 3, Azienda.getAziendaCorrente());
	//				int ret = ps.executeUpdate();
	//				if (ret < 0) {
	//					Trace.excStream.print("Impossibile eliminare i riferimenti del documento vendita");
	//				}
	//			}
	//		} catch (Exception ex) {
	//			ex.printStackTrace(Trace.excStream);
	//		}
	//	}
	//
	//	public String getIdRigaUdsAcquisto(String anno, String numero, String riga) {
	//		String idUds = null;
	//		try {
	//			ResultSet rs = null;
	//			String stmt = "SELECT ID_RIGA_UDS FROM SOFTRE.YUDS_ACQ_RIG"
	//					+ " WHERE ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "'"
	//					+ " AND R_ANNO_DOC_ACQ = '" + anno + "'"
	//					+ " AND R_NUM_DOC_ACQ = '" + numero + "'"
	//					+ "AND R_RIGA_DOC_ACQ = '" + riga + "'";
	//			CachedStatement cs = new CachedStatement(stmt);
	//			rs = cs.executeQuery();
	//			if(rs.next()) {
	//				idUds = rs.getString("ID_RIGA_UDS") != null ? rs.getString("ID_RIGA_UDS") : "";
	//			}
	//			cs.free();
	//		}catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//		return idUds;
	//	}
	//
	//	public String getIdUdsAcquisto(String anno, String numero) {
	//		String idUds = null;
	//		try {
	//			ResultSet rs = null;
	//			String stmt = "SELECT ID_UDS FROM SOFTRE.YUDS_ACQ_TES"
	//					+ " WHERE ID_AZIENDA = '" + Azienda.getAziendaCorrente() + "'"
	//					+ " AND R_ANNO_DOC_ACQ = '" + anno + "'"
	//					+ " AND R_NUM_DOC_ACQ = '" + numero + "'";
	//			CachedStatement cs = new CachedStatement(stmt);
	//			rs = cs.executeQuery();
	//			if(rs.next()) {
	//				idUds = rs.getString("ID_UDS") != null ? rs.getString("ID_UDS") : "";
	//			}
	//			cs.free();
	//		}catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//		return idUds;
	//	}

}
