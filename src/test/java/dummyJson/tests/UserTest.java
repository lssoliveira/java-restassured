package dummyJson.tests;

import dummyJson.domain.ApiBase;
import dummyJson.domain.ApiService;
import dummyJson.domain.set.Auth;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;

import static org.hamcrest.CoreMatchers.is;

@TestMethodOrder(OrderAnnotation.class)
public class UserTest extends ApiBase {

    @Test
    @Tag("getTest")
    @DisplayName("Validar o resultado de sucesso da api")
    public void getTest() {
        // Act
        Response response = ApiService.ApiGetTest();

        // Assert
        response.then().
            statusCode(HttpStatus.SC_OK).
            body(
                "status", is("ok"),
                "method", is("GET")
            );
    }

    @Test
    @Tag("getUsers")
    @DisplayName("Validar os usuarios retornados na busca de Usuarios")
    public void getUsers() {
        // Act
        Response response = ApiService.ApiGetUsers();

        // Assert
        response.then().
            statusCode(HttpStatus.SC_OK).
            assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getUsers_schema.json")).
            body(
                "users.size()", is(30)
            );
    }

    @Test
    @Tag("postAuthLogin")
    @Tag("Success")
    @DisplayName("Realizar uma autenticacao com usuario e senha")
    public void postAuthLogin() {
        // Arrange
        Auth auth = new Auth("kminchelle", "0lelplR");

        // Act
        Response response = ApiService.ApiPostAuthLogin(auth);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_OK).
            assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/postAuth.json")).
            body(
                "username", is(auth.username)
            );
    }

    @Test
    @Tag("postAuthLoginInvalid")
    @Tag("Invalid")
    @DisplayName("Realizar uma autenticacao com dados invalidos")
    public void postAuthLoginInvalid() {
        // Arrange
        Auth auth = new Auth("InvalidUser", "X1Y2Z3");

        // Act
        Response response = ApiService.ApiPostAuthLogin(auth);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body(
                "message", is("Invalid credentials")
            );
    }
}
