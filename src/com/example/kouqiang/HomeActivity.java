package com.example.kouqiang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

public class HomeActivity extends Activity implements android.view.View.OnClickListener{
private RadioButton protectButton;
private RadioButton cycleButton;
private RadioButton positiveButton;
private RadioButton repairButton;
private RadioButton childButton;
private RadioButton toothButton;
private RadioButton surgicalButton;
private RadioButton cropButton;
private RadioButton specialButton;
private RadioGroup radioGroup;
private ListView listView;
private String keyString = null;
ArrayList<Hashtable<String, String>> blockdata ;
Hashtable<String, String> item;
//设置人物头像
private int [] image={R.drawable.one,R.drawable.oneb,
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
		setContentView(R.layout.home);
		
		keyString = (String)getIntent().getExtras().getString("key");
		
		radioGroup = (RadioGroup)findViewById(R.id.Home_RadioGroup);
		protectButton = (RadioButton)findViewById(R.id.Home_Protect);
		cycleButton = (RadioButton)findViewById(R.id.Home_Cycle);
		positiveButton = (RadioButton)findViewById(R.id.Home_Positive);
		repairButton = (RadioButton)findViewById(R.id.Home_Repair);
		childButton = (RadioButton)findViewById(R.id.Home_Child);
		toothButton = (RadioButton)findViewById(R.id.Home_Tooth);
		surgicalButton = (RadioButton)findViewById(R.id.Home_Surgical);
		cropButton = (RadioButton)findViewById(R.id.Home_Crop);
		specialButton = (RadioButton)findViewById(R.id.Home_Special);
		listView = (ListView)findViewById(R.id.Home_ListView);
		protectButton.setOnClickListener(this);
		cycleButton.setOnClickListener(this);
		positiveButton.setOnClickListener(this);
		repairButton.setOnClickListener(this);
		childButton.setOnClickListener(this);
		toothButton.setOnClickListener(this);
		surgicalButton.setOnClickListener(this);
		cropButton.setOnClickListener(this);
		specialButton.setOnClickListener(this);
		
		if (keyString.equals("0")) {
			protectButton.setChecked(true);
			text(0);
		}
		else if (keyString.equals("1")) {
			cycleButton.setChecked(true);
			text(1);
		}
		else if (keyString.equals("2")) {
			positiveButton.setChecked(true);
			text(2);
		}
		else if (keyString.equals("3")) {
			repairButton.setChecked(true);
			text(3);
		}
		else if (keyString.equals("4")) {
			childButton.setChecked(true);
			text(4);
		}
		else if (keyString.equals("5")) {
			toothButton.setChecked(true);
			text(5);
		}
		else if (keyString.equals("6")) {
			surgicalButton.setChecked(true);
			text(6);
		}
		else if (keyString.equals("7")) {
			cropButton.setChecked(true);
			text(7);
		}
		else  {
			specialButton.setChecked(true);
			text(8);
		}
		

		
	}
	/*
	public void contentXML(String string) {
		GetSoapData soap = new GetSoapData();
		ArrayList<String> returnparams = new ArrayList<String>();
		
		blockdata = soap.getSoapDataWithParam(string,returnparams);//查询信息
	}
	*/
	/*
	 * 数据
	 */
	public void text(int i) {

		blockdata =new ArrayList<Hashtable<String,String>>();
			switch (i) {
			case 0: 
				item= new Hashtable<String,String>();
				 item.put("0", "口腔预防科");
				 item.put("1", "台保军教授");
				 item.put("2", "龋齿防治、口腔溃疡、口腔疾病预防");
				 item.put("3", "台保军，武汉大学口腔医院党委副书记、口腔预防科教授、全国著名口腔预防专家。");
				 item.put("4", "taibaojun");
				 item.put("5", "0");
				 blockdata.add(item);
				 item= new Hashtable<String,String>();
				 item.put("0", "口腔预防科");
				 item.put("1", "陈曦主治医师");
				 item.put("2", "口腔预防、口腔流行病学、龋病的病因和预防口腔临床实验");
				 item.put("3", "陈曦，武汉大学口腔医院预防科主治医师，中荷双博士学位。");
				 item.put("4", "sicony");
				 item.put("5", "1");
				 blockdata.add(item);
				break;
				case 1:
					 item= new Hashtable<String,String>();
					 item.put("0", "牙周科");
					 item.put("1", "谢昊主任医师");
					 item.put("2", "龋齿、牙髓炎、根尖周炎、牙周炎、牙龈病、口腔黏膜病");
					 item.put("3", "谢昊，武汉大学口腔医院花桥门诊部主任，牙周科主任医师、副教授、硕导。中华口腔医学会牙周病学专业委员会常务委员、武汉市第十一届政协委员。");
					 item.put("4", "xiehaodentist");
					 item.put("5", "2");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "牙周科");
					 item.put("1", "曹正国副主任");
					 item.put("2", "牙周疾病、牙髓牙周病变及牙周手");
					 item.put("3", "曹正国，武汉大学口腔医院牙周科副主任。");
					 item.put("4", "jery7677");
					 item.put("5", "3");
					 blockdata.add(item);
					 
					 item= new Hashtable<String,String>();
					 item.put("0", "牙周科");
					 item.put("1", "李成章主任");
					 item.put("2", "各种牙周疾病");
					 item.put("3", "李成章，武汉大学口腔医院牙周科主任。主任医师、教授、博导、知名专家。中华口腔医学会牙周病学专业委员会副主任委员。");
					 item.put("4", "lichengzhang4567");
					 item.put("5", "4");
					 blockdata.add(item);
					 
					break;
					
				case 2:
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔正畸科");
					 item.put("1", "贺红主任");
					 item.put("2", "青少年及成人各类牙颌畸形矫治");
					 item.put("3", "贺红，武汉大学口腔医院正畸一科主任。");
					 item.put("4", "hehong123wuda");
					 item.put("5", "5");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔正畸科");
					 item.put("1", "熊晖副主任");
					 item.put("2", "错颌畸形、颌面畸形、颞下颌关节疾病");
					 item.put("3", "熊晖，武汉大学口腔医院正畸一科副主任。");
					 item.put("4", "xionghui_123");
					 item.put("5", "6");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔正畸科");
					 item.put("1", "韩光丽主任");
					 item.put("2", "青少年、成人错颌畸形、唇腭裂患者正畸序列治疗、压裂缺失患者的局部正畸治疗");
					 item.put("3", "韩光丽，武汉大学口腔医院口腔正畸二科主任，博士，副教授，中华医学会正畸专委会常委。");
					 item.put("4", "hanguangli4229");
					 item.put("5", "7");
					 blockdata.add(item);
					break;
				case 3:
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "王贻宁主任");
					 item.put("2", "牙漂白美容、冠修复等各种牙齿修复问题");
					 item.put("3", "王贻宁，武汉大学口腔医院口腔修复科主任，主任医师、教授、博导、知名专家。中华口腔修复学专业委员会主任委员");
					 item.put("4", "wangynwhu");
					 item.put("5", "8");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "赵熠主治医师");
					 item.put("2", "牙漂白美容、冠修复等各种牙齿修复");
					 item.put("3", "赵熠，武汉大学口腔医院口腔修复科医师。");
					 item.put("4", "zhao-yi-");
					 item.put("5", "9");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "陈小晖主任医师");
					 item.put("2", "各种活动、固定义齿修复");
					 item.put("3", "陈小晖，武汉大学口腔医院口腔修复科副主任医师，《口腔医学研究》编委。");
					 item.put("4", "kuxiaobude717915");
					 item.put("5", "10");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "李超宏主任医师");
					 item.put("2", "口腔修复问题");
					 item.put("3", "李超宏，武汉大学口腔医院汉阳门诊部主任。");
					 item.put("4", "shouji1328615436");
					 item.put("5", "11");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "徐东选");
					 item.put("2", "活动义齿、全口义齿雅美容修复");
					 item.put("3", "徐东选，武汉大学口腔医院武胜路门诊部主任、口腔修复主任医师。");
					 item.put("4", "xdxxqy");
					 item.put("5", "12");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔修复科");
					 item.put("1", "王革主任医师");
					 item.put("2", "各种复杂活动固定修复体的制作、前牙美容修复");
					 item.put("3", "王革，武汉大学口腔医院口腔修复科主任医师。");
					 item.put("4", "wanggeinus");
					 item.put("5", "13");
					 blockdata.add(item);
					break;
				case 4:
					 item= new Hashtable<String,String>();
					 item.put("0", "儿童口腔科");
					 item.put("1", "宋光泰主任");
					 item.put("2", "儿童口腔疾病");
					 item.put("3", "宋光泰，武汉大学口腔医院儿童牙科主任、主任医师。兼任中华口腔医学会儿童口腔医学专业委员会副主任委员。");
					 item.put("4", "songguangtai2012");
					 item.put("5", "14");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "儿童口腔科");
					 item.put("1", "聂德周主任医师");
					 item.put("2", "儿童牙合有道、牙外伤修复等常见病");
					 item.put("3", "聂德周，武汉大学口腔医院儿童口腔科副主任、副主任医师，中华口腔医学会儿童口腔医学专委会委员。");
					 item.put("4", "wudaertongyayi-ndz");
					 item.put("5", "15");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "儿童口腔科");
					 item.put("1", "王孜");
					 item.put("2", "儿童口腔疾病的治疗");
					 item.put("3", "王孜，武汉大学口腔医院儿童口腔科副主任医师。");
					 item.put("4", "princewangzi2010");
					 item.put("5", "16");
					 blockdata.add(item);
					break;
				case 5:
					 item= new Hashtable<String,String>();
					 item.put("0", "牙体牙髓科");
					 item.put("1", "彭彬");
					 item.put("2", "押题压碎病、现代根管治疗及押题美容修复");
					 item.put("3", "彭彬，武汉大学口腔医院牙体牙髓科主任，主任医师、教授、博导、知名专家。中华口腔医学会牙体牙髓病学专业委员会常务委员。");
					 item.put("4", "wudakouqiangpengbin");
					 item.put("5", "17");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "牙体牙髓科");
					 item.put("1", "王蓉主任医师");
					 item.put("2", "显微根管治疗及各种牙体疾病再治疗");
					 item.put("3", "王蓉，武汉大学口腔医院牙体牙髓二科主治医师。");
					 item.put("4", "rongrongyn");
					 item.put("5", "18");
					 blockdata.add(item);
					break;
				case 6:
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔外科");
					 item.put("1", "赵吉宏");
					 item.put("2", "血管瘤、血管畸形口腔肿瘤、术后修复口腔外科手术问题");
					 item.put("3", "赵吉宏，武汉大学口腔医院口腔外科主任、主任医师。全国脉管疾病学组副组长，全国牙槽外科学组副组长，全国口腔整形美容学会常务理事。");
					 item.put("4", "zhaojihong5166");
					 item.put("5", "19");
					 blockdata.add(item);
					 item= new Hashtable<String,String>();
					 item.put("0", "口腔外科");
					 item.put("1", "蔡育副主任医");
					 item.put("2", "颌面部美容整形、血管瘤治疗、牙槽外科");
					 item.put("3", "蔡育，武汉大学口腔医院口腔外科副主任医师。");
					 item.put("4", "caiyu_20012");
					 item.put("5", "20");
					 blockdata.add(item);
					break;
				case 7:
					 item= new Hashtable<String,String>();
					 item.put("0", "种植中心");
					 item.put("1", "施斌主任");
					 item.put("2", "颌面部美容整形、血管瘤治疗、牙槽外科");
					 item.put("3", "施斌，武汉大学口腔医院种植科主任。");
					 item.put("4", "wuhan_dentist");
					 item.put("5", "21");
					 blockdata.add(item);
					break;
				case 8:
					 item= new Hashtable<String,String>();
					 item.put("0", "特诊科");
					 item.put("1", "陈群主任医师");
					 item.put("2", "义齿修复治疗");
					 item.put("3", "陈群，武汉大学口腔医院口腔修复主任医师、教授。");
					 item.put("4", "chen1105410923");
					 item.put("5", "22");
					 blockdata.add(item);
					break;
					
			default:
				break;
			}
			
			ListDate();
	}
	
	
	public void ListDate() {
		ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < blockdata.size(); i++) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			//itemMap.put("image_left", R.drawable.oficial_green);
			itemMap.put("name",blockdata.get(i).get("1"));
			itemMap.put("specialty", blockdata.get(i).get("2"));
			itemMap.put("summary", blockdata.get(i).get("3"));
			itemMap.put("images",image[Integer.parseInt( blockdata.get(i).get("5").toString())]);
			mData.add(itemMap);
		}
		SimpleAdapter adapter = new SimpleAdapter(HomeActivity.this, mData,
				R.layout.home_list, new String[] {
						"name", "specialty","summary" ,"images"}, new int[] {
						R.id.Home_List_Name,
						R.id.Home_List_Specialty,
						R.id.Home_List_summary,
						R.id.Home_List_Image});

		listView.setAdapter(adapter);
		// 点击触发
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			Intent intent;

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
					intent = new Intent(HomeActivity.this,
							ContentActivity.class);
					intent.putExtra("division", blockdata.get(arg2).get("0"));//传递科室
					intent.putExtra("name", blockdata.get(arg2).get("1"));//传递姓名
					intent.putExtra("specialty", blockdata.get(arg2).get("2"));//传递专长
					intent.putExtra("summary", blockdata.get(arg2).get("3"));//传递简介
					intent.putExtra("weibo", blockdata.get(arg2).get("4"));//传递微博
					intent.putExtra("image", blockdata.get(arg2).get("5"));//传递头像
					intent.putExtra("key", keyString);
					startActivity(intent);
					finish();
					
			}
		});
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.Home_Protect:
			text(0);
			keyString = "0";
			break;
		case R.id.Home_Cycle:
			text(1);
			keyString = "1";
			break;
		case R.id.Home_Positive:
			text(2);
			keyString = "2";
			break;
		case R.id.Home_Repair:
			text(3);
			keyString = "3";
			break;
		case R.id.Home_Child:
			text(4);
			keyString = "4";
			break;
		case R.id.Home_Tooth:
			text(5);
			keyString = "5";
			break;
		case R.id.Home_Surgical:
			text(6);
			keyString = "6";
			break;
		case R.id.Home_Crop:
			text(7);
			keyString = "7";
			break;
		case R.id.Home_Special:
			text(8);
			keyString = "8";
			break;
		default:
			break;
		}
	}

	
	
	
}
