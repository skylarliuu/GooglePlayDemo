package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.SubjectInfo;

/**专题网络数据访问
 * @author Administrator
 *
 */
public class SubjectProtocol extends BaseProtocol {

	@Override
	public String getKey() {
		return "subject";
	}

	@Override
	public String getParams() {
		return "";//不能返回null
	}

	@Override
	public ArrayList<SubjectInfo> parseData(String result) {
		try {			
			JSONArray ja = new JSONArray(result);
			
			ArrayList<SubjectInfo> sujectInfoList = new ArrayList<SubjectInfo>();
			for(int i=0;i<ja.length();i++){
				SubjectInfo info = new SubjectInfo();
				JSONObject jo = ja.getJSONObject(i);
				info.des = jo.getString("des");
				info.url = jo.getString("url");
				
				sujectInfoList.add(info);
			}
			
			return sujectInfoList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
