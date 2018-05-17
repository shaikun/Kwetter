package com.kwetter.filters

import com.kwetter.api.annotations.Secured
import com.kwetter.services.TokenService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import java.io.IOException
import java.lang.reflect.AnnotatedElement
import javax.annotation.Priority
import javax.ws.rs.NotAuthorizedException
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ResourceInfo
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider

@Provider
@Secured
@Priority(Priorities.AUTHORIZATION)
class AuthorizationFilter : ContainerRequestFilter {

    @Context
    private lateinit var resourceInfo: ResourceInfo

    @Throws(IOException::class)
    override fun filter(requestContext: ContainerRequestContext) {

        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw NotAuthorizedException("Authorization header must be provided")
        }
        val token = authorizationHeader.substring("Bearer".length).trim({ it <= ' ' })

        try {
            val userRole: String = TokenService.validateToken(token)

            println(userRole)

            val classRoles = extractRoles(resourceInfo.resourceClass)
            val methodRoles = extractRoles(resourceInfo.resourceMethod)

            if (methodRoles.isNotEmpty()) {
                if (!methodRoles.contains(userRole)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build())
                }
            }
            if (classRoles.isNotEmpty()) {
                if (!classRoles.contains(userRole)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build())
                }
            }
        } catch (e: ExpiredJwtException) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        } catch (e: UnsupportedJwtException) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        } catch (e: MalformedJwtException) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        } catch (e: SignatureException) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        } catch (e: IllegalArgumentException) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
        }

    }

    private fun extractRoles(annotatedElement: AnnotatedElement?): Array<String> {
        val roles = emptyArray<String>()
        if (annotatedElement == null) {
            return roles
        } else {
            val secured = annotatedElement.getAnnotation(Secured::class.java)
            if (secured == null) {
                return roles
            } else {
                val allowedRoles = secured.value
                return allowedRoles as Array<String>
            }
        }
    }

}