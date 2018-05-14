package com.kwetter.api.routes

import com.kwetter.models.Kweet
import com.kwetter.services.KweetService
import com.kwetter.services.UserService
import com.pusher.rest.Pusher
import java.util.HashMap
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

        return broadcast(kweetService.create(text, user.email))
    }

    private fun broadcast(kweet: Kweet): Kweet {
        // Create a new Pusher instance
        val pusher = Pusher("521456", "dc2704ace5aa76084ef3", "c520d52ec9f3357a64e4")
        pusher.setCluster("eu")
        println("setup pusher")

        for (user in userService.followers(kweet.user.id!!)) {
            System.out.println("Broadcasting to " + user.email)

            val data = HashMap<String, Any>()

            data["id"] = kweet.id!!
            data["user_id"] = kweet.user.id!!
            data["text"] = kweet.text
            data["date"] = kweet.date

            pusher.trigger(user.email, "newEvent", data)
        }
        return kweet
    }
}