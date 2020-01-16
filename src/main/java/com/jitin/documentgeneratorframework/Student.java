package com.jitin.documentgeneratorframework;

public class Student {
	private String college;
	private Integer rollNumber;
	private String name;
	private Double percentage;
	private Boolean isPass;
	private Address address;

	public Student() {

	}

	public Student(String college, Integer rollNumber, String name, Double percentage, Boolean isPass,
			Address address) {
		super();
		this.college = college;
		this.rollNumber = rollNumber;
		this.name = name;
		this.percentage = percentage;
		this.isPass = isPass;
		this.address = address;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
