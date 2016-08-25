package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.AppInfo;

public class HomeProtocol extends BaseProtocol {

	private ArrayList<String> pictureList;

	@Override
	public String getKey() {
		return "home";
	}

	@Override
	public String getParams() {
		return "";//不能返回null
	}

	@Override
	public ArrayList<AppInfo> parseData(String result) {
		try {
			JSONObject jo = new JSONObject(result);
			JSONArray ja = jo.getJSONArray("list");
			
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
			
			//轮播条数据
			JSONArray ja1 = jo.getJSONArray("picture");
			pictureList = new ArrayList<String>();
			for(int j=0;j<ja1.length();j++){
				String url = ja1.getString(j);
				pictureList.add(url);
			}
			
			return appInfoList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * @return 首页轮播条数据
	 */
	public ArrayList<String> getPictureList(){
		return pictureList;
	}

}
