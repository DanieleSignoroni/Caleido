package it.caleido.thip.vendite.ordineVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;
import com.thera.thermfw.web.servlet.GridActionAdapter;

import it.thera.thip.vendite.ordineVE.web.OrdineVenditaRigaPrmGridActionAdapter;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 03/02/2025
 * <br><br>
 * <b>71811    DSSOF3    03/02/2025</b>
 * <p></p>
 */

public class YOrdineVenditaRigaPrmGridActionAdapter extends OrdineVenditaRigaPrmGridActionAdapter {

	private static final long serialVersionUID = 1L;

	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI = "MOD_MASS_SCH_PROVV";
	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_IMG = "it/caleido/thip/vendite/generaleVE/img/TrasfCostiComm.svg";
	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_RES = "it.caleido.thip.vendite.generaleVE.resources.YModificaScontiProvvigioniRigheVendita";

	public static final String MODIFICA_CONFIGURAZIONE = "MODIFICA_CONFIGURAZIONE";
	public static final String MODIFICA_CONFIGURAZIONE_IMG = "com/thera/thermfw/util/images/sql_export.svg";

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		WebToolBarButton sbloccaArticolo = new WebToolBarButton(MODIFICA_MASSIVA_SCONTI_PROVVIGIONI, "action_submit", "new", "no",
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_RES,
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI,
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_IMG, MODIFICA_MASSIVA_SCONTI_PROVVIGIONI, "multipleSelSingleWindow", false);
		toolBar.addButton(sbloccaArticolo);

		WebToolBarButton modificaConfigurazione = new WebToolBarButton(MODIFICA_CONFIGURAZIONE, "action_submit", "new", "no",
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_RES,
				MODIFICA_CONFIGURAZIONE,
				MODIFICA_CONFIGURAZIONE_IMG, MODIFICA_CONFIGURAZIONE, "single", false);
		toolBar.addButton(modificaConfigurazione);
		super.modifyToolBar(toolBar);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String action = se.getRequest().getParameter(ACTION) != null ? se.getRequest().getParameter(ACTION) : "";
		if (action.equals(MODIFICA_MASSIVA_SCONTI_PROVVIGIONI)) {
			modificaMassivaScontiProvvigioni(se);
		}else if(action.equals(MODIFICA_CONFIGURAZIONE)) {
			modificaConfigurazione(cadc,se);
		}else {
			super.otherActions(cadc, se);
		}
	}

	protected void modificaConfigurazione(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		//String className = getStringParameter(se.getRequest(), CLASS_NAME);
		boolean isAutorizzato = true;//YModificaScontiProvvigioniRigheVendita.isAutorizzatoTask(className, MODIFICA_CONFIGURAZIONE);
		String url = null;
		if(isAutorizzato) {
			url = "it/caleido/thip/vendite/generaleVE/YModificaConfigurazioneRigaVendita.jsp?Mode=NEW";
			url += "&InitialActionAdapter="+GridActionAdapter.class.getName();
		}else {
			se.addErrorMessage(new ErrorMessage("BAS0000020"));
			url = "it/caleido/thip/base/utils/ShowErrorMessage.jsp";
		}
		se.sendRequest(getServletContext(), url, false);
	}

	protected void modificaMassivaScontiProvvigioni(ServletEnvironment se) throws ServletException, IOException {
		//String className = getStringParameter(se.getRequest(), CLASS_NAME);
		boolean isAutorizzato = true;//YModificaScontiProvvigioniRigheVendita.isAutorizzatoTask(className, MODIFICA_MASSIVA_SCONTI_PROVVIGIONI);
		String url = null;
		if(isAutorizzato) {
			url = "it/caleido/thip/vendite/generaleVE/YModificaScontiProvvigioniRigheVendita.jsp?Mode=NEW";
			url += "&InitialActionAdapter="+GridActionAdapter.class.getName();
		}else {
			se.addErrorMessage(new ErrorMessage("BAS0000020"));
			url = "it/caleido/thip/base/utils/ShowErrorMessage.jsp";
		}
		se.sendRequest(getServletContext(), url, false);
	}

}
