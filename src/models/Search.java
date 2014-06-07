package models;

import java.sql.Date;
import java.util.ArrayList;

public class Search extends Bean{
	
	public static int COURSE = 0;
	public static int INSTITUTION = 1;
	
	private int id;
	private Date date;
	private int year;
	private int option;
	private String indicator;
	private int minValue;
	private int maxValue;
	
	
	public Search() {
		this.identifier="search";
		this.relationship="";
	}
	
	public Search(int id) {
		this.identifier="search";
		this.relationship="";
	}

	@Override
	public String get(String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(String field, String data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> fieldsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public void setId(int id) {
		this.id = id;
		
	}

}
