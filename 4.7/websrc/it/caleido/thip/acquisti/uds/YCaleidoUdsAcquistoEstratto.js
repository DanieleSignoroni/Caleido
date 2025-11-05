function addBtnEvasionePers(){
	var tr = (document.getElementById('Testata').parentNode).parentNode;
	var btn = document.createElement('button');
	btn.type = 'button' ;
	btn.id = 'EvasPers'; 
	btn.style.width = '30px'; 
	btn.style.height = '30px';
	btn.innerHTML = '<img height="24px" width="24px" src="it/caleido/thip/acquisti/uds/img/EvaOffForDir48.gif"/>';
	btn.setAttribute("onclick","evasionePersCaleido()");
	btn.title = 'Ordine/Documento Acquisto';
	var td = tr.insertCell(1);
	td.noWrap = true;
	td.appendChild(btn);
}

function evasionePersCaleido(){
	runActionDirect("EVADI_UDS_CALEIDO", "action_submit", document.forms[0].thClassName.value, document.forms[0].thKey.value, "infoArea", "no");
}