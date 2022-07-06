package com.psw9999.android_mail_18.ui

import android.view.Gravity
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.databinding.FragmentMailBinding

class MailFragment : BaseFragment<FragmentMailBinding>(FragmentMailBinding::inflate) {

    override fun initialViews() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                drawerLayout.openDrawer(Gravity.LEFT)
            }
            navigationViewHome.setNavigationItemSelectedListener { menuItem ->
                    menuItem.isChecked = true
                    true
                }
            }
        }
    }