package facades;

import dtos.*;
import entities.*;
import errorhandling.EntityNotFoundException;
import errorhandling.InvalidInputException;
import utils.EMF_Creator;

import javax.persistence.*;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



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



        try {
        Pattern pa = Pattern.compile("[a-zA-Z]");
        Matcher m = pa.matcher(p.getPhoneNumber());
            if (!p.getEmailAddress().contains("@")){
            throw new InvalidInputException("invalid email address");
        } else if(m.find()){
                throw new InvalidInputException("invalid phone number");
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
            throw new WebApplicationException(l.getMessage(), 400);

        } finally {
            em.close();
        }
        return new PersonDTO(pe);
    }
    public List<PersonDTO> getAllByHobby(String name) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery query = em.createQuery("Select p from HobbyEntity h JOIN h.pList p Where h.name = :name", PersonEntity.class);
            query.setParameter("name", name);
            List<PersonEntity> pe = query.getResultList();
            return PersonDTO.getPersonDTO(pe);
        }  finally{
            em.close();
        }
    }
    public PersonDTO getByPhone(String phoneNumber) {
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
    public PersonDTO editPerson(PersonDTO p) throws InvalidInputException, EntityNotFoundException {
        EntityManager em = getEntityManager();
        PersonEntity person = em.find(PersonEntity.class, p.getID());
        try {
            Pattern pa = Pattern.compile("[a-zA-Z]");
            Matcher m = pa.matcher(p.getPhoneNumber());
            if (!p.getEmailAddress().contains("@")){
                throw new InvalidInputException("invalid email address");
            }else if(m.find()){
                throw new InvalidInputException("invalid phone number");
            }
            if(person == null){
                throw new EntityNotFoundException("Could not find anyone with that id");
            }

            em.getTransaction().begin();

            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setEmailAddress(p.getEmailAddress());
            person.setPhoneNumber(p.getPhoneNumber());

            CityInfoEntity ci = ci1.getEntityByID(p.getAddressDTO().getCityInfoDTO().getZipcode());
            person.setAddress(new AddressEntity(p.getAddressDTO().getAddress(), ci));
            em.getTransaction().commit();
            return new PersonDTO(person);
        }

        catch (InvalidInputException l){
            throw new WebApplicationException(l.getMessage(), 400);

        } catch(EntityNotFoundException oo){
            throw new WebApplicationException(oo.getMessage(), 404);
        } finally{
            em.close();
        }
    }
    public PersonDTO deletePerson(int id)throws EntityNotFoundException{
        EntityManager em = getEntityManager();
        PersonEntity person = em.find(PersonEntity.class, id);
            try {
                if (person == null){
                throw new EntityNotFoundException("Could not find anyone with that id");
            }
                em.getTransaction().begin();
                em.remove(person);
                em.getTransaction().commit();

            } catch(EntityNotFoundException oo){
                throw new WebApplicationException(oo.getMessage(), 404);
            }
            finally{
                em.close();
            }
            return new PersonDTO (person);
        }
         /*catch(NoResultException nre){
        throw new WebApplicationException("Could not find anyone with that id", 404);
    }*/


    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);

    }

}
