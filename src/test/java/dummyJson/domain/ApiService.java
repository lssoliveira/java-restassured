package dummyJson.domain;

import dummyJson.domain.set.Product;
import dummyJson.domain.set.Auth;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ApiService {
    private static final String TEST = "/test";
    private static final String SEARCH_USERS = "/users";
    private static final String AUTH_LOGIN = "/auth/login";
    private static final String SEARCH_PRODUCTS_BY_AUTH = "/auth/products";
    private static final String ADD_PRODUCTS = "/products/add";
    private static final String SEARCH_PRODUCTS = "/products";

    public static Response ApiGetTest() {
        return
            when().
                 get(TEST);
    }

    public static Response ApiGetUsers() {
        return
            when().
                get(SEARCH_USERS);
    }

    public static Response ApiPostAuthLogin(Auth auth) {
        return
            given().
                body(auth).
            when().
                post(AUTH_LOGIN);
    }

    public static Response ApiGetAuthProducts(String tokenAuth) {
        return
            given().
                auth().oauth2(tokenAuth).
            when().
                get(SEARCH_PRODUCTS_BY_AUTH);
    }

    public static Response ApiPostAddProduct(Product product) {
        return
            given().
                body(product).
            when().
                post(ADD_PRODUCTS);
    }

    public static Response ApiGetProducts() {
        return
            when().
                get(SEARCH_PRODUCTS);
    }

    public static Response ApiGetProductsId(Integer productId) {
        return
            when().
                get(SEARCH_PRODUCTS + "/" + productId);
    }
}
