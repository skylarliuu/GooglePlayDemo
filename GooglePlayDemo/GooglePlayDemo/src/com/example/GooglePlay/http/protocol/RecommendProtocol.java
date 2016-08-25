package com.example.GooglePlay.http.protocol;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.GooglePlay.bean.AppInfo;
import com.example.GooglePlay.bean.SubjectInfo;

/**推荐网络数据访问
 * @author Administrator
 *
 */
public class RecommendProtocol extends BaseProtocol {

	@Override
	public String getKey() {
		return "recommend";
	}

	@Override
	public String getParams() {
		return "";//不能返回null
	}

	@Override
	public ArrayList<String> parseData(String result) {
		try {			
			JSONArray ja = new JSONArray(result);
			
			ArrayList<String> infoList = new ArrayList<String>();
			for(int i=0;i<ja.length();i++){
				String appName = ja.getString(i);
				
				infoList.add(appName);
			}
			
			return infoList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
