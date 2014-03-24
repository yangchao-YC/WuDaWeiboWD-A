package com.example.kouqiang;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv1.OAuthV1;
import com.tencent.weibo.oauthv1.OAuthV1Client;
import com.tencent.weibo.utils.QHttpClient;
import com.tencent.weibo.webview.OAuthV1AuthorizeWebView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ContentActivity extends Activity {
	
	//!!!请根据您的实际情况修改!!!      认证成功后浏览器会被重定向到这个url中   本例子中不需改动
		private String oauthCallback = "null"; 
	    //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP KEY
		private String oauthConsumeKey = "801294096"; 
	    //!!!请根据您的实际情况修改!!!      换为您为自己的应用申请到的APP SECRET
		private String oauthConsumerSecret="625784181d9d1ecc7d6c49c9692428dc";
		
		
private Button	backButton;//返回
private Button askbtnButton;//我要提问
private ImageView imageView;//头像
private ImageView weiBoView;//微博图像
private TextView weiBoTextView;//微博
private TextView nameTextView;//姓名
private TextView divisionTextView;//科室
private TextView fieldTextView;//擅长领域
private TextView summaryTextView;//医生简介
private TextView navTextView;
private String division = null;
private String name = null;
private String specialty = null;
private String summary = null;
private String weibo = null;
private String image = null;
private String keyString = null;
private OAuthV1 oAuth; 
private String b ="1";//判断授权是否正确   1为正确  2为错误
UserData userData;
private Timer timer = new Timer();
private TimerTask task;
//设置人物头像
private int [] images={R.drawable.one,R.drawable.oneb,
		R.drawable.two_a,R.drawable.two_b,R.drawable.twoc,
		R.drawable.three_a,R.drawable.three_b,R.drawable.three_c,
		R.drawable.four_a,R.drawable.four_b,R.drawable.four_c,R.drawable.four_d,R.drawable.four_e,R.drawable.four_f,
		R.drawable.five_a,R.drawable.five_b,R.drawable.five_c,
		R.drawable.six_a,R.drawable.six_b,
		R.drawable.seven_a,R.drawable.seven_b,
		R.drawable.eight,
		R.drawable.nine};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content);
		
		userData = new UserData(this);
		
		keyString = (String)getIntent().getExtras().getString("key");
		division = (String)getIntent().getExtras().getString("division");//传递科室
		name = (String)getIntent().getExtras().getString("name");//传递姓名
		specialty = (String)getIntent().getExtras().getString("specialty");//传递专长
		summary = (String)getIntent().getExtras().getString("summary");//传递简介
		weibo = (String)getIntent().getExtras().getString("weibo");//传递微博
		image = (String)getIntent().getExtras().getString("image");//传递头像
		
		oAuth=new OAuthV1(oauthCallback);
        oAuth.setOauthConsumerKey(oauthConsumeKey);
        oAuth.setOauthConsumerSecret(oauthConsumerSecret);
		
		navTextView = (TextView)findViewById(R.id.Content_nav_Text);
		backButton = (Button)findViewById(R.id.About_back);
		askbtnButton = (Button)findViewById(R.id.Content_Button);//提问按钮
		imageView = (ImageView)findViewById(R.id.Content_Image);
		weiBoView = (ImageView)findViewById(R.id.Content_Buttom_Image);
		weiBoTextView = (TextView)findViewById(R.id.Content_Buttom_Text);
		nameTextView = (TextView)findViewById(R.id.Content_Name);
		divisionTextView = (TextView)findViewById(R.id.Content_Division);
		fieldTextView = (TextView)findViewById(R.id.Content_Field);
		summaryTextView = (TextView)findViewById(R.id.Content_Summary);
		
		if (userData.getUserName().toString().equals("")) {//如果授权为空  则直接进入授权申请
			b = "2";
		}
		else {
			oAuth.setOauthToken(userData.getUserName().toString());
			oAuth.setOauthTokenSecret(userData.getPassword().toString());
			
			test();//测试授权是否正确
		}
		nameTextView.setText("姓 名:    " + name);
		navTextView.setText(name);
		divisionTextView.setText("科 室:    " + division);
		fieldTextView.setText(specialty);
		summaryTextView.setText(summary);
		imageView.setImageDrawable(getResources().getDrawable(images[Integer.parseInt(image)]));
		backButton.setOnClickListener(new backButton());
		askbtnButton.setOnClickListener(new askbtn());
		weiBoTextView.setOnClickListener(new weiBo());//绑定微博跳转
		weiBoView.setOnClickListener(new weiBo());//绑定微博跳转
		//关闭OAuthV1Client中的默认开启的QHttpClient。
		OAuthV1Client.getQHttpClient().shutdownConnection();
		//为OAuthV1Client配置自己定义QHttpClient。
		OAuthV1Client.setQHttpClient(new QHttpClient());
	}
	/*
	 * 提问按钮
	 */
	class askbtn implements OnClickListener{
		Intent intent;
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if (b.equals("2")) {//判断是否登录过  或者以前登录记录号不可用
				oAuth.setOauthToken("");
				oAuth.setOauthTokenSecret("");
				warrant();//申请授权
			}
			else {
				weibo();
			}	
		}	
	}
	
	
	private void warrant()//申请授权
	{
		Log.v("-----143-----", "-------");
		Intent intent;
			try {
				oAuth=OAuthV1Client.requestToken(oAuth);
			} catch (Exception e) {
				// TODO: handle exception
			}
			intent = new Intent(ContentActivity.this, OAuthV1AuthorizeWebView.class);//创建Intent，使用WebView让用户授权
	        intent.putExtra("oauth", oAuth);
			startActivityForResult(intent,1);
			/*
			task = new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (String.valueOf(oAuth.getOauthToken()).equals("")) {
						
					}
					else {
						timer.cancel();//关闭定时器
						handler.sendEmptyMessage(1);
					}
				}
			};
			
			timer.schedule(task, 1000,1000);
		//	handler.sendEmptyMessage(1);
		 */
	}
	
	private Handler handler = new Handler()//存储授权  并进行页面跳转
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			try {
				oAuth=OAuthV1Client.accessToken(oAuth);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			userData.saveUserName(String.valueOf(oAuth.getOauthToken()));
			userData.savePassword(String.valueOf(oAuth.getOauthTokenSecret()));
			
			weibo();
		}
		
	};
	
	private void weibo()//跳转到微博页面
	{
		Intent intent;
		intent = new Intent(ContentActivity.this,WeiBoActivity.class);
		intent.putExtra("division", division);//传递科室
		intent.putExtra("name", name);//传递姓名
		intent.putExtra("specialty", specialty);//传递专长
		intent.putExtra("summary", summary);//传递简介
		intent.putExtra("weibo", weibo);//传递微博
		intent.putExtra("image", image);//传递头像
		intent.putExtra("key", keyString);
		intent.putExtra("oauth",oAuth);
		startActivity(intent);
		finish();
	}
	
	private void test()//测试原始授权是否可用
	{
		UserAPI userAPI;
		 String response;
		userAPI=new UserAPI(OAuthConstants.OAUTH_VERSION_1);
        try {
            response=userAPI.info(oAuth, "xml");//获取用户信息
           content(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userAPI.shutdownConnection();
	}
	
	public void content(String string) {//获取返回值   判定授权是否正确
		GetSoapData soap = new GetSoapData();
		ArrayList<String> returnparams = new ArrayList<String>();
		returnparams.add("ret");
		ArrayList<Hashtable<String, String>> 	blockdata = soap.getSoapDataWithParam(string,returnparams);//查询信息
		
		if (blockdata.get(0).get("0").toString().equals("0")) {
			b = "1";
		}
		else {
			b = "2";
		}
	}
/*
 * 返回按钮
 */
	 class backButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
			intent	 = new Intent(ContentActivity.this,HomeActivity.class);
			intent.putExtra("key", keyString);
			startActivity(intent);
			finish();
		}
		 
	 }
	/*
	 * 微博跳转
	 */
	 class weiBo implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String urlString = "http://t.qq.com/"+weibo;
			Intent intent = new	Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri conUri = Uri.parse(urlString);
			intent.setData(conUri);
			startActivity(intent);
		}
		 
	 }
	 

	 public void onBackPressed() {
	      //关闭OAuthV1Client中的自定义的QHttpClient。
	        OAuthV1Client.getQHttpClient().shutdownConnection();
	        finish();
	        System.exit(0);
	    }
	/*
	 * 通过读取OAuthV1AuthorizeWebView返回的Intent，获取用户授权后的验证码
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)	{
		if (requestCode==1)	{
			if (resultCode==OAuthV1AuthorizeWebView.RESULT_CODE)	{
				//从返回的Intent中获取验证码
				oAuth=(OAuthV1) data.getExtras().getSerializable("oauth");
			//	bTextView.setText("/n---3----"+oAuth.getOauthVerifier());
				handler.sendEmptyMessage(1);
			}
		}
	}
	 
	/*
	 * 返回键
	 * (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent;
			intent	 = new Intent(ContentActivity.this,HomeActivity.class);
			intent.putExtra("key", keyString);
			startActivity(intent);
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
	}


