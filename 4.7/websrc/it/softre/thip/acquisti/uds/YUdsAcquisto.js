function YUdsAcquistoOL(){
	if(document.getElementById('thMode').value == 'COPY'){
		document.getElementById('IdUds').value = '0';
	}
	window.resizeTo(1200,700);
	getNumeroFigli();
}

function passaATestata(){
	var thKey = document.getElementById('thKey').value;
	var url ="/" + webAppPath + "/it/softre/thip/acquisti/uds/YUdsAcquistoNuovo.jsp?Mode=UPDATE&Key="+thKey+"&InitialActionAdapter=it.thera.thip.cs.web.AziendaGridActionAdapter";
	window.location = url;

}

function getNumeroFigli(){
	var thKey = document.getElementById('thKey').value;
	var url ="/" + webAppPath + "/" + servletPath + "/it.softre.thip.acquisti.uds.web.YRecuperaNumeroFigli?Key="+thKey;
	var f = document.getElementById('thLightSearchMenu').contentWindow ;
	setLocationOnWindow(f,url);
}

function evasioneUds(){
	var className = document.forms[0].thClassName.value;
	var key = document.forms[0].thKey.value;
 	runActionDirect("EVADI_UDS","action_submit",className,key,"infoArea","no");
}

function convalidaYUdsAcquisto(){
	var thKey = document.getElementById('thKey').value;
	var url ="/" + webAppPath + "/it/softre/thip/acquisti/uds/YConvalidaUdsAcquisto.jsp?Key="+thKey;
	window.open(url,"Convalida Uds Acquisto","width=800,height=500");
}

function setReadOnly(campo){
	campo.readOnly = true;
	campo.style.backgroundColor = 'rgb(232, 232, 232)';
}

function regressioneYUdsAcquisto(){
	var thKey = document.getElementById('thKey').value;
	var url ="/" + webAppPath + "/" + servletPath + "/it.softre.thip.acquisti.uds.web.YRegressioneUdsAcquistoGridActionAdapter?Key="+thKey;
	var f = document.getElementById('thLightSearchMenu').contentWindow ;
	setLocationOnWindow(f,url);
}

function stampaEtichetta(){
	var thKey = document.getElementById('thKey').value;
	var url ="/" + webAppPath + "/" + servletPath + "/it.softre.thip.acquisti.uds.web.YStampaEtichettaUdsAcquisto?Key="+thKey;
	var f = document.getElementById('thLightSearchMenu').contentWindow ;
	setLocationOnWindow(f,url);
}