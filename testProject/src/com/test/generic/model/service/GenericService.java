package com.test.generic.model.service;

import java.io.Serializable;
import java.util.List;

public interface GenericService<Domain extends Serializable, IdClass extends Serializable> extends Serializable {

	public Domain insert(Domain domain);

	public void insert(List<Domain> domian);

	public boolean update(Domain domain);

	public boolean update(List<Domain> domain);

	public boolean delete(Domain domain);

	public boolean delete(List<Domain> domain);

	public boolean merge(Domain domain);

	public Domain insert(Domain domain, boolean audit);

	public void insert(List<Domain> domian, boolean audit);

	public boolean update(Domain domain, boolean audit);

	public boolean update(List<Domain> domain, boolean audit);

	public boolean delete(Domain domain, boolean audit);

	public boolean delete(List<Domain> domain, boolean audit);

	public boolean merge(Domain domain, boolean audit);

	public Domain getById(IdClass id);

	public List<Domain> getAll(Integer start, Integer pageSize);

	public Long getAllCount();

	public List<Domain> getbyValue(Integer start, Integer pageSize, String fieldName, Object fieldValue);

	public Long getbyValueCount(String fieldName, Object fieldValue);

	public List<Domain> getbyValues(Integer start, Integer pageSize, List<String> fieldNames, List<Object> fieldValues);

	public Long getbyValuesCount(List<String> fieldNames, List<Object> fieldValues);

	public List<Domain> getbyValueJoinTable(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue);

	public Long getbyValueJoinTableCount(String sonName, String fieldname, Object fieldValue);

	public List<Domain> getbyValuesJoinTable(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues);

	public Long getbyValuesJoinTableCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues);

	public List<Domain> getbyValueJoinTables(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue);

	public Long getbyValueJoinTablesCount(String sonName, String fieldname, Object fieldValue);

	public List<Domain> getbyValuesJoinTables(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues);

	public Long getbyValuesJoinTablesCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues);

	public List<Domain> getbyListOfValues(Integer start, Integer pageSize, String fieldname, List<Object> fieldValues);
}
