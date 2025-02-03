package it.caleido.thip.vendite.offerteCliente.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.WebToolBar;
import com.thera.thermfw.web.WebToolBarButton;

import it.caleido.thip.vendite.generaleVE.YModificaScontiProvvigioniRigheVendita;
import it.thera.thip.vendite.offerteCliente.web.OffertaClienteRigaPrmGridActionAdapter;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 03/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    03/02/2025</b>
 * <p></p>
 */

public class YOffertaClienteRigaPrmGridActionAdapter extends OffertaClienteRigaPrmGridActionAdapter {

	private static final long serialVersionUID = 1L;

	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI = "MOD_MASS_SCH_PROVV";
	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_IMG = "it/caleido/thip/vendite/generaleVE/img/TrasfCostiComm.svg";
	public static final String MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_RES = "it.caleido.thip.vendite.generaleVE.resources.YModificaScontiProvvigioniRigheVendita";

	@Override
	public void modifyToolBar(WebToolBar toolBar) {
		WebToolBarButton sbloccaArticolo = new WebToolBarButton(MODIFICA_MASSIVA_SCONTI_PROVVIGIONI, "action_submit", "new", "no",
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_RES,
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI,
				MODIFICA_MASSIVA_SCONTI_PROVVIGIONI_IMG, MODIFICA_MASSIVA_SCONTI_PROVVIGIONI, "multipleSelSingleWindow", false);
		toolBar.addButton(sbloccaArticolo);
		super.modifyToolBar(toolBar);
	}

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String action = se.getRequest().getParameter(ACTION) != null ? se.getRequest().getParameter(ACTION) : "";
		if (action.equals(MODIFICA_MASSIVA_SCONTI_PROVVIGIONI)) {
			modificaMassivaSocntiProvvigioni(se);
		}else {
			super.otherActions(cadc, se);
		}
	}

	protected void modificaMassivaSocntiProvvigioni(ServletEnvironment se) throws ServletException, IOException {
		String className = getStringParameter(se.getRequest(), CLASS_NAME);
		boolean isAutorizzato = YModificaScontiProvvigioniRigheVendita.isAutorizzatoTask(className, MODIFICA_MASSIVA_SCONTI_PROVVIGIONI);
		isAutorizzato = true;
		if(isAutorizzato) {
			se.sendRequest(getServletContext(), "it/caleido/thip/vendite/generaleVE/YModificaScontiProvvigioniRigheVendita.jsp", false);
		}else {
			se.addErrorMessage(new ErrorMessage("BAS0000078",""));
		}
	}
	
}
