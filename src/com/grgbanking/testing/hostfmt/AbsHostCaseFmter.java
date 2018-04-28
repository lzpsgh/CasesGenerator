/**
 * 用于对仿真主机报文进行XML格式化与流程封装
 * 
 * @author lzp
 * @date  2018/03/13
 */

package com.grgbanking.testing.hostfmt;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.grgbanking.testing.util.LogUtil;

public abstract class AbsHostCaseFmter{

	StringBuilder sbInfant = null;
	StringBuilder sbSaiyan = null;
	
	public AbsHostCaseFmter() {
		if (sbInfant!=null || sbSaiyan!=null) {
			sbInfant = null;
			sbSaiyan = null;
		}
		sbInfant = new StringBuilder();
		sbSaiyan = new StringBuilder();
	}
	
	public void format() {
		joint();
		legalize();
		customValue();
	}
	
	/* 将输入转成单行 */
	void joint() {
		Scanner scanner = new Scanner(System.in);
		String line;
		while (true) {
			line = scanner.nextLine();
			if (line.equals("")) {
				break;
			} else {
				sbInfant.append(line);
			}
		}
		scanner.close();//TODO
	}
	
	void legalize(){
		int headIndex = sbInfant.indexOf("<");
		sbInfant.delete(0, headIndex);
		int tailIndex = sbInfant.lastIndexOf(">");
		sbInfant.delete(tailIndex + 1, sbInfant.length());
		final Pattern MY_PATTERN = Pattern.compile(">\\.+<");
		Matcher m = MY_PATTERN.matcher(sbInfant);
		sbInfant.replace(0, sbInfant.length(), m.replaceAll("><"));
//		LogUtil.d("regex replace: "+sbInfant.toString());
	}

	/* 去除标准XML前的长度值 */
	void cutHeadLen() {
		int headIndex = sbInfant.indexOf("<");
		sbInfant.delete(0, headIndex);
		LogUtil.d("cutHeadLen " + "success");
		return ;
	}

	/* 去除标准XML后的特殊字符 */
	void cutTail() {
		int tailIndex = sbInfant.lastIndexOf(">");
		sbInfant.delete(tailIndex + 1, sbInfant.length());
		LogUtil.d("cutTail " + "success");
		return ;
	}

	/* 去除两个不同XML标签之间的所有字符 */
	void rmTagDot() {
		final Pattern MY_PATTERN = Pattern.compile(">\\.+<");
		Matcher m = MY_PATTERN.matcher(sbInfant);
		sbInfant.replace(0, sbInfant.length(), m.replaceAll("><"));
		LogUtil.d("rmTagDot " + "success");
		return ;
	}

	void customValue() {
		String tmp = sbInfant.toString();
		StringReader reader = new StringReader(tmp);
		Document doc;
		XMLWriter writer = null;
		try {
			doc = new SAXReader().read(reader);
			/*使用treeWalk递归遍历,处理doc中的所有节点值*/
			treeWalk(doc.getRootElement());
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			/*是否对空元素的标签进行扩展，使得<a/>扩展还原成<a></a>*/
			formater.setExpandEmptyElements(true);
			formater.setIndentSize(4);
			formater.setSuppressDeclaration(false);
			StringWriter out = new StringWriter();
			writer = new XMLWriter(out, formater);
			writer.write(doc);
			writer.close();
			reader.close();
			sbSaiyan.replace(0, sbSaiyan.length(), out.toString());
			System.out.println(sbSaiyan.toString());
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if (writer != null) {
					writer.flush();
					writer.close();
				}
				if(reader != null){
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/*
	 * 递归所有子节点，子类自行实现相应操作
	 * */
	abstract void treeWalk(Element element);
	
}
