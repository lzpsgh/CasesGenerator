/**
 * 配置文件，包含大部分配置参数
 *
 * @author lzp
 * @date  2018/03/13
 */

package com.grgbanking.testing.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.grgbanking.testing.util.IniEditor;
import com.grgbanking.testing.util.LogUtil;

public final class Config {
	public IniEditor iniEditor = new IniEditor();

	public Config() {
	}

	public static String srcFile ; //交易列表XLS文件路径
	public static int isCustomized ; //是否指定开始行和结束行，如果填1后面两个选项生效，如果填0默认读取所有行
	public static int rowBegin = 0; // 开始行（对应excel表）
	public static int rowEnd = 237; // 结束行（参考excel表）

	/* Swack */
	public static String dstSwackFile = "./SwackCases.txt"; // 输出文件路径
	public static int firstCaseNo = 10; /* 初始caseNo */
	public static int caseNoInvl = 4; /* caseNo间隔为4，方便后续手动新增用例 */

	/* TD */
	public static String dstTDFile = "./TDCases.txt"; // 输出文件路径
	public static String[] suffixArray;

	/* TDPoster */
	public static int postInterval = 0; // TD提交的时间间隔，如时间不紧急建议改为3或以上，以免出现用例状态为空的异常情况
	public static String postTester = "lzpeng8"; // 测试人，以TD显示为准
	public static String postSubject = "6205"; // 代表用例所在的文件夹
	public static String postConststr; // 重启TD后需重新用WireShark抓包获取，否则提交失败。

	/* HostRuleCase */
	public static String dstRuleFile; // 输出文件路径

	/* HostReqFmter */
	public static int mHostReqFmterITER = 200;

	/* LogUtil */
	public static int LEVEL ; // 日志等级：0不打印，1严重，2一般，3调试

	// 程序启动后获取ini文件所有值并赋值给Config的变量
	public void loadIni() {

		if (iniEditor == null) {
			iniEditor = new IniEditor();
		}
		try {
			iniEditor.load("./CaseGen.ini");
			LogUtil.d("加载ini配置文件");
		} catch (IOException e) {
			e.printStackTrace();
		}

		applyIni();
	}

	private void applyIni() {

		//Common
		srcFile = iniEditor.get("common", "srcFile");
		isCustomized = Integer.parseInt(iniEditor.get("common", "isCustomized"));
		rowBegin = Integer.parseInt(iniEditor.get("common", "rowBegin"));
		rowEnd = Integer.parseInt(iniEditor.get("common", "rowEnd"));

		//Swack
		dstSwackFile = iniEditor.get("Swack", "dstSwackFile");
		firstCaseNo = Integer.parseInt(iniEditor.get("Swack", "firstCaseNo"));
		caseNoInvl = Integer.parseInt(iniEditor.get("Swack", "caseNoInvl"));

		//TD
		dstTDFile = iniEditor.get("TD", "dstTDFile");
		// suffixArray = iniEditor.get("TD", "suffixArray");
		suffixArray = setArray();

		//TDPoster
		postInterval = Integer.parseInt(iniEditor.get("TDPoster", "postInterval"));
		postTester = iniEditor.get("TDPoster", "postTester");
		postSubject = iniEditor.get("TDPoster", "postSubject");
		postConststr = iniEditor.get("TDPoster", "postConststr");

		//HostRuleCase
		dstRuleFile = iniEditor.get("HostRuleCase", "dstRuleFile");

		//HostReqFmter
		mHostReqFmterITER = Integer.parseInt(iniEditor.get("HostReqFmter", "mHostReqFmterITER"));

		//LogUtil
		LEVEL = Integer.parseInt(iniEditor.get("Log", "LEVEL"));
		
		LogUtil.d("应用ini配置文件");
	}
	
	private String[] setArray(){
		String sufStr = iniEditor.get("TD", "sufArray");
		String[] sufArray = sufStr.split(":");
		return sufArray;
	}

}
