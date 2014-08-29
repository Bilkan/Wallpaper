package net.uyghurdev.app.wallpaper;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyXMLHandler extends DefaultHandler {
	 
	Boolean currentElement = false;
	 String currentValue = null;
	 public static SitesList sitesList = null;
	 
	public static SitesList getSitesList() {
	 return sitesList;
	 }
	 
	public static void setSitesList(SitesList sitesList) {
	 MyXMLHandler.sitesList = sitesList;
	 }
	 
	/** Called when tag starts ( ex:- <name>AndroidPeople</name>
	 * -- <name> )*/
	 @Override
	 public void startElement(String uri, String localName, String qName,
	 Attributes attributes) throws SAXException {
	 
	currentElement = true;
	 
	if (localName.equals("wallpapers"))
	 {
	 /** Start */
	 sitesList = new SitesList();
//	 } else if (localName.equals("wallpaper")) {
//	 /** Get attribute value */
//	 String attr = attributes.getValue("wallpaper");
//	 sitesList.setIMGid(attr);
	 }
	 
	}
	 
	/** Called when tag closing ( ex:- <name>AndroidPeople</name>
	 * -- </name> )*/
	 @Override
	 public void endElement(String uri, String localName, String qName)
	 throws SAXException {
	 
	currentElement = false;
	 
	/** set value */
//	if (localName.equalsIgnoreCase("id"))
//	else	 sitesList.setIMGid(currentValue); 
	 if (localName.equalsIgnoreCase("name"))
	 sitesList.setIMGName(currentValue);
	 else if (localName.equalsIgnoreCase("thumbnail"))
		 sitesList.setImgSmall(currentValue); 
	 else if (localName.equalsIgnoreCase("picture"))
	 sitesList.setImgAddr(currentValue);
	 
//	 else if (localName.equalsIgnoreCase("date"))
//		 sitesList.setIMGDate(currentValue);
	}
	 
	/** Called to get tag characters ( ex:- <name>AndroidPeople</name>
	 * -- to get AndroidPeople Character ) */
	 @Override
	 public void characters(char[] ch, int start, int length)
	 throws SAXException {
	 
	if (currentElement) {
	 currentValue = new String(ch, start, length);
	 currentElement = false;
	 }
	 
	}
	 
	}
