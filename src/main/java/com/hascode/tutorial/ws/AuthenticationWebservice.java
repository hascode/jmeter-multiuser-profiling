package com.hascode.tutorial.ws;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.hascode.tutorial.entity.User;

@Stateless
@Path("/session")
@RolesAllowed(value = { "administrators" })
public class AuthenticationWebservice {
	@PersistenceContext
	private EntityManager em;

	@Path("/logout")
	@POST
	public Response logout(@Context final SecurityContext ctx, @Context final HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUserInformation(@Context final SecurityContext ctx) {
		Principal principal = ctx.getUserPrincipal();

		User user = em.find(User.class, principal.getName());
		return Response.ok(user).build();
	}
}
