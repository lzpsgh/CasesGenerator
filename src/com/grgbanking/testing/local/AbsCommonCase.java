/**
 * 用于对excel文件进行预处理和流程封装
 *
 * @author lzp
 * @date  2018/03/13
 */

package com.grgbanking.testing.local;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.bean.TCase;
import com.grgbanking.testing.util.ExcelUtil;
import com.grgbanking.testing.util.LogUtil;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public abstract class AbsCommonCase {

	protected Sheet srcSheet = null;
	protected ArrayList<TCase> tCaseList = null ;
	protected StringBuilder caseBuilder = null; //最终生成的字符串

	public AbsCommonCase() {
		this.caseBuilder = new StringBuilder();
	}

	public void process() {
		this.from();
		this.calcRow();
		this.make();
	}

	public void from() {
		tCaseList = ExcelUtil.getCaseList();
	}

	/*
	 * 获得ArrayList<TCase>以后的核心操作，子类自行实现
	 * */
	abstract void make();


	// TODO 应该改用jxl库中自带的方法
	public void setRows(int rowBegin, int rowEnd) {
		if (rowBegin <= 0 || rowEnd <= 0 || rowBegin > rowEnd) {
			LogUtil.e("参数错误!");
			Config.rowBegin = 0;
			Config.rowEnd = 0;
		} 
			
		else {
			Config.rowBegin = rowBegin - 1;// jxl库默认是从0计算，所以实参rowBegin需要先减去1
			Config.rowEnd = rowEnd;
		}
	}

	public void calcRow(){
		if(Config.isCustomized == 1){
			//直接读取rowBegin和rowEnd的值
//			LogUtil.e("end=" + Config.rowEnd);
		}else if(Config.isCustomized == 0){
			//智能读取所有行
//			LogUtil.e("isCustomized=0");
			Config.rowBegin = 0;
			Config.rowEnd = tCaseList.size();
		}else {
			LogUtil.e("isCustomized值错误，只能是0或1");
			System.exit(0);
		}
	}
}
