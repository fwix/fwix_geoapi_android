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

import com.fwix.android.api.FwixError;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ErrorHandler implements ContentHandler {
  public StringBuffer cNames;
  public StringBuffer cValue;

  public static String formatValue(StringBuffer value) {
    String res = value.toString();
    if (res == null) return res;
    res.trim();
    res = res.replace("&lt;", "<");
    res = res.replace("&gt;", ">");
    res = res.replace("&quot;", "\"");
    res = res.replace("&apos;", "'");
    return res.replace("&amp;", "&");
  }

  public void startDocument() {
    cNames = new StringBuffer();
    cValue = new StringBuffer();
  }

  public void characters(char[] ch, int start, int length) {
    cValue.append(ch, start, length);
  }
  
  public void handleStartElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName, Attributes atts) { }

  public void startElement(String uri, String localName, String qName,
      Attributes atts) {
    cNames.append(localName);
    cValue.delete(0,cValue.length());
    cValue.trimToSize();
    handleStartElement(cNames, cValue, uri, localName, qName, atts);
  }
  
  public void handleEndElement(StringBuffer names, StringBuffer value,
      String uri, String localName, String qName) { }

  public void endElement(String uri, String localName, String qName)
      throws SAXException {
    int cNLength = cNames.length();
    cNames.delete(cNLength - localName.length(),cNLength);
    if (cNames.indexOf("error") == 0 && localName == "message") {
      throw new FwixError(formatValue(cValue));
    }
    handleEndElement(cNames, cValue, uri, localName, qName);
  }

  public void endDocument() { }
  public void startPrefixMapping(String prefix, String uri) { }
  public void endPrefixMapping(String prefix) { }
  public void ignorableWhitespace(char[] ch, int start, int length) { }
  public void processingInstruction(String target, String data) { }
  public void setDocumentLocator(Locator locator) { }
  public void skippedEntity(String name) { }
}
