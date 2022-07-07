package com.psw9999.android_mail_18.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        viewModel.setMailArrayList(intent.getParcelableArrayListExtra<Email>(EMAILDATA) as ArrayList<Email>)
        initObserver()
    }

    override fun onBackPressed() {
        val mailFragment = supportFragmentManager.findFragmentByTag(FragmentType.EMAIL.tag)
        mailFragment?.let{ mailFragment ->
            if(mailFragment.isVisible) {
                if (mailFragment.childFragmentManager.backStackEntryCount == 1) {
                    mailFragment.childFragmentManager.popBackStackImmediate()
                } else {
                    finish()
                }
            } else {
                if (supportFragmentManager.backStackEntryCount == 1) {
                    binding.bottomNavigation?.selectedItemId = R.id.menu_mail
                    binding.homeNavigationRail?.selectedItemId = R.id.menu_mail
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    private fun changeFragment(fragmentType: FragmentType) {
        val transaction = supportFragmentManager.beginTransaction()
        var targetFragment = supportFragmentManager.findFragmentByTag(fragmentType.tag)
        if (targetFragment == null) {
            targetFragment = getFragment(fragmentType)
            if (fragmentType == FragmentType.EMAIL) {
                transaction
                    .add(R.id.container, targetFragment, fragmentType.tag)
            }else{
                if (supportFragmentManager.backStackEntryCount == 0) transaction.addToBackStack(null)
                transaction.add(R.id.container, targetFragment, fragmentType.tag)
            }
        }

        if (fragmentType != FragmentType.EMAIL) {
            val mailFragment = supportFragmentManager.findFragmentByTag(FragmentType.EMAIL.tag)
            mailFragment?.let { mailFragment ->
                mailFragment.childFragmentManager.popBackStackImmediate()
            }
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
//            FragmentType.EMAIL -> MailFragment.newInstance(
//                emailList = intent.getParcelableArrayListExtra<Email>(EMAILDATA) as ArrayList<Email>
//            )
            FragmentType.EMAIL -> MailFragment()
            FragmentType.SETTING -> SettingFragment.newInstance(
                nickname = intent.getStringExtra(NICKNAME),
                email = intent.getStringExtra(EMAIL))
            }

    private fun initViews() {
        binding.bottomNavigation?.tabInitial()
        binding.homeNavigationRail?.tabInitial()
    }

    private fun initObserver() {
        viewModel.currentFragment.observe(this) {
            changeFragment(it)
        }
    }

    private fun NavigationBarView.tabInitial() {
        this.setOnItemSelectedListener { menuItem ->
            viewModel.setCurrentFragment(menuItem.itemId)
        }
        this.selectedItemId = viewModel.currentFragment.value?.itemId ?: R.id.menu_mail
    }
}

enum class FragmentType(val tag : String, val itemId : Int) {
    EMAIL("EMAIL", R.id.menu_mail),
    SETTING("SETTING", R.id.menu_setting)
}