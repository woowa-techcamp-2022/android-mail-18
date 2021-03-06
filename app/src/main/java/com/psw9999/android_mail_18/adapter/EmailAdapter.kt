package com.psw9999.android_mail_18.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.psw9999.android_mail_18.data.Email
import com.psw9999.android_mail_18.databinding.ItemMailBinding
import java.util.ArrayList

class EmailAdapter : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {
    private var emailList : List<Email> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val binding = ItemMailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        //TODO : Databinding 고려
        with(emailList[position]) {
            holder.binding.imageViewSender.firstLetter(content.sender.substring(0 until 1))
            holder.binding.textViewTitle.text = content.title
            holder.binding.textViewContent.text = content.content
            holder.binding.textViewName.text = content.sender
        }
    }

    override fun getItemCount(): Int = emailList.size

    fun setEmailList(emailList : List<Email>) {
        this.emailList = emailList
        notifyDataSetChanged()
    }

    class EmailViewHolder(val binding : ItemMailBinding) : RecyclerView.ViewHolder(binding.root)

}