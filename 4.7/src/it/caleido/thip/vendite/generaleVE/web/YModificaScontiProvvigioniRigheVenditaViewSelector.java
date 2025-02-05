package it.caleido.thip.vendite.generaleVE.web;

import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.ViewSelectorDefault;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 05/02/2025
 * <br><br>
 * <b>71811    DSSOF3    05/02/2025</b>
 * <p></p>
 */

public class YModificaScontiProvvigioniRigheVenditaViewSelector extends ViewSelectorDefault {

	@Override
	public String getAfterSaveURL(BODataCollector boDC, ServletEnvironment se) {
		String ret = super.getAfterSaveURL(boDC, se);
		ret = "it/caleido/thip/vendite/generaleVE/YModificaScontiProvvigioniRigheVenditaAfterSave.jsp";
		return ret;
	}
}
