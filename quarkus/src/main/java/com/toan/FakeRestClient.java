package com.toan;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FakeRestClient {

  @Inject
  AuthenticatedUserContext authenticatedUserContext;

  public String method1() {
    String name = authenticatedUserContext.myUser().name();
    Log.info("method1: name: " + name);
    return name;
  }

  public String method2() {
    String name = authenticatedUserContext.myUser().name();
    Log.info("method1: name: " + name);
    return name;
  }
}
