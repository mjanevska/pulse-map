package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.Foodcheckin;
import models.Foodvenue;

import flexjson.JSONSerializer;
import play.mvc.*;

public class Services extends Controller {

	/*
	 * method that gets foodvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void foodvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeFoodvenue(Foodvenue.findAll());
		}else{
			res = serializeFoodvenue(Foodvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object foodvenue and redirects result to the object
	 */
	public static void foodvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		Foodvenue venue =  new Foodvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeFoodvenue(venue));
	}
	
	/*
	 * method that deletes object foodvenue and redirects result to the object
	 */
	public static void foodvenueDelete(Long id){
		//TODO: authentication
		
		Foodvenue venue = Foodvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object foodvenue and redirects result to the object
	 */
	public static void foodvenueUpdate(Foodvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeFoodvenue(venue));
	}
	
	/*
	 *  method for foodvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeFoodvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets foodtime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void foodcheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeFoodcheckin(Foodcheckin.findAll());
		}else{
			res = serializeFoodcheckin(Foodcheckin.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that select by venueID
	 **/
	public static void foodcheckinGetvenue(String venueID){
		String res="";
		res = serializeFoodcheckin(Foodcheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void foodcheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeFoodcheckin(Foodcheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object foodtime and redirects result to the object
	 */
	public static void foodcheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		Foodcheckin venue =  new Foodcheckin();

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
		renderJSON(serializeFoodcheckin(venue));
	}	

	/*
	 * method that deletes object Foodcheckin and redirects result to the object
	 */
	public static void foodcheckinDelete(Long id){
		//TODO: authentication
		
		Foodcheckin venue = Foodcheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object foodcheckin and redirects result to the object
	 */
	public static void foodcheckinUpdate(Foodcheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeFoodcheckin(venue));
	}
	
	/*
	 *  method for Foodcheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeFoodcheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
