package com.toan;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import java.security.Principal;

@Provider
@PreMatching
public class UserFilter implements ContainerRequestFilter {
  private final AuthenticatedUserContext context;

  @Inject
  public UserFilter(AuthenticatedUserContext context) {
    this.context = context;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {
    Principal userPrincipal = requestContext.getSecurityContext().getUserPrincipal();
    context.initMyUser(userPrincipal);
  }
}
