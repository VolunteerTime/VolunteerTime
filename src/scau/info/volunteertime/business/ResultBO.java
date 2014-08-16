/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-23
 */
package scau.info.volunteertime.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.vo.Result;
import android.util.Log;
import cn.trinea.android.common.util.HttpUtils;

/**
 * Results��BO
 * 
 * @author �̳���
 * 
 */
public class ResultBO {

	/**
	 * ���췽������ʼ����̬����
	 */
	public ResultBO() {
		List<Result> allResults = new ArrayList<Result>();
		for (int i = 0; i < 30; i++) {// ��������30��result��list
			allResults
					.add(new Result(
							i,
							"����title" + i,
							"�������ݣ�ʲô���������Ҳ���д�ܶණ���ģ�����ǵ�" + i + "������",
							"http://img.hb.aicdn.com/fda4bb25fc546ed22c9ff137ac72b7ba27e62e9916225-2ywtVp_fw658",
							"chaoKing" + i, new Date(i * 100000).toGMTString()));
		}
	}

	/**
	 * ���ص�pageҳ������
	 * 
	 * @param page
	 * @return Pagination<Result>
	 */
	public ArrayList<Result> getDownData(int page) {

		return null;
	}

	public ArrayList<Result> getDownData(Date date, int currentPageSize) {

		return null;
	}

	/**
	 * @param firstId
	 * @param endId
	 * @return
	 */
	public ArrayList<Integer> update(int firstId, int endId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ��һ�θ������ݣ����µ�����ǰ��currentPageSize������
	 * 
	 * @param currentPageSize
	 * @return ArrayList<Result>
	 */
	public ArrayList<Result> getNewData(int currentPageSize) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("action_type", 0 + "");
		maps.put("currentPageSize", currentPageSize + "");

		String jsonStr = HttpUtils.httpPostString(
				BOConstant.GET_NEW_RESULTS_DATA_URL, maps);

		Log.d("getMessageByUserId", "��ȡ��Ϣ�б�" + currentPageSize + ":" + jsonStr);

		return jsonToList(jsonStr);

	}

	/**
	 * @param jsonStr
	 * @return ArrayList<Result>
	 */
	private ArrayList<Result> jsonToList(String jsonStr) {
		JSONArray jaryProducts = null;
		int pageSize = 10;
		try {
			JSONObject jsonResults = new JSONObject(jsonStr); // �����ַ���ת��JSONObject����
			jaryProducts = jsonResults.getJSONArray("records");
			pageSize = jsonResults.getInt("pageSize"); // �õ�pagesize��ֵ

			Log.d("ResultBO-jsonToPagination", "pageSize = " + pageSize);

			ArrayList<Result> list = new ArrayList<Result>(); // �õ�Result��List
			JSONObject jsonResult = null; // ���ڱ���CheapCard��json����
			Result result;

			for (int i = 0; i < jaryProducts.length(); i++) {
				jsonResult = jaryProducts.getJSONObject(i);
				result = new Result(jsonResult.getInt("id"));
				result.setTitle(jsonResult.getString("title"));
				result.setContent(jsonResult.getString("content"));
				list.add(result);
			}

			return list;
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("debug", "���������ط�json");
		}
		return null;
	}
}
