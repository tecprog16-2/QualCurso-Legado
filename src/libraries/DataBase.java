package libraries;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBase extends SQLiteAssetHelper{

	private static final String DATABASE_NAME = "database.sqlite3.db";
	private static final int DATABASE_VERSION = 1;
	
	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
}
