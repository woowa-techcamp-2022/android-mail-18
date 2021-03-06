package com.psw9999.android_mail_18.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.ui.FragmentType

class HomeViewModel : ViewModel() {
    private val _currentFragment = MutableLiveData(FragmentType.EMAIL)
    val currentFragment : LiveData<FragmentType> = _currentFragment

    private val _mailArrayList = MutableLiveData<ArrayList<Email>>()
    val mailArrayList : LiveData<ArrayList<Email>> = _mailArrayList

    private fun getFragmentType(menuItemId : Int) : FragmentType =
        when(menuItemId) {
            R.id.menu_mail -> FragmentType.EMAIL
            R.id.menu_setting -> FragmentType.SETTING
            else -> throw IllegalArgumentException()
        }

    fun setCurrentFragment(menuItemId: Int) : Boolean {
        changeCurrentFragment(getFragmentType(menuItemId))
        return true
    }

    fun setMailArrayList(mailArrayList : ArrayList<Email>) {
        this._mailArrayList.value = mailArrayList
    }

    fun changeCurrentFragment(fragmentType : FragmentType) {
        if (currentFragment.value != fragmentType) _currentFragment.value = fragmentType
    }
}