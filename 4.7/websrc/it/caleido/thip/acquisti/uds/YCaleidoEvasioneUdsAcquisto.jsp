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
  BODataCollector YCaleidoEvasioneUdsAcquisBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YCaleidoEvasioneUdsAcquisForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YCaleidoEvasioneUdsAcquisForm", "YCaleidoEvasioneUdsAcquis", null, "it.caleido.thip.acquisti.uds.web.YCaleidoEvasioneUdsAcqFormActionAdapter", false, false, false, true, true, true, null, 1, true, "it/caleido/thip/acquisti/uds/YCaleidoEvasioneUdsAcquisto.js"); 
  YCaleidoEvasioneUdsAcquisForm.setServletEnvironment(se); 
  YCaleidoEvasioneUdsAcquisForm.setJSTypeList(jsList); 
  YCaleidoEvasioneUdsAcquisForm.setHeader("it.thera.thip.cs.Header.jsp"); 
  YCaleidoEvasioneUdsAcquisForm.setFooter("it.thera.thip.cs.Footer.jsp"); 
  YCaleidoEvasioneUdsAcquisForm.setWebFormModifierClass("it.caleido.thip.acquisti.uds.web.YCaleidoEvasioneUdsAcqFormModifier"); 
  YCaleidoEvasioneUdsAcquisForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YCaleidoEvasioneUdsAcquisForm.getMode(); 
  String key = YCaleidoEvasioneUdsAcquisForm.getKey(); 
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
        YCaleidoEvasioneUdsAcquisForm.outTraceInfo(getClass().getName()); 
        String collectorName = YCaleidoEvasioneUdsAcquisForm.findBODataCollectorName(); 
                YCaleidoEvasioneUdsAcquisBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YCaleidoEvasioneUdsAcquisBODC instanceof WebDataCollector) 
            ((WebDataCollector)YCaleidoEvasioneUdsAcquisBODC).setServletEnvironment(se); 
        YCaleidoEvasioneUdsAcquisBODC.initialize("YCaleidoEvasioneUdsAcquis", true, 1); 
        YCaleidoEvasioneUdsAcquisForm.setBODataCollector(YCaleidoEvasioneUdsAcquisBODC); 
        int rcBODC = YCaleidoEvasioneUdsAcquisForm.initSecurityServices(); 
        mode = YCaleidoEvasioneUdsAcquisForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YCaleidoEvasioneUdsAcquisForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YCaleidoEvasioneUdsAcquisBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YCaleidoEvasioneUdsAcquisForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<title>Evasione Uds Acquisto Caleido</title>
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YCaleidoEvasioneUdsAcquisForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>

<body bottommargin="0" leftmargin="0" onbeforeunload="<%=YCaleidoEvasioneUdsAcquisForm.getBodyOnBeforeUnload()%>" onload="<%=YCaleidoEvasioneUdsAcquisForm.getBodyOnLoad()%>" onunload="<%=YCaleidoEvasioneUdsAcquisForm.getBodyOnUnload()%>" rightmargin="0" topmargin="0"><%
   YCaleidoEvasioneUdsAcquisForm.writeBodyStartElements(out); 
%> 


	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YCaleidoEvasioneUdsAcquisForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YCaleidoEvasioneUdsAcquisBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YCaleidoEvasioneUdsAcquisForm.getServlet()%>" method="post" name="YCaleidoEvasioneUdsAcqForm" style="height:100%"><%
  YCaleidoEvasioneUdsAcquisForm.writeFormStartElements(out); 
%>

		<table border="0" cellpadding="1" cellspacing="1" height="100%" width="100%">
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td nowrap width="120"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "DataDocumento", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="DataDocumento"><%label.write(out);%></label><%}%></td>
							<td colspan="3"><% 
  WebTextInput YCaleidoEvasioneUdsAcquisDataDocumento =  
     new com.thera.thermfw.web.WebTextInput("YCaleidoEvasioneUdsAcquis", "DataDocumento"); 
  YCaleidoEvasioneUdsAcquisDataDocumento.setShowCalendarBtn(true); 
  YCaleidoEvasioneUdsAcquisDataDocumento.setParent(YCaleidoEvasioneUdsAcquisForm); 
%>
<input class="<%=YCaleidoEvasioneUdsAcquisDataDocumento.getClassType()%>" id="<%=YCaleidoEvasioneUdsAcquisDataDocumento.getId()%>" maxlength="<%=YCaleidoEvasioneUdsAcquisDataDocumento.getMaxLength()%>" name="<%=YCaleidoEvasioneUdsAcquisDataDocumento.getName()%>" size="12" type="text"><% 
  YCaleidoEvasioneUdsAcquisDataDocumento.write(out); 
%>
</td>
						</tr>
						<tr>
							<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "IdFornitore", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="Fornitore"><%label.write(out);%></label><%}%></td>
							<td valign="top"><% 
  WebMultiSearchForm YCaleidoEvasioneUdsAcquisFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCaleidoEvasioneUdsAcquis", "Fornitore", false, false, true, 1, null, null); 
  YCaleidoEvasioneUdsAcquisFornitore.setParent(YCaleidoEvasioneUdsAcquisForm); 
  YCaleidoEvasioneUdsAcquisFornitore.write(out); 
%>
<!--<span class="multisearchform" id="Fornitore"></span>--></td>
						</tr>
						<tr>
							<td colspan="2">
								<fieldset style="width: fit-content">
									<legend>Parametri Ordine Acquisto</legend>
									<table>
										<tr style="display: none;">
											<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "IdNumeratoreOrdAcq", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="NumeratoreOrdAcq"><%label.write(out);%></label><%}%></td>
											<td valign="top"><% 
  WebMultiSearchForm YCaleidoEvasioneUdsAcquisNumeratoreOrdAcq =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCaleidoEvasioneUdsAcquis", "NumeratoreOrdAcq", false, false, true, 1, null, null); 
  YCaleidoEvasioneUdsAcquisNumeratoreOrdAcq.setParent(YCaleidoEvasioneUdsAcquisForm); 
  YCaleidoEvasioneUdsAcquisNumeratoreOrdAcq.write(out); 
%>
<!--<span class="multisearchform" id="NumeratoreOrdAcq"></span>--></td>
										</tr>
										<tr>
											<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "IdSerieOrdAcq", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="SerieOrdAcq"><%label.write(out);%></label><%}%></td>
											<td valign="top"><% 
  WebMultiSearchForm YCaleidoEvasioneUdsAcquisSerieOrdAcq =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCaleidoEvasioneUdsAcquis", "SerieOrdAcq", false, false, true, 1, null, null); 
  YCaleidoEvasioneUdsAcquisSerieOrdAcq.setParent(YCaleidoEvasioneUdsAcquisForm); 
  YCaleidoEvasioneUdsAcquisSerieOrdAcq.write(out); 
%>
<!--<span class="multisearchform" id="SerieOrdAcq"></span>--></td>
										</tr>
										<tr>
											<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "IdCauOrdAcqTes", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="CauOrdAcqTes"><%label.write(out);%></label><%}%></td>
											<td valign="top"><% 
  WebMultiSearchForm YCaleidoEvasioneUdsAcquisCauOrdAcqTes =  
     new com.thera.thermfw.web.WebMultiSearchForm("YCaleidoEvasioneUdsAcquis", "CauOrdAcqTes", false, false, true, 1, null, null); 
  YCaleidoEvasioneUdsAcquisCauOrdAcqTes.setParent(YCaleidoEvasioneUdsAcquisForm); 
  YCaleidoEvasioneUdsAcquisCauOrdAcqTes.setOnKeyChange("checkCampiObbligatori()"); 
  YCaleidoEvasioneUdsAcquisCauOrdAcqTes.write(out); 
%>
<!--<span class="multisearchform" id="CauOrdAcqTes"></span>--></td>
										</tr>
									</table>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<fieldset id="riferimenti" style="width: fit-content; display: none;">
									<legend>Riferimenti</legend>
									<table>
										<tr>
											<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "DataRifIntestatario", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="DataRifIntestatario"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YCaleidoEvasioneUdsAcquisDataRifIntestatario =  
     new com.thera.thermfw.web.WebTextInput("YCaleidoEvasioneUdsAcquis", "DataRifIntestatario"); 
  YCaleidoEvasioneUdsAcquisDataRifIntestatario.setShowCalendarBtn(true); 
  YCaleidoEvasioneUdsAcquisDataRifIntestatario.setParent(YCaleidoEvasioneUdsAcquisForm); 
%>
<input class="<%=YCaleidoEvasioneUdsAcquisDataRifIntestatario.getClassType()%>" id="<%=YCaleidoEvasioneUdsAcquisDataRifIntestatario.getId()%>" maxlength="<%=YCaleidoEvasioneUdsAcquisDataRifIntestatario.getMaxLength()%>" name="<%=YCaleidoEvasioneUdsAcquisDataRifIntestatario.getName()%>" size="<%=YCaleidoEvasioneUdsAcquisDataRifIntestatario.getSize()%>" type="text"><% 
  YCaleidoEvasioneUdsAcquisDataRifIntestatario.write(out); 
%>
</td>
										</tr>
										<tr>
											<td nowrap><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YCaleidoEvasioneUdsAcquis", "NumeroRifIntestatario", null); 
   label.setParent(YCaleidoEvasioneUdsAcquisForm); 
%><label class="<%=label.getClassType()%>" for="NumeroRifIntestatario"><%label.write(out);%></label><%}%></td>
											<td><% 
  WebTextInput YCaleidoEvasioneUdsAcquisNumeroRifIntestatario =  
     new com.thera.thermfw.web.WebTextInput("YCaleidoEvasioneUdsAcquis", "NumeroRifIntestatario"); 
  YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.setParent(YCaleidoEvasioneUdsAcquisForm); 
%>
<input class="<%=YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.getClassType()%>" id="<%=YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.getId()%>" maxlength="<%=YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.getMaxLength()%>" name="<%=YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.getName()%>" size="<%=YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.getSize()%>" type="text"><% 
  YCaleidoEvasioneUdsAcquisNumeroRifIntestatario.write(out); 
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
			<tr valign="bottom">
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YCaleidoEvasioneUdsAcquisForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YCaleidoEvasioneUdsAcquisForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YCaleidoEvasioneUdsAcquisForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YCaleidoEvasioneUdsAcquisBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YCaleidoEvasioneUdsAcquisForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YCaleidoEvasioneUdsAcquisBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YCaleidoEvasioneUdsAcquisBODC.getErrorList().getErrors()); 
           if(YCaleidoEvasioneUdsAcquisBODC.getConflict() != null) 
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
     if(YCaleidoEvasioneUdsAcquisBODC != null && !YCaleidoEvasioneUdsAcquisBODC.close(false)) 
        errors.addAll(0, YCaleidoEvasioneUdsAcquisBODC.getErrorList().getErrors()); 
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
     String errorPage = YCaleidoEvasioneUdsAcquisForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YCaleidoEvasioneUdsAcquisBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YCaleidoEvasioneUdsAcquisForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
