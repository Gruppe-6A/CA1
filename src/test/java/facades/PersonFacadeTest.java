package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static CityInfoFacade facade1;
    private static HobbyFacade facade2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = PersonFacade.getPersonFacade(emf);
       facade1 = CityInfoFacade.getCityInfoFacade(emf);
       facade2 = HobbyFacade.getHobbyFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            HobbyEntity beskæftigelse = new HobbyEntity("beskæftigende", "idk", "udendørs", "indendørs");
            CityInfoEntity camillasCityInfo = new CityInfoEntity("800", "Larsby");
            CityInfoEntity cbvci = new CityInfoEntity("820", "camillaby");
            CityInfoEntity ckb = new CityInfoEntity("1337", "gamertown");
            CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
            AddressEntity camillasAdresse = new AddressEntity("Skrrtvej 8199999", camillasCityInfo);

            PersonEntity Camilla = new PersonEntity("krølle bølle", "ingen kvinder", "hvad skal den ellers have?", "thomas", camillasAdresse);
            PersonEntity ole = new PersonEntity("ole", "ole", "1-800-ole", "ole@ole.dk", camillasAdresse);
            ole.addHobby(beskæftigelse);
            Camilla.addHobby(beskæftigelse);

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

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() throws Exception {
        assertEquals("krølle bølle", facade.getByPhone("hvad skal den ellers have?").getFirstName());
    }
    @Test
    public void testCreatePerson() throws Exception{
        CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
        AddressEntity johnAdresse = new AddressEntity("rema", NicolaiBy);
        PersonEntity John = new PersonEntity("Køl", "hansen", "112", "email", johnAdresse);
        PersonDTO John1 = new PersonDTO(John);
        assertEquals("hansen", facade.createPerson(John1).getLastName());
    }
    @Test
    public void testGetHobbyCount() throws Exception{
        assertEquals(2, facade2.countByHobby("beskæftigende"));
    }
    @Test
    public void testCityInfo()throws Exception{
        assertEquals("camillaby", facade1.getByID("820").getCity());
    }


}
