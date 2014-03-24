package com.example.kouqiang;

public class User {
	
	private int id;//主键ID
	private String infoID;//信息ID
	private String info;//详细信息
	private String doctorID;//医生ID
	
	/////////////getter and setter///////////////
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInfoID() {
		return infoID;
	}
	public void setInfoID(String infoID) {
		this.infoID = infoID;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDoctorID() {
		return doctorID;
	}
	public void setDoctorID(String doctorID) {
		this.doctorID = doctorID;
	}
}
