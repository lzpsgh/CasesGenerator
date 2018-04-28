package com.grgbanking.testing.local;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.local.AbsCommonCase;
import com.grgbanking.testing.util.LogUtil;


public class HostRuleCase extends AbsCommonCase {

	@Override
	void make() {
		BufferedWriter bufferedWriter = null;
		try {
			LogUtil.i("配置rule.xml-开始");
			bufferedWriter = new BufferedWriter(new FileWriter(Config.dstRuleFile));
//			bufferedWriter = new BufferedWriter(new FileWriter(PropertiesUtil.getProperty("dstRuleFile")));
			
			LogUtil.i("解包规则生成-开始");
			for (int row = Config.rowBegin; row < Config.rowEnd; row++) {
				String txnID = super.tCaseList.get(row).getTxnID();
				String bkdCode = super.tCaseList.get(row).getBkdCode();
				if(bkdCode.equals("null")){
					continue;
				}
				//生成unpack解包规则    <file check="/cfx/head/trade_code=62060103">861_request.xml</file>
				String unpackCase = new StringBuilder("<file check=\"/cfx/head/trade_code=" + bkdCode + "\">" + txnID + "_request.xml</file>" ).toString();
				LogUtil.i("unpackCase=" + unpackCase);
				bufferedWriter.write(unpackCase);
				bufferedWriter.newLine();
				
			}
			bufferedWriter.newLine();
			LogUtil.i("解包规则生成-完成");
			
			LogUtil.i("打包规则生成-开始");
			for (int row = Config.rowBegin; row < Config.rowEnd; row++) {
				String txnID = super.tCaseList.get(row).getTxnID();
				String bkdCode = super.tCaseList.get(row).getBkdCode();
				if(bkdCode.equals("null")){
					continue;
				}
				//生成pack打包规则    <file check="7=12001761">904_response.xml</file>
				String packCase = new StringBuilder("<file check=\"7=" + bkdCode + "\">" + txnID + "_response.xml</file>").toString();
				LogUtil.i("packCase=" + packCase);
				bufferedWriter.write(packCase);
				bufferedWriter.newLine();
			}
			bufferedWriter.newLine();
			LogUtil.i("打包规则生成-完成");
			
			LogUtil.i("配置rule.xml-完成");
		} catch (IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
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
