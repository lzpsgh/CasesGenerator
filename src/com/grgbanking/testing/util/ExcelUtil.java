package com.grgbanking.testing.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.bean.TCase;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelUtil {

	static Sheet srcSheet = null;
	static ArrayList<TCase> tCaseList = new ArrayList<TCase>();

	private ExcelUtil() {
	}

	// 直接返回Sheet工作表
	public static Sheet getSheet() {
//		File folder = new File("./excel");
//		File[] files = folder.listFiles();
//		if (files == null) {
//			LogUtil.e("请将《交易列表》拷贝到excel文件夹中");
//			System.exit(0);
//		}
//		File file = files[0];// 只打开匹配到的第一个xls文件
		
		File file = new File(Config.srcFile);
		if (!(file.getName().endsWith("xls"))) {
			LogUtil.e("请将《交易列表》另存为xls格式");
			System.exit(0);
		}
		try {
			srcSheet = Workbook.getWorkbook(file).getSheet(0);
			LogUtil.d("工作簿打开成功");
		} catch (IndexOutOfBoundsException | BiffException | IOException e) {
			e.printStackTrace();
		}
		int casesSum = srcSheet.getRows() - 1;
		LogUtil.d("该工作表总行数是: " + casesSum);
//		Config.rowEnd = casesSum;
		return srcSheet;
	}

	// 将Sheet转为List后再返回
	public static ArrayList<TCase> getCaseList() {
		if (srcSheet == null) {
			srcSheet = getSheet();
		}
		if (tCaseList == null) {
			tCaseList = new ArrayList<TCase>();
		}
		int casesSum = srcSheet.getRows() - 1;
		for (int rowNo = 0; rowNo < casesSum; rowNo++) {
			TCase tCase = new TCase(rowNo);
			// LogUtil.e("行号: "+tCase.getLineNo());
			tCase.setTxnID(srcSheet.getCell(0, rowNo).getContents().trim());
			// LogUtil.e("交易ID: "+tCase.getTxnID());
			tCase.setTxnName(srcSheet.getCell(1, rowNo).getContents().trim());
			// LogUtil.e("交易名: "+tCase.getTxnName());
			tCase.setMsgCode(srcSheet.getCell(2, rowNo).getContents().trim());
			// LogUtil.e("消息码: "+tCase.getMsgCode());
			tCase.setBkdCode(srcSheet.getCell(3, rowNo).getContents().trim());
			// LogUtil.e("后台交易码: "+tCase.getBkdCode());
			tCaseList.add(tCase);
		}
		return tCaseList;
	}
}
