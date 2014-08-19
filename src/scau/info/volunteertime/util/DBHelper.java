/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-24
 */
package scau.info.volunteertime.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHelper�̳���SQLiteOpenHelper����Ϊά���͹������ݿ�Ļ���
 * Ŀǰֻ��Result��������Ҫ�ӱ��밴�չ淶
 * 
 * @author 蔡超敏
 * 
 */
public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "scau_volunteertime.db";// ���ݿ���
	private static final int DATABASE_VERSION = 1;// �汾��

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * @param context
	 */
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS Result"
				+ "(id INTEGER PRIMARY KEY, title VARCHAR,"
				+ " image VARCHAR, content TEXT, editor VARCHAR , publishTime VARCHAR )");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("ALTER TABLE Result ADD COLUMN other STRING");
	}

}
