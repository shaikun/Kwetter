package com.kwetter.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kwetter.api.routes.Kweets
import com.kwetter.api.routes.Users
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient
import javax.ws.rs.core.UriInfo
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@Entity
@NamedQuery(name = "findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
@Table(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        override val id: Long? = null, //nullable because of kotlin, instead of -1.
        @Column(unique = true)
        var username: String,
        @Column(unique = true)
        var email: String,
        @JsonIgnore
        var password: String,
        var bio: String,
        var location: String,
        var website: String
) : Model() {

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    var kweets: Set<Kweet> = emptySet()

    @Transient
    var following: Set<User> = emptySet()

    @Transient
    var followers: Set<User> = emptySet()

    //needed for kotlin to be able to init and persist with id null.
    constructor(username: String, email: String, password: String, location: String, website: String,
                bio: String) : this(null, username, email, password, location, website, bio)

    fun buildLinks(uriInfo: UriInfo) {
        // Set the resource class so we can build URI's
        resourceClass = Users::class.java

        // build all links
        buildSelf(uriInfo)
        buildRelation(uriInfo, "following")
        buildRelation(uriInfo, "followers")
        buildCustom(uriInfo)
    }

    /**
     * Build custom urls
     */
    private fun buildCustom(uriInfo: UriInfo) {
        buildUsers(uriInfo, "kweets")
    }

    /**
     * Build custom kweets url
     */
    private fun buildKweets(uriInfo: UriInfo, ref: String) {
        val uri = uriInfo.baseUriBuilder
                .path(Kweets::class.java)
                .path(id.toString())
                .path("kweets")
                .build()
                .toString()

        addLink(uri, ref)
    }

    /**
     * Build timeline url
     */
    private fun buildUsers(uriInfo: UriInfo, ref: String) {
        val uri = uriInfo.baseUriBuilder
                .path(Kweets::class.java)
                .path(id.toString())
                .path("users")
                .build()
                .toString()

        addLink(uri, ref)
    }
}


