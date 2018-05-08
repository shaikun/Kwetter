package com.kwetter.api.routes

import com.kwetter.models.Kweet
import com.kwetter.services.KweetService
import com.kwetter.services.UserService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
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
     * UserService instance.
     */
    @Inject
    private lateinit var userService: UserService

    /**
     * Get all Kweets endpoint
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun all(): List<Kweet> {
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

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA)
    @Path("/create")
    fun create(@FormParam("text") text: String, @FormParam("user_id") user_id: String): Kweet {
        val user = userService.find(user_id.toLong())

        return kweetService.create(text, user.email)
    }
}