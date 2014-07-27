package models;

import play.*;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class OutdoorsRecreationvenue extends Model {
	@Required
	public String latitude;
	@Required
	public String longitude;
	@Required
	@Unique
	public String idVenue;
	@Required
	public String nameVenue;
	
	
	public OutdoorsRecreationvenue() {}
	
	public OutdoorsRecreationvenue(String lat, String lon, String id, String name){
		super();
		this.latitude = lat;
		this.longitude = lon;
		this.idVenue = id;
		this.nameVenue = name;
	}
	
	@Override
	public String toString() {
		return nameVenue;
	}
}
