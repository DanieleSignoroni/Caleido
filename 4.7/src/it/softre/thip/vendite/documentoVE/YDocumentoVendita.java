package it.softre.thip.vendite.documentoVE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CachedStatement;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;

import it.softre.thip.vendite.uds.YUdsVendita;
import it.softre.thip.vendite.uds.YUdsVenditaTM;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.cs.ThipException;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;

/**
 * 
 * @author DSSOF3
 *	70687	DSSOF3	04/10/2022	Cancellazione riferimenti DocumentoVendita sull'Uds Vendita post evasione.
 */

public class YDocumentoVendita extends DocumentoVendita{	


	@Override
	public int delete() throws SQLException {
		int rc = super.delete();
		if(rc > 0) {
			gestioneUdsVendita();
		}
		return rc;
	}

	@Override
	public ErrorMessage convalida(int rc) {
		ErrorMessage em = super.convalida(rc);
		if(em == null) {
			try {
				List<String> listaUdsCollegate = recuperaChiaviUdsVenditaCollegate();
				for(String key : listaUdsCollegate) {
					try {
						YUdsVendita uds = (YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, key, PersistentObject.NO_LOCK);
						if(uds != null) {
							uds.aggiornaStatoUdsDaConvalidaDocumento(YUdsVendita.VERSATO_A_MAGAZZINO,this);
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
				List<String> listaUdsCollegate = recuperaChiaviUdsVenditaCollegate();
				for(String key : listaUdsCollegate) {
					try {
						YUdsVendita uds = (YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, key, PersistentObject.NO_LOCK);
						if(uds != null) {
							uds.aggiornaStatoUdsDaConvalidaDocumento(YUdsVendita.GENERATO_DOCUMENTO,this);
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

	protected void gestioneUdsVendita() {
		List<String> listaUdsCollegate = recuperaChiaviUdsVenditaCollegate();
		for(String key : listaUdsCollegate) {
			try {
				YUdsVendita uds = (YUdsVendita) YUdsVendita.elementWithKey(YUdsVendita.class, key, PersistentObject.NO_LOCK);
				if(uds != null) {
					uds.aggiornaUdsPostCancellazioneDocumentoVendita(this);
					int rc = uds.save();
					if(rc < 0) {
						throw new ThipException("Impossibile sistemare l'uds di vendita : "+uds.getKey());
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
	public List<String> recuperaChiaviUdsVenditaCollegate() {
		List<String> listaUdsCollegate = new ArrayList<String>();
		ResultSet rs = null;
		try {
			String stmt = "SELECT "+YUdsVenditaTM.ID_AZIENDA+","+YUdsVenditaTM.ID_UDS+" FROM "+YUdsVenditaTM.TABLE_NAME+" "
					+ " WHERE "+YUdsVenditaTM.ID_AZIENDA+" = '" + Azienda.getAziendaCorrente() + "'"
					+ " AND ("+YUdsVenditaTM.R_ANNO_DOC_VEN+" = '" + getAnnoDocumento()+ "'"
					+ " AND "+YUdsVenditaTM.R_NUM_DOC_VEN+" = '" + getNumeroDocumento() + "') "
					+ "OR ("+YUdsVenditaTM.R_ANNO_DOC_VEN_TRASF+" = '" + getAnnoDocumento()+ "'"
					+ " AND "+YUdsVenditaTM.R_NUMERO_DOC_VEN_TRASF+" = '" + getNumeroDocumento() + "') ";
			CachedStatement cs = new CachedStatement(stmt);
			rs = cs.executeQuery();
			while(rs.next()) {
				String idAzienda = rs.getString(YUdsVenditaTM.ID_AZIENDA);
				String idUds = rs.getString(YUdsVenditaTM.ID_UDS);
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
