package nz.geek.hurford.listfragdyloading.rss;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSHandler extends DefaultHandler {
	private StringBuilder content = null;
	private ArrayList<RSSItem> items = new ArrayList<RSSItem>();
	private RSSItem item = null;
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content.append(ch);
		super.characters(ch, start, length);
	}

	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(localName.equalsIgnoreCase("title")) {
			item.setTitle(content.toString());
		} else if(localName.equalsIgnoreCase("link")) {
			item.setLink(content.toString());
		} else if(localName.equalsIgnoreCase("description")) {
			item.setDescription(content.toString());
		} else if(localName.equalsIgnoreCase("item")) {
			items.add(item);
			item = null;
			item = new RSSItem();
		}
		super.endElement(uri, localName, qName);
	}

	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		
	}

	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		content = new StringBuilder();
		super.startElement(uri, localName, qName, attributes);
	}

	
	public List<RSSItem> getItems() {
		return items;
	}
}
