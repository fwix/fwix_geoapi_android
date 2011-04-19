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

import java.util.Date;

public class Content implements Parcelable {
  public String uuid;
  public Double latitude;
  public Double longitude;
  public String title;
  public String body;
  public String author;
  public Date publishedAt;
  public String link;
  public String image;

  public Content() { }

  public Content(Parcel i) {
    uuid = i.readString();
    latitude = i.readDouble();
    longitude = i.readDouble();
    title = i.readString();
    body = i.readString();
    author = i.readString();
    publishedAt = new Date(i.readLong());
    link = i.readString();
    image = i.readString();
  }

  public void writeToParcel(Parcel o, int f) {
    o.writeString(uuid);
    o.writeDouble(latitude);
    o.writeDouble(longitude);
    o.writeString(title);
    o.writeString(body);
    o.writeString(author);
    o.writeLong(publishedAt.getTime());
    o.writeString(link);
    o.writeString(image);
  }

  public static final Parcelable.Creator<Content> CREATOR =
      new Parcelable.Creator<Content>() {
    public Content createFromParcel(Parcel i) { return new Content(i); }
    public Content[] newArray(int s) { return new Content[s]; }
  };

  public int describeContents() { return 0; }
}
