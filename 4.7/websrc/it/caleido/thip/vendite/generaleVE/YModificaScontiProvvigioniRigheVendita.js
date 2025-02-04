function YModificaScProvRigheVenOL(){
	window.resizeTo(800,400);	
	disabilitaCampiOfferta();
}

/**
 * Se sono in offerta riga non ho questi due campi quindi li nascondo
 */
function disabilitaCampiOfferta(){
	var className = document.forms[0].ClassName.value;
	if(className != undefined && className === "OffertaClienteRigaPrm"){
		document.getElementById('AlfanumRiservatoUtente1').style.display = 'none';
		document.getElementById('FlagRiservatoUtente1').parentNode.style.display = 'none';
		document.getElementById('FlagRiservatoUtente1').parentNode.previousElementSibling.style.display = 'none';
	}
}

function conferma(){
	var className = eval("document.forms[0].thClassName.value");
	var conferma = window.confirm('Sei sicuro di voler propagare le modiifche alle righe?');
	if(conferma){
		runActionDirect('SAVE_AND_CLOSE', 'action_submit', className , '', 'errorsFrame', 'no');
	}
}
