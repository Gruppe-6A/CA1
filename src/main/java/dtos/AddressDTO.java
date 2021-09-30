package dtos;

import entities.AddressEntity;

public class AddressDTO {
    private String Address;
    private CityInfoDTO cityInfoDTO;


    public AddressDTO(String address, CityInfoDTO cityInfoDTO) {
        Address = address;
        cityInfoDTO = cityInfoDTO;
    }
    public AddressDTO(AddressEntity address){
        this.Address = address.getAddress();
        this.cityInfoDTO = new CityInfoDTO(address.getCityInfo());
    }

    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
