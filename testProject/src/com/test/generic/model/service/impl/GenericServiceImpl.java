package com.test.generic.model.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.test.generic.model.dao.GenericDao;
import com.test.generic.model.service.GenericService;

public class GenericServiceImpl<Domain extends Serializable, Dao extends Serializable, IdClass extends Serializable>
		implements GenericService<Domain, IdClass> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Dao dao;

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Domain insert(Domain domain) {
		return ((GenericDao<Domain, IdClass>) dao).insert(domain);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void insert(List<Domain> domian) {
		((GenericDao<Domain, IdClass>) dao).insert(domian);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean update(Domain domain) {
		return ((GenericDao<Domain, IdClass>) dao).update(domain);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean merge(Domain domain) {
		return ((GenericDao<Domain, IdClass>) dao).merge(domain);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean update(List<Domain> domain) {

		return ((GenericDao<Domain, IdClass>) dao).update(domain);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean delete(Domain domain) {
		return ((GenericDao<Domain, IdClass>) dao).delete(domain);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean delete(List<Domain> domain) {
		return ((GenericDao<Domain, IdClass>) dao).delete(domain);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Domain insert(Domain domain, boolean audit) {
		return ((GenericDao<Domain, IdClass>) dao).insert(domain, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void insert(List<Domain> domian, boolean audit) {
		((GenericDao<Domain, IdClass>) dao).insert(domian, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean update(Domain domain, boolean audit) {
		return ((GenericDao<Domain, IdClass>) dao).update(domain, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean merge(Domain domain, boolean audit) {
		return ((GenericDao<Domain, IdClass>) dao).merge(domain, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean update(List<Domain> domain, boolean audit) {

		return ((GenericDao<Domain, IdClass>) dao).update(domain, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean delete(Domain domain, boolean audit) {
		return ((GenericDao<Domain, IdClass>) dao).delete(domain, audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean delete(List<Domain> domain, boolean audit) {
		return ((GenericDao<Domain, IdClass>) dao).delete(domain, audit);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Domain getById(IdClass id) {
		return ((GenericDao<Domain, IdClass>) dao).getById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getAll(Integer start, Integer pageSize) {
		return ((GenericDao<Domain, IdClass>) dao).getAll(start, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getAllCount() {

		return ((GenericDao<Domain, IdClass>) dao).getAllCount();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValue(Integer start, Integer pageSize, String fieldName, Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValue(start, pageSize, fieldName, fieldValue);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValueCount(String fieldName, Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValueCount(fieldName, fieldValue);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValues(Integer start, Integer pageSize, List<String> fieldNames,
			List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValues(start, pageSize, fieldNames, fieldValues);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValuesCount(List<String> fieldNames, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValuesCount(fieldNames, fieldValues);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValueJoinTable(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValueJoinTable(start, pageSize, sonName, fieldname, fieldValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValueJoinTableCount(String sonName, String fieldname, Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValueJoinTableCount(sonName, fieldname, fieldValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValuesJoinTable(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValuesJoinTable(start, pageSize, sonNames, fieldnames,
				fieldValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValuesJoinTableCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValuesJoinTableCount(sonNames, fieldnames, fieldValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValueJoinTables(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValueJoinTables(start, pageSize, sonName, fieldname,
				fieldValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValueJoinTablesCount(String sonName, String fieldname, Object fieldValue) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValueJoinTablesCount(sonName, fieldname, fieldValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyValuesJoinTables(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValuesJoinTables(start, pageSize, sonNames, fieldnames,
				fieldValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Long getbyValuesJoinTablesCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyValuesJoinTablesCount(sonNames, fieldnames, fieldValues);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Domain> getbyListOfValues(Integer start, Integer pageSize, String fieldname, List<Object> fieldValues) {
		return ((GenericDao<Domain, IdClass>) dao).getbyListOfValues(start, pageSize, fieldname, fieldValues);
	}
}