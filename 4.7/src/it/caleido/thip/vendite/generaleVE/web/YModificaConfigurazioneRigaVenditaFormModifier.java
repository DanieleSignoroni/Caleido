package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.base.Trace;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.web.WebElement;
import com.thera.thermfw.web.WebFormModifier;
import com.thera.thermfw.web.servlet.GridActionAdapter;

import it.caleido.thip.vendite.generaleVE.YModificaConfigurazioneRigaVendita;
import it.thera.thip.base.catalogo.CatalEsterno;
import it.thera.thip.base.comuniVenAcq.DocumentoOrdineRiga;
import it.thera.thip.base.profilo.UtenteAzienda;
import it.thera.thip.cs.ColonneFiltri;
import it.thera.thip.cs.DescrizioneInLingua;
import it.thera.thip.datiTecnici.configuratore.Configurazione;
import it.thera.thip.datiTecnici.configuratore.GestoreMacroConfigurazione;
import it.thera.thip.datiTecnici.configuratore.SezioneConfigurazione;
import it.thera.thip.datiTecnici.configuratore.ValoreVariabileCfg;
import it.thera.thip.datiTecnici.configuratore.VariabileSchemaCfg;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 04/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    04/02/2025</b>
 * <p></p>
 */

public class YModificaConfigurazioneRigaVenditaFormModifier extends WebFormModifier {

	@SuppressWarnings("rawtypes")
	protected List iErrsFromMacroEntrata = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		YModificaConfigurazioneRigaVendita bo = (YModificaConfigurazioneRigaVendita) getBODataCollector().getBo();
		String className = getServletEnvironment().getRequest().getParameter(GridActionAdapter.CLASS_NAME);
		String[] objectKeys = getServletEnvironment().getRequest().getParameterValues(GridActionAdapter.OBJECT_KEY);
		String keysSel = "";
		Iterator iterSelected = Arrays.asList(objectKeys).iterator();
		while(iterSelected.hasNext()) {
			String key = (String) iterSelected.next();
			keysSel += key;
			if(iterSelected.hasNext()) {
				keysSel += ColonneFiltri.LISTA_SEP;
			}
		}
		bo.setClassName(className);
		bo.setChiaviSelezionati(keysSel);
		List<PersistentObject> righe = bo.getRigheSelezionate();
		for(PersistentObject riga : righe) {
			if(riga instanceof DocumentoOrdineRiga) {
				bo.setArticolo(((DocumentoOrdineRiga)riga).getArticolo());
				bo.setConfigurazione(((DocumentoOrdineRiga)riga).getConfigurazione());
			}
		}
		getBODataCollector().setOnBORecursive();

	}

	@Override
	public void writeBodyStartElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeFormStartElements(JspWriter out) throws IOException {

	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		out.println("<script language='JavaScript1.2'>");
		YModificaConfigurazioneRigaVendita bo = (YModificaConfigurazioneRigaVendita) getBODataCollector().getBo();
		Configurazione configurazione = bo.getConfigurazione();
		int count = 1;
		out.println("document.getElementById('IdSchemaCfg').value = '"+configurazione.getIdSchemaCfg()+"';");
		for (Iterator sezioni = configurazione.getSchemaCfg().getSezioni().iterator(); sezioni.hasNext();) {
			SezioneConfigurazione sezioneCfg = (SezioneConfigurazione) sezioni.next();
			out.println(" var varSez"+count+" ='"+sezioneCfg.getIdSezioneCfg()+"';");
			String sezioneKey = sezioneCfg.getKey(); //Fix 12331
			if(sezioneKey != null){
				String idSezione = KeyHelper.getTokenObjectKey(sezioneKey, 3);
				//out.println("  eval('document.forms[0].' + idFromName['IdSezioneCfg']).value = '" + WebElement.formatStringForHTML(idSezione) + "';"); //Fix 12331
				if (idSezione.equals(Configurazione.ID_SEZ_CONFERMA)) //Fix 12331
					out.println("  changeConfermaLayout(\"" + configurazione.getSchemaCfg().getTipoCodiceCfg()  + "\")"); //Fix 12331
			}
			String varModifDaMacro = com.thera.thermfw.web.servlet.BaseServlet.getStringParameter(getRequest(),"VariabiliModifDaMacro");
			if(varModifDaMacro != null && !varModifDaMacro.equals("null")){
				out.println("document.getElementById('VariabiliModifDaMacro').value = '" + WebElement.formatStringForHTML(varModifDaMacro) + "';");	
			}
			GestoreMacroConfigurazione gestore = new GestoreMacroConfigurazione();
			List errs = iErrsFromMacroEntrata;
			if(errs != null && errs.isEmpty()){
				String variabiliModifDaMacro = Configurazione.buildVariabiliModifDaMacro(gestore.getVariabiliConfigModificate(), configurazione);           
				configurazione.setVariabiliModifDaMacro(variabiliModifDaMacro);
				out.println("  if(document.getElementById('VariabiliModifDaMacro').value !=\"\" )");
				out.println("    document.getElementById('VariabiliModifDaMacro').value += '" + KeyHelper.KEY_SEPARATOR + WebElement.formatStringForHTML(configurazione.getVariabiliModifDaMacro()) + "';");
				out.println("  else ");
				out.println("    document.getElementById('VariabiliModifDaMacro').value += '" + WebElement.formatStringForHTML(configurazione.getVariabiliModifDaMacro()) + "';");
			}    
			out.println("  var oldSintesi = \"\";");//Fix 11994 //Fix 12013
			//out.println("  var additionalSintesi = \"\";"); //Fix 12331
			String additionalSintesi = ""; //Fix 12331
			if(configurazione.getSintesiConfig() != null && (errs == null || errs.isEmpty())) {
				HashMap vars = new HashMap();
				HashMap vals = new HashMap();
				Iterator iterVar = sezioneCfg.getVariabili().iterator();
				while (iterVar.hasNext()) {
					VariabileSchemaCfg var = (VariabileSchemaCfg) iterVar.next();
					vars.put(var.getKey(), var);
					Iterator iterVal = var.getValori().iterator();
					while (iterVal.hasNext()) {
						ValoreVariabileCfg val = (ValoreVariabileCfg) iterVal.next();
						vals.put(val.getKey(), val);
					} 
				}
				String s = new String(configurazione.getSintesiConfig());
				String separator = String.valueOf(PersistentObject.KEY_SEPARATOR);
				while (s.indexOf(separator) > 0) {
					String idVariabile = KeyHelper.getTokenObjectKey(s, 1);
					String idValore = KeyHelper.getTokenObjectKey(s, 3);
					String value = KeyHelper.getTokenObjectKey(s, 2);
					String variabileKey = configurazione.getSchemaCfgKey().concat(separator).concat(idVariabile);
					VariabileSchemaCfg variabileCfg = (VariabileSchemaCfg) vars.get(variabileKey); 
					if (variabileCfg != null) { //Fix 12013  //Fix 12331
						String valoreKey = variabileKey.concat(separator).concat(idValore);
						ValoreVariabileCfg valoreVariabileCfg = (ValoreVariabileCfg) vals.get(valoreKey);
						String primoValore = valoreVariabileCfg.getPrimoValore() != null ? valoreVariabileCfg.getPrimoValore() : "";
						String secondoValore = valoreVariabileCfg.getSecondoValore() != null ? valoreVariabileCfg.getSecondoValore() : "";
						char valoreEsterno = valoreVariabileCfg.getValoreEsterno();
						String iconaValore = valoreVariabileCfg.getIcona() != null ? valoreVariabileCfg.getIcona() : "";
						String immagineValore = valoreVariabileCfg.getImmagine() != null ? valoreVariabileCfg.getImmagine() : "";
						String seq = String.valueOf(variabileCfg.getSeqFisica());//Fix 12013
						out.println(" var varCfg"+seq+" ='VarCfg" + seq + "';");
						out.println(" eval('document.forms[0].' + idFromName[varCfg"+seq+"+'.Descrizione.Descrizione']).value = '" + WebElement.formatStringForHTML(getTextInLingua(valoreVariabileCfg.getDescrizione())) + "';");
						out.println(" eval('document.forms[0].SequenzaValore_'+varCfg"+seq+").value = '" + WebElement.formatStringForHTML(idValore)+ "';");
						out.println(" eval('document.forms[0].Valore_'+varCfg"+seq+").value = '" + WebElement.formatStringForHTML(value) + "';");
						out.println(" fillValoriInternal(varCfg"+seq+",'"+WebElement.formatStringForHTML(primoValore)+"','"+WebElement.formatStringForHTML(secondoValore)+"','"+WebElement.formatStringForHTML(iconaValore)+"','"+valoreEsterno+"','"+WebElement.formatStringForHTML(immagineValore)+"');");
						if(valoreVariabileCfg.getValoreEsterno() == ValoreVariabileCfg.DA_CATALOGO_ESTERNO){
							CatalEsterno catalogoEst = getCatalogoEsterno(valoreVariabileCfg.getIdAzienda(), value);
							if(catalogoEst != null){
								out.println(" eval('document.forms[0].' + idFromName['Catalogo'+varCfg+'.DescrizioneEstesa']).value = '" + WebElement.formatStringForHTML(catalogoEst.getDescrEstesa()) + "';");
							}
						}
						else if(valoreVariabileCfg.getValoreEsterno() == ValoreVariabileCfg.DA_TABELLA_ESTERNA) {
							String desc = loadDescValEst(variabileCfg, valoreVariabileCfg, value);
							out.println("  var field = eval('document.forms[0].' + idFromName['Esterna'+varCfg+'.Descrizione']);");
							out.println("  field.value = '" + WebElement.formatStringForHTML(desc) + "';");
						}
						//abilitaImmagineBut(out, seq); //Fix 35143
					}
					else {//if (getMode() == WebForm.NEW) { //Fix 19225
						if (!additionalSintesi.equals(""))
							additionalSintesi += separator;
						additionalSintesi += idVariabile + separator + value + separator + idValore;
					}
					s = s.substring(s.indexOf(separator) + 1);
					s = s.substring(s.indexOf(separator) + 1);
					if (s.indexOf(separator) > 0)
						s = s.substring(s.indexOf(separator) + 1);
				}
				//out.println("  oldSintesi = buildSintesiConfig();");//Fix 11994
				//out.println("  additionalSintesi = \"" + webForm.formatStringForHTML(additionalSintesi) + "\";"); //Fix 14167
			}
			else if (errs != null && !errs.isEmpty()) {
				String errsTxt = "";
				Iterator iter = errs.iterator();
				while (iter.hasNext())
					errsTxt += "\n" + ((ErrorMessage) iter.next()).getLongText();
				out.println("alert(\"Errori in esecuzione delle macro associate" + WebElement.formatStringForHTML(errsTxt) + "\");"); //Fix 12084
			}
			//out.println("  eval('document.forms[0].' + idFromName['StatoSezioneCfg']).disabled = true;");
			out.println("  var tipoCodiceCfg = '" + configurazione.getSchemaCfg().getTipoCodiceCfg() + "';");
			
			count++;
		}
		out.println("</script>");
	}

	public String getTextInLingua(DescrizioneInLingua descrLingua) {
		String descrizione = descrLingua.getHandler().getText("Descrizione", UtenteAzienda.getUtenteAziendaConnesso().getLanguage());
		return descrizione == null ? descrLingua.getDescrizione() : descrizione;
	}

	protected String loadDescValEst(VariabileSchemaCfg variabileCfg, ValoreVariabileCfg valoreVariabileCfg, String value) {
		return Configurazione.loadDescValEst( variabileCfg, valoreVariabileCfg, value, null);
	}

	public CatalEsterno getCatalogoEsterno(String idAzienda, String idCatalogo){
		String key = KeyHelper.buildObjectKey(new String[]{idAzienda, idCatalogo});
		try{
			return CatalEsterno.elementWithKey(key, PersistentObject.NO_LOCK);
		}
		catch(Exception ex){
			ex.printStackTrace(Trace.excStream);
		}
		return null;
	}

}
