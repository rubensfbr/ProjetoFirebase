package com.example.projetofirebase.data.repository

import com.example.projetofirebase.domain.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    MainRepository {

    override suspend fun deslogar() {
        firebaseAuth.signOut()
    }
}