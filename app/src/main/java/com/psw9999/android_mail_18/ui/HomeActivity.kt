package com.psw9999.android_mail_18.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.databinding.ActivityHomeBinding
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAIL
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAILDATA
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.NICKNAME
import com.psw9999.android_mail_18.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel : HomeViewModel by viewModels()
    private var backKeyPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    override fun onBackPressed() {
        if (viewModel.currentFragment.value != FragmentType.EMAIL) {
            binding.bottomNavigation.selectedItemId = R.id.menu_mail
            viewModel.changeCurrentFragment(FragmentType.EMAIL)
        } else {
            if (System.currentTimeMillis() > backKeyPressTime + 2000) {
                backKeyPressTime = System.currentTimeMillis()
                Toast.makeText(this,getString(R.string.backPressWarning),Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }

    private fun changeFragment(fragmentType: FragmentType) {
        val transaction = supportFragmentManager.beginTransaction()
        var targetFragment = supportFragmentManager.findFragmentByTag(fragmentType.tag)
        if (targetFragment == null) {
            targetFragment = getFragment(fragmentType)
            transaction.add(R.id.container, targetFragment, fragmentType.tag)
        }
        transaction.show(targetFragment)

        FragmentType.values()
            .filterNot { it == fragmentType }
            .forEach { type ->
                supportFragmentManager.findFragmentByTag(type.tag)?.let {
                    transaction.hide(it)
                }
            }
        transaction.commitAllowingStateLoss()
    }

    private fun getFragment(fragmentType: FragmentType): Fragment =
        when(fragmentType) {
            FragmentType.EMAIL -> MailFragment.newInstance(
                emailList = intent.getParcelableArrayListExtra<Email>(EMAILDATA) as ArrayList<Email>
            )
            FragmentType.SETTING -> SettingFragment.newInstance(
                nickname = intent.getStringExtra(NICKNAME),
                email = intent.getStringExtra(EMAIL))
            }

    private fun initViews() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            viewModel.setCurrentFragment(menuItem.itemId)
        }
    }

    private fun initObserver() {
        viewModel.currentFragment.observe(this) {
            changeFragment(it)
        }
    }
}

enum class FragmentType(val tag : String) {
    EMAIL("EMAIL"),
    SETTING("SETTING")
}