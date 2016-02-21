package com.entity;

public class Node {
	private int nID, status,group,flag;
	public Node(int nID, int status, String type,int group, int flag){
		this.nID = nID;
		this.status = status;
		this.group = group;
		this.flag = flag;
		this.type = type;
	}
	public int getnID() {
		return nID;
	}
	private String type;
	public void setnID(int nID) {
		this.nID = nID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
}
