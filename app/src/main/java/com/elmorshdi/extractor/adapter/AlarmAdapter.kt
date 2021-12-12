package com.elmorshdi.extractor.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.elmorshdi.extractor.R
import com.elmorshdi.extractor.db.AlarmDisplayModel
import com.elmorshdi.extractor.ui.fragment.ListOfAlarmsFragmentDirections

class AlarmAdapter(private val list: List<AlarmDisplayModel>) :
    RecyclerView.Adapter<AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AlarmViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val item: AlarmDisplayModel = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size

}

class AlarmViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {
    private var mTitleView: TextView? = null
    private var cardView: CardView? = null


    init {
         mTitleView = itemView.findViewById(R.id.textView3)
        cardView = itemView.findViewById(R.id.card)

    }

    fun bind(item: AlarmDisplayModel) {
        mTitleView?.text = item.title

        cardView?.setOnClickListener(View.OnClickListener {
            if (item.done){
                val action = ListOfAlarmsFragmentDirections.actionListOfAlarmsFragmentToAlarmViewFragment(item)
                cardView?.findNavController()?.navigate(action)
            }else{
                val action = ListOfAlarmsFragmentDirections.actionListOfAlarmsFragmentToUpdateFragment( item)
                cardView?.findNavController()?.navigate(action)
            }


        })
     }

}