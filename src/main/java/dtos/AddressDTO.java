package dtos;

public class AddressDTO {
    private String Address;

    public AddressDTO(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
