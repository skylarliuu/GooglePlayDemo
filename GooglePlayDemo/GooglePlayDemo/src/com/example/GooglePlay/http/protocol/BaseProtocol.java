package com.example.GooglePlay.http.protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.example.GooglePlay.http.HttpHelper;
import com.example.GooglePlay.http.HttpHelper.HttpResult;
import com.example.GooglePlay.utils.IOUtils;
import com.example.GooglePlay.utils.UIUtils;

/**
 * 访问网络的基类 获取网络数据-缓存机制-解析数据
 * 
 * @author Administrator
 * @param <T>
 * 
 */
public abstract class BaseProtocol<T> {

	public T getData(int index) {
		// 获取网络缓存
        String result = getCache(index);
        if(result == null){
        	//缓存不存在或者过期时访问网络数据
        	result = getDataFromServer(index);	       	
        }
        
        if(result!= null){
        	//解析数据
        	return parseData(result);
        }
        return null;
	}


	/**
	 * 访问网络链接 获取数据
	 * 
	 * @param index
	 *            分页算法访问页面的起始位置
	 * @return 返回访问到的数据字符串
	 */
	private String getDataFromServer(int index) {
		// http://127.0.0.1:8090/home?index=index&name=zhangsan&age=18
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
				+ "?index=" + index + getParams());
		if (httpResult != null) {
			String result = httpResult.getString();

			//将数据写入缓存中
			setCache(index, result);
			
			return result;
		}
		return null;
	}

	// 写缓存
	public void setCache(int index, String result) {
		// 获取到系统的缓存目录
		File cacheDir = UIUtils.getContext().getCacheDir();
		// 以URL作为文件名新建文件
		File file = new File(cacheDir, getKey() + "?index=" + index
				+ getParams());
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			// 写入缓存的有效期,有效期半个小时
			long deadline = System.currentTimeMillis() + 30 * 60 * 1000;
			writer.write(deadline + "\n");//将缓存时间写入文件第一行
			// 将数据写入缓存
			writer.write(result);

			writer.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(writer);
		}

	}

	// 读缓存
	public String getCache(int index) {
		// 获取到系统的缓存目录
		File cacheDir = UIUtils.getContext().getCacheDir();
		// 以URL作为文件名新建文件
		File file = new File(cacheDir, getKey() + "?index=" + index
				+ getParams());
		
		if(file.exists()){
		    //缓存文件存在
			BufferedReader reader = null ;
			try {
				reader = new BufferedReader(new FileReader(file));
				//读取缓存时间
				String deadline = reader.readLine();
				long deadtime = Long.parseLong(deadline);
				if(System.currentTimeMillis() < deadtime){
					//缓存有效
					String line;
					StringBuffer buffer = new StringBuffer();
					while((line = reader.readLine())!= null){
						buffer.append(line);
					}
					return buffer.toString();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				IOUtils.close(reader);
			}
			
		}
		return null;
	}

	// 由子类去实现具体的网络连接的具体地址
	public abstract String getKey();

	// 具体参数
	public abstract String getParams();
    
	//解析数据，返回数据的类型也由子类决定
	public abstract T parseData(String result);
}
