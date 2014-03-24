package com.example.kouqiang;

import android.graphics.Bitmap;

public class DetailEntity {
    private String name;
    private String date;
    private String text;
    private int layoutID;
    private Bitmap imageID;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Bitmap getImageID() {
		return imageID;
	}
	public void setImageID() {
		this.imageID = imageID;
	}
	public int getLayoutID() {
		return layoutID;
	}
	public void setLayoutID(int layoutID) {
		this.layoutID = layoutID;
	}
	public DetailEntity() {
	}
	public DetailEntity(String name, String date, String text,Bitmap imageID, int layoutID) {
		super();
		this.name = name;
		this.date = date;
		this.text = text;
		this.imageID = imageID;
		this.layoutID = layoutID;
	}
	
}
