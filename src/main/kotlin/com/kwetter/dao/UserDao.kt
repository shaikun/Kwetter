package com.kwetter.dao

import com.kwetter.models.User
import javax.ejb.Stateless

@Stateless
class UserDao : AbstractDao<User>() {

    /**
     * Retrieve the model class
     */
    override fun getEntityClass(): Class<User> {
        return User::class.java
    }

}