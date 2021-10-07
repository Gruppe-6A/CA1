package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import errorhandling.EntityNotFoundException;
import facades.CityInfoFacade;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("cityinfo")
public class CityInfoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final CityInfoFacade FACADE =  CityInfoFacade.getCityInfoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("zip/{zipcode}")
    @Produces("application/json")
    public String getByZip(@PathParam("zipcode") String zipcode)throws EntityNotFoundException {
        CityInfoDTO ci = FACADE.getDTOByID(zipcode);
        return GSON.toJson(ci);
    }

    @GET
    @Path("all")
    @Produces("application/json")
    public String getAllZip(){
        List<CityInfoDTO> ciL = FACADE.getAll();
        return GSON.toJson(ciL);
    }


}
