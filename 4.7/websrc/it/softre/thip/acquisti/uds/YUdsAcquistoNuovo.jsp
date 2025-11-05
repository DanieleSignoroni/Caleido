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
     new com.thera.thermfw.web.WebForm(request, response, "YUdsAcquistoForm", "YUdsAcquisto", null, "it.softre.thip.acquisti.uds.web.YUdsAcquistoFormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/softre/thip/acquisti/uds/YUdsAcquistoNuovo.js"); 
  YUdsAcquistoForm.setServletEnvironment(se); 
  YUdsAcquistoForm.setJSTypeList(jsList); 
  YUdsAcquistoForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YUdsAcquistoForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YUdsAcquistoForm.setWebFormModifierClass("it.softre.thip.acquisti.uds.web.YUdsAcquistoTestataFormModifier"); 
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
        YUdsAcquistoBODC.initialize("YUdsAcquisto", true, 0); 
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
<% 
  WebToolBar myToolBarTB = new com.thera.thermfw.web.WebToolBar("myToolBar", "24", "24", "16", "16", "#f7fbfd","#C8D6E1"); 
  myToolBarTB.setParent(YUdsAcquistoForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<body onbeforeunload="<%=YUdsAcquistoForm.getBodyOnBeforeUnload()%>" onload="<%=YUdsAcquistoForm.getBodyOnLoad()%>" onunload="<%=YUdsAcquistoForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
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

		<table cellpadding="0" cellspacing="0" height="100%" id="emptyborder" width="100%">
			<tr>
				<td style="height: 0"><% myToolBarTB.writeChildren(out); %> 
</td>
			</tr>
			<tr>
				<td height="100%"><!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YUdsAcquistoForm); 
 mytabbed.addTab("tab1", "it.softre.thip.acquisti.uds.resources.YUdsAcquisto", "tab1", "YUdsAcquisto", null, null, null, null); 
 mytabbed.addTab("tab2", "it.softre.thip.acquisti.uds.resources.YUdsAcquisto", "tab2", "YUdsAcquisto", null, null, null, null); 
 mytabbed.addTab("tab3", "it.softre.thip.acquisti.uds.resources.YUdsAcquisto", "tab3", "YUdsAcquisto", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;"> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
							<fieldset style="width: 90%;">
								<legend> Riferimenti UDS </legend>
								<table>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "IdTipoContenitore", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="RelTipoUDS"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelTipoUDS =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelTipoUDS", false, false, true, 1, null, null); 
  YUdsAcquistoRelTipoUDS.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelTipoUDS.setOnKeyChange("recuperaMisure()"); 
  YUdsAcquistoRelTipoUDS.write(out); 
%>
<!--<span class="multisearchform" id="RelTipoUDS"></span>--></td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "IdUds", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="IdUds"><%label.write(out);%></label><%}%></td>
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
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "DataUds", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="DataUds"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoDataUds =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "DataUds"); 
  YUdsAcquistoDataUds.setShowCalendarBtn(true); 
  YUdsAcquistoDataUds.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoDataUds.getClassType()%>" id="<%=YUdsAcquistoDataUds.getId()%>" maxlength="<%=YUdsAcquistoDataUds.getMaxLength()%>" name="<%=YUdsAcquistoDataUds.getName()%>" size="<%=YUdsAcquistoDataUds.getSize()%>"><% 
  YUdsAcquistoDataUds.write(out); 
%>

										</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "RIdUdsPadre", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="RelUdsPadre"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelUdsPadre =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelUdsPadre", false, false, true, 1, null, null); 
  YUdsAcquistoRelUdsPadre.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelUdsPadre.write(out); 
%>
<!--<span class="multisearchform" id="RelUdsPadre"></span>--></td>
									</tr>
								</table>
							</fieldset>
							<table>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "IdFornitore", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="RelFornitore"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelFornitore =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelFornitore", false, false, true, 1, null, null); 
  YUdsAcquistoRelFornitore.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelFornitore.write(out); 
%>
<!--<span class="multisearchform" id="RelFornitore"></span>--></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "RUbicazione", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="RelUbicazione"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelUbicazione =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelUbicazione", false, false, true, 2, null, null); 
  YUdsAcquistoRelUbicazione.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelUbicazione.write(out); 
%>
<!--<span class="multisearchform" id="RelUbicazione"></span>--></td>
								</tr>
								<tr style="display:none;">
									<td valign="top"><% 
  WebTextInput YUdsAcquistoIdVariabileCfg =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "IdVariabileCfg"); 
  YUdsAcquistoIdVariabileCfg.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoIdVariabileCfg.getClassType()%>" id="<%=YUdsAcquistoIdVariabileCfg.getId()%>" maxlength="<%=YUdsAcquistoIdVariabileCfg.getMaxLength()%>" name="<%=YUdsAcquistoIdVariabileCfg.getName()%>" size="<%=YUdsAcquistoIdVariabileCfg.getSize()%>"><% 
  YUdsAcquistoIdVariabileCfg.write(out); 
%>

									</td>
									<td valign="top"><% 
  WebTextInput YUdsAcquistoIdSchemaCfg =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "IdSchemaCfg"); 
  YUdsAcquistoIdSchemaCfg.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoIdSchemaCfg.getClassType()%>" id="<%=YUdsAcquistoIdSchemaCfg.getId()%>" maxlength="<%=YUdsAcquistoIdSchemaCfg.getMaxLength()%>" name="<%=YUdsAcquistoIdSchemaCfg.getName()%>" size="<%=YUdsAcquistoIdSchemaCfg.getSize()%>"><% 
  YUdsAcquistoIdSchemaCfg.write(out); 
%>
</td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "SeqValore", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="ValoreVariabileCfg"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoValoreVariabileCfg =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "ValoreVariabileCfg", false, false, true, 1, null, null); 
  YUdsAcquistoValoreVariabileCfg.setParent(YUdsAcquistoForm); 
  YUdsAcquistoValoreVariabileCfg.write(out); 
%>
<!--<span class="multisearchform" id="ValoreVariabileCfg"></span>--></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "Note", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="Note"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebTextInput YUdsAcquistoNote =  
     new com.thera.thermfw.web.WebTextArea("YUdsAcquisto", "Note"); 
  YUdsAcquistoNote.setParent(YUdsAcquistoForm); 
%>
<textarea class="<%=YUdsAcquistoNote.getClassType()%>" cols="60" id="<%=YUdsAcquistoNote.getId()%>" maxlength="<%=YUdsAcquistoNote.getMaxLength()%>" name="<%=YUdsAcquistoNote.getName()%>" rows="5" size="<%=YUdsAcquistoNote.getSize()%>"></textarea><% 
  YUdsAcquistoNote.write(out); 
%>
</td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "StatoStEti", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="StatoStEti"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebComboBox YUdsAcquistoStatoStEti =  
     new com.thera.thermfw.web.WebComboBox("YUdsAcquisto", "StatoStEti", null); 
  YUdsAcquistoStatoStEti.setParent(YUdsAcquistoForm); 
%>
<select id="<%=YUdsAcquistoStatoStEti.getId()%>" name="<%=YUdsAcquistoStatoStEti.getName()%>"><% 
  YUdsAcquistoStatoStEti.write(out); 
%> 
</select>
									</td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "StatoStPacking", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="StatoStPacking"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebComboBox YUdsAcquistoStatoStPacking =  
     new com.thera.thermfw.web.WebComboBox("YUdsAcquisto", "StatoStPacking", null); 
  YUdsAcquistoStatoStPacking.setParent(YUdsAcquistoForm); 
%>
<select id="<%=YUdsAcquistoStatoStPacking.getId()%>" name="<%=YUdsAcquistoStatoStPacking.getName()%>"><% 
  YUdsAcquistoStatoStPacking.write(out); 
%> 
</select></td>
								</tr>
								<tr>
									<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "StatoEvasione", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="StatoEvasione"><%label.write(out);%></label><%}%></td>
									<td valign="top"><% 
  WebComboBox YUdsAcquistoStatoEvasione =  
     new com.thera.thermfw.web.WebComboBox("YUdsAcquisto", "StatoEvasione", null); 
  YUdsAcquistoStatoEvasione.setParent(YUdsAcquistoForm); 
%>
<select id="<%=YUdsAcquistoStatoEvasione.getId()%>" name="<%=YUdsAcquistoStatoEvasione.getName()%>"><% 
  YUdsAcquistoStatoEvasione.write(out); 
%> 
</select></td>
								</tr>
								<tr>
									<td valign="top"><% 
   request.setAttribute("parentForm", YUdsAcquistoForm); 
   String CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp = "DatiComuniEstesi"; 
%>
<jsp:include page="/it/thera/thip/cs/DatiComuniEstesi.jsp" flush="true"> 
<jsp:param name="CDName" value="<%=CDForDatiComuniEstesi$it$thera$thip$cs$DatiComuniEstesi$jsp%>"/> 
</jsp:include> 
<!--<span class="subform" id="DatiComuniEstesi"></span>--></td>
									<td valign="top"></td>
								</tr>
							</table>
					<% mytabbed.endTab(); %> 
</div> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab2")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab2"); %>
							<fieldset style="width: 40%;">
								<legend>Misure</legend>
								<table>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "PesoNetto", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="PesoNetto"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoPesoNetto =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "PesoNetto"); 
  YUdsAcquistoPesoNetto.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoPesoNetto.getClassType()%>" id="<%=YUdsAcquistoPesoNetto.getId()%>" maxlength="<%=YUdsAcquistoPesoNetto.getMaxLength()%>" name="<%=YUdsAcquistoPesoNetto.getName()%>" size="<%=YUdsAcquistoPesoNetto.getSize()%>"><% 
  YUdsAcquistoPesoNetto.write(out); 
%>

										</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "PesoLordo", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="PesoLordo"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoPesoLordo =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "PesoLordo"); 
  YUdsAcquistoPesoLordo.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoPesoLordo.getClassType()%>" id="<%=YUdsAcquistoPesoLordo.getId()%>" maxlength="<%=YUdsAcquistoPesoLordo.getMaxLength()%>" name="<%=YUdsAcquistoPesoLordo.getName()%>" size="<%=YUdsAcquistoPesoLordo.getSize()%>"><% 
  YUdsAcquistoPesoLordo.write(out); 
%>

										</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "PesoContenitore", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="PesoContenitore"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoPesoContenitore =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "PesoContenitore"); 
  YUdsAcquistoPesoContenitore.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoPesoContenitore.getClassType()%>" id="<%=YUdsAcquistoPesoContenitore.getId()%>" maxlength="<%=YUdsAcquistoPesoContenitore.getMaxLength()%>" name="<%=YUdsAcquistoPesoContenitore.getName()%>" size="<%=YUdsAcquistoPesoContenitore.getSize()%>"><% 
  YUdsAcquistoPesoContenitore.write(out); 
%>
</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "Volume", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="Volume"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoVolume =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "Volume"); 
  YUdsAcquistoVolume.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoVolume.getClassType()%>" id="<%=YUdsAcquistoVolume.getId()%>" maxlength="<%=YUdsAcquistoVolume.getMaxLength()%>" name="<%=YUdsAcquistoVolume.getName()%>" size="<%=YUdsAcquistoVolume.getSize()%>"><% 
  YUdsAcquistoVolume.write(out); 
%>
</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "Altezza", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="Altezza"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoAltezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "Altezza"); 
  YUdsAcquistoAltezza.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoAltezza.getClassType()%>" id="<%=YUdsAcquistoAltezza.getId()%>" maxlength="<%=YUdsAcquistoAltezza.getMaxLength()%>" name="<%=YUdsAcquistoAltezza.getName()%>" size="<%=YUdsAcquistoAltezza.getSize()%>"><% 
  YUdsAcquistoAltezza.write(out); 
%>

										</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "Lunghezza", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="Lunghezza"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoLunghezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "Lunghezza"); 
  YUdsAcquistoLunghezza.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoLunghezza.getClassType()%>" id="<%=YUdsAcquistoLunghezza.getId()%>" maxlength="<%=YUdsAcquistoLunghezza.getMaxLength()%>" name="<%=YUdsAcquistoLunghezza.getName()%>" size="<%=YUdsAcquistoLunghezza.getSize()%>"><% 
  YUdsAcquistoLunghezza.write(out); 
%>

										</td>
									</tr>
									<tr>
										<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YUdsAcquisto", "Larghezza", null); 
   label.setParent(YUdsAcquistoForm); 
%><label class="<%=label.getClassType()%>" for="Larghezza"><%label.write(out);%></label><%}%></td>
										<td valign="top"><% 
  WebTextInput YUdsAcquistoLarghezza =  
     new com.thera.thermfw.web.WebTextInput("YUdsAcquisto", "Larghezza"); 
  YUdsAcquistoLarghezza.setParent(YUdsAcquistoForm); 
%>
<input class="<%=YUdsAcquistoLarghezza.getClassType()%>" id="<%=YUdsAcquistoLarghezza.getId()%>" maxlength="<%=YUdsAcquistoLarghezza.getMaxLength()%>" name="<%=YUdsAcquistoLarghezza.getName()%>" size="<%=YUdsAcquistoLarghezza.getSize()%>"><% 
  YUdsAcquistoLarghezza.write(out); 
%>

										</td>
									</tr>
								</table>
							</fieldset>
					<% mytabbed.endTab(); %> 
</div> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab3")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab3"); %>
							<fieldset style="width: 45%;">
								<legend>Documento Acquisto</legend>
								<table>
									<tr>

										<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelDocumentoAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelDocumentoAcquisto", false, false, true, 2, null, null); 
  YUdsAcquistoRelDocumentoAcquisto.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelDocumentoAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelDocumentoAcquisto"></span>--></td>
									</tr>
								</table>
							</fieldset>
							<fieldset style="width: 45%;">
								<legend>Ordine Acquisto</legend>
								<table>
									<tr>

										<td valign="top"><% 
  WebMultiSearchForm YUdsAcquistoRelOrdineAcquisto =  
     new com.thera.thermfw.web.WebMultiSearchForm("YUdsAcquisto", "RelOrdineAcquisto", false, false, true, 2, null, null); 
  YUdsAcquistoRelOrdineAcquisto.setParent(YUdsAcquistoForm); 
  YUdsAcquistoRelOrdineAcquisto.write(out); 
%>
<!--<span class="multisearchform" id="RelOrdineAcquisto"></span>--></td>
									</tr>
								</table>
							</fieldset>
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
  errorList.setParent(YUdsAcquistoForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
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
