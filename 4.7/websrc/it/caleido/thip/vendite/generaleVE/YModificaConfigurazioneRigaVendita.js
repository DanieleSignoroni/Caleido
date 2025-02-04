function YModificaConfRigheVenOL() {

}

function openVariabile(idVariabile, idSezione, idSchemaCfg) {
	var idAzienda = eval('document.forms[0].' + idFromName['IdAzienda']).value;
	var schemaKey = idAzienda + fsep + idSchemaCfg;
	var url = "/" + webAppPath + "/it/thera/thip/datiTecnici/configuratore/SchemaCfg.jsp?Mode=UPDATE&Key=" + schemaKey +
		"&thUSRPVariabile=" + idVariabile + "&thUSRPSezione=" + idSezione;
	var winName = "newWind" + Math.round(Math.random() * 1000000);
	window.open(url, winName, "width=800, height=600, resizable=yes");
}

function setupNSFM(field, typeJS, valueStr, NLSValue, isFCheckAll, isFKey, isActionEnable, FClassName, FTooltip, FRelatedAD, FIsRight, FOnSearchBack, butIdValue) {
	setupCSFM(field, typeJS, valueStr, NLSValue, isFCheckAll, isFKey, isActionEnable, FClassName, FTooltip, FRelatedAD, FIsRight, FOnSearchBack, null, null, null, null, butIdValue);
}

function setupCSFM(field, typeJS, valueStr, NLSValue, isFCheckAll, isFKey, isActionEnable, FClassName, FTooltip, FRelatedAD, FIsRight, FOnSearchBack, FFixedADRels, FFixedADRelValues, FAdditADRels, FAdditADAtts, butIdValue) {
	addIdFromName(field.name, field.id);
	addCorrNameFromName(field.name, FRelatedAD);
	field.value = valueStr;
	field.typeNameJS = typeJS;
	field.isRight = !FIsRight; //Fix 30331
	if (isActionEnable)
		field.butIdValue = butIdValue;
	else
		field.butIdValue = "";

	if (FClassName)
		field.className = FClassName;

	if (FTooltip != null)
		field.title = FTooltip;
	else
		field.title = typeJS.getTooltipText();

	//if(FIsRight)
	//{
	NLSFld[field.name] = NLSValue;
	field.isCheckAll = isFCheckAll;

	if (!isFKey)
		//Mod. 2828:controllo obbligatorietà anche per le chiavi{
		field.isKey = true;
	if (typeJS != null && typeJS.mandatory)
		field.isMand = true;
	//fine mod. 2828}
	//Fix 02645 MM fine

	if (FFixedADRels != null) {
		field.fixedADRels = FFixedADRels;
		field.fixedADRelValues = FFixedADRelValues;
	}
	if (FAdditADRels != null) {
		field.additADRels = FAdditADRels;
		field.additADAtts = FAdditADAtts;
	}
	//}
	field.isActionEnable = isActionEnable; //mOD.806
	//field.readOnly = -1;//Fix 11994
	//Fix 11994 blocco commentato
	if (!isActionEnable) {
		field.readOnly = -1;
		field.style.background = document.forms[0].style.background;
	}
	else {
		//if(FIsRight && FOnSearchBack) Mekki
		if (FIsRight && FOnSearchBack)
			field.onSearchBack = FOnSearchBack;
		if (field.addOB)
			//field.onblur = function() {typeJS.onBlur(); field.addOB();}; // Fix 10897
			defineEvent(field, eventBLUR, function() {
				//12722
				if (typeJS && typeJS.onBlur)
					typeJS.onBlur();
				if (field && field.addOB)
					field.addOB();
			});

		else
			//			defineEvent(field, eventBLUR, function() {searchOnBlurEvent(field.typeNameJS, field.name);});
			//field.onblur = function() {typeJS.onBlur();}; // Fix 10897
			defineEvent(field, eventBLUR, function() {
				if (typeJS && typeJS.onBlur) //12722
					typeJS.onBlur();
			});

		if (field.addOF)
			//field.onfocus = function() {field.addOF(); typeJS.onFocus();};  // Fix 10897
			//defineEvent(field, eventFOCUS,function() {field.addOF(); typeJS.onFocus();});// Fix 10897//30682
			defineEvent(field, eventFOCUS, function() { field.addOF(); typeJS.onFocus(); ricercaLight(field); });// Fix 10897 //30682
		else
			//field.onfocus = function() {typeJS.onFocus();};  // Fix 10897
			//defineEvent(field, eventFOCUS,function() {typeJS.onFocus();});// Fix 10897//30682
			defineEvent(field, eventFOCUS, function() { typeJS.onFocus(); ricercaLight(field); });// Fix 10897 //30682

		if (field.addOC)
			//field.onchange = function() {clearSearch(field.name, field.isRight); typeJS.onBlur(); field.addOC();}; //GN modifica solo nella versione Thip 2.1.0 // Fix 10897
			//defineEvent(field, eventCHANGE,function() {clearSearch(field.name, field.isRight); typeJS.onBlur(); field.addOC();});// Fix 10897
			defineEvent(field, eventCHANGE, function() { clearSearch(field.name, field.isRight); typeJS.onBlur(); field.addOC(); decodeOnBlur(field); });// Fix 13037
		else
			//field.onchange =  function() {clearSearch(field.name, field.isRight);}; // Fix 10897
			//defineEvent(field, eventCHANGE,function() {clearSearch(field.name, field.isRight);});// Fix 10897
			defineEvent(field, eventCHANGE, function() { clearSearch(field.name, field.isRight); typeJS.onBlur(); decodeOnBlur(field); });// Fix 13037

		//field.onkeydown = function() {typeJS.onKeyDown();handleKeyboardEnter(field)}; // Fix 10897
		defineEvent(field, eventKEYDOWN, function() { typeJS.onKeyDown(); return handleKeyboardEnter(field) });// Fix 10897 //Fix 13004

		//field.onkeyup = function() {searchOnKeyUpType(field.typeNameJS, field.name, field.isRight);}; //Fix 10897
		defineEvent(field, eventKEYUP, function() { searchOnKeyUpType(field.typeNameJS, field.name, field.isRight); }); //Fix 10897

		//Fix 13232 inizio
		//Fix 14077 righe commentate
		//if (field.className == null || field.className == "")
		//field.className = "searchInputClass";
		defineEvent(field, eventMOUSEDOWN, function() { displayLightSearchMenu(field) });
		defineEvent(field, eventCLICK, function() { openLightSearchMenu(field) });
		//Fix 13232 fine
	}
}

function fillValoriInternal(id, primoValore, secondoValore, iconaValore, valoreEsterno, immagineValore) {
	if (iconaValore != '') {
		eval("document.forms[0].IconaValore_" + id + "TD").style.display = displayBlock; //Fix 12139
		eval("document.forms[0].IconaValore_" + id).src = iconaValore;
	}
	if (immagineValore != '') {
		eval("document.forms[0].ImmagineValore_" + id).value = immagineValore;
	}

	if (valoreEsterno == 'Da catalogo esterno' || valoreEsterno == 'C') {
		eval("document.forms[0].thCatalogo" + id + "SearchBut").style.display = displayBlock; //Fix 12139
		//eval("document.forms[0].thCatalogo"+ id +"EditBut").style.display = '';
		eval("document.forms[0]." + idFromName["Catalogo" + id + ".DescrizioneEstesa"]).style.display = displayBlock; //Fix 12139
		eval("document.forms[0].Valore_" + id).parentNode.colSpan = 3; //Fix 12139
		//eval("document.forms[0].PrimoValore_"+ id).parentElement.style.display = 'none';
		eval("document.forms[0].PrimoValore_" + id).parentNode.style.display = displayNone; //Fix 12139
		//eval("document.forms[0].SecondoValore_"+ id).parentElement.style.display = 'none';
		eval("document.forms[0].Valore_" + id).size = 10;
		eval("document.forms[0].Valore_" + id).readOnly = false;
		eval("document.forms[0].Valore_" + id).style.background = sCo;
	}
	else if (valoreEsterno == 'No' || valoreEsterno == 'N') {
		if (eval("document.forms[0].Intervallo_" + id).value == 'N' || eval("document.forms[0].th" + id + "SearchBut").disabled) { //Fix 12084
			eval("document.forms[0].Valore_" + id).readOnly = true;
			eval("document.forms[0].Valore_" + id).style.background = bCo; //Fix 12139
		}
		else {
			eval("document.forms[0].Valore_" + id).readOnly = false;
			eval("document.forms[0].Valore_" + id).style.background = sCo;
		}

		if (eval("document.forms[0].PrimoValore_" + id) != undefined)//30331
			eval("document.forms[0].PrimoValore_" + id).parentNode.style.display = displayBlock; //Fix 12139
		//eval("document.forms[0].SecondoValore_"+ id).parentElement.style.display = '';
		//Fix 12738 inizio
		var tmp = eval("document.forms[0].Valore_" + id).value;
		eval("document.forms[0].Valore_" + id).value = eval("document.forms[0].Valore_" + id).typeNameJS.format(tmp);
		var intervallo = eval("document.forms[0].Valore_" + id).typeNameJS.format(primoValore);
		if (secondoValore != null && secondoValore != "")
			intervallo += " - " + eval("document.forms[0].Valore_" + id).typeNameJS.format(secondoValore);
		//Fix 12738 fine
		if (eval("document.forms[0].PrimoValore_" + id) != undefined)//30331
			eval("document.forms[0].PrimoValore_" + id).value = intervallo;
		//eval("document.forms[0].SecondoValore_"+ id).value = secondoValore;
		eval("document.forms[0].Valore_" + id).parentNode.colSpan = 1; //Fix 12139
		eval("document.forms[0].thCatalogo" + id + "SearchBut").style.display = displayNone; //Fix 12139
		//eval("document.forms[0].thCatalogo"+ id +"EditBut").style.display = 'none';
		eval("document.forms[0]." + idFromName["Catalogo" + id + ".DescrizioneEstesa"]).style.display = displayNone; //Fix 12139
	}
	else {
		eval("document.forms[0].Valore_" + id).readOnly = false;
		eval("document.forms[0].Valore_" + id).style.background = sCo;
	}
}

function fillValori(idVariabile, seqValore, descValore, primoValore, secondoValore, iconaValore, valoreEsterno, immagineValore) {
	document.getElementById('ValoriSearchWin').style.display = displayNone; //Fix 12139
	var id = null;
	var inputs = document.forms[0].getElementsByTagName("input");
	for (var i = 0; i < inputs.length && id == null; i++) {
		var tmp = inputs[i].getAttribute("idVariabileCfg");
		if (tmp == idVariabile)
			id = inputs[i].id;
	}
	id = id.split('$')[0];
	myRemoveComponentFromError(getInnerText(document.getElementById("DescrVar_" + id))); // Fix 12139
	eval("document.forms[0]." + idFromName[id + ".Descrizione.Descrizione"]).value = descValore;
	eval("document.forms[0].SequenzaValore_" + id).value = seqValore;
	eval("document.forms[0].Valore_" + id).value = primoValore;
	fillValoriInternal(id, primoValore, secondoValore, iconaValore, valoreEsterno, immagineValore);
}

function ricercaLight(field) {
	//if(field.name.indexOf("Var") == 0){//33417
	if (field.name.indexOf("Var") == 0 && !field.readOnly) { //33417
		componentInSearch = field.name;
		var searchWin = document.getElementById("ValoriSearchWin");
		var url = "/" + webAppPath + "/" + servletPath + "/it.thera.thip.datiTecnici.configuratore.web.ValoriSearchWinServlet";
		var idAzienda = eval('document.forms[0].' + idFromName['IdAzienda']).value;
		var idSchema = document.getElementById('IdSchemaCfg').value;
		var curComp = eval("document.forms[0]." + idFromName[field.name]);
		//30331 inizio
		var curVal = "";
		if (curComp.value != null) {
			var curVal = curComp.value;
		}
		//30331 fine
		thLightSearchFrameOver = true;
		//var idVar = curComp.idVariabileCfg;
		var idVar = curComp.getAttribute("idVariabileCfg"); //Fix 12139
		var varKey = idAzienda + fsep + idSchema + fsep + idVar;
		var sint = buildSintesiConfig();
		var idSez = trovaSezione(curComp);
		var idConfig = eval('document.forms[0].' + idFromName['IdConfigurazione']).value; //Fix 13478
		var idArt = eval('document.forms[0].' + idFromName['IdArticolo']).value; //Fix 15697
		var params = "Var=" + URLEncode(varKey) + "&Sint=" + URLEncode(sint) + "&Sez=" + URLEncode(idSez) + "&IdConfig=" + URLEncode(idConfig) + "&IdArt=" + URLEncode(idArt); //Fix 12442 //Fix 13478 //Fix 15697
		params = params + "&CurVal=" + URLEncode(curVal);//30331
		tmpX = lightSearchLeftPosition(field);
		//tmpY = lightSearchTopPosition(field) + 20;  //Fix 31770
		tmpY = lightSearchTopPosition(field) - lightSearchScrollTop(field) + 20; //Fix 31770
		var formTxt = getSearchGridWinText(url + "?" + params);
		searchWin.contentWindow.document.open();
		searchWin.contentWindow.document.write(formTxt);
		searchWin.contentWindow.document.close();
		searchWin.contentWindow.document.forms[0].submit();
	}

}

var oldDoSearch = doSearch;
doSearch = function(rightFieldName, url, extraRelatedClassAD, specificDOList) {
	if (rightFieldName.indexOf("Var") == 0) {
		componentInSearch = rightFieldName;
		var searchWin = document.getElementById("ValoriSearchWin");
		var url = "/" + webAppPath + "/" + servletPath + "/it.thera.thip.datiTecnici.configuratore.web.ValoriSearchWinServlet";
		var idAzienda = eval('document.forms[0].' + idFromName['IdAzienda']).value;
		var idSchema = document.getElementById('IdSchemaCfg').value;
		var curComp = eval("document.forms[0]." + idFromName[rightFieldName]);
		var idVar = curComp.getAttribute("idVariabileCfg"); //Fix 12139
		var varKey = idAzienda + fsep + idSchema + fsep + idVar;
		var sint = buildSintesiConfig();
		var idSez = trovaSezione(curComp);
		var idConfig = eval('document.forms[0].' + idFromName['IdConfigurazione']).value; //Fix 13478
		var idArt = eval('document.forms[0].' + idFromName['IdArticolo']).value; //Fix 15697
		var params = "Var=" + URLEncode(varKey) + "&Sint=" + URLEncode(sint) + "&Sez=" + URLEncode(idSez) + "&IdConfig=" + URLEncode(idConfig) + "&IdArt=" + URLEncode(idArt); //Fix 12442 //Fix 13478 //Fix 15697
		params = params + "&CurVal=";//+ URLEncode(curVal);//30331//30682
		tmpX = lightSearchLeftPosition(curComp);//window.event.clientX+document.body.scrollLeft;
		tmpY = lightSearchTopPosition(curComp) - lightSearchScrollTop(curComp) + 20; //Fix 31770
		var formTxt = getSearchGridWinText(url + "?" + params);
		searchWin.contentWindow.document.open();
		searchWin.contentWindow.document.write(formTxt);
		searchWin.contentWindow.document.close();
		searchWin.contentWindow.document.forms[0].submit();
	}
	else
		oldDoSearch(rightFieldName, url, extraRelatedClassAD, specificDOList);
}

function trovaSezione(startElement) {
	var table = startElement.closest('table');
	var sezione = '';
	if (table != undefined && table != null) {
		sezione = table.getAttribute('id');
	}
	return sezione;
}

function buildSintesiConfig() {//Fix 11994
	var first = true;
	var aStringa = '';
	var idValore = '';
	var idVariabile = '';
	var value = '';
	var idVarCfg = '';//Fix 11994
	var inputs = document.forms[0].getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		var id = inputs[i].getAttribute("id");
		if (id.indexOf('SequenzaValore_') == 0) {
			idVariabile = id.substring(id.indexOf('_') + 1);
			var seqValVar = "document.forms[0].SequenzaValore_" + idVariabile;
			idValore = eval(seqValVar).value;
			//idVarCfg  = eval(seqValVar).idVariabileCfg;//Fix 11994
			idVarCfg = eval(seqValVar).getAttribute("idVariabileCfg");//Fix 12139
		}
		if (idValore != '') {
			if (id.indexOf('Valore_') == 0) {
				//Fix 12738 inizio
				var field = eval("document.forms[0].Valore_" + idVariabile);
				value = field.value;
				value = field.typeNameJS.unformat(value);
				//Fix 12738 fine
				if (value != '') {
					if (first == true) {
						aStringa += idVarCfg + fsep + value + fsep + idValore;//Fix 11994
						first = false;
					}
					else {
						aStringa += fsep + idVarCfg + fsep + value + fsep + idValore;//Fix 11994
					}
				}
				idValore = '';
			}
		}
	}
	return aStringa;
}

function showSearchWin(idVariabile, height) { //Fix 12013
	//Fix 12013 inizio
	var id = null;
	var inputs = document.forms[0].getElementsByTagName("input");
	for (var i = 0; i < inputs.length && id == null; i++) {
		var tmp = inputs[i].getAttribute("idVariabileCfg");
		if (tmp == idVariabile)
			id = inputs[i].id;
	}
	id = id.split('$')[0];
	//Fix 12013 fine
	var searchWin = document.getElementById("ValoriSearchWin");
	if (height > 10)
		height = 10;

	//Fix 12703 inizio
	if (isFirefox) {
		height = height * 21 + 1;
		searchWin.scrolling = "auto";
	}
	//Fix 12703 fine
	else
		height = height * 24 + 3;
	if ((tmpY + height) < document.body.clientHeight)
		tmpY -= 3;
	else if ((tmpY - height) > 30) {
		tmpY -= height;
		tmpY += 3;
	}
	else {
		height = document.body.clientHeight - tmpY;
		tmpY -= 3;
	}
	//Fix 12013 inizio
	var left = leftPosition(eval("document.forms[0]." + idFromName[id + ".Descrizione.Descrizione"]));
	if (!isFirefox) //Fix 12703
		left += 2; //Fix 12703
	searchWin.style.left = left;
	searchWin.style.top = tmpY;
	var field = eval("document.forms[0].Valore_" + id); //Fix 12084
	var width = leftPosition(field) - left + 118; //Fix 12084//30331
	searchWin.style.width = width;
	//Fix 12013 fine
	//30682 inizio
	if (isChrome && height >= 100)
		height -= 20;
	else if (isChrome && height >= 60)
		height -= 10;
	//30682 fine
	searchWin.style.height = height;
	searchWin.style.display = displayBlock; //Fix 12139
}

//Fix 12013 inizio
function leftPosition(obj) {
	var curleft = 0;
	if (obj) {
		do {
			curleft += obj.offsetLeft;
		} while (obj = obj.offsetParent);
	}
	return curleft;
}
//Fix 12013 fine

var over = false;
function onframeout() {
	over = false;
	var js = "if (over==false) {";
	js += "  document.getElementById('ValoriSearchWin').style.display = displayNone;"; //Fix 12139
	js += "}";
	setTimeout(js, 400);
}

function onframeover() {
	over = true;
}

function myRemoveComponentFromError(idComponent) {
	var errorViewPresent = false;
	var undefined;
	if (window.errorsViewName != undefined) {
		var errorView = eval(errorsViewName);
		errorViewPresent = true;
	}
	if (errorViewPresent) {
		var errors = errorView.Errors;
		var idx = 0;
		var found = false;
		while (!found && idx < errors.size()) {
			var err = errors.getErrorAt(idx);
			found = err.Text.length == 0 ? false : err.Text.indexOf(idComponent) > 0;
			if (!found)
				idx++;
		}
		if (found)
			errorView.removeErrorFromViewAtPos(idx);
	}
}