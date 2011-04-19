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

import java.util.ArrayList;

import com.fwix.android.api.Util;
import com.fwix.android.api.object.Content;
import com.fwix.android.api.object.CriticReview;
import com.fwix.android.api.object.Event;
import com.fwix.android.api.object.News;
import com.fwix.android.api.object.Photo;
import com.fwix.android.api.object.Review;
import com.fwix.android.api.object.UserReview;

import org.xml.sax.Attributes;

public class ContentHandler extends ErrorHandler {
  public Content content;
  public ArrayList<Content> contents;

  public void handleStartElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName, Attributes atts) {
    super.handleStartElement(names, value, uri, localName, qName, atts);
    if (localName == "response") {
      contents = new ArrayList<Content>();
    } else if (localName == "photo") {
      content = new Photo();
    } else if (localName == "user_review") {
      content = new UserReview();
    } else if (localName == "critic_review") {
      content = new CriticReview();
    } else if (localName == "news") {
      content = new News();
    } else if (localName == "event") {
      content = new Event();
    }
  }

  public void handleEndElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName) {
    super.handleEndElement(names, value, uri, localName, qName);
    if (localName == "response" && !(contents.size() > 0)) {
      contents = null;
    } else if (localName == "body") {
      content.body = formatValue(value);
    } else if (localName == "uuid") {
      content.uuid = formatValue(value);
    } else if (localName == "title") {
      content.title = formatValue(value);
    } else if (localName == "author") {
      content.author = formatValue(value);
    } else if (localName == "link") {
      content.link = formatValue(value);
    } else if (localName == "lat") {
      content.latitude = new Double(formatValue(value));
    } else if (localName == "lng") {
      content.longitude = new Double(formatValue(value));
    } else if (localName == "published_at") {
      content.publishedAt = Util.stringToDate(formatValue(value));
    } else if (localName == "image") {
      content.image = formatValue(value);
    } else if (content instanceof Photo) {
      Photo p = (Photo)content;
      if (localName == "photo") {
        contents.add(content);
      } else if (localName == "thumbnail") {
        p.thumbnail = formatValue(value);
      }
    } else if (content instanceof Review) {
      Review r = (Review)content;
      if (localName == "rating") {
        r.rating = Float.valueOf(formatValue(value));
      } else if (content instanceof UserReview) {
        if (localName == "user_review") {
          contents.add(content);
        }
      } else if (content instanceof CriticReview) {
        if (localName == "critic_review") {
          contents.add(content);
        }
      }
    } else if (content instanceof News) {
      if (localName == "news" && names.indexOf("news") != -1) {
        contents.add(content);
      }
    } else if (content instanceof Event) {
      Event e = (Event)content;
      if (localName == "event") {
        contents.add(content);
      } else if (localName == "start_time") {
        e.localStartTime = Util.stringToDate(formatValue(value));
      } else if (localName == "end_time") {
        e.localEndTime=  Util.stringToDate(formatValue(value));
      }
    }
  }

}
