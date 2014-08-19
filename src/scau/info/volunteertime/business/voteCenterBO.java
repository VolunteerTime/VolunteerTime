/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-16
 */

package scau.info.volunteertime.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import scau.info.volunteertime.vo.VoteData;

/**
 * @author 蔡超敏
 * 
 */
public class voteCenterBO {

	/**
	 * @return
	 */
	public static String VoteCenterAddress = "http://192.168.253.1:8088/VolunteerTimeWeb/VolunteerTimeVoteCenterServlet";

	public ArrayList<VoteData> getVotesData(int lastId) {
		// TODO Auto-generated method stub

		URL url;
		String allData = "";
		try {
			url = new URL(VoteCenterAddress + "?lastId=" + lastId);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String data;

			while ((data = br.readLine()) != null) {
				allData = allData + data;
			}
			br.close();
			con.disconnect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("拿到数据吗" + allData);
		ArrayList<VoteData> list = new ArrayList<VoteData>();
		try {
			JSONArray array = new JSONArray(allData);

			for (int i = 0; i < array.length(); i++) { // 把json数据全部转换成vo数据

				JSONObject jsonObject = array.getJSONObject(i);
				VoteData s = new VoteData();

				if (jsonObject.get("single").equals("1"))
					s.setSingle(true);
				else
					s.setSingle(false);

				s.setTitle((String) jsonObject.get("title"));

				s.setId(Integer.valueOf((String) jsonObject.get("id")));

				String choices = (String) jsonObject.get("choice");
				String[] cho = choices.split("\\|");

				ArrayList<String> choiceList = new ArrayList<String>();
				for (int j = 0; j < cho.length; j++)
					choiceList.add(cho[j]);
				s.setChoice(choiceList);

				String vote = (String) jsonObject.get("vote");

				String[] votes = vote.split("\\|");
				ArrayList<Integer> voteList = new ArrayList<Integer>();
				System.out.println("----  " + vote + "   " + votes.length);
				for (int j = 0; j < votes.length; j++) {
					voteList.add(Integer.valueOf(votes[j]));
				}
				s.setVotes(voteList);

				System.out.println(jsonObject.get("id"));
				System.out.println(jsonObject.get("single"));
				System.out.println(jsonObject.get("title"));
				System.out.println(jsonObject.get("choice"));
				System.out.println(jsonObject.get("vote"));

				list.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("votesCenter的json解析出错");
		}
		return list;
	}

}
