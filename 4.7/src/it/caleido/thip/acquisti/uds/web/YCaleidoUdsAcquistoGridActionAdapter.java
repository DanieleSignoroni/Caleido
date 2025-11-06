package it.caleido.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.softre.thip.acquisti.uds.web.YUdsAcquistoGridActionAdapter;

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

public class YCaleidoUdsAcquistoGridActionAdapter extends YUdsAcquistoGridActionAdapter{

	private static final long serialVersionUID = 1L;

	protected static final String EVADI_UDS_CALEIDO = "EVADI_UDS_CALEIDO";
	
	protected static String EVADI_UDS_CALEIDO_IMG = "it/caleido/thip/acquisti/uds/img/EvaOffForDir48.gif";
	
	protected static String EVADI_UDS_CALEIDO_RES = "it/caleido/thip/acquisti/uds/resources/YCaleidoUdsAcquisto";

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		super.modifyToolBar(toolBar);
		WebToolBarButton evadiUdsCaleido = new WebToolBarButton(EVADI_UDS_CALEIDO, "action_submit", "infoArea"
				, "no", EVADI_UDS_CALEIDO_RES, EVADI_UDS_CALEIDO, EVADI_UDS_CALEIDO_IMG, EVADI_UDS_CALEIDO, "multiple_action", false);
		toolBar.addButton(STAMPA_ETICHETTA_UDS, evadiUdsCaleido);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String azione = getAzione(se);
		if(azione.equals(EVADI_UDS_CALEIDO)) {
			lanciaEvasioneUdsCaleido(cadc,se);
		}else {
			super.otherActions(cadc, se);
		}
	}

	protected void lanciaEvasioneUdsCaleido(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String[] chiaviSel = se.getRequest().getParameterValues(OBJECT_KEY);
		se.getRequest().setAttribute("ChiaviSelEvasioneUdsAcquistoCaleido", chiaviSel);
		String url = "it.caleido.thip.acquisti.uds.web.YCaleidoEvasioneUdsAcquistoSV?thAction="+EVADI_UDS_CALEIDO;
		url += "&thClassName=YCaleidoEvasioneUdsAcquis";
		se.getRequest().setAttribute("DaEstratto", false);
		se.sendRequest(getServletContext(), se.getServletPath() + url, false);
	}

	protected String getAzione(ServletEnvironment se) {
		return getStringParameter(se.getRequest(), "thAction").toUpperCase();
	}
}
