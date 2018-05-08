package com.kwetter.api.routes

import com.kwetter.models.Kweet
import com.kwetter.models.User
import com.kwetter.services.UserService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.PUT
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Stateless
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
class Users {

    /**
     * UserService instance.
     */
    @Inject
    private lateinit var userService: UserService

    /**
     * Get all users endpoint
     */
    @GET
    @Produces("application/json")
    fun all(): List<User> {
        return userService.all()
    }

    /**
     * Get user by id endpoint
     * @param id
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    fun getById(@PathParam("id") id: Long): User {
        return userService.find(id)
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA)
    @Path("create")
    fun create(@FormParam("bio") bio: String,
               @FormParam("email") email: String,
               @FormParam("location") location: String,
               @FormParam("password") password: String,
               @FormParam("username") username: String,
               @FormParam("website") website: String): User {
        val user = User(username, email, password, location, website, bio)

        return userService.create(user)
    }

    /**
     * Delete User endpoint
     * @param id
     */
    @DELETE
    @Path("/{id}")
    fun remove(@PathParam("id") id: Long): Response {
        userService.remove(id)
        return Response.status(Response.Status.OK).build() // returns 200 code
    }

    /**
     * Update User endpoint
     * @param user: User
     */
    @PUT
    fun update(user: User): User {
        return userService.update(user)
    }

    @GET
    @Path("/{id}/followers")
    fun followers(@PathParam("id") id: Long): List<User> {
        return userService.followers(id)
    }

    @GET
    @Path("/{id}/following")
    fun following(@PathParam("id") id: Long): List<User> {
        return userService.following(id)
    }

    @GET
    @Path("/{id}/kweets")
    fun tweets(@PathParam("id") id: Long): List<Kweet> {
        return userService.kweets(id)
    }

    /**
     * Follow User endpoint
     * @param id: User.id - User who gets followed
     */
//    @POST
//    @Path("/{id}/follow")
//    fun follow(@PathParam("id") id: Long): User {
//        return userService.follow(id)
//    }
}
