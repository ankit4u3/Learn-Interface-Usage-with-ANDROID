package in.emapps.vidur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseConnector {

	// Declare Variables
	private static final String DB_NAME = "MyNotes.db";
	private static final String TABLE_NAME = "tablenotes";
	public static final String TABLE_NAME_DARBARI = "darbari";
	public static final String TABLE_NAME_MEDICA = "medica";
	private static final String TITLE = "title";
	private static final String ID = "_id";
	private static final String NOTE = "note";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase database;
	private DatabaseHelper dbOpenHelper;
	public static final String URL = "url";

	public DatabaseConnector(Context context) {
		dbOpenHelper = new DatabaseHelper(context, DB_NAME, null,
				DATABASE_VERSION);

	}

	// Open Database function
	public void open() throws SQLException {
		// Allow database to be in writable mode
		database = dbOpenHelper.getWritableDatabase();
	}

	// Close Database function
	public void close() {
		if (database != null)
			database.close();
	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	public void InsertNote(String title, String note) {
		ContentValues newCon = new ContentValues();
		newCon.put(TITLE, title);
		newCon.put(NOTE, note);

		open();
		database.insert(TABLE_NAME, null, newCon);

		close();
	}

	public void InsertDarbari(String title, String note) {
		ContentValues newCon = new ContentValues();
		newCon.put(TITLE, title);
		newCon.put(NOTE, note);
		open();
		database.insert(TABLE_NAME_DARBARI, null, newCon);
		close();
	}

	public void InsertMateriaMedica(String title, String note) {
		ContentValues newCon = new ContentValues();
		newCon.put(TITLE, title);
		newCon.put(NOTE, note);
		open();
		database.insert(TABLE_NAME_MEDICA, null, newCon);
		close();
	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// Update Database function
	public void UpdateNote(long id, String title, String note) {
		ContentValues editCon = new ContentValues();
		editCon.put(TITLE, title);
		editCon.put(NOTE, note);

		open();
		database.update(TABLE_NAME, editCon, ID + "=" + id, null);
		close();
	}

	// Delete Database function
	public void DeleteNote(long id) {
		open();
		database.delete(TABLE_NAME, ID + "=" + id, null);

		close();
	}

	public void DeleteALL() {
		open();
		database.delete(TABLE_NAME, null, null);

		close();
	}

	public void Delete() {
		open();
		database.rawQuery("DELETE FROM " + TABLE_NAME, null);
		database.rawQuery("DELETE FROM  " + TABLE_NAME_DARBARI, null);
		database.rawQuery("DELETE FROM  " + TABLE_NAME_MEDICA, null);
		close();
	}

	public void DeleteDuplicate() {
		open();

	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// List all data function
	public Cursor ListAllNotes() {
		return database.query(true, TABLE_NAME,
				new String[] { ID, TITLE, NOTE }, null, null, null, null, TITLE
						+ " DESC", null);
	}

	public Cursor ListAllDarbari() {
		return database.query(TABLE_NAME_DARBARI, new String[] { ID, TITLE },
				null, null, null, null, TITLE + " DESC");
	}

	// public Cursor query (boolean distinct, String table,
	// String[] columns, String selection,
	// String[] selectionArgs, String groupBy,
	// String having, String orderBy, String limit)
	// Parameters
	// distinct true if you want each row to be unique, false otherwise.
	// table The table name to compile the query against.
	// columns A list of which columns to return. Passing null will return all
	// columns, which is discouraged to prevent reading data from storage that
	// isn't going to be used.
	// selection A filter declaring which rows to return, formatted as an SQL
	// WHERE clause (excluding the WHERE itself). Passing null will return all
	// rows for the given table.
	// selectionArgs You may include ?s in selection, which will be replaced by
	// the values from selectionArgs, in order that they appear in the
	// selection. The values will be bound as Strings.
	// groupBy A filter declaring how to group rows, formatted as an SQL GROUP
	// BY clause (excluding the GROUP BY itself). Passing null will cause the
	// rows to not be grouped.
	// having A filter declare which row groups to include in the cursor, if row
	// grouping is being used, formatted as an SQL HAVING clause (excluding the
	// HAVING itself). Passing null will cause all row groups to be included,
	// and is required when row grouping is not being used.
	// orderBy How to order the rows, formatted as an SQL ORDER BY clause
	// (excluding the ORDER BY itself). Passing null will use the default sort
	// order, which may be unordered.
	// limit Limits the number of rows returned by the query, formatted as LIMIT
	// clause. Passing null denotes no LIMIT clause.
	// Returns
	//
	//
	//
	public Cursor ListAllMateriaMedica() {
		return database.query(TABLE_NAME_MEDICA, new String[] { ID, TITLE },
				null, null, null, null, null);
	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

	// Capture single data by ID

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	public Cursor GetOneNote(long id) {
		return database.query(TABLE_NAME, null, ID + "=" + id, null, null,
				null, null);
	}

	public Cursor GetOneDarbari(long id) {
		return database.query(TABLE_NAME_DARBARI, null, ID + "=" + id, null,
				null, null, null);
	}

	public Cursor GetOneMateriaMedica(long id) {
		return database.query(TABLE_NAME_MEDICA, null, ID + "=" + id, null,
				null, null, null);
	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// |||||||||||||||||||||||||||||SEARCH TABLE
	// |||||||||||||||||||||||||||||||||||

	public Cursor SearchMedica(String name) {
		return database.query(TABLE_NAME, new String[] { ID, TITLE }, NOTE
				+ " LIKE " + '"' + "%" + name + "%" + '"', null, null, null,
				null);
	}

	public Cursor SearchDarbari(String name) {
		return database.query(TABLE_NAME_DARBARI, new String[] { ID, TITLE },
				NOTE + " LIKE " + '"' + "%" + name + "%" + '"', null, null,
				null, null);
	}

	public Cursor SearchMateriaMedica(String name) {
		return database.query(TABLE_NAME_MEDICA, new String[] { ID, TITLE },
				NOTE + " LIKE " + '"' + "%" + name + "%" + '"', null, null,
				null, null);
	}

	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	// ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

}
