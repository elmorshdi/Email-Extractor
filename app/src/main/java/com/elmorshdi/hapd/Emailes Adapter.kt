package com.elmorshdi.hapd;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class EmailsAdapter(
private val list: List<String>,
private val sub: String,
private val msg: String,
private val context: Context
)
: RecyclerView.Adapter<EmailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EmailViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val item: String = list[position]
        holder.bind(item,sub,msg,context)
    }

    override fun getItemCount(): Int = list.size

}

class EmailViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {
    private var mTitleView: TextView? = null
    private var cardView: CardView? = null


    init {
        mTitleView = itemView.findViewById(R.id.textView3)
       cardView = itemView.findViewById(R.id.card)
    }

    fun bind(item: String, sub: String, msg: String, context: Context) {
        mTitleView?.text = item
        cardView?.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(item.toString()))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub)
            emailIntent.putExtra(Intent.EXTRA_TEXT, msg)

            emailIntent.type = "message/rfc822"

            context.startActivity(emailIntent)
        })



    }

}