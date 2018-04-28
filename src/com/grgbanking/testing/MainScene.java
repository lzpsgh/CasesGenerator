/**
 * 主场景，实现不同功能
 *
 * @author LensAclrtn
 * @date  2018/03/13
 */

package com.grgbanking.testing;

import com.grgbanking.testing.local.*;
import com.grgbanking.testing.util.Config;
import com.grgbanking.testing.util.LogUtil;

/* 
 * Ver 0.6 ReleaseNote
 * 优化解析效率，简化调用参数
 * 统一《交易列表》格式，第1234列分别是：交易ID、交易名称、消息码、后台交易码
 * 使用外部CaseGen.ini文件进行参数配置
 * 
 * ver 0.8
 * TODO Fmter能自动对文件夹下的所有主机报文进行XML格式化
 * TODO Fmter 遍历读取 fmt-before 目录下文件，格式化后在 fmt-after 目录下依次生成新文件
 * TODO 自动生成unpack和pack文件夹
 * 
 * version 1.0
 * TODO 配置脚本能持续运行
 * TODO TDPoster post完所有用例后再发一个用于刷新列表的post
 * 
 * 隐患：TDPoster需要TDCase.txt文件，内容必须是GBK编码。而Swack和TD等本地文件必须是UTF-8编码。目前因为只有TDPoster需要中文的参数，所以将ini配置文件设置成gbk编码格式。后续如果本地文件也需要中文参数就有点麻烦
 * 
 * */

public class MainScene {

	public static void main(String[] args) {

		init();

		switch (args[0]) {
		// Swack用例配置
		case "1":
			new SwackCase().process();
			break;
		// TD用例名配置
		case "2":
			new TDCase().process();
			break;
		// TD用例名自动提交
		case "3":
			new TDPoster().post();
			break;
		// 仿真主机rule.xml打解包规则配置
		case "4":
			new HostRuleCase().process();
			break;
		// 仿真主机请求报文的自动格式化
		case "7":
			// new HostReqFmter().format();
			break;
		// 仿真主机响应报文自动格式化
		case "8":
			// new HostRspFmter().format();
			break;
		// 临时调试用
		case "9":
//			LogUtil.i("");
			break;
		case "0":
			LogUtil.i("Exit");
			break;
		default:
			break;
		}
	}

	public static void init() {
		new Config().loadIni();
	}
}
