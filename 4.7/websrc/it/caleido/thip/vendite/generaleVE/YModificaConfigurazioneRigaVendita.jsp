<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.0.0/websrcsvil/dtd/xhtml1-transitional.dtd">
<%@page import="it.thera.thip.cs.ColonneFiltri"%>
<html>
<!-- WIZGEN Therm 2.0.0 as Form - multiBrowserGen = true -->
<%=WebGenerator.writeRuntimeInfo()%>
<head>
<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= " 
  java.sql.*, 
  java.util.*, 
  java.lang.reflect.*, 
  javax.naming.*, 
  com.thera.thermfw.common.*, 
  com.thera.thermfw.type.*, 
  com.thera.thermfw.web.*, 
  com.thera.thermfw.security.*, 
  com.thera.thermfw.base.*, 
  com.thera.thermfw.ad.*, 
  com.thera.thermfw.persist.*, 
  com.thera.thermfw.gui.cnr.*, 
  com.thera.thermfw.setting.*, 
  com.thera.thermfw.collector.*, 
  com.thera.thermfw.batch.web.*, 
  com.thera.thermfw.batch.*, 
  com.thera.thermfw.pref.* 
"%> 
<%
  ServletEnvironment se = (ServletEnvironment)Factory.createObject("com.thera.thermfw.web.ServletEnvironment"); 
  BODataCollector YModificaConfRigheVenBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YModificaConfRigheVenForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YModificaConfRigheVenForm", "YModificaConfRigheVen", null, "it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRigaVenditaFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/caleido/thip/vendite/generaleVE/YModificaConfigurazioneRigaVendita.js"); 
  YModificaConfRigheVenForm.setServletEnvironment(se); 
  YModificaConfRigheVenForm.setJSTypeList(jsList); 
  YModificaConfRigheVenForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YModificaConfRigheVenForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YModificaConfRigheVenForm.setWebFormModifierClass("it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRigaVenditaFormModifier"); 
  YModificaConfRigheVenForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YModificaConfRigheVenForm.getMode(); 
  String key = YModificaConfRigheVenForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  boolean conflitPresent = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YModificaConfRigheVenForm.outTraceInfo(getClass().getName()); 
        String collectorName = YModificaConfRigheVenForm.findBODataCollectorName(); 
                YModificaConfRigheVenBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YModificaConfRigheVenBODC instanceof WebDataCollector) 
            ((WebDataCollector)YModificaConfRigheVenBODC).setServletEnvironment(se); 
        YModificaConfRigheVenBODC.initialize("YModificaConfRigheVen", true, 0); 
        YModificaConfRigheVenForm.setBODataCollector(YModificaConfRigheVenBODC); 
        int rcBODC = YModificaConfRigheVenForm.initSecurityServices(); 
        mode = YModificaConfRigheVenForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YModificaConfRigheVenForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YModificaConfRigheVenBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YModificaConfRigheVenForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
</head>
<script language="JavaScript1.2">
var fsep = '<%=PersistentObject.KEY_SEPARATOR%>';
var sezSepForSave =  '<%=ColonneFiltri.SEP%>';
</script>
<body onbeforeunload="<%=YModificaConfRigheVenForm.getBodyOnBeforeUnload()%>" onload="<%=YModificaConfRigheVenForm.getBodyOnLoad()%>" onunload="<%=YModificaConfRigheVenForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YModificaConfRigheVenForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YModificaConfRigheVenForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YModificaConfRigheVenBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YModificaConfRigheVenForm.getServlet()%>" method="post" name="YModificaConfRigheVenForm" style="height:100%"><%
  YModificaConfRigheVenForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" id="emptyborder" width="100%">
			<!--    <tr> -->
			<!--     <td style="height: 0"><span class="toolbar" id="myToolBar" /></td> -->
			<!--    </tr> -->
			<tr>
				<td><% 
  WebTextInput YModificaConfRigheVenIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YModificaConfRigheVen", "IdAzienda"); 
  YModificaConfRigheVenIdAzienda.setParent(YModificaConfRigheVenForm); 
%>
<input class="<%=YModificaConfRigheVenIdAzienda.getClassType()%>" id="<%=YModificaConfRigheVenIdAzienda.getId()%>" maxlength="<%=YModificaConfRigheVenIdAzienda.getMaxLength()%>" name="<%=YModificaConfRigheVenIdAzienda.getName()%>" size="<%=YModificaConfRigheVenIdAzienda.getSize()%>" type="hidden"><% 
  YModificaConfRigheVenIdAzienda.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaConfRigheVenClassName =  
     new com.thera.thermfw.web.WebTextInput("YModificaConfRigheVen", "ClassName"); 
  YModificaConfRigheVenClassName.setParent(YModificaConfRigheVenForm); 
%>
<input class="<%=YModificaConfRigheVenClassName.getClassType()%>" id="<%=YModificaConfRigheVenClassName.getId()%>" maxlength="<%=YModificaConfRigheVenClassName.getMaxLength()%>" name="<%=YModificaConfRigheVenClassName.getName()%>" size="<%=YModificaConfRigheVenClassName.getSize()%>" type="hidden"><% 
  YModificaConfRigheVenClassName.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaConfRigheVenIdConfigurazione =  
     new com.thera.thermfw.web.WebTextInput("YModificaConfRigheVen", "IdConfigurazione"); 
  YModificaConfRigheVenIdConfigurazione.setParent(YModificaConfRigheVenForm); 
%>
<input class="<%=YModificaConfRigheVenIdConfigurazione.getClassType()%>" id="<%=YModificaConfRigheVenIdConfigurazione.getId()%>" maxlength="<%=YModificaConfRigheVenIdConfigurazione.getMaxLength()%>" name="<%=YModificaConfRigheVenIdConfigurazione.getName()%>" size="<%=YModificaConfRigheVenIdConfigurazione.getSize()%>" type="hidden"><% 
  YModificaConfRigheVenIdConfigurazione.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><input id="IdSchemaCfg" name="IdSchemaCfg" type="hidden"></td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaConfRigheVenSintesiConfigurazione =  
     new com.thera.thermfw.web.WebTextInput("YModificaConfRigheVen", "SintesiConfigurazione"); 
  YModificaConfRigheVenSintesiConfigurazione.setParent(YModificaConfRigheVenForm); 
%>
<input class="<%=YModificaConfRigheVenSintesiConfigurazione.getClassType()%>" id="<%=YModificaConfRigheVenSintesiConfigurazione.getId()%>" maxlength="<%=YModificaConfRigheVenSintesiConfigurazione.getMaxLength()%>" name="<%=YModificaConfRigheVenSintesiConfigurazione.getName()%>" size="<%=YModificaConfRigheVenSintesiConfigurazione.getSize()%>" type="hidden"><% 
  YModificaConfRigheVenSintesiConfigurazione.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaConfRigheVenChiaviSelezionati =  
     new com.thera.thermfw.web.WebTextInput("YModificaConfRigheVen", "ChiaviSelezionati"); 
  YModificaConfRigheVenChiaviSelezionati.setParent(YModificaConfRigheVenForm); 
%>
<input class="<%=YModificaConfRigheVenChiaviSelezionati.getClassType()%>" id="<%=YModificaConfRigheVenChiaviSelezionati.getId()%>" maxlength="<%=YModificaConfRigheVenChiaviSelezionati.getMaxLength()%>" name="<%=YModificaConfRigheVenChiaviSelezionati.getName()%>" size="<%=YModificaConfRigheVenChiaviSelezionati.getSize()%>" type="hidden"><% 
  YModificaConfRigheVenChiaviSelezionati.write(out); 
%>
</td>
			</tr>
			<tr style="margin-top:5px">
				<td>
					<table>
						<tr>
								<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaConfRigheVen", "IdEsternoConfig", null); 
   label.setParent(YModificaConfRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Configurazione"><%label.write(out);%></label><%}%></td>
								<td><% 
  WebMultiSearchForm YModificaConfRigheVenConfigurazione =  
     new com.thera.thermfw.web.WebMultiSearchForm("YModificaConfRigheVen", "Configurazione", false, false, true, 1, null, null); 
  YModificaConfRigheVenConfigurazione.setParent(YModificaConfRigheVenForm); 
  YModificaConfRigheVenConfigurazione.write(out); 
%>
<!--<span class="multisearchform" id="Configurazione"></span>--></td>
								<td><button id="bottoneConferma" name="bottoneConferma" onclick="conferma()" style="width: 125px; display: inline; margin-top: 2.5rem; margin-left: 1rem" type="button">Conferma</button></td>
							</tr>
					</table>
				</td>
				
			</tr>
		
			<tr>
				<td>
					<table>
						<tr id>
							<td>
								<table>
									 <%
						            it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRowForm ConfigurazioneconfigVarRow = (it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRowForm)Factory.createObject(it.caleido.thip.vendite.generaleVE.web.YModificaConfigurazioneRowForm.class);
						            ConfigurazioneconfigVarRow.setParent(YModificaConfRigheVenForm);
						            ConfigurazioneconfigVarRow.write(out);
						            %>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YModificaConfRigheVenForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
		<iframe frameborder="0" id="ValoriSearchWin" marginheight="0" marginwidth="0" onmouseout="onframeout();setCurrentEvent(event);" onmouseover="onframeover();setCurrentEvent(event);" scrolling="yes" style="position: absolute; left: 0; top: 0; width:0; height:0; border:1px solid #6579A0; margin: 3px 0px 0px 0px">
<!-- FIX 12703 fine -->
</iframe>
	<%
  YModificaConfRigheVenForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YModificaConfRigheVenForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YModificaConfRigheVenBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YModificaConfRigheVenForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YModificaConfRigheVenBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YModificaConfRigheVenBODC.getErrorList().getErrors()); 
           if(YModificaConfRigheVenBODC.getConflict() != null) 
                conflitPresent = true; 
     } 
     else 
        errors.add(new ErrorMessage("BAS0000010")); 
  } 
  catch(NamingException e) { 
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("CBS000025", errorMessage));  } 
  catch(SQLException e) {
     errorMessage = e.getMessage(); 
     errors.add(new ErrorMessage("BAS0000071", errorMessage));  } 
  catch(Throwable e) {
     e.printStackTrace(Trace.excStream);
  }
  finally 
  {
     if(YModificaConfRigheVenBODC != null && !YModificaConfRigheVenBODC.close(false)) 
        errors.addAll(0, YModificaConfRigheVenBODC.getErrorList().getErrors()); 
     try 
     { 
        se.end(); 
     }
     catch(IllegalArgumentException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(Trace.excStream); 
     } 
  } 
  if(!errors.isEmpty())
  { 
      if(!conflitPresent)
  { 
     request.setAttribute("ErrorMessages", errors); 
     String errorPage = YModificaConfRigheVenForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YModificaConfRigheVenBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YModificaConfRigheVenForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
