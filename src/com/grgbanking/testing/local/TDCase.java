package com.grgbanking.testing.local;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.util.LogUtil;

public class TDCase extends AbsCommonCase{

	@Override
	public void make() {
		LogUtil.i("开始生成TD用例文件");
		int suffixLength = Config.suffixArray.length;
		for (int row = Config.rowBegin; row < Config.rowEnd; row++) {
			String txnID = super.tCaseList.get(row).getTxnID();
			String txnName = super.tCaseList.get(row).getTxnName();
			for (int suffix = 0; suffix < suffixLength; suffix++) {
				String temp = txnID + txnName + Config.suffixArray[suffix];
				LogUtil.i(temp);
				super.caseBuilder.append(temp).append("\r\n");
			}
			LogUtil.d("单趟拼接成功, excel行号为: " + (row + 1));
		}
		LogUtil.d("开始将用例写入文件");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(Config.dstTDFile));
			bufferedWriter.write(super.caseBuilder.toString());
			LogUtil.i("TD用例文件已生成");
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
