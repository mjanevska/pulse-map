package controllers;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.ArtsEntertainmentvenue;
import models.NightlifeSpotcheckin;

import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesAE extends Controller {

	/*
	 * method that gets ArtsEntertainmentvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void artsentertainmentvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeArtsEntertainmentvenue(ArtsEntertainmentvenue.findAll());
		}else{
			res = serializeArtsEntertainmentvenue(ArtsEntertainmentvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object ArtsEntertainmentvenue and redirects result to the object
	 */
	public static void artsentertainmentvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		ArtsEntertainmentvenue venue =  new ArtsEntertainmentvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeArtsEntertainmentvenue(venue));
	}
	
	/*
	 * method that deletes object ArtsEntertainmentvenue and redirects result to the object
	 */
	public static void artsentertainmentvenueDelete(Long id){
		//TODO: authentication
		
		ArtsEntertainmentvenue venue = ArtsEntertainmentvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object ArtsEntertainmentvenue and redirects result to the object
	 */
	public static void artsentertainmentvenueUpdate(ArtsEntertainmentvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeArtsEntertainmentvenue(venue));
	}
	
	/*
	 *  method for ArtsEntertainmentvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeArtsEntertainmentvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets ArtsEntertainmenttime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void artsentertainmentcheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeArtsEntertainmentcheckin(ArtsEntertainmentcheckin.findAll());
		}else{
			res = serializeArtsEntertainmentcheckin(ArtsEntertainmentcheckin.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that select by venueID
	 **/
	public static void nightlifespotcheckinGetvenue(String venueID){
		String res="";
		res = serializeArtsEntertainmentcheckin(ArtsEntertainmentcheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	
	/*
	 * method that select by date
	 **/
	public static void artsentertainmentcheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeArtsEntertainmentcheckin(ArtsEntertainmentcheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object ArtsEntertainmenttime and redirects result to the object
	 */
	public static void artsentertainmentcheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		ArtsEntertainmentcheckin venue =  new ArtsEntertainmentcheckin();

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
		renderJSON(serializeArtsEntertainmentcheckin(venue));
	}	

	/*
	 * method that deletes object ArtsEntertainmentcheckin and redirects result to the object
	 */
	public static void artsentertainmentcheckinDelete(Long id){
		//TODO: authentication
		
		ArtsEntertainmentcheckin venue = ArtsEntertainmentcheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object ArtsEntertainmentcheckin and redirects result to the object
	 */
	public static void artsentertainmentcheckinUpdate(ArtsEntertainmentcheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeArtsEntertainmentcheckin(venue));
	}
	
	/*
	 *  method for ArtsEntertainmentcheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeArtsEntertainmentcheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
