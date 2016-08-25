package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.CategoryInfo;

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {

	private ArrayList<CategoryInfo> infoList;

	@Override
	public String getKey() {
		return "category";
	}

	@Override
	public String getParams() {
		return "";
	}

	@Override
	public ArrayList<CategoryInfo> parseData(String result) {
		try {
			JSONArray ja = new JSONArray(result);
			
			infoList = new ArrayList<CategoryInfo>();
			for(int i=0;i<ja.length();i++){
				CategoryInfo titleInfo = new CategoryInfo();
				JSONObject jo = ja.getJSONObject(i);
				
				if(jo.has("title")){
					titleInfo.isTitle = true;
					titleInfo.title = jo.getString("title");
					infoList.add(titleInfo);
				}
				
				if(jo.has("infos")){
					JSONArray ja1 = jo.getJSONArray("infos");
					for(int j=0;j<ja1.length();j++){
						CategoryInfo info = new CategoryInfo();
						JSONObject jo1 = ja1.getJSONObject(j);
						info.name1 = jo1.getString("name1");
						info.name2 = jo1.getString("name2");
						info.name3 = jo1.getString("name3");
						info.url1 = jo1.getString("url1");
						info.url2 = jo1.getString("url2");
						info.url3 = jo1.getString("url3");
						
						info.isTitle = false;
						infoList.add(info);
					}					
				}
			}				
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return infoList;
	}

}
