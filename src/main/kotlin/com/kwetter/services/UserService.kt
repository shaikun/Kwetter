package com.kwetter.services

import com.kwetter.dao.UserDao
import com.kwetter.models.User
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class UserService {

    /**
     * UserDao instance.
     */
    @Inject
    private lateinit var userDao: UserDao

    /**
     * Retrieve all users
     */
    fun all(): Set<User> {
        return userDao.all()
    }

    /**
     * Get user by id
     */
    fun find(id: Long): User {
        return userDao.find(id)
    }

    /**
     * Create user
     */
    fun create(model: User): User {
        return userDao.create(model)
    }

    /**
     * Update a user
     */
    fun update(model: User): User {
        return userDao.update(model)
    }

    /**
     * Delete a user
     */
    fun remove(id: Long) {
        return userDao.remove(find(id))
    }

    fun follow(id: Long): User {
        val current = find(1) //for now get static user, when JAAS is implemented, change.
        val follower = find(id)
        current.followers = current.followers.plus(follower)
        return userDao.update(current)
    }

}