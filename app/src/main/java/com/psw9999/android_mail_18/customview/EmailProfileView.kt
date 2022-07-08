package com.psw9999.android_mail_18.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.psw9999.android_mail_18.R
import com.psw9999.android_mail_18.databinding.EmailProfileViewBinding

class EmailProfileView : ConstraintLayout {

    private lateinit var binding : EmailProfileViewBinding
    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
        getAttrs(attrs, defStyleAttr)
    }

    private fun initView() {
        binding = EmailProfileViewBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
    }

    private fun getAttrs(attrs : AttributeSet?, defStyleAttr: Int? = null) {
        val typedArray = if (defStyleAttr == null) {
            context.obtainStyledAttributes(attrs,R.styleable.EmailProfile)
        } else {
            context.obtainStyledAttributes(attrs,R.styleable.EmailProfile, defStyleAttr, 0)
        }
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        val firstLetter = typedArray.getString(R.styleable.EmailProfile_firstLetter) ?: ""
        firstLetter(firstLetter)
    }

    fun firstLetter(firstLetter : String) {
        with(binding) {
            if (firstLetter.matches(letterRegex)) {
                imageViewProfile.setImageResource(0)
                val color = (firstLetter[0].code % 8)
                imageViewProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        colorList[color]
                    )
                )
                textViewInitial.text = firstLetter
                textViewInitial.visibility = View.VISIBLE
            } else {
                binding.imageViewProfile.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.grey
                    )
                )
                imageViewProfile.setImageResource(R.drawable.ic_baseline_person_24)
                textViewInitial.visibility = View.GONE
            }
        }
    }

    companion object {
        val letterRegex = Regex("[a-zA-z]")
        val colorList = listOf(
            R.color.red,
            R.color.orange,
            R.color.yellow,
            R.color.green,
            R.color.blue,
            R.color.deepblue,
            R.color.purple,
            R.color.greenyellow
        )
    }
}