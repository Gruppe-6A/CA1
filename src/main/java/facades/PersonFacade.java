package facades;

import dtos.*;
import entities.*;
import errorhandling.InvalidInputException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    private static CityInfoFacade ci1;
    private static HobbyFacade hobbyFacade;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            ci1 = CityInfoFacade.getCityInfoFacade(emf);
            hobbyFacade = HobbyFacade.getHobbyFacade(emf);
            instance = new PersonFacade();
        }
        return instance;
    }




    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO createPerson(PersonDTO p) throws InvalidInputException {
        EntityManager em = getEntityManager();
        CityInfoEntity ci = ci1.getEntityByID(p.getAddressDTO().getCityInfoDTO().getZipcode());
        AddressEntity ae = new AddressEntity(p.getAddressDTO().getAddress(), ci);
        PersonEntity pe = new PersonEntity(p.getFirstName(), p.getLastName(), p.getPhoneNumber(), p.getEmailAddress(), ae);


       // p.getHobbyDTO().forEach(hobby -> pe.addHobby(hobbyFacade.getHobbyByName(hobby.getName()));
        try {
            if (!p.getEmailAddress().contains("@")){
            throw new InvalidInputException("invalid email address");
        }
            em.getTransaction().begin();

            em.persist(pe);
            for (HobbyDTO dto : p.getHobbyDTO()) {

                pe.addHobby(hobbyFacade.getOrCreateHobby(dto));
                em.merge(pe);
            }
            em.merge(pe);
            em.getTransaction().commit();
        }

            catch (InvalidInputException l){
            throw new WebApplicationException(l.getMessage(), 404);

        } finally {
            em.close();
        }
        return new PersonDTO(pe);
    }
    public List<PersonDTO> getAllByHobby(String name){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery query = em.createQuery("Select p from HobbyEntity h JOIN h.pList p Where h.name = :name", PersonEntity.class);
            query.setParameter("name", name);
            List<PersonEntity> pe = query.getResultList();
            return PersonDTO.getPersonDTO(pe);
        } finally {
            em.close();
        }
    }
    public PersonDTO getByPhone(String phoneNumber){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery query = em.createQuery("Select p from PersonEntity p where p.phoneNumber = :phonenumber", PersonEntity.class);
            query.setParameter("phonenumber", phoneNumber);
            PersonEntity pe = (PersonEntity) query.getSingleResult();
            return new PersonDTO(pe);
        }
        finally {
            em.close();
        }
    }

    public List<PersonDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery query = em.createQuery("Select p from PersonEntity p", PersonEntity.class);
            List<PersonEntity> pe = query.getResultList();
            return PersonDTO.getPersonDTO(pe);
        } finally{
            em.close();
        }
    }
    public PersonDTO editPerson(PersonDTO p){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity person = em.find(PersonEntity.class, p.getID());
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setEmailAddress(p.getEmailAddress());
            person.setPhoneNumber(p.getPhoneNumber());
            /*person.getAddress().getCityInfo().setZipcode(p.getAddressDTO().getCityInfoDTO().getZipcode());
            person.getAddress().setAddress(p.getAddressDTO().getAddress());
            person.getAddress().getCityInfo().setCity(p.getAddressDTO().getCityInfoDTO().getCity());

             */
            CityInfoEntity ci = ci1.getEntityByID(p.getAddressDTO().getCityInfoDTO().getZipcode());
            person.setAddress(new AddressEntity(p.getAddressDTO().getAddress(), ci));
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally{
            em.close();
        }
    }
    public void deletePerson(int id){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery query = em.createQuery("DELETE from PersonEntity p where p.id = :id", PersonEntity.class);
            query.setParameter("id", id);
            query.executeUpdate();
            em.getTransaction().commit();
        } finally{
            em.close();
        }

    }

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);

    }

}
