package com.test.view.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.test.generic.view.utils.BeanUtility;
import com.test.model.entity.Deparment;
import com.test.model.entity.Employee;
import com.test.model.service.DeparmentService;


@SessionScoped
@ManagedBean
public class DepartmentBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5724499491959046599L;

	private Deparment deparment;
	private DeparmentService deparmentService;
	List<Employee> employees =new ArrayList<Employee>(); 
	Employee employee;
	private boolean canEdit;
	public DepartmentBean() {
		super();
		employees = new ArrayList<Employee>();
		deparment = new Deparment();
		employee = new Employee();
		canEdit = false;
		deparmentService = (DeparmentService) BeanUtility.getBean("departmentService");
		// TODO Auto-generated constructor stub
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public void addDeparment() {
		for (Employee employee : employees){
			employee.setDeparment(deparment);
			deparment.getEmployees().add(employee);
		}
		deparmentService.insert(deparment);
		employees.clear();
		employees=new ArrayList<Employee>(0);
		
		//deparmentService.update(deparmentService.getAll(null, null));


	}
	public void addEmployee() {
		employees.add(employee);
		employee = new Employee();
	}
	
	public void deletDepartment(Deparment deparment){
	deparmentService.delete(deparment);
	}
	public void updateDepartment(Deparment deparment){
		deparmentService.getById(deparment.getId());
		}
	public List<Deparment> getListDepartment() {
		return deparmentService.getAll(null, null);
		

	}
	 public void onRowEdit(RowEditEvent event) {
		 	deparmentService.update(deparment);
	}
	     
	

	public Deparment getDeparment() {
		return deparment;
	}

	public void setDeparment(Deparment deparment) {
		this.deparment = deparment;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

}
