package com.example.projetofirebase.data.repository

import android.content.Context
import android.widget.Toast
import com.example.projetofirebase.domain.repository.LoginRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    LoginRepository {

    override suspend fun verificarUsuarioLogado(): Boolean {
        val user = firebaseAuth.currentUser
        return user != null
    }

    override suspend fun logarUsuario(context: Context, email: String, senha: String):
            Task<AuthResult> {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnSuccessListener {
                Toast.makeText(context, "Logado com Sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { erro ->
                try {
                    throw erro
                } catch (erro: FirebaseAuthInvalidUserException) {
                    Toast.makeText(context, "E-mail não cadastrado", Toast.LENGTH_SHORT).show()
                } catch (erro: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
            }
        return authResult
    }
}