package com.grgbanking.testing.util;

import com.grgbanking.testing.util.Config;

public class LogUtil {

	static String className;
	static String methodName;
	static int lineNumber;

	// public static final int DEBUG = 1;
	// public static final int INFO = 2;
	// public static final int ERROR = 3;
	// public static final boolean isPrintable = true;
	// public static final int LEVEL = 1;
	// 以上可删除
	
	private LogUtil() {
	}

	private static void getRef(StackTraceElement[] stElements) {
		className = stElements[1].getFileName();
		methodName = stElements[1].getMethodName();
		lineNumber = stElements[1].getLineNumber();
	}

	public static void e(String message) {
		if (Config.LEVEL >= 1) {
			getRef(new Throwable().getStackTrace());
			System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
		}
	}

	public static void i(String message) {
		if (Config.LEVEL >= 2) {
			getRef(new Throwable().getStackTrace());
			System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
		}
	}

	public static void d(String message) {
		if (Config.LEVEL >= 3) {
			getRef(new Throwable().getStackTrace());
			System.out.println(" 【" + className + "】【" + methodName + ":" + lineNumber + "】 " + message);
		}
	}

}
