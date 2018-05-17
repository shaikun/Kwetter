package com.kwetter.api.routes


import com.kwetter.api.annotations.Secured
import com.kwetter.models.User
import com.kwetter.services.TokenService
import com.kwetter.services.UserService
import java.util.Calendar
import javax.ejb.Stateless
import javax.inject.Inject
import javax.json.Json
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response


@Stateless
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
class Auth {

    /**
     * UserService instance.
     */
    @Inject
    private lateinit var userService: UserService

    /**
     * Authenticate a user
     */
    @POST
    @Produces("application/json")
    fun authenticateUser(@FormParam("email") email: String, @FormParam("password") password: String): Response {
        val role = userService.login(email, password)

        return if (role !== null) {
            Response.ok(
                    Json.createObjectBuilder().add("token", createToken(email, role)).build(),
                    MediaType.APPLICATION_JSON
            ).build()
        } else {
            // If role is not found, user is not known/properly registered. Therefore unauthorized.
            Response.status(Response.Status.UNAUTHORIZED).build()
        }
    }

    /**
     * Get User by token
     */
    @GET
    @Secured("users")
    @Produces("application/json")
    @Path("/{token}")
    fun getUserByToken(@PathParam("token") token: String): User? {
        val email = TokenService.getEmailByToken(token)
        return userService.findByEmail(email!!)
    }

    /**
     * Creates a token
     */
    private fun createToken(email: String, role: String?): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.HOUR_OF_DAY, 3)
        return TokenService.createToken(email, role, cal.timeInMillis)
    }

}