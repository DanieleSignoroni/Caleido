package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import org.w3c.dom.Node;

import com.thera.thermfw.ad.ClassAD;
import com.thera.thermfw.ad.ClassADCollection;
import com.thera.thermfw.ad.ClassADCollectionManager;
import com.thera.thermfw.ad.ClassRD;
import com.thera.thermfw.ad.SimpleClassAD;
import com.thera.thermfw.base.ResourceLoader;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.base.Utils;
import com.thera.thermfw.collector.BODataCollector;
import com.thera.thermfw.collector.BaseComponentManager;
import com.thera.thermfw.collector.EnhBOComponentManager;
import com.thera.thermfw.persist.Factory;
import com.thera.thermfw.type.EnumerationType;
import com.thera.thermfw.type.NumberType;
import com.thera.thermfw.type.Type;
import com.thera.thermfw.web.StyleDefinition;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebForm;
import com.thera.thermfw.web.WebFormForInternalRowForm;
import com.thera.thermfw.web.WebGeneratorElement;
import com.thera.thermfw.web.WebMultiSearchForm;
import com.thera.thermfw.web.WebTextInput;
import com.thera.thermfw.web.WebTextInputForSearch;

import it.caleido.thip.vendite.generaleVE.YModificaConfigurazioneRigaVendita;
import it.thera.thip.base.profilo.UtenteAzienda;
import it.thera.thip.cs.DescrizioneInLingua;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.SezioneConfigurazione;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;
import it.thera.thip.datiTecnici.configuratore.web.WebMenuConfigurazione;

/**
 * Form interna copiata praticamente dallo standard che mi mostra tutte le sezioni di una configurazione con le sue variabili e i suoi valori.<br>
 * A differenza dello standard questa mostra in 1 sola form tutte le sezioni con i valori.<br></br>
 * Ogni sezione e' una <code>table</code>
 * <h1>Softre Solutions</h1> <br>
 * 
 * @author Daniele Signoroni 04/02/2025 <br>
 *         <br>
 *         <b>71811 DSSOF3 04/02/2025</b>
 *         <p>
 *         </p>
 */

public class YModificaConfigurazioneRowForm extends WebElement implements WebGeneratorElement {

	@Override
	public WebGeneratorElement getResolver() {
		return null;
	}

	@Override
	public Node resolve() {
		return null;
	}

	@Override
	public Node resolveTFML() {
		return null;
	}

	protected String immagineButId;

	@SuppressWarnings("rawtypes")
	@Override
	public void write(JspWriter out) throws IOException {
		try {
			YModificaConfigurazioneRigaVendita bo = (YModificaConfigurazioneRigaVendita) getOwnerForm()
					.getBODataCollector().getBo();
			Configurazione configurazione = bo.getConfigurazione();
			if(configurazione != null) {
				for (Iterator sezioni = configurazione.getSchemaCfg().getSezioni().iterator(); sezioni.hasNext();) {
					SezioneConfigurazione sezione = (SezioneConfigurazione) sezioni.next();
					out.println("<tr>");
					out.println("<td>");
					out.println("<table name='sezione' id='"+sezione.getIdSezioneCfg()+"'>");
					writeSezione(out, sezione);
					out.println("</table>");
					out.println("</td>");
					out.println("</tr>");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	protected void writeSezione(JspWriter out, SezioneConfigurazione sezione) throws IOException {
		writeSezioneLabel(out, sezione);
		List variabili = sezione.getVariabili();
		for (int i = 0; i < variabili.size(); i++) {
			VariabileSchemaCfg variabileCfg = (VariabileSchemaCfg) variabili.get(i);
			writeVariabile(out, variabileCfg, i);
		}
	}

	protected void writeVariabile(JspWriter out, VariabileSchemaCfg variabileCfg, int i) throws IOException {
		out.println("<tr>");
		writeIconVariabile(out, variabileCfg);
		writeDecrizioneVariabile(out, variabileCfg);
		writeMultiSearchValoreVariabile(out, variabileCfg, i);
		writeValore(out, variabileCfg);
		writePrimoSecondoValore(out, variabileCfg);
		writeIconValore(out, variabileCfg);
		writeJavaScript(out, variabileCfg);
		out.println("</tr>");
	}

	public void writeMultiSearchValoreVariabile(JspWriter out, VariabileSchemaCfg variabileCfg, int i) throws java.io.IOException {
		if (variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.INTERNA) 
			out.println("<td nowrap='true'>");
		else 
			out.println("<td nowrap='true' style='display:none'>"); 
		ValoreMultiSearchForm valoreMultiSearchForm = getValoreMultiSearchForm(variabileCfg);
		valoreMultiSearchForm.setParent(this.getParent());
		valoreMultiSearchForm.write(out);
		out.println("</td>");
	}

	public void writeJavaScript(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		out.println("<script>");
		//Fix 11994 inizio
		if (variabileCfg.getObbligVariabile())
			out.println("eval(\"document.forms[0].\" + idFromName[\"" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione.Descrizione\"]).style.background = mCo;");
		else //Fix 15062
			out.println("eval(\"document.forms[0].\" + idFromName[\"" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione.Descrizione\"]).style.background = sCo;"); //Fix 15062
		if(getOwnerForm().getMode() == WebForm.SHOW ||
				variabileCfg.getEsponiInDialogo() == VariabileSchemaCfg.ESP_IN_DIALOGO_SI_SOLO_LETTURA || variabileCfg.getIdVariabileConfig().equals("FASCIA")) {//Fix 12013 //Fix 12084
			//out.println("eval('document.forms[0].' + idFromName['"+getIdVarCfgXRowForm(variabileCfg)+".Descrizione.Descrizione']).readonly = true;");//30331
			//out.println("eval('document.forms[0].' + idFromName['"+getIdVarCfgXRowForm(variabileCfg)+".Descrizione.Descrizione']).readonly = false;");//30331//33417
			out.println("eval('document.forms[0].' + idFromName['"+getIdVarCfgXRowForm(variabileCfg)+".Descrizione.Descrizione']).readOnly = true;");//30331 //33417
			out.println("eval('document.forms[0].' + idFromName['"+getIdVarCfgXRowForm(variabileCfg)+".Descrizione.Descrizione']).style.background = bCo;"); //Fix 12084 //Fix 12139
			out.println("document.forms[0].th" + getIdVarCfgXRowForm(variabileCfg) + "SearchBut.disabled = true;");
			out.println("document.forms[0].th" + getIdVarCfgXRowForm(variabileCfg) + "ImmagineBut.disabled = true;");//Fix 34759
			//Fix 12013 inizio
			out.println("  var img = eval('document.forms[0].th" + getIdVarCfgXRowForm(variabileCfg) + "SearchButImg');");
			out.println("  img.src = \"it/thera/thip/datiTecnici/configuratore/images/ValoriSearchWinDis.gif\";");
			//Fix 12013 fine
			out.println("document.forms[0].Valore_"+getIdVarCfgXRowForm(variabileCfg)+".readOnly = true;");//30331//33417
			//out.println("document.forms[0].Valore_"+getIdVarCfgXRowForm(variabileCfg)+".readonly = false;");//30331//33417
			out.println("document.forms[0].Valore_"+getIdVarCfgXRowForm(variabileCfg)+".style.background = bCo;"); //Fix 12084 //Fix 12139
			if(variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.INTERNA){
				out.println("document.forms[0].thCatalogo" + getIdVarCfgXRowForm(variabileCfg) + "SearchBut.disabled = true;");
				//out.println("document.forms[0].thCatalogo" + getIdVarCfgXRowForm(variabileCfg) + "EditBut.disabled = true;");
				//out.println("eval('document.forms[0].' + idFromName['Catalogo" + getIdVarCfgXRowForm(variabileCfg) + ".DescrizioneEstesa']).readonly = true;");//30331
				//out.println("eval('document.forms[0].' + idFromName['Catalogo" + getIdVarCfgXRowForm(variabileCfg) + ".DescrizioneEstesa']).readonly = false;");//30331//33417
				out.println("eval('document.forms[0].' + idFromName['Catalogo" + getIdVarCfgXRowForm(variabileCfg) + ".DescrizioneEstesa']).readOnly = true;");//30331//33417
			}
			else{ //Esterna
				out.println("document.forms[0].thEsterna" + getIdVarCfgXRowForm(variabileCfg) + "SearchBut.disabled = true;");
				//out.println("document.forms[0].thEsterna" + getIdVarCfgXRowForm(variabileCfg) + "EditBut.disabled = true;");
				//out.println("eval('document.forms[0].' + idFromName['Esterna" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione']).readonly = true;");//30331
				//out.println("eval('document.forms[0].' + idFromName['Esterna" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione']).readonly = false;");//30331//33417
				out.println("eval('document.forms[0].' + idFromName['Esterna" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione']).readOnly = true;");//30331 //33417
			}
		}
		//Fix 11994 fine
		//Fix 15062 inizio
		if(variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.ESTERNA){
			out.println("  var estF1 = eval('document.forms[0].' + idFromName['Valore_" + getIdVarCfgXRowForm(variabileCfg) + "']);");
			out.println("  defineEvent(estF1, eventMOUSEDOWN , function() {});");
			out.println("  defineEvent(estF1, eventCLICK , function() {});");
			//30331 inizio
			out.println("  var estVar = eval('document.forms[0].' + idFromName['" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione.Descrizione']);");
			out.println("  estVar.value = '" +  ((ValoreVariabileCfg)(variabileCfg.getValori().get(0))).getDescrizione().getDescrizione()  + "';");
			out.println("  var estSeq = eval('document.forms[0].' + idFromName['SequenzaValore_" + getIdVarCfgXRowForm(variabileCfg) + "']);");
			out.println("  estSeq.value = '" +  ((ValoreVariabileCfg)(variabileCfg.getValori().get(0))).getSequenzaValore()  + "';");
			//30331 fine
			if (variabileCfg.getObbligVariabile())
				out.println("  estF1.style.background = mCo;");
			else
				out.println("  estF1.style.background = sCo;");
			out.println("  var estF2 = eval('document.forms[0].' + idFromName['Esterna" + getIdVarCfgXRowForm(variabileCfg) + ".Descrizione']);");
			out.println("  defineEvent(estF2, eventMOUSEDOWN , function() {});");
			out.println("  defineEvent(estF2, eventCLICK , function() {});");
			out.println("  estF2.style.background = sCo;");
		}
		//Fix 15062 fine
		out.println("</script>");
	}

	public void writeValore(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		if (variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.INTERNA) {
			out.println("<td nowrap='true'>");
			CatalogoMultiSearchForm catalogoMultiSearchForm = new CatalogoMultiSearchForm(variabileCfg);
			catalogoMultiSearchForm.setParent(this.getParent());
			catalogoMultiSearchForm.write(out);
			out.println("</td>");
			out.println("<script>");
			out.println("document.forms[0].thCatalogo"+getIdVarCfgXRowForm(variabileCfg)+"SearchBut.style.display = displayNone;"); //Fix 12139
			out.println("eval('document.forms[0].' + idFromName['Catalogo"+getIdVarCfgXRowForm(variabileCfg)+".DescrizioneEstesa']).style.display = displayNone;"); //Fix 12139
			out.println("</script>");
		}
		else {
			out.println("<td colspan='3' nowrap='true'>");
			EsternaMultiSearchForm esternaMultiSearchForm = new EsternaMultiSearchForm(variabileCfg);
			esternaMultiSearchForm.setParent(this.getParent());
			esternaMultiSearchForm.setOnSearchBack("variabileEsternaOnSearchBack()");		//Fix 24603
			esternaMultiSearchForm.setDecodeOnBlur(false); //30331
			esternaMultiSearchForm.write(out);
			out.println("</td>");
		}
		out.println("<script>");
		String idVar = getIdVarCfgXRowForm(variabileCfg);
		if (variabileCfg.getTipoVariabileCfg() == VariabileSchemaCfg.INTERO) {
			out.println("var TP$field1 = new IntegerType(document.forms[0].Valore_" + idVar + ",false,'" + variabileCfg.getDimVariabile() + "');");
			out.println("setupNF(document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ",TP$field1,document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ".value,NLSFld['Valore_" + getIdVarCfgXRowForm(variabileCfg) + "'],true,false,true,null,null);");
		}
		else if (variabileCfg.getTipoVariabileCfg() == VariabileSchemaCfg.DECIMALE) { //Fix12738
			out.println("var TP$field1 = new DecimalType(document.forms[0].Valore_" + idVar + ", false, " + variabileCfg.getDimVariabile() + ", " + variabileCfg.getNumDecimali() + ");");
			out.println("setupNF(document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ",TP$field1,document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ".value,NLSFld['Valore_" + getIdVarCfgXRowForm(variabileCfg) + "'],true,false,true,null,null);");
		}
		else if(variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.INTERNA){ //Fix 30881
			out.println("var TP$field1 = new StringType(document.forms[0].Valore_" + idVar + ",false,'" + variabileCfg.getDimVariabile() + "');");
			out.println("setupNF(document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ",TP$field1,document.forms[0].Valore_" + getIdVarCfgXRowForm(variabileCfg) + ".value,NLSFld['Valore_" + getIdVarCfgXRowForm(variabileCfg) + "'],true,false,true,null,null);");
		}
		out.println("eval(\"document.forms[0].Valore_" + idVar + "\").readOnly = false;");//30331
		out.println("eval(\"document.forms[0].Valore_" + idVar + "\").style.background = bCo;"); //Fix 12139
		out.println("</script>");
	}

	public void writePrimoSecondoValore(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		if (!variabileCfg.getIntervallo()) {
			out.println("<td width='140'>");
			out.println("</td>");
		}
		else if(variabileCfg.getNaturaVariabileCfg() == VariabileSchemaCfg.INTERNA){//30682
			out.println("<td>");
			out.println("<input class='inputClass' id='PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + "' maxlength='30' name='PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + "' size='18' type='text' readonly='true'>");//30682//Fix 38984 Modefica maxlength a 30
			out.println("</td>");
			out.println("<script>");
			if (variabileCfg.getTipoVariabileCfg() == VariabileSchemaCfg.INTERO) {
				out.println("var TP$field1 = new IntegerType(document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ",true,'" + variabileCfg.getDimVariabile() + "');");
				out.println("setupNF(document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ",TP$field1,document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ".value,NLSFld['PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + "'],true,false,true,null,null);");
			}
			if (variabileCfg.getTipoVariabileCfg() == VariabileSchemaCfg.DECIMALE) {
				out.println("var TP$field1 = new DecimalType(document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ", false, " + variabileCfg.getDimVariabile() + ", " + variabileCfg.getNumDecimali() + ");");
				out.println("setupNF(document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ",TP$field1,document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ".value,NLSFld['PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + "'],true,false,true,null,null);");
			}
			out.println("document.forms[0].PrimoValore_" + getIdVarCfgXRowForm(variabileCfg) + ".style.background = bCo;"); //Fix 12139
			out.println("</script>");
		}
	}

	public void writeIconValore(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		out.println("<td id='IconaValore_" + getIdVarCfgXRowForm(variabileCfg) + "TD' style='display:none'>");
		out.println("<img id='IconaValore_" + getIdVarCfgXRowForm(variabileCfg) + "' width=24 height=24 />");
		out.println("</td>");
	}

	public void writeIconVariabile(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		out.println("<td>");
		writeHiddenInputs(out, variabileCfg);
		String src = "it/thera/thip/datiTecnici/configuratore/images/VariabileCfg.gif";
		if (variabileCfg.getIcona() != null)
			src = variabileCfg.getIcona();
		String event = " onselectstart=\"return false;\""
				+ " onmouseover=\"this.style.border='1px buttonhighlight outset';\""
				+ " onmouseout=\"this.style.border='1px #C8D6E1 solid';\""
				+ " onmousedown=\"this.style.border='1px buttonhighlight inset';\""
				+ " onmouseup=\"this.style.border='1px buttonhighlight outset'; window.focus();\"";
		String style = "style=\"border: 1px #C8D6E1 solid;\"";
		out.println("<button id=\"iconaBtn\" type=\"button\" onclick=\"openVariabile('"
				+ variabileCfg.getIdVariabileConfig() + "','" + variabileCfg.getIdSezioneCfg() + "','"
				+ variabileCfg.getIdSchemaCfg() + "')\" " + event + style + ">" + "<img id='IconaVariabile_"
				+ getIdVarCfgXRowForm(variabileCfg) + "' src='" + src + "' width=16 height=16 />" + "</button>");
		out.println("</td>");
	}

	public void writeHiddenInputs(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		String value = variabileCfg.getIntervallo() ? "Y" : "N";
		out.println("<input type='hidden' id='Intervallo_" + getIdVarCfgXRowForm(variabileCfg) + "' name='Intervallo_"
				+ getIdVarCfgXRowForm(variabileCfg) + "' value = '" + value + "' >");
		out.println("<input type='hidden' id='ImmagineValore_" + getIdVarCfgXRowForm(variabileCfg)
		+ "' name='ImmagineValore_" + getIdVarCfgXRowForm(variabileCfg) + "' >");
	}

	public void writeDecrizioneVariabile(JspWriter out, VariabileSchemaCfg variabileCfg) throws java.io.IOException {
		out.println("<td width='40%'>");

		out.println("<label id='DescrVar_" + getIdVarCfgXRowForm(variabileCfg) + "' class='' for='DescrVar'>"
				+ getTextInLingua(variabileCfg.getDescrizione()) + "</label>");
		out.println("</td>");
	}

	public String getTextInLingua(DescrizioneInLingua descrLingua) {
		String descrizione = descrLingua.getHandler().getText("Descrizione",
				UtenteAzienda.getUtenteAziendaConnesso().getLanguage());
		return descrizione == null ? descrLingua.getDescrizione() : descrizione;
	}

	protected String getIdVarCfgXRowForm(VariabileSchemaCfg variabileCfg) {
		return "VarCfg" + String.valueOf(variabileCfg.getSeqFisica());
	}

	protected void writeSezioneLabel(JspWriter out, SezioneConfigurazione sez) throws IOException {
		out.println("<tr valign=\"top\">");
		out.println("<td colspan='3' style=\"background: #E4EAEF;\";>");
		String sezStr = ResourceLoader.getString(WebMenuConfigurazione.RES_FILE, "sezioneLbl") + " "
				+ sez.getIdSezioneCfg() + " - " + sez.getDescrizione().getDescrizione();
		out.println("<label class='' for='Sezione' style=\"color: blue; font-weight: bold;\">" + sezStr + "</label>");
		out.println("</td>");
		out.println("</tr>");
	}

	protected ClassAD getClassAD(String classADCollectionName, String attributeName)
	{
		try
		{
			ClassADCollection cad = ClassADCollectionManager.collectionWithName(Factory.getName(classADCollectionName, Factory.CLASS_HDR));
			return cad.getAttribute(attributeName);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public class ValoreMultiSearchForm extends WebMultiSearchForm {
		protected VariabileSchemaCfg variabileCfg;

		public ValoreMultiSearchForm(String classHdr, String classRD, boolean onlyId, boolean onlyDesc,
				boolean editButton, int numLeftFields, String idCols, String descCols) {
			this.classHdr = classHdr;
			this.classRD = classRD;
			this.onlyId = onlyId;
			this.onlyDesc = onlyDesc;
			this.editButton = editButton;
			this.numLeftFields = numLeftFields;
			this.idCols = idCols;
			this.descCols = descCols;

		}

		public ValoreMultiSearchForm(VariabileSchemaCfg variabileCfg) {
			this.variabileCfg = variabileCfg;
			this.idCols = "1"; //Fix 11994
			//this.descCols = "35"; //Fix 11994//30682
			this.descCols = "42"; //Fix 11994//30682
			this.extraRelatedClassAD = "IdVariabileConfig, PrimoValore, SecondoValore, Icona, ValoreEsterno, Immagine";
			this.onSearchBack = "fillValori()";
			this.editButton = true; //Fix 11994
		}

		//Fix 30485 inizio
		public ValoreMultiSearchForm(){

		}
		//Fix 30485 fine

		//32623 inizio
		public void writeButtons(JspWriter out) throws java.io.IOException{
			super.writeButtons(out);
			if(variabileCfg.isAbilitaImmagini())
				writeImmagineButton(out);
		}

		public void writeImmagineButton(JspWriter out) throws java.io.IOException {
			String classValue = " ";
			String buttonsClassType = getButtonsClassType();
			if(buttonsClassType.length() > 0)
				classValue = " class=\"" + buttonsClassType + "\"";
			immagineButId = "th" + getIdVarCfgXRowForm(variabileCfg) + "ImmagineBut";
			//String img = "it/thera/thip/datiTecnici/configuratore/images/NuovoValore.gif";//32788
			String img = "it/thera/thip/datiTecnici/configuratore/images/ricerca.gif";//32788
			String immagineButStr = "<button" + classValue + " type=\"button\" id = \"" + immagineButId + "\"" +
					getValoreImmagineButtonScript(getIdVarCfgXRowForm(variabileCfg), variabileCfg.getKey()) + ">" +
					"<img id = \"" + immagineButId + "Img\" border=\"0\" width=\"16\" height=\"16\" src=\"" + img + "\">" +
					"</button>";
			out.println(immagineButStr);
		}

		protected String getValoreImmagineButtonScript(String idVariabile, String variabileKey) {
			return " onClick = \"apriValoreImmagineAction('" + idVariabile + "', '" + variabileKey + "')" + "\" ";
		}
		//32623 fine
		public void writeLeftFields(JspWriter out) throws java.io.IOException {
			super.writeRightField(out);
		}

		public WebElement getParent() {
			return parent;
		}

		//Fix 11994 inizio
		public void writeSearchButton(JspWriter out) throws java.io.IOException {
			extractInternalButtonIdentifier();
			String classValue = " ";
			String buttonsClassType = getButtonsClassType();
			if(buttonsClassType.length() > 0)
				classValue = " class=\"" + buttonsClassType + "\"";
			String img = "it/thera/thip/datiTecnici/configuratore/images/ValoriSearchWin.gif";
			// Fix 30485 inizio
			searchButId = "th" + getIdVarCfgXRowForm(variabileCfg) + "SearchBut";
			editButId = "th" + getIdVarCfgXRowForm(variabileCfg) + "EditBut";
			// Fix 30485 fine
			String searchButStr = "<button" + classValue + " type=\"button\" id = \"" + searchButId + "\">" +
					"<img id = \"" + searchButId + "Img\" border=\"0\" width=\"16\" height=\"16\" src=\"" + img + "\">" + "</button>";
			out.println(searchButStr);
		}
		//Fix 11994 fine

		//30682 inizio
		public void writeEditButton(JspWriter out) throws java.io.IOException {
			extractInternalButtonIdentifier();
			String classValue = " ";
			String buttonsClassType = getButtonsClassType();
			if(buttonsClassType.length() > 0)
				classValue = " class=\"" + buttonsClassType + "\"";

			//Fix 10428 inizio
			//String editButStr = "<button" + classValue + " id = \"" + editButId + "\">" +
			String editButStr = "<button" + classValue + " type=\"button\" id = \"" + editButId + "\">" +
					//Fix 10428 fine
					"<img border=\"0\" width=\"16\" height=\"16\"  src=\"" + StyleDefinition.getInstance().getStylePath(EDIT_IMAGE) + "\">" + "</button>"; //12500
			out.println(editButStr);
		}
		//30682 fine

		public void writeRightField(JspWriter out) throws java.io.IOException {
			super.writeLeftFields(out);
		}

		protected void extractRDObject() {
			try {
				classRDObject = buildClassRD();
			}
			catch (Exception ex) {ex.printStackTrace(Trace.excStream);
			}
		}

		private ClassRD buildClassRD() throws SQLException {
			ClassRD relation = new ClassRD();
			relation.setClassName(Factory.getName("VariabileSchemaCfg", Factory.CLASS_HDR));
			//relation.setRelationNumber(relationNumber);
			relation.setRelationName(getIdVarCfgXRowForm(variabileCfg)); //Fix 11994
			relation.setRelatedClassName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
			relation.setOuterJoin(true);
			relation.setOnlySelect(false);
			relation.setXMLExportable(false);
			relation.setGridDisplayable(true);
			//relation.setDefaultSettingPosition(rsRel.getInt("DEF_SETTING_POS"));
			relation.setRelationNameNLS("Valore");
			relation.setColumnTitleNLS("Valore");
			relation.setLabelTextNLS("Valore");
			//Fix 24121 Inizio
			//relation.setSearchRelatedDecodAttribute(relation.getRelationHDR().getAttribute("Descrizione.Descrizione"));
			//relation.setAttributes(getAttributes());
			//relation.setRelatedAttributes(getRelatedAttributes());
			ClassADCollection rel = relation.getRelationHDR();
			relation.setSearchRelatedDecodAttribute(rel.getAttribute("Descrizione.Descrizione"));
			relation.setAttributes(getAttributes(rel));
			relation.setRelatedAttributes(getRelatedAttributes(rel));
			//Fix 24121 Fine
			return relation;
		}

		//public Vector getAttributes() { //Fix 24121 --Riga commentata
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Vector getAttributes(ClassADCollection rel) { //Fix 24121
			Vector vect = new Vector();
			if(rel == null) return vect; //Fix 24121
			try {
				//Fix 24121 Inizio
				//ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
				//ClassAD ad = classADCollection.getAttribute("IdAzienda");
				ClassAD ad1 = rel.getAttribute("IdAzienda");
				ClassAD ad2 = rel.getAttribute("IdSchemaCfg");
				ClassAD ad3 = rel.getAttribute("IdVariabileConfig");
				ClassAD ad4 = rel.getAttribute("SequenzaValore");
				//Fix 24121 Fine
				ClassAD idAzienda = new SimpleClassAD("", "IdAzienda", "IdAzienda", "IdAzienda", true, "", (short) 0, ad1.getType()); //Fix 24121
				ClassAD idSchemaCfg = new SimpleClassAD("", "IdSchemaCfg", "IdSchemaCfg", "IdSchemaCfg", true, "", (short) 1, ad2.getType()); //Fix 24121
				//Fix 11994 inizio
				ClassAD idVariabileConfig = new SimpleClassAD("", getIdVarCfgXRowForm(variabileCfg), getIdVarCfgXRowForm(variabileCfg), getIdVarCfgXRowForm(variabileCfg), true, "", (short) 1, ad3.getType()); //Fix 24121
				ClassAD sequenzaValore = new SimpleClassAD("", "SequenzaValore_" + getIdVarCfgXRowForm(variabileCfg), "SequenzaValore", "SequenzaValore", true, "", (short) 1, ad4.getType()); //Fix 24121
				//Fix 11994 fine
				vect.add(idAzienda);
				vect.add(idSchemaCfg);
				vect.add(idVariabileConfig);
				vect.add(sequenzaValore);
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		//public Vector getRelatedAttributes() { //Fix 24121 --Riga commentata
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Vector getRelatedAttributes(ClassADCollection rel) { //Fix 24121
			Vector vect = new Vector();
			if(rel == null) return vect; //Fix 24121
			try {
				//ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR)); //Fix 24121 --Riga commentata
				ClassADCollection classADCollection = rel; //Fix 24121
				vect.add(classADCollection.getAttribute("IdAzienda"));
				vect.add(classADCollection.getAttribute("IdSchemaCfg"));
				vect.add(classADCollection.getAttribute("IdVariabileConfig"));
				vect.add(classADCollection.getAttribute("SequenzaValore"));
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		@SuppressWarnings("rawtypes")
		public WebTextInputForSearch createWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight) {
			return new ValoreWebTextInputForSearch(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight, variabileCfg);
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.initialize(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
			return bodc;
		}

		@SuppressWarnings("rawtypes")
		protected Vector getRelatedParents(int keyIndex) {
			return getParents(keyIndex);
		}

		protected String getRightValueStr() {
			String rightValue = "";
			return rightValue;
		}

	}

	public class ValoreWebTextInputForSearch extends WebTextInputForSearch {
		public boolean isThisRight;
		public boolean onlyId;
		protected VariabileSchemaCfg variabileCfg;

		//Fix 30485 inizio
		public ValoreWebTextInputForSearch(){
			super();
		}
		//Fix 30485 fine

		@SuppressWarnings("rawtypes")
		public ValoreWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight, VariabileSchemaCfg variabileCfg) {
			super(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight);
			isThisRight = isRight;
			this.variabileCfg = variabileCfg;
			if(isRight){
				onChange = "resetValues('"+getIdVarCfgXRowForm(variabileCfg)+"')";//Fix 11994
			}
		}

		public ClassAD loadClassAd() {
			//return this.ownerForm.getClassADCollection().getAttribute(completeClassAD);
			ClassRD relation = new ClassRD();
			relation.setRelationName("Valore");
			relation.setRelatedClassName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
			if (completeClassAD.endsWith("Descrizione"))
				return relation.getRelationHDR().getAttribute("Descrizione.Descrizione");
			else
				return relation.getRelationHDR().getAttribute("SequenzaValore");
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.initialize(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
			return bodc;
		}

		protected boolean extractActionEnable() {
			return true;
		}

		protected void extractParam() {
			if (classADObject == null) {
				ownerForm = getOwnerForm();
				if (classCD != null && classCD.length() > 0) {
					this.inSubForm = true;
					completeClassAD = classCD + "." + classAD;
					this.classHdr = ownerForm.getClassADCollection().getClassName();
					renameParentsAndChildren();
				}

				if (getOwnerForm() instanceof WebFormForInternalRowForm)
					isCheckAll = false;
				classADObject = loadClassAd();
				addSearchableElement();
				addRestrictConditionParents();

				if (!isThisRight) {
					compManager = getBODataCollector().getComponentManager("SequenzaValore");
					if (compManager instanceof EnhBOComponentManager) {
						EnhBOComponentManager enhCompManager = (EnhBOComponentManager) compManager;
						handlingMode = enhCompManager.getHandlingMode();
					}
					actionEnable = extractActionEnable();

					if (compManager.isDenied())
						denied = true;
				}

				String typeNameJS = ownerForm.getJSTypeList().startNewType(classADObject.getType());

				if (actionEnable) {
					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					if (onChange != null) {
						onChangeValue = onChange + ";";
					}
					String searchClearParams = "'" + getName() + "', " + isThisRight;
					String searchClearFunction = "clearSearch(" + searchClearParams + ");";

					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					onKeyDownValue = typeNameJS + ".onKeyDown();";

					onKeyUpValue = "searchOnKeyUp('" + typeNameJS + "'," + searchClearParams + ");";
					//         onKeyUpValue = searchClearFunction + typeNameJS + ".onKeyUp();";

					onChangeValue = searchClearFunction;
					if (onChange != null)
						onChangeValue = onChangeValue + onChange + ";";
				}

				ownerForm.registerElementOnTab(this);
			}
		}

		/*public void write(JspWriter out) throws java.io.IOException {
	      String oldCompleteClassAD = completeClassAD;
	      if (completeClassAD.startsWith("SequenzaValore")) {
	        completeClassAD = "SequenzaValore";
	        super.write(out);
	        completeClassAD = oldCompleteClassAD;
	      }
	      else {
	        super.write(out);
	      }
	         }*/

		public void write(JspWriter out) throws java.io.IOException {

			extractParam();

			String classValue = " class=" + getQuotedString(getClassType());
			String maxLengthValue = " maxlength=" + getQuotedString(getMaxLength());
			String sizeValue = " size=";
			if (cols != null)
				sizeValue += getQuotedString(cols);
			else
				sizeValue += getQuotedString(getSize());

			String typeValue = " type=";
			//Mod.1401 if(isRight && onlyId)
			//if (onlyId) Mekki
			if (!isThisRight)
				typeValue += "\"hidden\"";
			else
				typeValue += "\"text\"";
			//Fix 11994 inizio
			String aggVal = "N";
			if (variabileCfg.isAbilitaAggiuntaValori())
				aggVal = "Y";
			out.println("<input " + classValue + typeValue +
					" id=" + getQuotedString(getId()) + " name=" + getQuotedString(getName()) +
					" idVariabileCfg=" + getQuotedString(variabileCfg.getIdVariabileConfig()) +
					" aggVal=" + getQuotedString(aggVal) +
					maxLengthValue + sizeValue + " value >");

			//Fix 11994 fine
			//String inportForType = ownerForm.getJSTypeList().getImportForCurrentType();//Fix 26505
			String inportForType = ownerForm.getJSTypeList().getImportForCurrentType(ownerForm);
			if (inportForType.length() > 0)
				out.println(inportForType);

			boolean mandatory = false;
			//Mekki
			//	      if (!isRight)
			//	        mandatory = true;
			//	      else
			//	        mandatory = classADObject.isMandatory();
			if(isThisRight && variabileCfg.getObbligVariabile()){
				mandatory = true;
			}
			//End Mekki

			/////////////////////////////////////
			addJavaScriptString(" " + ownerForm.getJSTypeList().getConstructorForCurrentType(WebForm.FORM_PREF + getId(), mandatory));
			String fieldElement = "document.forms[0]." + getId();
			String fieldType = ownerForm.getJSTypeList().getCurrentTypeName();
			String fieldRelatedAD = "'" + relatedClassAD + "'";
			String fieldValue = valueStr;
			String fieldNLS = "null";
			boolean isFieldCheckAll = false;
			boolean isFieldKey = false;
			boolean isFieldActionEnable = actionEnable;

			String fieldClassName = getRealClassType();

			String fieldTooltipText = "null";
			if (tooltipText != null)
				fieldTooltipText = "'" + tooltipText + "'";

			boolean fieldIsRight = !isThisRight;
			String fieldOnSearchBack = "null";
			if (isThisRight && onSearchBack != null)
				fieldOnSearchBack = "'" + getOnSearchBack() + "'";

			String fieldFixedADRels = "null";
			String fieldFixedADRelValues = "null";
			String fieldAdditADRels = "null";
			String fieldAdditADAtts = "null";

			if (!isThisRight) {
				fieldValue = ""; //(String) getBODataCollector().get(completeClassAD);
				if (!Utils.areEqual(fieldValue, BaseComponentManager.STARED_VALUE)) {
					Type type = classADObject.getType();
					fieldValue = (type instanceof EnumerationType) ? fieldValue : type.format(fieldValue);
				}
				fieldValue = "'" + formatValue(fieldValue) + "'";

				fieldNLS = "'" + formatValue(classADObject.findLabelText()) + "'";

				isFieldCheckAll = isCheckAll;
				isFieldKey = classADObject.getKeySequence() > 0;
				if (fixedRestrictRelatedAttributes != null) {

					StringBuffer fixAttrs = new StringBuffer("new Array(");
					StringBuffer fixVals = new StringBuffer("new Array(");
					for (int i = 0; i < fixedRestrictRelatedAttributes.size(); i++) {
						if (i > 0) {
							fixAttrs.append(",");
							fixVals.append(",");
						}
						fixAttrs.append(getQuotedString((String) fixedRestrictRelatedAttributes.elementAt(i)));
						fixVals.append(getQuotedString((String) fixedRestrictRelatedValues.elementAt(i)));
					}
					fixAttrs.append(")"); //Mod. 559
					fixVals.append(")"); //Mod. 559
					fieldFixedADRels = fixAttrs.toString();
					fieldFixedADRelValues = fixVals.toString();
				}
				if (additionalRestrictAttributes != null) {

					StringBuffer additonalADRels = new StringBuffer("new Array(");
					StringBuffer additonalADAtts = new StringBuffer("new Array(");
					for (int i = 0; i < additionalRestrictAttributes.size(); i++) {
						if (i > 0) {
							additonalADRels.append(",");
							additonalADAtts.append(",");
						}
						additonalADRels.append(getQuotedString((String) additionalRestrictRelatedAttributes.elementAt(i)));
						additonalADAtts.append(getQuotedString((String) additionalRestrictAttributes.elementAt(i)));
					}
					additonalADRels.append(")"); //Mod. 559
					additonalADAtts.append(")"); //Mod. 559
					fieldAdditADRels = additonalADRels.toString();
					fieldAdditADAtts = additonalADAtts.toString();
				}
			}

			else {
				if (!Utils.areEqual(fieldValue, DENIED_VALUE)) {
					Type type = classADObject.getType();

					fieldValue = (type instanceof NumberType) ? type.format(fieldValue) : fieldValue;
					fieldValue = formatValue(fieldValue);
				}
				fieldValue = "'" + fieldValue + "'";
				//Mekki
				fieldNLS = "'" + getIdVarCfgXRowForm(variabileCfg) + "'";//Fix 11994
			}
			//Fix 01226 MM fine

			if (actionEnable) {
				if (onBlur != null)
					addJavaScriptString(fieldElement + ".addOB = function(){" + onBlur + "};");

				if (onFocus != null)
					addJavaScriptString(fieldElement + ".addOF = function(){" + onFocus + "};");

				if (onChange != null)
					addJavaScriptString(fieldElement + ".addOC = function(){" + onChange + "};");
			}

			//09870 - DF
			//09892 - DF
			addJavaScriptString(" " + fieldElement + ".fieldSecurity=\"" + getSecurityFieldString(findClassADObject().getAuthorizationLevel()) + "\";");
			//09870 - Fine

			String butIdValue = "'" + buttonsIdentifier + "'";

			if (fixedRestrictRelatedAttributes == null && additionalRestrictAttributes == null) {
				addJavaScriptString(" setupNSFM(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," + butIdValue + ");");
			}
			else {
				addJavaScriptString(" setupCSFM(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," +
						fieldFixedADRels + "," + fieldFixedADRelValues + "," +
						fieldAdditADRels + "," + fieldAdditADAtts + "," + butIdValue + ");");
			}

		}

	}

	public class ValoreWebTextInput extends WebTextInput {

		public ValoreWebTextInput() {
		}

		public ValoreWebTextInput(String classHdr, String classAD) {
			this.classHdr = classHdr;
			this.classAD = classAD;
			this.completeClassAD = classAD;
		}

		protected ClassAD getClassADObject() {
			try {
				ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
				return classADCollection.getAttribute(this.classAD);
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return null;
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.initialize(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
			return bodc;
		}

	}

	public class CatalogoMultiSearchForm extends WebMultiSearchForm {
		protected VariabileSchemaCfg variabileCfg;

		public CatalogoMultiSearchForm(String classHdr, String classRD, boolean onlyId, boolean onlyDesc,
				boolean editButton, int numLeftFields, String idCols, String descCols) {
			this.classHdr = classHdr;
			this.classRD = classRD;
			this.onlyId = onlyId;
			this.onlyDesc = onlyDesc;
			this.editButton = editButton;
			this.numLeftFields = numLeftFields;
			this.idCols = idCols;
			this.descCols = descCols;

		}

		public CatalogoMultiSearchForm(VariabileSchemaCfg variabileCfg) {
			this.variabileCfg = variabileCfg;
			//Fix 11994 inizio
			//30682 inizio
			/*
	      this.idCols = "15";
	      this.descCols = "15";
			 */
			this.idCols = "18";
			this.descCols = "18";
			//30682 fine
			this.editButton = false;
			//Fix 11994 fine
		}

		public WebElement getParent() {
			return parent;
		}

		protected void extractRDObject() {
			try {
				classRDObject = buildClassRD();
			}
			catch (Exception ex) {ex.printStackTrace(Trace.excStream);
			}
		}

		private ClassRD buildClassRD() throws SQLException {
			ClassRD relation = new ClassRD();
			relation.setClassName(Factory.getName("VariabileSchemaCfg", Factory.CLASS_HDR));
			//relation.setRelationNumber(relationNumber);
			relation.setRelationName("Catalogo"+getIdVarCfgXRowForm(variabileCfg));//Fix 11994
			relation.setRelatedClassName(Factory.getName("CatalogoEsterno", Factory.CLASS_HDR));
			relation.setOuterJoin(true);
			relation.setOnlySelect(false);
			relation.setXMLExportable(false);
			relation.setGridDisplayable(true);
			//relation.setDefaultSettingPosition(rsRel.getInt("DEF_SETTING_POS"));
			relation.setRelationNameNLS("CatalogoEsterno");
			relation.setColumnTitleNLS("CatalogoEsterno");
			relation.setLabelTextNLS("CatalogoEsterno");
			relation.setSearchRelatedDecodAttribute(relation.getRelationHDR().getAttribute("DescrizioneEstesa"));
			relation.setAttributes(getAttributes());
			relation.setRelatedAttributes(getRelatedAttributes());
			return relation;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Vector getAttributes() {
			Vector vect = new Vector();
			try {
				ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
				ClassAD ad = classADCollection.getAttribute("IdAzienda");
				ClassAD idAzienda = new SimpleClassAD("", "IdAzienda", "IdAzienda", "IdAzienda", true, "", (short) 0, ad.getType());
				ClassAD idCatalogo = new SimpleClassAD("", "Valore_" + getIdVarCfgXRowForm(variabileCfg), "IdCatalogo", "IdCatalogo", true, "", (short) 1, ad.getType());//Fix 11994
				vect.add(idAzienda);
				vect.add(idCatalogo);
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Vector getRelatedAttributes() {
			Vector vect = new Vector();
			try {
				ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("CatalogoEsterno", Factory.CLASS_HDR));
				vect.add(classADCollection.getAttribute("IdAzienda"));
				vect.add(classADCollection.getAttribute("IdCatalogo"));
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		@SuppressWarnings("rawtypes")
		public WebTextInputForSearch createWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight) {
			return new CatalogoWebTextInputForSearch(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight);
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.initialize(Factory.getName("CatalogoEsterno", Factory.CLASS_HDR));
			return bodc;
		}

		@SuppressWarnings("rawtypes")
		protected Vector getRelatedParents(int keyIndex) {
			return getParents(keyIndex);
		}

		protected String getRightValueStr() {
			String rightValue = "";
			//Fix 19225 blocco comentato
			/*try {
	        String subFormCDPrefix = this.inSubForm ? classCD + "." : "";
	        BODataCollector relBODC = new BODataCollector(relatedClassName, false); //GN messo false affinchè non carichi tutta la struttura dei descrittori di classe non serve
	        Object[] relKeys = new Object[attributes.size()];
	        for (int i = 0; i < relKeys.length; i++) {
	          ClassAD ad = (ClassAD) attributes.elementAt(i);
	          Type t = ad.getType();
	          String completeClassADKey = subFormCDPrefix + ad.getAttributeName();
	          String value = "";
	          if (completeClassADKey.equals("Valore_" + getIdVarCfgXRowForm(variabileCfg)))//Fix 11994
	            value = "";
	          else
	            value = (String) getBODataCollector().get(completeClassADKey);
	          relKeys[i] = (t instanceof EnumerationType) ? value : t.stringToObject(value);
	        }
	        relBODC.setRetrieveFromCache(true);
	        int rc = relBODC.retrieve(KeyHelper.buildObjectKey(relKeys));
	        if (rc == BODataCollector.OK)
	          rightValue = (String) relBODC.get(relatedDecodAD.getAttributeName());
	      }
	      catch (Exception e) {
	        e.printStackTrace(Trace.excStream);
	      }*/

			return rightValue;
		}

		//Fix 32142 -- Inizio
		@SuppressWarnings("rawtypes")
		public String getOutJavaScriptString()
		{
			StringBuffer sb = new StringBuffer("<script language=\"JavaScript1.2\">");
			Iterator iter = javaScriptStrings.iterator();
			int i=0;
			while(iter.hasNext())
			{
				if(i>2) {
					sb.append("\n");
					sb.append(iter.next());
				}else {
					iter.next();
				}
				i++;
			}
			sb.append("\n</script>");
			return sb.toString();
		}
		//Fix 32142 -- Fine

	}

	public class EsternaMultiSearchForm extends WebMultiSearchForm {
		protected VariabileSchemaCfg variabileCfg;

		public EsternaMultiSearchForm(String classHdr, String classRD, boolean onlyId, boolean onlyDesc,
				boolean editButton, int numLeftFields, String idCols, String descCols) {
			this.classHdr = classHdr;
			this.classRD = classRD;
			this.onlyId = onlyId;
			this.onlyDesc = onlyDesc;
			this.editButton = editButton;
			this.numLeftFields = numLeftFields;
			this.idCols = idCols;
			this.descCols = descCols;

		}

		public EsternaMultiSearchForm(VariabileSchemaCfg variabileCfg) {
			this.variabileCfg = variabileCfg;
			//30682 inizio
			//this.idCols = "10";
			//Fix 11994 inizio
			//this.descCols = "15";
			this.idCols = "42";
			this.descCols = "42";
			//30682 fine
			this.editButton = false;
			//Fix 11994 fine
		}

		public WebElement getParent() {
			return parent;
		}

		protected void extractRDObject() {
			try {
				classRDObject = buildClassRD();
			}
			catch (Exception ex) {ex.printStackTrace(Trace.excStream);
			}
		}

		private ClassRD buildClassRD() throws SQLException {
			ClassRD relation = new ClassRD();
			relation.setClassName(Factory.getName("VariabileSchemaCfg", Factory.CLASS_HDR));
			//relation.setRelationNumber(relationNumber);
			relation.setRelationName("Esterna"+getIdVarCfgXRowForm(variabileCfg));//Fix 11994
			relation.setRelatedClassName(Factory.getName(variabileCfg.getIdDescrittore(), Factory.CLASS_HDR));
			relation.setOuterJoin(true);
			relation.setOnlySelect(false);
			relation.setXMLExportable(false);
			relation.setGridDisplayable(true);
			//relation.setDefaultSettingPosition(rsRel.getInt("DEF_SETTING_POS"));
			relation.setRelationNameNLS("ValoreEsterno");
			relation.setColumnTitleNLS("ValoreEsterno");
			relation.setLabelTextNLS("ValoreEsterno");
			relation.setSearchRelatedDecodAttribute(relation.getRelationHDR().getAttribute("Descrizione"));
			relation.setAttributes(getAttributes());
			relation.setRelatedAttributes(getRelatedAttributes());
			return relation;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Vector getAttributes() {
			Vector vect = new Vector();
			try {
				ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName("ValoreVariabileCfg", Factory.CLASS_HDR));
				ClassAD ad = classADCollection.getAttribute("IdAzienda");
				//Fix 15511 --inizio
				if(getClassAD(variabileCfg.getIdDescrittore() ,"IdAzienda") != null){
					ClassAD idAzienda = new SimpleClassAD("", "IdAzienda", "IdAzienda", "IdAzienda", true, "", (short) 0, ad.getType());
					vect.add(idAzienda);
				}
				//Fix 15511 --fine
				ClassAD idCatalogo = new SimpleClassAD("", "Valore_" + getIdVarCfgXRowForm(variabileCfg), "IdValore", "IdValore", true, "", (short) 1, ad.getType());
				vect.add(idCatalogo);
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Vector getRelatedAttributes() {
			Vector vect = new Vector();
			try {
				ClassADCollection classADCollection = ClassADCollectionManager.collectionWithName(Factory.getName(variabileCfg.getIdDescrittore(), Factory.CLASS_HDR));
				if(getClassAD(variabileCfg.getIdDescrittore() ,"IdAzienda") != null){  //Fix 15511
					vect.add(classADCollection.getAttribute("IdAzienda"));
				}
				vect.add(classADCollection.getAttribute("IdValore"));
			}
			catch (Exception ex) {
				ex.printStackTrace(Trace.excStream);
			}
			return vect;
		}

		@SuppressWarnings("rawtypes")
		public WebTextInputForSearch createWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight) {
			return new EsternaWebTextInputForSearch(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight, variabileCfg.getIdDescrittore());
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.setCreateComponentAlways(true);
			bodc.initialize(Factory.getName(variabileCfg.getIdDescrittore(), Factory.CLASS_HDR));
			return bodc;
		}

		@SuppressWarnings("rawtypes")
		protected Vector getRelatedParents(int keyIndex) {
			return getParents(keyIndex);
		}

		protected String getRightValueStr() {
			String rightValue = "";
			//Fix 19225 blocco comentato
			/*try {
	        String subFormCDPrefix = this.inSubForm ? classCD + "." : "";
	        BODataCollector relBODC = new BODataCollector(relatedClassName, false); //GN messo false affinchè non carichi tutta la struttura dei descrittori di classe non serve
	        Object[] relKeys = new Object[attributes.size()];
	        for (int i = 0; i < relKeys.length; i++) {
	          ClassAD ad = (ClassAD) attributes.elementAt(i);
	          Type t = ad.getType();
	          String completeClassADKey = subFormCDPrefix + ad.getAttributeName();
	          String value = "";
	          if (completeClassADKey.equals("Valore_" + getIdVarCfgXRowForm(variabileCfg)))//Fix 11994
	            value = "";
	          else if(completeClassADKey.equals("IdAzienda"))
	            value = variabileCfg.getIdAzienda();
	          else
	             value = (String) getBODataCollector().get(completeClassADKey);
	          relKeys[i] = (t instanceof EnumerationType) ? value : t.stringToObject(value);
	        }
	        relBODC.setRetrieveFromCache(true);
	        int rc = relBODC.retrieve(KeyHelper.buildObjectKey(relKeys));
	        if (rc == BODataCollector.OK)
	          rightValue = (String) relBODC.get(relatedDecodAD.getAttributeName());
	      }
	      catch (Exception e) {
	        e.printStackTrace(Trace.excStream);
	      }*/

			return rightValue;
		}

		//30331 inizio
		protected void writeJavaScriptPart(JspWriter out) throws java.io.IOException {
			addJavaScriptString("automaticLightSearch = true;");
			out.println(getOutJavaScriptString());
		}
		//30331 fine

		//30682 inizio
		public void writeEditButton(JspWriter out) throws java.io.IOException {
			extractInternalButtonIdentifier();
			String classValue = " ";
			String buttonsClassType = getButtonsClassType();
			if(buttonsClassType.length() > 0)
				classValue = " class=\"" + buttonsClassType + "\"";

			//Fix 10428 inizio
			//String editButStr = "<button" + classValue + " id = \"" + editButId + "\">" +
			String editButStr = "<button" + classValue + " type=\"button\" id = \"" + editButId + "\">" +
					//Fix 10428 fine
					"<img border=\"0\" width=\"16\" height=\"16\" style=\"display:none\"  src=\"" + StyleDefinition.getInstance().getStylePath(EDIT_IMAGE) + "\">" + "</button>"; //12500
			out.println(editButStr);
		}
		//30682 fine

	}

	public class CatalogoWebTextInputForSearch extends WebTextInputForSearch {
		public boolean isThisRight;
		public boolean onlyId;

		@SuppressWarnings("rawtypes")
		public CatalogoWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight) {
			super(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight);
			isThisRight = isRight;
		}

		public ClassAD loadClassAd() {
			//return this.ownerForm.getClassADCollection().getAttribute(completeClassAD);
			ClassRD relation = new ClassRD();
			relation.setRelationName("Catalogo");
			relation.setRelatedClassName(Factory.getName("CatalogoEsterno", Factory.CLASS_HDR));
			if (completeClassAD.endsWith("DescrizioneEstesa"))
				return relation.getRelationHDR().getAttribute("DescrizioneEstesa");
			else
				return relation.getRelationHDR().getAttribute("IdCatalogo");
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.initialize(Factory.getName("CatalogoEsterno", Factory.CLASS_HDR));
			return bodc;
		}

		protected boolean extractActionEnable() {
			return true;
		}

		protected void extractParam() {
			if (classADObject == null) {
				ownerForm = getOwnerForm();
				if (classCD != null && classCD.length() > 0) {
					this.inSubForm = true;
					completeClassAD = classCD + "." + classAD;
					this.classHdr = ownerForm.getClassADCollection().getClassName();
					renameParentsAndChildren();
				}

				if (getOwnerForm() instanceof WebFormForInternalRowForm)
					isCheckAll = false;
				classADObject = loadClassAd();
				addSearchableElement();
				addRestrictConditionParents();

				if (!isThisRight) {
					compManager = getBODataCollector().getComponentManager("IdCatalogo");
					if (compManager instanceof EnhBOComponentManager) {
						EnhBOComponentManager enhCompManager = (EnhBOComponentManager) compManager;
						handlingMode = enhCompManager.getHandlingMode();
					}
					actionEnable = extractActionEnable();

					if (compManager.isDenied())
						denied = true;
				}

				String typeNameJS = ownerForm.getJSTypeList().startNewType(classADObject.getType());

				if (actionEnable) {
					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					if (onChange != null) {
						onChangeValue = onChange + ";";
					}
					String searchClearParams = "'" + getName() + "', " + isThisRight;
					String searchClearFunction = "clearSearch(" + searchClearParams + ");";

					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					onKeyDownValue = typeNameJS + ".onKeyDown();";

					onKeyUpValue = "searchOnKeyUp('" + typeNameJS + "'," + searchClearParams + ");";
					//         onKeyUpValue = searchClearFunction + typeNameJS + ".onKeyUp();";

					onChangeValue = searchClearFunction;
					if (onChange != null)
						onChangeValue = onChangeValue + onChange + ";";
				}

				ownerForm.registerElementOnTab(this);
			}
		}

		/*public void write(JspWriter out) throws java.io.IOException {
	      String oldCompleteClassAD = completeClassAD;
	      if (completeClassAD.startsWith("SequenzaValore")) {
	        completeClassAD = "SequenzaValore";
	        super.write(out);
	        completeClassAD = oldCompleteClassAD;
	      }
	      else {
	        super.write(out);
	      }
	         }*/

		public void write(JspWriter out) throws java.io.IOException {

			extractParam();

			String classValue = " class=" + getQuotedString(getClassType());
			String maxLengthValue = " maxlength=" + getQuotedString(getMaxLength());
			String sizeValue = " size=";
			if (cols != null)
				sizeValue += getQuotedString(cols);
			else
				sizeValue += getQuotedString(getSize());

			String typeValue = " type=";
			//Mod.1401 if(isRight && onlyId)
			if (onlyId)
				typeValue += "\"hidden\"";
			else
				typeValue += "\"text\"";

			out.println("<input " + classValue + typeValue +
					" id=" + getQuotedString(getId()) + " name=" + getQuotedString(getName()) +
					maxLengthValue + sizeValue + " value >");

			//String inportForType = ownerForm.getJSTypeList().getImportForCurrentType();//Fix 26505
			String inportForType = ownerForm.getJSTypeList().getImportForCurrentType(ownerForm);//Fix 26505
			if (inportForType.length() > 0)
				out.println(inportForType);

			boolean mandatory = false;
			//	      if (!isRight)
			//	        mandatory = true;
			//	      else
			//	        mandatory = classADObject.isMandatory();

			/////////////////////////////////////
			addJavaScriptString(" " + ownerForm.getJSTypeList().getConstructorForCurrentType(WebForm.FORM_PREF + getId(), mandatory));
			String fieldElement = "document.forms[0]." + getId();
			String fieldType = ownerForm.getJSTypeList().getCurrentTypeName();
			String fieldRelatedAD = "'" + relatedClassAD + "'";
			String fieldValue = valueStr;
			String fieldNLS = "null";
			boolean isFieldCheckAll = false;
			boolean isFieldKey = false;
			boolean isFieldActionEnable = actionEnable;

			String fieldClassName = getRealClassType();

			String fieldTooltipText = "null";
			if (tooltipText != null)
				fieldTooltipText = "'" + tooltipText + "'";

			boolean fieldIsRight = isThisRight;
			String fieldOnSearchBack = "null";
			if (isThisRight && onSearchBack != null)
				fieldOnSearchBack = "'" + getOnSearchBack() + "'";

			String fieldFixedADRels = "null";
			String fieldFixedADRelValues = "null";
			String fieldAdditADRels = "null";
			String fieldAdditADAtts = "null";

			if (!isThisRight) {
				fieldValue = ""; //(String) getBODataCollector().get(completeClassAD);
				if (!Utils.areEqual(fieldValue, BaseComponentManager.STARED_VALUE)) {
					Type type = classADObject.getType();
					fieldValue = (type instanceof EnumerationType) ? fieldValue : type.format(fieldValue);
				}
				fieldValue = "'" + formatValue(fieldValue) + "'";

				fieldNLS = "'" + formatValue(classADObject.findLabelText()) + "'";

				isFieldCheckAll = isCheckAll;
				isFieldKey = classADObject.getKeySequence() > 0;
				if (fixedRestrictRelatedAttributes != null) {

					StringBuffer fixAttrs = new StringBuffer("new Array(");
					StringBuffer fixVals = new StringBuffer("new Array(");
					for (int i = 0; i < fixedRestrictRelatedAttributes.size(); i++) {
						if (i > 0) {
							fixAttrs.append(",");
							fixVals.append(",");
						}
						fixAttrs.append(getQuotedString((String) fixedRestrictRelatedAttributes.elementAt(i)));
						fixVals.append(getQuotedString((String) fixedRestrictRelatedValues.elementAt(i)));
					}
					fixAttrs.append(")"); //Mod. 559
					fixVals.append(")"); //Mod. 559
					fieldFixedADRels = fixAttrs.toString();
					fieldFixedADRelValues = fixVals.toString();
				}
				if (additionalRestrictAttributes != null) {

					StringBuffer additonalADRels = new StringBuffer("new Array(");
					StringBuffer additonalADAtts = new StringBuffer("new Array(");
					for (int i = 0; i < additionalRestrictAttributes.size(); i++) {
						if (i > 0) {
							additonalADRels.append(",");
							additonalADAtts.append(",");
						}
						additonalADRels.append(getQuotedString((String) additionalRestrictRelatedAttributes.elementAt(i)));
						additonalADAtts.append(getQuotedString((String) additionalRestrictAttributes.elementAt(i)));
					}
					additonalADRels.append(")"); //Mod. 559
					additonalADAtts.append(")"); //Mod. 559
					fieldAdditADRels = additonalADRels.toString();
					fieldAdditADAtts = additonalADAtts.toString();
				}
			}

			else {
				if (!Utils.areEqual(fieldValue, DENIED_VALUE)) {
					Type type = classADObject.getType();

					fieldValue = (type instanceof NumberType) ? type.format(fieldValue) : fieldValue;
					fieldValue = formatValue(fieldValue);
				}
				fieldValue = "'" + fieldValue + "'";
			}
			//Fix 01226 MM fine

			if (actionEnable) {
				if (onBlur != null)
					addJavaScriptString(fieldElement + ".addOB = function(){" + onBlur + "};");

				if (onFocus != null)
					addJavaScriptString(fieldElement + ".addOF = function(){" + onFocus + "};");

				if (onChange != null)
					addJavaScriptString(fieldElement + ".addOC = function(){" + onChange + "};");
			}

			//09870 - DF
			//09892 - DF
			addJavaScriptString(" " + fieldElement + ".fieldSecurity=\"" + getSecurityFieldString(findClassADObject().getAuthorizationLevel()) + "\";");
			//09870 - Fine

			String butIdValue = "'" + buttonsIdentifier + "'";

			if (fixedRestrictRelatedAttributes == null && additionalRestrictAttributes == null) {
				addJavaScriptString(" setupNSF(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," + butIdValue + ");");
			}
			else {
				addJavaScriptString(" setupCSF(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," +
						fieldFixedADRels + "," + fieldFixedADRelValues + "," +
						fieldAdditADRels + "," + fieldAdditADAtts + "," + butIdValue + ");");
			}
		}
	}

	public class EsternaWebTextInputForSearch extends WebTextInputForSearch {
		public boolean isThisRight;
		public boolean onlyId;
		public String idDescrittore;

		@SuppressWarnings("rawtypes")
		public EsternaWebTextInputForSearch(String classHdr, String classAD, String relatedClassAD, Vector parents, Vector relatedParents, String cols, boolean isRight, String idDescr) {
			super(classHdr, classAD, relatedClassAD, parents, relatedParents, cols, isRight);
			isThisRight = isRight;
			idDescrittore = idDescr;
		}

		public ClassAD loadClassAd() {
			//return this.ownerForm.getClassADCollection().getAttribute(completeClassAD);
			ClassRD relation = new ClassRD();
			relation.setRelationName(idDescrittore);
			relation.setRelatedClassName(Factory.getName(idDescrittore, Factory.CLASS_HDR));
			if (completeClassAD.endsWith("Descrizione"))
				return relation.getRelationHDR().getAttribute("Descrizione");
			else
				return relation.getRelationHDR().getAttribute("IdValore");
		}

		protected BODataCollector getBODataCollector() {
			BODataCollector bodc = (BODataCollector) Factory.createObject(BODataCollector.class);
			bodc.setCreateComponentAlways(true);
			bodc.initialize(Factory.getName(idDescrittore, Factory.CLASS_HDR));
			return bodc;
		}

		protected boolean extractActionEnable() {
			return true;
		}

		protected void extractParam() {
			if (classADObject == null) {
				ownerForm = getOwnerForm();
				if (classCD != null && classCD.length() > 0) {
					this.inSubForm = true;
					completeClassAD = classCD + "." + classAD;
					this.classHdr = ownerForm.getClassADCollection().getClassName();
					renameParentsAndChildren();
				}

				if (getOwnerForm() instanceof WebFormForInternalRowForm)
					isCheckAll = false;
				classADObject = loadClassAd();
				addSearchableElement();
				addRestrictConditionParents();

				if (!isThisRight) {
					compManager = getBODataCollector().getComponentManager("IdValore");
					if (compManager instanceof EnhBOComponentManager) {
						EnhBOComponentManager enhCompManager = (EnhBOComponentManager) compManager;
						handlingMode = enhCompManager.getHandlingMode();
					}
					actionEnable = extractActionEnable();

					if (compManager.isDenied())
						denied = true;
				}

				String typeNameJS = ownerForm.getJSTypeList().startNewType(classADObject.getType());

				if (actionEnable) {
					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					if (onChange != null) {
						onChangeValue = onChange + ";";
					}
					String searchClearParams = "'" + getName() + "', " + isThisRight;
					String searchClearFunction = "clearSearch(" + searchClearParams + ");";

					onBlurValue = typeNameJS + ".onBlur();";
					if (onBlur != null)
						onBlurValue = onBlurValue + onBlur + ";";

					onFocusValue = typeNameJS + ".onFocus();";
					if (onFocus != null)
						onFocusValue = onFocus + ";" + onFocusValue;

					onKeyDownValue = typeNameJS + ".onKeyDown();";

					onKeyUpValue = "searchOnKeyUp('" + typeNameJS + "'," + searchClearParams + ");";
					//         onKeyUpValue = searchClearFunction + typeNameJS + ".onKeyUp();";

					onChangeValue = searchClearFunction;
					if (onChange != null)
						onChangeValue = onChangeValue + onChange + ";";
				}

				ownerForm.registerElementOnTab(this);
			}
		}

		/*public void write(JspWriter out) throws java.io.IOException {
	      String oldCompleteClassAD = completeClassAD;
	      if (completeClassAD.startsWith("SequenzaValore")) {
	        completeClassAD = "SequenzaValore";
	        super.write(out);
	        completeClassAD = oldCompleteClassAD;
	      }
	      else {
	        super.write(out);
	      }
	         }*/

		public void write(JspWriter out) throws java.io.IOException {

			extractParam();

			String classValue = " class=" + getQuotedString(getClassType());
			String maxLengthValue = " maxlength=" + getQuotedString(getMaxLength());
			String sizeValue = " size=";
			if (cols != null)
				sizeValue += getQuotedString(cols);
			else
				sizeValue += getQuotedString(getSize());

			String typeValue = " type=";
			//Mod.1401 if(isRight && onlyId)
			if (onlyId)
				typeValue += "\"hidden\"";
			else
				typeValue += "\"text\"";

			String automaticLSLengthValue = " automaticLSLength=1";//30331

			out.println("<input " + classValue + typeValue +
					" id=" + getQuotedString(getId()) + " name=" + getQuotedString(getName()) +
					maxLengthValue + sizeValue + automaticLSLengthValue + " value >");//30331

			//String inportForType = ownerForm.getJSTypeList().getImportForCurrentType();//Fix 26505
			String inportForType = ownerForm.getJSTypeList().getImportForCurrentType(ownerForm);//Fix 26505

			if (inportForType.length() > 0)
				out.println(inportForType);

			boolean mandatory = false;
			//	      if (!isRight)
			//	        mandatory = true;
			//	      else
			//	        mandatory = classADObject.isMandatory();

			/////////////////////////////////////
			addJavaScriptString(" " + ownerForm.getJSTypeList().getConstructorForCurrentType(WebForm.FORM_PREF + getId(), mandatory));
			String fieldElement = "document.forms[0]." + getId();
			String fieldType = ownerForm.getJSTypeList().getCurrentTypeName();
			String fieldRelatedAD = "'" + relatedClassAD + "'";
			String fieldValue = valueStr;
			String fieldNLS = "null";
			boolean isFieldCheckAll = false;
			boolean isFieldKey = false;
			boolean isFieldActionEnable = actionEnable;

			String fieldClassName = getRealClassType();

			String fieldTooltipText = "null";
			if (tooltipText != null)
				fieldTooltipText = "'" + tooltipText + "'";

			boolean fieldIsRight = isThisRight;
			String fieldOnSearchBack = "null";
			if (isThisRight && onSearchBack != null)
				fieldOnSearchBack = "'" + getOnSearchBack() + "'";

			String fieldFixedADRels = "null";
			String fieldFixedADRelValues = "null";
			String fieldAdditADRels = "null";
			String fieldAdditADAtts = "null";

			if (!isThisRight) {
				fieldValue = ""; //(String) getBODataCollector().get(completeClassAD);
				if (!Utils.areEqual(fieldValue, BaseComponentManager.STARED_VALUE)) {
					Type type = classADObject.getType();
					fieldValue = (type instanceof EnumerationType) ? fieldValue : type.format(fieldValue);
				}
				fieldValue = "'" + formatValue(fieldValue) + "'";

				fieldNLS = "'" + formatValue(classADObject.findLabelText()) + "'";

				isFieldCheckAll = isCheckAll;
				isFieldKey = classADObject.getKeySequence() > 0;
				if (fixedRestrictRelatedAttributes != null) {

					StringBuffer fixAttrs = new StringBuffer("new Array(");
					StringBuffer fixVals = new StringBuffer("new Array(");
					for (int i = 0; i < fixedRestrictRelatedAttributes.size(); i++) {
						if (i > 0) {
							fixAttrs.append(",");
							fixVals.append(",");
						}
						fixAttrs.append(getQuotedString((String) fixedRestrictRelatedAttributes.elementAt(i)));
						fixVals.append(getQuotedString((String) fixedRestrictRelatedValues.elementAt(i)));
					}
					fixAttrs.append(")"); //Mod. 559
					fixVals.append(")"); //Mod. 559
					fieldFixedADRels = fixAttrs.toString();
					fieldFixedADRelValues = fixVals.toString();
				}
				if (additionalRestrictAttributes != null) {

					StringBuffer additonalADRels = new StringBuffer("new Array(");
					StringBuffer additonalADAtts = new StringBuffer("new Array(");
					for (int i = 0; i < additionalRestrictAttributes.size(); i++) {
						if (i > 0) {
							additonalADRels.append(",");
							additonalADAtts.append(",");
						}
						additonalADRels.append(getQuotedString((String) additionalRestrictRelatedAttributes.elementAt(i)));
						additonalADAtts.append(getQuotedString((String) additionalRestrictAttributes.elementAt(i)));
					}
					additonalADRels.append(")"); //Mod. 559
					additonalADAtts.append(")"); //Mod. 559
					fieldAdditADRels = additonalADRels.toString();
					fieldAdditADAtts = additonalADAtts.toString();
				}
			}

			else {
				if (!Utils.areEqual(fieldValue, DENIED_VALUE)) {
					Type type = classADObject.getType();

					fieldValue = (type instanceof NumberType) ? type.format(fieldValue) : fieldValue;
					fieldValue = formatValue(fieldValue);
				}
				fieldValue = "'" + fieldValue + "'";
			}
			//Fix 01226 MM fine

			if (actionEnable) {
				if (onBlur != null)
					addJavaScriptString(fieldElement + ".addOB = function(){" + onBlur + "};");

				if (onFocus != null)
					addJavaScriptString(fieldElement + ".addOF = function(){" + onFocus + "};");

				if (onChange != null)
					//addJavaScriptString(fieldElement + ".addOC = function(){" + onChange + "};");//30331
					addJavaScriptString(fieldElement + ".addOC = function(){if (!thLightSearchFrameOver) {" + onChange + ";}};"); //30331
			}

			//09870 - DF
			//09892 - DF
			addJavaScriptString(" " + fieldElement + ".fieldSecurity=\"" + getSecurityFieldString(findClassADObject().getAuthorizationLevel()) + "\";");
			//09870 - Fine
			//Fix 30331 inizio
			if (!isThisRight) {
				addJavaScriptString(" "+fieldElement + ".setAttribute(\"relatedClassName\",\"" + relatedClassName + "\");");
				if (decodeOnBlur){
					addJavaScriptString(" " + fieldElement + ".setAttribute(\"decodeonblur\", \"" + decodeOnBlur + "\");");
					addJavaScriptString(" " + fieldElement + ".setAttribute(\"classhdr\", \"" + classHDR + "\");");
					addJavaScriptString(" " + fieldElement + ".setAttribute(\"classrd\", \"" + classRD + "\");");
				}
				if (extraRelatedClassAD != null)
					addJavaScriptString(" "+fieldElement + ".setAttribute(\"extraRelatedClassAD\",\"" + extraRelatedClassAD + "\");");
				if (specificDOList != null)
					addJavaScriptString(" "+fieldElement + ".setAttribute(\"specificDOList\",\"" + specificDOList + "\");");
			}
			//Fix 30331 fine
			String butIdValue = "'" + buttonsIdentifier + "'";

			if (fixedRestrictRelatedAttributes == null && additionalRestrictAttributes == null) {
				addJavaScriptString(" setupNSF(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," + butIdValue + ");");
			}
			else {
				addJavaScriptString(" setupCSF(" + fieldElement + "," + fieldType + "," +
						fieldValue + "," + fieldNLS + "," +
						isFieldCheckAll + "," + isFieldKey + "," +
						isFieldActionEnable + "," + fieldClassName + "," + fieldTooltipText + "," +
						fieldRelatedAD + "," + fieldIsRight + "," + fieldOnSearchBack + "," +
						fieldFixedADRels + "," + fieldFixedADRelValues + "," +
						fieldAdditADRels + "," + fieldAdditADAtts + "," + butIdValue + ");");
			}

		}

	}

	public ValoreMultiSearchForm getValoreMultiSearchForm(VariabileSchemaCfg variabileCfg){
		return new ValoreMultiSearchForm(variabileCfg);
	}

	public ValoreWebTextInputForSearch getValoreWebTextInputForSearch(){
		return new ValoreWebTextInputForSearch();
	}

}
