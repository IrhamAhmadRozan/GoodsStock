package com.example.goodstcok.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.goodstcok.R
import com.example.goodstcok.data.api.RemoteService
import com.example.goodstcok.data.api.entity.BaseResponse
import com.example.goodstcok.data.local.sharedpref.UserPref
import org.json.JSONException


class ProfileFragment : Fragment() {
    private lateinit var llAccountSetting : LinearLayout

    private val remoteService = RemoteService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = UserPref.init.getToken()
        view.findViewById<ConstraintLayout>(R.id.constraintLayout6).setOnClickListener {
            logoutAction(token)
        }
    }

    private fun logoutAction(token: String) {
        remoteService.logoutUser("Token $token", object : BaseResponse<String> {
            override fun onSuccess(response: String) {
                try {
                    logoutBerhasil(response)
                } catch (e: JSONException) {
                    logoutGagal(e)
                }
            }

            override fun onError(error: String) {
                logoutGagal(error)
            }
        })
    }

    private fun logoutBerhasil(token: String) {
        Toast.makeText(activity, "Berhasil Logout!", Toast.LENGTH_SHORT).show()
        UserPref.init.logout()
        startActivity(Intent(activity, DashboardActivity::class.java))
    }

    private fun logoutGagal(e: JSONException) {
        Toast.makeText(activity, "Error : e.message", Toast.LENGTH_SHORT).show()
    }

    private fun logoutGagal(e: String) {
        Toast.makeText(activity, "Error : $e", Toast.LENGTH_SHORT).show()
    }
}