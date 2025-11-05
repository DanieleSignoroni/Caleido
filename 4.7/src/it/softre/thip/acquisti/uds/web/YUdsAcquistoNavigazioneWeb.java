/**
 * 
 */
package it.softre.thip.acquisti.uds.web;

import it.thera.thip.base.documenti.web.DocumentoNavigazioneWeb;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 05/11/2025
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    05/11/2025  DSSOF3   Prima stesura
 */

public class YUdsAcquistoNavigazioneWeb extends DocumentoNavigazioneWeb {

	public YUdsAcquistoNavigazioneWeb() {
		iDescrittoreTestata = "YUdsAcquisto";
		iDescrittoreRigaPrm= "YUdsAcqRig";

		iJspTestataEstratto = "it/softre/thip/acquisti/uds/YUdsAcquisto.jsp";
		iJspTestataEstrattoAltezza = 250;

		iJspTestataNuovo = "it/softre/thip/acquisti/uds/YUdsAcquistoNuovo.jsp";
		iJspTestataCompleta = "it/softre/thip/acquisti/uds/YUdsAcquistoNuovo.jsp";

		iJspRigaPrmCompleta = "it/softre/thip/acquisti/uds/YUdsAcqRig.jsp";
		iJspRigaPrmNuovo = "it/softre/thip/acquisti/uds/YUdsAcqRig.jsp";
	}
}
