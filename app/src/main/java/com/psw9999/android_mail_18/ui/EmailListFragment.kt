package com.psw9999.android_mail_18.ui

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.psw9999.android_mail_18.adapter.EmailAdapter
import com.psw9999.android_mail_18.base.BaseFragment
import com.psw9999.android_mail_18.databinding.FragmentEmailListBinding
import com.psw9999.android_mail_18.viewmodel.HomeViewModel

class EmailListFragment : BaseFragment<FragmentEmailListBinding>(FragmentEmailListBinding::inflate) {
    lateinit var emailAdapter: EmailAdapter
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun initialViews() {
        initRecyclerView()
        binding.textViewEmailType.text = arguments?.getString(EMAIL_TYPE)
    }

    private fun initRecyclerView() {
        emailAdapter = EmailAdapter()
        var filter = arguments?.getString(EMAIL_TYPE)
        emailAdapter.setEmailList(homeViewModel.mailArrayList.value!!.filter { it.type == filter})
        binding.recylcerViewEmail.adapter = emailAdapter
        binding.recylcerViewEmail.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        const val EMAIL_TYPE = "EMAIL_TYPE"

        @JvmStatic
        fun newInstance(emailType : String) =
            EmailListFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL_TYPE, emailType)
                }
            }
    }
}