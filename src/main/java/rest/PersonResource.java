package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import errorhandling.EntityNotFoundException;
import errorhandling.InvalidInputException;
import facades.FacadeExample;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("phone/{phone}")
    @GET
    @Produces("application/json")
    public String getByPhone(@PathParam("phone") String phone) {
        PersonDTO pdto = FACADE.getByPhone(phone);
        return GSON.toJson(pdto);
    }
    @Path("hobby/{hobby}")
    @GET
    @Produces("application/json")
    public String getByHobby(@PathParam("hobby") String hobby)  {
        List<PersonDTO> pdtoList = FACADE.getAllByHobby(hobby);
        return GSON.toJson(pdtoList);
    }
    @Path("all")
    @GET
    @Produces("application/json")
    public String getAll(){
        List<PersonDTO> pdtoList = FACADE.getAll();
        return GSON.toJson(pdtoList);
    }


    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String createPerson(String person) throws InvalidInputException{
        PersonDTO pdto = GSON.fromJson(person, PersonDTO.class);
         PersonDTO newpdto = FACADE.createPerson(pdto);





        return GSON.toJson(newpdto);
    }
    @Path("edit/{id}")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public String editPerson(@PathParam("id")int id, String person)throws InvalidInputException, EntityNotFoundException{
      PersonDTO dto =  GSON.fromJson(person, PersonDTO.class);
        dto.setID(id);
        return GSON.toJson(FACADE.editPerson(dto));
    }
    @Path("delete/{id}")
    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public String deletePerson(@PathParam("id") int id, String person) throws EntityNotFoundException {

       PersonDTO pDTO = FACADE.deletePerson(id);
       return GSON.toJson(pDTO);
    }

    }

