package com.example.project_3.repository

import com.example.project_3.model.Device
import com.example.project_3.model.DeviceAPI
import com.example.project_3.model.User
import com.example.project_3.util.logD
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object Repository {
    lateinit var realtime: DatabaseReference
    lateinit var fireStore: DocumentReference

    private lateinit var uid: String

    fun init(uid: String) = apply {
        Repository.uid = uid
        realtime = FirebaseDatabase.getInstance().reference
        fireStore = FirebaseFirestore.getInstance().collection("User").document(Repository.uid)
    }

    fun getUser() = callbackFlow {
        val listener = fireStore.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val user = snapshot.toObject(User::class.java)
                offer(user)
            } else logD("Error : $error")
        }
        awaitClose { listener.remove() }
    }

    fun setUser(user: User) {
        fireStore.set(user).addOnCompleteListener {}
    }

    fun addDevice(device: Device) {
        CoroutineScope(IO).launch {
            getUser().collect {
                it!!.devices[device.id] = device
                setUser(it)
                this.cancel()
            }
        }
    }

    fun getDevice(id: String) = callbackFlow<DeviceAPI?> {
        val listener = realtime.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val device = p0.getValue(DeviceAPI::class.java)
                offer(device)
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        awaitClose { realtime.removeEventListener(listener) }
    }

}