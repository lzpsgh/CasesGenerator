/**
 * 用于向TD发送GBK编码的post请求
 * 用例集源文件 参考Config.dstTDFile
 * 
 * @author lzp
 * @date  2018/03/13
 */

/*
 * 请求体的内容如下
 * String body = "{0: \"0:int:17\", 1: \"0:conststr:542332-1029926515\", 2: \"0:conststr:{SUBJECT:6046,TYPE:MANUAL,NAME:2-GBK2UTF-单行-conststr恒定,TESTER:lzpeng8,METHOD:CREATE,NEW_VALUES:\\\"{TS_STATUS:\\\\\\\"1-提交\\\\\\\"}\\\"}\", 3: \"65536:str:\"}";
 * 不要求有换行符，但对每个 \符号 和 "符号 转义时需小心，
 * 
 * 部分无48域的交易需要手动在TD删用例，不光界面卡，而且删完还自动置顶定位
 * 由于响应码封装在HttpUtil中，暂不提供非200响应时的报文重发功能
 */

package com.grgbanking.testing.local;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.util.HttpUtil;
import com.grgbanking.testing.util.LogUtil;

public class TDPoster {

	String conststr;
	String caseName;
	static Map<String, String> headers = new HashMap<String, String>();
	static final String URL = "http://10.1.3.110/tdbin/wcomsrv.dll/TDAPI_GeneralWebTreatment?Zipped=0"; // URL后面不要接HTTP版本号

	static String bodyPre = "{0: \"0:int:17\", 1: \"0:conststr:" + Config.postConststr + "\", 2: \"0:conststr:{SUBJECT:"
			+ Config.postSubject + ",TYPE:MANUAL,NAME:";
	static String bodySuf = ",TESTER:" + Config.postTester
			+ ",METHOD:CREATE,NEW_VALUES:\\\"{TS_STATUS:\\\\\\\"1-新建\\\\\\\"}\\\"}\", 3: \"65536:str:\"}";

	public TDPoster() {
		initHeader();
	}

	public String getConststr() {
		return conststr;
	}

	public TDPoster setConststr(String conststr) {
		this.conststr = conststr;
		return this;
	}

	public String getCaseName() {
		return caseName;
	}

	public TDPoster setCaseName(String caseName) {
		this.caseName = caseName;
		return this;
	}

	private void initHeader() {
		if (headers == null) {
			LogUtil.d("请求头为空，首次组装请求头");
			headers = new HashMap<String, String>();
		}
		headers.put("User-Agent", "devSoft's ickHTTP Control");
		headers.put("Host", "10.1.3.110");
		headers.put("Connection", "Keep-Alive");
		headers.put("Content-Type", "text/html");
		// headers.put("Content-Length", "231");//TD不会校验请求头中的Content-Length
	}

	// 临时调试用
	public TDPoster singlePost(String bodyConststr, String bodyName) {
		if (bodyConststr == null || bodyName == null) {
			LogUtil.e("ERR: some value is null");
		}
		String body = bodyPre + bodyName + bodySuf;

		String rsp = null;
		try {
			LogUtil.i("开始发送请求");
			rsp = HttpUtil.post(URL, body, headers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (rsp.length() <= 0) {
			LogUtil.e("响应体为空，可能需要更新conststr值");
		} else {
			LogUtil.i("单条响应：\r\n" + rsp);
		}
		return this;
	}

	// 使用Config配置的默认值
	public void post() {
		String bodyConststr = Config.postConststr;
		post(bodyConststr);
		return;
	}

	// 使用自定义值
	public void post(String conststr) {
		String bodyConststr = conststr;
		BufferedReader bufferedReader = null;
		String tmpBodyName;
		String rsp = null;
		try {
			LogUtil.i("开始 发送请求");
			bufferedReader = new BufferedReader(new FileReader(Config.dstTDFile));
			while ((tmpBodyName = bufferedReader.readLine()) != null) {
				String body = bodyPre + tmpBodyName + bodySuf;
				rsp = HttpUtil.post(URL, body, headers);
				if (Config.postInterval > 0) {
					Thread.sleep(Config.postInterval * 1000); /* TD提交间隔 秒数 */
				}
				if (rsp.length() <= 0) {
					LogUtil.e("响应体为空，请检查conststr值");
				} else {
					LogUtil.i("提交用例名: " + tmpBodyName);
				}
			}
		} catch (IOException | InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		LogUtil.i("所有用例集提交完成");
		return;
	}

}
