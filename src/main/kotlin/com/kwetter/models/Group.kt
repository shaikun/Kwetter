package com.kwetter.models

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "groups")
data class Group(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null, //nullable because of kotlin, instead of -1.
        @Column(unique = true)
        val email: String,
        val groupName: String
) : Serializable {

}


