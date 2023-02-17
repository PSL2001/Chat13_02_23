package com.example.chat13_02_2023.adapters

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.chat13_02_2023.databinding.UsuarioLayoutBinding
import com.example.chat13_02_2023.models.Usuarios
import com.google.firebase.storage.FirebaseStorage

class UsuariosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = UsuarioLayoutBinding.bind(v)
    private val storage = FirebaseStorage.getInstance("gs://proyecto-chat-13-02-23.appspot.com")

    fun render(usuario: Usuarios) {
        binding.tvEmail.text = usuario.email
        binding.tvCiudad.text = usuario.username
        agregarFoto(usuario.email.toString())
    }

    private fun agregarFoto(email: String) {
        val ref = storage.reference
        val file = ref.child("$email/perfil.jpg")
        file.metadata.addOnSuccessListener {
            //La foto existe, la descargamos
            file.downloadUrl.addOnSuccessListener { uri ->
                rellenaImagen(uri)
            }
        }
            .addOnFailureListener {
            //La foto no existe, ponemos una por defecto
            val default = ref.child("default/perfil.jpg")
            default.downloadUrl.addOnSuccessListener { uri ->
                rellenaImagen(uri)
            }
        }
    }

    private fun rellenaImagen(uri: Uri) {
        val requestOptions = RequestOptions().transform(CircleCrop())
        Glide.with(binding.ivPerfil.context)
            .load(uri)
            .centerCrop()
            .apply(requestOptions)
            .into(binding.ivPerfil)
    }

}
