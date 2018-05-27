/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

/**
 *
 * @author Hubert Januszek
 */
@Entity
@TableGenerator(name = "specialist")
public class Specialist extends User implements Serializable {

    @OneToMany(mappedBy = "specialist")
    private List<Specialization> specializations;
    
    @OneToMany
    private List<RangeOfAdmission> rangeOfAdmissions;

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<RangeOfAdmission> getRangeOfAdmissions() {
        return rangeOfAdmissions;
    }

    public void setRangeOfAdmissions(List<RangeOfAdmission> rangeOfAdmissions) {
        this.rangeOfAdmissions = rangeOfAdmissions;
    }


}
