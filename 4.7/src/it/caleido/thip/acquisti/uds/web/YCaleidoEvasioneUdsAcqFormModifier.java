package it.caleido.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import it.caleido.thip.acquisti.uds.YCaleidoEvasioneUdsAcquisto;
import it.thera.thip.acquisti.generaleAC.CausaleOrdineTestataAcq;
import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;

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

public class YCaleidoEvasioneUdsAcqFormModifier extends DocumentoAbsFormModifier{

	@Override
	public int getTipoJSP() {
		return 0;
	}

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		YCaleidoEvasioneUdsAcquistoDataCollector bodc = (YCaleidoEvasioneUdsAcquistoDataCollector) this.getBODataCollector();
		bodc.updateHandlingModeOnComponentManagers();
		YCaleidoEvasioneUdsAcquisto evas = (YCaleidoEvasioneUdsAcquisto) getBODataCollector().getBo();
		YCaleidoEvasioneUdsAcquisto eva = (YCaleidoEvasioneUdsAcquisto) this.iDatiSessione.getDocumentoBO();
		CausaleOrdineTestataAcq cauDef = eva.getFornitore().getCausaleOrdineAcquisto();
		if(cauDef != null) {
			evas.setCausaleOrdAcq(cauDef);
		}
		evas.setFornitore(eva.getFornitore());
		getBODataCollector().setBo(evas);
		getBODataCollector().setOnBORecursive();
	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
	}
}