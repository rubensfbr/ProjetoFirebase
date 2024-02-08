package com.example.projetofirebase.di

import com.example.projetofirebase.data.repository.AdicionarProdutoRepositoryImpl
import com.example.projetofirebase.data.repository.CadastroRepositoryImpl
import com.example.projetofirebase.data.repository.CategoriaRepositoryImpl
import com.example.projetofirebase.data.repository.LoginRepositoryImpl
import com.example.projetofirebase.data.repository.MainFragmentRepositoryImpl
import com.example.projetofirebase.data.repository.MainRepositoryImpl
import com.example.projetofirebase.data.repository.ProdutosRepositoryImpl
import com.example.projetofirebase.domain.repository.AdicionarProdutoRepository
import com.example.projetofirebase.domain.repository.CadastroRepository
import com.example.projetofirebase.domain.repository.CategoriaRepository
import com.example.projetofirebase.domain.repository.LoginRepository
import com.example.projetofirebase.domain.repository.MainFragmentRepository
import com.example.projetofirebase.domain.repository.MainRepository
import com.example.projetofirebase.domain.repository.ProdutosRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideCadastroRepository(firebaseAuth: FirebaseAuth): CadastroRepository {
        return CadastroRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository {
        return LoginRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideMainRepository(firebaseAuth: FirebaseAuth): MainRepository {
        return MainRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideCategoriaRepository(storage: FirebaseStorage, firestore: FirebaseFirestore)
            : CategoriaRepository {
        return CategoriaRepositoryImpl(storage, firestore)
    }

    @Provides
    fun provideMainFragmentRepository(
        firestore: FirebaseFirestore, storage: FirebaseStorage
    ): MainFragmentRepository {
        return MainFragmentRepositoryImpl(firestore, storage)
    }

    @Provides
    fun provideProdutosRepository(
        firestore: FirebaseFirestore, storage: FirebaseStorage
    ): ProdutosRepository {
        return ProdutosRepositoryImpl(firestore, storage)
    }

    @Provides
    fun provideAdicionarProdutoRepository(
        firestore: FirebaseFirestore, storage: FirebaseStorage
    ): AdicionarProdutoRepository {
        return AdicionarProdutoRepositoryImpl(firestore, storage)
    }

}