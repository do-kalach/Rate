package com.agening.rate

import android.app.Application
import com.agening.rate.model.UserService

class App:Application() {
    val usersService = UserService()
}