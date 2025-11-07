package it.softre.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;


import it.softre.thip.acquisti.uds.YEvasioneUdsAcquisto;
import it.thera.thip.acquisti.generaleAC.CausaleDocumentoTestataAcq;
import it.thera.thip.acquisti.generaleAC.CausaleOrdineTestataAcq;
import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 07/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    07/11/2025  DSSOF3   Prima stesura
 */

public class YEvasioneUdsAcquistoFormModifier extends DocumentoAbsFormModifier{

	@Override
	public int getTipoJSP() {
		return 0;
	}

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		YEvasioneUdsAcquistoDataCollector bodc = (YEvasioneUdsAcquistoDataCollector) this.getBODataCollector();
		bodc.updateHandlingModeOnComponentManagers();
		YEvasioneUdsAcquisto evas = (YEvasioneUdsAcquisto) getBODataCollector().getBo();
		YEvasioneUdsAcquisto eva = (YEvasioneUdsAcquisto) this.iDatiSessione.getDocumentoBO();
		evas.setFornitore(eva.getFornitore());
		getBODataCollector().setBo(evas);
		getBODataCollector().setOnBORecursive();
	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
	}
}