package it.caleido.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.web.WebJSTypeList;

import it.softre.thip.acquisti.uds.web.YUdsAcquistoFormModifier;

public class YCaleidoUdsAcquistoEstrattoFormModifier extends YUdsAcquistoFormModifier{

	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		out.println(WebJSTypeList.getImportForJSLibrary("it/caleido/thip/acquisti/uds/YCaleidoUdsAcquistoEstratto.js", getServletEnvironment().getRequest()));
	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
		out.println("<script>");
		//out.println("addBtnEvasionePers();");
		out.println("</script>");
	}

	@Override
	public void writePulsantiBarraAzioniPers(JspWriter out) throws IOException {
		super.writePulsantiBarraAzioniPers(out);
		out.println("<td nowrap=\"true\" height=\"0\">");
		out.println("<button name=\"EvasPers\" id=\"EvasPers\" onclick=\"evasionePersCaleido()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnEvasionePersCaleido") + "\">");
		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + "it/caleido/thip/acquisti/uds/img/EvaOffForDir48.gif" + "\" >");
		out.println("</button>");
		out.println("</td>");
	}
}
