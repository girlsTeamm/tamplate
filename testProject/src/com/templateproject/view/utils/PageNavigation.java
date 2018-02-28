package com.templateproject.view.utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class PageNavigation  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 621386652654080971L;
	private String page = "/homePage.xhtml";

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	

	@PostConstruct
	public void init(){
		
	}
	
	public PageNavigation(){
		
	}
	
	public void navigate(String page){
		this.page=page;
		
	}
	
	public boolean isActive(String page){
		if(page.equalsIgnoreCase(this.page))
			return true;
		else
			return false;
	}
	
	
	

}
