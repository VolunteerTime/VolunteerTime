/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.business;

import java.util.ArrayList;

import scau.info.volunteertime.vo.ActivityData;
import android.util.Log;

/**
 * @author 蔡超敏
 * 
 */
public class UserBO {

	/**
	 * 大于0：登陆正确，返回id 0：账号密码为空 -1：密码不正确 -2：账号不存在
	 * 
	 * @param userId
	 * @param password
	 * @return int
	 */
	public int CheckUserLoginResult(String userId, String password) {
		if ("201230560202".equals(userId) && "123".equals(password)) {
			return 1;
		} else if (!"123".equals(password)) {
			return -1;
		} else if (!"201230560202".equals(userId)) {
			return -2;
		} else if (userId == null || userId.equals("") || password == null
				|| password.equals("")) {
			return 0;
		}
		Log.d("CheckUserLoginResult", "漏了其他可能性");
		return -1;
	}
	

	ArrayList<ArrayList<ActivityData>> list=new ArrayList<ArrayList<ActivityData>>();
	public ArrayList<ActivityData> getActivityRecord(int page)  //每次获取20条数据到
	{
		ArrayList<ActivityData> arraylist=new ArrayList<ActivityData>();
		ArrayList<ActivityData> arraylist2=new ArrayList<ActivityData>();
		
		ActivityData[] a=new ActivityData[20];
		
		 a[0]=new ActivityData("df", "asdf", "df", "dffd"); 

		  a[1]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		  
			 a[1].addComment("好好笑啊！！！！！！！！！！！！");
			 a[1].addComment("哈哈哈哈^_^");
			 a[1].addComment("不错不错喔。。。。");
		  
		  a[2]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		  a[3]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		  a[4]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		  a[5]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		  a[6]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		  a[7]=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
		  a[8]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		  a[9]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		  a[10]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		  a[11]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		  a[12]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		  a[13]=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
		  a[14]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
		  a[15]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
		  a[16]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
		  a[17]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
		  a[18]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
		  a[19]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
			ActivityData[] ab=new ActivityData[20];

			  ab[0]=new ActivityData("df", "asdf", "df", "dffd"); 
			  ab[1]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
			  ab[2]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
			  ab[3]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
			  ab[4]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
			  ab[5]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
			  ab[6]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
			  ab[7]=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
			  ab[8]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
			  ab[9]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
			  ab[10]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
			  ab[11]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
			  ab[12]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
			  ab[13]=new ActivityData("xin","本人21，去吃饭，吃完结账，我喊一声，大姐，结账！","2014-07-22","eee");
			  ab[14]=new ActivityData("name","哥们刚失恋，去一家餐厅吃饭，刚好后面有一对情吕在唧唧我我的，好不亲热。","2014-07-22","eee");
			  ab[15]=new ActivityData("name","“我靠，哥们不用这样吧！”他以为我怕了还点头，我快速拿起钱跟钱包就走，尼玛追了我两条街还没追上被我跑","2014-07-22","eee");
			  ab[16]=new ActivityData("name","一对男女的对话：男：为什么你看上我啊？女：因为你长得帅埃男：帅又不能当饭吃。女：但是不帅的话，对着会吃不下饭。","2014-07-22","eee");
			  ab[17]=new ActivityData("name","1.校长和英语老师一起去法国某中学访问,校长在礼堂讲话,英语老师做翻译","2009-9-26","eee");
			  ab[18]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
			  ab[19]=new ActivityData("name","笔记本说:我给你说个笑话呗。茶杯说:好啊好啊。笔记本说:从前有个茶杯,脑子进水了。茶杯:","5小时前","eee");
			
			  
		  for(int i=0;i<20;i++)
		  {arraylist.add(a[i]);}
		  for(int i=0;i<20;i++)
		  {arraylist2.add(ab[i]);}
		  list.add(arraylist);
		  list.add(arraylist2);
		  if(page>=list.size()) return null;
		  else
		return list.get(page);
	}

}
