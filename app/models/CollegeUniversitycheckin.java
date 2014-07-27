package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class CollegeUniversitycheckin extends Model {
	@Required
	public String time;
	@Required
	public String day;
	@Required
	public String dayofweek;
	@Required
	public String month;
	@Required
	public String year;
	@Required
	public String venueID;
	@Required
	public String numC;
	
	public CollegeUniversitycheckin() {}
	
	public CollegeUniversitycheckin(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		super();
		this.time = time;
		this.day = day;
		this.dayofweek = dayofweek;
		this.month = month;
		this.year = year;
		this.venueID = venueID;
		this.numC = numC;
	}
	
	@Override
	public String toString() {
		return time+"-"+day+"/"+month+"/"+year+":"+venueID;
	}
}
