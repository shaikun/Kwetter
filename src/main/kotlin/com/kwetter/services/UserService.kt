package com.kwetter.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kwetter.dao.UserDao
import com.kwetter.models.User
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.ejb.Stateless
import javax.inject.Inject
import javax.xml.bind.DatatypeConverter

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

    fun findByEmail(email: String): User {
        return userDao.findByEmail(email)
    }

    /**
     * Create user
     */
    fun create(model: User): User {
        model.password = encode(model.password)
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

    /**
     * Get all followers of user with given id
     */
    fun followers(id: Long): List<User> {
        return userDao.followers(id)
    }

    /**
     * Get all following users of user with given id
     */
    fun following(id: Long): List<User> {
        return userDao.following(id)
    }

    fun login(email: String, password: String): String {
        if (!userDao.login(email, encode(password))) {
            throw Exception("No such combination found")
        }

        val algorithm = Algorithm.HMAC256("secret")

        return JWT.create()
                .withIssuer(email)
                .sign(algorithm)
    }

    @Throws(UnsupportedEncodingException::class, NoSuchAlgorithmException::class)
    fun encode(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(password.toByteArray(charset("UTF-8")))

        val digest = md.digest()

        return DatatypeConverter.printBase64Binary(digest).toString()
    }

}