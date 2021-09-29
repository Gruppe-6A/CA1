package dtos;

import entities.PersonEntity;

public class PersonDTO {private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;

    public PersonDTO(String firstName, String lastName, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public PersonDTO(PersonEntity pe){
        this.firstName = pe.getFirstName();
        this.lastName = pe.getLastName();
        this.phoneNumber = pe.getPhoneNumber();
        this.emailAddress = pe.getEmailAddress();

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
}
