package facades;

import dtos.RenameMeDTO;
import entities.HobbyEntity;
import entities.RenameMe;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class HobbyFacade {

    private static HobbyFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private HobbyFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static HobbyFacade getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long countByHobby(String hobby){
        EntityManager em = getEntityManager();
        try{
            TypedQuery PersonCount = em.createQuery("SELECT COUNT(p) from HobbyEntity h JOIN h.pList p where h.name = :name ", HobbyEntity.class);
            PersonCount.setParameter("name", hobby);
            return (long)PersonCount.getSingleResult();
        }
        finally {
            em.close();
        }
    }

    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        HobbyFacade fe = getHobbyFacade(emf);

    }

}
