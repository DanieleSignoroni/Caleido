<!-- WIZGEN Therm 2.0.0 as Form riga interna - multiBrowserGen = true -->
<% 
  if(false) 
  { 
%> 
<head><% 
  } 
%> 

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
  BODataCollector YPsnDatiEcommerceSerieBODC = null; 
  WebFormForInternalRowForm YPsnDatiEcommerceSerieForm =  
     new com.thera.thermfw.web.WebFormForInternalRowForm(request, response, "YPsnDatiEcommerceSerieForm", "YPsnDatiEcommerceSerie", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, false, false, true, true, null, 1); 
  int mode = YPsnDatiEcommerceSerieForm.getMode(); 
  String key = YPsnDatiEcommerceSerieForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YPsnDatiEcommerceSerieForm.outTraceInfo(getClass().getName()); 
        ClassADCollection globCadc = YPsnDatiEcommerceSerieForm.getClassADCollection(); 
        requestIsValid = true; 
        YPsnDatiEcommerceSerieForm.write(out); 
        String collectorName = YPsnDatiEcommerceSerieForm.findBODataCollectorName(); 
				 YPsnDatiEcommerceSerieBODC = (BODataCollector)Factory.createObject(collectorName); 
        YPsnDatiEcommerceSerieBODC.initialize("YPsnDatiEcommerceSerie", true, 1); 
        YPsnDatiEcommerceSerieForm.setBODataCollector(YPsnDatiEcommerceSerieBODC); 
        WebForm parentForm = (WebForm)request.getAttribute("parentForm"); 
        YPsnDatiEcommerceSerieForm.setJSTypeList(parentForm.getOwnerForm().getJSTypeList()); 
        YPsnDatiEcommerceSerieForm.setParent(parentForm); 
        YPsnDatiEcommerceSerieForm.writeHeadElements(out); 
     }
  }
  catch(NamingException e) { 
    errorMessage = e.getMessage(); 
  } 
  catch(SQLException e) { 
     errorMessage = e.getMessage(); 
  } 
  finally 
  { 
     try 
     { 
        se.end(); 
     } 
     catch(IllegalArgumentException e) { 
        e.printStackTrace(); 
     } 
     catch(SQLException e) { 
        e.printStackTrace(); 
     } 
  } 
%> 
<% 
  if(false) 
  { 
%> 
</head><% 
  } 
%> 


<% 
  if(false) 
  { 
%> 
<body style="margin: 0px; overflow: hidden;"><% 
  } 
%> 
<%
   YPsnDatiEcommerceSerieForm.writeBodyStartElements(out); 
%> 

	<% 
  if(false) 
  { 
%> 
<form name="YPsnDatiEcommerceSerieForm"><% 
  } 
%> 
<%
   YPsnDatiEcommerceSerieForm.writeFormStartElements(out); 
%> 

		<table id="emptyborder">
<tr><td colspan="2">
<% WebTextInput YPsnDatiEcommerceSerieIdAzienda = new WebTextInput("YPsnDatiEcommerceSerie", "IdAzienda");
   YPsnDatiEcommerceSerieIdAzienda.setParent(YPsnDatiEcommerceSerieForm); %>
<input type="hidden" id="<%=YPsnDatiEcommerceSerieIdAzienda.getId()%>" name="<%=YPsnDatiEcommerceSerieIdAzienda.getName()%>"
 class="<%=YPsnDatiEcommerceSerieIdAzienda.getClassType()%>" maxlength="<%=YPsnDatiEcommerceSerieIdAzienda.getMaxLength()%>" size="<%=YPsnDatiEcommerceSerieIdAzienda.getSize()%>">
<% YPsnDatiEcommerceSerieIdAzienda.write(out); %>
<% WebTextInput YPsnDatiEcommerceSerieIdRiga = new WebTextInput("YPsnDatiEcommerceSerie", "IdRiga");
   YPsnDatiEcommerceSerieIdRiga.setParent(YPsnDatiEcommerceSerieForm); %>
<input type="hidden" id="<%=YPsnDatiEcommerceSerieIdRiga.getId()%>" name="<%=YPsnDatiEcommerceSerieIdRiga.getName()%>"
 class="<%=YPsnDatiEcommerceSerieIdRiga.getClassType()%>" maxlength="<%=YPsnDatiEcommerceSerieIdRiga.getMaxLength()%>" size="<%=YPsnDatiEcommerceSerieIdRiga.getSize()%>">
<% YPsnDatiEcommerceSerieIdRiga.write(out); %>
</td></tr>
<tr><td valign="top"><%{ WebLabelCompound label = new WebLabelCompound(null, null, "YPsnDatiEcommerceSerie", "RNazione", null);
 label.setParent(YPsnDatiEcommerceSerieForm); %><label class="<%=label.getClassType()%>" for="Nazione"><%label.write(out);%></label><%}%></td>
<td valign="top"><% WebMultiSearchForm YPsnDatiEcommerceSerieNazione = new WebMultiSearchForm("YPsnDatiEcommerceSerie", "Nazione", false, false, true, 1, null, null);
 YPsnDatiEcommerceSerieNazione.setParent(YPsnDatiEcommerceSerieForm);
 YPsnDatiEcommerceSerieNazione.write(out); %></td></tr>
<tr><td valign="top"><%{ WebLabelCompound label = new WebLabelCompound(null, null, "YPsnDatiEcommerceSerie", "RSerie", null);
 label.setParent(YPsnDatiEcommerceSerieForm); %><label class="<%=label.getClassType()%>" for="Serie"><%label.write(out);%></label><%}%></td>
<td valign="top"><% WebMultiSearchForm YPsnDatiEcommerceSerieSerie = new WebMultiSearchForm("YPsnDatiEcommerceSerie", "Serie", false, false, true, 2, null, null);
 YPsnDatiEcommerceSerieSerie.setParent(YPsnDatiEcommerceSerieForm);
 YPsnDatiEcommerceSerieSerie.write(out); %></td></tr>
<tr><td colspan="2">Nazione vuota: configurazione generica.</td></tr>
</table>
	<%
  YPsnDatiEcommerceSerieForm.writeFormEndElements(out); 
%>
<% 
  if(false) 
  { 
%> 
</form><% 
  } 
%> 

<%
   YPsnDatiEcommerceSerieForm.writeBodyEndElements(out); 
%> 
<% 
  if(false) 
  { 
%> 
</body><% 
  } 
%> 

