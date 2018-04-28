package com.grgbanking.testing.hostfmt;

import org.dom4j.Element;
import org.dom4j.Node;

import com.grgbanking.testing.util.LogUtil;

/*
 * 用于格式化xml
 * 指定域值：给非空域加上default=前缀或者给空域赋值为empty=null
 * */

public class HostRspFmter extends AbsHostCaseFmter {

	public HostRspFmter() {
		super();
		LogUtil.e("配置响应报文");
	}

	StringBuilder sbInfant = null;

	@Override
	void treeWalk(Element element) {

		String eName = element.getName();// 标签名
		String value = element.getText();// 标签值
		if (!(value.equals(""))) {
			switch (eName) {
			case "tx_code":
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
				element.setText("default=" + value);
			}

		} else {
			if (!(element.hasContent() || element.hasMixedContent())) {// 防止给 已经内含子标签的标签中加上empty
				element.setText("empty=keep");
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
