package com.example.springcourse.dto;

import java.util.List;

public class UserDto {
    private String firstName;
    private String lastName;
    private String mobile;
    private String gender;
    private String address;
  //  private List<AddressDto> address;

    public UserDto(String firstName, String lastName, String mobile, String gender, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address;}

///   public List<AddressDto> getAddress() {
//        return address;


//    public void setAddress(List<AddressDto> address) {
//        this.address = address;
//    }
//}

}
