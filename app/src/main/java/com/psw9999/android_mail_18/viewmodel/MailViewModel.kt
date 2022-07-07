package com.psw9999.android_mail_18.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.ui.EmailType

class MailViewModel : ViewModel() {
    private val _emailType = MutableLiveData(EmailType.Primary)
    val emailType : LiveData<EmailType> = _emailType

    private fun getEmailType(menuItemId : Int) : EmailType =
        when(menuItemId) {
            R.id.drawer_item_primary -> EmailType.Primary
            R.id.drawer_item_social -> EmailType.Social
            R.id.drawer_item_promotion -> EmailType.Promotion
            else -> throw IllegalArgumentException()
        }

    fun setEmailType(menuItemId: Int) : Boolean {
        changeCurrentFragment(getEmailType(menuItemId))
        return true
    }

    fun changeCurrentFragment(emailType : EmailType) {
        if (this.emailType.value != emailType) _emailType.value = emailType
    }

}