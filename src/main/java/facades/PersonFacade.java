package facades;

import dtos.*;
import entities.AddressEntity;
import entities.CityInfoEntity;
import entities.PersonEntity;
import entities.RenameMe;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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
            instance = new PersonFacade();
        }
        return instance;
    }




    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO createPerson(PersonDTO p){
        EntityManager em = getEntityManager();
        CityInfoEntity ci = ci1.getByID(p.getAddressDTO().getCityInfoDTO().getZipcode());
        AddressEntity ae = new AddressEntity(p.getAddressDTO().getAddress(), ci);
        PersonEntity pe = new PersonEntity(p.getFirstName(), p.getLastName(), p.getPhoneNumber(), p.getEmailAddress(), ae);

        try {
            em.getTransaction().begin();
            em.persist(pe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(pe);
    }
    public List<PersonDTO> getByHobby(String name){
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



    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
    }
    
    public List<RenameMeDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<RenameMe> query = em.createQuery("SELECT r FROM RenameMe r", RenameMe.class);
        List<RenameMe> rms = query.getResultList();
        return RenameMeDTO.getDtos(rms);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
        CityInfoFacade ci = CityInfoFacade.getCityInfoFacade(emf);
        fe.getByHobby("cykling");


        CityInfoDTO cityInfoDTO = new CityInfoDTO(ci.getByID("900"));
        AddressDTO addressDTO = new AddressDTO("psykopatvej 49", cityInfoDTO);
        fe.createPerson(new PersonDTO("betinna", "bætinna", "1-800-beate", "cphbusinessdinmor", new ArrayList<HobbyDTO>(), addressDTO));
        System.out.println(fe.getByPhone("?dafuq").getHobbyDTO().get(0).getName());
    }

}
