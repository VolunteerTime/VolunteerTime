/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.business;

/**
 * @author �̳���
 * 
 */
public class BOConstant {
	// ��֤�û�����ֵ
	// ����0����½��ȷ������id 0���˺�����Ϊ�� -1�����벻��ȷ -2���˺Ų�����
	public static final int REP_VAR_PARA_ERR = 0;// �ʺ�����Ϊ��
	public static final int PASSWORD_ERROR = -1;// ���벻�Ϸ�
	public static final int USER_NOT_EXIST = -2;// �û�������
	public static final int USER_NOT_ACTIVATED = -3;// �û�δ����
	public static final int USER_REGISTER_SUCCESS = 1;// �û�ע��ɹ�

	public static final String ROOT_URL = "http://192.168.173.1:8080";

	public static final String GET_NEW_RESULTS_DATA_URL = ROOT_URL
			+ "/VolunteerTimeWeb/VolunteerTimeResultExhibitionServlet";

}
