package com.psw9999.android_mail_18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.psw9999.android_mail_18.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater)}

    private val nickNameRegex = Regex("[A-Z|a-z|0-9]{4,12}")
    private val emailRegex = Regex("^[a-zA-Z0-9_]{4,12}+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    private val checkInputList = arrayOf(false, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getSavedInstanceState(savedInstanceState)
        initEditText()
    }

    // TODO : 화면 돌림시 조건을 만족하여도 Button 다시 Disable됨. -> LiveData로 수정?
    private fun initEditText() {
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

    companion object {
        const val NICKNAME = "NICKNAME"
        const val EMAIL = "EMAIL"
    }
}