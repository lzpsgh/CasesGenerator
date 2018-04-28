package com.grgbanking.testing.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @desc properties 资源文件解析工具
 * @author lzpeng8
 * @datatime Mar 16, 2018 19:30:45 PM
 * 
 *           String aaa = PropertiesUtil.getProperty("aaa");
 */
public class PropertiesUtil {

	private static Properties props = null;
	private static InputStream fis = null;
	private static BufferedReader br = null;
	// private URI uri;

	private PropertiesUtil() {
	}

	public static String getProperty(String key) {
		if (fis == null) {
			try {
//				fis = new FileInputStream(new File("./CaseGen-utf8.properties"));
//				BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./CaseGen-utf8.properties"), "UTF-8")));//网上用来统一编码的
//				br = new BufferedReader(new FileReader("./CaseGen-utf8.properties"));
				br = new BufferedReader(new FileReader("D:/CaseGen-utf8.ini"));
				LogUtil.d("fis应该只出现一次");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if (props == null) {
			props = new Properties();
			try {
				props.load(br);
				LogUtil.d("props应该只出现一次");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props.getProperty(key);
	}

	/**
	 * 获取所有属性，返回一个map,不常用
	 */
	public Map getAllProperty() {
		Map map = new HashMap();
		Enumeration enu = props.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = props.getProperty(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 在控制台上打印出所有属性，调试时用。
	 */
	public void printProperties() {
		props.list(System.out);
	}

}