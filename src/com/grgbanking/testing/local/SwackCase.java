package com.grgbanking.testing.local;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.util.LogUtil;

public class SwackCase extends AbsCommonCase{

	/*
	 * 以下变量务必小心修改
	 * 如要修改相应域的域值指令，直接修改caseSeg6和caseSeg7的值即可
	 * 如要新增和删除域，需要同时修改caseSeg6和caseSeg7，以及caseSeg5
	 * */
	private final String caseSeg1 = "<case><caseno>";
	private final String caseSeg2 = "</caseno><folderno>";
	private final int folderNo = 0; 
	private final String caseSeg3 = "</folderno><casename>";
	private final String caseSeg4 = "</casename><comment></comment>";
	private final String caseSeg5 = "<casefields fields=\"$-1$0$1$2$3$4$7$11$12$13$22$23$25$26$35$36$41$43$48$49$52$53$55$60$102$103$128\">";
	private final String caseSeg6 = "<field fieldnum=\"-1\">0000000000</field><field fieldnum=\"0\">0200</field><field fieldnum=\"1\">auto</field><field fieldnum=\"2\">6210001111155555</field><field fieldnum=\"3\">";
	private final String caseSeg7 = "</field><field fieldnum=\"4\">000000400000</field><field fieldnum=\"7\">datetime=MMDDhhmmss</field><field fieldnum=\"11\">func=iRandom</field><field fieldnum=\"12\">datetime=hhmmss</field><field fieldnum=\"13\">datetime=MMDD</field><field fieldnum=\"22\">051</field><field fieldnum=\"23\">123</field><field fieldnum=\"25\">02</field><field fieldnum=\"26\">12</field><field fieldnum=\"35\">6223595390112563463=1608101</field><field fieldnum=\"41\">20000010</field><field fieldnum=\"43\">grgbankinggrgbankinggrgbankinggrgbanking</field><field fieldnum=\"48\">48484848</field><field fieldnum=\"49\">156</field><field fieldnum=\"52\">func=iGenPinData(111111)</field><field fieldnum=\"53\">2600000000000000</field><field fieldnum=\"55\">55555</field><field fieldnum=\"60\">000005000100000000000022000</field><field fieldnum=\"128\">func=iGenMacData</field></casefields></case>";
	
	/**
	 * 用jxl库自带方法可进一步精简
	 */
	@Override
	public void make() {
		LogUtil.i("开始生成Swack用例文件");
		int caseNo = Config.firstCaseNo;
		int caseNoInvl = Config.caseNoInvl;
		int row;
		for (row = Config.rowBegin; row < Config.rowEnd; row++, caseNo = caseNo + caseNoInvl) {
			LogUtil.d("caseNo=" + caseNo);
			String txnID = super.tCaseList.get(row).getTxnID();
			String txnName = super.tCaseList.get(row).getTxnName();
			String msgCode = super.tCaseList.get(row).getMsgCode();					
			String caseName = txnID+txnName+"-"+msgCode;
			LogUtil.i("caseName=" + caseName);
			caseBuilder.append(caseSeg1).append(caseNo).append(caseSeg2)
						.append(folderNo).append(caseSeg3).append(caseName)
						.append(caseSeg4).append(caseSeg5).append(caseSeg6)
						.append(msgCode).append(caseSeg7);
			LogUtil.d("单趟拼接成功，内部行号：" + row);
		}
		LogUtil.d("Swack用例拼接成功,最后处理的内部行号：" + row);
		LogUtil.d("开始将用例写入文件");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(Config.dstSwackFile));
			bufferedWriter.write(super.caseBuilder.toString());
			LogUtil.i("Swack用例文件已生成");
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
