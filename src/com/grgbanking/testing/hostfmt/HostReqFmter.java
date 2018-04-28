package com.grgbanking.testing.hostfmt;

import org.dom4j.Element;
import org.dom4j.Node;

import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.util.LogUtil;

/*
 * 用于格式化xml
 * 自动生成域序号：少数域钦定，多数域序号递增
 * */

public class HostReqFmter extends AbsHostCaseFmter {

	int ITER = Config.mHostReqFmterITER;
	
	public HostReqFmter() {
		super();
		ITER = Config.mHostReqFmterITER;
		LogUtil.e("配置请求报文");
	}

	@Override
	void treeWalk(Element element) {
		
		String eName = element.getName();// 标签名
		String value = element.getText();// 标签值
		if (!(value.equals(""))) {
			switch (eName) {
			case "trade_code":
				element.setText("7");
				break;
			case "tx_date":
				element.setText("151");
				break;
			case "tx_time":
				element.setText("152");
				break;
			case "cli_serial_no":
				element.setText("31");
				break;
			case "file_flag":
				element.setText("160");
				break;
			case "mac_data":
				element.setText("163");
				break;
			default:
				element.setText(ITER+"");
				ITER++;
			}
		} else {
			if (!(element.hasContent() || element.hasMixedContent())) {// 防止给 已经内含子标签的标签中加上empty
				element.setText(ITER+"");
				ITER++;
			}
		}
		
		for (int i = 0, size = element.nodeCount(); i < size; i++) {
			Node node = element.node(i);
			if (node instanceof Element) {
				treeWalk((Element) node);
			}
		}
	}
}
