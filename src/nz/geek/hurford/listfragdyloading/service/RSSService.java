package nz.geek.hurford.listfragdyloading.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import nz.geek.hurford.listfragdyloading.provider.DataProvider;
import nz.geek.hurford.listfragdyloading.provider.ItemListDBHelper;
import nz.geek.hurford.listfragdyloading.rss.Item;
import nz.geek.hurford.listfragdyloading.rss.ItemFactory;
import nz.geek.hurford.listfragdyloading.rss.RSSHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

public class RSSService extends IntentService {

    //private final String TAG = "RSSService";
    private XMLReader reader;
    
    
    public RSSService() {
        super("RSSService");
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            List<Item> items = new ArrayList<Item>();
            RSSHandler handler = new RSSHandler(items, ItemFactory.newInstance());
            reader.setContentHandler(handler);
            URL url = new URL("https://www.greens.org.nz/everything/feed");
            reader.parse(new InputSource(url.openStream()));
            getContentResolver().delete(DataProvider.URI_RSS, null, null);
            for(Item item : items) {
                ContentValues mNewValues = new ContentValues();
                mNewValues.put(ItemListDBHelper.COL_TITLE, item.getTitle());
                mNewValues.put(ItemListDBHelper.COL_LINK, item.getLink());
                mNewValues.put(ItemListDBHelper.COL_DESCRIPTION, item.getDescription());
                getContentResolver().insert(DataProvider.URI_RSS, mNewValues);
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
