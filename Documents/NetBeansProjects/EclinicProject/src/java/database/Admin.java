/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */

@Entity
@TableGenerator(name = "admin")
public class Admin extends User implements Serializable{
    
}
