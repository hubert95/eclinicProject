/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.converters;

import database.VisitState;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Admin
 */
@Converter
public class VisitStateConverter implements AttributeConverter<VisitState, Integer>{

    @Override
    public Integer convertToDatabaseColumn(VisitState attribute) {
        switch (attribute) {
            case UNRESERVED:
                return 0;
            case UNPAYED:
                return 1;
            case PAYED:
                return 2;
            case CANCELEDBYCLINIC:
                return 3;
            case CANCELEDBYPATIENT:
                return 4;
            case FINISHED:
                return 5;
        }
        return null;
    }

    @Override
    public VisitState convertToEntityAttribute(Integer dbData) {
        switch (dbData) {
            case 0:
                return VisitState.UNRESERVED;
            case 1:
                return VisitState.UNPAYED;
            case 2:
                return VisitState.PAYED;
            case 3:
                return VisitState.CANCELEDBYCLINIC;
            case 4:
                return VisitState.CANCELEDBYPATIENT;
            case 5:
                return VisitState.FINISHED;
        }
        return null;
    }
    
}
