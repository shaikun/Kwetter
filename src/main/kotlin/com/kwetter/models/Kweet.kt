package com.kwetter.models

import java.io.Serializable
import java.util.Date
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "kweets")
data class Kweet(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null, //nullable kotlin, instead of -1.
        val text: String,
        val date: Date) : Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    lateinit var user: User //used val instead of var because it has to be mutable when using lateinit.

    //needed for kotlin to be able to init and persist with id null.
    constructor(text: String, date: Date) : this(null, text, date)
}