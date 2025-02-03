package it.caleido.thip.vendite.ordineVE.web;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;

import it.thera.thip.vendite.ordineVE.web.OrdineVenditaRigaSecFormModifier;

/**
 * 
 * @author thomas.brescianini
 *
 *	70479	TBSOF3	16/03/2022	Aggiunto bottone per lo sblocco del tab Agenti nella riga secondaria dell'ordine di vendita
 *
 */

public class YOrdineVenditaRigaSecFormModifier extends OrdineVenditaRigaSecFormModifier{


	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		super.writeBodyEndElements(out);
		out.println("<script>");
		out.println("function insertAfter(el, referenceNode) { referenceNode.parentNode.insertBefore(el, referenceNode.nextSibling);}");
		out.println("function sbloccaAgenti(){MainTabbed.enableTab('AgentiTab');}");
		out.println("var btn = document.createElement('Button')");
		out.println("btn.innerHTML='Sblocca Agenti';");
		out.println("btn.type='button';");
		out.println("btn.setAttribute('onclick','sbloccaAgenti()');");
		out.println("var ref = document.getElementById('DatiComuniEstesi$$Stato');");
		out.println("btn.style.padding='0px 2px'");
		out.println("btn.style.marginLeft=10;");
		//out.println("insertAfter(btn,ref);");
		out.println("</script>");
	}
}
