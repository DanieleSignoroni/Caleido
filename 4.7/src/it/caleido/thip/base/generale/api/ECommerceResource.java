package it.caleido.thip.base.generale.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.json.JSONObject;

import com.thera.thermfw.rs.BaseResource;

/**
 *
 * <p></p>
 *
 * <p>
 * Company: Softre Solutions<br>
 * Author: Daniele Signoroni<br>
 * Date: 28/08/2026
 * </p>
 */

/*
 * Revisions:
 * Number   Date        Owner    Description
 * 72630    28/08/2026  DSSOF3   Prima stesura
 */

@Path("/caleido/ecommerce")
public class ECommerceResource extends BaseResource {

	private static ECommerceService service = ECommerceService.getECommerceService();

	@POST
	@Path("/configurazione/ricevi")
	public Response riceviConfigurazione(String body) {
		JSONObject response = service.riceviConfigurazione(body);
		return buildResponse((StatusType) response.get("status"),response.get("response"));
	}
}
