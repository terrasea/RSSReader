
package nz.geek.hurford.listfragdyloading.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DataProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    private ItemListDBHelper dbHelper;
    private SQLiteDatabase itemDB;

    public static final String AUTHORITY = "nz.geek.hurford.listfragdyloading.provider";
    public static final String RSS_PATH = "rss";

    public static final Uri URI_RSS = Uri.parse("content://" + AUTHORITY + "/"
            + RSS_PATH);

    private static final int URI_KIND_RSS_ID = 0x01;
    private static final int URI_KIND_RSSs = 0x02;

    // public static final Uri CONTENT_ID_URI_BASE = Uri
    // .parse("content://nz.geek.hurford.listfragdyloading.provider/listitem");

    static {
        uriMatcher.addURI(AUTHORITY, RSS_PATH + "/#", URI_KIND_RSS_ID);
        uriMatcher.addURI(AUTHORITY, RSS_PATH, URI_KIND_RSSs);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        switch (uriMatcher.match(uri)) {
            case URI_KIND_RSS_ID:
                itemDB = itemDB != null ? itemDB : dbHelper.getWritableDatabase();
                int count = itemDB.delete(ItemListDBHelper.TABLE_NAME, where,
                        whereArgs);
                if (count > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return count;
            default:
                break;
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case URI_KIND_RSSs:
                return "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + RSS_PATH;
            default:
                break;
        }

        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        itemDB = itemDB != null ? itemDB : dbHelper
                .getWritableDatabase();
        Uri rssUri = null;
        switch (uriMatcher.match(uri)) {
            case URI_KIND_RSSs:
                if (values != null
                        && values.containsKey(ItemListDBHelper.COL_TITLE)
                        && values.containsKey(ItemListDBHelper.COL_LINK)
                        && values.containsKey(ItemListDBHelper.COL_DESCRIPTION)) {
                    long rowid = itemDB.insert(ItemListDBHelper.TABLE_NAME, null,
                            values);
                    if (rowid > 0) {
                        rssUri = ContentUris.withAppendedId(URI_RSS, rowid);
                        getContext().getContentResolver()
                                .notifyChange(rssUri, null);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return rssUri;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new ItemListDBHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
            String[] selectionArgs, String sortOrder) {
        Cursor c;
        SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
        itemDB = itemDB != null ? itemDB : dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case URI_KIND_RSS_ID:
                qBuilder.setTables(ItemListDBHelper.TABLE_NAME);
                qBuilder.appendWhere(ItemListDBHelper.COL_ID + "="
                        + uri.getLastPathSegment());
                break;
            case URI_KIND_RSSs:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_id ASC";
                }
                qBuilder.setTables(ItemListDBHelper.TABLE_NAME);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        c = qBuilder.query(itemDB, projection, selection, selectionArgs,
                null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where,
            String[] whereArgs) {

        itemDB = itemDB != null ? itemDB : dbHelper.getWritableDatabase();
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_KIND_RSS_ID:
                if (TextUtils.isEmpty(where)) {
                    where = ItemListDBHelper.COL_ID + "=" + uri.getLastPathSegment();
                } else {
                    where = ItemListDBHelper.COL_ID + "=" + uri.getLastPathSegment() + " AND "
                            + where;
                }
                count = itemDB.update(ItemListDBHelper.TABLE_NAME, values,
                        where, whereArgs);
                break;
            case URI_KIND_RSSs:
                count = itemDB.update(ItemListDBHelper.TABLE_NAME, values,
                        where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

}
