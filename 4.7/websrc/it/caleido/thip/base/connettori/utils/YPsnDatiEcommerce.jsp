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
  BODataCollector YPsnDatiEcommerceBODC = null; 
  List errors = new ArrayList(); 
  WebJSTypeList jsList = new WebJSTypeList(); 
  WebForm YPsnDatiEcommerceForm =  
     new com.thera.thermfw.web.WebForm(request, response, "YPsnDatiEcommerceForm", "YPsnDatiEcommerce", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, true, true, true, true, null, 0, true, "it/caleido/thip/base/connettori/utils/YPsnDatiEcommerce.js"); 
  YPsnDatiEcommerceForm.setServletEnvironment(se); 
  YPsnDatiEcommerceForm.setJSTypeList(jsList); 
  YPsnDatiEcommerceForm.setHeader("it.thera.thip.cs.PantheraHeader.jsp"); 
  YPsnDatiEcommerceForm.setFooter("com.thera.thermfw.common.Footer.jsp"); 
  YPsnDatiEcommerceForm.setWebFormModifierClass("it.caleido.thip.base.connettori.utils.web.YPsnDatiEcommerceFormModifier"); 
  YPsnDatiEcommerceForm.setDeniedAttributeModeStr("hideNone"); 
  int mode = YPsnDatiEcommerceForm.getMode(); 
  String key = YPsnDatiEcommerceForm.getKey(); 
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
        YPsnDatiEcommerceForm.outTraceInfo(getClass().getName()); 
        String collectorName = YPsnDatiEcommerceForm.findBODataCollectorName(); 
                YPsnDatiEcommerceBODC = (BODataCollector)Factory.createObject(collectorName); 
        if (YPsnDatiEcommerceBODC instanceof WebDataCollector) 
            ((WebDataCollector)YPsnDatiEcommerceBODC).setServletEnvironment(se); 
        YPsnDatiEcommerceBODC.initialize("YPsnDatiEcommerce", true, 0); 
        YPsnDatiEcommerceForm.setBODataCollector(YPsnDatiEcommerceBODC); 
        int rcBODC = YPsnDatiEcommerceForm.initSecurityServices(); 
        mode = YPsnDatiEcommerceForm.getMode(); 
        if (rcBODC == BODataCollector.OK) 
        { 
           requestIsValid = true; 
           YPsnDatiEcommerceForm.write(out); 
           if(mode != WebForm.NEW) 
              rcBODC = YPsnDatiEcommerceBODC.retrieve(key); 
           if(rcBODC == BODataCollector.OK) 
           { 
              YPsnDatiEcommerceForm.writeHeadElements(out); 
           // fine blocco XXX  
           // a completamento blocco di codice YYY a fine body con catch e gestione errori 
%> 
<% 
  WebMenuBar menuBar = new com.thera.thermfw.web.WebMenuBar("HM_Array1", "150", "#000000","#000000","#A5B6CE","#E4EAEF","#FFFFFF","#000000"); 
  menuBar.setParent(YPsnDatiEcommerceForm); 
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
  myToolBarTB.setParent(YPsnDatiEcommerceForm); 
   request.setAttribute("toolBar", myToolBarTB); 
%> 
<jsp:include page="/it/thera/thip/cs/defObjMenu.jsp" flush="true"> 
<jsp:param name="partRequest" value="toolBar"/> 
</jsp:include> 
<% 
   myToolBarTB.write(out); 
%> 
</head>
<body onbeforeunload="<%=YPsnDatiEcommerceForm.getBodyOnBeforeUnload()%>" onload="<%=YPsnDatiEcommerceForm.getBodyOnLoad()%>" onunload="<%=YPsnDatiEcommerceForm.getBodyOnUnload()%>" style="margin: 0px; overflow: hidden;"><%
   YPsnDatiEcommerceForm.writeBodyStartElements(out); 
%> 

	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
<tr>
<td style="height:0" valign="top">
<% String hdr = YPsnDatiEcommerceForm.getCompleteHeader();
 if (hdr != null) { 
   request.setAttribute("dataCollector", YPsnDatiEcommerceBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= hdr %>" flush="true"/> 
<% } %> 
</td>
</tr>

<tr>
<td valign="top" height="100%">
<form action="<%=YPsnDatiEcommerceForm.getServlet()%>" method="post" name="YPsnDatiEcommerceForm" style="height:100%"><%
  YPsnDatiEcommerceForm.writeFormStartElements(out); 
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
				<td height="100%"><!--<span class="tabbed" id="mytabbed">-->
<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="padding-right:1px">
   <tr valign="top">
     <td><% 
  WebTabbed mytabbed = new com.thera.thermfw.web.WebTabbed("mytabbed", "100%", "100%"); 
  mytabbed.setParent(YPsnDatiEcommerceForm); 
 mytabbed.addTab("tab1", "it.caleido.thip.base.connettori.utils.resources.YPsnDatiEcommerce", "tab1", "YPsnDatiEcommerce", null, null, null, null); 
  mytabbed.write(out); 
%>

     </td>
   </tr>
   <tr>
     <td height="100%"><div class="tabbed_pagine" id="tabbedPagine" style="position: relative; width: 100%; height: 100%;"> <div class="tabbed_page" id="<%=mytabbed.getTabPageId("tab1")%>" style="width:100%;height:100%;overflow:auto;"><% mytabbed.startTab("tab1"); %>
							<table style="width: 100%;">
								<tr>
									<td>
										<fieldset style="padding: 10px;">
											<legend>
												<label>Offerta cliente</label>
											</legend>
											<table style="width: 100%;">
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RSerieOffVen", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelSerieOffVen"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelSerieOffVen =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelSerieOffVen", false, false, true, 2, null, null); 
  YPsnDatiEcommerceRelSerieOffVen.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelSerieOffVen.write(out); 
%>
<!--<span class="multisearchform" id="RelSerieOffVen"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RValuta", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelValuta"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelValuta =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelValuta", false, false, true, 1, null, null); 
  YPsnDatiEcommerceRelValuta.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelValuta.write(out); 
%>
<!--<span class="multisearchform" id="RelValuta"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RAssogIva", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelAssogIva"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelAssogIva =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelAssogIva", false, false, true, 1, null, null); 
  YPsnDatiEcommerceRelAssogIva.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelAssogIva.write(out); 
%>
<!--<span class="multisearchform" id="RelAssogIva"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RCauOffTes", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelCauOffTes"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelCauOffTes =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelCauOffTes", false, false, true, 1, null, null); 
  YPsnDatiEcommerceRelCauOffTes.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelCauOffTes.write(out); 
%>
<!--<span class="multisearchform" id="RelCauOffTes"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RCauOffRig", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelCauOffRig"><%label.write(out);%></label><%}%></td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelCauOffRig =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelCauOffRig", false, false, true, 1, null, null); 
  YPsnDatiEcommerceRelCauOffRig.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelCauOffRig.write(out); 
%>
<!--<span class="multisearchform" id="RelCauOffRig"></span>--></td>
												</tr>
												<tr>
													<td valign="top"><%{  WebLabelCompound label = new com.thera.thermfw.web.WebLabelCompound(null, null, "YPsnDatiEcommerce", "RCauRigOffScMerce", null); 
   label.setParent(YPsnDatiEcommerceForm); 
%><label class="<%=label.getClassType()%>" for="RelCauOffRigScMerce"><%label.write(out);%></label><%}%>
													</td>
													<td valign="top"><% 
  WebMultiSearchForm YPsnDatiEcommerceRelCauOffRigScMerce =  
     new com.thera.thermfw.web.WebMultiSearchForm("YPsnDatiEcommerce", "RelCauOffRigScMerce", false, false, true, 1, null, null); 
  YPsnDatiEcommerceRelCauOffRigScMerce.setParent(YPsnDatiEcommerceForm); 
  YPsnDatiEcommerceRelCauOffRigScMerce.write(out); 
%>
<!--<span class="multisearchform" id="RelCauOffRigScMerce"></span>--></td>
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
  errorList.setParent(YPsnDatiEcommerceForm); 
  errorList.write(out); 
%>
<!--<span class="errorlist"></span>--></td>
			</tr>
		</table>
	<%
  YPsnDatiEcommerceForm.writeFormEndElements(out); 
%>
</form></td>
</tr>

<tr>
<td style="height:0">
<% String ftr = YPsnDatiEcommerceForm.getCompleteFooter();
 if (ftr != null) { 
   request.setAttribute("dataCollector", YPsnDatiEcommerceBODC); 
   request.setAttribute("servletEnvironment", se); %>
  <jsp:include page="<%= ftr %>" flush="true"/> 
<% } %> 
</td>
</tr>
</table>


<%
           // blocco YYY  
           // a completamento blocco di codice XXX in head 
              YPsnDatiEcommerceForm.writeBodyEndElements(out); 
           } 
           else 
              errors.addAll(0, YPsnDatiEcommerceBODC.getErrorList().getErrors()); 
        } 
        else 
           errors.addAll(0, YPsnDatiEcommerceBODC.getErrorList().getErrors()); 
           if(YPsnDatiEcommerceBODC.getConflict() != null) 
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
     if(YPsnDatiEcommerceBODC != null && !YPsnDatiEcommerceBODC.close(false)) 
        errors.addAll(0, YPsnDatiEcommerceBODC.getErrorList().getErrors()); 
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
     String errorPage = YPsnDatiEcommerceForm.getErrorPage(); 
%> 
     <jsp:include page="<%=errorPage%>" flush="true"/> 
<% 
  } 
  else 
  { 
     request.setAttribute("ConflictMessages", YPsnDatiEcommerceBODC.getConflict()); 
     request.setAttribute("ErrorMessages", errors); 
     String conflictPage = YPsnDatiEcommerceForm.getConflictPage(); 
%> 
     <jsp:include page="<%=conflictPage%>" flush="true"/> 
<% 
   } 
   } 
%> 
</body>
</html>
