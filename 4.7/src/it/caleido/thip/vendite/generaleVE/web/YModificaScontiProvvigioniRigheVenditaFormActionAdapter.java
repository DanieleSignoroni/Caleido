package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.FormActionAdapter;

import it.caleido.thip.vendite.offerteCliente.web.YOffertaClienteRigaPrmGridActionAdapter;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 03/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    03/02/2025</b>
 * <p></p>
 */

public class YModificaScontiProvvigioniRigheVenditaFormActionAdapter extends FormActionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void otherActions(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		String action = getStringParameter(se.getRequest(), ACTION);
		if(action.equals(YOffertaClienteRigaPrmGridActionAdapter.MODIFICA_MASSIVA_SCONTI_PROVVIGIONI)) {
			save(cadc, se);
		}else {
			super.otherActions(cadc, se);
		}
	}
}
