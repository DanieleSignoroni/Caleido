package it.caleido.thip.base.connettori.utils;

import java.sql.SQLException;
import java.util.Hashtable;

import com.thera.thermfw.common.ErrorMessage;
import com.thera.thermfw.persist.PersistentObject;
import com.thera.thermfw.persist.PersistentObjectCache;

import it.thera.thip.base.azienda.Azienda;

/**
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 31/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72XXX    31/08/2026  DSSOF3   Prima stesura
 */

public class YPsnDatiEcommerce extends YPsnDatiEcommercePO {

	@SuppressWarnings("rawtypes")
	protected static Hashtable iHistory_YPsnDatiECommerce = new Hashtable();

	public static YPsnDatiEcommerce getCurrentYPsnDatiEcommerce()
	{
		return getYPsnDatiEcommerce(Azienda.getAziendaCorrente());
	}

	@SuppressWarnings("unchecked")
	public static YPsnDatiEcommerce getYPsnDatiEcommerce(String iIdAzienda)
	{
		if (iIdAzienda == null)
			return null;

		YPsnDatiEcommerce iYPsnDatiConnPthSl2app = null;

		try
		{
			if(PersistentObjectCache.isEnabled())
			{
				return (YPsnDatiEcommerce)PersistentObject.readOnlyElementWithKey(YPsnDatiEcommerce.class, iIdAzienda);
			}
			else
			{
				if(iHistory_YPsnDatiECommerce.containsKey(iIdAzienda))
					return (YPsnDatiEcommerce)iHistory_YPsnDatiECommerce.get(iIdAzienda);
				else
				{
					iYPsnDatiConnPthSl2app=YPsnDatiEcommerce.elementWithKey(iIdAzienda, PersistentObject.OPTIMISTIC_LOCK);
					if(iYPsnDatiConnPthSl2app != null)
						iHistory_YPsnDatiECommerce.put(iIdAzienda,iYPsnDatiConnPthSl2app);
				}
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}

		return iYPsnDatiConnPthSl2app;
	}

	@SuppressWarnings("unchecked")
	public int saveOwnedObjects(int rc) throws SQLException{
		rc += super.saveOwnedObjects(rc);

		if(rc >= 0)
			iHistory_YPsnDatiECommerce.put(this.getIdAzienda(),this);

		return rc;
	}

	public ErrorMessage checkDelete() {
		return null;
	}

}