package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.CollegeUniversitycheckin;
import models.CollegeUniversityvenue;

import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesCU extends Controller {

	/*
	 * method that gets CollegeUniversityvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void collegeuniversityvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeCollegeUniversityvenue(CollegeUniversityvenue.findAll());
		}else{
			res = serializeCollegeUniversityvenue(CollegeUniversityvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object collegeuniversityvenue and redirects result to the object
	 */
	public static void collegeuniversityvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		CollegeUniversityvenue venue =  new CollegeUniversityvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeCollegeUniversityvenue(venue));
	}
	
	/*
	 * method that deletes object collegeuniversityvenue and redirects result to the object
	 */
	public static void collegeuniversityvenueDelete(Long id){
		//TODO: authentication
		
		CollegeUniversityvenue venue = CollegeUniversityvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object collegeuniversityvenue and redirects result to the object
	 */
	public static void collegeuniversityvenueUpdate(CollegeUniversityvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeCollegeUniversityvenue(venue));
	}
	
	/*
	 *  method for collegeuniversityvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeCollegeUniversityvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets collegeuniversitytime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void collegeuniversitycheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeCollegeUniversitycheckin(CollegeUniversitycheckin.findAll());
		}else{
			res = serializeCollegeUniversitycheckin(CollegeUniversitycheckin.findById(id));			
		}
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void collegeuniversitycheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeCollegeUniversitycheckin(CollegeUniversitycheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by venueID
	 **/
	public static void collegeuniversitycheckinGetvenue(String venueID){
		String res="";
		res = serializeCollegeUniversitycheckin(CollegeUniversitycheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	
	/*
	 * method that creates object collegeuniversitytime and redirects result to the object
	 */
	public static void collegeuniversitycheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		CollegeUniversitycheckin venue =  new CollegeUniversitycheckin();

		venue.time = time;
		venue.day = day;
		venue.dayofweek = dayofweek;
		venue.month = month;
		venue.year = year;
		venue.venueID = venueID;
		venue.numC = numC;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeCollegeUniversitycheckin(venue));
	}	

	/*
	 * method that deletes object collegeuniversitycheckin and redirects result to the object
	 */
	public static void collegeuniversitycheckinDelete(Long id){
		//TODO: authentication
		
		CollegeUniversitycheckin venue = CollegeUniversitycheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object collegeuniversitycheckin and redirects result to the object
	 */
	public static void collegeuniversitycheckinUpdate(CollegeUniversitycheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeCollegeUniversitycheckin(venue));
	}
	
	/*
	 *  method for collegeuniversitycheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeCollegeUniversitycheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
