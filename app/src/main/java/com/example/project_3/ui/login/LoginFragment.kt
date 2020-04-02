package com.example.project_3.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.project_3.R
import com.example.project_3.ui.MainActivity
import com.example.project_3.util.GoogleAuth
import com.example.project_3.util.MyDialog
import com.example.project_3.util.logD
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener(view)
    }

    private fun initListener(view: View) {
        view.Button_Login.setOnClickListener {
            val loading = MyDialog(it.context).showLoading()
            GoogleAuth(requireActivity()).signIn(requireActivity()) {
                if (it != null) {
                    requireActivity().finishAffinity()
                    requireActivity().startActivity(Intent(requireActivity(),
                        MainActivity::class.java))
                } else {
                    logD("Login Gagal")
                }
                loading.dismiss()
            }
        }
    }

}
