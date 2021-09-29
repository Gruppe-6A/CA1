package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "city_info")
@Entity
public class CityInfoEntity {
    @Id
    @Column(name = "zipcode", nullable = false)
    private int zipcode;
    private String city;
    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.PERSIST)
    private List<AddressEntity> addressEntityList;


    public CityInfoEntity() {
    }

    public List<AddressEntity> getAddresses(){
        return addressEntityList;
    }
    public void addAddress(AddressEntity address){
        addressEntityList.add(address);
    }
    public void deleteAddress(AddressEntity address){
        addressEntityList.remove(address);
    }

    public CityInfoEntity(int zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
        addressEntityList = new ArrayList<>();
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