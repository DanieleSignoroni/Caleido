package it.caleido.thip.base.articolo.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import it.caleido.thip.base.articolo.YArticoloDatiTecnici;
import it.thera.thip.base.articolo.web.ArticoloDatiTecniciFormModifier;

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
 * 72630    28/08/2026  DSSOF3   Prima stesura
 */

public class YArticoloDatiTecniciFormModifier extends ArticoloDatiTecniciFormModifier {

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		YArticoloDatiTecnici bo = (YArticoloDatiTecnici) getBODataCollector().getBo();
		if(bo.getVariabilePotenza() != null) {
			bo.setIdVariabilePW("POTENZA");
		}
		getBODataCollector().setBo(bo);
	}

	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		super.writeBodyEndElements(out);
		YArticoloDatiTecnici bo = (YArticoloDatiTecnici) getBODataCollector().getBo();
		if(bo.getVariabilePotenza() != null) {
			out.println("<script language='JavaScript1.2'>");
			out.println("document.forms[0].SequenzaValorePW.parentNode.parentNode.style.display = displayBlock;");
			out.println("document.forms[0].PotenzaWattElettrici.parentNode.parentNode.style.display = displayNone;");
			out.println("</script>");
		}
	}
}
