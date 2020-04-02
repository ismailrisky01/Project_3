package com.example.project_3.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_3.R
import com.example.project_3.model.Device
import com.example.project_3.model.User
import com.example.project_3.repository.Repository
import com.example.project_3.ui.map.MapsFragment
import com.example.project_3.util.logD
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val activeDevice = MutableLiveData(Device())
    private val maps = MapsFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            Repository.getUser().collect { user ->
                checkUserInfo(user)
            }
        }
        activeDevice.observe(viewLifecycleOwner) {
            updateDevice(view, it)
        }
        initUI(view)
    }

    lateinit var recyclerAdapter: DashboardAdapter
    private fun initUI(view: View) {
        view.ID_Dashboard_RecyclerView.apply {
            layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = DashboardAdapter(arrayListOf(), activeDevice)
            recyclerAdapter = adapter as DashboardAdapter
        }
        childFragmentManager.beginTransaction().replace(ID_Map_View.id, maps).commit()

    }

    private fun updateUI(user: User) {
        recyclerAdapter.list = user.devices.values.toList()
        recyclerAdapter.notifyDataSetChanged()
    }

    private fun updateDevice(view: View, device: Device) {
        if(device.id != ""){
            view.ID_Detail_Nopol.text = device.nopol
            view.ID_Detail_Merk.text = device.merk
            lifecycleScope.launch {
                Repository.getDevice(device.id).collect { deviceAPI ->
                    maps.updateMapsPin(deviceAPI!!.latlng.googleLatLng())
                }
            }
        }
    }

    private fun checkUserInfo(user: User?) {
        if (user != null) {
            updateUI(user)
        } else {
            val firebaseUser = FirebaseAuth.getInstance().currentUser!!
            val myUser = User(firebaseUser.displayName.toString(), hashMapOf())
            Repository.setUser(myUser)
        }
    }
}
