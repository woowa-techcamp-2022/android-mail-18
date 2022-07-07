package com.psw9999.android_mail_18.ui

import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.LinearLayoutManager
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.adapter.EmailAdapter
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.databinding.FragmentMailBinding
import com.psw9999.android_mail_18.ui.LoginActivity.Companion.EMAILDATA

class MailFragment : BaseFragment<FragmentMailBinding>(FragmentMailBinding::inflate) {
    lateinit var emailAdapter: EmailAdapter

    override fun initialViews() {
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                drawerLayout.openDrawer(Gravity.LEFT)
            }
            navigationViewHome.setNavigationItemSelectedListener { menuItem ->
                val emailType = EmailType.values().filter{it.itemID == menuItem.itemId}[0]
                textViewEmailType.text = emailType.title
                emailAdapter.setEmailType(emailType.type)
                true
            }
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        emailAdapter = EmailAdapter()
        arguments?.getParcelableArrayList<Email>(EMAILDATA)?.let { emailAdapter.setEmailList(it) }
        emailAdapter.setEmailType(EmailType.Primary.type)
        binding.textViewEmailType.text = EmailType.Primary.title
        binding.recyclerViewEmail.adapter = emailAdapter
        binding.recyclerViewEmail.layoutManager = LinearLayoutManager(requireContext())
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
    Primary(R.id.drawer_item_primary, "중요", "Primary"),
    Social(R.id.drawer_item_social, "소셜", "Social"),
    Promotion(R.id.drawer_item_promotion, "프로모션", "Promotion")
}