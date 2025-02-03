function YModificaScProvRigheVenOL(){
	window.resizeTo(800,400);	
}

function conferma(){
	var className = eval("document.forms[0].thClassName.value");
	var conferma = window.confirm('Sei sicuro di voler propagare le modiifche alle righe?');
	if(conferma){
		runActionDirect('MOD_MASS_SCH_PROVV', 'action_submit', className , '', 'errorsFrame', 'no');
	}
}
