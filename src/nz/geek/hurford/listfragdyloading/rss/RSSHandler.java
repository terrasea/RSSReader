package nz.geek.hurford.listfragdyloading.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

public class RSSHandler extends DefaultHandler {
	private StringBuilder content = null;
	private List<Item> items = null;
	private Item item = null;
	private ItemFactory itemFactory = null;

	//private final static String TAG = "RSSHandler";
	
	public RSSHandler(List<Item> items, ItemFactory itemFactory) {
	    this.items = items;
	    this.itemFactory = itemFactory;
	    item = itemFactory.newItem();
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		content.append(ch, start, length);
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
			item = itemFactory.newItem();
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
	    content = null;
		content = new StringBuilder();

		super.startElement(uri, localName, qName, attributes);
	}

	
	public List<Item> getItems() {
		return items;
	}
}
