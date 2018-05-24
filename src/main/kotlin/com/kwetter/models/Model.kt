package com.kwetter.models

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.ws.rs.core.UriInfo

abstract class Model : Serializable {

    abstract val id: Long?
    var links: Set<Link> = emptySet()

    @JsonIgnore
    lateinit var resourceClass: Class<*>

    /**
     * Appends the link to the model
     */
    fun addLink(uri: String, ref: String) {
        links = links.plus(Link(uri, ref))
    }

    /**
     * Builds a self link
     */
    fun buildSelf(uriInfo: UriInfo) {
        val uri = uriInfo.baseUriBuilder
                .path(resourceClass)
                .path(id.toString())
                .build()
                .toString()

        addLink(uri, "self")
    }

    /**
     * Builds a relation link
     */
    fun buildRelation(uriInfo: UriInfo, relation: String) {
        val uri = uriInfo.baseUriBuilder
                .path(resourceClass)
                .path(id.toString())
                .path(relation)
                .build()
                .toString()

        addLink(uri, relation)
    }
}