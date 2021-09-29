package dtos;

public class CityInfoDTO {
    private int zipcode;
    private String city;

    public CityInfoDTO(int zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
