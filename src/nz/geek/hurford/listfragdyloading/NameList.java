
package nz.geek.hurford.listfragdyloading;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.CursorAdapter;

import nz.geek.hurford.listfragdyloading.provider.DataProvider;
import nz.geek.hurford.listfragdyloading.provider.ItemListDBHelper;

public class NameList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    CursorAdapter mAdapter;
    
    //private static final String TAG = "NameList";

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        //View view = inflater.inflate(android.R.layout.list_content, container, true);
        
        //return view;
        return super.onCreateView(inflater, container, savedInstanceState);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new MyCursorAdapter(getActivity());
        setListAdapter(mAdapter);

        setListShown(false);

        getLoaderManager().initLoader(0, null, this);
    }

    static final String[] ITEM_PROJECTION = {
            ItemListDBHelper.COL_ID,
            ItemListDBHelper.COL_TITLE,
            ItemListDBHelper.COL_LINK,
            ItemListDBHelper.COL_DESCRIPTION
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle arg1) {
        Uri uri = DataProvider.URI_RSS;

        return new CursorLoader(getActivity(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);

        setListShown(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        mAdapter.swapCursor(null);
    }

}
