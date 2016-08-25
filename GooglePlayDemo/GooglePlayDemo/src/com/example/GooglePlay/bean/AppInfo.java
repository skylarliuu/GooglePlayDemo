package com.example.GooglePlay.bean;

import java.util.ArrayList;

/**应用信息
 * @author Administrator
 *
 */
public class AppInfo {

	public String id;
	public String name;
	public String packageName;
	public String iconUrl;
	public float stars;
	public long size;
	public String downloadUrl;
	public String des;
	
	//应用详情页补充
	public String author;
    public String date;
    public String downloadNum;
    public ArrayList<SafeInfo> SafeInfo;
    public String version;
    public ArrayList<String> screen;
    
    public static  class SafeInfo{
    	public String safeDes;
        public String safeDesUrl;
        public String safeUrl;
    }

}
