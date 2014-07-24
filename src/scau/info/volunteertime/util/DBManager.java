/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-24
 */
package scau.info.volunteertime.util;

import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.vo.Result;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author 蔡超敏
 * 
 */
public class DBManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	/**
	 * add Results
	 * 
	 * @param Results
	 */
	public void add(List<Result> results) {
		/*
		 * "CREATE TABLE IF NOT EXISTS Result" +
		 * "(id INTEGER PRIMARY KEY, title VARCHAR," +
		 * " image VARCHAR, content TEXT, editor VARCHAR , publishTime Date )");
		 */
		db.beginTransaction(); // 开始事务
		try {
			for (Result result : results) {
				db.execSQL(
						"INSERT INTO result(id, title, image, content, editor, publishTime) VALUES( ?, ?, ?, ?, ?, ?)",
						new Object[] { result.getId(), result.getTitle(),
								result.getImage(), result.getContent(),
								result.getEditor(), result.getPublishTime() });
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * update
	 * 
	 * @param result
	 */
	public void updateResult(Result result) {
		ContentValues cv = new ContentValues();
		cv.put("title", result.getTitle());
		cv.put("image", result.getImage());
		cv.put("content", result.getContent());
		cv.put("editor", result.getEditor());
		cv.put("publishTime", result.getPublishTime());
		db.update("result", cv, "id = ?",
				new String[] { String.valueOf(result.getId()) });
	}

	/**
	 * delete old result
	 * 
	 * @param result
	 */
	public void deleteOldResult(int id) {
		db.delete("result", "id = ?", new String[] { String.valueOf(id) });
	}

	/**
	 * query all results, return list
	 * 
	 * @return List<result>
	 */
	public List<Result> query() {
		ArrayList<Result> Results = new ArrayList<Result>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			Result result = new Result(c.getInt(c.getColumnIndex("id")),
					c.getString(c.getColumnIndex("title")), c.getString(c
							.getColumnIndex("content")), c.getString(c
							.getColumnIndex("image")), c.getString(c
							.getColumnIndex("editor")), c.getString(c
							.getColumnIndex("publishTime")));
			Results.add(result);
		}
		c.close();
		return Results;
	}

	/**
	 * query all results, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor() {
		Cursor c = db.rawQuery("SELECT * FROM result", null);
		return c;
	}

	/**
	 * close database
	 */
	public void closeDB() {
		db.close();
	}
}
