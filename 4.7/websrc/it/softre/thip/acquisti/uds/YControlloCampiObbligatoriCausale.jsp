<%@page import="it.thera.thip.acquisti.generaleAC.CausaleDocumentoTestataAcq"%>
<%@page contentType="text/html; charset=Cp1252"%>
<%@page import="com.thera.thermfw.persist.KeyHelper"%>
<%@page import="it.thera.thip.base.azienda.*"%>
<%@ page
	import=" 
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
	se.initialize(request, response);
	 try 
	  {
	if(se.begin()){
		String idCausale = request.getParameter("Causale");
		CausaleDocumentoTestataAcq causale = (CausaleDocumentoTestataAcq) CausaleDocumentoTestataAcq.elementWithKey(CausaleDocumentoTestataAcq.class, 
				KeyHelper.buildObjectKey(new String []{Azienda.getAziendaCorrente(), idCausale}), 0);
		if(causale != null){
			if(causale.getTipiGestione().getTPGestioneRiferimentoFor().getTipoGestione() == '1'){
			%>
				<script>
					parent.document.getElementById("DataRifIntestatario").parentNode.parentNode.style.display = "revert";
					parent.document.getElementById("NumeroRifIntestatario").parentNode.parentNode.style.display = "revert";
				</script>
			<%
			}else{
				%>
				<script>
					parent.document.getElementById("DataRifIntestatario").parentNode.parentNode.style.display = "none";
					parent.document.getElementById("NumeroRifIntestatario").parentNode.parentNode.style.display = "none";
				</script>
				<%
			}
		}
	}
	  } 
	  catch(Exception e) {
	     e.printStackTrace(Trace.excStream);
	  }
	  finally 
	  {
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
%>