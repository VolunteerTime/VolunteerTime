/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.business;

import java.util.ArrayList;

import scau.info.volunteertime.vo.ActivityData;
import android.util.Log;

/**
 * @author �̳���
 * 
 */
public class UserBO {

	/**
	 * ����0����½��ȷ������id 0���˺�����Ϊ�� -1�����벻��ȷ -2���˺Ų�����
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
		Log.d("CheckUserLoginResult", "©������������");
		return -1;
	}
	

	ArrayList<ArrayList<ActivityData>> list=new ArrayList<ArrayList<ActivityData>>();
	public ArrayList<ActivityData> getActivityRecord(int page)  //ÿ�λ�ȡ20�����ݵ�
	{
		ArrayList<ActivityData> arraylist=new ArrayList<ActivityData>();
		ArrayList<ActivityData> arraylist2=new ArrayList<ActivityData>();
		
		ActivityData[] a=new ActivityData[20];
		
		 a[0]=new ActivityData("df", "asdf", "df", "dffd"); 

		  a[1]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
		  
			 a[1].addComment("�ú�Ц��������������������������");
			 a[1].addComment("��������^_^");
			 a[1].addComment("������ม�������");
		  
		  a[2]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
		  a[3]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
		  a[4]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
		  a[5]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
		  a[6]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
		  a[7]=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
		  a[8]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
		  a[9]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
		  a[10]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
		  a[11]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
		  a[12]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
		  a[13]=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
		  a[14]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
		  a[15]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
		  a[16]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
		  a[17]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
		  a[18]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
		  a[19]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
			ActivityData[] ab=new ActivityData[20];

			  ab[0]=new ActivityData("df", "asdf", "df", "dffd"); 
			  ab[1]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
			  ab[2]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
			  ab[3]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
			  ab[4]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
			  ab[5]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
			  ab[6]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
			  ab[7]=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
			  ab[8]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
			  ab[9]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
			  ab[10]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
			  ab[11]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
			  ab[12]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
			  ab[13]=new ActivityData("xin","����21��ȥ�Է���������ˣ��Һ�һ������㣬���ˣ�","2014-07-22","eee");
			  ab[14]=new ActivityData("name","���Ǹ�ʧ����ȥһ�Ҳ����Է����պú�����һ���������������ҵģ��ò����ȡ�","2014-07-22","eee");
			  ab[15]=new ActivityData("name","���ҿ������ǲ��������ɣ�������Ϊ�����˻���ͷ���ҿ�������Ǯ��Ǯ�����ߣ�����׷���������ֻ�û׷�ϱ�����","2014-07-22","eee");
			  ab[16]=new ActivityData("name","һ����Ů�ĶԻ����У�Ϊʲô�㿴���Ұ���Ů����Ϊ�㳤��˧���У�˧�ֲ��ܵ����ԡ�Ů�����ǲ�˧�Ļ������Ż�Բ��·���","2014-07-22","eee");
			  ab[17]=new ActivityData("name","1.У����Ӣ����ʦһ��ȥ����ĳ��ѧ����,У�������ý���,Ӣ����ʦ������","2009-9-26","eee");
			  ab[18]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
			  ab[19]=new ActivityData("name","�ʼǱ�˵:�Ҹ���˵��Ц���¡��豭˵:�ð��ð����ʼǱ�˵:��ǰ�и��豭,���ӽ�ˮ�ˡ��豭:","5Сʱǰ","eee");
			
			  
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
