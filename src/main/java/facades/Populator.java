/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CityInfoDTO;
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
        CityInfoFacade cityInfoFacade = CityInfoFacade.getCityInfoFacade(emf);
        //FacadeExample fe = FacadeExample.getFacadeExample(emf);
        /*
        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));
        fe.create(new RenameMeDTO(new RenameMe("First 2", "Last 2")));
        fe.create(new RenameMeDTO(new RenameMe("First 3", "Last 3")));
         */

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        CityInfoEntity sjøbbenhavnstrup = cityInfoFacade.getEntityByID("6200");
        AddressEntity address = new AddressEntity("sejvej 69420360", sjøbbenhavnstrup);
        PersonEntity lars = new PersonEntity("(:)","32","privat", "verycool@cum.cum", address);
        PersonEntity Henrik = new PersonEntity("kristine","69","?dafuq", "breve :)", address);
        lars.setAddress(address);
        HobbyEntity cykelmand = new HobbyEntity("!", "sport", "https://en.wikipedia.org/wiki/cycling", "cykler! :)");
        Henrik.setAddress(address);
        Henrik.addHobby(cykelmand);
        lars.addHobby(cykelmand);


        em.persist(lars);
        em.persist(Henrik);
        em.getTransaction().commit();
        em.close();
    }
    
    public static void main(String[] args) {
        populate();
    }
}
