package com.test.generic.model.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends Serializable, IdClass extends Serializable> extends Serializable {

	public T insert(T t);

	public List<T> insert(List<T> t);

	public boolean update(T t);

	public boolean update(List<T> t);

	public boolean delete(T t);

	public boolean delete(List<T> t);

	public boolean merge(T t);

	public T insert(T t, boolean audit);

	public List<T> insert(List<T> t, boolean audit);

	public boolean update(T t, boolean audit);

	public boolean update(List<T> t, boolean audit);

	public boolean delete(T t, boolean audit);

	public boolean delete(List<T> t, boolean audit);

	public boolean merge(T t, boolean audit);

	public T getById(IdClass id);

	public List<T> getAll(Integer start, Integer pageSize);

	public Long getAllCount();

	public List<T> getbyValue(Integer start, Integer pageSize, String fieldname, Object fieldValue);

	public Long getbyValueCount(String fieldname, Object fieldValue);

	public List<T> getbyValues(Integer start, Integer pageSize, List<String> fieldnames, List<Object> fieldValues);

	public Long getbyValuesCount(List<String> fieldnames, List<Object> fieldValues);

	public List<T> getbyValueJoinTable(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue);

	public Long getbyValueJoinTableCount(String sonName, String fieldname, Object fieldValue);

	public List<T> getbyValuesJoinTable(Integer start, Integer pageSize, List<String> sonNames, List<String> fieldnames,
			List<Object> fieldValues);

	public Long getbyValuesJoinTableCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues);

	public List<T> getbyValueJoinTables(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue);

	public Long getbyValueJoinTablesCount(String sonName, String fieldname, Object fieldValue);

	public List<T> getbyValuesJoinTables(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues);

	public Long getbyValuesJoinTablesCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues);

	public List<T> getbyListOfValues(Integer start, Integer pageSize, String fieldname, List<Object> fieldValues);
}
