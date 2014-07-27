package controllers;

import models.ArtsEntertainmentcheckin;
import models.Foodcheckin;
import models.NightlifeSpotcheckin;
import models.NightlifeSpotvenue;
import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesNS extends Controller {

	/*
	 * method that gets foodvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void nightlifespotvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeNightlifeSpotvenue(NightlifeSpotvenue.findAll());
		}else{
			res = serializeNightlifeSpotvenue(NightlifeSpotvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object NightlifeSpotvenue and redirects result to the object
	 */
	public static void nightlifespotvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		NightlifeSpotvenue venue =  new NightlifeSpotvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeNightlifeSpotvenue(venue));
	}
	
	/*
	 * method that deletes object NightlifeSpotvenue and redirects result to the object
	 */
	public static void nightlifespotvenueDelete(Long id){
		//TODO: authentication
		
		NightlifeSpotvenue venue = NightlifeSpotvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object NightlifeSpotvenue and redirects result to the object
	 */
	public static void nightlifespotvenueUpdate(NightlifeSpotvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeNightlifeSpotvenue(venue));
	}
	
	/*
	 *  method for NightlifeSpotvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeNightlifeSpotvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets NightlifeSpottime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void nightlifespotcheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeNightlifeSpotcheckin(NightlifeSpotcheckin.findAll());
		}else{
			res = serializeNightlifeSpotcheckin(NightlifeSpotcheckin.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that select by date
	 **/
	public static void nightlifespotcheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeNightlifeSpotcheckin(NightlifeSpotcheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by venueID
	 **/
	public static void nightlifespotcheckinGetvenue(String venueID){
		String res="";
		res = serializeNightlifeSpotcheckin(NightlifeSpotcheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object NightlifeSpottime and redirects result to the object
	 */
	public static void nightlifespotcheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		NightlifeSpotcheckin venue =  new NightlifeSpotcheckin();

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
		renderJSON(serializeNightlifeSpotcheckin(venue));
	}	

	/*
	 * method that deletes object NightlifeSpotcheckin and redirects result to the object
	 */
	public static void nightlifespotcheckinDelete(Long id){
		//TODO: authentication
		
		NightlifeSpotcheckin venue = NightlifeSpotcheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object NightlifeSpotcheckin and redirects result to the object
	 */
	public static void nightlifespotcheckinUpdate(NightlifeSpotcheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeNightlifeSpotcheckin(venue));
	}
	
	/*
	 *  method for NightlifeSpotcheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeNightlifeSpotcheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}

}
