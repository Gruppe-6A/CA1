/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.RenameMeDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PersonEntity lars = new PersonEntity("(:)","32","privat", "verycool@cum.cum");
        CityInfoEntity sjøbbenhavnstrup = new CityInfoEntity(1337, "leetcity");
        lars.setAddress(new AddressEntity("sejvej 69420360", sjøbbenhavnstrup));
        HobbyEntity cykelmand = new HobbyEntity("cykling", "sport", "https://en.wikipedia.org/wiki/cycling", "cykler! :)");

        lars.addHobby(cykelmand);


        em.persist(lars);
        em.getTransaction().commit();
        em.close();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
