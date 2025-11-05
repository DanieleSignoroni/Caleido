package it.softre.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.thera.thip.base.documenti.web.DocumentoFormActionAdapter;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura, aggiunto il bottone 'Passa a righe' nella form YUdsAcquistoNuovo				
 *								Aggiunto il bottone crea righe per ora NON UTILIZZATO.
 */

public class YUdsAcquistoFormActionAdapter extends DocumentoFormActionAdapter {

	private static final long serialVersionUID = 1L;

	protected static final String TO_RIGHE = "TO_RIGHE";
	protected static String TO_RIGHE_IMG= "it/thera/thip/base/documenti/ToRighe.gif";
	protected static String TO_RIGHE_RES= "it/softre/thip/acquisti/uds/resources/YUdsAcquisto";

	protected static final String CREA_RIGHE_UDS = "CREA_RIGHE_UDS";
	protected static String CREA_RIGHE_UDS_IMG = "it/thera/thip/base/commessa/images/apri_dettaglio_riga.gif";
	protected static String CREA_RIGHE_UDS_RES= "it/softre/thip/acquisti/uds/resources/YUdsAcquisto";

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		/*String action = se.getRequest().getParameter(ACTION);
		if(action != null && action.equals(TO_RIGHE)) {
			String  url = se.getServletPath() + "it.softre.thip.acquisti.uds.web.YUdsAcquistoPassaARighe";			
			se.sendRequest(getServletContext(),  url , true);
		}
		if(action != null && action.equals(CREA_RIGHE_UDS)) {
			String idUds = se.getRequest().getParameter("IdUds");
			if(idUds != null) {
				String url = "it/softre/thip/acquisti/uds/YCreaRigheUds.jsp?IdUds="+idUds;
				se.sendRequest(getServletContext(), url, true);
			}
		}*/
		super.otherActions(cadc, se);
	}

	@SuppressWarnings("unused")
	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton toRighe = new WebToolBarButton(TO_RIGHE, "action_submit", "errorsFrame", "no",TO_RIGHE_RES, TO_RIGHE,TO_RIGHE_IMG, TO_RIGHE, "multiple", false);
		//toolBar.addButton("tbbtProprieta", toRighe); 
		WebToolBarButton creaRigheUds = new WebToolBarButton(CREA_RIGHE_UDS, "action_submit", "new"
				, "no", CREA_RIGHE_UDS_RES, CREA_RIGHE_UDS, CREA_RIGHE_UDS_IMG, CREA_RIGHE_UDS, "multiple", false);
		//toolBar.addButton("tbbtTO_RIGHE", creaRigheUds);
	}

}