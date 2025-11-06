package it.caleido.thip.acquisti.uds;

import java.sql.SQLException;
import java.util.Iterator;

import com.thera.thermfw.base.TimeUtils;
import com.thera.thermfw.base.Trace;
import com.thera.thermfw.persist.Factory;

import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.generale.PersDatiGen;
import it.thera.thip.datiTecnici.modpro.ImpNodoArticolo;
import it.thera.thip.datiTecnici.modpro.ModproImplosione;

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

	@SuppressWarnings("rawtypes")
	public Articolo trovaVerniciatoTramiteGrezzo(Articolo grezzo) {
		Articolo vern = null;
		ModproImplosione modproImplosione = (ModproImplosione) Factory.createObject(ModproImplosione.class);
		modproImplosione.setArticolo(grezzo);
		modproImplosione.setIdStabilimento(PersDatiGen.getCurrentPersDatiGen().getIdStabilimento());
		modproImplosione.setData(TimeUtils.getCurrentDate());
		modproImplosione.setTipoImplosione(ModproImplosione.PRODUZIONE);
		modproImplosione.setDominio(ModproImplosione.PIANIFICAZIONE);
		modproImplosione.setLivello(1);
		try {
			modproImplosione.run();
			 if (modproImplosione.getNodoRadice() == null) {
				 return null;
			 }else {
				 Iterator iterNodiFigli = modproImplosione.getNodoRadice().getNodiFigli().iterator();
				 while(iterNodiFigli.hasNext()) {
					 ImpNodoArticolo nodo = (ImpNodoArticolo) iterNodiFigli.next();
					 vern = nodo.getArticolo().getArticolo();
					 break;
				 }
			 }
		} catch (SQLException e) {
			e.printStackTrace(Trace.excStream);
		}
		return vern;
	}
}
