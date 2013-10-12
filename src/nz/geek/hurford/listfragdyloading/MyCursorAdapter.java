package nz.geek.hurford.listfragdyloading;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import nz.geek.hurford.listfragdyloading.provider.ItemListDBHelper;

public class MyCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    
    //private static final String TAG = "MyCursorAdapter";
    
    class Holder {
        TextView title;
        TextView link;
    }
    
    public MyCursorAdapter(Context context) {
        super(context, null, 0);
        
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder held = (Holder)view.getTag();
        if(held != null && cursor != null) {
            int titlePos = cursor.getColumnIndex(ItemListDBHelper.COL_TITLE);
            String title = cursor.getString(titlePos);
            held.title.setText(title);
            int linkPos = cursor.getColumnIndex(ItemListDBHelper.COL_LINK);
            String link = cursor.getString(linkPos);
            held.link.setText(link);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View itemView =
                mInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        final Holder holder = new Holder();
        holder.title = (TextView)itemView.findViewById(android.R.id.text1);
        holder.link = (TextView)itemView.findViewById(android.R.id.text2);

        itemView.setTag(holder);
        
        return itemView;
    }

}
