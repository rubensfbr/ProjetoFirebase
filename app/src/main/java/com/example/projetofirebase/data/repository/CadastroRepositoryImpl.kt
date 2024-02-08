package com.example.projetofirebase.data.repository

import android.content.Context
import android.widget.Toast
import com.example.projetofirebase.domain.repository.CadastroRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import javax.inject.Inject

class CadastroRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : CadastroRepository {

    override suspend fun cadastrarUsuario(context: Context, email: String, senha: String) {

        firebaseAuth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { resultado ->
                if (resultado.isSuccessful) {
                    Toast.makeText(context, "Sucesso ao fazer cadastro", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { erro ->
                try {
                    throw erro
                } catch (erro: FirebaseAuthUserCollisionException) {
                    Toast.makeText(context, "E-mail já está em uso", Toast.LENGTH_SHORT).show()
                } catch (erro: FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "E-mail inválido", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
