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
public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;


    //Private Constructor to ensure Singleton
    private CityInfoFacade() {}
    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CityInfoEntity getEntityByID(String zipcode){
        EntityManager em = emf.createEntityManager();
        return em.find(CityInfoEntity.class, zipcode);
    }

    public CityInfoDTO getDTOByID(String zipcode){
        EntityManager em = emf.createEntityManager();
        return new CityInfoDTO(em.find(CityInfoEntity.class, zipcode));
    }

    public List<CityInfoDTO> getAll(){
        EntityManager em = getEntityManager();
        try{
            TypedQuery query = em.createQuery("select z from CityInfoEntity z", CityInfoEntity.class);
            List<CityInfoEntity> ci = query.getResultList();
            return CityInfoDTO.getCityInfoDTO(ci);
        }
        finally {
            em.close();
        }
    }

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        CityInfoFacade fe = getCityInfoFacade(emf);
        System.out.println(fe.getEntityByID("800").getCity());
    //    fe.getAll().forEach(dto->System.out.println(dto.getZipcode()));
    }

}
