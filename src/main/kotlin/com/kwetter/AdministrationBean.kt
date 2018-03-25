package com.kwetter

import com.kwetter.models.Kweet
import com.kwetter.models.User
import com.kwetter.services.KweetService
import com.kwetter.services.UserService
import java.io.Serializable
import javax.annotation.PostConstruct
import javax.faces.view.ViewScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@ViewScoped
class AdministrationBean : Serializable {
    @Inject
    private lateinit var kweetService: KweetService

    @Inject
    private lateinit var userService: UserService

    var kweets: MutableSet<Kweet> = mutableSetOf()
    var users: MutableSet<User> = mutableSetOf()

    var username = ""

    @PostConstruct
    fun load() {
        kweets = kweetService.all().toMutableSet()
        users = userService.all().toMutableSet()

        username = userService.find(1).username
    }

    fun removeKweet(kweet: Kweet) {
        kweetService.remove(kweet.id!!)
        kweets.remove(kweet)
    }
}
