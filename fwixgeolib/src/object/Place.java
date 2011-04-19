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

package com.fwix.android.api.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Place implements Parcelable {
  public String uuid;
  public String name;
  public Double latitude;
  public Double longitude;
  public String phoneNumber;
  public Location location;
  public String link;
  public ArrayList<Category> categories;

  public Place() { }

  public Place(Parcel i) {
    uuid = i.readString();
    name = i.readString();
    latitude = i.readDouble();
    longitude = i.readDouble();
    phoneNumber = i.readString();
    location = (Location)i.readParcelable(Location.class.getClassLoader());
    link = i.readString();
    categories = new ArrayList<Category>();
    i.readTypedList(categories, Category.CREATOR);
  }

  public void writeToParcel(Parcel o, int f) {
    o.writeString(uuid);
    o.writeString(name);
    o.writeDouble(latitude);
    o.writeDouble(longitude);
    o.writeString(phoneNumber);
    o.writeParcelable(location,f);
    o.writeString(link);
    o.writeTypedList(categories);
  }

  public static final Parcelable.Creator<Place> CREATOR =
      new Parcelable.Creator<Place>() {
    public Place createFromParcel(Parcel i) { return new Place(i); }
    public Place[] newArray(int s) { return new Place[s]; }
  };

  public int describeContents() { return 0; }
}
