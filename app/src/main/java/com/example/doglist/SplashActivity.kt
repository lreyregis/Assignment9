package com.example.doglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.doglist.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var image_logo_view = findViewById(R.id.splash_screen_logo_imageView) as ImageView
        image_logo_view.alpha = 0f
        image_logo_view.animate().setDuration(3000).alpha(1f)
        image_logo_view.animate().setDuration(3000).alpha(1f).withEndAction {
            val login_page = Intent(this, LoginActivity::class.java)
            startActivity(login_page)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}