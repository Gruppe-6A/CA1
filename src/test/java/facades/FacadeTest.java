package facades;

import dtos.PersonDTO;
import entities.*;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class FacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static CityInfoFacade facade1;
    private static HobbyFacade facade2;

    public FacadeTest() {
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

    @AfterEach
    public void tearDown() {


    }

    // TODO: Delete or change this method 
    @Test
    public void testAFacadeMethod() throws Exception {
        assertEquals("krølle bølle", facade.getByPhone("123").getFirstName());
    }
    /*@Test
    public void testDelete() throws Exception{
            facade.deletePerson(16);
            assertEquals(1, facade.getAll().size());

    }

     */
    @Test
    public void testCreatePerson() throws Exception{
        CityInfoEntity NicolaiBy = new CityInfoEntity("42069", "ROKKENTOWN");
        AddressEntity johnAdresse = new AddressEntity("rema", NicolaiBy);
        PersonEntity John = new PersonEntity("Køl", "hansen", "112", "email@", johnAdresse);
        John.setId(14);
        PersonDTO John1 = new PersonDTO(John);

        assertEquals("hansen", facade.createPerson(John1).getLastName());
    }
    @Test
    public void testGetByHobby() throws Exception{
        assertEquals(2, facade.getAllByHobby("beskæftigende").size());
    }
    @Test
    public void testGetHobbyCount() throws Exception{
        assertEquals(2, facade2.countByHobby("beskæftigende"));
    }
    @Test
    public void testCityInfo()throws Exception{
        assertEquals("camillaby", facade1.getEntityByID("820").getCity());
    }
    @Test
    public void getAllTest()throws Exception{
        assertEquals(2, facade.getAll().size());
    }


    @Test
    public void testHobbyFinder() throws Exception{
        assertEquals("idk", facade2.getHobbyByName("beskæftigende").getcategory());
    }


}
