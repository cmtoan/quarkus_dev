package com.toan;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.concurrent.ExecutorService;

@Path("/api")
public class MainResource {

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  FakeRestClient fakeRestClient;

  @Inject
  MyService myService;

  @VirtualThreads
  ExecutorService executorService;

  @GET
  @Path("/vt")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    myService.executeAll();
    return "Hello from Quarkus REST";
  }
}
