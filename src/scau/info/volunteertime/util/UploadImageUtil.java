/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-25
 */
package scau.info.volunteertime.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * @author 蔡超敏
 * 
 */
public class UploadImageUtil {

	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10 * 10000000; // ��ʱʱ��
	private static final String CHARSET = "utf-8"; // ���ñ���
	public static final String SUCCESS = "1";
	public static final String FAILURE = "0";

	/**
	 * fileΪ�ļ���RequestURLΪ�ϴ���ַ�� ����ֻ���ϴ�һ���ļ�
	 * 
	 * @param file
	 * @param RequestURL
	 * @return
	 */

	public static String uploadFile(File file, String RequestURL) {
		String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			// ���±���ģ���������Ӧ��
			conn.setRequestProperty("connection", "keep-alive"); // �������ӷ�ʽ
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);// ���ô����������ͺͱ߽��־
			if (file != null) {
				OutputStream outputSteam = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""
						+ file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				int res = conn.getResponseCode();
				Log.e(TAG, "response code:" + res);
				if (res == 200) {// ��ȡ��Ӧ�� 200=�ɹ�
					return SUCCESS;
				}
				dos.close();
				outputSteam.close();
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return FAILURE;
	}

	public static int uploadFiles(String userName, int productId,
			String productName, List<String> imageList, int userId,
			String content, String RequestURL) {
		String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			// ���±���ģ���������Ӧ��
			conn.setRequestProperty("connection", "keep-alive"); // �������ӷ�ʽ
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);// ���ô����������ͺͱ߽��־
			if (imageList != null) {
				OutputStream outputSteam = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"productId\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(productId);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"productName\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(productName);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"content\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(content);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"userName\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(userName);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"userId\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(userId);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());

				Iterator<String> iterator = imageList.iterator();
				while (iterator.hasNext()) {
					StringBuffer sb1 = new StringBuffer();
					String imageUrl = (String) iterator.next();
					File file = new File(imageUrl);
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINE_END);
					sb1.append("Content-Disposition: form-data; name=\"img\"; filename=\""
							+ file.getName() + "\"" + LINE_END);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + "\"" + LINE_END);
					sb1.append(LINE_END);
					dos.write(sb1.toString().getBytes());
					InputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					is.close();
					dos.write(LINE_END.getBytes());

				}
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				int res = conn.getResponseCode();
				Log.e(TAG, "response code:" + res);
				if (res == 200)// ��ȡ��Ӧ�� 200=�ɹ�
				{
					System.out.println(UploadImageUtil.inputStreamToString(conn
							.getInputStream()));
					int flag = Integer.parseInt(UploadImageUtil
							.inputStreamToString(conn.getInputStream()));
					return flag;
				}
				dos.close();
				outputSteam.close();
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int uploadCompressedFiles(String userName, int productId,
			String productName, List<String> imageList,
			List<Bitmap> bitmapList, int userId, String content,
			String RequestURL) {
		String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // ��������

		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			// ���±���ģ���������Ӧ��
			conn.setRequestProperty("connection", "keep-alive"); // �������ӷ�ʽ
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);// ���ô����������ͺͱ߽��־
			if (imageList != null) {
				OutputStream outputSteam = conn.getOutputStream();
				DataOutputStream dos = new DataOutputStream(outputSteam);
				StringBuffer sb = new StringBuffer();

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"productId\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(productId);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"productName\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(productName);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"content\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(content);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"userName\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(userName);
				sb.append(LINE_END);

				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"userId\""
						+ LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + "\"" + LINE_END + LINE_END);
				sb.append(userId);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());

				Iterator<String> iterator = imageList.iterator();
				// Iterator bitmapIterator = bitmapList.iterator();
				int bitmapIndex = -1;
				while (iterator.hasNext()) {
					StringBuffer sb1 = new StringBuffer();
					String imageUrl = (String) iterator.next();
					bitmapIndex++;
					// Bitmap bitmap = (Bitmap) bitmapIterator.next();
					File file = new File(imageUrl);
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINE_END);
					sb1.append("Content-Disposition: form-data; name=\"img\"; filename=\""
							+ file.getName() + "\"" + LINE_END);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + "\"" + LINE_END);
					sb1.append(LINE_END);
					dos.write(sb1.toString().getBytes());
					InputStream is = FormatTools.getInstance()
							.Bitmap2InputStream(bitmapList.get(bitmapIndex));
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					is.close();
					dos.write(LINE_END.getBytes());

				}
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				int res = conn.getResponseCode();
				Log.e(TAG, "response code:" + res);
				if (res == 200)// ��ȡ��Ӧ�� 200=�ɹ�
				{
					int flag = Integer.parseInt(UploadImageUtil
							.inputStreamToString(conn.getInputStream()));
					return flag;
				}
				dos.close();
				outputSteam.close();
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String inputStreamToString(InputStream in) throws Exception {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/*
	 * public static int uploadFileTest(CheapCard cheapCard, Bitmap bitmap,
	 * String addMerchantCouponUrl) { String BOUNDARY =
	 * UUID.randomUUID().toString(); // �߽��ʶ ������� String PREFIX = "--",
	 * LINE_END = "\r\n"; String CONTENT_TYPE = "multipart/form-data"; //
	 * ��������
	 * 
	 * try { URL url = new URL(addMerchantCouponUrl); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setReadTimeout(TIME_OUT);
	 * conn.setConnectTimeout(TIME_OUT); conn.setDoInput(true); // ����������
	 * conn.setDoOutput(true); // ��������� conn.setUseCaches(false); //
	 * ������ʹ�û��� conn.setRequestMethod("POST"); // ����ʽ
	 * conn.setRequestProperty("Charset", CHARSET); // ���ñ��� //
	 * ���±���ģ���������Ӧ�� conn.setRequestProperty("connection", "keep-alive");
	 * // �������ӷ�ʽ conn.setRequestProperty("Content-Type", CONTENT_TYPE +
	 * ";boundary=" + BOUNDARY);// ���ô����������ͺͱ߽��־ if
	 * (cheapCard.getChca_pic() != null) { OutputStream outputSteam =
	 * conn.getOutputStream(); DataOutputStream dos = new
	 * DataOutputStream(outputSteam); StringBuffer sb = new StringBuffer();
	 * 
	 * int chca_id = cheapCard.getChca_id(); if (chca_id > 0) {
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"chca_id\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(chca_id); sb.append(LINE_END); }
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"merc_id\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getMerc_id());
	 * sb.append(LINE_END);
	 * 
	 * String store_name = cheapCard.getStore_name(); if (store_name != null) {
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"store_name\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END); sb.append(store_name);
	 * sb.append(LINE_END); }
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"title\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getChca_title());
	 * sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"content\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getContent());
	 * sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"start_time\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getChca_time()); sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"end_time\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getEnd_time()); sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"is_can_search\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getIs_can_search()); sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"chca_url\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getChca_url()); sb.append(LINE_END);
	 * 
	 * dos.write(sb.toString().getBytes());
	 * 
	 * StringBuffer sb1 = new StringBuffer(); String imageUrl =
	 * cheapCard.getChca_pic(); File file = new File(imageUrl);
	 * sb1.append(PREFIX); sb1.append(BOUNDARY); sb1.append(LINE_END);
	 * sb1.append("Content-Disposition: form-data; name=\"img\"; filename=\"" +
	 * file.getName() + "\"" + LINE_END);
	 * sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END); sb1.append(LINE_END);
	 * 
	 * dos.write(sb1.toString().getBytes()); InputStream is =
	 * FormatTools.getInstance().Bitmap2InputStream( bitmap); byte[] bytes = new
	 * byte[1024]; int len = 0; while ((len = is.read(bytes)) != -1) {
	 * dos.write(bytes, 0, len); } is.close(); dos.write(LINE_END.getBytes());
	 * 
	 * byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END) .getBytes();
	 * dos.write(end_data); dos.flush(); int res = conn.getResponseCode();
	 * Log.e(TAG, "response code:" + res);
	 * 
	 * dos.close(); outputSteam.close(); conn.disconnect();
	 * 
	 * if (res == 200)// ��ȡ��Ӧ�� 200=�ɹ� { return 1; }
	 * 
	 * } } catch (MalformedURLException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } return 0; }
	 */
	/*
	 * public static int uploadFile(CheapCard cheapCard, String
	 * addMerchantCouponUrl) { String BOUNDARY = UUID.randomUUID().toString();
	 * // �߽��ʶ ������� String PREFIX = "--", LINE_END = "\r\n"; String
	 * CONTENT_TYPE = "multipart/form-data"; // ��������
	 * 
	 * try { URL url = new URL(addMerchantCouponUrl); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setReadTimeout(TIME_OUT);
	 * conn.setConnectTimeout(TIME_OUT); conn.setDoInput(true); // ����������
	 * conn.setDoOutput(true); // ��������� conn.setUseCaches(false); //
	 * ������ʹ�û��� conn.setRequestMethod("POST"); // ����ʽ
	 * conn.setRequestProperty("Charset", CHARSET); // ���ñ��� //
	 * ���±���ģ���������Ӧ�� conn.setRequestProperty("connection", "keep-alive");
	 * // �������ӷ�ʽ conn.setRequestProperty("Content-Type", CONTENT_TYPE +
	 * ";boundary=" + BOUNDARY);// ���ô����������ͺͱ߽��־ if
	 * (cheapCard.getChca_pic() != null) { OutputStream outputSteam =
	 * conn.getOutputStream(); DataOutputStream dos = new
	 * DataOutputStream(outputSteam); StringBuffer sb = new StringBuffer();
	 * 
	 * int chca_id = cheapCard.getChca_id(); if (chca_id > 0) {
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"chca_id\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(chca_id); sb.append(LINE_END); }
	 * 
	 * int merc_id = cheapCard.getMerc_id(); if (merc_id > 0) {
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"merc_id\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getMerc_id());
	 * sb.append(LINE_END); }
	 * 
	 * String store_name = cheapCard.getStore_name(); if (store_name != null) {
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"store_name\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END); sb.append(store_name);
	 * sb.append(LINE_END); }
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"title\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getChca_title());
	 * sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"content\"" + LINE_END);
	 * sb.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END + LINE_END); sb.append(cheapCard.getContent());
	 * sb.append(LINE_END);
	 * 
	 * String start_time = cheapCard.getChca_time(); if (start_time != null &&
	 * !start_time.equals("")) { sb.append(PREFIX); sb.append(BOUNDARY);
	 * sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"start_time\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getChca_time()); sb.append(LINE_END); }
	 * 
	 * String end_time = cheapCard.getEnd_time(); if (start_time != null &&
	 * !start_time.equals("")) { sb.append(PREFIX); sb.append(BOUNDARY);
	 * sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"end_time\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END); sb.append(end_time);
	 * sb.append(LINE_END); }
	 * 
	 * // sb.append(PREFIX); // sb.append(BOUNDARY); // sb.append(LINE_END); //
	 * sb.append("Content-Disposition: form-data; name=\"is_can_search\"" // +
	 * LINE_END); //
	 * sb.append("Content-Type: application/octet-stream; charset=" // + CHARSET
	 * + "\"" + LINE_END // + LINE_END); //
	 * sb.append(cheapCard.getIs_can_search()); // sb.append(LINE_END);
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"chca_url\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getChca_url()); sb.append(LINE_END);
	 * 
	 * String imageUrl = cheapCard.getChca_pic();
	 * 
	 * if (imageUrl.contains("http://")) {
	 * 
	 * sb.append(PREFIX); sb.append(BOUNDARY); sb.append(LINE_END);
	 * sb.append("Content-Disposition: form-data; name=\"chca_pic\"" +
	 * LINE_END); sb.append("Content-Type: application/octet-stream; charset=" +
	 * CHARSET + "\"" + LINE_END + LINE_END);
	 * sb.append(cheapCard.getChca_pic()); sb.append(LINE_END);
	 * dos.write(sb.toString().getBytes());
	 * 
	 * } else {
	 * 
	 * dos.write(sb.toString().getBytes());
	 * 
	 * StringBuffer sb1 = new StringBuffer();
	 * 
	 * File file = new File(imageUrl); sb1.append(PREFIX); sb1.append(BOUNDARY);
	 * sb1.append(LINE_END);
	 * sb1.append("Content-Disposition: form-data; name=\"img\"; filename=\"" +
	 * file.getName() + "\"" + LINE_END);
	 * sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET +
	 * "\"" + LINE_END); sb1.append(LINE_END);
	 * dos.write(sb1.toString().getBytes()); InputStream is = new
	 * FileInputStream(file); byte[] bytes = new byte[1024]; int len = 0; while
	 * ((len = is.read(bytes)) != -1) { dos.write(bytes, 0, len); } is.close();
	 * 
	 * }
	 * 
	 * dos.write(LINE_END.getBytes());
	 * 
	 * byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END) .getBytes();
	 * dos.write(end_data); dos.flush(); int res = conn.getResponseCode();
	 * Log.e(TAG, "response code:" + res);
	 * 
	 * dos.close(); outputSteam.close(); conn.disconnect();
	 * 
	 * if (res == 200)// ��ȡ��Ӧ�� 200=�ɹ� { return 1; } } } catch
	 * (MalformedURLException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
	 * return 0; }
	 */

}
