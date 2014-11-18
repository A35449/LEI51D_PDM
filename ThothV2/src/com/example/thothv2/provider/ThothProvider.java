package com.example.thothv2.provider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.example.thothv2.provider.ProviderContract;

public class ThothProvider extends ProviderContract{

	//public static  UriMatcher _urimatcherClass;
	//public static  UriMatcher _urimatcherNews;

	private static ClassesProvider _classProvider;
	private static NewsProvider _newsProvider;
	
	public static UriMatcher _urimatcher;
	public static SQLRunner _sql;
	

	//public final static int ROOT_NEWS  = 10;
		
	static {
		
		//_urimatcher = new UriMatcher(ROOT_NEWS);
		_classProvider = new ClassesProvider();
		_newsProvider = new NewsProvider();
		
		_urimatcher = new UriMatcher(ROOT);
		_urimatcher.addURI(AUTHORITY, "classes", CLASSES);
		_urimatcher.addURI(AUTHORITY, "classes/selected", SELECTED);
		_urimatcher.addURI(AUTHORITY,"news",NEWS);
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		int uriMatchResult = _urimatcher.match(uri);
		
		switch(uriMatchResult){
			case NEWS:
				return _newsProvider.query(uri, projection, selection, selectionArgs, sortOrder);
			
			case CLASSES:
				return _classProvider.query(uri, projection, selection, selectionArgs, sortOrder);
				
			default:
				 throw new IllegalArgumentException("Unknown Uri "+uri);
		}
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriMatchResult = _urimatcher.match(uri);
		
		switch(uriMatchResult){
			case NEWS:
				return _newsProvider.insert(uri, values);
			
			case CLASSES:
				return _classProvider.insert(uri,values);
				
			default:
				 throw new IllegalArgumentException("Unknown Uri "+uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriMatchResult = _urimatcher.match(uri);
		
		switch(uriMatchResult){
		case NEWS:
				return _newsProvider.delete(uri, selection, selectionArgs);
		case CLASSES:
				return _classProvider.delete(uri,selection,selectionArgs);
			default:
				 throw new IllegalArgumentException("Unknown Uri "+uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int uriMatchResult = _urimatcher.match(uri);
		
		switch(uriMatchResult){
			case NEWS:
				return _newsProvider.update(uri, values, selection, selectionArgs);
			case CLASSES:
				return _classProvider.update(uri, values, selection, selectionArgs);
			default:
				 throw new IllegalArgumentException("Unknown Uri "+uri);
		}
	}
	
	@Override
	public boolean onCreate() {	
		if(_sql != null) _sql.close();
		_sql = new SQLRunner(getContext());
		return true;
	}

}
