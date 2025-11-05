<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/5.1.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector YUdsAcquistoBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YUdsAcquistoForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YUdsAcquistoForm", "YUdsAcquisto", null, "it.softre.thip.acquisti.uds.web.YUdsAcquistoEstrattoFormActionAdapter", false, false, true, true, true, true, null, 1, true, "it/softre/thip/acquisti/uds/YUdsAcquisto.js"); 
  YUdsAcquistoForm.setServletEnvironment(se); 
  YUdsAcquistoForm.setJSTypeList(jsList); 
  YUdsAcquistoForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  YUdsAcquistoForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YUdsAcquistoForm.setWebFormModifierClass("it.softre.thip.acquisti.uds.web.YUdsAcquistoFormModifier"); 
  YUdsAcquistoForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YUdsAcquistoForm.getMode(); 
  String key = YUdsAcquistoForm.getKey(); 
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
        YUdsAcquistoForm.outTraceInfo(getClass().getName()); 
        String collectorName = YUdsAcquistoForm.findBODataCollectorName(); 
                YUdsAcquistoBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YUdsAcquistoBODC instanceof WebDataCollector) 
            ((WebDataCollector)YUdsAcquistoBODC).setServletEnvironment(se); 
        YUdsAcquistoBODC.initialize("YUdsAcquisto", true, 1); 
        YUdsAcquistoForm.setBODataCollector(YUdsAcquistoBODC); 
        int rcBODC = YUdsAcquistoForm.initSecurityServices(); 
        mode = YUdsAcquistoForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YUdsAcquistoForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YUdsAcquistoBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YUdsAcquistoForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

</head>
<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YUdsAcquistoForm.getBodyOnBeforeUnload()%>" onload="<%=YUdsAcquistoForm.getBodyOnLoad()%>" onunload="<%=YUdsAcquistoForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YUdsAcquistoForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YUdsAcquistoForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YUdsAcquistoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YUdsAcquistoForm.getServlet()%>" method="post" name="YUdsAcquistoForm" style="height:100%"><%
  YUdsAcquistoForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" class="resTableEstratto" id="emptyborder" width="100%">
			<tr>
				<td colspan="2">
					<table style="width: 100%; margin-top: -4px">
						<tr>
							<td colspan="2" style="padding-right: 5px; padding-left: 2px;">
								<fieldset style="width: 100%;">
									<legend>Riferimenti testata</legend>
									<table style="width: 100%; height: 100%; margin-top: -15px">
										<tr>
											<td>
												<table>
													<tr>
														<td>
															<fieldset id="FieldsetForn" style="width: 100%">
																<legend>Fornitore</legend>
																<table>
																	<tr>
																		<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelFornitore", false, false, true, 1, null, null); 
  YUdsAcquistoRelFornitore.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelFornitore.write(out); 
%>
<!--<span class="multisearchform" id="RelFornitore"></span>--></td>
																	</tr>
																</table>
															</fieldset>
														</td>

													</tr>
													<tr>
														<td colspan="2" style="visibility: hidden">
															<fieldset id="FieldsetDoc" style="width: 65%">
																<legend>Documento acquisto</legend>
																<table>
																	<tr>
																		<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelDocumentoAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelDocumentoAcquisto", true, false, true, 2, null, null); 
  YUdsAcquistoRelDocumentoAcquisto.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelDocumentoAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelDocumentoAcquisto"></span>--></td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td colspan="2" style="visibility: hidden">
															<fieldset id="FieldsetOrd" style="width: 65%">
																<legend>Ordine acquisto</legend>
																<table>
																	<tr>
																		<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelOrdineAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelOrdineAcquisto", true, false, true, 2, null, null); 
  YUdsAcquistoRelOrdineAcquisto.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelOrdineAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineAcquisto"></span>--></td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td colspan="1">
															<fieldset id="FieldsetChild" style="width: 18%">
																<legend>Numero figli</legend>
																<table>
																	<tr>
																		<td valign="top"><input id="NFigli" name="NFigli" readonly style="width: 40px; background: rgb(232, 232, 232);"></td>
																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
												</table>
											</td>
											<td>
												<table style="width: 100%">
													<tr>
														<td>
															<fieldset id="FieldsetChild" style="width: 30%">
																<legend>Note</legend>
																<table>
																	<tr>
																		<td valign="top"><% 
  WebTextInput YUdsAcquistoNote =  
     new com.thera.thermfw.web.WebTextArea("YUdsAcquisto", "Note"); 
  YUdsAcquistoNote.setParent(YUdsAcquistoForm); 
%>
<textarea class="<%=YUdsAcquistoNote.getClassType()%>" cols="60" id="<%=YUdsAcquistoNote.getId()%>" maxlength="<%=YUdsAcquistoNote.getMaxLength()%>" name="<%=YUdsAcquistoNote.getName()%>" rows="5" size="<%=YUdsAcquistoNote.getSize()%>" style="width: 480px; height: 68px;"></textarea><% 
  YUdsAcquistoNote.write(out); 
%>
</td>

																	</tr>
																</table>
															</fieldset>
														</td>
													</tr>
													<tr>
														<td>
															<fieldset id="FieldsetChild" style="width: 20%">
																<legend>Uds</legend>
																<table>
																	<tr>
																		<td valign="top"><% 
  WebTextInput YUdsAcquistoIdUds =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "IdUds"); 
  YUdsAcquistoIdUds.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoIdUds.getClassType()%>" id="<%=YUdsAcquistoIdUds.getId()%>" maxlength="<%=YUdsAcquistoIdUds.getMaxLength()%>" name="<%=YUdsAcquistoIdUds.getName()%>" size="<%=YUdsAcquistoIdUds.getSize()%>"><% 
  YUdsAcquistoIdUds.write(out); 
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
									</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	<%
  YUdsAcquistoForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YUdsAcquistoForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YUdsAcquistoBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YUdsAcquistoForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YUdsAcquistoBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YUdsAcquistoBODC.getErrorList().getErrors()); 
           if(YUdsAcquistoBODC.getConflict() != null) 
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
     if(YUdsAcquistoBODC != null && !YUdsAcquistoBODC.close(false)) 
        errors.addAll(0, YUdsAcquistoBODC.getErrorList().getErrors()); 
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
     String errorPage = YUdsAcquistoForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YUdsAcquistoBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YUdsAcquistoForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
