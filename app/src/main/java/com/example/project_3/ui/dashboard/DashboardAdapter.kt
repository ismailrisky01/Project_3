package com.example.project_3.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.project_3.R
import com.example.project_3.model.Device
import kotlinx.android.synthetic.main.view_dashboard_list.view.*


class DashboardAdapter(
    var list: List<Device>,
    val activeDevice: MutableLiveData<Device>
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_dashboard_list,parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val device = list[position]
        holder.name.text = device.name
        holder.itemView.setOnClickListener { activeDevice.value = device }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.ID_DashboardList_Name
    }
}
