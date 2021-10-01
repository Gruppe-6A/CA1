package dtos;

import entities.CityInfoEntity;
import entities.HobbyEntity;
import entities.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class CityInfoDTO {
    private String zipcode;
    private String city;

    public CityInfoDTO(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public CityInfoDTO(CityInfoEntity ce){
        this.zipcode = ce.getZipcode();
        this.city = ce.getCity();
    }

    public static List<CityInfoDTO> getCityInfoDTO(List<CityInfoEntity> ci){
        List<CityInfoDTO> ciDTO = new ArrayList();
        ci.forEach(cil->ciDTO.add(new CityInfoDTO(cil)));
        return ciDTO;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
