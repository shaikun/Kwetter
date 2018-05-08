package com.kwetter.dao

import java.io.Serializable
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
abstract class AbstractDao<T : Serializable> {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    protected abstract fun getEntityClass(): Class<T>

    /**
     * Get all records by model class name
     */
    fun all(): List<T> {
        val c = entityManager.criteriaBuilder.createQuery(getEntityClass())
        c.from(getEntityClass())
        val query = entityManager.createQuery(c)
        return query.resultList
    }

    /**
     * Find an object by Id
     * @param id: Long
     */
    fun find(id: Long): T {
        return entityManager.find(getEntityClass(), id)
    }

    /**
     * Create a new object of the model
     * @param model: T
     */
    fun create(model: T): T {
        entityManager.persist(model)
        return model
    }

    /**
     * Update an existing object of the model
     * @param model: T
     */
    fun update(model: T): T {
        entityManager.merge(model)
        return model
    }

    /**
     * Remove an existing object of the model
     * @param model: T
     */
    fun remove(model: T) {
        entityManager.remove(entityManager.merge(model))
    }

}