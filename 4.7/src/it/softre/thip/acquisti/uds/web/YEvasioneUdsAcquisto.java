package it.softre.thip.acquisti.uds.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorList;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.ServletEnvironment;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.base.documenti.web.DocumentoCambiaJSP;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoDatiSessione;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 08/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    08/11/2025  DSSOF3   Prima stesura
 */

public class YEvasioneUdsAcquisto extends DocumentoCambiaJSP{

	private static final long serialVersionUID = 1L;

	public static final String EVADI_UDS = "EVADI_UDS";

	public static final String CONFERMA_EVASIONE = "CONFERMA_EVASIONE";

	public static final String REFRESH_GRID = "REFRESH_GRID";

	@Override
	public void eseguiAzioneSpecifica(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC,
			DocumentoDatiSessione datiSessione) throws ServletException, IOException, SQLException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS)) {
			evadiUds(se,cadc,docBODC,datiSessione);
		}else if(azione.equals(CONFERMA_EVASIONE)) {
			confermaEvasioneUds(se,cadc,docBODC,datiSessione);
		}else {
			super.eseguiAzioneSpecifica(se, cadc, docBODC, datiSessione);
		}
	}

	private void confermaEvasioneUds(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.setServletEnvironment(se);
		docBODC.setCheckWithDomain(true);
		docBODC.initialize(className, true, getCurrentLockType(se));
		setValues(cadc, docBODC, se);
		docBODC.setOnBORecursive(); //necessario per caricare gli attr
		YDatiSessioneEvasioneUdsAcquisto datiSessioneEvasione = (YDatiSessioneEvasioneUdsAcquisto) DocumentoDatiSessione.getDocumentoDatiSessione(se);
		String[] chiaviSel = datiSessioneEvasione.getChiaviSel();
		if(docBODC.check() == DocumentoDataCollector.OK) {
			it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto bo = (it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto) docBODC.getBo();
			if(chiaviSel != null && bo.getRFornitore() != null && bo.getRCauDocTes() != null && bo.getRSerie() != null) {
				Object[] ogg = (Object[]) it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto.evasioneUdsAcquisto(
						chiaviSel, 
						bo.getDataDocumento(), 
						bo.getRCauDocTes(), 
						bo.getRSerie(),
						bo.getRFornitore(),
						bo.getDataRifIntestatario(),
						bo.getNumeroRifIntestatario());
				ErrorList errori = (ErrorList)ogg[0];
				String key = (String) ogg[1];
				if(errori.getErrors().isEmpty()) {
					boolean daEstratto = datiSessioneEvasione.isDaEstratto();
					String url = "it/softre/thip/acquisti/uds/YAperturaDocAcq.jsp?ChiaveDocAcq="+key;
					url += "&DaEstratto="+(daEstratto == true ? "true" : "false")+"";
					se.sendRequest(getServletContext(), url, false);
				}else {
					se.addErrorMessages(errori.getErrors());
					se.sendRequest(getServletContext(), "com/thera/thermfw/common/ErrorListHandler.jsp", false);
				}
			}
		}else {
			se.addErrorMessages(docBODC.getErrorList().getErrors());
			se.sendRequest(getServletContext(), "com/thera/thermfw/common/ErrorListHandler.jsp", false);
		}
	}

	private void evadiUds(ServletEnvironment se, ClassADCollection cadc, DocumentoDataCollector docBODC, DocumentoDatiSessione datiSessione) throws ServletException, IOException {
		String className = se.getRequest().getParameter(CLASS_NAME);
		docBODC.initialize(className, true, getCurrentLockType(se));
		String chiaviSel[] = (String[]) se.getRequest().getAttribute("ChiaviSelEvasioneUdsAcquisto");
		List<YUdsAcquisto> list = recuperaUdsAcquistoDaChiavi(chiaviSel);
		String fornitore = list.get(0).getIdFornitore();
		if(chiaviSel != null) {
			ErrorMessage em = checkCongruenzaFornitore(list);
			if(em != null) {
				se.addErrorMessage(em);
			}
			em = checkUdsEvase(list,getAzione(se));
			if(em != null) {
				se.addErrorMessage(em);
			}
			em = checkPresenzaRigheUds(list);
			if(em != null) {
				se.addErrorMessage(em);
			}
			if(se.isErrorListEmpity()) {
				it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto udsAcqBO = null;
				udsAcqBO = (it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto) docBODC.getBo();
				udsAcqBO.setRFornitore(fornitore);
				docBODC.setBo(udsAcqBO);
				YDatiSessioneEvasioneUdsAcquisto datiSessioneEvasione = (YDatiSessioneEvasioneUdsAcquisto) Factory.createObject(YDatiSessioneEvasioneUdsAcquisto.class);
				datiSessioneEvasione.setDocumentoBO(docBODC.getBo());
				datiSessioneEvasione.setNavigatore(docBODC.getNavigatore());
				datiSessioneEvasione.salvaInSessione(se);
				datiSessioneEvasione.setIdFornitore(fornitore);
				datiSessioneEvasione.setChiaviSel(chiaviSel);
				boolean daEstratto = (Boolean) se.getRequest().getAttribute("DaEstratto");
				datiSessioneEvasione.setDaEstratto(daEstratto);
				se.getRequest().setAttribute(DocumentoDataCollector.CARICA_DA_SESSIONE, "true");
				String url = "it/softre/thip/acquisti/uds/YEvasioneUdsAcquisto.jsp?Fornitore="+fornitore;
				String params = "&" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" +(String) se.getRequest().getAttribute(DocumentoDatiSessione.CHIAVE_DATI_SESSIONE);
				url += params;
				executeJSOpenAction(se, url, docBODC);
			}else {
				se.sendRequest(getServletContext(), "com/thera/thermfw/common/InfoAreaHandler.jsp", false);
			}
		}
	}

	public ErrorMessage checkPresenzaRigheUds(List<YUdsAcquisto> list) {
		for(YUdsAcquisto uds : list) {
			if(uds.getRigheUDSAcquisti().size() == 0) {
				return  new ErrorMessage("YSOFTRE001","L'UDS " + uds.getKey() + " non ha righe, non puo' essere evasa");
			}
		}
		return null;
	}

	public ErrorMessage checkCongruenzaFornitore(List<YUdsAcquisto> list) {
		if(list.size() == 0)
			return new ErrorMessage("BAS0000000");
		String firstForn = list.get(0).getIdFornitore();
		if(firstForn == null)
			return new ErrorMessage("BAS0000000");
		for(YUdsAcquisto uds : list) {
			if(uds.getIdFornitore() == null) {
				return new ErrorMessage("BAS0000000");
			}else if(!uds.getIdFornitore().equals(firstForn)){
				return new ErrorMessage("YSOFTRE001","Non e' possibile evadere UDS con fornitori diversi");
			}
		}
		return null;
	}

	public List<YUdsAcquisto> recuperaUdsAcquistoDaChiavi(String[] keys){
		List<YUdsAcquisto> list = new ArrayList<YUdsAcquisto>();
		for(String key : keys) {
			try {
				list.add((YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class, key, PersistentObject.NO_LOCK));
			} catch (SQLException e) {
				e.printStackTrace(Trace.excStream);
			}
		}
		return list;
	}

	public ErrorMessage checkUdsEvase(List<YUdsAcquisto> list, String azione) {
		for(YUdsAcquisto uds : list) {
			if(uds.getRAnnoDocAcq() != null && uds.getRNumDocAcq() != null) {
				return new ErrorMessage("YSOFTRE001","Non e' possibile evadere un UDS gia' evasa");
			}
		}
		return null;
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
			if(( (Boolean) se.getRequest().getAttribute("DaEstratto") != null &&
					((Boolean) se.getRequest().getAttribute("DaEstratto")).booleanValue() == false) && se.isErrorListEmpity()) {
				out.println("parent.runAction('" + REFRESH_GRID + "','none','same','no');");
			}
			out.println("  </script>");
		}
		catch (Exception ex) {
			ex.printStackTrace(Trace.excStream);
		}
	}
}