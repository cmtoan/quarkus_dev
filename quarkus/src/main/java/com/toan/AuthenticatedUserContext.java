package com.toan;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class AuthenticatedUserContext {
  private MyUser myUser;

  public MyUser myUser() {
    return myUser;
  }

  public void initMyUser(SecurityIdentity securityIdentity) {
    this.myUser = new MyUser(securityIdentity.getPrincipal().getName());
  }
}
