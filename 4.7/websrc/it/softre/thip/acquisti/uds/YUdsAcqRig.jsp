<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"
                      "file:///K:/Thip/4.7.0/websrcsvil/dtd/xhtml1-transitional.dtd">
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
  BODataCollector YUdsAcqRigBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YUdsAcqRigForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YUdsAcqRigForm", "YUdsAcqRig", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 1, true, "it/softre/thip/acquisti/uds/YUdsAcqRig.js"); 
  YUdsAcqRigForm.setServletEnvironment(se); 
  YUdsAcqRigForm.setJSTypeList(jsList); 
  YUdsAcqRigForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YUdsAcqRigForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YUdsAcqRigForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YUdsAcqRigForm.getMode(); 
  String key = YUdsAcqRigForm.getKey(); 
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
        YUdsAcqRigForm.outTraceInfo(getClass().getName()); 
        String collectorName = YUdsAcqRigForm.findBODataCollectorName(); 
                YUdsAcqRigBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YUdsAcqRigBODC instanceof WebDataCollector) 
            ((WebDataCollector)YUdsAcqRigBODC).setServletEnvironment(se); 
        YUdsAcqRigBODC.initialize("YUdsAcqRig", true, 1); 
        YUdsAcqRigForm.setBODataCollector(YUdsAcqRigBODC); 
        int rcBODC = YUdsAcqRigForm.initSecurityServices(); 
        mode = YUdsAcqRigForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YUdsAcqRigForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YUdsAcqRigBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YUdsAcqRigForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 

<!-- <meta charset="UTF-8"></meta>  -->
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YUdsAcqRigForm); 
   request.setAttribute("menuBar", menuBar); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="menuBar"/> 
</jsp:include> 
<% 
  menuBar.write(out); 
  menuBar.writeChildren(out); 
%> 
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YUdsAcqRigForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<body onbeforeunload="<%=YUdsAcqRigForm.getBodyOnBeforeUnload()%>" onload="<%=YUdsAcqRigForm.getBodyOnLoad()%>" onunload="<%=YUdsAcqRigForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YUdsAcqRigForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YUdsAcqRigForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YUdsAcqRigBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YUdsAcqRigForm.getServlet()%>" method="post" name="YUdsAcqRigForm" style="height:100%"><%
  YUdsAcqRigForm.writeFormStartElements(out); 
%>

		<table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
			<tr>
				<td style="height: 0"><% menuBar.writeElements(out); %> 
</td>
			</tr>
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td><% 
  WebTextInput YUdsAcqRigIdUds =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "IdUds"); 
  YUdsAcqRigIdUds.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigIdUds.getClassType()%>" id="<%=YUdsAcqRigIdUds.getId()%>" maxlength="<%=YUdsAcqRigIdUds.getMaxLength()%>" name="<%=YUdsAcqRigIdUds.getName()%>" size="<%=YUdsAcqRigIdUds.getSize()%>" type="hidden"><% 
  YUdsAcqRigIdUds.write(out); 
%>
</td>
			</tr>
<!--    <tr> -->
<!--     <td><input type="hidden" name="IdRigaUds" id="IdRigaUds" /></td> -->
<!--    </tr> -->
			<tr>
				<td height="100%"><!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YUdsAcqRigForm); 
 mytabbed.addTab("tab1", "it.softre.thip.acquisti.uds.resources.YUdsAcqRig", "tab1", "YUdsAcqRig", null, null, null, null); 
 mytabbed.addTab("tab2", "it.softre.thip.acquisti.uds.resources.YUdsAcqRig", "tab2", "YUdsAcqRig", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;"> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
							<table style="width: 100%;">
							<tr>
									<td valign="top"><label>Sequenza</label></td>
									<td valign="top"><% 
  WebTextInput YUdsAcqRigIdRigaUds =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "IdRigaUds"); 
  YUdsAcqRigIdRigaUds.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigIdRigaUds.getClassType()%>" id="<%=YUdsAcqRigIdRigaUds.getId()%>" maxlength="<%=YUdsAcqRigIdRigaUds.getMaxLength()%>" name="<%=YUdsAcqRigIdRigaUds.getName()%>" size="<%=YUdsAcqRigIdRigaUds.getSize()%>"><% 
  YUdsAcqRigIdRigaUds.write(out); 
%>
</td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "RArticolo", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="RelArticolo"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelArticolo =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelArticolo", false, false, true, 1, null, null); 
  YUdsAcqRigRelArticolo.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelArticolo.write(out); 
%>
<!--<span class="multisearchform" id="RelArticolo"></span>--></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "RVersione", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="RelVersione"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelVersione =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelVersione", false, false, true, 1, null, null); 
  YUdsAcqRigRelVersione.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelVersione.write(out); 
%>
<!--<span class="multisearchform" id="RelVersione"></span>--></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "RConfig", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="RelConfigurazione"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelConfigurazione =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelConfigurazione", false, false, true, 1, null, null); 
  YUdsAcqRigRelConfigurazione.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelConfigurazione.write(out); 
%>
<!--<span class="multisearchform" id="RelConfigurazione"></span>--></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "RLotto", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="RelLotto"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelLotto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelLotto", false, false, true, 1, null, null); 
  YUdsAcqRigRelLotto.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelLotto.write(out); 
%>
<!--<span class="multisearchform" id="RelLotto"></span>--></td>
								</tr>
							</table>
							<table name="Mis" style="width: 100%">
								<tr>
									<td>
										<fieldset style="width: 25%; display: inline-block">
											<legend> Misure </legend>
											<table>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "PesoNetto", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="PesoNetto"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigPesoNetto =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "PesoNetto"); 
  YUdsAcqRigPesoNetto.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigPesoNetto.getClassType()%>" id="<%=YUdsAcqRigPesoNetto.getId()%>" maxlength="<%=YUdsAcqRigPesoNetto.getMaxLength()%>" name="<%=YUdsAcqRigPesoNetto.getName()%>" size="<%=YUdsAcqRigPesoNetto.getSize()%>"><% 
  YUdsAcqRigPesoNetto.write(out); 
%>
</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "PesoLordo", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="PesoLordo"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigPesoLordo =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "PesoLordo"); 
  YUdsAcqRigPesoLordo.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigPesoLordo.getClassType()%>" id="<%=YUdsAcqRigPesoLordo.getId()%>" maxlength="<%=YUdsAcqRigPesoLordo.getMaxLength()%>" name="<%=YUdsAcqRigPesoLordo.getName()%>" size="<%=YUdsAcqRigPesoLordo.getSize()%>"><% 
  YUdsAcqRigPesoLordo.write(out); 
%>
</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "Volume", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="Volume"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigVolume =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "Volume"); 
  YUdsAcqRigVolume.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigVolume.getClassType()%>" id="<%=YUdsAcqRigVolume.getId()%>" maxlength="<%=YUdsAcqRigVolume.getMaxLength()%>" name="<%=YUdsAcqRigVolume.getName()%>" size="<%=YUdsAcqRigVolume.getSize()%>"><% 
  YUdsAcqRigVolume.write(out); 
%>

													</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "Altezza", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="Altezza"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigAltezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "Altezza"); 
  YUdsAcqRigAltezza.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigAltezza.getClassType()%>" id="<%=YUdsAcqRigAltezza.getId()%>" maxlength="<%=YUdsAcqRigAltezza.getMaxLength()%>" name="<%=YUdsAcqRigAltezza.getName()%>" size="<%=YUdsAcqRigAltezza.getSize()%>"><% 
  YUdsAcqRigAltezza.write(out); 
%>

													</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "Lunghezza", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="Lunghezza"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigLunghezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "Lunghezza"); 
  YUdsAcqRigLunghezza.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigLunghezza.getClassType()%>" id="<%=YUdsAcqRigLunghezza.getId()%>" maxlength="<%=YUdsAcqRigLunghezza.getMaxLength()%>" name="<%=YUdsAcqRigLunghezza.getName()%>" size="<%=YUdsAcqRigLunghezza.getSize()%>"><% 
  YUdsAcqRigLunghezza.write(out); 
%>
</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "Larghezza", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="Larghezza"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigLarghezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "Larghezza"); 
  YUdsAcqRigLarghezza.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigLarghezza.getClassType()%>" id="<%=YUdsAcqRigLarghezza.getId()%>" maxlength="<%=YUdsAcqRigLarghezza.getMaxLength()%>" name="<%=YUdsAcqRigLarghezza.getName()%>" size="<%=YUdsAcqRigLarghezza.getSize()%>"><% 
  YUdsAcqRigLarghezza.write(out); 
%>
</td>
												</tr>
											</table>
										</fieldset>
										<fieldset style="width: 25%; display: inline-block">
											<legend> Quantita </legend>
											<table>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "QtaPrm", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="QtaPrm"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigQtaPrm =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "QtaPrm"); 
  YUdsAcqRigQtaPrm.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigQtaPrm.getClassType()%>" id="<%=YUdsAcqRigQtaPrm.getId()%>" maxlength="<%=YUdsAcqRigQtaPrm.getMaxLength()%>" name="<%=YUdsAcqRigQtaPrm.getName()%>" size="<%=YUdsAcqRigQtaPrm.getSize()%>"><% 
  YUdsAcqRigQtaPrm.write(out); 
%>

													</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "QtaSec", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="QtaSec"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigQtaSec =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "QtaSec"); 
  YUdsAcqRigQtaSec.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigQtaSec.getClassType()%>" id="<%=YUdsAcqRigQtaSec.getId()%>" maxlength="<%=YUdsAcqRigQtaSec.getMaxLength()%>" name="<%=YUdsAcqRigQtaSec.getName()%>" size="<%=YUdsAcqRigQtaSec.getSize()%>"><% 
  YUdsAcqRigQtaSec.write(out); 
%>

													</td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "QtaAcq", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="QtaAcq"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebTextInput YUdsAcqRigQtaAcq =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcqRig", "QtaAcq"); 
  YUdsAcqRigQtaAcq.setParent(YUdsAcqRigForm); 
%>
<input class="<%=YUdsAcqRigQtaAcq.getClassType()%>" id="<%=YUdsAcqRigQtaAcq.getId()%>" maxlength="<%=YUdsAcqRigQtaAcq.getMaxLength()%>" name="<%=YUdsAcqRigQtaAcq.getName()%>" size="<%=YUdsAcqRigQtaAcq.getSize()%>"><% 
  YUdsAcqRigQtaAcq.write(out); 
%>

													</td>
												</tr>
												<tr>
													<td><br></td>
												</tr>

												<tr>
													<td><br></td>
												</tr>

												<tr>
													<td><br></td>
												</tr>
											</table>
										</fieldset>

									</td>
								</tr>
							</table>
							<table>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "Note", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="Note"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebTextInput YUdsAcqRigNote =  
     new com.thera.thermfw.web.WebTextArea("YUdsAcqRig", "Note"); 
  YUdsAcqRigNote.setParent(YUdsAcqRigForm); 
%>
<textarea class="<%=YUdsAcqRigNote.getClassType()%>" cols="60" id="<%=YUdsAcqRigNote.getId()%>" maxlength="<%=YUdsAcqRigNote.getMaxLength()%>" name="<%=YUdsAcqRigNote.getName()%>" rows="5" size="<%=YUdsAcqRigNote.getSize()%>"></textarea><% 
  YUdsAcqRigNote.write(out); 
%>
</td>
								</tr>
								<tr>
									<td valign="top"><% 
   request.setAttribute("parentForm", YUdsAcqRigForm); 
   String CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp = "DatiComuniEstesi"; 
%>
<jsp:include page="/it/thera/thip/cs/DatiComuniEstesi.jsp" flush="true"> 
<jsp:param name="CDName" value="<%=CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp%>"/> 
</jsp:include> 
<!--<span class="subform" id="DatiComuniEstesi"></span>--></td>
									<td valign="top"></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcqRig", "StatoEvasione", null); 
   label.setParent(YUdsAcqRigForm); 
%><label class="<%=label.getClassType()%>" for="StatoEvasione"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebComboBox YUdsAcqRigStatoEvasione =  
     new com.thera.thermfw.web.WebComboBox("YUdsAcqRig", "StatoEvasione", null); 
  YUdsAcqRigStatoEvasione.setParent(YUdsAcqRigForm); 
%>
<select id="<%=YUdsAcqRigStatoEvasione.getId()%>" name="<%=YUdsAcqRigStatoEvasione.getName()%>"><% 
  YUdsAcqRigStatoEvasione.write(out); 
%> 
</select></td>
								</tr>
							</table>
					<% mytabbed.endTab(); %> 
</div> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab2")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab2"); %>
							<table>
								<tr>
									<td>
										<fieldset style="width: 100%; height: 100%">
											<legend>Fornitore</legend>
											<table>
												<tr>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelFornitore", false, false, true, 1, null, null); 
  YUdsAcqRigRelFornitore.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelFornitore.write(out); 
%>
<!--<span class="multisearchform" id="RelFornitore"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
							</table>
							<table>
								<tr>
									<td>
										<fieldset style="width: 100%; height: 100%">
											<legend>Ordine Produzione</legend>
											<table>

												<tr>
													<td valign="top"><label>Anno/Numero</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdPrd =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdPrd", false, false, true, 2, null, null); 
  YUdsAcqRigRelOrdPrd.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdPrd.setOnKeyChange("recuperaOrdineVendita()"); 
  YUdsAcqRigRelOrdPrd.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdPrd"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td>
										<fieldset style="width: 100%; height: 100%">
											<legend>Ordine Vendita</legend>
											<table>

												<tr>
													<td valign="top"><label>Anno/Numero</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdineVendita =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdineVendita", false, false, true, 2, null, null); 
  YUdsAcqRigRelOrdineVendita.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdineVendita.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineVendita"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Riga</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdineVenditaRiga =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdineVenditaRiga", false, false, true, 1, null, null); 
  YUdsAcqRigRelOrdineVenditaRiga.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdineVenditaRiga.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineVenditaRiga"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>

							</table>
							<table>
								<tr>
									<td>
										<fieldset style="width: 100%; height: 100%">
											<legend> Ordine Fornitore </legend>
											<table>
												<tr>
													<td valign="top"><label>Anno/Numero</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdineAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdineAcquisto", false, false, true, 2, null, null); 
  YUdsAcqRigRelOrdineAcquisto.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdineAcquisto.setAdditionalRestrictConditions("IdFornitore,IdFornitore"); 
  YUdsAcqRigRelOrdineAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineAcquisto"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Riga</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdineAcquistoRiga =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdineAcquistoRiga", false, false, true, 1, null, null); 
  YUdsAcqRigRelOrdineAcquistoRiga.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdineAcquistoRiga.setAdditionalRestrictConditions("RArticolo,IdArticolo"); 
  YUdsAcqRigRelOrdineAcquistoRiga.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineAcquistoRiga"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Dettaglio</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelOrdineAcquistoRigaSec =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelOrdineAcquistoRigaSec", false, false, true, 1, null, null); 
  YUdsAcqRigRelOrdineAcquistoRigaSec.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelOrdineAcquistoRigaSec.setAdditionalRestrictConditions("RArticolo,IdArticolo"); 
  YUdsAcqRigRelOrdineAcquistoRigaSec.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineAcquistoRigaSec"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Causale</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelCausaleRigaOrdAcq =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelCausaleRigaOrdAcq", false, false, true, 1, null, null); 
  YUdsAcqRigRelCausaleRigaOrdAcq.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelCausaleRigaOrdAcq.write(out); 
%>
<!--<span class="multisearchform" id="RelCausaleRigaOrdAcq"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
								<tr>
									<td>
										<fieldset style="width: 100%; height: 100%">
											<legend> Documento Acquisto </legend>
											<table>
												<tr>
													<td valign="top"><label>Anno/Numero</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelDocumentoAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelDocumentoAcquisto", false, false, true, 2, null, null); 
  YUdsAcqRigRelDocumentoAcquisto.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelDocumentoAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelDocumentoAcquisto"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Riga</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelDocumentoAcqRiga =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelDocumentoAcqRiga", false, false, true, 1, null, null); 
  YUdsAcqRigRelDocumentoAcqRiga.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelDocumentoAcqRiga.setAdditionalRestrictConditions("RArticolo,IdArticolo"); 
  YUdsAcqRigRelDocumentoAcqRiga.write(out); 
%>
<!--<span class="multisearchform" id="RelDocumentoAcqRiga"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><label>Dettaglio</label></td>
													<td valign="top"><% 
  WebMultiSearchForm YUdsAcqRigRelDocumentoAcqRigaSec =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcqRig", "RelDocumentoAcqRigaSec", false, false, true, 1, null, null); 
  YUdsAcqRigRelDocumentoAcqRigaSec.setParent(YUdsAcqRigForm); 
  YUdsAcqRigRelDocumentoAcqRigaSec.setAdditionalRestrictConditions("RArticolo,IdArticolo"); 
  YUdsAcqRigRelDocumentoAcqRigaSec.write(out); 
%>
<!--<span class="multisearchform" id="RelDocumentoAcqRigaSec"></span>--></td>
												</tr>
											</table>
										</fieldset>
									</td>
								</tr>
							</table>
					<% mytabbed.endTab(); %> 
</div>
				</div><% mytabbed.endTabbed();%> 

     </td>
   </tr>
</table><!--</span>--></td>
			</tr>
			<tr>
				<td style="height: 0"><% 
  WebErrorList errorList = new com.thera.thermfw.web.WebErrorList(); 
  errorList.setParent(YUdsAcqRigForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YUdsAcqRigForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YUdsAcqRigForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YUdsAcqRigBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YUdsAcqRigForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YUdsAcqRigBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YUdsAcqRigBODC.getErrorList().getErrors()); 
           if(YUdsAcqRigBODC.getConflict() != null) 
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
     if(YUdsAcqRigBODC != null && !YUdsAcqRigBODC.close(false)) 
        errors.addAll(0, YUdsAcqRigBODC.getErrorList().getErrors()); 
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
     String errorPage = YUdsAcqRigForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YUdsAcqRigBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YUdsAcqRigForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
