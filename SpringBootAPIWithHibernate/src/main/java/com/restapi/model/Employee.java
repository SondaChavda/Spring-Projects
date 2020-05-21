package com.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="employee_detail")
//@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {
	
	@Id
	@Column(name="id")
	@GeneratedValue()
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="salary")
	private Long salary;
	
	
}
