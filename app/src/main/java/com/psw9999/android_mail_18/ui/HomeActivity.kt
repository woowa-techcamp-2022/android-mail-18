package com.psw9999.android_mail_18.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.databinding.ActivityHomeBinding
import com.psw9999.android_mail_18.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater)}
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initObserver()
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
            FragmentType.EMAIL -> MailFragment()
            FragmentType.SETTING -> SettingFragment()
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