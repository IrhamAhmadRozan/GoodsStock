package com.developer.rozan.goodsstock.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.developer.rozan.goodsstock.R
import com.developer.rozan.goodsstock.data.api.RemoteService
import com.developer.rozan.goodsstock.data.api.entity.BaseResponse
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

        llAccountSetting = view.findViewById(R.id.ll_account_setting)

        llAccountSetting.setOnClickListener {
            logoutAction
        }
    }

    private fun logoutAction(token: String) {
        remoteService.logoutUser(token, object : BaseResponse<String> {
            override fun onSuccess(response: String) {
                try {
                    loginBerhasil(response)
                } catch (e: JSONException) {
                    loginGagal(e)
                }
            }

            override fun onError(error: String) {
                loginGagal(error)
            }

        })
    }
}