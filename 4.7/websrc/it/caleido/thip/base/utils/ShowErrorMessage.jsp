<%@page import="com.thera.thermfw.persist.Factory"%>
<%@page import="com.thera.thermfw.web.ServletEnvironment"%>
<%@page import="java.sql.SQLException"%>
<%@page import="javax.naming.NamingException"%>
<html>
<!-- HANDGEN Therm 1.1.9  multiBrowserGen = true -->
<%=com.thera.thermfw.web.WebGenerator.writeRuntimeInfo()%>

<head>
<title>Avviso</title>
<%@ page contentType="text/html; charset=Cp1252"%>
<%@ page import= "com.thera.thermfw.base.*, com.thera.thermfw.common.*, com.thera.thermfw.web.servlet.*, java.util.*, java.net.*"%>

<%-- 38415 - inizio --%>
<%@ page import= "com.thera.thermfw.web.LayoutUtils"%>
<%-- 38415 - fine --%>


<base href="http://<%=request.getServerName()%>:<%=request.getServerPort()%>/<%=IniFile.getValue("thermfw.ini","Web","WebApplicationPath")%>/">
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("thermweb/factory/gui/therm.js", request)%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForJSLibrary("thermweb/factory/gui/components/portal2.js", request)%>
<%=com.thera.thermfw.web.WebGenerator.getMetaTagEmulate()%>
<%=com.thera.thermfw.web.WebJSTypeList.getImportForCSS("thermweb/css/therm.css", request)%>
<%
	String webAppPath = IniFile.getValue("thermfw.ini", "Web", "WebApplicationPath");
	out.println("<script language='javaScript1.2'>");
	out.println(" webAppPath='" + webAppPath + "';");
	out.println(" servletAppPath='" + IniFile.getValue("thermfw.ini", "Web", "ServletPath") + "';");
	out.println(" servletPath='" + IniFile.getValue("thermfw.ini", "Web", "ServletPath") + "';");
	out.println("</script>");
%>
<%-- 38415 - inizio --%>
<%
ServletEnvironment se = (ServletEnvironment)Factory.createObject("com.thera.thermfw.web.ServletEnvironment");
try {
  se.initialize(request, response);
  if (se.begin()) {
%>
</head>
<%
%>
<body leftmargin="1" rightmargin="1" topmargin="1" bottommargin="1" >
<jsp:include page="/com/thera/thermfw/common/LoginHeader.jsp" flush="true"/>

  <div align="center"><center>
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="loginBody">
    <tr>
      <td width="100%" align="center" valign="top"><table border="0"
      width="48%" height="181">
        <tr>
          <td width="100%" height="175"><table border="0" width="99%" height="74" cellspacing="0"
          cellpadding="3">
            <tr align="center">
              <td width="50%" height="38" class="loginEtichetta">
                <div align="center"><p>
					<%
					List  errorMessages = (ArrayList)request.getAttribute("ErrorMessages");
					ErrorMessage err = (ErrorMessage) errorMessages.get(0);
					Enumeration e = request.getParameterNames();
					StringBuffer queryString = new StringBuffer();
					while (e.hasMoreElements())
					{
						String paramName = (String)e.nextElement();
						String[] paramValues = request.getParameterValues(paramName);
						for (int j = 0; j < paramValues.length; j++)
						{
							String paramValue = BaseServlet.removeExternalEncoding(paramValues[j]);
							if (!paramValue.equals("") && !paramName.equals("thtbbut") && !paramName.equals("password"))
								queryString.append("&").append(paramName).append("=").append(URLEncoder.encode(paramValue));
						}
					}
					%>
                    <%=err.getLongText()%>
                 
              </td>
            </tr>
          </table>
          </td>
        </tr>
      </table>
      </td>
    </tr>
  </table>
  </center></div>

<p align="center"></p>
<div align="center"><center>


<jsp:include page="/com/thera/thermfw/common/FooterLogin.jsp" flush="true"/>
</body>
<%
  }
} catch(NamingException e) {
  e.printStackTrace();
} catch(SQLException e) {
  e.printStackTrace();
} finally {
  try {
    se.end();
  } catch(IllegalArgumentException e) {
    e.printStackTrace();
  } catch(SQLException e) {
    e.printStackTrace();
  }
}
%>
%>
</html>