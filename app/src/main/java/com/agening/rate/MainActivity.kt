package com.agening.rate

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.agening.rate.databinding.ActivityMainBinding
import com.agening.rate.model.*

class MainActivity : AppCompatActivity() {

    // https://www.youtube.com/watch?v=WMVzidyoQag&list=PLRmiL0mct8WnodKkGLpBN0mfXIbAAX-Ux&index=20
    // time 16:05

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter

    private val userService: UserService
        get()= (applicationContext as App).usersService

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        adapter = UsersAdapter(
            object :UserActionListener{
                override fun onUserMove(user: User, moveBy: Int) {
                    userService.moveUser(user, moveBy)
                }

                override fun onUserDelete(user: User) {
                    userService.deleteUser(user)
                }

                override fun onUserDetails(user: User) {
                    Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
                }

            }
        )
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        userService.addListener(usersListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.removeListener(usersListener)
    }

    private val usersListener:UserListener = {
        adapter.users = it
    }
}