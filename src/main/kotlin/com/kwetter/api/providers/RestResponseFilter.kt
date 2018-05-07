package com.kwetter.api.providers

import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import javax.ws.rs.ext.Provider

@Provider
class RestResponseFilter : ContainerResponseFilter {
    override fun filter(requestContext: ContainerRequestContext, responseContext: ContainerResponseContext?) {
        if (responseContext != null) {
            responseContext.headers.add("Access-Control-Allow-Origin", "*")
            responseContext.headers.add("Access-Control-Allow-Credentials", "true")
            responseContext.headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
            responseContext.headers.add("Access-Control-Allow-Headers", "Content-Type, Accept")
        }
    }
}
