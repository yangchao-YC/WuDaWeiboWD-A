package com.example.kouqiang;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;
/*在AndroidManifest.xml 中加入 访问网络的权限。<uses-permission android:name="android.permission.INTERNET"/>
 * namespace和url 中 ip、port请尽量填写WIFI或者公网，因为如果你用手机去模拟上传，你的手机是连接不到内网的（如IP：192.168.1.1或 localhost），连接的域名部分不要用IP地址。
 * 注意Webservice服务器的Soap版本如“version=1.0”，所以客户端指定 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
 * String url= "http://134.192.44.105:8080/SSH2/service/IService?wsdl";
 * Url指的是你的webservice的地址.一般都是以***.wsdl或者***.?wsdl结束的...但是.需要注意的是..要去掉后面的.wsdl或者.?wsdl。（这个一般不会有人犯错吧！）。
 * 可能你用了所有方法也不能解决这个问题，这时你就要注意一下你调用的WevService是不是能够被ksoap2解析了
 * */
public class GetSoapData {
	/**
    //命名空间  
    private static final String NAMESPACE = "http://WebXml.com.cn/";  
    //WebService地址  
    private static final String URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";  
    //需要调用的方法名  
    private static final String METHOD_NAME = "getWeather";  
    private static String SOAP_ACTION = "http://WebXml.com.cn/getWeather";
    */
    private InputStream inputstream;
    private SoapObject result;
    private String resultstr;
    private XmlPullParser parser;
    private ArrayList<Hashtable<String, String>> blockdata = new ArrayList<Hashtable<String, String>>();
    /** 
     * @desc 获得信息
     * @return 信息列表
     */
	public ArrayList<Hashtable<String, String>> getSoapDataWithParam(String classname, ArrayList<String> returnparams){

		resultstr = classname;
    	
    	dealWithXml(returnparams);
    	
    	return blockdata;
    } 
	
	public void dealWithXml(ArrayList<String> params){
        try {
        	
			for (int i = 0; i < params.size(); i++) {
				parser = Xml.newPullParser();
				inputstream = new ByteArrayInputStream(resultstr.getBytes("UTF-8"));//把这个字符串变成对应的字节放入输入流中
				parser.setInput(inputstream, "UTF-8");//放入解析器内
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);//设置解析器的属性
				int event = parser.getEventType();//获得解析器的事件
				Log.v("paramthis", params.get(i).toString());
				LoopGetDataFromXml(event, params.get(i).toString());
			}
			
			Hashtable<String, String> item = new Hashtable<String, String>();
			item.put("result", resultstr);
			blockdata.add(item);
			
			Log.e("blockdata", blockdata.toString());
			Log.e("nulldata", String.valueOf(blockdata.get(2).size()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void LoopGetDataFromXml(int event, String param){
    	
    	int i = 0;
    	Hashtable<String, String> item = new Hashtable<String, String>();
    	
		while(event!=XmlPullParser.END_DOCUMENT){//没到字节流的未时不停循环
			try {
				switch(event){   
					case XmlPullParser.START_DOCUMENT: //
					break; 
					case XmlPullParser.START_TAG:
							if(param.equals(parser.getName())){//匹配
								item.put(String.valueOf(i), parser.nextText());
								Log.v("param", item.get(String.valueOf(i)));
								i++;
							}
					break; 
					case XmlPullParser.END_TAG:
					break;
				}
				
				event = parser.next();
			
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		if(item.size()>0){
			blockdata.add(item);
//		}
    }

}