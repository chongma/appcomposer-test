package com.test.appcomposer.services.cors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.test.appcomposer.controllers.TestController;

@Path("/test2")
public class Test2Service {

	@Inject
	private HttpServletRequest httpServletRequest;

	@Inject
	private TestController testController;

	@Path("/hello")
	@GET
	public Response selectHello() {
		if (!httpServletRequest.isUserInRole("general")) {
			return Response.status(401).build();
		}
		String string = testController.selectHello();
		return Response.ok(string).build();
	}

}
