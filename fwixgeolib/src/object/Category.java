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

public class Category implements Parcelable {
  public Integer id;
  public Integer parentId;
  public String name;

  public Category() { }

  public Category(Parcel i) {
    id = i.readInt();
    parentId = i.readInt();
    name = i.readString();
  }

  public void writeToParcel(Parcel o, int f) {
    o.writeInt(id);
    o.writeInt(parentId);
    o.writeString(name);
  }

  public static final Parcelable.Creator<Category> CREATOR =
      new Parcelable.Creator<Category>() {
    public Category createFromParcel(Parcel i) { return new Category(i); }
    public Category[] newArray(int s) { return new Category[s]; }
  };

  public int describeContents() { return 0; }
}
