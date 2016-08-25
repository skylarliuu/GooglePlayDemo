package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.AppInfo.SafeInfo;

public class HomeDetailProtocol extends BaseProtocol {

    private String packageName;
	
	public HomeDetailProtocol(String packageName){
		this.packageName = packageName;
	}
	
	@Override
	public String getKey() {
		return "detail";
	}

	@Override
	public String getParams() {
		return "&packageName="+packageName;
	}

	@Override
	public AppInfo parseData(String result) {
		try {
			JSONObject jo = new JSONObject(result);
			AppInfo info = new AppInfo();
			info.author = jo.getString("author");
			info.date = jo.getString("date");
			info.des = jo.getString("des");
			info.downloadNum = jo.getString("downloadNum");
			info.downloadUrl = jo.getString("downloadUrl");
			info.iconUrl = jo.getString("iconUrl");
			info.name = jo.getString("name");
			info.packageName = jo.getString("packageName");
			info.size = jo.getLong("size");
			info.stars = (float) jo.getDouble("stars");
			info.version = jo.getString("version");
			
			JSONArray safe = jo.getJSONArray("safe");			
			ArrayList<SafeInfo> safeInfoList = new ArrayList<SafeInfo>();
			for(int i=0;i<safe.length();i++){
				SafeInfo safeInfo = new SafeInfo();
				JSONObject jo1 = safe.getJSONObject(i);
				safeInfo.safeDes = jo1.getString("safeDes");
				safeInfo.safeDesUrl = jo1.getString("safeDesUrl");
				safeInfo.safeUrl = jo1.getString("safeUrl");
				safeInfoList.add(safeInfo);			
			}
			info.SafeInfo = safeInfoList;
			
			JSONArray screen = jo.getJSONArray("screen");
			ArrayList<String> screenList = new ArrayList<String>();
			for(int j=0;j<screen.length();j++){
				String url = screen.getString(j);
				screenList.add(url);
			}
			info.screen = screenList;
			
			return info;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
