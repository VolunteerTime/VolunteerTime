/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-23
 */
package scau.info.volunteertime.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.Result;

/**
 * Results��BO
 * 
 * @author �̳���
 * 
 */
public class ResultBO {

	public ResultBO() {
		List<Result> allResults = new ArrayList<Result>();
		for (int i = 0; i < 30; i++) {// ��������30��result��list
			allResults
					.add(new Result(
							i,
							"����title" + i,
							"�������ݣ�ʲô���������Ҳ���д�ܶණ���ģ�����ǵ�" + i + "������",
							"http://img.hb.aicdn.com/fda4bb25fc546ed22c9ff137ac72b7ba27e62e9916225-2ywtVp_fw658",
							"chaoKing" + i, new Date(i * 100000)));
		}
	}

	/**
	 * ���ص�pageҳ������
	 * 
	 * @param page
	 * @return Pagination<Result>
	 */
	public Pagination<Result> getDownData(int page) {

		return null;
	}

}
