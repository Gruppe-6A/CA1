package rest;

import dtos.PersonDTO;
import entities.*;
import facades.PersonFacade;
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
import java.lang.reflect.Array;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest {

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
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("person.deleteAllRows").executeUpdate();
            em.createNamedQuery("address.deleteAllRows").executeUpdate();
            em.createNamedQuery("cityinfo.deleteAllRows").executeUpdate();
            em.createNamedQuery("HOBBY.deleteAllRows").executeUpdate();
            HobbyEntity beskæftigelse = new HobbyEntity("beskæftigende", "idk", "udendørs", "indendørs");
            CityInfoEntity camillasCityInfo = new CityInfoEntity("800", "Larsby");
            CityInfoEntity cbvci = new CityInfoEntity("820", "camillaby");
            CityInfoEntity ckb = new CityInfoEntity("1337", "gamertown");
            CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
            AddressEntity camillasAdresse = new AddressEntity("Skrrtvej 8199999", camillasCityInfo);

            PersonEntity Camilla = new PersonEntity("krølle bølle", "ingen kvinder", "123", "thomas", camillasAdresse);
            PersonEntity ole = new PersonEntity("ole", "ole", "1-800-123", "ole@ole.dk", camillasAdresse);

            Camilla.addHobby(beskæftigelse);
            ole.addHobby(beskæftigelse);

            em.persist(camillasCityInfo);
            em.persist(cbvci);
            em.persist(ckb);
            em.persist(NicolaiBy);
            em.persist(camillasAdresse);
            em.persist(Camilla);
            em.persist(ole);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/xxx").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testGetAll() throws Exception {
        String[] sk  = new String[2];
        sk[0] = "ole";
        sk[1] = "krølle bølle";
        given()
                .contentType("application/json")
                .get("/person/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItems("ole", "krølle bølle"));
    }

    @Test
    public void testGetByPhone() throws Exception {
        given()
                .contentType("application/json")
                .pathParam("phone", "1-800-123").when()
                .get("/person/phone/{phone}").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("lastName", equalTo("ole"));
    }
    @Test
    public void testGetAllByHobby() throws Exception {
        String[] sk  = new String[2];
        sk[0] = "ole";
        sk[1] = "krølle bølle";
        given()
                .contentType("application/json")
                .pathParam("hobby", "beskæftigende").when()
                .get("/person/hobby/{hobby}").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("firstName", hasItems("ole", "krølle bølle"));
    }
    @Test
    public void testCreatePerson() throws Exception{
        CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
        AddressEntity johnAdresse = new AddressEntity("rema", NicolaiBy);
        PersonEntity John = new PersonEntity("Køl", "hansen", "112", "email@", johnAdresse);
        John.setId(100);
        PersonDTO John1 = new PersonDTO(John);
        given().
                contentType("application/json").
                body(John1)
                .when()
                .request("post", "/person").then()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
    @Test
    public void testEditPerson() throws Exception{
        CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
        AddressEntity johnAdresse = new AddressEntity("rema 1000", NicolaiBy);
        PersonEntity John = new PersonEntity("Jake", "der er hund", "132", "email@", johnAdresse);
        John.setId(99);
        PersonDTO Jake = new PersonDTO(John);
        given().
                contentType("application/json").
                pathParam("id", 99).
                body(Jake)
                .when()
                .request("put", "/person/edit/{id}").then()
                .statusCode(404);
    }

    @Test public void testDeletePerson() throws Exception {given().contentType("application/json").pathParam("id", 99).when().request("delete", "/person/delete/{id}").then().statusCode(404);}


}
