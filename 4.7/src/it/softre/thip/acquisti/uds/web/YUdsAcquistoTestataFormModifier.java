package it.softre.thip.acquisti.uds.web;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.web.WebJSTypeList;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.base.documenti.web.DocumentoAbsFormModifier;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 05/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    05/11/2025  DSSOF3   Prima stesura
 */

public class YUdsAcquistoTestataFormModifier extends DocumentoAbsFormModifier {

	@Override
	public int getTipoJSP() {
		return 0;
	}

	public void writeHeadElements(JspWriter out) throws java.io.IOException
	{
		super.writeHeadElements(out);
		out.println(WebJSTypeList.getImportForJSLibrary("it/thera/thip/base/documenti/Documento.js", getServletEnvironment().getRequest()));
		out.println(WebJSTypeList.getImportForJSLibrary("it/thera/thip/base/comuniVenAcq/ComuniVenAcq.js", getServletEnvironment().getRequest()));

		YUdsAcquisto uds = (YUdsAcquisto) getBODataCollector().getBo();
		uds.setIdSchemaCfg("COLORE");
		uds.setIdVariabileCfg("COLORE");
		getBODataCollector().setOnBORecursive();
		getBODataCollector().loadAttValue();
	}

}
