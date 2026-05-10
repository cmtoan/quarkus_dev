package com.toan;

import jakarta.enterprise.context.RequestScoped;

import java.security.Principal;

@RequestScoped
public class AuthenticatedUserContext {
  private MyUser myUser;

  public MyUser myUser() {
    return myUser;
  }

  public void initMyUser(Principal principal) {
    this.myUser = new MyUser(principal.getName());
  }
}
