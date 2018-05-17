package com.kwetter.api.annotations

import javax.ws.rs.NameBinding

@NameBinding
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Secured(vararg val value: String)