package com.kwetter.services

import com.kwetter.dao.KweetDao
import com.kwetter.dao.UserDao
import com.kwetter.models.Kweet
import java.util.Date
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
     * KweetDao instance.
     */
    @Inject
    private lateinit var userDao: UserDao

    /**
     * Retrieve all Kweets
     */
    fun all(): List<Kweet> {
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
    fun create(text: String, email: String): Kweet {
        val user = userDao.findByEmail(email)

        val tweet = Kweet(text, Date())
        tweet.user = user

        return kweetDao.create(tweet)
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