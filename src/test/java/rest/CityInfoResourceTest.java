package rest;

import entities.CityInfoEntity;
import entities.RenameMe;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class CityInfoResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public  void setUp() {
        EntityManager em = emf.createEntityManager();
        try{
        em.getTransaction().begin();
            em.createNamedQuery("person.deleteAllRows").executeUpdate();
            em.createNamedQuery("address.deleteAllRows").executeUpdate();
            em.createNamedQuery("cityinfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("HOBBY.deleteAllRows").executeUpdate();
        CityInfoEntity camillasCityInfo = new CityInfoEntity("800", "Larsby");
        CityInfoEntity cbvci = new CityInfoEntity("820", "camillaby");
        CityInfoEntity ckb = new CityInfoEntity("1337", "gamertown");
        CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
        em.persist(camillasCityInfo);
        em.persist(cbvci);
        em.persist(ckb);
        em.persist(NicolaiBy);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    @Test
    public void getAll() {
        given()
                .contentType("application/json")
                .get("/cityinfo/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("city", hasItems("Larsby", "camillaby", "gamertown", "ROKKENTOWN"));
    }
    @Test
    public void getZip() {
        given()
                .contentType("application/json")
                .pathParam("zipcode","42069").when()
                .get("/cityinfo/zip/{zipcode}").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("city", equalTo("ROKKENTOWN"));
    }

}
