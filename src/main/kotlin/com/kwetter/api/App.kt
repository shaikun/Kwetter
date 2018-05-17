package com.kwetter.api

import com.kwetter.api.providers.RestResponseFilter
import com.kwetter.api.routes.Auth
import com.kwetter.api.routes.Kweets
import com.kwetter.api.routes.Users
import com.kwetter.filters.AuthorizationFilter
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/api/*")
class App : Application() {

    override fun getClasses(): Set<Class<*>> {
        val classes = mutableSetOf<Class<*>>()

        classes.add(JacksonFeature::class.java)
        classes.add(JacksonJsonProvider::class.java)
        classes.add(RestResponseFilter::class.java)
        classes.add(AuthorizationFilter::class.java)
        classes.add(Users::class.java)
        classes.add(Kweets::class.java)
        classes.add(Auth::class.java)

        return classes
    }
}
