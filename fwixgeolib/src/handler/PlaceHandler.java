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

package com.fwix.android.api.handler;

import com.fwix.android.api.object.Category;
import com.fwix.android.api.object.Location;
import com.fwix.android.api.object.Place;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class PlaceHandler extends CategoryHandler {
  public Place place;
  public ArrayList<Place> places;

  public void handleStartElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName, Attributes atts) {
    super.handleStartElement(names, value, uri, localName, qName, atts);
    if (localName == "places") {
      places = new ArrayList<Place>();
    } else if (localName == "place") {
      place = new Place();
      place.location = new Location();
    }
  }

  public void handleEndElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName) {
    super.handleEndElement(names, value, uri, localName, qName);
    if (localName == "place") {
      place.categories = categories;
      if (places != null) places.add(place);
      categories = null;
    } 
    if (names.indexOf("place") != -1) {
      if (localName == "phone_number") {
        place.phoneNumber = formatValue(value);
      } else if (localName == "city") {
        place.location.city = formatValue(value);
      } else if (localName == "province") {
        place.location.province = formatValue(value);
      } else if (localName == "uuid") {
        place.uuid = formatValue(value);
      } else if (localName == "locality") {
        place.location.locality = formatValue(value);
      } else if (localName == "country") {
        place.location.country = formatValue(value);
      } else if (localName == "link") {
        place.link = formatValue(value);
      } else if (localName == "address") {
        place.location.address = formatValue(value);
      } else if (localName == "lat") {
        place.latitude = new Double(formatValue(value));
      } else if (localName == "lon") {
        place.longitude = new Double(formatValue(value));
      } else if (localName == "postal_code") {
        place.location.postalCode = formatValue(value);
      } else if (localName == "name") {
        place.name = formatValue(value);
      }
    }
  }
}
