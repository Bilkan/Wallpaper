package net.uyghurdev.app.wallpaper;


import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
//import android.content.res.Resources;
public class MyParser {
	
	SitesList sitesList = null;
	public void parserXML(String urlStr,String fileName)
	{
		
		try {
	        
		       /** Handling XML */

		SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        
       /** Send URL to parse XML Tags */
        URL sourceUrl = new URL(
        "http://www.androidpeople.com/wp-content/uploads/2010/06/example.xml");
        
       /** Create handler to handle XML Tags ( extends DefaultHandler ) */
        MyXMLHandler myXMLHandler = new MyXMLHandler();
        xr.setContentHandler(myXMLHandler);
        xr.parse(new InputSource(sourceUrl.openStream()));
        
       } catch (Exception e) {
        e.toString();
        }


			 
	}

}
