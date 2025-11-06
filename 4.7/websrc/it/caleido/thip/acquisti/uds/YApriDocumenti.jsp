<%@page contentType="text/html; charset=Cp1252"%>
<%@page import="java.net.URLEncoder"%>
<%
	String keyOrd = request.getParameter("ChiaveOrdAcq");
	boolean daEstratto = request.getParameter("DaEstratto").equalsIgnoreCase("true") ? true : false;
%>
<script>
	var conf = window.confirm("Procedura terminata correttamente \n Aprire i documenti?");
	if(conf){		
		var urlOrdAcq = "/" + parent.webAppPath + "/" + parent.servletPath + "/it.thera.thip.acquisti.ordineAC.web.OrdineAcquistoGridActionAdapter?thClassName=OrdineAcquisto&ObjectKey="+"<%= URLEncoder.encode(keyOrd) %>"+"&thTarget=NEW&thAction=UPDATE_RIGHE";
		window.open(urlOrdAcq,"Ordine Acquisto","width=1150,height=650");
		<%
		if(daEstratto){
		%>
			parent.opener.parent.parent.runActionDirect('REFRESH','action_submit','YUdsAcquisto','','same','no');
		<%
		}
		%>
			parent.window.close();
		}else{
		<%
		if(daEstratto){
		%>
			parent.opener.parent.parent.runActionDirect('REFRESH','action_submit','YUdsAcquisto','','same','no');
		<%
		}
		%>
		parent.window.close();
	}
</script>	