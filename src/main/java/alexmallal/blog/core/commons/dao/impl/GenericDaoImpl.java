package alexmallal.blog.core.commons.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Inherited;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import alexmallal.blog.core.commons.dao.GenericDao;
import alexmallal.blog.core.commons.model.Base;


@Transactional
public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
	@PersistenceContext(unitName="mytestdomain")
	protected EntityManager entityManager;
	protected Class<T> persistentClass;
	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
             .getGenericSuperclass();
        this.persistentClass = (Class<T>) genericSuperclass
             .getActualTypeArguments()[0];
    }
	
//	public GenericDaoImpl(Class<T> persistentClass) {
//		this.persistentClass = persistentClass;
//	}
//
//	protected EntityManager getEntityManager() {
//		return entityManager;
//	}
//
//	@PersistenceContext
//	public void setEntityManager(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
//
//	public Class<T> getPersistentClass() {
//		return persistentClass;
//	}
	
	@Transactional(readOnly=true)
	public T findById(ID id) {
		T entity = entityManager.find(persistentClass, id);
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<T> findAll() {
		return entityManager
			.createQuery("select x from " + persistentClass.getSimpleName() + " x")
			.getResultList();
	}
	
	@Transactional
	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}
	
	@Transactional
	public T update(T entity) {
		T mergedEntity = entityManager.merge(entity);
		return mergedEntity;
	}
	
	@Transactional
	public void delete(T entity) {
		if (Base.class.isAssignableFrom(persistentClass)) {
			entityManager.remove(
					entityManager.getReference(entity.getClass(), 
							((Base)entity).getId()));
		} else {
			entity = entityManager.merge(entity);
			entityManager.remove(entity);
		}
	}
	
	@Transactional
	public void flush() {
		entityManager.flush();
	}
	
	@Transactional
	public void clear() {
		entityManager.clear();
	}
	
	@Transactional(readOnly=true)
	public long countEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM " + persistentClass.getSimpleName() + " o", Long.class).getSingleResult();
    }
	
	@Transactional(readOnly=true)
	public List<T> findRangeEntries(int firstResult, int maxResults) {
        return entityManager.createQuery("SELECT o FROM User o", persistentClass).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
}

