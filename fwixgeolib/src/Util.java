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
import android.util.Xml;

import com.fwix.android.api.handler.ErrorHandler;
import com.fwix.android.api.object.Category;
import com.fwix.android.api.object.Location;
import com.fwix.android.api.object.Page;
import com.fwix.android.api.object.Place;
import com.fwix.android.api.object.Range;

import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.xml.sax.SAXException;

public class Util {

  public static Bundle createParams(Page page, Range range) {
    return createParams(null, page, range);
  }
  
  public static Bundle createParams(ArrayList<String> types, Page page,
      Range range) {
    Bundle params = new Bundle();
    params.putParcelable(Fwix.PAGE, page);
    params.putParcelable(Fwix.RANGE, range);
    params.putStringArrayList(Fwix.CONTENT_TYPES, types);
    return params;
  }
  
  public static Date stringToDate (String sdate) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
      return formatter.parse(sdate);
    } catch (ParseException e) { }
    return null;
  }

  public static String formatUrl(String baseUrl, String path, String format,
      Bundle params) {
    StringBuilder s = new StringBuilder(baseUrl + path + "." + format);
    if (params == null) return s.toString();
    boolean f = true;
    while (!params.isEmpty()) {
      Set<String> keys = params.keySet();
      String k = keys.iterator().next();
      String v = null;
      Object obj = params.get(k);
      if (obj instanceof String || obj instanceof Integer ||
          obj instanceof Double) {
        v = obj.toString();
      } else if (obj instanceof Long) {
        v = Long.toString((Long)obj);
      } else if (obj instanceof Page) {
        Page page = (Page)obj;
        if (page.number != null) params.putInt(Fwix.P_PAGE, page.number);
        if (page.size != null) params.putInt(Fwix.P_PAGE_SIZE, page.size);
      } else if (obj instanceof Range) {
        Range range = (Range)obj;
        if (range.start != null) {
          params.putLong(Fwix.P_RANGE_START, range.start.getTime());
        }
        if (range.end != null) {
          params.putLong(Fwix.P_RANGE_END, range.end.getTime());
        }
      } else if (obj instanceof Location) {
        Location loc = (Location)obj;
        if (loc.country != null) {
          params.putString(Fwix.P_COUNTRY, loc.country);
        }
        if (loc.province != null) {
          params.putString(Fwix.P_PROVINCE, loc.province);
        }
        if (loc.city != null) {
          params.putString(Fwix.P_CITY, loc.city);
        }
        if (loc.address != null) {
          params.putString(Fwix.P_ADDRESS, loc.address);
        }
        if (loc.postalCode != null) {
          params.putString(Fwix.P_POSTAL_CODE, loc.postalCode);
        }
        if (loc.locality != null) {
          params.putString(Fwix.P_LOCALITY, loc.locality);
        }
      } else if (obj instanceof Place) {
        Place p = (Place)obj;
        if (p.uuid != null) {
          params.putString(Fwix.P_PLACE_ID, p.uuid);
        }
        if (p.latitude != null) {
          params.putDouble(Fwix.P_LAT, p.latitude);
        }
        if (p.longitude != null) {
          params.putDouble(Fwix.P_LON, p.longitude);
        }
        if (p.name != null) {
          params.putString(Fwix.P_NAME, p.name);
        }
        if (p.phoneNumber != null) {
          params.putString(Fwix.P_PHONE_NUMBER, p.phoneNumber);
        }
        if (p.categories != null) {
          params.putParcelableArrayList(Fwix.P_CATEGORIES,
            (ArrayList<Category>)p.categories);
        }
      } else if (obj instanceof ArrayList) {
        ArrayList list = (ArrayList)obj;
        if (list.size() > 0) {
          Object lobj = list.get(0);
          if (lobj instanceof Category) {
            StringBuilder cats = new StringBuilder();
            for (Category c : (ArrayList<Category>)obj) {
              cats.append(c.id + ",");
            }
            if (cats.length() > 0) v = cats.substring(0, cats.length() - 1);
          } else if (lobj instanceof String) {
            StringBuilder ts = new StringBuilder();
            for (String t : (ArrayList<String>)obj) { ts.append(t + ","); }
            if (ts.length() > 0) {
              params.putString(Fwix.P_CONTENT_TYPES,
                ts.substring(0, ts.length() - 1));
            }
          }
        }
      }

      if (v != null) {
        if (f) {
          f = false;
          s.append("?");
        } else {
          s.append("&");
        }
        s.append(URLEncoder.encode(k) + "=" + URLEncoder.encode(v));
      }
      params.remove(k);
    }
    return s.toString();
  }
  
  public static void handle(InputStream stream, ErrorHandler handler)
      throws FwixError {
    if (stream == null) return;
    try {
      Xml.parse(stream, Xml.Encoding.UTF_8, handler);
    } catch (IOException e) {
      throw new FwixError(e);
    } catch (SAXException e) {
      if (e instanceof FwixError) throw (FwixError)e;
      throw new FwixError(e);
    } finally {
      try { stream.close();
      } catch (IOException e) { }
    }
  }

  public static InputStream sendRequest(HttpUriRequest request)
      throws FwixError {
    HttpParams httpParameters = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(httpParameters, 20000);
    HttpConnectionParams.setSoTimeout(httpParameters, 25000);
    //HttpConnectionParams.setLinger(httpParameters, 0);
    HttpClient client = new DefaultHttpClient();
    HttpResponse response = null;
    try {
      response = (HttpResponse) client.execute(request);
    } catch (IOException e) {
      throw new FwixError(e);
    } finally {
      request = null;
      client = null;
    }
    HttpEntity entity = response.getEntity();
    InputStream stream = null;
    try {
      stream = entity.getContent();
    } catch (IOException e) {
      throw new FwixError(e);
    } finally {
      response = null;
      entity = null;
    }
    return stream;
  }

  public static String inputStreamToString(InputStream stream)
      throws FwixError {
    BufferedReader reader = new BufferedReader(
      new InputStreamReader(stream), 8000);
    StringBuffer sb = new StringBuffer();
    String line;
    try { while ((line = reader.readLine()) != null) { sb.append(line); }
    } catch (IOException e) {
      throw new FwixError(e);
    } finally {
      try { stream.close();
      } catch (IOException e) {
        throw new FwixError(e);
      }
    }
    return sb.toString();
  }

}
