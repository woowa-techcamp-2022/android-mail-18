package com.psw9999.android_mail_18.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.data.Content
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}

    private val nickNameRegex = Regex("[A-Z|a-z|0-9]{4,12}")
    private val emailRegex = Regex("^[a-zA-Z0-9_]{4,12}+@[a-zA-Z0-9._]+\\.[a-zA-Z]{2,6}")
    private val checkInputList = arrayOf(false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        test()
        getSavedInstanceState(savedInstanceState)
    }

    private fun initViews() {
        with(binding) {
            editTextEmail.editText?.doOnTextChanged { inputText, _, _, _ ->
                inputText?.let { inputText->
                    checkEditText(editTextEmail, inputText, emailRegex, getString(R.string.email_error), 0)
                }
            }

            editTextNickname.editText?.doOnTextChanged { inputText, _, _, _ ->
                inputText?.let { inputText->
                    checkEditText(editTextNickname, inputText, nickNameRegex, getString(R.string.nickname_error), 1)
                }
            }

            buttonNext.setOnClickListener {
                val homeIntent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                    this.putExtra(NICKNAME, editTextNickname.editText?.text.toString())
                    this.putExtra(EMAIL, editTextEmail.editText?.text.toString())
                    this.putParcelableArrayListExtra(EMAILDATA, createDummyData())
                }
                startActivity(homeIntent)
                finish()
            }
        }
    }

    private fun checkEditText(textInputLayout : TextInputLayout, inputText : CharSequence, regex : Regex, error : String, i : Int) {
        if(regex.matches(inputText)) {
            checkInputList[i] = true
            textInputLayout.error = null
        }else{
            checkInputList[i] = false
            textInputLayout.error = error
        }
        binding.buttonNext.isEnabled = checkInputList.all { it }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(NICKNAME, binding.editTextNickname.editText?.text.toString())
        outState.putString(EMAIL, binding.editTextEmail.editText?.text.toString())
    }

    private fun getSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let{
            binding.editTextNickname.editText?.setText(it.getString(NICKNAME))
            binding.editTextEmail.editText?.setText(it.getString(EMAIL))
        }
    }

    private fun createDummyData() : ArrayList<Email> = arrayListOf(
            Email("Primary", Content("Google", "보안 알림", "Android에서 새로 로그인함. abcdek abcdek", "2022-05-11")),
            Email("Social", Content("김영희", "1촌 맺어요", "1촌을 맺고 싶습니다.", "2022-07-12")),
            Email("Promotion", Content("Zoom", "성공적인 회의 호스트를 위한 팁", "줌 좋아요", "2022-11-11")),
            Email("Promotion", Content("김철수", "철수철수철수", "철수철수철수철수철수철수", "2022-01-11")),
            Email("Promotion", Content("신세계백화점", "여름맞이세일", "여름맞이 대세일", "2022-11-11")),
            Email("Social", Content("LinkedIn", "제이가 1촌 신청을 보냈습니다.", "인맥키우기 활성화", "2022-05-11")),
            Email("Social", Content("Facebook", "가입 하신 그룹에 새로운 글이 등록됐습니다.", "ㅁㅇ머어머넝먼어ㅓㅁㄴ", "2022-05-11")),
            Email("Promotion", Content("김나나", "김나나나", "김나나나나나나난나나나나나", "2022-05-11")),
            Email("Primary", Content("KIM", "보안 알림", "Android에서 새로 로그인함. abcdek abcdek", "2022-05-11")),
            Email("Primary", Content("ParkSeungWoon", "보안 알림", "Android에서 새로 로그인함. abcdek abcdek", "2022-05-11")),
            Email("Promotion", Content("우아한테크캠프5기", "5기를 모집합니다.", "우아한 개발자가 되고 싶은 사람은 나에게..", "2022-05-17")),
            Email("Primary", Content("abcdek", "abcdefg", "abcdekabcdekabcdek", "2022-04-11")),
            Email("Promotion", Content("kkkkkk", "kkkkkk", "kkkkkkkkkkkkkkkkkkkkk", "2022-11-11")))

    private fun test() {
        binding.imageViewEmail.setOnClickListener {
            val homeIntent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                this.putExtra(NICKNAME, "psw9999")
                this.putExtra(EMAIL, "abcd@cdcdc.com")
                this.putParcelableArrayListExtra(EMAILDATA, createDummyData())
            }
            startActivity(homeIntent)
            finish()
        }
    }

    companion object {
        const val NICKNAME = "NICKNAME"
        const val EMAIL = "EMAIL"
        const val EMAILDATA = "EMAILLIST"
    }
}