package it.caleido.thip.acquisti.uds;

import com.thera.thermfw.persist.Factory;

import it.thera.thip.base.articolo.Articolo;

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

public class YRecuperoVerniciatoEvasioneUds {

	private static YRecuperoVerniciatoEvasioneUds instance;

	public static YRecuperoVerniciatoEvasioneUds getInstance() {
		if(instance == null) {
			instance = (YRecuperoVerniciatoEvasioneUds) Factory.createObject(YRecuperoVerniciatoEvasioneUds.class);
		}
		return instance;
	}

	public Articolo trovaVerniciatoTramiteGrezzo(Articolo grezzo) {
		Articolo vern = null;
		return vern;
	}
}
