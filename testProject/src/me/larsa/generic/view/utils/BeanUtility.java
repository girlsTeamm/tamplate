package me.larsa.generic.view.utils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BeanUtility {
	
	public static Object getBean(String beanName){
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return appContext.getBean(beanName);
	}

}
