package nz.geek.hurford.listfragdyloading.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class ItemListDBHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "itemlist";
	private static final int DBVERSION = 1;
	
	public static final String TABLE_NAME = DBNAME;
	public static final String COL_ID = "_id";
	public static final String COL_TITLE = "title";
	public static final String COL_LINK = "link";
	public static final String COL_DESCRIPTION = "description";
	
	private static final String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " " +
			"(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					COL_TITLE + " TEXT NOT NULL," +
					COL_LINK + " TEXT NOT NULL," +
					COL_DESCRIPTION + " TEXT NOT NULL);";
	
	public ItemListDBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
