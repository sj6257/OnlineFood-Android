package com.teamXDev.onlineordering;

import java.io.IOException;
import java.util.ArrayList;

//import android.content.ContentValues; //Need While inserting values
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class OnlineOrderingDataBaseAdapter {

	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private OnlineOrderingDataBaseOpenHelper mDbHelper;

	// Constructor for OnlineOrderingDataBaseAdapter

	public OnlineOrderingDataBaseAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new OnlineOrderingDataBaseOpenHelper(mContext);
	}

	public OnlineOrderingDataBaseAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public OnlineOrderingDataBaseAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			Log.i(TAG, "Asking for Readable Acess to DB");
			mDb = mDbHelper.getReadableDatabase();
			Log.i(TAG, "Readable Access Granted");
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// custom operations funtions

	public ArrayList<ArrayOrderItemTemplate> getOrderItemIfo(String sql) {
		ArrayList<ArrayOrderItemTemplate> result = new ArrayList<ArrayOrderItemTemplate>();

		Log.i(TAG, "Getting Information..");
		Cursor mCur = null;
		try {

			Log.i(TAG, "Executing Query: " + sql);
			mCur = mDb.rawQuery(sql, null);
			Log.i(TAG, "Query Executed Successfully");
			if (mCur != null) {
				if (mCur.moveToFirst()) {

					do {

						String itemName = mCur.getString(mCur
								.getColumnIndex("item_name"));
						String itemServingSize = mCur.getString(mCur
								.getColumnIndex("item_serving_size"));
						double itemUnitCost = mCur.getFloat(mCur
								.getColumnIndex("item_unit_cost"));
						double itemTotalCost = mCur.getFloat(mCur
								.getColumnIndex("item_total_cost"));
						int itemQty = mCur.getInt(mCur
								.getColumnIndex("item_qty"));

						ArrayOrderItemTemplate b1 = new ArrayOrderItemTemplate(
								itemName, itemServingSize, itemUnitCost,
								itemTotalCost, itemQty);
						result.add(b1);

					} while (mCur.moveToNext());

					mCur.close();
				} else {
					mCur.close();
					Log.e(TAG, "No Data Received from Query");
				}
			}

		} catch (Exception e) {
			Log.e(TAG, "getOrderItemIfo >>" + e.toString());
			// throw mSQLException;
		}
		return result;

	}

	public void applyTaxesFees(double tax) {

		String sql = "select sub_total_amount from order_summary";
		Cursor mCur = null;
		try {

			Log.i(TAG, "Executing Query: " + sql);
			mCur = mDb.rawQuery(sql, null);
			Log.i(TAG, "Query Executed Successfully");
			if (mCur != null) {
				if (mCur.moveToFirst()) {

					do {
						double sub_total_amount = mCur.getFloat(mCur
								.getColumnIndex("sub_total_amount"));
						double netTaxAmmount = (tax / 100) * sub_total_amount;
						updateTaxesFees(netTaxAmmount);

					} while (mCur.moveToNext());

					mCur.close();
				} else {
					mCur.close();
					Log.e(TAG, "No Data Received from Query");
				}
			}

		} catch (Exception e) {
			Log.e(TAG, "Exception While updating tax >>" + e.toString());
			// throw mSQLException;
		}

	}

	public void updateTaxesFees(double netTaxAmmount) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("taxes_fees_amount", netTaxAmmount);
			mDb.update("order_summary", cv, null, null);
			Log.d("updateTaxesFees", "Tax saved");

		} catch (Exception ex) {
			Log.d("updateTaxesFees", ex.toString());

		}
	}

	//
	public void applyCoupneCodeInDb(double coupneCodeAmount) {
		try {
			ContentValues cv = new ContentValues();
			cv.put("coupne_code_deduction", coupneCodeAmount);
			mDb.update("order_summary", cv, null, null);
			Log.d("applyCoupneCode", "CoupneCodeAmount Applied");

		} catch (Exception ex) {
			Log.d("applyCoupneCode", ex.toString());

		}
	}
	

	public double getDoubleValue(String sql)
	{
		double columnVal = 0;
		Log.i(TAG,"Getting Information..");
		Cursor mCur=null;
		try 
		{ 

			Log.i(TAG,"Executing Query : "+sql);
			mCur = mDb.rawQuery(sql, null);
			Log.i(TAG,"Query Executed Successfully");
			if(mCur!=null)
			{
				if  (mCur.moveToFirst()) 
				{

					do {

						columnVal= mCur.getDouble(0);

					}while (mCur.moveToNext());

					mCur.close();
				}
				else
				{
					mCur.close();
					Log.e(TAG,"No Data Received from Query");
				}
			}

		}
		catch (Exception e)  
		{ 
			Log.e(TAG, "getDoubleValue >>"+ e.toString()); 
			// throw mSQLException; 
		} 
		return columnVal;

	}
	
	
	public void addItemToOrder(String orderID,String itemName,String itemServingSize,int itemQty,double itemUnitCost)
	{
		try {
			ContentValues cv = new ContentValues();
			cv.put("order_id",orderID );
			cv.put("item_name",itemName );
			cv.put("item_serving_size",itemServingSize );
			cv.put("item_qty",itemQty );
			cv.put("item_unit_cost",itemUnitCost );
			
			mDb.insert("order_details", null, cv);
			Log.d("addItemToOrder", "Item Added");

		} catch (Exception ex) {
			Log.d("updateTaxesFees", ex.toString());

		}

     }
	
   public void emptyCart()
   {
		String sql1 = "delete from order_summary";
		String sql2 = "delete from order_details";
		try {

			Log.i(TAG, "Executing Query: " + sql1);
			mDb.execSQL(sql1);
			
			Log.i(TAG, "Query Executed Successfully");
			Log.i(TAG, "Executing Query: " + sql2);
			mDb.execSQL(sql2);
				Log.i(TAG, "Query Executed Successfully");
			

		} catch (Exception e) {
			Log.e(TAG, "Exception While trying to Empty Cart >>" + e.toString());
			// throw mSQLException;
		}
   }
 
}
