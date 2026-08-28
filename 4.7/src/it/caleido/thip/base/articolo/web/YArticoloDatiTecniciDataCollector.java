package it.caleido.thip.base.articolo.web;

import java.util.Vector;

import it.caleido.thip.base.articolo.YArticoloDatiTecnici;
import it.thera.thip.base.articolo.web.SezioneDataCollector;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 28/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    28/08/2026  DSSOF3   Prima stesura
 */

public class YArticoloDatiTecniciDataCollector extends SezioneDataCollector {

	@SuppressWarnings("rawtypes")
	@Override
	protected Vector runCheckAll() {
		YArticoloDatiTecnici bo = (YArticoloDatiTecnici) getBo();
		if(bo.getVariabilePotenza() != null) {
			getComponent("SequenzaValorePW").getComponentManager().setMandatory(true);
		}
		return super.runCheckAll();
	}
}
