package it.caleido.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

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
}
