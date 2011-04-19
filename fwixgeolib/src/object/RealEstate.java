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

public class RealEstate extends Content {
  public Location location;
  public Double price;
  public Float numberOfBeds;
  public Float numberOfBaths;
  public Float squareFeet;
  public String propertyType;

  public RealEstate() { }

  public RealEstate(Parcel i) {
    super(i);
    location = (Location)i.readParcelable(Location.class.getClassLoader());
    price = i.readDouble();
    numberOfBeds = i.readFloat();
    numberOfBaths = i.readFloat();
    squareFeet = i.readFloat();
    propertyType = i.readString();
  }

  public void writeToParcel(Parcel o, int f) {
    super.writeToParcel(o,f);
    o.writeParcelable(location,f);
    o.writeDouble(price);
    o.writeFloat(numberOfBeds);
    o.writeFloat(numberOfBaths);
    o.writeFloat(squareFeet);
    o.writeString(propertyType);
  }

  public static final Parcelable.Creator<RealEstate> CREATOR =
      new Parcelable.Creator<RealEstate>() {
    public RealEstate createFromParcel(Parcel i) { return new RealEstate(i); }
    public RealEstate[] newArray(int s) { return new RealEstate[s]; }
  };

}
