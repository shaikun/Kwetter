package com.kwetter.dao

import com.kwetter.models.Kweet
import javax.ejb.Stateless

@Stateless
class KweetDao : AbstractDao<Kweet>() {

    /**
     * Retrieve the model class
     */
    override fun getEntityClass(): Class<Kweet> {
        return Kweet::class.java
    }

}