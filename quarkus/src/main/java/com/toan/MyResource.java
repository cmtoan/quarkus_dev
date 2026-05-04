package com.toan;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class MyResource {

  @Inject
  MyService myService;

  @GET
  @Path("/vt")
  @RolesAllowed("admin")
  public void executeParallel() {
    myService.executeAll();
  }
}
