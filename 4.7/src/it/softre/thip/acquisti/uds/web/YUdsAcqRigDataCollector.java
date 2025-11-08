package it.softre.thip.acquisti.uds.web;

import com.thera.thermfw.collector.EnhBOComponentManager;

import it.softre.thip.acquisti.uds.YUdsAcqRig;
import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.base.documenti.TipoGestione;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;

/**
 * 
 * @author Daniele Signoroni	20/03/2023
 * <br><br>
 * <b>70687	DSSOF3	20/03/2023</b>	<br>Prima stesura.
 * 									<br>Aggiunto data collector per gestire l'oggetto di business.
 *
 */

public class YUdsAcqRigDataCollector extends DocumentoDataCollector{

	@Override
	protected String getNavigatoreName() {
		return null;
	}

	@Override
	public void impostaSecondoCausale() {

	}

	/**
	 * Se ho generato il documento di vendita lo stato va in read-only e cosi anche il fornitore.
	 */
	@Override
	public void updateHandlingModeOnComponentManagers() {
		EnhBOComponentManager p0 = null;
		char p1 = ' ';
		YUdsAcqRig uds = (YUdsAcqRig) this.getBo();
		if(uds.getStatoEvasione() == YUdsAcquisto.GENERATO_DOCUMENTO) {
			p0 = (EnhBOComponentManager) getComponentManager("StatoEvasione");
			p1 = TipoGestione.SOLO_VISUALIZZ;
			impostaHandlingModeOnComponentManagers(p0, p1);
			p0 = (EnhBOComponentManager) getComponentManager("Fornitore");
			p1 = TipoGestione.SOLO_VISUALIZZ;
			impostaHandlingModeOnComponentManagers(p0, p1);
		}else if(uds.getStatoEvasione() == YUdsAcquisto.VERSATO_A_MAGAZZINO) {
			p0 = (EnhBOComponentManager) getComponentManager("RAnnoOrdAcq");
			impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
			p0 = (EnhBOComponentManager) getComponentManager("RNumOrdAcq");
			impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
			p0 = (EnhBOComponentManager) getComponentManager("RRigaOrdAcq");
			impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
			p0 = (EnhBOComponentManager) getComponentManager("RRigaDetOrdAcq");
			impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
		}
		super.updateHandlingModeOnComponentManagers();
	}
}
