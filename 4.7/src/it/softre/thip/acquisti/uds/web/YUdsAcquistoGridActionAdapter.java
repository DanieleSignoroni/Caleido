package it.softre.thip.acquisti.uds.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.softre.thip.uds.YUtilsStampaEtichettaUds;
import it.thera.thip.base.documenti.web.DocumentoGridActionAdapter;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura, serlvet per la griglia Uds Acquisto.
 */

public class YUdsAcquistoGridActionAdapter extends DocumentoGridActionAdapter  {

	private static final long serialVersionUID = 1L;

	protected static final String UDS_SECONDO_LIVELLO = "UDS_SECONDO_LIVELLO";
	protected static String UDS_SECONDO_LIVELLO_IMG = "it/thera/thip/vendite/pickingPacking/images/GestionePacking.gif";
	protected static String UDS_SECONDO_LIVELLO_RES = "it/softre/thip/acquisti/uds/resources/YudsAcquisto";

	protected static final String SPOSTAMENTO_UDS = "SPOSTAMENTO_UDS";
	protected static String SPOSTAMENTO_UDS_IMG = "it/thera/thip/vendite/pickingPacking/images/PrelevaTutto.gif";
	protected static String SPOSTAMENTO_UDS_RES= "it/softre/thip/acquisti/uds/resources/YudsAcquisto";

	protected static final String EVADI_UDS = "EVADI_UDS";
	protected static String EVADI_UDS_IMG = "it/thera/thip/base/comuniVenAcq/image/EvaOrdAcqDir.gif";
	protected static String EVADI_UDS_RES= "it/softre/thip/acquisti/uds/resources/YudsAcquisto";

	protected static final String UDS_ACQUISTO_SEMPLIFICATA = "UDS_ACQUISTO_SEMPLIFICATA";
	protected static String UDS_ACQUISTO_SEMPLIFICATA_IMG = "it/softre/thip/vendite/uds/img/possible.gif";
	protected static String UDS_ACQUISTO_SEMPLIFICATA_RES = "it/softre/thip/acquisti/uds/resources/YudsAcquisto";

	protected static final String STAMPA_ETICHETTA_UDS = "STAMPA_ETICHETTA_UDS";
	protected static String STAMPA_ETICHETTA_UDS_IMG = "it/softre/thip/vendite/uds/img/StampaEtic.gif";
	protected static String STAMPA_ETICHETTA_UDS_RES = "it/softre/thip/acquisti/uds/resources/YudsAcquisto";

	protected static int i = 0;

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton udsSecondoLvl = new WebToolBarButton(UDS_SECONDO_LIVELLO, "action_submit", "new"
				, "no", UDS_SECONDO_LIVELLO_RES, UDS_SECONDO_LIVELLO, UDS_SECONDO_LIVELLO_IMG, UDS_SECONDO_LIVELLO, "multiple_action", false);
		toolBar.addButton("tbbtTicklerPullDown", udsSecondoLvl);

		WebToolBarButton spostamentoUds = new WebToolBarButton(SPOSTAMENTO_UDS, "action_submit", "new"
				, "no", SPOSTAMENTO_UDS_RES, SPOSTAMENTO_UDS, SPOSTAMENTO_UDS_IMG, SPOSTAMENTO_UDS, "", false);
		toolBar.addButton(UDS_SECONDO_LIVELLO, spostamentoUds);

		WebToolBarButton evasioneUds = new WebToolBarButton(EVADI_UDS, "action_submit", "infoArea"
				, "no", EVADI_UDS_RES, EVADI_UDS, EVADI_UDS_IMG, EVADI_UDS, "multiple_action", false);
		toolBar.addButton(SPOSTAMENTO_UDS, evasioneUds);

		WebToolBarButton udsAcquistoSemplificata = new WebToolBarButton(UDS_ACQUISTO_SEMPLIFICATA, "action_submit", "new"
				, "no", UDS_ACQUISTO_SEMPLIFICATA_RES, UDS_ACQUISTO_SEMPLIFICATA, UDS_ACQUISTO_SEMPLIFICATA_IMG, UDS_ACQUISTO_SEMPLIFICATA, "", false);
		toolBar.addButton(UDS_ACQUISTO_SEMPLIFICATA, udsAcquistoSemplificata);


		WebToolBarButton stampaEtichettaUds = new WebToolBarButton(STAMPA_ETICHETTA_UDS, "action_submit", "new"
				, "no", STAMPA_ETICHETTA_UDS_RES, STAMPA_ETICHETTA_UDS, STAMPA_ETICHETTA_UDS_IMG, STAMPA_ETICHETTA_UDS, "single", false);
		toolBar.addButton(STAMPA_ETICHETTA_UDS, stampaEtichettaUds);
	}

	/**
	 * DSSOF3	70687	04/10/2022	Copia personalizzata.
	 */
	@Override
	public void processAction(ServletEnvironment se) throws ServletException, IOException {
		String action = se.getRequest().getParameter(ACTION) != null ? se.getRequest().getParameter(ACTION) : "";
		if(action.equals("COPY")) {
			String key = se.getRequest().getParameter(OBJECT_KEY);
			se.sendRequest(getServletContext(), "it/softre/thip/acquisti/uds/YCopiaUdsAcquisto.jsp?Key="+key, false);
		}
		super.processAction(se);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		super.otherActions(cadc, se);
		String action = se.getRequest().getParameter(ACTION) != null ? se.getRequest().getParameter(ACTION) : "";
		if(action.equals(UDS_SECONDO_LIVELLO)) {
			String[] chiaviSel = (String[]) (se.getRequest().getParameterValues(OBJECT_KEY));
			if(chiaviSel != null) {
				se.getSession().setAttribute("chiaviUdsAcquisto", chiaviSel);
				//String  url = se.getServletPath() + "it.softre.thip.acquisti.uds.web.YUdsAcquistoBancaleFormActionAdapter";	
				String url = "it/softre/thip/acquisti/uds/YUdsSecondoLivello.jsp";
				se.sendRequest(getServletContext(), url, false);
			}
		}

		if(action.equals(SPOSTAMENTO_UDS)) {
			String url = "it/softre/thip/acquisti/uds/YSpostamentoUdsAcquisto.jsp?";
			se.sendRequest(getServletContext(), url, true);
		}

		if(action.equals(EVADI_UDS)) {
			lanciaEvasioneUds(se,cadc);
		}
		if(action.equals(UDS_ACQUISTO_SEMPLIFICATA)) {
			//String url = se.getServletPath().substring(1) + "com.thera.thermfw.web.servlet.Execute?JSP=it/softre/thip/acquisti/uds/YUdsAcquistoSemplificata.jsp?Mode=NEW&EntityId=YUdsAcqSemp&JSPName=it/softre/thip/acquisti/uds/YUdsAcquistoSemplificata.jsp";
			String url = "it/softre/thip/acquisti/uds/YUdsAcquistoSemplificata.jsp";
			url += "?JSPName=it/softre/thip/acquisti/uds/YUdsAcquistoSemplificata.jsp";
			se.sendRequest(getServletContext(), url, false);
		}
		if(action.equals(STAMPA_ETICHETTA_UDS)) {
			ServletOutputStream out = se.getResponse().getOutputStream();
			out.println("<script language='JavaScript1.2'>");
			String key = se.getRequest().getParameter(OBJECT_KEY);
			YUtilsStampaEtichettaUds.stampaEtichettaUdsAcquisto(se, out, key, i);
			out.println("window.close();");
			out.println("</script>");
		}
	}

	@Override
	protected void deleteObject(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		se.sendRequest(getServletContext(),se.getServletPath() + "com.thera.thermfw.web.servlet.Delete", false);
	}

	public void lanciaEvasioneUds(ServletEnvironment se, ClassADCollection cadc) throws ServletException, IOException {
		String[] chiaviSel = se.getRequest().getParameterValues(OBJECT_KEY);
		se.getRequest().setAttribute("ChiaviSelEvasioneUdsAcquisto", chiaviSel);
		String url = "it.softre.thip.acquisti.uds.web.YEvasioneUdsAcquisto?thAction="+EVADI_UDS;
		url += "&thClassName=YEvasioneUdsAcquisto";
		se.getRequest().setAttribute("DaEstratto", false);
		se.sendRequest(getServletContext(), se.getServletPath() + url, false);
	}

	public void printAlert(ServletOutputStream out, ArrayList<String> listaChiavi) throws IOException {
		String msg = "Le seguenti UDS sono già state evase \n";
		for(int i = 0; i < listaChiavi.size(); i++){
			msg += listaChiavi.get(i) + "\n";
		}
		out.println("window.alert('" + WebElement.formatStringForHTML(msg) + "');");
	}

	public ArrayList<String> chiaviUdsEvase(String[] chiaviSel) {
		ArrayList<String> key = new ArrayList<String>();
		try {
			for(int i = 0; i < chiaviSel.length; i++) {
				YUdsAcquisto riga = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class,chiaviSel[i], 0);
				if(riga != null) {
					if(riga.getStatoEvasione() == '3'){
						key.add(chiaviSel[i]);
					}
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * DSSOF3	70687	04/10/2022	Verifico che tutte le uds selezionate appartengano allo stesso cliente.
	 * @param chiaviSel
	 * @return
	 */
	public boolean checkCliente(String[] chiaviSel) {
		boolean isOk = true;
		try {
			List<String> listaCli = new ArrayList<String>();
			for(int i = 0; i < chiaviSel.length; i++) {
				YUdsAcquisto testata = (YUdsAcquisto) YUdsAcquisto.elementWithKey(YUdsAcquisto.class,chiaviSel[i], 0);
				listaCli.add(testata.getIdFornitore());
			}
			isOk = verifyAllEqualUsingFrequency(listaCli);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return isOk;
	}

	public  boolean verifyAllEqualUsingFrequency(List<String> list) {
		return list.isEmpty() || Collections.frequency(list, list.get(0)) == list.size();
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
