package com.example.demo;


public class Employee {
    private int roll_no;
    private String emp_name;
	private String emp_region;
    private String applicable_patch_id;
    private String patch_compliance;
    private String date_of_completion;
    private String e_mail_id;


    
    public int getRoll_no() {
		return roll_no;
	}
	public void setRoll_no(int roll_no) {
		this.roll_no = roll_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getEmp_region() {
		return emp_region;
	}
	public void setEmp_region(String emp_region) {
		this.emp_region = emp_region;
	}
	public String getApplicable_patch_id() {
		return applicable_patch_id;
	}
	public void setApplicable_patch_id(String applicable_patch_id) {
		this.applicable_patch_id = applicable_patch_id;
	}
	public String getPatch_compliance() {
		return patch_compliance;
	}
	public void setPatch_compliance(String patch_compliance) {
		this.patch_compliance = patch_compliance;
	}
	public String getDate_of_completion() {
		return date_of_completion;
	}
	public void setDate_of_completion(String date_of_completion) {
		this.date_of_completion = date_of_completion;
	}
	public String getE_mail_id() {
		return e_mail_id;
	}
	public void setE_mail_id(String e_mail_id) {
		this.e_mail_id = e_mail_id;
	}
}