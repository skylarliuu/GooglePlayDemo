package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.AppInfo;

public class AppProtocol extends BaseProtocol {

	@Override
	public String getKey() {
		return "app";
	}

	@Override
	public String getParams() {
		return "";//不能返回null
	}

	@Override
	public ArrayList<AppInfo> parseData(String result) {
		try {			
			JSONArray ja = new JSONArray(result);
			
			ArrayList<AppInfo> appInfoList = new ArrayList<AppInfo>();
			for(int i=0;i<ja.length();i++){
				AppInfo info = new AppInfo();
				JSONObject jo1 = ja.getJSONObject(i);
				info.id = jo1.getString("id");
				info.name = jo1.getString("name");
				info.packageName = jo1.getString("packageName");
				info.downloadUrl = jo1.getString("downloadUrl");
				info.des = jo1.getString("des");
				info.iconUrl = jo1.getString("iconUrl");
				info.size = jo1.getLong("size");
				info.stars = (float) jo1.getDouble("stars");
				appInfoList.add(info);
			}
			
			return appInfoList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
