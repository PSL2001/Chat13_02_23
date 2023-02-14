package com.example.chat13_02_2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.chat13_02_2023.databinding.ActivityMainBinding
import com.example.chat13_02_2023.prefs.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val tarea = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val cuenta = tarea.getResult(ApiException::class.java)
                if (cuenta != null) {
                    val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credenciales).addOnCompleteListener {
                        if (it.isSuccessful) {
                            prefs.saveEmail(cuenta.email!!)
                            irUsuarios()
                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    private fun irUsuarios() {
        startActivity(Intent(this, UsuariosActivity::class.java))
    }


    lateinit var prefs: Prefs
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = Prefs(this)
        setContentView(binding.root)
        comprobarSesion()
        setListeners()
    }

    private fun comprobarSesion() {
        if (prefs.getEmail() != "") {
            irUsuarios()
        }
    }

    private fun setListeners() {
        binding.signInButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("471502603408-hqel89objm6789ogtg2hnuui4o4eb80o.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSignInOptions)
        googleClient.signOut()
        responseLauncher.launch(googleClient.signInIntent)
    }
}