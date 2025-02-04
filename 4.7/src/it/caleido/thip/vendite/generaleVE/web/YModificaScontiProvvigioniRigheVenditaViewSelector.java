package it.caleido.thip.vendite.generaleVE.web;

import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.web.ServletEnvironment;
import com.thera.thermfw.web.ViewSelectorDefault;

public class YModificaScontiProvvigioniRigheVenditaViewSelector extends ViewSelectorDefault {

	@Override
	public String getAfterSaveURL(BODataCollector boDC, ServletEnvironment se) {
		String ret = super.getAfterSaveURL(boDC, se);
		ret = "it/caleido/thip/vendite/generaleVE/YModificaScontiProvvigioniRigheVenditaAfterSave.jsp";
		return ret;
	}
}
