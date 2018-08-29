package com.simpsoft.salesCommission.app.UImodel;

public class Person {
	private String condition;
	private String parameter;
    private String name;
    private String age;
    
 
    /**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public Person() {
    }


    public Person(String name, String age, String condition, String parameter) {
        this.name = name;
        this.age = age;
        this.condition=condition;
        this.parameter=parameter;
        
    }
}
