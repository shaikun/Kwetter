package com.kwetter.dao

import com.kwetter.models.Group
import com.kwetter.models.Kweet
import com.kwetter.models.User
import javax.ejb.Stateless
import javax.persistence.NoResultException

@Stateless
class UserDao : AbstractDao<User>() {

    /**
     * Retrieve the model class
     */
    override fun getEntityClass(): Class<User> {
        return User::class.java
    }

    fun findByEmail(email: String): User {
        val sql = "SELECT * FROM users WHERE email = ?1"

        val query = entityManager.createNativeQuery(sql, User::class.java)
        query.setParameter(1, email)

        return query.singleResult as User
    }

    fun followers(id: Long): List<User> {
        val sql = "SELECT * FROM users WHERE id IN (SELECT user_id FROM following WHERE `follower_id` = ?1)"

        val query = entityManager.createNativeQuery(sql, User::class.java)
        query.setParameter(1, id)

        @Suppress("UNCHECKED_CAST") //needed because the cast to a list of Users isn't checked.
        return query.resultList as List<User>
    }

    fun following(id: Long): List<User> {
        val sql = "SELECT * FROM users WHERE id IN (SELECT follower_id FROM following WHERE user_id = ?1)"

        val query = entityManager.createNativeQuery(sql, User::class.java)
        query.setParameter(1, id)

        @Suppress("UNCHECKED_CAST") //needed because the cast to a list of Users isn't checked.
        return query.resultList as List<User>
    }

    fun kweets(id: Long): List<Kweet> {
        val sql = "SELECT t.* FROM tweets t " +
                "JOIN following f ON (t.user_id IN (SELECT follower_id FROM following WHERE user_id = ?1)) " +
                "UNION " +
                "SELECT * FROM tweets WHERE user_id = ?1 " +
                "ORDER BY `date` DESC"

        val query = entityManager.createNativeQuery(sql, Kweet::class.java)
        query.setParameter(1, id)

        @Suppress("UNCHECKED_CAST") //needed because the cast to a list of Kweets isn't checked.
        return query.resultList as List<Kweet>
    }

    fun login(email: String, password: String): Boolean {
        val sql = "SELECT * FROM users WHERE email = ?1 AND password = ?2"

        val query = entityManager.createNativeQuery(sql, User::class.java)
        query.setParameter(1, email)
        query.setParameter(2, password)

        try {
            val user = query.singleResult
        } catch (error: NoResultException) {
            return false
        }

        return true
    }

    override fun create(model: User): User {
        val user = super.create(model)
        val group = Group(null, user.email, "users")
        entityManager.persist(group)
        return user
    }
}