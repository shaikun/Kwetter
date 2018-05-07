package com.kwetter

import com.kwetter.models.User
import com.kwetter.services.UserService
import java.io.IOException
import java.io.Serializable
import javax.annotation.PostConstruct
import javax.faces.application.FacesMessage
import javax.faces.context.FacesContext
import javax.faces.view.ViewScoped
import javax.inject.Inject
import javax.inject.Named
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@Named
@ViewScoped
class LoginBean : Serializable {

    @Inject
    private lateinit var userService: UserService

    var email: String? = null
    var password: String? = null
    var authenticatedUser: User? = null

    @PostConstruct
    fun init() {
    }

    fun preRender() {

        if (authenticatedUser !== null) {
            val facesContext = FacesContext.getCurrentInstance()
            val externalContext = facesContext.externalContext
            externalContext.redirect(externalContext.requestContextPath + "/admin/index.xhtml")
        }

    }

    fun logger() {
        println("tested")
    }

    fun login(): String {

        val context = FacesContext.getCurrentInstance()
        val request = context.externalContext.request as HttpServletRequest

        try {
            request.login(email, password)
        } catch (e: ServletException) {
            context.addMessage(null, FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null))
            return "index"
        }

        val principal = request.userPrincipal
        this.authenticatedUser = userService.findByEmail(principal.name)
        val externalContext = FacesContext.getCurrentInstance().externalContext
        val sessionMap = externalContext.sessionMap
        sessionMap["User"] = authenticatedUser

        return if (request.isUserInRole("user")) {
            "/admin/index"
        } else {
            "index"
        }

    }

    @Throws(IOException::class)
    fun logout() {
        val facesContext = FacesContext.getCurrentInstance()
        val externalContext = facesContext.externalContext
        val request = facesContext.externalContext.request as HttpServletRequest
        try {
            this.authenticatedUser = null
            request.logout()
            // clear the session
            (facesContext.externalContext.getSession(false) as HttpSession).invalidate()
        } catch (e: ServletException) {
            e.printStackTrace()
        }

        externalContext.redirect(externalContext.requestContextPath + "index.xhtml")
    }
}
