package it.softre.thip.acquisti.uds;

import com.thera.thermfw.persist.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Vector;

import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.produzione.ordese.OrdineEsecutivo;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.acquisti.ordineAC.OrdineAcquistoRigaPrm;

import com.thera.thermfw.common.*;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura.	
 */

public class YUdsAcqRig extends YUdsAcqRigPO {

	public static final String QUERY_SAVE = "SELECT COALESCE(MAX(ID_RIGA_UDS)+1,1) AS MAXI_VAL FROM SOFTRE.YUDS_ACQ_RIG WHERE ID_AZIENDA = ? AND ID_UDS = ?";

	public static CachedStatement querySave = new CachedStatement(QUERY_SAVE);

	public static final String SELECT_RIGA_ORDINE_CLIENTE = "SELECT "+YUdsAcqRigTM.R_ANNO_ORD_PRD+","+YUdsAcqRigTM.R_NUM_ORD_PRD+" FROM "+YUdsAcqRigTM.TABLE_NAME 
			+ " WHERE "+YUdsAcqRigTM.ID_AZIENDA+"= ? "
			+ " AND "+YUdsAcqRigTM.ID_UDS+"= ? "
			+ " AND "+YUdsAcqRigTM.R_ANNO_ORD_PRD+"= ?  "
			+ " AND "+YUdsAcqRigTM.R_NUM_ORD_PRD+"= ? ";

	public static CachedStatement selectRigaOrdineCliente = new CachedStatement(SELECT_RIGA_ORDINE_CLIENTE);

	public YUdsAcqRig() {
		setIdRigaUds(new Integer(0));
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public YUdsAcqRig(YUdsAcquisto tes, BigDecimal qta) {
		this.setParent(tes);
		this.setIdAzienda(tes.getIdAzienda());
		this.setIdUds(tes.getIdUds());
		this.setQtaPrm(qta);
		this.setFornitore(tes.getIdFornitore());
	}

	public void assegnaDatiRigaUds(OrdineEsecutivo ordEsec) {
		this.setRArticolo(ordEsec.getIdArticolo());
		this.setRVersione(ordEsec.getIdVersione());
		this.setRConfig(ordEsec.getIdEsternoConfig());
		this.setRAnnoOrdPrd(ordEsec.getIdAnnoOrdine());
		this.setRNumOrdPrd(ordEsec.getIdNumeroOrdine());
		this.setRAnnoOrdAcq(ordEsec.getAnnoOrdineCliente());
		this.setRRigaOrdAcq(ordEsec.getRigaOrdineCliente());
		this.setRNumOrdAcq(ordEsec.getNumeroOrdineCliente());
		try {
			OrdineAcquistoRigaPrm ordAcqRig = (OrdineAcquistoRigaPrm) OrdineAcquistoRigaPrm.elementWithKey(OrdineAcquistoRigaPrm.class
					, KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(),ordEsec.getAnnoOrdineCliente(),ordEsec.getNumeroOrdineCliente(),String.valueOf(ordEsec.getRigaOrdineCliente())}), 0);
			if(ordAcqRig != null) {
				String idCausale = ordAcqRig.getIdCauRig() != null ? ordAcqRig.getIdCauRig() : "";
				this.setRCauOrdAcqRig(idCausale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * DSSOF3	70687	04/10/2022	Non faccio eliminare una riga che sia in statoEvasione != 0.
	 * @return
	 */
	public ErrorMessage checkStatoBeforeDelete() {
		ErrorMessage em = null;
		if(this.getStatoEvasione() == '1') {
			em = new ErrorMessage("YSOFTRE001", "Impossibile eliminare un UDS in stato 'COMPLETATO'");
		}else if(this.getStatoEvasione() == '2') {
			em = new ErrorMessage("YSOFTRE001","Impossibile eliminare un UDS versata a magazzino");
		}else if(this.getStatoEvasione() == '3') {
			em = new ErrorMessage("YSOFTRE001","Impossibile eliminare un UDS evasa");
		}
		return em;
	}

	public ErrorMessage checkDelete() {
		ErrorMessage em = checkStatoBeforeDelete();
		return em;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector checkAll(BaseComponentsCollection components) {
		Vector errors = super.checkAll(components);
		ErrorMessage em = controlloEsistenzaRigaOrdineCliente();
		if(em != null) {
			errors.add(em);
		}
		return errors;
	}

	/**
	 * In caso di inserimento riga, non lascio permettere all'utente di inserire una riga,
	 * con riferimenti dell'ordine di produzione e dunque dell'ordine cliente gia presenti nella tabella SOFTRE.YUDS_ACQ_RIG.
	 * @return
	 */
	protected ErrorMessage controlloEsistenzaRigaOrdineCliente() {
		ErrorMessage em = null;
		ResultSet rs = null;
		try {
			if(!this.isOnDB() && this.getRAnnoOrdPrd() != null && this.getRNumOrdPrd() != null) {
				Database db = ConnectionManager.getCurrentDatabase();
				PreparedStatement ps = selectRigaOrdineCliente.getStatement();
				db.setString(ps, 1, getIdAzienda());
				db.setString(ps, 2, getIdUds());
				db.setString(ps, 3, this.getRAnnoOrdPrd());
				db.setString(ps, 4, this.getRNumOrdPrd());
				rs = ps.executeQuery();
				if (rs.next()) {
					String anno = rs.getString(1);
					String numero = rs.getString(2);
					String c = KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(),anno,numero});
					em = new ErrorMessage("YSOFTRE001","Nella uds e' gia presente una riga con ordine PSO: "+c);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
			}catch (Throwable t) {
				t.printStackTrace();
			}
		}
		return em;
	}

	public int save() throws SQLException {
		YUdsAcquisto uds = this.getParent();
		if (!isOnDB()) {
			ResultSet rs = null;
			int value = 1;
			Database db = ConnectionManager.getCurrentDatabase();
			synchronized(querySave){
				PreparedStatement ps = querySave.getStatement();
				db.setString(ps, 1, getIdAzienda());
				db.setString(ps, 2, getIdUds());
				rs = ps.executeQuery();
				if (rs.next())
					value = rs.getInt("MAXI_VAL");
				rs.close();
				setIdRigaUds(new Integer(value));
			}
		}
		if(this.getFornitore() == null && uds.getIdFornitore() != null)
			this.setFornitore(uds.getIdFornitore());
		return super.save();
	}

	public void rendiDefinitivaUdsAcquisto() {
		this.setStatoEvasione(YUdsAcquisto.GENERATO_DOCUMENTO);
	}

	/**
	 * @author Daniele Signoroni 18/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Si occupa di aggiornare gli stati e i riferimenti post cancellazione documento vendita.<br>
	 * </p>
	 * @param documentoVendita 
	 * @throws SQLException 
	 */
	public void aggiornaUdsPostCancellazioneDocumentoAcquisto(DocumentoAcquisto DocumentoAcquisto) throws SQLException {
		String docAcqKey = KeyHelper.buildObjectKey(new String[] {getIdAzienda(),getRAnnoDocAcq(),getRNumDocAcq()});
		if(docAcqKey.equals(DocumentoAcquisto.getKey())) {
			setRAnnoDocAcq(null);
			setRNumDocAcq(null);
			setRRigaDocAcq(null);
			setRRigaDetDocAcq(null);
		}
		setStatoEvasione(YUdsAcquisto.IN_CORSO);
		save();
	}

	public void aggiornaStatoUdsDaConvalidaDocumento(char stato) throws SQLException {
		setStatoEvasione(stato);
		save();
	}

}

