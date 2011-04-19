/*
* Copyright 2011 Fwix, Inc
*
* Permission to use, copy, modify, distribute, and sell this software
* and its documentation for any purpose is hereby granted without fee,
* provided that the above copyright notice appears in all copies and that
* both that copyright notice and this permission notice appear in
* supporting documentation, and that the name of Fwix, Inc
* not be used in advertising or publicity
* pertaining to distribution of the software without specific, written
* prior permission.  Fwix, Inc makes no representations about the 
* suitability of this software for any
* purpose.  It is provided "as is" without express or implied warranty.
*
* Fwix, Inc disclaims all warranties with regard to this software, 
* including all implied warranties of merchantability and fitness, 
* in no event shall Fwix, Inc be liable for any special, indirect or
* consequential damages or any damages whatsoever resulting from loss of
* use, data or profits, whether in an action of contract, negligence or
* other tortious action, arising out of or in connection with the use or
* performance of this software.
*
*
*/

package com.fwix.android.api;

import android.os.Bundle;

import com.fwix.android.api.handler.CategoryHandler;
import com.fwix.android.api.handler.ContentHandler;
import com.fwix.android.api.handler.ErrorHandler;
import com.fwix.android.api.handler.LocationHandler;
import com.fwix.android.api.handler.PlaceHandler;
import com.fwix.android.api.object.Category;
import com.fwix.android.api.object.Content;
import com.fwix.android.api.object.Location;
import com.fwix.android.api.object.Page;
import com.fwix.android.api.object.Place;
import com.fwix.android.api.object.Range;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class Fwix {

  public static final String BASE_URL = "http://geoapi.fwix.com/";
  public static final String FORMAT = "xml";

  public static final String P_API_KEY = "api_key";
  public static final String P_USER_ID = "user_id"; 

  public static final String PAGE = "Page"; 
  public static final String P_PAGE = "page"; 
  public static final String P_PAGE_SIZE = "page_size"; 

  public static final String RANGE = "Range"; 
  public static final String P_RANGE_START = "after"; 
  public static final String P_RANGE_END = "before"; 
  
  public static final String P_LAT = "lat";
  public static final String P_LON = "lng";
  public static final String P_POSTAL_CODE = "postal_code";

  public static final String LOCATION = "Location";
  public static final String P_COUNTRY = "country";
  public static final String P_PROVINCE = "province";
  public static final String P_CITY = "city";
  public static final String P_ADDRESS = "address";
  public static final String P_LOCALITY = "locality";
  
  public static final String PLACE = "Place";
  public static final String P_PLACE_ID = "place_id";
  public static final String P_NAME = "name";
  public static final String P_PHONE_NUMBER = "phone_number";

  public static final String P_CATEGORIES = "categories";

  public static final String CONTENT_TYPES = "Content_types";
  public static final String CONTENT_TYPE_NEWS = "news";
  public static final String CONTENT_TYPE_PHOTOS = "photos";
  public static final String CONTENT_TYPE_REVIEWS = "reviews";
  public static final String CONTENT_TYPE_CRITIC_REVIEWS = "critic_reviews";
  public static final String CONTENT_TYPE_STATUS_UPDATES = "status_updates";
  public static final String CONTENT_TYPE_EVENTS = "events";
  public static final String CONTENT_TYPE_REAL_ESTATE = "real_estate";

  public static final String P_CONTENT_TYPES = "content_types";

  private String mApiKey;
  private String mUserId;

  public Fwix(String apiKey) { this(apiKey, null); } 
  public Fwix(String apiKey, String userId) {
    mApiKey = apiKey;
    mUserId = userId;
  }
  
  public ArrayList<Category> getCategories() throws FwixError {
    CategoryHandler handler = new CategoryHandler();
    Util.handle(request("categories", null), handler);
    return handler.categories;
  }

  public Location getLocation(Double lat, Double lon) throws FwixError {
    Bundle params = new Bundle();
    params.putDouble(P_LAT, lat);
    params.putDouble(P_LON, lon);

    LocationHandler handler = new LocationHandler();
    Util.handle(request("location", params), handler);
    return handler.location;
  }

  public Place getPlace(String uuid) throws FwixError {
    PlaceHandler handler = new PlaceHandler();
    Util.handle(request("places/" + uuid, null), handler);
    return handler.place;
  }
  
  public List<Place> getPlaces(Double lat, Double lon, Page page,
      Range range) throws FwixError {
    Bundle params = Util.createParams(page, range);
    params.putDouble(P_LAT, lat);
    params.putDouble(P_LON, lon);

    PlaceHandler handler = new PlaceHandler();
    Util.handle(request("places", params), handler);
    return handler.places;
  }

  public List<Place> getPlaces(String postalCode, Page page, Range range)
      throws FwixError {
    Bundle params = Util.createParams(page, range);
    params.putString(P_POSTAL_CODE, postalCode);

    PlaceHandler handler = new PlaceHandler();
    Util.handle(request("places", params), handler);
    return handler.places;
  }

  public List<Place> getPlaces(Location location, Page page) throws FwixError {
    Bundle params = Util.createParams(page, null);
    params.putParcelable(LOCATION, location);

    PlaceHandler handler = new PlaceHandler();
    Util.handle(request("places", params), handler);
    return handler.places;
  }

  public boolean setPlace(Place place) throws FwixError {
    Bundle params = new Bundle();
    params.putParcelable(PLACE, place);

    String path = "places";
    if (place.uuid != null) path += "/" + place.uuid; 
    ErrorHandler handler = new ErrorHandler();
    Util.handle(request("POST", path, params), handler);
    return true;
  }

  public boolean deletePlace(Place place) throws FwixError {
    String path = "places";
    if (place.uuid != null) path += "/" + place.uuid; 
    else throw new FwixError("Delete requires a uuid for place.");
    ErrorHandler handler = new ErrorHandler();
    Util.handle(request("DELETE", path, null), handler);
    return true;
  }

  public List<Content> getContent(Double lat, Double lon,
      ArrayList<String> types, Page page, Range range) throws FwixError {
    Bundle params = Util.createParams(types, page, range);
    params.putDouble(P_LAT, lat);
    params.putDouble(P_LON, lon);

    ContentHandler handler = new ContentHandler();
    Util.handle(request("content", params), handler);
    return handler.contents;
  }

  public List<Content> getContent(String postalCode,
      ArrayList<String> types, Page page, Range range) throws FwixError {
    Bundle params = Util.createParams(types, page, range);
    params.putString(P_POSTAL_CODE, postalCode);

    ContentHandler handler = new ContentHandler();
    Util.handle(request("content", params), handler);
    return handler.contents;
  }

  public List<Content> getContent(Location location,
      ArrayList<String> types, Page page, Range range) throws FwixError {
    Bundle params = Util.createParams(types, page, range);
    params.putParcelable(LOCATION, location);

    ContentHandler handler = new ContentHandler();
    Util.handle(request("content", params), handler);
    return handler.contents;
  }

  public List<Content> getContent(Place place, ArrayList<String> types,
      Page page, Range range) throws FwixError {
    Bundle params = Util.createParams(types, page, range);
    params.putParcelable(PLACE, place);

    ContentHandler handler = new ContentHandler();
    Util.handle(request("content", params), handler);
    return handler.contents;
  }

  public InputStream request(String path, Bundle params) throws FwixError {
    return request("GET", path, params);
  }
  public InputStream request(String method, String path, Bundle params)
      throws FwixError {
    if (params == null) params = new Bundle();
    params.putString(P_API_KEY, mApiKey);
    if (mUserId != null) params.putString(P_USER_ID, mUserId);

    String url = Util.formatUrl(BASE_URL, path, FORMAT, params);
    android.util.Log.v("Fwix","url: " + url);
    HttpUriRequest httpRequest = null;
    if (method == "GET") httpRequest = new HttpGet(url);
    else if (method == "POST") httpRequest = new HttpPost(url);
    else if (method == "DELETE") httpRequest = new HttpDelete(url);
    else throw new FwixError("Http method must be either POST GET DELETE");
    params.clear();
    return Util.sendRequest(httpRequest);
  }

}
