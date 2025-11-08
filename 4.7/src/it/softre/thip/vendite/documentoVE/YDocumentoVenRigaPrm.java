package it.softre.thip.vendite.documentoVE;

import java.math.BigDecimal;
import java.sql.SQLException;
import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.CopyException;
import com.thera.thermfw.persist.Copyable;
import com.thera.thermfw.persist.KeyHelper;
import com.thera.thermfw.persist.Proxy;
import it.caleido.thip.vendite.generaleVE.YCausaleDifetto;
import it.caleido.thip.vendite.generaleVE.YCausaleRigaDocVen;
import it.thera.thip.base.agentiProvv.AgentiScontiProvv;
import it.thera.thip.base.articolo.Articolo;
import it.thera.thip.base.azienda.Azienda;
import it.thera.thip.vendite.documentoVE.DocumentoVenRigaPrm;
import it.thera.thip.vendite.documentoVE.DocumentoVendita;
import it.thera.thip.vendite.generaleVE.PersDatiVen;
import it.thera.thip.vendite.generaleVE.RicercaCondizioniDiVendita;

/**
 * <h1>Softre Solutions</h1>
 * @author Daniele Signoroni	04/10/2023
 *	<br><br>
 *	<b>70687	DSSOF3	04/10/2022</b>	Cancellazione dei riferimenti al DocumentoVendita sulla tabella SOFTRE.YUDS_VEN_RIG
 *	<b>71011	DSSOF3	21/03/2023</b>	<br>Merge UDS.<br>
 *										<br>In seguito all'installazione delle UDS e' stata inibita la classe:<br>
 *										{@link it.caleido.thip.vendite.documentoVE.YDocumentoVenRigaPrmo} <br>
 *										Sostituita dalla classe:<br>
 *										{@link it.softre.thip.vendite.documentoVE.YDocumentoVenRigaPrm}
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72184    06/11/2025  GLSOF3   Aggiunto campo/relazione Causale difetto
 */

public class YDocumentoVenRigaPrm extends DocumentoVenRigaPrm{

	protected Proxy iCausaledifetto = new Proxy(it.caleido.thip.vendite.generaleVE.YCausaleDifetto.class); //72184

	@SuppressWarnings("deprecation")
	public void modificaProvv2Agente() throws SQLException {
		Articolo articolo = getArticolo();
		String idLineaProdotto = articolo.getIdLineaProdotto();
		String idMacroFamiglia = articolo.getIdMacroFamiglia();
		String idSubFamiglia = articolo.getIdSubFamiglia();
		String idMicroFamiglia = articolo.getIdMicroFamiglia();

		//MG FIX 4348
		PersDatiVen pdv = PersDatiVen.getCurrentPersDatiVen();
		BigDecimal sconto = null;
		if(pdv.getGestionePvgSuScalaSconti()) { //27616
			BigDecimal provvSuPrezzoExtra = calcoloProvvigioniSuPrezzoExtra();//Fix 13515
			BigDecimal magg = getMaggiorazione();
			if(magg != null && magg.compareTo(BigDecimal.ZERO) == 1) {
				magg = BigDecimal.ZERO;
			}
			if(pdv.getScontiEsaminati() == PersDatiVen.SCONTI_TESTATA_RIGA)
				sconto =
				RicercaCondizioniDiVendita.calcoloScontoDaScontiRiga(
						getPrcScontoIntestatario(),
						getPrcScontoModalita(),
						getScontoModalita(),
						//getScontoArticolo1(),
						getValue(getScontoArticolo1(),provvSuPrezzoExtra),//Fix 13515
						//Fix 26145 - inizio
						//                getScontoArticolo2(),
						getScontoArticolo2(),
						//Fix 26145 - fine
						//Fix 24299 - inizio
						//              getMaggiorazione(),
						magg,
						//Fix 24299 - fine
						getSconto(),
						2
						);
			else
				sconto =
				RicercaCondizioniDiVendita.calcoloScontoDaScontiRiga(
						//getScontoArticolo1(),
						getValue(getScontoArticolo1(),provvSuPrezzoExtra),//Fix 13515
						//Fix 26145 - inizio
						//                getScontoArticolo2(),
						getScontoArticolo2(),
						//Fix 26145 - fine
						//Fix 24299 - inizio
						//              getMaggiorazione(),
						magg,
						//Fix 24299 - fine
						getSconto(),
						2
						);

			//AGSOF3 REMMATA RIGA SOTTO 
			//			sconto = getScontoProvv2Pers(sconto);	//Fix 28653

		} //27616
		//MG FIX 4348

		//MG FIX 10750 inizio
		//if (isServeRicalProvvAg() || isServeRicalProvvSubag()) { //Fix 25214 PM
		//if (isServeRicalProvvAg() || isServeRicalProvvSubag()  || !isOnDB()) { //Fix 25214 PM//Fix 26599
		//		if (isServeRicalProvvAg() || isServeRicalProvvSubag()  || isRicalProvvAgSubag()) { //Fix 26599 //AGSOF3 REMMATA
		if (condVen == null)
			condVen = recuperaCondizioniVendita((DocumentoVendita)this.getTestata());
		//		}
		//MG FIX 10750 fine

		//if(isServeRicalProvvAg()) //Fix 25214 PM
		//if(isServeRicalProvvAg() || !isOnDB()) //Fix 25214 PM//Fix 26599
		//		if(isServeRicalProvvAg() || isRicalProvvAgSubag()) //Fix 25214 PM//Fix 26599 //AGSOF3 REMMATA
		//		{
		BigDecimal provv2 = null; //27616
		if(pdv.getGestionePvgSuScalaSconti()) { //27616      	

			provv2 =
					AgentiScontiProvv.getProvvigioneDaSconto(
							Azienda.getAziendaCorrente(),
							getIdAgente(),
							getCodiceCliente(),//Fix 22229
							idLineaProdotto,
							idMacroFamiglia,
							idSubFamiglia,
							idMicroFamiglia,
							sconto
							);
			//MG FIX 10750 inizio
			BigDecimal nuovaProvvigione = provv2;
			BigDecimal vecchiaProvvigione = null;
			if (condVen != null)
				vecchiaProvvigione = condVen.getProvvigioneAgente2();
			if (vecchiaProvvigione != null) {

				char condPvgScalaSconti = PersDatiVen.getCurrentPersDatiVen().getCondizPvgScalaSconti();
				if (condPvgScalaSconti == PersDatiVen.PVG_SCALA_SCONTI_MINIMA) {
					if ( (nuovaProvvigione == null) || (nuovaProvvigione.compareTo(vecchiaProvvigione) == 1) )
						provv2 = vecchiaProvvigione;
					else
						provv2 = nuovaProvvigione;
				}
				else if (condPvgScalaSconti == PersDatiVen.PVG_SCALA_SCONTI_PRIOR) {
					if (nuovaProvvigione == null)
						provv2 = vecchiaProvvigione;
					else
						provv2 = nuovaProvvigione;
				}
			}
			//MG FIX 10750 fine

			//27616 inizio
		} 
		else {
			if(condVen != null)//Fix 31168
				provv2 = condVen.getProvvigioneAgente2();
		}
		//27616 fine
		//Fix 3738 (aggiunto controllo su null) - inizio
		if(provv2 != null)
		{
			setProvvigione2Agente(provv2);
		}
		//Fix 3738 - fine
		//		}

		//if(isServeRicalProvvSubag()) //Fix 25214 PM
		//if(isServeRicalProvvSubag() || !isOnDB()) //Fix 25214 PM//Fix 26599
		//		if(isServeRicalProvvSubag() || isRicalProvvAgSubag()) //Fix 26599 //AGSOF3 REMMATA
		//		{
		provv2 = null; //27616
		if(pdv.getGestionePvgSuScalaSconti()) { //27616

			provv2 =
					AgentiScontiProvv.getProvvigioneDaSconto(
							Azienda.getAziendaCorrente(),
							getIdAgenteSub(),
							getCodiceCliente(),//Fix 22229
							idLineaProdotto,
							idMacroFamiglia,
							idSubFamiglia,
							idMicroFamiglia,
							sconto
							);
			//MG FIX 10750 inizio
			BigDecimal nuovaProvvigione = provv2;
			BigDecimal vecchiaProvvigione = null;
			if (condVen != null)
				vecchiaProvvigione = condVen.getProvvigioneSubagente2();
			if (vecchiaProvvigione != null) {
				char condPvgScalaSconti = PersDatiVen.getCurrentPersDatiVen().getCondizPvgScalaSconti();
				if (condPvgScalaSconti == PersDatiVen.PVG_SCALA_SCONTI_MINIMA) {
					if ( (nuovaProvvigione == null) || (nuovaProvvigione.compareTo(vecchiaProvvigione) == 1) )
						provv2 = vecchiaProvvigione;
					else
						provv2 = nuovaProvvigione;
				}
				else if (condPvgScalaSconti == PersDatiVen.PVG_SCALA_SCONTI_PRIOR) {
					if (nuovaProvvigione == null)
						provv2 = vecchiaProvvigione;
					else
						provv2 = nuovaProvvigione;
				}
			}
			//MG FIX 10750 fine

			//27616 inizio
		} 
		else {
			if(condVen != null)//Fix 31168
				provv2 = condVen.getProvvigioneSubagente2();
		}
		//27616 fine
		//Fix 3738 (aggiunto controllo su null) - inizio
		if(provv2 != null)
		{
			setProvvigione2Subagente(provv2);
		}
		//Fix 3738 - fine
		//		}
	}

	//<fix 72184 inizio
	public YDocumentoVenRigaPrm() {
		setIdAzienda(Azienda.getAziendaCorrente());
	}

	public void setCausaledifetto(YCausaleDifetto causaledifetto) {
		String oldObjectKey = getKey();
		this.iCausaledifetto.setObject(causaledifetto);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public YCausaleDifetto getCausaledifetto() {
		return (YCausaleDifetto)iCausaledifetto.getObject();
	}

	public void setCausaledifettoKey(String key) {
		String oldObjectKey = getKey();
		iCausaledifetto.setKey(key);
		setDirty();
		if (!KeyHelper.areEqual(oldObjectKey, getKey())) {
			setOnDB(false);
		}
	}

	public String getCausaledifettoKey() {
		return iCausaledifetto.getKey();
	}

	public void setIdAzienda(String idAzienda) {
		super.setIdAzienda(idAzienda);
		if(iCausaledifetto != null) {
			String key = iCausaledifetto.getKey();
			iCausaledifetto.setKey(KeyHelper.replaceTokenObjectKey(key , 1, idAzienda));
		}
	}

	public void setIdCauDifetto(String idCauDifetto) {
		String key = iCausaledifetto.getKey();
		iCausaledifetto.setKey(KeyHelper.replaceTokenObjectKey(key , 2, idCauDifetto));
		setDirty();
	}

	public String getIdCauDifetto() {
		String key = iCausaledifetto.getKey();
		String objIdCauDifetto = KeyHelper.getTokenObjectKey(key,2);
		return objIdCauDifetto;
	}

	public void setEqual(Copyable obj) throws CopyException {
		super.setEqual(obj);
		YDocumentoVenRigaPrm yDocumentoVenRigaPrm = (YDocumentoVenRigaPrm)obj;
		iCausaledifetto.setEqual(yDocumentoVenRigaPrm.iCausaledifetto);
	}

	public ErrorMessage checkIdCauDifetto() {
		ErrorMessage em = null;
		if( ((YCausaleRigaDocVen)getCausaleRiga()).getCauDifettoObbligatoria() == true && getCausaledifetto() == null) {
			em = new ErrorMessage("BAS0000078", "È obbligatorio inserire una causale difetto");
		}
		return em;
	}
	//fix 72184 fine>
}
