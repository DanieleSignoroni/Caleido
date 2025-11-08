package it.softre.thip.acquisti.uds;

import com.thera.thermfw.persist.*;

import it.softre.thip.base.uds.YTipoUds;
import it.thera.thip.acquisti.documentoAC.DocumentoAcquisto;
import it.thera.thip.base.azienda.Azienda;

import java.sql.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.*;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura.
 */

public class YUdsAcquisto extends YUdsAcquistoPO {

	public static final char IN_CORSO = '0';

	public static final char COMPLETATO = '1';

	public static final char VERSATO_A_MAGAZZINO = '2';

	public static final char GENERATO_DOCUMENTO = '3';

	protected boolean iCopia;

	public boolean isCopia() {
		return iCopia;
	}

	public void setCopia(boolean iCopia) {
		this.iCopia = iCopia;
	}

	public YUdsAcquisto() {
		setIdUds("0");
		this.setDataUds(TimeUtils.getCurrentDate());
	}

	@Override
	public int save() throws SQLException {
		if(!isOnDB()) {
			this.setIdUds(getNewProgressivo());
			compilaMisureNuovo();
		}
		return super.save();
	}

	public void compilaMisureNuovo() throws SQLException {
		YTipoUds tipoUds = (YTipoUds)
				YTipoUds.elementWithKey(YTipoUds.class, 
						KeyHelper.buildObjectKey(new String[] {Azienda.getAziendaCorrente(),this.getRIdUdsPadre()}), 0);
		if(tipoUds != null) {
			if(this.getPesoContenitore() == null)
				this.setPesoContenitore(tipoUds.getPeso() != null ? tipoUds.getPeso() : null);
			if(this.getAltezza() == null)
				this.setAltezza(tipoUds.getAltezza() != null ? tipoUds.getAltezza() : null);
			if(this.getVolume() == null)
				this.setVolume(tipoUds.getVolume() != null ? tipoUds.getVolume() : null);
			if(this.getLarghezza() == null)
				this.setLarghezza(tipoUds.getLarghezza() != null ? tipoUds.getLarghezza() : null);
			if(this.getAltezza() == null)
				this.setLunghezza(tipoUds.getLunghezza() != null ? tipoUds.getLunghezza() : null);
		}
	}


	/**
	 * DSSOF3	70687	04/10/2022	Non faccio eliminare una uds che sia in statoEvasione != 0.
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

	protected static String getNewProgressivo() {
		String newProg = "1";
		try {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			String prefAnno = String.valueOf(year).substring(2);
			String select = "";
			if(ConnectionManager.getCurrentDatabase() instanceof SQLServerJTDSNoUnicodeDatabase) {
				select = "SELECT COALESCE(MAX(SUBSTRING(ID_UDS,3,9))+1,1) " + 
						"FROM SOFTRE.YUDS_ACQ_TES " + 
						"WHERE SUBSTRING(ID_UDS,1,2) = '"+prefAnno+"' ";
			}else if(ConnectionManager.getCurrentDatabase() instanceof DB2NetDatabase) {
				select = "SELECT COALESCE(MAX(SUBSTRING(ID_UDS,3,9,CODEUNITS32))+1,1) " + 
						"FROM SOFTRE.YUDS_ACQ_TES " + 
						"WHERE SUBSTRING(ID_UDS,1,2,CODEUNITS32) = '"+prefAnno+"' ";
			}
			CachedStatement cs = new CachedStatement(select);
			ResultSet rs = cs.executeQuery();
			if(rs.next()) {
				newProg = rs.getString(1).trim();
			}
			while(newProg.length() < 7) {
				newProg = 0 + newProg;
			}
			newProg = prefAnno + newProg;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return newProg;
	}

	public void convalidaUdsAcquisto(String note) {
		this.setNote(note);
		this.setStatoEvasione('1');
	}

	public void regressioneUdsAcquisto() {
		this.setStatoEvasione('0');
	}

	public void rendiDefinitivaUdsAcquisto() {
		this.setStatoEvasione('3');
	}

	public static YUdsAcquisto getUdsAcquisto(String key) {
		YUdsAcquisto ret = null;
		try {
			ret = (YUdsAcquisto)
					YUdsAcquisto.elementWithKey(YUdsAcquisto.class, key, 0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @author Daniele Signoroni 18/04/2024
	 * <p>
	 * Prima stesura.<br>
	 * Si occupa di aggiornare l'uds di vendita dopo la cancellazione del documento di vendita.<br>
	 * Aggiorna anche le righe.<br>
	 * La save e' a cura dell'utilizzatore.<br>
	 * </p>
	 * @param yDocumentoVendita 
	 */
	@SuppressWarnings("unchecked")
	public void aggiornaUdsPostCancellazioneDocumentoAcquisto(DocumentoAcquisto DocumentoAcquisto) {
		String docAcqKey = KeyHelper.buildObjectKey(new String[] {getIdAzienda(),getRAnnoDocAcq(),getRNumDocAcq()});
		if(docAcqKey.equals(DocumentoAcquisto.getKey())) {
			setRAnnoDocAcq(null);
			setRNumDocAcq(null);
		}
		setStatoEvasione(IN_CORSO);
		List<YUdsAcqRig> righe;
		try {
			righe = getRigheUDSAcquisti();

			Iterator<YUdsAcqRig> iterRows = righe.iterator();
			while (iterRows.hasNext()) {
				YUdsAcqRig riga = (YUdsAcqRig) iterRows.next();
				riga.aggiornaUdsPostCancellazioneDocumentoAcquisto(DocumentoAcquisto);
			}

		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
	}

	@SuppressWarnings("unchecked")
	public void aggiornaStatoUdsDaConvalidaDocumento(char stato, DocumentoAcquisto DocumentoAcquisto) {
		setStatoEvasione(stato);
		List<YUdsAcqRig> righe;
		try {
			righe = getRigheUDSAcquisti();

			Iterator<YUdsAcqRig> iterRows = righe.iterator();
			while (iterRows.hasNext()) {
				YUdsAcqRig riga = (YUdsAcqRig) iterRows.next();
				riga.aggiornaStatoUdsDaConvalidaDocumento(stato);
			}

		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
	}

}