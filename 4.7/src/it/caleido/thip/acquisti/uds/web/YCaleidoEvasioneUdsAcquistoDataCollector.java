package it.caleido.thip.acquisti.uds.web;

import com.thera.thermfw.collector.EnhBOComponentManager;

import it.thera.thip.base.documenti.TipoGestione;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb;

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

public class YCaleidoEvasioneUdsAcquistoDataCollector extends DocumentoDataCollector{

	@Override
	protected String getNavigatoreName() {
		return "it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb";
	}

	@Override
	public void impostaSecondoCausale() {

	}

	@Override
	public void updateHandlingModeOnComponentManagers() {
		super.updateHandlingModeOnComponentManagers();
		EnhBOComponentManager p0 = (EnhBOComponentManager)getComponentManager("IdFornitore");
		impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
	}

	@Override
	public DocumentoNavigazioneWeb getNavigatore() {
		return super.getNavigatore();
	}

}
