package com.entity;

public class Edge {
	private int nID1;
	private int nID2;
	public Edge(int node1, int node2) {
		this.nID1 = node1;
		this.nID2 = node2;
	}
	public int getNode1() {
		return nID1;
	}
	public void setNode1(int node1) {
		this.nID1 = node1;
	}
	public int getNode2() {
		return nID2;
	}
	public void setNode2(int node2) {
		this.nID2 = node2;
	}
}
