package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;

import javax.servlet.ServletException;

import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.servlet.Save;

import it.caleido.thip.vendite.generaleVE.YModificaConfigurazioneRigaVendita;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/02/2025
 * <br><br>
 * <b>71811    DSSOF3    05/02/2025</b>
 * <p></p>
 */

public class YModificaConfigurazioneRigaVenditaSave extends Save {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterProcessAction(BODataCollector boDC, ServletEnvironment se) throws ServletException, IOException {
		if(se.isErrorListEmpity()) {
			YModificaConfigurazioneRigaVendita bo = (YModificaConfigurazioneRigaVendita) boDC.getBo();
			se.getRequest().setAttribute("ChiaveSelezionato", bo.getChiaviSelezionati());
			se.getRequest().setAttribute(CLASS_NAME, bo.getClassName());
			String url = "/it/caleido/thip/vendite/generaleVE/YApriRigaVenditaPostCambioConfigurazione.jsp";
			se.sendRequest(getServletContext(), url, false);
		}else {
			super.afterProcessAction(boDC, se);
		}
	}

}
