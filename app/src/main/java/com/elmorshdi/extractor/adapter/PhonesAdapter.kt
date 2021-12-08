package com.elmorshdi.extractor.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.other.Utility
import pub.devrel.easypermissions.EasyPermissions


class PhonesAdapter(private val list: List<String>,
                    private val context: Context,
                    private val activity: Activity,
                    private val sharedPreferences: SharedPreferences)
    : RecyclerView.Adapter<PhoneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PhoneViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val item: String = list[position]
        holder.bind(item,context,activity,sharedPreferences)
    }
    override fun getItemCount(): Int = list.size
}
class PhoneViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.phone_item, parent, false)) {
    private var phoneNum: TextView? = null
    private var btCall: ImageButton? = null
    private var btWa: ImageButton? = null

    init {
        phoneNum = itemView.findViewById(R.id.number)
        btCall = itemView.findViewById(R.id.call)
        btWa = itemView.findViewById(R.id.whatsapp)

    }

    fun bind(
        item: String,
        contexts: Context,
        activity: Activity?,
        sharedPreferences: SharedPreferences
    ) {
        phoneNum?.text = item
        val phone="2$item"

        btWa?.setOnClickListener(View.OnClickListener {
            try {
               val msg = if (sharedPreferences.getBoolean( "key_auto_add_msg_wa", false)) {
                    sharedPreferences.getString("key_select_msg_wa", "").toString()
                } else {
                    ""
                }
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phone&text=$msg")
                contexts.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        btCall?.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
             intent.data = Uri.parse("tel:" + phone)
            if (Utility.hasPermissions(contexts)) {
                contexts.startActivity(intent)}


            EasyPermissions.requestPermissions(
                activity!!,
                "You need to accept  permissions to use this app",
                0,
                android.Manifest.permission.CALL_PHONE

            )

        })

    }

}