package com.example.project_3.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.project_3.R
import com.example.project_3.repository.Repository
import com.example.project_3.util.GoogleAuth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkLogin()
    }

    private fun checkLogin() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            findNavController(ID_Main_Navigation.id).navigate(R.id.action_dashboard_to_loginFragment)
        } else {
            Repository.init(user.uid)
        }
    }

    private var listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit =
        { _, _, _ -> }

    fun setOnActivityResultListener(listener: (requestCode: Int, resultCode: Int, data: Intent?) -> Unit) {
        this.listener = listener
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        listener.invoke(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.ID_Menu_switch -> {
                GoogleAuth(this).signOut {
                    finishAffinity()
                    startActivity(intent)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
