package com.psw9999.android_mail_18.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.databinding.FragmentMailBinding
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAILDATA
import com.psw9999.android_mail_18.viewmodel.MailViewModel

class MailFragment : BaseFragment<FragmentMailBinding>(FragmentMailBinding::inflate) {
    private val mailViewModel : MailViewModel by viewModels()

    override fun initialViews() {
        initObserver()
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                drawerLayout.openDrawer(Gravity.LEFT)
            }
            navigationViewHome.setNavigationItemSelectedListener { menuItem ->
                mailViewModel.setEmailType(menuItem.itemId)
                true
            }
        }
    }

    private fun changeFragment(emailType: EmailType) {
        val transaction = childFragmentManager.beginTransaction()
        var targetFragment = childFragmentManager.findFragmentByTag(emailType.type)
        if (targetFragment == null) {
            targetFragment = getFragment(emailType.type)
            if (emailType != EmailType.Primary && childFragmentManager.backStackEntryCount == 0) transaction.addToBackStack(null)
            transaction.add(R.id.email_container, targetFragment, emailType.type)
        }
        transaction.show(targetFragment)
        EmailType.values()
            .filterNot { it == emailType }
            .forEach { emailType ->
                childFragmentManager.findFragmentByTag(emailType.type)?.let {
                    transaction.hide(it)
                }
            }
        transaction.commitAllowingStateLoss()
    }
    private fun getFragment(emailType : String): Fragment = EmailListFragment.newInstance(emailType)

    private fun initObserver() {
        mailViewModel.emailType.observe(viewLifecycleOwner) {
            changeFragment(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(emailList : ArrayList<Email>) =
            MailFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(EMAILDATA, emailList)
                }
            }
    }
}

enum class EmailType(val itemID : Int, val title : String, val type : String) {
    Primary(R.id.drawer_item_primary, "기본", "Primary"),
    Social(R.id.drawer_item_social, "소셜", "Social"),
    Promotion(R.id.drawer_item_promotion, "프로모션", "Promotion")
}