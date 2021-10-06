package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "cityinfo")
@Entity
@NamedQuery(name = "cityinfo.deleteAllRows", query = "DELETE from CityInfoEntity c")
public class CityInfoEntity {
    @Id
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    private String city;
    @OneToMany(mappedBy = "cityInfo")
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

    public CityInfoEntity(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
        addressEntityList = new ArrayList<>();
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