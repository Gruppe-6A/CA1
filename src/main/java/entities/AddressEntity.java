package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "address")
@Entity
@NamedQuery(name = "address.deleteAllRows", query = "DELETE from AddressEntity a")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String Address;
    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private List<PersonEntity> personEntityList;
    @ManyToOne()
    private CityInfoEntity cityInfo;



    //Cityinfo cityinfo


    public List<PersonEntity> getPersonEntityList() {
        return personEntityList;
    }
    public void addPerson(PersonEntity person){
        personEntityList.add(person);
    }
    public void rmPersonEntityList(PersonEntity person){
        personEntityList.remove(person);
    }
    public AddressEntity() {
    }

    public AddressEntity(String address, CityInfoEntity ci) {
        Address = address;
        this.cityInfo = ci;
        personEntityList = new ArrayList<PersonEntity>();
    }

    public CityInfoEntity getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoEntity cityInfo) {
        this.cityInfo = cityInfo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}