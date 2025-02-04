<%@page import="it.thera.thip.cs.ColonneFiltri"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="it.caleido.thip.vendite.generaleVE.YModificaScontiProvvigioniRigheVendita"%>
<%@page import="com.thera.thermfw.collector.BODataCollector"%>
<%@page import="java.util.Enumeration"%>
<%
String className = request.getParameter("ClassName");
String chiaviSel = request.getParameter("ChiaviSelezionati");
List<String> keys = Arrays.asList(chiaviSel.split(ColonneFiltri.LISTA_SEP));
for(String key : keys){
	%>
		<script>
<%-- 			parent.opener.updateGridForSave('<%=className%>','UPDATE','<%=key%>'); --%>
// 			parent.opener.enableFormActions();
		</script>
	<%
}
%>
<jsp:include page="/com/thera/thermfw/common/ErrorListHandler.jsp" flush="true"/> 