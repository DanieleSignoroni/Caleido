<%@page import="com.thera.thermfw.persist.KeyHelper"%>
<%@page import="com.thera.thermfw.web.servlet.GridActionAdapter"%>
<%@page import="it.thera.thip.base.documenti.web.DocumentoDatiSessione"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.thera.thermfw.persist.Factory"%>
<%@page import="com.thera.thermfw.web.ServletEnvironment"%>
<%@page import="javax.naming.NamingException"%>
<%
ServletEnvironment se = (ServletEnvironment)Factory.createObject(ServletEnvironment.class);
try {
    se.initialize(request, response);
    if(se.begin()) {
    	DocumentoDatiSessione d = DocumentoDatiSessione.getDocumentoDatiSessione(se);
    	String jsp = d.getNavigatore().getJspRigaPrmCompleta();
    	String url = se.getWebApplicationPath() + "/" + jsp + "?Mode=UPDATE&Key=" + com.thera.thermfw.web.URLUtils.get().encode((String)request.getAttribute("ChiaveSelezionato")) + "&InitialActionAdapter=" + GridActionAdapter.class.getName();
    	url += "&" + DocumentoDatiSessione.CHIAVE_DATI_SESSIONE + "=" + d.getChiaveDatiSessione();
    	url += "&" + "thClassName" + "=" + request.getParameter(GridActionAdapter.CLASS_NAME);
    	%>
    	<script>
    		parent.window.location = '<%=url%>';
    	</script>
    	<%
    }
}catch(NamingException e) {
	out.println(e.getMessage());
}catch(SQLException e) {
	out.println(e.getMessage());
}catch(Exception e) {
	e.printStackTrace();
}finally {
	try {
	  se.end();
	}catch(IllegalArgumentException e) {
	   e.printStackTrace();
	}catch(SQLException e) {
	   e.printStackTrace();
	}
}
%>