package com.toan;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;


@Provider
@PreMatching
public class UserFilter implements ContainerRequestFilter {
  private final SecurityIdentity securityIdentity;
  private final AuthenticatedUserContext context;

  @Inject
  public UserFilter(SecurityIdentity securityIdentity, AuthenticatedUserContext context) {
    this.securityIdentity = securityIdentity;
    this.context = context;
  }

  @Override
  public void filter(ContainerRequestContext requestContext)  {
    context.initMyUser(securityIdentity);
  }
}
