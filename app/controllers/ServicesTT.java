package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ArtsEntertainmentcheckin;
import models.OutdoorsRecreationcheckin;
import models.TravelTransportcheckin;
import models.TravelTransportvenue;

import flexjson.JSONSerializer;
import play.mvc.*;

public class ServicesTT extends Controller {

	/*
	 * method that gets TravelTransportvenue from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void TravelTransportvenueGet(Long id){
		String res = "";
		if(id == null){
			res = serializeTravelTransportvenue(TravelTransportvenue.findAll());
		}else{
			res = serializeTravelTransportvenue(TravelTransportvenue.findById(id));			
		}
		renderJSON(res);		
	}
	
	/*
	 * method that creates object TravelTransportvenue and redirects result to the object
	 */
	public static void traveltransportvenueCreate(String latitude, String longitude, String idVenue, String nameVenue, String vis){
		//TODO: authentication
		//TODO: validation
		TravelTransportvenue venue =  new TravelTransportvenue();
		venue.latitude = latitude;
		venue.longitude = longitude;
		venue.idVenue = idVenue;
		venue.nameVenue = nameVenue;
		
		venue.save();
		venue.validateAndCreate();
		//what to return: newly created object or redirect to the object?
		renderJSON(serializeTravelTransportvenue(venue));
	}
	
	/*
	 * method that deletes object TravelTransportvenue and redirects result to the object
	 */
	public static void traveltransportvenueDelete(Long id){
		//TODO: authentication
		
		TravelTransportvenue venue = TravelTransportvenue.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object TravelTransportvenue and redirects result to the object
	 */
	public static void traveltransportvenueUpdate(TravelTransportvenue venue){
		//TODO: validation
		
		venue.save();
		//kurs.validateAndSave();
		//what to return?
		renderJSON(serializeTravelTransportvenue(venue));
	}
	
	/*
	 *  method for TravelTransportvenue object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeTravelTransportvenue(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("latitude", "longitude", "idVenue", "nameVenue")
				.exclude("*").serialize(venue);
		
		return res;
	}

	/*
	 * method that gets TravelTransporttime from input parameter kurs id
	 * return a JSON string and set the response content type
	 */
	public static void traveltransportcheckinGet(Long id){
		String res = "";
		if(id == null){
			res = serializeTravelTransportcheckin(TravelTransportcheckin.findAll());
		}else{
			res = serializeTravelTransportcheckin(TravelTransportcheckin.findById(id));			
		}
		renderJSON(res);		
	}
	/*
	 * method that select by venueID
	 **/
	public static void traveltransportcheckinGetvenue(String venueID){
		String res="";
		res =serializeTravelTransportcheckin(TravelTransportcheckin.find("venueID", venueID).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that select by date
	 **/
	public static void traveltransportcheckinGettime(String dayf, String monthf, String yearf, String dayt, String montht){
		String res="";
		res = serializeTravelTransportcheckin(TravelTransportcheckin.find("day >= ? and day <= ? and month >= ? and month <= ?",dayf, dayt, monthf, montht).fetch());			
		
		renderJSON(res);		
	}
	/*
	 * method that creates object TravelTransporttime and redirects result to the object
	 */
	public static void traveltransportcheckinCreate(String time, String day, String dayofweek, String month, String year, String venueID, String numC){
		//TODO: authentication
		//TODO: validation
		TravelTransportcheckin venue =  new TravelTransportcheckin();

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
		renderJSON(serializeTravelTransportcheckin(venue));
	}	

	/*
	 * method that deletes object TravelTransportcheckin and redirects result to the object
	 */
	public static void traveltransportcheckinDelete(Long id){
		//TODO: authentication
		
		TravelTransportcheckin venue = TravelTransportcheckin.findById(id);
		venue.delete();
		//what to return?
	}
	
	/*
	 * method that updates object TravelTransportcheckin and redirects result to the object
	 */
	public static void traveltransportcheckinUpdate(TravelTransportcheckin venue){
		//TODO: validation
		
		venue.save();
		renderJSON(serializeTravelTransportcheckin(venue));
	}
	
	/*
	 *  method for TravelTransportcheckin object serialization,
	 *  translating data structures or object state
	 *  into a format that can be stored
	 */
	private static String serializeTravelTransportcheckin(Object venue){
		JSONSerializer serializer = new JSONSerializer();
		String res = serializer.include("time", "day", "dayofweek", "month", "year", "venueID", "numC")
				.exclude("*").serialize(venue);
		
		return res;
	}
}
