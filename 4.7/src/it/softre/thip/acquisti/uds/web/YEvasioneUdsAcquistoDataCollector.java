package it.softre.thip.acquisti.uds.web;

import java.util.Vector;

import com.thera.thermfw.collector.EnhBOComponentManager;

import it.thera.thip.base.documenti.TipoGestione;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb;

public class YEvasioneUdsAcquistoDataCollector extends DocumentoDataCollector{

	@Override
	protected String getNavigatoreName() {
		return "it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb";
	}

	@Override
	public DocumentoNavigazioneWeb getNavigatore() {
		return super.getNavigatore();
	}

	@Override
	public void impostaSecondoCausale() {
	}

	@Override
	public void updateHandlingModeOnComponentManagers() {
		super.updateHandlingModeOnComponentManagers();
		EnhBOComponentManager p0 = (EnhBOComponentManager)getComponentManager("RFornitore");
		impostaHandlingModeOnComponentManagers(p0, TipoGestione.SOLO_VISUALIZZ);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Vector runCheckAll() {
		Vector errors = super.runCheckAll();
		return errors;
	}
}
