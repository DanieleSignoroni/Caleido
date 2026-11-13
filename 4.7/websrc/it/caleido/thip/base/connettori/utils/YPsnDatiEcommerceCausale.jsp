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
  BODataCollector YPsnDatiEcommerceCausaleBODC = null; 
  WebFormForInternalRowForm YPsnDatiEcommerceCausaleForm =  
     new com.thera.thermfw.web.WebFormForInternalRowForm(request, response, "YPsnDatiEcommerceCausaleForm", "YPsnDatiEcommerceCausale", null, "com.thera.thermfw.web.servlet.FormActionAdapter", false, false, false, false, true, true, null, 1); 
  int mode = YPsnDatiEcommerceCausaleForm.getMode(); 
  String key = YPsnDatiEcommerceCausaleForm.getKey(); 
  String errorMessage; 
  boolean requestIsValid = false; 
  boolean leftIsKey = false; 
  String leftClass = ""; 
  try 
  {
     se.initialize(request, response); 
     if(se.begin()) 
     { 
        YPsnDatiEcommerceCausaleForm.outTraceInfo(getClass().getName()); 
        ClassADCollection globCadc = YPsnDatiEcommerceCausaleForm.getClassADCollection(); 
        requestIsValid = true; 
        YPsnDatiEcommerceCausaleForm.write(out); 
        String collectorName = YPsnDatiEcommerceCausaleForm.findBODataCollectorName(); 
				 YPsnDatiEcommerceCausaleBODC = (BODataCollector)Factory.createObject(collectorName); 
        YPsnDatiEcommerceCausaleBODC.initialize("YPsnDatiEcommerceCausale", true, 1); 
        YPsnDatiEcommerceCausaleForm.setBODataCollector(YPsnDatiEcommerceCausaleBODC); 
        WebForm parentForm = (WebForm)request.getAttribute("parentForm"); 
        YPsnDatiEcommerceCausaleForm.setJSTypeList(parentForm.getOwnerForm().getJSTypeList()); 
        YPsnDatiEcommerceCausaleForm.setParent(parentForm); 
        YPsnDatiEcommerceCausaleForm.writeHeadElements(out); 
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
   YPsnDatiEcommerceCausaleForm.writeBodyStartElements(out); 
%> 

	<% 
  if(false) 
  { 
%> 
<form name="YPsnDatiEcommerceCausaleForm"><% 
  } 
%> 
<%
   YPsnDatiEcommerceCausaleForm.writeFormStartElements(out); 
%> 

		<table id="emptyborder">
<tr><td colspan="2">
<% WebTextInput YPsnDatiEcommerceCausaleIdAzienda = new WebTextInput("YPsnDatiEcommerceCausale", "IdAzienda");
   YPsnDatiEcommerceCausaleIdAzienda.setParent(YPsnDatiEcommerceCausaleForm); %>
<input type="hidden" id="<%=YPsnDatiEcommerceCausaleIdAzienda.getId()%>" name="<%=YPsnDatiEcommerceCausaleIdAzienda.getName()%>"
 class="<%=YPsnDatiEcommerceCausaleIdAzienda.getClassType()%>" maxlength="<%=YPsnDatiEcommerceCausaleIdAzienda.getMaxLength()%>" size="<%=YPsnDatiEcommerceCausaleIdAzienda.getSize()%>">
<% YPsnDatiEcommerceCausaleIdAzienda.write(out); %>
<% WebTextInput YPsnDatiEcommerceCausaleIdRiga = new WebTextInput("YPsnDatiEcommerceCausale", "IdRiga");
   YPsnDatiEcommerceCausaleIdRiga.setParent(YPsnDatiEcommerceCausaleForm); %>
<input type="hidden" id="<%=YPsnDatiEcommerceCausaleIdRiga.getId()%>" name="<%=YPsnDatiEcommerceCausaleIdRiga.getName()%>"
 class="<%=YPsnDatiEcommerceCausaleIdRiga.getClassType()%>" maxlength="<%=YPsnDatiEcommerceCausaleIdRiga.getMaxLength()%>" size="<%=YPsnDatiEcommerceCausaleIdRiga.getSize()%>">
<% YPsnDatiEcommerceCausaleIdRiga.write(out); %>
</td></tr>
<tr><td valign="top"><%{ WebLabelCompound label = new WebLabelCompound(null, null, "YPsnDatiEcommerceCausale", "RNazione", null);
 label.setParent(YPsnDatiEcommerceCausaleForm); %><label class="<%=label.getClassType()%>" for="Nazione"><%label.write(out);%></label><%}%></td>
<td valign="top"><% WebMultiSearchForm YPsnDatiEcommerceCausaleNazione = new WebMultiSearchForm("YPsnDatiEcommerceCausale", "Nazione", false, false, true, 1, null, null);
 YPsnDatiEcommerceCausaleNazione.setParent(YPsnDatiEcommerceCausaleForm);
 YPsnDatiEcommerceCausaleNazione.write(out); %></td></tr>
<tr><td valign="top"><%{ WebLabelCompound label = new WebLabelCompound(null, null, "YPsnDatiEcommerceCausale", "RCausale", null);
 label.setParent(YPsnDatiEcommerceCausaleForm); %><label class="<%=label.getClassType()%>" for="CausaleOffertaCliente"><%label.write(out);%></label><%}%></td>
<td valign="top"><% WebMultiSearchForm YPsnDatiEcommerceCausaleCausaleOffertaCliente = new WebMultiSearchForm("YPsnDatiEcommerceCausale", "CausaleOffertaCliente", false, false, true, 1, null, null);
 YPsnDatiEcommerceCausaleCausaleOffertaCliente.setParent(YPsnDatiEcommerceCausaleForm);
 YPsnDatiEcommerceCausaleCausaleOffertaCliente.write(out); %></td></tr>
<tr><td colspan="2"><% WebCheckBox YPsnDatiEcommerceCausalePreventivo = new WebCheckBox("YPsnDatiEcommerceCausale", "Preventivo");
 YPsnDatiEcommerceCausalePreventivo.setParent(YPsnDatiEcommerceCausaleForm); %>
<input type="checkbox" value="Y" id="<%=YPsnDatiEcommerceCausalePreventivo.getId()%>" name="<%=YPsnDatiEcommerceCausalePreventivo.getName()%>">
<% YPsnDatiEcommerceCausalePreventivo.write(out); %></td></tr>
<tr><td colspan="2">Nazione vuota: configurazione generica. Preventivo non selezionato: ordine confermato.</td></tr>
</table>
	<%
  YPsnDatiEcommerceCausaleForm.writeFormEndElements(out); 
%>
<% 
  if(false) 
  { 
%> 
</form><% 
  } 
%> 

<%
   YPsnDatiEcommerceCausaleForm.writeBodyEndElements(out); 
%> 
<% 
  if(false) 
  { 
%> 
</body><% 
  } 
%> 

