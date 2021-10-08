package dtos;

import entities.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private int ID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private List<HobbyDTO> hobbyDTO;
    private AddressDTO addressDTO;

    public PersonDTO(String firstName, String lastName, String phoneNumber, String emailAddress, List<HobbyDTO> hobbyDTO, AddressDTO addressDTO) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.hobbyDTO = hobbyDTO;
        this.addressDTO = addressDTO;
    }

    public PersonDTO(PersonEntity pe){
        this.firstName = pe.getFirstName();
        this.lastName = pe.getLastName();
        this.phoneNumber = pe.getPhoneNumber();
        this.emailAddress = pe.getEmailAddress();
        this.hobbyDTO = HobbyDTO.getDtos(pe.getHobby());
        this.addressDTO = new AddressDTO(pe.getAddress());
        this.ID = pe.getId();
    }



    public static List<PersonDTO> getPersonDTO(List<PersonEntity> pe){
        List<PersonDTO> pDTO = new ArrayList();
        pe.forEach(pes->pDTO.add(new PersonDTO(pes)));
        return pDTO;
    }

    public List<HobbyDTO> getHobbyDTO() {
        return hobbyDTO;
    }

    public void setHobbyDTO(List<HobbyDTO> hobbyDTO) {
        this.hobbyDTO = hobbyDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
