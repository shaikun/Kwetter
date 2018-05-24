package com.kwetter.models

import com.kwetter.api.routes.Kweets
import java.util.Date
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.ws.rs.core.UriInfo
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@Entity
@Table(name = "kweets")
@XmlAccessorType(XmlAccessType.FIELD)
data class Kweet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        override val id: Long? = null, //nullable kotlin, instead of -1.
        val text: String,
        val date: Date) : Model() {

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var user: User //used val instead of var because it has to be mutable when using lateinit.

    //needed for kotlin to be able to init and persist with id null.
    constructor(text: String, date: Date) : this(null, text, date)


    /**
     * Build all urls
     */
    fun buildLinks(uriInfo: UriInfo) {

        // Set the resource class so we can build URI's
        resourceClass = Kweets::class.java

        // build all links
        buildSelf(uriInfo)
        buildCustom(uriInfo)

    }

    /**
     * Build custom urls
     */
    private fun buildCustom(uriInfo: UriInfo) {
        user.buildLinks(uriInfo)
    }
}