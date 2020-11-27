package com.example.myguider

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import android.view.LayoutInflater
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.explore_menu_item.*


class MainActivity : AppCompatActivity() {

    var active = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each

        navView.setupWithNavController(navController)

        val mbottomNavigationMenuView =
            nav_view.getChildAt(0) as BottomNavigationMenuView

        mbottomNavigationMenuView.setOnClickListener(View.OnClickListener {

        })

        val view = mbottomNavigationMenuView.getChildAt(2)

        val itemView = view as BottomNavigationItemView

        val cart_badge = LayoutInflater.from(this)
            .inflate(
                R.layout.explore_menu_item,
                mbottomNavigationMenuView, false
            )

        itemView.addView(cart_badge)
    }
}
