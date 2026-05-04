package com.toan;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class MyResourceTest {

  @Test
  @TestSecurity(user = "myUser", roles = {"admin", "user"})
  void testEndpoint() {
    given()
      .when().get("api/vt")
      .then()
      .statusCode(200);
  }
}