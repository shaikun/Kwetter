package com.kwetter.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
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
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType

@Entity
@NamedQuery(name = "findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
@Table(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null, //nullable because of kotlin, instead of -1.
        @Column(unique = true)
        var username: String,
        @Column(unique = true)
        var email: String,
        @JsonIgnore
        var password: String,
        var bio: String,
        var location: String,
        var website: String
) : Serializable {

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
}


