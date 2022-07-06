package com.psw9999.android_mail_18.ui

import android.os.Bundle
import android.view.View
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.databinding.FragmentSettingBinding
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAIL
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.NICKNAME

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textViewNickName.text = getString(R.string.setting_nickname).format(arguments?.getString(NICKNAME)?:"ERROR")
        binding.textViewEmail.text = getString(R.string.setting_email).format(arguments?.getString(EMAIL)?:"ERROR")
    }



    companion object {
        @JvmStatic
        fun newInstance(nickname : String?, email: String?) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(NICKNAME, nickname)
                    putString(EMAIL, email)
                }
            }
    }
}