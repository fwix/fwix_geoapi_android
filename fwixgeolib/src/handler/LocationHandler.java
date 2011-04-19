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

import com.fwix.android.api.object.Location;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class LocationHandler extends ErrorHandler {
  public Location location;

  public void handleStartElement(StringBuffer names, StringBuffer values,
      String uri, String localName, String qName, Attributes atts) {
    if (localName == "response") {
      location = new Location();
    }
  }

  public void handleEndElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName) {
    if (localName == "province") {
      location.province = formatValue(value);
    } else if (localName == "city") {
      location.city = formatValue(value);
    } else if (localName == "country") {
      location.country = formatValue(value);
    } else if (localName == "locality") {
      location.locality = formatValue(value);
    } else if (localName == "postal_code") {
      location.postalCode = formatValue(value);
    } else if (localName == "address") {
      location.address = formatValue(value);
    }
  }
}
