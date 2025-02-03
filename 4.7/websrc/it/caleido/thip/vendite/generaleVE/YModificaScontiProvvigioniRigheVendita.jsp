<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.0.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector YModificaScProvRigheVenBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YModificaScProvRigheVenForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YModificaScProvRigheVenForm", "YModificaScProvRigheVen", null, "it.caleido.thip.vendite.generaleVE.web.YModificaScontiProvvigioniRigheVenditaFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/caleido/thip/vendite/generaleVE/YModificaScontiProvvigioniRigheVendita.js"); 
  YModificaScProvRigheVenForm.setServletEnvironment(se); 
  YModificaScProvRigheVenForm.setJSTypeList(jsList); 
  YModificaScProvRigheVenForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YModificaScProvRigheVenForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YModificaScProvRigheVenForm.setWebFormModifierClass("it.caleido.thip.vendite.generaleVE.web.YModificaScontiProvvigioniRigheVenditaFormModifier"); 
  YModificaScProvRigheVenForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YModificaScProvRigheVenForm.getMode(); 
  String key = YModificaScProvRigheVenForm.getKey(); 
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
        YModificaScProvRigheVenForm.outTraceInfo(getClass().getName()); 
        String collectorName = YModificaScProvRigheVenForm.findBODataCollectorName(); 
                YModificaScProvRigheVenBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YModificaScProvRigheVenBODC instanceof WebDataCollector) 
            ((WebDataCollector)YModificaScProvRigheVenBODC).setServletEnvironment(se); 
        YModificaScProvRigheVenBODC.initialize("YModificaScProvRigheVen", true, 0); 
        YModificaScProvRigheVenForm.setBODataCollector(YModificaScProvRigheVenBODC); 
        int rcBODC = YModificaScProvRigheVenForm.initSecurityServices(); 
        mode = YModificaScProvRigheVenForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YModificaScProvRigheVenForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YModificaScProvRigheVenBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YModificaScProvRigheVenForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
</head>
<body onbeforeunload="<%=YModificaScProvRigheVenForm.getBodyOnBeforeUnload()%>" onload="<%=YModificaScProvRigheVenForm.getBodyOnLoad()%>" onunload="<%=YModificaScProvRigheVenForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YModificaScProvRigheVenForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YModificaScProvRigheVenForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YModificaScProvRigheVenBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YModificaScProvRigheVenForm.getServlet()%>" method="post" name="YModificaScProvRigheVenForm" style="height:100%"><%
  YModificaScProvRigheVenForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
			<!--    <tr> -->
			<!--     <td style="height: 0"><span class="toolbar" id="myToolBar" /></td> -->
			<!--    </tr> -->
			<tr>
				<td><% 
  WebTextInput YModificaScProvRigheVenIdAzienda =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "IdAzienda"); 
  YModificaScProvRigheVenIdAzienda.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenIdAzienda.getClassType()%>" id="<%=YModificaScProvRigheVenIdAzienda.getId()%>" maxlength="<%=YModificaScProvRigheVenIdAzienda.getMaxLength()%>" name="<%=YModificaScProvRigheVenIdAzienda.getName()%>" size="<%=YModificaScProvRigheVenIdAzienda.getSize()%>" type="hidden"><% 
  YModificaScProvRigheVenIdAzienda.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaScProvRigheVenClassName =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "ClassName"); 
  YModificaScProvRigheVenClassName.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenClassName.getClassType()%>" id="<%=YModificaScProvRigheVenClassName.getId()%>" maxlength="<%=YModificaScProvRigheVenClassName.getMaxLength()%>" name="<%=YModificaScProvRigheVenClassName.getName()%>" size="<%=YModificaScProvRigheVenClassName.getSize()%>" type="hidden"><% 
  YModificaScProvRigheVenClassName.write(out); 
%>
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YModificaScProvRigheVenChiaviSelezionati =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "ChiaviSelezionati"); 
  YModificaScProvRigheVenChiaviSelezionati.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenChiaviSelezionati.getClassType()%>" id="<%=YModificaScProvRigheVenChiaviSelezionati.getId()%>" maxlength="<%=YModificaScProvRigheVenChiaviSelezionati.getMaxLength()%>" name="<%=YModificaScProvRigheVenChiaviSelezionati.getName()%>" size="<%=YModificaScProvRigheVenChiaviSelezionati.getSize()%>" type="hidden"><% 
  YModificaScProvRigheVenChiaviSelezionati.write(out); 
%>
</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td>
								<fieldset style="width: fit-content;display: inline;">
									<legend>Sconti</legend>
									<table>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "ScontoArticolo1", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="ScontoArticolo1"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YModificaScProvRigheVenScontoArticolo1 =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "ScontoArticolo1"); 
  YModificaScProvRigheVenScontoArticolo1.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenScontoArticolo1.getClassType()%>" id="<%=YModificaScProvRigheVenScontoArticolo1.getId()%>" maxlength="<%=YModificaScProvRigheVenScontoArticolo1.getMaxLength()%>" name="<%=YModificaScProvRigheVenScontoArticolo1.getName()%>" size="<%=YModificaScProvRigheVenScontoArticolo1.getSize()%>" type="text"><% 
  YModificaScProvRigheVenScontoArticolo1.write(out); 
%>
</td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "ScontoArticolo2", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="ScontoArticolo2"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YModificaScProvRigheVenScontoArticolo2 =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "ScontoArticolo2"); 
  YModificaScProvRigheVenScontoArticolo2.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenScontoArticolo2.getClassType()%>" id="<%=YModificaScProvRigheVenScontoArticolo2.getId()%>" maxlength="<%=YModificaScProvRigheVenScontoArticolo2.getMaxLength()%>" name="<%=YModificaScProvRigheVenScontoArticolo2.getName()%>" size="<%=YModificaScProvRigheVenScontoArticolo2.getSize()%>" type="text"><% 
  YModificaScProvRigheVenScontoArticolo2.write(out); 
%>
</td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "Maggiorazione", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Maggiorazione"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YModificaScProvRigheVenMaggiorazione =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "Maggiorazione"); 
  YModificaScProvRigheVenMaggiorazione.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenMaggiorazione.getClassType()%>" id="<%=YModificaScProvRigheVenMaggiorazione.getId()%>" maxlength="<%=YModificaScProvRigheVenMaggiorazione.getMaxLength()%>" name="<%=YModificaScProvRigheVenMaggiorazione.getName()%>" size="<%=YModificaScProvRigheVenMaggiorazione.getSize()%>" type="text"><% 
  YModificaScProvRigheVenMaggiorazione.write(out); 
%>
</td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "IdSconto", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Sconto"><%label.write(out);%></label><%}%></td>
											<td colspan="2"><% 
  WebMultiSearchForm YModificaScProvRigheVenSconto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YModificaScProvRigheVen", "Sconto", false, false, true, 1, null, null); 
  YModificaScProvRigheVenSconto.setParent(YModificaScProvRigheVenForm); 
  YModificaScProvRigheVenSconto.write(out); 
%>
<!--<span class="multisearchform" id="Sconto"></span>--></td>
										</tr>
									</table>
								</fieldset>
								<button id="bottoneConferma" name="bottoneConferma" onclick="conferma()" style="width: 125px; display: inline; margin-top: 2.5rem; margin-left: 1rem" type="button">Conferma modifica</button>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="width: fit-content;">
									<legend>Provvigioni</legend>
									<table>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "IdAgente", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Agente"><%label.write(out);%></label><%}%></td>
											<td colspan="2"><% 
  WebMultiSearchForm YModificaScProvRigheVenAgente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YModificaScProvRigheVen", "Agente", false, false, true, 1, null, null); 
  YModificaScProvRigheVenAgente.setParent(YModificaScProvRigheVenForm); 
  YModificaScProvRigheVenAgente.write(out); 
%>
<!--<span class="multisearchform" id="Agente"></span>--></td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "Provvigione2Agente", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Provvigione2Agente"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YModificaScProvRigheVenProvvigione2Agente =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "Provvigione2Agente"); 
  YModificaScProvRigheVenProvvigione2Agente.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenProvvigione2Agente.getClassType()%>" id="<%=YModificaScProvRigheVenProvvigione2Agente.getId()%>" maxlength="<%=YModificaScProvRigheVenProvvigione2Agente.getMaxLength()%>" name="<%=YModificaScProvRigheVenProvvigione2Agente.getName()%>" size="<%=YModificaScProvRigheVenProvvigione2Agente.getSize()%>" type="text"><% 
  YModificaScProvRigheVenProvvigione2Agente.write(out); 
%>
</td>
											<td><% 
  WebTextInput YModificaScProvRigheVenAlfanumRiservatoUtente1 =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "AlfanumRiservatoUtente1"); 
  YModificaScProvRigheVenAlfanumRiservatoUtente1.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenAlfanumRiservatoUtente1.getClassType()%>" id="<%=YModificaScProvRigheVenAlfanumRiservatoUtente1.getId()%>" maxlength="<%=YModificaScProvRigheVenAlfanumRiservatoUtente1.getMaxLength()%>" name="<%=YModificaScProvRigheVenAlfanumRiservatoUtente1.getName()%>" size="<%=YModificaScProvRigheVenAlfanumRiservatoUtente1.getSize()%>" type="text"><% 
  YModificaScProvRigheVenAlfanumRiservatoUtente1.write(out); 
%>
</td>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "FlagRiservatoUtente1", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="FlagRiservatoUtente1"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebComboBox YModificaScProvRigheVenFlagRiservatoUtente1 =  
     new com.thera.thermfw.web.WebComboBox("YModificaScProvRigheVen", "FlagRiservatoUtente1", null); 
  YModificaScProvRigheVenFlagRiservatoUtente1.setParent(YModificaScProvRigheVenForm); 
%>
<select id="<%=YModificaScProvRigheVenFlagRiservatoUtente1.getId()%>" name="<%=YModificaScProvRigheVenFlagRiservatoUtente1.getName()%>"><% 
  YModificaScProvRigheVenFlagRiservatoUtente1.write(out); 
%> 

													
											</select></td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "IdSubagente", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Subagente"><%label.write(out);%></label><%}%></td>
											<td colspan="2"><% 
  WebMultiSearchForm YModificaScProvRigheVenSubagente =  
     new com.thera.thermfw.web.WebMultiSearchForm("YModificaScProvRigheVen", "Subagente", false, false, true, 1, null, null); 
  YModificaScProvRigheVenSubagente.setParent(YModificaScProvRigheVenForm); 
  YModificaScProvRigheVenSubagente.write(out); 
%>
<!--<span class="multisearchform" id="Subagente"></span>--></td>
										</tr>
										<tr>
											<td><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YModificaScProvRigheVen", "Provvigione2Subagente", null); 
   label.setParent(YModificaScProvRigheVenForm); 
%><label class="<%=label.getClassType()%>" for="Provvigione2Subagente"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YModificaScProvRigheVenProvvigione2Subagente =  
     new com.thera.thermfw.web.WebTextInput("YModificaScProvRigheVen", "Provvigione2Subagente"); 
  YModificaScProvRigheVenProvvigione2Subagente.setParent(YModificaScProvRigheVenForm); 
%>
<input class="<%=YModificaScProvRigheVenProvvigione2Subagente.getClassType()%>" id="<%=YModificaScProvRigheVenProvvigione2Subagente.getId()%>" maxlength="<%=YModificaScProvRigheVenProvvigione2Subagente.getMaxLength()%>" name="<%=YModificaScProvRigheVenProvvigione2Subagente.getName()%>" size="<%=YModificaScProvRigheVenProvvigione2Subagente.getSize()%>" type="text"><% 
  YModificaScProvRigheVenProvvigione2Subagente.write(out); 
%>
</td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YModificaScProvRigheVenForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YModificaScProvRigheVenForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YModificaScProvRigheVenForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YModificaScProvRigheVenBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YModificaScProvRigheVenForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YModificaScProvRigheVenBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YModificaScProvRigheVenBODC.getErrorList().getErrors()); 
           if(YModificaScProvRigheVenBODC.getConflict() != null) 
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
     if(YModificaScProvRigheVenBODC != null && !YModificaScProvRigheVenBODC.close(false)) 
        errors.addAll(0, YModificaScProvRigheVenBODC.getErrorList().getErrors()); 
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
     String errorPage = YModificaScProvRigheVenForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YModificaScProvRigheVenBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YModificaScProvRigheVenForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
