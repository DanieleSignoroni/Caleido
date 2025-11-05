package it.softre.thip.acquisti.uds.web;

import java.util.Vector;

import com.thera.thermfw.collector.BaseComponentManager;
import com.thera.thermfw.collector.EnhBOComponentManager;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.base.documenti.TipoGestione;
import it.thera.thip.base.documenti.web.DocumentoDataCollector;
import it.thera.thip.cs.ThipRelatedBoValidationGroup;

/**
 * 
 * @author Daniele Signoroni	20/03/2023
 * <br><br>
 * <b>70687	DSSOF3	20/03/2023</b>	<br>Prima stesura.
 * 									<br>Aggiunto data collector per gestire l'oggetto di business.
 *
 */

public class YUdsAcquistoDataCollector extends DocumentoDataCollector{

	@Override
	protected String getNavigatoreName() {
		return "it.softre.thip.acquisti.uds.web.YUdsAcquistoNavigazioneWeb";
	}

	@Override
	public void impostaSecondoCausale() {

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Vector runCheckAll() {
		YUdsAcquisto bo = (YUdsAcquisto) getBo();
		if(bo.getSeqValore() == null || bo.getSeqValore().compareTo(0) == 0) {
			getComponent("SeqValore").getComponentManager().setCheckKeys(false);
			getComponent("SeqValore").getComponentManager().setRunCheckRelatedBo(false);;
			getComponent("SeqValore").getComponentManager().setCheckMode(BaseComponentManager.CHECK_NEVER);
			((ThipRelatedBoValidationGroup)getValidationGroup("ValoreVariabileCfg")).setCheckMode(BaseComponentManager.CHECK_NEVER);;
		}
		return super.runCheckAll();
	}

	/**
	 * Se ho generato il documento di vendita lo stato va in read-only e cosi anche il fornitore.
	 */
	@Override
	public void updateHandlingModeOnComponentManagers() {
		EnhBOComponentManager p0 = null;
		char p1 = ' ';
		YUdsAcquisto uds = (YUdsAcquisto) this.getBo();
		if(uds.getStatoEvasione() == YUdsAcquisto.GENERATO_DOCUMENTO) {
			p0 = (EnhBOComponentManager) getComponentManager("StatoEvasione");
			p1 = TipoGestione.SOLO_VISUALIZZ;
			impostaHandlingModeOnComponentManagers(p0, p1);
			p0 = (EnhBOComponentManager) getComponentManager("IdFornitore");
			p1 = TipoGestione.SOLO_VISUALIZZ;
			impostaHandlingModeOnComponentManagers(p0, p1);
		}
		super.updateHandlingModeOnComponentManagers();
	}
}
