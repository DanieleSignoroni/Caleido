package it.softre.thip.acquisti.uds.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.web.WebJSTypeList;
import com.thera.thermfw.web.WebPullDownButton;
import com.thera.thermfw.web.WebPullDownButtonAction;

import it.softre.thip.acquisti.uds.YUdsAcquisto;
import it.thera.thip.base.documenti.web.DocumentoEstrattoFormModifier;

/**
 * 
 * @author DSSOF3	
 *	DSSOF3	70687	04/10/2022	Prima stesura, introdotta l'apertura della griglia righe,
 *								logica estrazione bottoni regressione e convalida.
 */

public class YUdsAcquistoFormModifier extends DocumentoEstrattoFormModifier  {
	
	public static final String RES_PERS = "it/softre/thip/acquisti/uds/resources/YUdsAcquisto";

	public void writeHeadElements(JspWriter out) throws IOException {
		super.writeHeadElements(out);
		out.println("<script language='JavaScript' type='text/javascript'>");
		//out.println("window.resizeTo(940,640)");
		out.println("</script>");
	}

	public void writeBodyStartElements(JspWriter out) throws IOException {
		//impostaURLGrigliaRighe(out);
		super.writeBodyStartElements(out);
	}

	public void writeFormStartElements(JspWriter out) throws IOException {
		super.writeFormStartElements(out);
		getBODataCollector().setOnBORecursive();
	}
	
	@Override
	public void writePulsantiBarraAzioniPers(JspWriter out) throws IOException {
		super.writePulsantiBarraAzioniPers(out);

		YUdsAcquisto uds = (YUdsAcquisto) getBODataCollector().getBo();

		if(uds.getDocumentoAcquisto() != null) {
			out.println("<td nowrap=\"true\" height=\"0\">");
			out.println("<button name=\"Regressione\" id=\"Regressione\" onclick=\"regressioneYUdsAcquisto)\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnRegressione") + "\">");
			out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"" + getIconaBarraAzioniStandard("it/thera/thip/base/documenti/images/Regressione.gif") + "\" >");
			out.println("</button>");
			out.println("</td>");
		}

		out.println("<td nowrap=\"true\" height=\"30px\">\n");
		WebPullDownButton pdb = new WebPullDownButton("thEvasioneDiretta", null, null, "it/caleido/thip/acquisti/uds/img/EvaOffForDir48.gif", "evasioneUds()()", null);
		pdb.setParent(getWebForm());
		pdb.setWidth(widthBtnBarraAzioniStandard);
		pdb.setHeight("30px");
		pdb.setImageWidth(widthImgBarraAzioniStandard);
		pdb.setImageHeight("24px");     
		pdb.setText(ResourceLoader.getString(RES_PERS, "btnEvasione"));
		WebPullDownButtonAction actionEvasDiretta = new WebPullDownButtonAction("thEvasioneDiretta", null, null, null, "evasioneUds()", null);
		pdb.addAction(actionEvasDiretta);
		actionEvasDiretta.setText(ResourceLoader.getString(RES_PERS, "btnEvasione"));
		pdb.write(out);

		out.println("<td nowrap=\"true\" height=\"0\">");
		out.println("<button name=\"StampaEtichetta\" id=\"StampaEtichetta\" onclick=\"stampaEtichetta()\" style=\"width:" + widthBtnBarraAzioniStandard + ";height:30px;\" type=\"button\" title=\"" + ResourceLoader.getString(RES_PERS, "btnStampaEtichetta") + "\">");
		out.println("<img border=\"0\" width=\"" + widthImgBarraAzioniStandard + "\" height=\"24px\" src=\"it/softre/thip/vendite/uds/img/StampaEtic.gif\" >");
		out.println("</button>");
		out.println("</td>");
	}


	public void writeFormEndElements(JspWriter out) throws IOException {
		super.writeFormEndElements(out);
		out.println("<script language='JavaScript' type='text/javascript'>");
		out.println("setReadOnly(document.getElementById('IdFornitore'))");
		out.println("setReadOnly(document.getElementById('RelFornitore$RagioneSociale'))");
		out.println("setReadOnly(document.getElementById('Note'))");
		out.println("document.getElementById('thRelFornitoreSearchBut').disabled = true");
		//out.println("document.getElementById('thRelFornitoreEditBut').disabled = true");
		out.println("</script>");
	}

	public void writeBodyEndElements(JspWriter out) throws IOException {
		out.println(
				"<script language=\"JavaScript1.2\" type=\"text/javascript\">" +
						// "parent.document.title = document.forms[0].document.title;" // Commented on fix 12090
						"parent.document.title = document.title;" +
						"</script>"
				);
		//Fix 12807 inizio
		out.println(WebJSTypeList.getImportForJSLibrary("it/thera/thip/base/documenti/DocumentoEstrattoEnd.js", getServletEnvironment().getRequest()));
		//Fix 12807 fine
		//Fix 21346 inizio
		initLabelSaldoRiapri(out);//Fix 21346
		//Fix 21346 fine

		//Fix 40412 - inizio
		// out.println("<script language=\"JavaScript1.2\" type=\"text/javascript\">");
		//out.println("getScadenze()");	     //45281
		//Fix 41181 - inizio
		//if (PersDatiGen.getCurrentPersDatiGen().getGesDocDgt()) 
		//	 out.println("getDocDgt()");
		//Fix 41181 - fine
		// out.println("</script>");
	}

}