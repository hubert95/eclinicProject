/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "user")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "User.returnOwner", query = "SELECT u FROM User u WHERE u.account = :account")
})
public abstract class User extends Person implements Serializable {
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Account account;

    public User() {
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    
}
