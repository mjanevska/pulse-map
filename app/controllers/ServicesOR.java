package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.NightlifeSpotcheckin;
import models.OutdoorsRecreationcheckin;
import models.OutdoorsRecreationvenue;

import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesOR extends Controller {

	/*
	 * method that gets OutdoorsRecreationvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void outdoorsrecreationvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeOutdoorsRecreationvenue(OutdoorsRecreationvenue.findAll());
		}else{
			res = serializeOutdoorsRecreationvenue(OutdoorsRecreationvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object outdoorsrecreationvenue and redirects result to the object
	 */
	public static void outdoorsrecreationvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		OutdoorsRecreationvenue venue =  new OutdoorsRecreationvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeOutdoorsRecreationvenue(venue));
	}
	
	/*
	 * method that deletes object OutdoorsRecreationvenue and redirects result to the object
	 */
	public static void outdoorsrecreationvenueDelete(Long id){
		//TODO: authentication
		
		OutdoorsRecreationvenue venue = OutdoorsRecreationvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object OutdoorsRecreationvenue and redirects result to the object
	 */
	public static void outdoorsrecreationvenueUpdate(OutdoorsRecreationvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeOutdoorsRecreationvenue(venue));
	}
	
	/*
	 *  method for OutdoorsRecreationvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeOutdoorsRecreationvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets OutdoorsRecreationtime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void outdoorsrecreationcheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeOutdoorsRecreationcheckin(OutdoorsRecreationcheckin.findAll());
		}else{
			res = serializeOutdoorsRecreationcheckin(OutdoorsRecreationcheckin.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that select by venueID
	 **/
	public static void outdoorsrecreationcheckinGetvenue(String venueID){
		String res="";
		res = serializeOutdoorsRecreationcheckin(OutdoorsRecreationcheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void outdoorsrecreationcheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeOutdoorsRecreationcheckin(OutdoorsRecreationcheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object OutdoorsRecreationtime and redirects result to the object
	 */
	public static void outdoorsrecreationcheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		OutdoorsRecreationcheckin venue =  new OutdoorsRecreationcheckin();

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
		renderJSON(serializeOutdoorsRecreationcheckin(venue));
	}	

	/*
	 * method that deletes object outdoorsrecreationcheckin and redirects result to the object
	 */
	public static void outdoorsrecreationcheckinDelete(Long id){
		//TODO: authentication
		
		OutdoorsRecreationcheckin venue = OutdoorsRecreationcheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object outdoorsrecreationcheckin and redirects result to the object
	 */
	public static void outdoorsrecreationcheckinUpdate(OutdoorsRecreationcheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeOutdoorsRecreationcheckin(venue));
	}
	
	/*
	 *  method for outdoorsrecreationcheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeOutdoorsRecreationcheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
