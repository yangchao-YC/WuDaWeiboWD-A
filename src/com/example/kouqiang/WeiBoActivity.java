package com.example.kouqiang;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import net.tsz.afinal.FinalDb;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class WeiBoActivity extends Activity {
	private Button backButton;// 返回
	private TextView tabTextView;
	private Button weiboButton;
	private EditText editText;
	private String division = null;
	private String name = null;
	private String specialty = null;
	private String summary = null;
	private String weibo = null;
	private String image = null;
	private String keyString = null;
	private String imageUrl=null;//存储网络获取的本人头像地址
	private Bitmap imageBitmap = null;//本人头像
	private Bitmap imageBitmap2 = null;//医生头像
	private ProgressDialog progressDialog;
	private ListView listView = null;
	private OAuthV1 oAuthV1;
	private int WeiBocount = 0;//存储对应微博的ID 的数目
	private int WeiBoSum = 0;	//存储当前查询微博ID循环次数
	private FinalDb db = null;//数据库对象,infoID为信息ID   info为详细信息   doctorID为医生ID
	private ArrayList<String> WeiBoID = new ArrayList<String>();//存储查找到的微博ID
	private ArrayList<String> WeiBoIDInfo = new ArrayList<String>();//存储查找到的微博ID对应的信息
	private ArrayList<DetailEntity> list = new ArrayList<DetailEntity>();
	ArrayList<Hashtable<String, String>> blockdata = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weibo);
		Intent intent = this.getIntent();
		division = (String) getIntent().getExtras().getString("division");// 传递科室
		name = (String) getIntent().getExtras().getString("name");// 传递姓名
		specialty = (String) getIntent().getExtras().getString("specialty");// 传递专长
		summary = (String) getIntent().getExtras().getString("summary");// 传递简介
		weibo = (String) getIntent().getExtras().getString("weibo");// 传递微博
		image = (String) getIntent().getExtras().getString("image");// 传递头像

		keyString = (String) getIntent().getExtras().getString("key"); 

		db = FinalDb.create(this);//实例化数据对象
		
		oAuthV1 = (OAuthV1) intent.getExtras().getSerializable("oauth");// 获取微博授权
		listView = (ListView) findViewById(R.id.WeiBo_ListView);
		backButton = (Button) findViewById(R.id.WeiBo_back);
		tabTextView = (TextView) findViewById(R.id.WeiBo_Tab_Name);
		editText = (EditText) findViewById(R.id.WeiBo_EditText);
		weiboButton = (Button) findViewById(R.id.WeiBo_button);

		tabTextView.setText("向" + name + "提问:");

		backButton.setOnClickListener(new backButton());
		weiboButton.setOnClickListener(new weiboButton());
		

		
		data();
		dataName();
		
		if (WeiBocount>0) {
			threadList();
		}

	}
	/**
	 * 查询数据库
	 * 表User：  infoID为信息ID   info为详细信息   doctorID为医生ID
	 */
	private void data() {
		
		String condition = "doctorID='" + weibo+ "'";//搜索条件
		List<User> list = db.findAllByWhere(User.class, condition);
		
		if ( list != null ) {
			WeiBocount = list.size();
			for (int i = 0; i <WeiBocount; i++) {
				WeiBoID.add(list.get(i).getInfoID());//存储微博ID
				WeiBoIDInfo.add(list.get(i).getInfo());
			}	
		}
		else {
			WeiBocount = 0;
		}
	}
	
/**
 * 查询微博列表Thread
 */
	private void threadList()
	{
		progressDialog = ProgressDialog.show(WeiBoActivity.this, "", "正在查询数据", true, false);
		new Thread()
		{
			@Override
			public void run() {
				// TODO Auto-generated method stub
				dateList();
			}
			
		}.start();
	}
	/**
	 * 添加微博Thread
	 */
	private void threadAdd() {
		progressDialog = ProgressDialog.show(WeiBoActivity.this, "", "正在发送微博", true, false);
		new Thread()
		{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				addWeiBo();
			}
			
		}.start();
	}
	// 发送微博按钮
	class weiboButton implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (editText.getText().toString().equals("") || editText.getText().toString().length()>140 ) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						WeiBoActivity.this);
				builder.setTitle("警告");
				builder.setMessage("发送内容不能为空或者长度不能超过140");
				builder.setPositiveButton("确定", null);
				builder.show();
			} else {
				threadAdd();
			}
		}
	}
	
	/**
	 * 查询个人信息
	 */
	private void dataName() {
		String response;
		 UserAPI userAPI;
		userAPI=new UserAPI(OAuthConstants.OAUTH_VERSION_1);
        try {
            response=userAPI.info(oAuthV1, "xml");//获取用户信息
            content(2,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userAPI.shutdownConnection();
	}
	/**
	 * 获取网络图片
	 * @param url  网络图片地址
	 * @return	返回图片
	 */
	private Bitmap imageName(String url) {
			URL imageUrl =null;
			Bitmap bitmap =null;
			try {
				imageUrl = new URL(url);
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				HttpURLConnection connection =(HttpURLConnection)imageUrl.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream is=connection.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				bitmap = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return bitmap;		
	}
	
/**
 * 查询微博列表
 */
	private void dateList() {
	
		for (int i = 0; i < WeiBocount; i++) {   //weiBoCount为微博ID数目这里循环加载
			WeiBoSum = i;
			TAPI tAPI;
			String response;
			tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);// 查询微博列表
			try {
				response = tAPI.reList(oAuthV1, "xml", "2", WeiBoID.get(i).toString(), "0",
						"0", "100", "0");
				content(0, response);
			} catch (Exception e) {
				// TODO: handle exception
			}
			tAPI.shutdownConnection();
		}

	}
/**
 * 发送微博
 */
	private void addWeiBo()
	{
		TAPI tAPI;
		String response;
		String editString = editText.getText().toString() + "@" + weibo;
		tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_1);// 发一条微博
		try {
			response = tAPI.add(oAuthV1, "xml",editString, "127.0.0.1");
			content(1, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tAPI.shutdownConnection();
	}
	
	/**
	 * 
	 * @param distinguish  辨别是查询微博还是发送微博 i=0则为查询 i=1为发送微博   i=2为查询本人信息
	 * @param string 为需解析的字符串
	 *  参数ret为判断查询或者发送微博是否成功 0-成功，非0-失败, //发微博 接收参数为id 可通过此id查询信息 //
	 *  查询列表 接收参数origtext
	 */
	public void content(int distinguish, String string) {

		blockdata = new ArrayList<Hashtable<String, String>>();
		GetSoapData soap = new GetSoapData();
		ArrayList<String> returnparams = new ArrayList<String>();
		returnparams.add("ret");
		if (distinguish == 0) {
			returnparams.add("self");
			returnparams.add("origtext");
			returnparams.add("head");
			blockdata = soap.getSoapDataWithParam(string, returnparams);// 查询信息
			DetailEntity a;
			a = new DetailEntity("", "",WeiBoIDInfo.get(WeiBoSum).toString(),imageBitmap,R.layout.list_say_me_item);
			list.add(a);
			if (WeiBoSum == 0) {//第一次查找的时候存放医生图片
				for (int i = 0; i < blockdata.get(1).size(); i += 2) {
					if (blockdata.get(1).get(String.valueOf(i)).equals("1")) {	
					}
					else {
						imageBitmap2 = imageName(blockdata.get(3).get(String.valueOf(i)).toString() +"/100");
						break;
					}
				}
			}
			for (int i = blockdata.get(1).size() -2 ; i >=0 ; i -= 2) {
					DetailEntity d;
					if (blockdata.get(1).get(String.valueOf(i)).equals("1")) {//为1的话  代表是本人发的微博
						d = new DetailEntity("", "", blockdata.get(2).get(String.valueOf(i)).toString(),imageBitmap,
								R.layout.list_say_me_item);
					} else {
						d = new DetailEntity("", "", blockdata.get(2).get(String.valueOf(i)).toString(),imageBitmap2,
								R.layout.list_say_he_item);
					}
					list.add(d);
			}
			if (WeiBoSum == (WeiBocount - 1)) {//判断是否是最后一次查询  如果是则通知程序可以加载信息
				handlerList.sendEmptyMessage(1);
			}

		} 
		else if (distinguish == 2) {
			returnparams.add("head");
			blockdata = soap.getSoapDataWithParam(string, returnparams);// 查询信息
			if (blockdata.get(0).get("0").toString().equals("0")) {
				imageUrl = blockdata.get(1).get("0").toString();
				imageBitmap=	imageName(imageUrl+"/100");
			}
		}
		else {
			returnparams.add("id");
			blockdata = soap.getSoapDataWithParam(string, returnparams);// 查询信息
			handlerAdd.sendEmptyMessage(1);
		}
	}
/**
 * 
 * @param ret	判定微博是否发送成功  0为成功  其他为失败
 * @param id		发送成功存储这条微博ID
 */
	private Handler handlerAdd = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//调用增加微博方法
			progressDialog.dismiss();
			addWeiBoJudge(blockdata.get(0).get("0").toString(),blockdata.get(1).get("0").toString());
		}
		
	};
	
	private void addWeiBoJudge(String ret, String id)
	{
		/**
		 *更改成数据库后  应该更换成匹配最后一条微博的ID是否一样  如果一样则不添加
		 */
		if (WeiBocount == 0) {
			if (ret.equals("0")) {
				User user;
				user = new User();
				user.setInfoID(id);
				user.setInfo(editText.getText().toString());
				user.setDoctorID(weibo);
				db.save(user);
				
				WeiBocount ++;
				WeiBoID.add(id);
				WeiBoIDInfo.add( editText.getText().toString());
				//刷新当前列表
				DetailEntity d;
				d = new DetailEntity("", "", editText.getText().toString(),imageBitmap,
						R.layout.list_say_me_item);
				list.add(d);
				listView.setAdapter(new DetailAdapter(WeiBoActivity.this, list));	
				editText.setText("");//清空当前信息
			}
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(WeiBoActivity.this);
				builder.setTitle("提示");
				builder.setMessage("没有发送成功");
				builder.setPositiveButton("确定", null);
				builder.show();
			}
		}
		else {
			if (id.equals(WeiBoID.get(WeiBocount - 1))) {
				AlertDialog.Builder builder = new AlertDialog.Builder(WeiBoActivity.this);
				builder.setTitle("提示");
				builder.setMessage("没有发送成功,内容不能重复");
				builder.setPositiveButton("确定", null);
				builder.show();
			}
			else {
				//增加一条数据
				
				User user;
				user = new User();
				user.setInfoID(id);
				user.setInfo(editText.getText().toString());
				user.setDoctorID(weibo);
				db.save(user);
				
				WeiBocount ++;
				//刷新当前列表
				DetailEntity d;
				d = new DetailEntity("", "", editText.getText().toString(),imageBitmap,
						R.layout.list_say_me_item);
				list.add(d);
				listView.setAdapter(new DetailAdapter(WeiBoActivity.this, list));	
				editText.setText("");//清空当前信息
			}
		}
		
	}
	
	private Handler handlerList = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
			listView.setAdapter(new DetailAdapter(WeiBoActivity.this, list));
		}
	};
	/*
	 * 返回按钮
	 */
	class backButton implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			intent = new Intent(WeiBoActivity.this, ContentActivity.class);
			intent.putExtra("division", division);// 传递科室
			intent.putExtra("name", name);// 传递姓名
			intent.putExtra("specialty", specialty);// 传递专长
			intent.putExtra("summary", summary);// 传递简介
			intent.putExtra("weibo", weibo);// 传递微博 可做为数据查询参数
			intent.putExtra("image", image);// 传递头像
			intent.putExtra("key", keyString);
			startActivity(intent);
			finish();
		}
	}

	/*
	 * 返回键 (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent;
			intent = new Intent(WeiBoActivity.this, ContentActivity.class);
			intent.putExtra("division", division);// 传递科室
			intent.putExtra("name", name);// 传递姓名
			intent.putExtra("specialty", specialty);// 传递专长
			intent.putExtra("summary", summary);// 传递简介
			intent.putExtra("weibo", weibo);// 传递微博
			intent.putExtra("image", image);// 传递头像
			intent.putExtra("key", keyString);
			startActivity(intent);
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}
