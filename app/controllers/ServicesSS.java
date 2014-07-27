package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.OutdoorsRecreationcheckin;
import models.ShopServicecheckin;
import models.ShopServicevenue;

import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesSS extends Controller {

	/*
	 * method that gets shopservicevenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void shopservicevenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeShopServicevenue(ShopServicevenue.findAll());
		}else{
			res = serializeShopServicevenue(ShopServicevenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object ShopServicevenue and redirects result to the object
	 */
	public static void shopservicevenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		ShopServicevenue venue =  new ShopServicevenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeShopServicevenue(venue));
	}
	
	/*
	 * method that deletes object ShopServicevenue and redirects result to the object
	 */
	public static void shopservicevenueDelete(Long id){
		//TODO: authentication
		
		ShopServicevenue venue = ShopServicevenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object ShopServicevenue and redirects result to the object
	 */
	public static void shopservicevenueUpdate(ShopServicevenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeShopServicevenue(venue));
	}
	
	/*
	 *  method for ShopServicevenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeShopServicevenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets ShopServicetime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void shopservicecheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeShopServicecheckin(ShopServicecheckin.findAll());
		}else{
			res = serializeShopServicecheckin(ShopServicecheckin.findById(id));			
		}
		renderJSON(res);		
	}
	/*
	 * method that select by venueID
	 **/
	public static void shopservicecheckinGetvenue(String venueID){
		String res="";
		res = serializeShopServicecheckin(ShopServicecheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void shopservicecheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeShopServicecheckin(ShopServicecheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object ShopServicetime and redirects result to the object
	 */
	public static void shopservicecheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		ShopServicecheckin venue =  new ShopServicecheckin();

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
		renderJSON(serializeShopServicecheckin(venue));
	}	

	/*
	 * method that deletes object ShopServicecheckin and redirects result to the object
	 */
	public static void shopservicecheckinDelete(Long id){
		//TODO: authentication
		
		ShopServicecheckin venue = ShopServicecheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object ShopServicecheckin and redirects result to the object
	 */
	public static void shopservicecheckinUpdate(ShopServicecheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeShopServicecheckin(venue));
	}
	
	/*
	 *  method for ShopServicecheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeShopServicecheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
