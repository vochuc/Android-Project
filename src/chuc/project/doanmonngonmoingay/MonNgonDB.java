package chuc.project.doanmonngonmoingay;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MonNgonDB {
	// database constants
	
    public static final String DB_NAME = "mon_ngon.db";
    
    public static final int    DB_VERSION = 1;
    // mon_ngon_detail table constants
    public static final String MONAN_TABLE = "monan_detail";
    public static final String MONAN_ID = "monan_detail_id";
    public static final int    MONAN_ID_COL = 0;
    
    public static final String MONAN_CATALOG_ID = "monan_catalog_id";
    public static final int    MONAN_CATALOG_ID_COL = 1;
    
    public static final String MONAN_TITLE_ID = "title";
    public static final int    MONAN_TITLE_ID_COL = 2;
    
    public static final String MONAN_DESC_ID = "desc";
    public static final int    MONAN_DESC_ID_COL = 3;
    
    public static final String MONAN_CONTENT_ID = "content";
    public static final int    MONAN_CONTENT_ID_COL = 4;
    
    public static final String MONAN_TIPS_ID = "tips";
    public static final int    MONAN_TIPS_ID_COL = 5;
    
    public static final String MONAN_MATERIALS_ID = "materials";
    public static final int    MONAN_MATERIALS_ID_COL = 6;
    
    public static final String MONAN_PATH_VIDEO_ID = "path_video";
    public static final int    MONAN_PATH_VIDEO_ID_COL = 7;
    
    public static final String MONAN_PATH_IMAGE_ID = "path_video";
    public static final int    MONAN_PATH_IMAGE_ID_COL = 8;
    
    public static final String MONAN_FAVORITE_ID = "Field10";
    public static final int    MONAN_FAVORITE_ID_COL = 9;
    
    public static final String YEUTHICH_TABLE = "monan_yeuthich";
    
    public static final String YEUTHICH_ID = "id";
    public static final int    YEUTHICH_ID_COL = 0;
    
    public static final String YEUTHICH_TITLE_ID = "title";
    public static final int    YEUTHICH_TITLE_ID_COL = 1;
    
    public static final String YEUTHICH_PATH_IMAGE_ID = "path_image";
    public static final int    YEUTHICH_PATH_IMAGE_ID_COL = 2;
    
    
    
    public static final String CREATE_YEUTHICH_TABLE = 
    		"CREATE TABLE monan_yeuthich (" +
    		" id INTEGER," +
    		" title TEXT," +
    		" path_image TEXT," +
    		" PRIMARY KEY (id));";
    
    public static final String CREATE_MONAN_TABLE = 
    		"CREATE TABLE monan_detail (" +
    		" monan_detail_id int(11) NOT NULL," +
    		" monan_catalog_id int(11) NOT NULL DEFAULT '1'," +
    		" title varchar(250) NOT NULL," +
    		" desc varchar(500) NOT NULL," +
    		" content varchar(2048) NOT NULL," +
    		" tips varchar(1024) NOT NULL," +
    		" materials varchar(1024) NOT NULL," +
    		" path_video varchar(250) NOT NULL," +
    		" path_image varchar(250) NOT NULL," +
    		" PRIMARY KEY (monan_detail_id));";

    private static class DBHelper extends SQLiteOpenHelper {
    	private static final String DB_DIR = "/data/data/chuc.project.doanmonngonmoingay/databases/";
    	private static String DB_PATH = DB_DIR + DB_NAME;
        private static String OLD_DB_PATH = DB_DIR + "old_" + DB_NAME;
        
    	private final Context myContext;

    	private boolean createDatabase = false;
        private boolean upgradeDatabase = false;
        
        public DBHelper(Context context, String name, 
                CursorFactory factory, int version) {
            super(context, name, factory, version);
            myContext = context;
            DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        }

        public DBHelper(Context context) {
            super(context, DB_NAME, null, context.getResources().getInteger(
                    R.string.databaseVersion));
            myContext = context;
            // Get the path of the database that is based on the context.
            DB_PATH = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        }
        
        public void initializeDataBase() {
        	SQLiteDatabase new_db = null;
            getWritableDatabase();

            if (createDatabase) {
                try {
                    copyDataBase();
                //    new_db = SQLiteDatabase.openDatabase(DB_PATH,null, SQLiteDatabase.OPEN_READWRITE);
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            } else if (upgradeDatabase) {
                try {
                    FileHelper.copyFile(DB_PATH, OLD_DB_PATH);
                    copyDataBase();
                    SQLiteDatabase old_db = SQLiteDatabase.openDatabase(OLD_DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
                    new_db = SQLiteDatabase.openDatabase(DB_PATH,null, SQLiteDatabase.OPEN_READWRITE);
                    /*
                     * Add code to load data into the new database from the old
                     * database and then delete the old database from internal
                     * storage after all data has been transferred.
                     */
                } catch (IOException e) {
                    throw new Error("Error copying database");
                }
            }
           // new_db.execSQL(MonNgonDB.CREATE_YEUTHICH_TABLE);

        }
        
        private void copyDataBase() throws IOException {
            close();
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            OutputStream myOutput = new FileOutputStream(DB_PATH);
            FileHelper.copyFile(myInput, myOutput);

            getWritableDatabase().close();
        }
        
        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            // db.execSQL(CREATE_MONAN_TABLE);
        	createDatabase = true;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, 
                int oldVersion, int newVersion) {

            Log.d("NewsRssReader", "Upgrading db from version " 
                    + oldVersion + " to " + newVersion);
            
//            db.execSQL(MonNgonDB.CREATE_MONAN_TABLE);
//            onCreate(db);
            upgradeDatabase = true;
        }
        
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
    }
    
 // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private Context context;
    
    
    // constructor
    public MonNgonDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        dbHelper.initializeDataBase();
    }
    
    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }
    
    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }
    
    private void closeDB() {
        if (db != null)
            db.close();
    }
    
    public ArrayList<MonNgonModel> getLists() {
        ArrayList<MonNgonModel> lists = new ArrayList<MonNgonModel>();
        openReadableDB();
        Cursor cursor = db.query(MONAN_TABLE, 
                null, null, null, null, null, MONAN_ID + " desc");
        while (cursor.moveToNext()) {
        	MonNgonModel monan = new MonNgonModel();
        	monan.setId(cursor.getInt(MONAN_ID_COL));
        	monan.setCatalog_id(cursor.getInt(MONAN_CATALOG_ID_COL));
        	monan.setImage(cursor.getString(MONAN_PATH_IMAGE_ID_COL));
        	monan.setTitle(cursor.getString(MONAN_TITLE_ID_COL));
        	monan.setDescription(cursor.getString(MONAN_DESC_ID_COL));
        	monan.setNguyenlieu(cursor.getString(MONAN_MATERIALS_ID_COL));
        	monan.setContent(cursor.getString(MONAN_CONTENT_ID_COL));
        	monan.setVideo(cursor.getString(MONAN_PATH_VIDEO_ID_COL));
        	monan.setTips(cursor.getString(MONAN_TIPS_ID_COL));
        	monan.setFavorite(cursor.getInt(MONAN_FAVORITE_ID_COL));
            lists.add(monan);
        }
        if (cursor != null)
            cursor.close();
        closeDB();
        
        return lists;
    }
    public ArrayList<MonNgonModel> getLists(String catalogId) {
        ArrayList<MonNgonModel> lists = new ArrayList<MonNgonModel>();
        openReadableDB();
        String where = MONAN_CATALOG_ID + "=?";
        String []whereArgs = {catalogId};
        Cursor cursor = db.query(MONAN_TABLE, 
                null, where, whereArgs, null, null, MONAN_ID + " desc");
        while (cursor.moveToNext()) {
        	MonNgonModel monan = new MonNgonModel();
        	monan.setId(cursor.getInt(MONAN_ID_COL));
        	monan.setCatalog_id(cursor.getInt(MONAN_CATALOG_ID_COL));
        	monan.setImage(cursor.getString(MONAN_PATH_IMAGE_ID_COL));
        	monan.setTitle(cursor.getString(MONAN_TITLE_ID_COL));
        	monan.setDescription(cursor.getString(MONAN_DESC_ID_COL));
        	monan.setNguyenlieu(cursor.getString(MONAN_MATERIALS_ID_COL));
        	monan.setContent(cursor.getString(MONAN_CONTENT_ID_COL));
        	monan.setVideo(cursor.getString(MONAN_PATH_VIDEO_ID_COL));
        	monan.setTips(cursor.getString(MONAN_TIPS_ID_COL));
        	monan.setFavorite(cursor.getInt(MONAN_FAVORITE_ID_COL));
            lists.add(monan);
        }
        if (cursor != null)
            cursor.close();
        closeDB();
        
        return lists;
    }
    public MonNgonModel getMonAnById(String id) {
        MonNgonModel monan = new MonNgonModel();
        openReadableDB();
        String where = MONAN_ID + "=?";
        String []whereArgs = {id};
        Cursor cursor = db.query(MONAN_TABLE, 
                null, where, whereArgs, null, null, MONAN_ID + " desc");
        while (cursor.moveToNext()) {
        	//MonNgonModel monan = new MonNgonModel();
        	monan.setId(cursor.getInt(MONAN_ID_COL));
        	monan.setCatalog_id(cursor.getInt(MONAN_CATALOG_ID_COL));
        	monan.setImage(cursor.getString(MONAN_PATH_IMAGE_ID_COL));
        	monan.setTitle(cursor.getString(MONAN_TITLE_ID_COL));
        	monan.setDescription(cursor.getString(MONAN_DESC_ID_COL));
        	monan.setNguyenlieu(cursor.getString(MONAN_MATERIALS_ID_COL));
        	monan.setContent(cursor.getString(MONAN_CONTENT_ID_COL));
        	monan.setVideo(cursor.getString(MONAN_PATH_VIDEO_ID_COL));
        	monan.setTips(cursor.getString(MONAN_TIPS_ID_COL));
        	monan.setFavorite(cursor.getInt(MONAN_FAVORITE_ID_COL));
            //lists.add(monan);
        }
        if (cursor != null)
            cursor.close();
        closeDB();
        
        return monan;
    }
    public MonNgonModel getMonAnByTitile(String title) {
        MonNgonModel monan = new MonNgonModel();
        openReadableDB();
        String where = MONAN_TITLE_ID + "=?";
        String []whereArgs = {title};
        Cursor cursor = db.query(MONAN_TABLE, 
                null, where, whereArgs, null, null, MONAN_ID + " desc");
        while (cursor.moveToNext()) {
        	monan.setId(cursor.getInt(MONAN_ID_COL));
        	monan.setCatalog_id(cursor.getInt(MONAN_CATALOG_ID_COL));
        	monan.setImage(cursor.getString(MONAN_PATH_IMAGE_ID_COL));
        	monan.setTitle(cursor.getString(MONAN_TITLE_ID_COL));
        	monan.setDescription(cursor.getString(MONAN_DESC_ID_COL));
        	monan.setNguyenlieu(cursor.getString(MONAN_MATERIALS_ID_COL));
        	monan.setContent(cursor.getString(MONAN_CONTENT_ID_COL));
        	monan.setVideo(cursor.getString(MONAN_PATH_VIDEO_ID_COL));
        	monan.setTips(cursor.getString(MONAN_TIPS_ID_COL));
        	monan.setFavorite(cursor.getInt(MONAN_FAVORITE_ID_COL));
        }
        if (cursor != null)
            cursor.close();
        closeDB();
        
        return monan;
    }
    public int updateMonNgon(MonNgonModel monngon){
    	ContentValues cv = new ContentValues();
    	cv.put(MONAN_ID, monngon.getId());
    	cv.put(MONAN_CATALOG_ID, monngon.getCatalog_id());
    	cv.put(MONAN_PATH_IMAGE_ID, monngon.getImage());
    	cv.put(MONAN_TITLE_ID, monngon.getTitle());
    	cv.put(MONAN_DESC_ID, monngon.getDescription());
    	cv.put(MONAN_MATERIALS_ID, monngon.getNguyenlieu());
    	cv.put(MONAN_CONTENT_ID, monngon.getContent());
    	cv.put(MONAN_PATH_VIDEO_ID, monngon.getVideo());
    	cv.put(MONAN_TIPS_ID, monngon.getTips());
    	cv.put(MONAN_FAVORITE_ID, monngon.getFavorite());
    	
    	String where = MONAN_ID + "= ?";
    	String []whereArg = { String.valueOf(monngon.getId()) };
    	this.openWriteableDB();
    	int rowCount = db.update(MONAN_TABLE, cv, where, whereArg);
    	this.closeDB();
    	
    	
    	return rowCount;
    }
    public ArrayList<YeuThichModel> getListsFavorite() {
        ArrayList<YeuThichModel> lists = new ArrayList<YeuThichModel>();
        openReadableDB();
        Cursor cursor = db.query(YEUTHICH_TABLE, 
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
        	YeuThichModel monan = new YeuThichModel();
        	monan.setId(cursor.getInt(YEUTHICH_ID_COL));
        	monan.setTitle(cursor.getString(YEUTHICH_TITLE_ID_COL));
        	monan.setImage(cursor.getString(YEUTHICH_PATH_IMAGE_ID_COL));
            lists.add(monan);
        }
        if (cursor != null)
            cursor.close();
        closeDB();
        
        return lists;
    }
    public long insertToFavorite(YeuThichModel yeuthich)
    {
        ContentValues cv = new ContentValues();
        cv.put(YEUTHICH_ID,yeuthich.getId());
        cv.put(YEUTHICH_TITLE_ID, yeuthich.getTitle());
        cv.put(YEUTHICH_PATH_IMAGE_ID, yeuthich.getImage());
        this.openWriteableDB();
        long rowID = db.insert(YEUTHICH_TABLE, null, cv);
        this.closeDB();
        return rowID ;
    }
    public int deleteFavorite(int id){
    	String where = YEUTHICH_ID + "= ?";
    	String []whereArg = {String.valueOf(id)};
    	this.openWriteableDB();
    	int rowCout = db.delete(YEUTHICH_TABLE, where, whereArg);
    	this.closeDB();
    	return rowCout;
    }
    public int deleteAll(){
    	this.openWriteableDB();
    	int row = db.delete(YEUTHICH_TABLE, null, null);
    	this.closeDB();
    	return row;
    }
    
}
