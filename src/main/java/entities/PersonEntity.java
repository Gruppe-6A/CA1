package entities;

import facades.CityInfoFacade;
import org.eclipse.persistence.jpa.config.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "person")
@Entity
@NamedQuery(name = "person.deleteAllRows", query = "DELETE from PersonEntity p")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private AddressEntity address;
    @ManyToMany(mappedBy = "pList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<HobbyEntity> hobbyList;

    public PersonEntity() {
    }

    public List <HobbyEntity> getHobby(){
        return hobbyList;
    }

    public void addHobby(HobbyEntity he){
            hobbyList.add(he);

        if(!he.getpList().contains(this)){
            he.getpList().add(this);
        }

    }
    public void removeHobby(HobbyEntity he){
        hobbyList.remove(he);
    }

    public PersonEntity(String firstName, String lastName, String phoneNumber, String emailAddress, List<HobbyEntity> hList, AddressEntity address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.hobbyList = hList;
    }

    public PersonEntity(String firstName, String lastName, String phoneNumber, String emailAddress, AddressEntity address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        hobbyList = new ArrayList<>();
    }
    public AddressEntity getAddress() {
        return address;
    }


    public void setAddress(AddressEntity address) {
        this.address = address;
        if(!address.getPersonEntityList().contains(this)){
            address.addPerson(this);
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
