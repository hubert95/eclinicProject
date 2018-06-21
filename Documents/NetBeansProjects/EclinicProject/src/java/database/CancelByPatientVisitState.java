/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javax.persistence.Entity;

/**
 *
 * @author Admin
 */
@Entity
public class CancelByPatientVisitState extends VisitState{

    @Override
    public void reserveTheVisit() {
        
    }

    @Override
    public void withdrawReservation() {
        
    }

    @Override
    public void payPerVisit() {
      
    }

    @Override
    public void cancelTheVisitByPatient() {
        
    }

    @Override
    public void cancelTheVisitByClinic() {
        
    }

    @Override
    public void finishTheVisit() {
        
    }
    
}
