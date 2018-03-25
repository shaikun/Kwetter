package com.kwetter

import com.kwetter.services.UserService
import java.io.Serializable
import javax.annotation.PostConstruct
import javax.faces.view.ViewScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@ViewScoped
class HomeBean : Serializable {

    @Inject
    private lateinit var userService: UserService

    var username = ""

    @PostConstruct
    fun load() {
        username = userService.find(1).username
    }
}
