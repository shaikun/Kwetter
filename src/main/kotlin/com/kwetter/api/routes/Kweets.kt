package com.kwetter.api.routes

import com.kwetter.models.Kweet
import com.kwetter.services.KweetService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Stateless
@Path("/kweets")
@Produces(MediaType.APPLICATION_JSON)
class Kweets {

    /**
     * KweetService instance.
     */
    @Inject
    private lateinit var kweetService: KweetService

    /**
     * Get all Kweets endpoint
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun all(): Set<Kweet> {
        return kweetService.all()
    }

    /**
     * Get Kweet by id endpoint
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun find(@PathParam("id") id: Long): Kweet {

        return kweetService.find(id)

    }

    /**
     * Delete Kweet endpoint
     * @param id
     */
    @DELETE
    @Path("/{id}")
    fun remove(@PathParam("id") id: Long): Response {
        kweetService.remove(id)
        return Response.status(Response.Status.OK).build() // returns 200 code
    }
}