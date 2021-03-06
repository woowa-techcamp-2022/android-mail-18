package com.psw9999.android_mail_18.ui

import android.os.Bundle
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.databinding.FragmentSettingBinding
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAIL
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.NICKNAME

class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {

    override fun initialViews() {
        with(binding) {
            textViewNickName.text = getString(R.string.setting_nickname).format(arguments?.getString(NICKNAME)?:"ERROR")
            textViewEmail.text = getString(R.string.setting_email).format(arguments?.getString(EMAIL)?:"ERROR")
        }
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