package com.test.generic.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.test.generic.model.dao.GenericDao;
import com.test.generic.view.utils.DateHelper;

public class GenericDaoImpl<T extends Serializable, IdClass extends Serializable> implements GenericDao<T, IdClass> {

	/**
	 * 
	 */

	private int BATCH_SIZE = 20;
	private static final long serialVersionUID = 1285068461097629206L;
	final static Log log = LogFactory.getLog(GenericDaoImpl.class);
	transient protected SessionFactory sessionFactory;
	protected Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public T insert(T t) {
		return this.insert(t, true);
	}

	public T insert(T t, boolean audit) {
		Session session = null;
		Serializable id = null;
		try {
			log.info("Getting session for persisting " + persistentClass + " in DB");
			session = sessionFactory.getCurrentSession();
			log.info("Persisting " + persistentClass + " in DB");
			id = session.save(t);
			session.flush();
			session.clear();
			log.info(persistentClass + " persisted in DB with id = " + id);

		} catch (Exception e) {
			// log.error(persistentClass + " can't be persisted in DB because of
			// the following Exception ");
			e.printStackTrace();
		}
		return t;
	}

	public List<T> insert(List<T> sot) {
		return this.insert(sot, true);
	}

	public List<T> insert(List<T> sot, boolean audit) {
		Session session = null;
		try {
			log.info("Getting session for persisting a set of " + persistentClass + " in DB");
			session = sessionFactory.getCurrentSession();
			log.info("Persisting a set of " + persistentClass + " in DB");

			for (int i = 0; i < sot.size(); i++) {
				session.save(sot.get(i));

				if ((i + 1) % BATCH_SIZE == 0) {
					session.flush();
					session.clear();
				}
			}

			session.flush();
			session.clear();

			log.info("Set of " + persistentClass + " persisted in DB");
		} catch (Exception e) {
			log.error("Set of " + persistentClass + " can't be persisted in DB because of the following Exception ");
			e.printStackTrace();
		}
		return sot;

	}

	public boolean update(T t) {
		return this.update(t, true);
	}

	@SuppressWarnings("unchecked")
	public boolean update(T t, boolean audit) {
		Session session = null;
		T oldt = null;
		try {
			if (audit) {
				Field f = t.getClass()
						.getDeclaredField(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName());
				f.setAccessible(true);
				// T oldt = this.getById((IdClass)
				// t.getClass().getField(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()).get(t));
				oldt = this.getById((IdClass) f.get(t));
			}
			log.info("Getting session for updating " + persistentClass + " in DB");
			session = sessionFactory.getCurrentSession();
			log.info("Updating " + persistentClass + " in DB");
			session.update(t);
			session.flush();
			session.clear();
			log.info(persistentClass + " updated in DB");

			return true;
		} catch (Exception e) {
			log.error(persistentClass + " can't be updated in DB because of the following Exception ");
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(List<T> sot) {
		return this.update(sot, true);
	}

	@SuppressWarnings("unchecked")
	public boolean update(List<T> sot, boolean audit) {
		Session session = null;
		try {
			List<T> lstOldt = new ArrayList<T>();
			log.info("Getting session for updating " + persistentClass + " in DB");
			session = sessionFactory.getCurrentSession();
			for (int i = 0; i < sot.size(); i++) {
				T oldt = this
						.getById(
								(IdClass) sot
										.get(i).getClass().getDeclaredField(sessionFactory
												.getClassMetadata(persistentClass).getIdentifierPropertyName())
								.get(sot.get(i)));
				log.info("Updating " + persistentClass + " in DB");
				session.update(sot.get(i));
				if ((i + 1) % BATCH_SIZE == 0) {
					session.flush();
					session.clear();
				}
				log.info(persistentClass + " updated in DB");
				lstOldt.add(oldt);
			}

			return true;
		} catch (Exception e) {
			log.error(persistentClass + " can't be updated in DB because of the following Exception ");
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(T t) {
		return this.delete(t, true);
	}

	public boolean delete(T t, boolean audit) {
		Session session = null;
		try {
			log.info("Getting session for deleting " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Deleting " + persistentClass + " from DB");
			session.delete(t);
			session.flush();
			session.clear();
			log.info(persistentClass + " deleted from DB");

			return true;
		} catch (Exception e) {
			log.error(persistentClass + " can't be deleted from DB because of the following Exception ");
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(List<T> t) {
		return this.delete(t, true);
	}

	public boolean delete(List<T> t, boolean audit) {
		Session session = null;
		try {
			log.info("Getting session for deleting " + persistentClass + " from DB");

			session = sessionFactory.getCurrentSession();
			log.info("Deleting " + persistentClass + " from DB");
			for (int i = 0; i < t.size(); i++) {
				session.delete(t.get(i));
				if ((i + 1) % BATCH_SIZE == 0) {
					session.flush();
					session.clear();
				}
			}
			log.info(persistentClass + " deleted from DB");

			return true;
		} catch (Exception e) {
			log.error(persistentClass + " can't be deleted from DB because of the following Exception ");
			e.printStackTrace();
			return false;
		}
	}

	public boolean merge(T t) {
		return this.merge(t, true);
	}

	@SuppressWarnings("unchecked")
	public boolean merge(T t, boolean audit) {
		Session session = null;
		T oldt = null;
		try {
			if (audit) {
				Field f = t.getClass()
						.getDeclaredField(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName());
				f.setAccessible(true);
				// T oldt = this.getById((IdClass)
				// t.getClass().getField(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()).get(t));
				oldt = this.getById((IdClass) f.get(t));
			}
			log.info("Getting session for updating " + persistentClass + " in DB");
			session = sessionFactory.getCurrentSession();
			log.info("Updating " + persistentClass + " in DB");

			session.merge(t);
			session.flush();
			session.clear();
			log.info(persistentClass + " updated in DB");

			return true;
		} catch (Exception e) {
			log.error(persistentClass + " can't be updated in DB because of the following Exception ");
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public T getById(IdClass id) {
		Session session = null;
		T fetchedObj = null;
		try {
			log.info("Getting session for getting " + persistentClass + " of id = " + id + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting " + persistentClass + " of id = " + id + " from DB");
			fetchedObj = (T) session.get(persistentClass, id);
			log.info(persistentClass + " of id = " + id + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return fetchedObj;
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll(Integer start, Integer pageSize) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);
			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	public Long getAllCount() {
		Session session = null;
		Criteria criteria = null;

		Long resultCount = 0L;

		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);
			criteria.setProjection(Projections.rowCount());

			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " - resultCount: " + resultCount);

		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	@SuppressWarnings("unchecked")
	public List<T> getbyValue(Integer start, Integer pageSize, String fieldname, Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);

			if (fieldValue == null)
				criteria.add(Restrictions.isNull(fieldname));
			else if (fieldValue instanceof Date) {
				Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
				Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
				criteria.add(Restrictions.ge(fieldname, from));
				criteria.add(Restrictions.lt(fieldname, to));
			} else if (fieldValue instanceof String)
				criteria.add(Restrictions.like(fieldname, fieldValue));
			else if (fieldValue instanceof List)
				criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
			else
				criteria.add(Restrictions.eq(fieldname, fieldValue));

			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	public Long getbyValueCount(String fieldname, Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);

			if (fieldValue == null)
				criteria.add(Restrictions.isNull(fieldname));
			else if (fieldValue instanceof Date) {
				Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
				Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
				criteria.add(Restrictions.ge(fieldname, from));
				criteria.add(Restrictions.lt(fieldname, to));
			} else if (fieldValue instanceof String)
				criteria.add(Restrictions.like(fieldname, fieldValue));
			else if (fieldValue instanceof List)
				criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
			else
				criteria.add(Restrictions.eq(fieldname, fieldValue));

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	@SuppressWarnings("unchecked")
	public List<T> getbyValues(Integer start, Integer pageSize, List<String> fieldnames, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);

			for (int i = 0; i < fieldnames.size(); i++) {
				if (fieldValues.get(i) == null)
					criteria.add(Restrictions.isNull(fieldnames.get(i)));
				else if (fieldValues.get(i) instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
					criteria.add(Restrictions.ge(fieldnames.get(i), from));
					criteria.add(Restrictions.lt(fieldnames.get(i), to));
				} else if (fieldValues.get(i) instanceof String)
					criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
				else if (fieldValues.get(i) instanceof List)
					criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
				else
					criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));

			}

			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	public Long getbyValuesCount(List<String> fieldnames, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);

			for (int i = 0; i < fieldnames.size(); i++) {
				if (fieldValues.get(i) == null)
					criteria.add(Restrictions.isNull(fieldnames.get(i)));
				else if (fieldValues.get(i) instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
					criteria.add(Restrictions.ge(fieldnames.get(i), from));
					criteria.add(Restrictions.lt(fieldnames.get(i), to));
				} else if (fieldValues.get(i) instanceof String)
					criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
				else if (fieldValues.get(i) instanceof List)
					criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
				else
					criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));

			}

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	// get by Value of Joined Table
	@SuppressWarnings("unchecked")
	public List<T> getbyValueJoinTable(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");

			if (sonName == null) {
				if (fieldValue == null)
					criteria.add(Restrictions.isNull(fieldname));
				else if (fieldValue instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
					criteria.add(Restrictions.ge(fieldname, from));
					criteria.add(Restrictions.lt(fieldname, to));
				} else if (fieldValue instanceof String)
					criteria.add(Restrictions.like(fieldname, fieldValue));
				else if (fieldValue instanceof List)
					criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
				else
					criteria.add(Restrictions.eq(fieldname, fieldValue));

			} else {
				String s = persistentClass.getSimpleName() + "_01." + sonName;
				criteria.createAlias(s, sonName + "_01");
				if (fieldname != null)
					if (fieldValue == null)
						criteria.add(Restrictions.isNull(sonName + "_01." + fieldname));
					else if (fieldValue instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
						criteria.add(Restrictions.ge(sonName + "_01." + fieldname, from));
						criteria.add(Restrictions.lt(sonName + "_01." + fieldname, to));
					} else if (fieldValue instanceof String)
						criteria.add(Restrictions.like(sonName + "_01." + fieldname, fieldValue));
					else if (fieldValue instanceof List)
						criteria.add(Restrictions.in(sonName + "_01." + fieldname, (List<?>) fieldValue));
					else
						criteria.add(Restrictions.eq(sonName + "_01." + fieldname, fieldValue));
			}

			if (start != null && pageSize != null)

			{
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (

		Exception e)

		{
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;

	}

	// get by Value of Joined Table Count
	public Long getbyValueJoinTableCount(String sonName, String fieldname, Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");

			if (sonName == null) {
				if (fieldValue == null)
					criteria.add(Restrictions.isNull(fieldname));
				else if (fieldValue instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
					criteria.add(Restrictions.ge(fieldname, from));
					criteria.add(Restrictions.lt(fieldname, to));
				} else if (fieldValue instanceof String)
					criteria.add(Restrictions.like(fieldname, fieldValue));
				else if (fieldValue instanceof List)
					criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
				else
					criteria.add(Restrictions.eq(fieldname, fieldValue));

			} else {
				String s = persistentClass.getSimpleName() + "_01." + sonName;
				criteria.createAlias(s, sonName + "_01");
				if (fieldname != null)
					if (fieldValue == null)
						criteria.add(Restrictions.isNull(sonName + "_01." + fieldname));
					else if (fieldValue instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
						criteria.add(Restrictions.ge(sonName + "_01." + fieldname, from));
						criteria.add(Restrictions.lt(sonName + "_01." + fieldname, to));
					} else if (fieldValue instanceof String)
						criteria.add(Restrictions.like(sonName + "_01." + fieldname, fieldValue));
					else if (fieldValue instanceof List)
						criteria.add(Restrictions.in(sonName + "_01." + fieldname, (List<?>) fieldValue));
					else
						criteria.add(Restrictions.eq(sonName + "_01." + fieldname, fieldValue));
			}

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (

		Exception e)

		{
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;

	}

	// get by Values of Joined Tables
	@SuppressWarnings("unchecked")
	public List<T> getbyValuesJoinTable(Integer start, Integer pageSize, List<String> sonNames, List<String> fieldnames,
			List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		List<String> aliases = new ArrayList<String>();
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");

			for (int i = 0; i < fieldnames.size(); i++) {
				if (sonNames.get(i) == null) {
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(fieldnames.get(i), from));
						criteria.add(Restrictions.lt(fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));
				} else {
					String s = persistentClass.getSimpleName() + "_01." + sonNames.get(i);
					if (!(aliases.contains(s))) {
						criteria.createAlias(s, sonNames.get(i) + "_01");
						aliases.add(s);
					}
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(sonNames.get(i) + "_01." + fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(sonNames.get(i) + "_01." + fieldnames.get(i), from));
						criteria.add(Restrictions.lt(sonNames.get(i) + "_01." + fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(
								Restrictions.like(sonNames.get(i) + "_01." + fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(sonNames.get(i) + "_01." + fieldnames.get(i),
								(List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(sonNames.get(i) + "_01." + fieldnames.get(i), fieldValues.get(i)));
				}
			}

			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	// get by Values of Joined Tables Count
	public Long getbyValuesJoinTableCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		List<String> aliases = new ArrayList<String>();
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");

			for (int i = 0; i < fieldnames.size(); i++) {
				if (sonNames.get(i) == null) {
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(fieldnames.get(i), from));
						criteria.add(Restrictions.lt(fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));
				} else {
					String s = persistentClass.getSimpleName() + "_01." + sonNames.get(i);
					if (!(aliases.contains(s))) {
						criteria.createAlias(s, sonNames.get(i) + "_01");
						aliases.add(s);
					}
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(sonNames.get(i) + "_01." + fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(sonNames.get(i) + "_01." + fieldnames.get(i), from));
						criteria.add(Restrictions.lt(sonNames.get(i) + "_01." + fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(
								Restrictions.like(sonNames.get(i) + "_01." + fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(sonNames.get(i) + "_01." + fieldnames.get(i),
								(List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(sonNames.get(i) + "_01." + fieldnames.get(i), fieldValues.get(i)));
				}
			}

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	// get by Value of Multiable Joined Tables
	@SuppressWarnings("unchecked")
	public List<T> getbyValueJoinTables(Integer start, Integer pageSize, String sonName, String fieldname,
			Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");
			Map<String, String> aliasesMap = new HashMap<String, String>();

			if (sonName == null)
				if (fieldValue == null)
					criteria.add(Restrictions.isNull(fieldname));
				else if (fieldValue instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
					criteria.add(Restrictions.ge(fieldname, from));
					criteria.add(Restrictions.lt(fieldname, to));
				} else if (fieldValue instanceof String)
					criteria.add(Restrictions.like(fieldname, fieldValue));
				else if (fieldValue instanceof List)
					criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
				else
					criteria.add(Restrictions.eq(fieldname, fieldValue));
			else {
				//////////
				String[] joinedTables = sonName.split("\\.");
				String joinTable = "";
				String tablealias = "";
				if (joinedTables.length > 0) {
					joinTable = persistentClass.getSimpleName() + "_01." + joinedTables[0];
					tablealias = joinedTables[0] + "_";

					if (!(aliasesMap.containsKey(joinTable))) {
						aliasesMap.put(joinTable, tablealias);
						criteria.createAlias(joinTable, tablealias);
					}

					if (joinedTables.length > 1)
						for (int j = 1; j < joinedTables.length; j++) {
							joinTable = tablealias + "." + joinedTables[j];
							if (!(aliasesMap.containsKey(joinTable))) {
								tablealias = joinedTables[j] + "_";
								aliasesMap.put(joinTable, tablealias);
								criteria.createAlias(joinTable, tablealias);
							} else {
								tablealias = aliasesMap.get(joinTable);
							}
						}
					//////////

					if (fieldValue == null)
						criteria.add(Restrictions.isNull(aliasesMap.get(joinTable) + "." + fieldname));
					else if (fieldValue instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
						criteria.add(Restrictions.ge(aliasesMap.get(joinTable) + "." + fieldname, from));
						criteria.add(Restrictions.lt(aliasesMap.get(joinTable) + "." + fieldname, to));
					} else if (fieldValue instanceof String)
						criteria.add(Restrictions.like(aliasesMap.get(joinTable) + "." + fieldname, fieldValue));
					else if (fieldValue instanceof List)
						criteria.add(
								Restrictions.in(aliasesMap.get(joinTable) + "." + fieldname, (List<?>) fieldValue));
					else
						criteria.add(Restrictions.eq(aliasesMap.get(joinTable) + "." + fieldname, fieldValue));
				}
			}

			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	// get by Value of Multiable Joined Tables Count
	public Long getbyValueJoinTablesCount(String sonName, String fieldname, Object fieldValue) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");
			Map<String, String> aliasesMap = new HashMap<String, String>();

			if (sonName == null)
				if (fieldValue == null)
					criteria.add(Restrictions.isNull(fieldname));
				else if (fieldValue instanceof Date) {
					Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
					Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
					criteria.add(Restrictions.ge(fieldname, from));
					criteria.add(Restrictions.lt(fieldname, to));
				} else if (fieldValue instanceof String)
					criteria.add(Restrictions.like(fieldname, fieldValue));
				else if (fieldValue instanceof List)
					criteria.add(Restrictions.in(fieldname, (List<?>) fieldValue));
				else
					criteria.add(Restrictions.eq(fieldname, fieldValue));
			else {
				//////////
				String[] joinedTables = sonName.split("\\.");
				String joinTable = "";
				String tablealias = "";
				if (joinedTables.length > 0) {
					joinTable = persistentClass.getSimpleName() + "_01." + joinedTables[0];
					tablealias = joinedTables[0] + "_";

					if (!(aliasesMap.containsKey(joinTable))) {
						aliasesMap.put(joinTable, tablealias);
						criteria.createAlias(joinTable, tablealias);
					}

					if (joinedTables.length > 1)
						for (int j = 1; j < joinedTables.length; j++) {
							joinTable = tablealias + "." + joinedTables[j];
							if (!(aliasesMap.containsKey(joinTable))) {
								tablealias = joinedTables[j] + "_";
								aliasesMap.put(joinTable, tablealias);
								criteria.createAlias(joinTable, tablealias);
							} else {
								tablealias = aliasesMap.get(joinTable);
							}
						}
					//////////

					if (fieldValue == null)
						criteria.add(Restrictions.isNull(aliasesMap.get(joinTable) + "." + fieldname));
					else if (fieldValue instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValue);
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValue));
						criteria.add(Restrictions.ge(aliasesMap.get(joinTable) + "." + fieldname, from));
						criteria.add(Restrictions.lt(aliasesMap.get(joinTable) + "." + fieldname, to));
					} else if (fieldValue instanceof String)
						criteria.add(Restrictions.like(aliasesMap.get(joinTable) + "." + fieldname, fieldValue));
					else if (fieldValue instanceof List)
						criteria.add(
								Restrictions.in(aliasesMap.get(joinTable) + "." + fieldname, (List<?>) fieldValue));
					else
						criteria.add(Restrictions.eq(aliasesMap.get(joinTable) + "." + fieldname, fieldValue));
				}
			}

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	// get by Values of Multiable Joined Tables
	@SuppressWarnings("unchecked")
	public List<T> getbyValuesJoinTables(Integer start, Integer pageSize, List<String> sonNames,
			List<String> fieldnames, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");
			Map<String, String> aliasesMap = new HashMap<String, String>();
			for (int i = 0; i < fieldnames.size(); i++) {
				if (sonNames.get(i) == null)
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(fieldnames.get(i), from));
						criteria.add(Restrictions.lt(fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));
				else {
					//////////
					String[] joinedTables = sonNames.get(i).split("\\.");
					String joinTable = "";
					String tablealias = "";
					if (joinedTables.length > 0) {
						joinTable = persistentClass.getSimpleName() + "_01." + joinedTables[0];
						tablealias = joinedTables[0] + "_";

						if (!(aliasesMap.containsKey(joinTable))) {
							aliasesMap.put(joinTable, tablealias);
							criteria.createAlias(joinTable, tablealias);
						}

						if (joinedTables.length > 1)
							for (int j = 1; j < joinedTables.length; j++) {
								joinTable = tablealias + "." + joinedTables[j];
								if (!(aliasesMap.containsKey(joinTable))) {
									tablealias = joinedTables[j] + "_";

									if (!(aliasesMap.containsValue(tablealias))) {
										aliasesMap.put(joinTable, tablealias);
										criteria.createAlias(joinTable, tablealias);
									} else {
										tablealias = joinedTables[j] + "_" + i;
										aliasesMap.put(joinTable, tablealias);
										criteria.createAlias(joinTable, tablealias);
									}
								} else {
									tablealias = aliasesMap.get(joinTable);
								}
							}
						//////////

						if (fieldValues.get(i) == null)
							criteria.add(Restrictions.isNull(aliasesMap.get(joinTable) + "." + fieldnames.get(i)));
						else if (fieldValues.get(i) instanceof Date) {
							Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
							Date to = DateHelper
									.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
							criteria.add(Restrictions.ge(aliasesMap.get(joinTable) + "." + fieldnames.get(i), from));
							criteria.add(Restrictions.lt(aliasesMap.get(joinTable) + "." + fieldnames.get(i), to));
						} else if (fieldValues.get(i) instanceof String)
							criteria.add(Restrictions.like(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									fieldValues.get(i)));
						else if (fieldValues.get(i) instanceof List)
							criteria.add(Restrictions.in(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									(List<?>) fieldValues.get(i)));
						else
							criteria.add(Restrictions.eq(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									fieldValues.get(i)));
					}
				}
			}
			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

	// get by Values of Multiable Joined Tables
	public Long getbyValuesJoinTablesCount(List<String> sonNames, List<String> fieldnames, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		Long resultCount = 0L;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass, persistentClass.getSimpleName() + "_01");
			Map<String, String> aliasesMap = new HashMap<String, String>();
			for (int i = 0; i < fieldnames.size(); i++) {
				if (sonNames.get(i) == null)
					if (fieldValues.get(i) == null)
						criteria.add(Restrictions.isNull(fieldnames.get(i)));
					else if (fieldValues.get(i) instanceof Date) {
						Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
						Date to = DateHelper.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
						criteria.add(Restrictions.ge(fieldnames.get(i), from));
						criteria.add(Restrictions.lt(fieldnames.get(i), to));
					} else if (fieldValues.get(i) instanceof String)
						criteria.add(Restrictions.like(fieldnames.get(i), fieldValues.get(i)));
					else if (fieldValues.get(i) instanceof List)
						criteria.add(Restrictions.in(fieldnames.get(i), (List<?>) fieldValues.get(i)));
					else
						criteria.add(Restrictions.eq(fieldnames.get(i), fieldValues.get(i)));
				else {
					//////////
					String[] joinedTables = sonNames.get(i).split("\\.");
					String joinTable = "";
					String tablealias = "";
					if (joinedTables.length > 0) {
						joinTable = persistentClass.getSimpleName() + "_01." + joinedTables[0];
						tablealias = joinedTables[0] + "_";

						if (!(aliasesMap.containsKey(joinTable))) {
							aliasesMap.put(joinTable, tablealias);
							criteria.createAlias(joinTable, tablealias);
						}

						if (joinedTables.length > 1)
							for (int j = 1; j < joinedTables.length; j++) {
								joinTable = tablealias + "." + joinedTables[j];

								if (!(aliasesMap.containsKey(joinTable))) {

									tablealias = joinedTables[j] + "_";
									if (!(aliasesMap.containsValue(tablealias))) {

										aliasesMap.put(joinTable, tablealias);
										criteria.createAlias(joinTable, tablealias);
									} else {
										tablealias = joinedTables[j] + "_" + i;
										aliasesMap.put(joinTable, tablealias);
										criteria.createAlias(joinTable, tablealias);
									}
								} else {
									tablealias = aliasesMap.get(joinTable);
								}
							}
						//////////

						if (fieldValues.get(i) == null)
							criteria.add(Restrictions.isNull(aliasesMap.get(joinTable) + "." + fieldnames.get(i)));
						else if (fieldValues.get(i) instanceof Date) {
							Date from = DateHelper.getDateWithoutTime((Date) fieldValues.get(i));
							Date to = DateHelper
									.getDateWithoutTime(DateHelper.getTomorrowDate((Date) fieldValues.get(i)));
							criteria.add(Restrictions.ge(aliasesMap.get(joinTable) + "." + fieldnames.get(i), from));
							criteria.add(Restrictions.lt(aliasesMap.get(joinTable) + "." + fieldnames.get(i), to));
						} else if (fieldValues.get(i) instanceof String)
							criteria.add(Restrictions.like(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									fieldValues.get(i)));
						else if (fieldValues.get(i) instanceof List)
							criteria.add(Restrictions.in(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									(List<?>) fieldValues.get(i)));
						else
							criteria.add(Restrictions.eq(aliasesMap.get(joinTable) + "." + fieldnames.get(i),
									fieldValues.get(i)));
					}
				}
			}

			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return resultCount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getbyListOfValues(Integer start, Integer pageSize, String fieldname, List<Object> fieldValues) {
		Session session = null;
		Criteria criteria = null;
		List<T> lot = null;
		try {
			log.info("Getting session for getting all " + persistentClass + " from DB");
			session = sessionFactory.getCurrentSession();
			log.info("Getting all " + persistentClass + " from DB");
			criteria = session.createCriteria(persistentClass);
			if (start != null && pageSize != null) {
				log.info("Setting pagination params");
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
			}
			criteria.add(Restrictions.in(fieldname, fieldValues));
			criteria.addOrder(Order.asc(sessionFactory.getClassMetadata(persistentClass).getIdentifierPropertyName()));
			lot = criteria.list();
			log.info(persistentClass + " fetched from DB");
		} catch (Exception e) {
			log.error(persistentClass + " can't be fetched from DB because of the following Exception ");
			e.printStackTrace();
		}
		return lot;
	}

}
