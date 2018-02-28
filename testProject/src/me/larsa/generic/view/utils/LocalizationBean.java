package me.larsa.generic.view.utils;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean
public class LocalizationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6678248337221920035L;
	
	private Locale currentLocal;

	private String localCode;
	
	
	private ResourceBundle template;
	private ResourceBundle newRequest;
	
	
	public LocalizationBean() {
		currentLocal = new Locale("ar");
		localCode="ar";
		
	}

	
	public void changeLocaliztion(String localCode) {
		currentLocal = new Locale(localCode);
		this.localCode=localCode;
		FacesContext.getCurrentInstance().getViewRoot().setLocale(currentLocal);
		Locale.setDefault(currentLocal);
	}
	public Locale getCurrentLocal() {
		return currentLocal;
	}

	public void setCurrentLocal(Locale currentLocal) {
		this.currentLocal = currentLocal;
	}


	public String getLocalCode() {
		return localCode;
	}


	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}


	public ResourceBundle getTemplate() {
		return template;
	}


	public void setTemplate(ResourceBundle template) {
		this.template = template;
	}


	public ResourceBundle getNewRequest() {
		return newRequest;
	}


	public void setNewRequest(ResourceBundle newRequest) {
		this.newRequest = newRequest;
	}
	
}
