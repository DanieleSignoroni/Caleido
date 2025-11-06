function YCaleidoEvasioneUdsAcquisOL(){
	window.resizeTo(600,350);
	document.getElementById("DataRifIntestatario").style.backgroundColor = 'rgb(255, 255, 153)';
	document.getElementById("NumeroRifIntestatario").style.backgroundColor = 'rgb(255, 255, 153)';
	checkCampiObbligatori();
}

function evasioneUds() {
	var dataDocumento = document.getElementById('DataDocumento').value;
	var idSerie = document.getElementById('RSerie').value;
	var idCausale = document.getElementById('RCauDocTes').value;
	var idFornitore = document.getElementById('RFornitore').value;
	var dataRifFornitore = document.getElementById("DataRifIntestatario").value;
	var numeroRifFornitore = document.getElementById("NumeroRifIntestatario").value; 
	if(idSerie != "" && idCausale!= ""){
		if(checkStyleCampi()){
			var url ="/" + webAppPath + "/" + servletPath + "/it.softre.thip.acquisti.uds.web.YEvasioneUdsAcquistoFormActionAdapter";
			url += "?Data="+dataDocumento+"&Serie="+idSerie+"&Causale="+idCausale+"&Fornitore="+idFornitore+"&DataRifIntestatario="+dataRifFornitore+"&NumeroRifIntestatario="+numeroRifFornitore;
			var f = document.getElementById('errorsFrameYCaleidoEvasioneUdsAcquis').contentWindow ;
			setLocationOnWindow(f,url)
		}
	}
	else{
		alert("Compilare tutti i campi");
	}
}

function checkStyleCampi(){
	var ret = true;
	if(parent.document.getElementById("riferimenti").parentNode.parentNode.style.display == 'revert'){
		if(document.getElementById('DataRifIntestatario').value == "" && document.getElementById('NumeroRifIntestatario').value == ""){
			alert("Campi riferimento cliente obbligatori");
			ret = false;
		}
	}
	return ret;
}

function checkCampiObbligatori(){
	var idCausaleOrdAcq = document.getElementById('IdCauOrdAcqTes').value;
	var url = "/" + parent.webAppPath + "/" + parent.servletPath + "/it.caleido.thip.acquisti.uds.web.YCaleidoControlloCausaliEvasione";
	url += "?CausaleOrdineAcquisto="+idCausaleOrdAcq;
	var f = document.getElementById('errorsFrameYCaleidoEvasioneUdsAcquis').contentWindow ;
	setLocationOnWindow(f,url)
}