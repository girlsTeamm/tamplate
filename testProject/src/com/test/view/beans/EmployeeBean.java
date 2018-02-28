package com.test.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.test.generic.view.utils.BeanUtility;
import com.test.model.entity.Deparment;
import com.test.model.entity.Employee;
import com.test.model.service.EmployeeService;
import com.test.model.service.impl.EmployeeServiceImpl;

@SessionScoped
@ManagedBean
public class EmployeeBean implements Serializable {

	private Employee employee;
	private EmployeeService employeeService;
	

	public EmployeeBean() {
		super();
		employee = new Employee();
		employeeService = (EmployeeService) BeanUtility.getBean("employeeService");
	}

	public void addEmployee() {
		employeeService.insert(employee);

	}

	public void deletEmployee(Employee employee){
		employeeService.delete(employee);
		

	}

	public List<Employee> getListEmployee() {
		return employeeService.getAll(null, null);

	}
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
