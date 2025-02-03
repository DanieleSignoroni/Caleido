package it.caleido.thip.vendite.generaleVE.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.jsp.JspWriter;

import com.thera.thermfw.web.WebFormModifier;
import com.thera.thermfw.web.servlet.GridActionAdapter;

import it.caleido.thip.vendite.generaleVE.YModificaScontiProvvigioniRigheVendita;
import it.thera.thip.cs.ColonneFiltri;

/**
 * <h1>Softre Solutions</h1>
 * <br>
 * @author Daniele Signoroni 03/02/2025
 * <br><br>
 * <b>71XXX    DSSOF3    03/02/2025</b>
 * <p></p>
 */

public class YModificaScontiProvvigioniRigheVenditaFormModifier extends WebFormModifier {

	@SuppressWarnings("rawtypes")
	@Override
	public void writeHeadElements(JspWriter out) throws IOException {
		YModificaScontiProvvigioniRigheVendita bo = (YModificaScontiProvvigioniRigheVendita) getBODataCollector().getBo();
		String className = getServletEnvironment().getRequest().getParameter(GridActionAdapter.CLASS_NAME);
		String[] objectKeys = getServletEnvironment().getRequest().getParameterValues(GridActionAdapter.OBJECT_KEY);
		String keysSel = "";
		Iterator iterSelected = Arrays.asList(objectKeys).iterator();
		while(iterSelected.hasNext()) {
			String key = (String) iterSelected.next();
			keysSel += key;
			if(iterSelected.hasNext()) {
				keysSel += ColonneFiltri.LISTA_SEP;
			}
		}
		bo.setClassName(className);
		bo.setChiaviSelezionati(keysSel);
		getBODataCollector().setOnBORecursive();
	}

	@Override
	public void writeBodyStartElements(JspWriter out) throws IOException {
		
	}

	@Override
	public void writeFormStartElements(JspWriter out) throws IOException {
		
	}

	@Override
	public void writeFormEndElements(JspWriter out) throws IOException {
		
	}

	@Override
	public void writeBodyEndElements(JspWriter out) throws IOException {
		
	}

}
