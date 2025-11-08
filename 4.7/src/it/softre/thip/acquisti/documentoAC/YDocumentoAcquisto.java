package it.softre.thip.acquisti.documentoAC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.softre.thip.acquisti.uds.YUdsAcquistoTM;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ThipException;

/**
 * 
 * @author DSSOF3
 *	70687	DSSOF3	04/10/2022	Cancellazione riferimenti DocumentoAcquisto sull'Uds Acquisto post evasione.
 */

public class YDocumentoAcquisto extends DocumentoAcquisto{

	@Override
	public int delete() throws SQLException {
		int rc = super.delete();
		if(rc > 0) {
			gestioneUdsAcquisto();
		}
		return rc;
	}

	@Override
	public ErrorMessage convalida(int rc) {
		ErrorMessage em = super.convalida(rc);
		if(em == null) {
			try {
				List<String> listaUdsCollegate = recuperaChiaviUdsAcquistoCollegate();
				for(String key : listaUdsCollegate) {
					try {
						YUdsAcquisto uds = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, key, PersistentObject.NO_LOCK);
						if(uds != null) {
							uds.aggiornaStatoUdsDaConvalidaDocumento(YUdsAcquisto.VERSATO_A_MAGAZZINO,this);
							int rcUds = uds.save();
							if(rcUds < 0) {
								//don't know
							}
						}
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}catch (Exception e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return em;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List regressione(int rc) {
		List errors = super.regressione(rc);
		if(errors == null || errors.isEmpty()) {
			try {
				List<String> listaUdsCollegate = recuperaChiaviUdsAcquistoCollegate();
				for(String key : listaUdsCollegate) {
					try {
						YUdsAcquisto uds = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, key, PersistentObject.NO_LOCK);
						if(uds != null) {
							uds.aggiornaStatoUdsDaConvalidaDocumento(YUdsAcquisto.GENERATO_DOCUMENTO,this);
							int rcUds = uds.save();
							if(rcUds < 0) {
								//don't know
							}
						}
					} catch (SQLException e) {
						e.printStackTrace(Trace.excStream);
					}
				}
			}catch (Exception e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return errors;
	}

	protected void gestioneUdsAcquisto() {
		List<String> listaUdsCollegate = recuperaChiaviUdsAcquistoCollegate();
		for(String key : listaUdsCollegate) {
			try {
				YUdsAcquisto uds = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, key, PersistentObject.NO_LOCK);
				if(uds != null) {
					uds.aggiornaUdsPostCancellazioneDocumentoAcquisto(this);
					int rc = uds.save();
					if(rc < 0) {
						throw new ThipException("Impossibile sistemare l'uds di acquisto : "+uds.getKey());
						// da provare non so se arriva su dato il try qui sotto
					}
				}
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
	}

	/**
	 * @author Daniele Signoroni 18/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Serve per recuperare la lista delle uds di vendita collegate ad un documento vendita.<br>
	 * </p>
	 * @return
	 */
	public List<String> recuperaChiaviUdsAcquistoCollegate() {
		List<String> listaUdsCollegate = new ArrayList<String>();
		ResultSet rs = null;
		try {
			String stmt = "SELECT "+YUdsAcquistoTM.ID_AZIENDA+","+YUdsAcquistoTM.ID_UDS+" FROM "+YUdsAcquistoTM.TABLE_NAME+" "
					+ " WHERE "+YUdsAcquistoTM.ID_AZIENDA+" = '" + Azienda.getAziendaCorrente() + "'"
					+ " AND ("+YUdsAcquistoTM.R_ANNO_DOC_ACQ+" = '" + getAnnoDocumento()+ "'"
					+ " AND "+YUdsAcquistoTM.R_NUM_DOC_ACQ+" = '" + getNumeroDocumento() + "')";
			CachedStatement cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			while(rs.next()) {
				String idAzienda = rs.getString(YUdsAcquistoTM.ID_AZIENDA);
				String idUds = rs.getString(YUdsAcquistoTM.ID_UDS);
				listaUdsCollegate.add(KeyHelper.buildObjectKey(new String[] {
						idAzienda,idUds
				}));
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(Trace.excStream);
				}
			}
		}
		return listaUdsCollegate;
	}
}
