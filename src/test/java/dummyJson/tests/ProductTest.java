package dummyJson.tests;

import dummyJson.domain.ApiBase;
import dummyJson.domain.ApiService;
import dummyJson.domain.set.Auth;
import dummyJson.domain.set.Product;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Random;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@TestMethodOrder(OrderAnnotation.class)
public class ProductTest extends ApiBase {

    @Test
    @Tag("getAuthProducts")
    @DisplayName("Buscar produtos apos estar autenticado")
    public void getAuthProducts() {
        // Arrange
        Auth auth = new Auth("emilys", "emilyspass");
        Response responseAuth = ApiService.ApiPostAuthLogin(auth);
        String token = responseAuth.path("accessToken");

        // Act
        Response response = ApiService.ApiGetAuthProducts(token);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_OK).
            assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getProductsAuth_schema.json")).
            body(
                "products.size()", is(30)
            );
    }

    @Test
    @Tag("getAuthProductsNExistentToken")
    @DisplayName("Buscar produtos com autenticacao nao existente")
    public void getAuthProductsNExistentToken() {
        // Arrange
        String InvalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW1lIjoia21pbmNoZWxs" +
            "ZSIsImVtYWlsIjoia21pbmNoZWxsZUBxcS5jb20iLCJmaXJzdE5hbWUiOiJKZWFubmUiLCJsYXN0TmFtZSI6IkhhbHZ" +
            "vcnNvbiIsImdlbmRlciI6ImZlbWFsZSIsImltYWdlIjoiaHR0cHM6Ly9yb2JvaGFzaC5vcmcvYXV0cXVpYXV0LnBuZy" +
            "IsImlhdCI6MTY5NzMxMDk1NSwiZXhwIjoxNjk3MzE0NTU1fQ.7zXA3hoyBj9MJyTWm7zcZ-X-EIQgVXlu-8rYQhgts_5";

        // Act
        Response response = ApiService.ApiGetAuthProducts(InvalidToken);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
            body(
                "message", is("invalid signature")
            );
    }

    @Test
    @Tag("getAuthProductsInvalidToken")
    @DisplayName("Buscar produtos com autenticacao invalida")
    public void getAuthProductsInvalidToken() {
        // Arrange
        String InvalidToken = "TokenXPTO123";

        // Act
        Response response = ApiService.ApiGetAuthProducts(InvalidToken);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_UNAUTHORIZED).
            body(
                "message", is("Invalid/Expired Token!")
            );
    }

    @Test
    @Tag("getAuthProductsExpiredToken")
    @DisplayName("Buscar produtos com autenticacao expirada")
    public void getAuthProductsExpiredToken() {
        // Arrange
        String InvalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW1lIjoia21pbmNoZWxs" +
            "ZSIsImVtYWlsIjoia21pbmNoZWxsZUBxcS5jb20iLCJmaXJzdE5hbWUiOiJKZWFubmUiLCJsYXN0TmFtZSI6IkhhbHZ" +
            "vcnNvbiIsImdlbmRlciI6ImZlbWFsZSIsImltYWdlIjoiaHR0cHM6Ly9yb2JvaGFzaC5vcmcvYXV0cXVpYXV0LnBuZy" +
            "IsImlhdCI6MTY5NzMxMDk1NSwiZXhwIjoxNjk3MzE0NTU1fQ.7zXA3hoyBj9MJyTWm7zcZ-X-EIQgVXlu-8rYQYs7m_s";

        // Act
        Response response = ApiService.ApiGetAuthProducts(InvalidToken);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).
            body(
                "message", is("invalid signature")
            );
    }

    @Test
    @Tag("postAddProducts")
    @DisplayName("Postagem um produto na lista de produtos")
    public void postAddProducts() {
        // Arrange
        Product product = new Product("Perfume Oil", "Mega Discount, Impression of A...", 13, 8.4, 4.26F, 65,
            "Impression of Acqua Di Gio", "fragrances", "https://i.dummyjson.com/data/products/11/thumnail.jpg");

        // Act
        Response response = ApiService.ApiPostAddProduct(product);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_CREATED).
            body(
                "id", instanceOf(Number.class),
                "title", is (product.title),
                "price", is (product.price),
                "stock", is (product.stock),
                "rating", is (product.rating),
                "thumbnail", is (product.thumbnail),
                "description", is (product.description),
                "brand", is (product.brand),
                "category", is (product.category)
            );
    }

    @Test
    @Tag("getProducts")
    @DisplayName("Realizar busca de produtos")
    public void getProducts() {
        // Act
        Response response = ApiService.ApiGetProducts();

        // Assert
        response.then().
            statusCode(HttpStatus.SC_OK).
            assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getProduct_schema.json")).
            body(
                "products.size()", is(30)
            );
    }

    @Test
    @Tag("getProductId")
    @DisplayName("Realizar busca de um produto expecifico")
    public void getProductId() {
        // Arrange
        Random random = new Random();
        int productId = random.nextInt(100) + 1;

        // Act
        Response response = ApiService.ApiGetProductsId(productId);

        // Assert
        response.then().
        statusCode(HttpStatus.SC_OK).
        assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/getProductId_schema.json")).
            body(
                "id", is (productId)
            );
    }

    @Test
    @Tag("getProductIdInvalid")
    @DisplayName("Realizar busca de um produto inexistente")
    public void getProductIdInvalid() {
        // Arrange
        int productId = 500;

        // Act
        Response response = ApiService.ApiGetProductsId(productId);

        // Assert
        response.then().
            statusCode(HttpStatus.SC_NOT_FOUND).
            body(
                "message", is ("Product with id '" + productId + "' not found")
            );
    }
}
