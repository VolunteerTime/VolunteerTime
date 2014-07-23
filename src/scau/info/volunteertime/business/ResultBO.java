/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-23
 */
package scau.info.volunteertime.business;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.Result;

/**
 * Results的BO
 * 
 * @author 蔡超敏
 * 
 */
public class ResultBO {

	public ResultBO() {
		List<Result> allResults = new ArrayList<Result>();
		for (int i = 0; i < 30; i++) {// 建立包含30个result的list
			allResults
					.add(new Result(
							i,
							"测试title" + i,
							"测试内容：什么东西啊，我不会写很多东西的，这个是第" + i + "个例子",
							"http://img.hb.aicdn.com/fda4bb25fc546ed22c9ff137ac72b7ba27e62e9916225-2ywtVp_fw658",
							"chaoKing" + i, new Date(i * 100000)));
		}
	}

	/**
	 * 返回第page页的数据
	 * 
	 * @param page
	 * @return Pagination<Result>
	 */
	public Pagination<Result> getDownData(int page) {

		return null;
	}

}
