
package nz.geek.hurford.listfragdyloading;

import nz.geek.hurford.listfragdyloading.provider.ItemListDBHelper;
import nz.geek.hurford.listfragdyloading.provider.DataProvider;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class NameList extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleCursorAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // View view = inflater.inflate(android.R.layout.simple_list_item_1,
        // container, false);
        // return view;

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_2, null,
                new String[] {
                        ItemListDBHelper.COL_TITLE, ItemListDBHelper.COL_DESCRIPTION
                },
                new int[] {
                        android.R.id.text1, android.R.id.text2
                }, 0);
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
