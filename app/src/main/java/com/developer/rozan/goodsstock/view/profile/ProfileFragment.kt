package com.developer.rozan.goodsstock.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
import com.developer.rozan.goodsstock.data.local.sharedpref.UserPref
import com.developer.rozan.goodsstock.view.dashboard.DashboardActivity
import com.developer.rozan.goodsstock.view.detail_profile.DetailProfileActivity
import com.developer.rozan.goodsstock.view.login.LoginActivity
import com.developer.rozan.goodsstock.view.splashscreen.SplashScreenActivity
import org.json.JSONException

class ProfileFragment : Fragment() {

    private lateinit var tvProfileName : TextView
    private lateinit var tvProfileEmail : TextView
    private lateinit var llAccountSetting : LinearLayout
    private lateinit var llHelp : LinearLayout
    private lateinit var llAbout : LinearLayout
    private lateinit var clLogout : ConstraintLayout

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

        tvProfileName = view.findViewById(R.id.tv_profile_name)
        tvProfileEmail = view.findViewById(R.id.tv_profile_email)
        llAccountSetting = view.findViewById(R.id.ll_account_setting)
        llHelp = view.findViewById(R.id.ll_help)
        llAbout = view.findViewById(R.id.ll_about)
        clLogout = view.findViewById(R.id.cl_logout)

        llAccountSetting.setOnClickListener {
            startActivity(Intent(requireContext(), DetailProfileActivity::class.java).apply {
                putExtra("name", tvProfileName.text.toString())
                putExtra("email", tvProfileEmail.text.toString())
            })
        }

        llHelp.setOnClickListener {
            Toast.makeText(requireContext(), "Help is under development.", Toast.LENGTH_SHORT).show()
        }

        llAbout.setOnClickListener {
            Toast.makeText(requireContext(), "About is under development.", Toast.LENGTH_SHORT).show()
        }

        clLogout.setOnClickListener {
            logoutAction()
        }
    }

    private fun logoutAction() {
        val token = UserPref.init.getToken()

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
        startActivity(Intent(activity, SplashScreenActivity::class.java))
    }

    private fun logoutGagal(e: JSONException) {
        Toast.makeText(activity, "Error : e.message", Toast.LENGTH_SHORT).show()
    }

    private fun logoutGagal(e: String) {
        Toast.makeText(activity, "Error : $e", Toast.LENGTH_SHORT).show()
    }
}