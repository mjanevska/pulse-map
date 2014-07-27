package controllers;

import models.ArtsEntertainmentcheckin;
import models.OutdoorsRecreationcheckin;
import models.Residencecheckin;
import models.Residencevenue;
import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesR extends Controller {

	/*
	 * method that gets foodvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void residencevenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeResidencevenue(Residencevenue.findAll());
		}else{
			res = serializeResidencevenue(Residencevenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object Residencevenue and redirects result to the object
	 */
	public static void residencevenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		Residencevenue venue =  new Residencevenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeResidencevenue(venue));
	}
	
	/*
	 * method that deletes object Residencevenue and redirects result to the object
	 */
	public static void residencevenueDelete(Long id){
		//TODO: authentication
		
		Residencevenue venue = Residencevenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object Residencevenue and redirects result to the object
	 */
	public static void residencevenueUpdate(Residencevenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeResidencevenue(venue));
	}
	
	/*
	 *  method for Residencevenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeResidencevenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets Residencetime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void residencecheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeResidencecheckin(Residencecheckin.findAll());
		}else{
			res = serializeResidencecheckin(Residencecheckin.findById(id));			
		}
		renderJSON(res);		
	}
	/*
	 * method that select by venueID
	 **/
	public static void residencecheckinGetvenue(String venueID){
		String res="";
		res = serializeResidencecheckin(Residencecheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void residencecheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeResidencecheckin(Residencecheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	
	/*
	 * method that creates object Residencetime and redirects result to the object
	 */
	public static void residencecheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		Residencecheckin venue =  new Residencecheckin();

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
		renderJSON(serializeResidencecheckin(venue));
	}	

	/*
	 * method that deletes object Residencecheckin and redirects result to the object
	 */
	public static void residencecheckinDelete(Long id){
		//TODO: authentication
		
		Residencecheckin venue = Residencecheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object Residencecheckin and redirects result to the object
	 */
	public static void residencecheckinUpdate(Residencecheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeResidencecheckin(venue));
	}
	
	/*
	 *  method for residencecheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeResidencecheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}

}
