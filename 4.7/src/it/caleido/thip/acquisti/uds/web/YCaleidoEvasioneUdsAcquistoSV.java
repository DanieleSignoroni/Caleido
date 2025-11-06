package it.caleido.thip.acquisti.uds.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.web.ServletEnvironment;

import it.caleido.thip.acquisti.uds.YCaleidoEvasioneUdsAcquisto;
import it.softre.thip.acquisti.uds.YAlertUdsAcquisto;
import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.softre.thip.vendite.uds.web.YUdsVenditaGridActionAdapter;
import it.thera.thip.base.documenti.web.DocumentoCambiaJSP;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;
import it.thera.thip.cs.ColonneFiltri;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 06/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    06/11/2025  DSSOF3   Prima stesura
 */

public class YCaleidoEvasioneUdsAcquistoSV extends DocumentoCambiaJSP{

	private static final long serialVersionUID = 1L;

	protected static final String EVADI_UDS_CALEIDO = "EVADI_UDS_CALEIDO";

	protected static final String CONFERMA_EVASIONE_UDS_CALEIDO = "CONFERMA_EVASIONE_UDS_CALEIDO";

	public static final String REFRESH_GRID = "REFRESH_GRID"; 

	@Override
	public void eseguiAzioneSpecifica(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException, SQLException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS_CALEIDO) || azione.equals(YUdsVenditaGridActionAdapter.TRASFERISCI_UDS)) {
			evadiUds(se,cadc,docBODC,datiSessione);
		}else if(azione.equals(CONFERMA_EVASIONE_UDS_CALEIDO)) {
			confermaEvasioneUds(se,cadc,docBODC,datiSessione);
		}else {
			super.eseguiAzioneSpecifica(se, cadc, docBODC, datiSessione);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void confermaEvasioneUds(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		List errors = new ArrayList();
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.initialize(className, true, getCurrentLockType(se));
		setValues(cadc, docBODC, se);
		YCaleidoEvasioneUdsAcquisto bo = (YCaleidoEvasioneUdsAcquisto) docBODC.getBo();
		String keysSel = "";
		java.util.Iterator lstChiaviSelectedIte = ( java.util.Iterator)Arrays.asList(datiSessione.getValoriChiaviDocumento()).iterator();
		while (lstChiaviSelectedIte.hasNext()) {
			String nextKey = (String)lstChiaviSelectedIte.next();
			keysSel += nextKey;
			if(lstChiaviSelectedIte.hasNext())
				keysSel += ColonneFiltri.LISTA_SEP;
		}
		bo.setChiaviSelezionati(keysSel);
		docBODC.setAutoCommit(false);
		if (docBODC.save(true) != BODataCollector.OK) {
			errors.addAll(docBODC.getErrorList().getErrors());
		}
		if(errors.isEmpty()) {
			boolean daEstratto = datiSessione.isSmartMode();
			String url = "it/caleido/thip/acquisti/uds/YApriDocumenti.jsp?ChiaveOrdAcq="+bo.getChiaviSelezionati();
			url += "&DaEstratto="+(daEstratto == true ? "true" : "false")+"";
			se.sendRequest(getServletContext(), url, false);
		}else {
			se.addErrorMessages(errors);
			se.sendRequest(getServletContext(), "com/thera/thermfw/common/ErrorListHandler.jsp", false);
		}
	}

	private void evadiUds(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.initialize(className, true, getCurrentLockType(se));
		String chiaviSel[] = (String[]) se.getRequest().getAttribute("ChiaviSelEvasioneUdsAcquistoCaleido");
		String fornitore = null;
		if(chiaviSel != null) {
			fornitore = getFornitore(chiaviSel[0]);
			boolean isOk = YAlertUdsAcquisto.checkFornitore(chiaviSel);
			if(isOk) {
				ArrayList<String> chiaviUdsEvase = YAlertUdsAcquisto.chiaviUdsEvase(chiaviSel);
				if(!chiaviUdsEvase.isEmpty()) {
					ErrorMessage em = new ErrorMessage("YSOFTRE001","Le seguenti UDS sono già state evase " + getMsgEvasione(chiaviUdsEvase));
					se.addErrorMessage(em);
				}
			}else {
				ErrorMessage em = new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS con clienti diversi");
				se.addErrorMessage(em);
			}
			if(fornitore == null) {
				ErrorMessage em = new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS senza cliente");
				se.addErrorMessage(em);
			}
			checkChiavi(chiaviSel,se);
		}else {
			ErrorMessage em = new ErrorMessage("YSOFTRE001","Non e' stata selezionata nessuna UDS");
			se.addErrorMessage(em);
		}
		if(se.isErrorListEmpity()) {
			YCaleidoEvasioneUdsAcquisto bo = null;
			bo = (YCaleidoEvasioneUdsAcquisto) docBODC.getBo();
			bo.setIdFornitore(fornitore);
			docBODC.setBo(bo);
			datiSessione = (DocumentoDatiSessione) Factory.createObject(DocumentoDatiSessione.class);
			datiSessione.setDocumentoBO(docBODC.getBo());
			datiSessione.setNavigatore(docBODC.getNavigatore());
			datiSessione.setValoriChiaviDocumento(chiaviSel);
			boolean daEstratto = (Boolean) se.getRequest().getAttribute("DaEstratto");
			datiSessione.setSmartMode(daEstratto); //uso questo attributo per sapere se e' da estratto o no, per non fare la classe Y
			datiSessione.salvaInSessione(se);
			se.getRequest().setAttribute(DocumentoDataCollector.CARICA_DA_SESSIONE, "true");
			String url = "it/caleido/thip/acquisti/uds/YCaleidoEvasioneUdsAcquisto.jsp?Fornitore="+fornitore;
			String params = "&" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" +(String) se.getRequest().getAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE);
			url += params;
			executeJSOpenAction(se, url, docBODC);
		}else {
			se.sendRequest(getServletContext(), "com/thera/thermfw/common/InfoAreaHandler.jsp", false);
		}
	}

	public void executeJSOpenAction(ServletEnvironment se, String url, DocumentoDataCollector docBODC) {
		try {
			PrintWriter out = se.getResponse().getWriter();
			out.println("  <script language=\'JavaScript1.2\'>");
			String initialActionAdapter = getStringParameter(se.getRequest(), "thInitialActionAdapter");
			if(initialActionAdapter != null) {
				out.println("    var errViewObj = window.parent.eval(window.parent.errorsViewName);");
				out.println("    errViewObj.setMessage(null);");
				out.println("    parent.enableFormActions();");
			}
			else {
				out.println("window.parent.ErVwinfoarea.clearDisplay();");
				out.println("window.parent.enableGridActions();");
			}
			if (url.startsWith("/"))
				url = url.substring(1);
			out.println("    var url = '" + se.getWebApplicationPath() + url + "'");
			out.println("    var winFeature = 'width=800, height=700, resizable=yes';");
			out.println("    var winName = '" + String.valueOf(System.currentTimeMillis()) + "';");
			out.println("    var winrUrl = window.open(url, winName, winFeature);");
			/*if(( (Boolean) se.getRequest().getAttribute("DaEstratto") != null &&
					((Boolean) se.getRequest().getAttribute("DaEstratto")).booleanValue() == false) && se.isErrorListEmpity()) {
				out.println("parent.runAction('" + REFRESH_GRID + "','none','same','no');");
			}*/
			out.println("  </script>");
		}
		catch (Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
	}

	public String getMsgEvasione(ArrayList<String> chiaviGiaEvase) {
		String ret = "";
		for(int i = 0; i < chiaviGiaEvase.size(); i++) {
			ret += " - " + chiaviGiaEvase.get(i) + " \n";
		}
		return ret;
	}

	private boolean checkChiavi(String[] chiaviSel, ServletEnvironment se) {
		try {
			for(int i = 0 ; i < chiaviSel.length; i++) {
				YUdsAcquisto udsAcq = (YUdsAcquisto)
						YUdsAcquisto.elementWithKey(YUdsAcquisto.class, chiaviSel[i], 0);
				if(udsAcq.getRigheUDSAcquisti().size() == 0) {
					ErrorMessage em = new ErrorMessage("YSOFTRE001","L'UDS " + chiaviSel[i] + " non ha righe, non puo' essere evasa");
					se.addErrorMessage(em);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return false;
	}

	public String getFornitore(String chiave) {
		try {
			YUdsAcquisto uds = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, chiave,0);
			if(uds != null) {
				return uds.getIdFornitore();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
