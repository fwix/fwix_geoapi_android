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

public class Event extends Content {
  public Date localStartTime;
  public Date localEndTime;

  public Event() { }

  public Event(Parcel i) {
    super(i);
    localStartTime = new Date(i.readLong());
    localEndTime = new Date(i.readLong());
  }

  public void writeToParcel(Parcel o, int f) {
    super.writeToParcel(o,f);
    o.writeLong(localStartTime.getTime());
    o.writeLong(localEndTime.getTime());
  }

  public static final Parcelable.Creator<Event> CREATOR =
      new Parcelable.Creator<Event>() {
    public Event createFromParcel(Parcel i) { return new Event(i); }
    public Event[] newArray(int s) { return new Event[s]; }
  };
}
