package com.kwetter.api.routes

import com.kwetter.api.annotations.Secured
import com.kwetter.models.User
import com.kwetter.services.MailService
import com.kwetter.services.UserService
import java.util.HashMap
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
     * MailService instance.
     */
    @Inject
    private lateinit var mailService: MailService

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
    fun getById(@PathParam("id") id: Long): Response {

        val response = HashMap<String, Any>()
        response["success"] = true
        response["data"] = userService.find(id)
        response["message"] = "Single User with id $id"

        return Response.ok(response)
                .link("http://localhost:8080/Kwetter-1.0-SNAPSHOT/api/users/$id/kweets",
                      "User timeline")
                .link("http://localhost:8080/Kwetter-1.0-SNAPSHOT/api/users/$id/followers",
                      "All users who are following th given user")
                .link("http://localhost:8080/Kwetter-1.0-SNAPSHOT/api/users/$id/following",
                      "All users which the giver user is following").build()
    }

    @GET
    @Path("/byEmail/{email}")
    @Produces("application/json")
    @Secured("users")
    fun getById(@PathParam("email") email: String): Response {

        val response = HashMap<String, Any>()
        response["success"] = true
        response["data"] = userService.findByEmail(email)
        response["message"] = "Single User with email $email"

        return Response.ok(response).build()
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
        var user = User(username, email, password, location, website, bio)

        user = userService.create(user)
        mailService.mailRegister(user)

        return user
    }

    /**
     * Delete User endpoint
     * @param id
     */
    @DELETE
    @Path("/{id}")
    @Secured("users")
    fun remove(@PathParam("id") id: Long): Response {
        userService.remove(id)
        return Response.status(Response.Status.OK).build() // returns 200 code
    }

    /**
     * Update User endpoint
     * @param user: User
     */
    @PUT
    @Secured("users")
    fun update(user: User): User {
        return userService.update(user)
    }

    @GET
    @Secured("users")
    @Path("/{id}/followers")
    fun followers(@PathParam("id") id: Long): List<User> {
        return userService.followers(id)
    }

    @GET
    @Secured("users")
    @Path("/{id}/following")
    fun following(@PathParam("id") id: Long): List<User> {
        return userService.following(id)
    }

    @GET
    @Secured("users")
    @Path("/{id}/kweets")
    fun tweets(@PathParam("id") id: Long): Response {

        val response = HashMap<String, Any>()
        response["success"] = true
        response["data"] = userService.kweets(id)
        response["message"] = "List of kweets"

        return Response.ok(response).link("http://localhost:8080/Kwetter-1.0-SNAPSHOT/api/kweets", "").build()
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
