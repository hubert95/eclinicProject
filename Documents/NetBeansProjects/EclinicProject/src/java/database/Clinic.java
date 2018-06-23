/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "clinic")
public class Clinic implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name = "Przychodnia NFZ Kielce";
    @OneToOne
    private Contact contact;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Address address;
    
    @PostConstruct
    public void init(){
        contact.setEmail("przychodnia-kielce@gmail.com");
        contact.setPhoneNumber("500-111-222");
        address.setLocality("Kielce");
        address.setStreet("Grunwaldzka");
        address.setPostalCode("00-321");
        address.setHouseNumber("230C");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}
