package com.carina.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "carpark_availability_friday")
public class CarparkAvailabilityFridayModel extends CarparkAvailabilityModel {

    @Override
    public String toString() {
        return "carpark_availability_friday";
    }

}