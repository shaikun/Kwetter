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
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@NamedQuery(name = "findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null, //nullable because of kotlin, instead of -1.
        @Column(unique = true)
        val username: String,
        @Column(unique = true)
        val email: String,
        @JsonIgnore
        val password: String,
        val bio: String,
        val location: String,
        val website: String
) : Serializable {
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
    var kweets: Set<Kweet> = emptySet()
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
    @JoinTable(
            name = "followers",
            joinColumns = [(JoinColumn(name = "user_id", referencedColumnName = "id"))],
            inverseJoinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))]
    )
    var followers: Set<User> = emptySet() // might change to following, not yet decided.

    //needed for kotlin to be able to init and persist with id null.
    constructor(username: String, email: String, password: String, location: String, website: String,
                bio: String) : this(null, username, email, password, location, website, bio)
}


