package com.kwetter.services

import com.kwetter.dao.KweetDao
import com.kwetter.models.Kweet
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class KweetService {
    /**
     * KweetDao instance.
     */
    @Inject
    private lateinit var kweetDao: KweetDao

    /**
     * Retrieve all Kweets
     */
    fun all(): Set<Kweet> {
        return kweetDao.all()
    }

    /**
     * Get Kweet by id
     */
    fun find(id: Long): Kweet {
        return kweetDao.find(id)
    }

    /**
     * Create Kweet
     */
    fun create(model: Kweet): Kweet {
        return kweetDao.create(model)
    }

    /**
     * Update a Kweet
     */
    fun update(model: Kweet): Kweet {
        return kweetDao.update(model)
    }

    /**
     * Remove a Kweet
     */
    fun remove(id: Long) {
        return kweetDao.remove(find(id))
    }
}