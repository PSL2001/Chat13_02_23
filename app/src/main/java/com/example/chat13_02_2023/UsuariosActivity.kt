package com.example.chat13_02_2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat13_02_2023.adapters.UsuariosAdapter
import com.example.chat13_02_2023.databinding.ActivityUsuariosBinding
import com.example.chat13_02_2023.models.Usuarios
import com.example.chat13_02_2023.prefs.Prefs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsuariosActivity : AppCompatActivity() {
    lateinit var prefs: Prefs
    lateinit var binding: ActivityUsuariosBinding
    lateinit var adapter: UsuariosAdapter
    lateinit var db: FirebaseDatabase

    var lista = ArrayList<Usuarios>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuariosBinding.inflate(layoutInflater)
        prefs = Prefs(this)
        db = FirebaseDatabase.getInstance("https://proyecto-chat-13-02-23-default-rtdb.europe-west1.firebasedatabase.app/")
        setContentView(binding.root)
        setRecycler()
        getUsuarios()
    }

    private fun getUsuarios() {
        db.getReference("usuarios").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setRecycler() {
        adapter = UsuariosAdapter(lista)
        binding.recUsers.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recUsers.layoutManager = layoutManager
    }
}