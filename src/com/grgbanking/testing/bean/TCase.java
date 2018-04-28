package com.grgbanking.testing.bean;

public class TCase {

	int rowNo;
	String txnID;
	String txnName;
	String msgCode;
	String bkdCode;
	
	public TCase(){
		
	}
	public TCase(int no){
		this.rowNo = no;
	}

	public int getLineNo() {
		return rowNo;
	}

	public void setLineNo(int lineNo) {
		this.rowNo = lineNo;
	}

	public String getTxnID() {
		return txnID;
	}

	public void setTxnID(String txnID) {
		this.txnID = txnID;
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getBkdCode() {
		return bkdCode;
	}

	public void setBkdCode(String bkdCode) {
		this.bkdCode = bkdCode;
	}

}
