package com.carina.backend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "carparkAvailability")
public class CarparkAvailability {
    @Id
    private ObjectId _id;
    private String CarParkID;
    private String Area;
    private String Development;
    private String Location;
    private String AvailableLots;
    private String LotType;
    private String Agency;

    // Constructors
    public CarparkAvailability() {}

    public CarparkAvailability(ObjectId _id, String CarParkID, String Area, String Development, String Location, String AvailableLots, String LotType, String Agency) {
        this.CarParkID = CarParkID;
        this.Area = Area;
        this.Development = Development;
        this.Location = Location;
        this.AvailableLots = AvailableLots;
        this.LotType = LotType;
        this.Agency = Agency;
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }

    public String getCarParkID() { return CarParkID; }
    public void setCarParkID(String CarParkID) { this.CarParkID = CarParkID; }

    public String getArea() { return Area; }
    public void setArea(String Area) { this.Area = Area; }

    public String getDevelopment() { return Development; }
    public void setDevelopment(String Development) { this.Development = Development; }

    public String getLocation() { return Location; }
    public void setLocation(String Location) { this.Location = Location; }

    public String getAvailableLots() { return AvailableLots; }
    public void setAvailableLots(String AvailableLots) { this.AvailableLots = AvailableLots; }

    public String getLotType() { return LotType; }
    public void setLotType(String LotType) { this.LotType = LotType; }

    public String getAgency() { return Agency; }
    public void setAgency(String Agency) { this.Agency = Agency; }
}
