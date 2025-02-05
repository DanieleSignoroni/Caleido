package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.FormActionAdapter;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    05/02/2025</b>
 * <p></p>
 */

public class YModificaConfigurazioneRigaVenditaFormActionAdapter extends FormActionAdapter {

	private static final long serialVersionUID = 1L;

	@Override
	protected void save(ClassADCollection cadc, ServletEnvironment se) throws ServletException, IOException {
		//super.save(cadc, se);
		se.sendRequest(getServletContext(), se.getServletPath() + "it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRigaVenditaSave", false);
	}
}
