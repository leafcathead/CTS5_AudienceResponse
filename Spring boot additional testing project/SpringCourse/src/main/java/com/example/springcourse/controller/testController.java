package com.example.springcourse.controller;

import com.example.springcourse.dto.AddressDto;
import com.example.springcourse.dto.ContactsDto;
import com.example.springcourse.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.springcourse.dto.RegistrationDto;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class testController {

//    @PostMapping(path = "/reg")
//    public String testPost(@RequestBody RegistrationDto form)  {
//    return  "Hi Mr. " + form.getUsername() ;
//    }

//    @PostMapping(path = "/reg")
//    public String testPost(@RequestBody RegistrationDto form) throws Exception {
//        throw new Exception("Testing Error");
//    }

    //    @GetMapping(value = "/postedName")
//    public String welcomeMsg(@PathVariable String postedName){
//
//        return "welcome " + postedName + " to our Spring App";
//    }

//    @PostMapping(path = "/reg")
//    public RegistrationDto testPost(@RequestBody RegistrationDto form)  {
//        return  form;
//    }
@CrossOrigin
//@GetMapping("getJsonObject")
//    public HashMap<String, Object> testJson(){
//        //HashMap<key , value> is returned with nested object "subObj" and ArrayList "contacts"
//        HashMap<String,Object> obj = new HashMap<>();
//    List<ContactsDto> contacts = new ArrayList<>();
//
//    contacts.add(new ContactsDto("Amer","05646468468"));
//    contacts.add(new ContactsDto("Ahmed","069876448"));
//
//    HashMap<String,Object> subObj = new HashMap<>();
//        subObj.put("workAddress", "my work address");
//        subObj.put("homeAddress", "my home address");
//
//        obj.put("firstName","Amer");
//        obj.put("lastName","Aljamous");
//        obj.put("id", "6545234853");
//        obj.put("address", subObj);
//        obj.put("contacts",contacts);
//
//
//    return obj;
//}
//@GetMapping("/contacts")
//    public List testArrayList(){
//    List<ContactsDto> contacts = new ArrayList<>();
//
//    contacts.add(new ContactsDto("Amer","05646468468"));
//    contacts.add(new ContactsDto("Ahmed","069876448"));
//
//        return contacts;
//}
//@CrossOrigin
//@GetMapping(path = "/users")
@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUsers(){
        List<UserDto> users = new ArrayList<>();

        //address list
      //  List<AddressDto> Address = new ArrayList<>();
     //   Address.add(new AddressDto("DE", "Ulm","Heidenheimer Str"));
    //    Address.add(new AddressDto("DE","Waiblingen","Saint Cruse Str"));

        users.add(new UserDto("Amer", "jamous","0175564","M", "Ulm"));
        users.add(new UserDto("Mucella", "jamous","0175489564","F","Berlin"));
        users.add(new UserDto("Kub", "CETIN","085454540","F","Stuttgart"));
    users.add(new UserDto("aMMAR", "DAR","085454540","M","uLM"));



    return users;

    }




}
