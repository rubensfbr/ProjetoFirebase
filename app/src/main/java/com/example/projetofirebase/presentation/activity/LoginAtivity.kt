package com.example.projetofirebase.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projetofirebase.databinding.ActivityLoginAtivityBinding
import com.example.projetofirebase.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginAtivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginAtivityBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var email: String
    private lateinit var senha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        eventosClick()
    }

    override fun onStart() {
        super.onStart()

        verificarUsuarioLogado()
    }

    private fun verificarUsuarioLogado() {
        val user = viewModel.verificarUsuarioLogado()
        if (user) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun eventosClick() {
        binding.textCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }

        binding.buttonLogar.setOnClickListener {
            if (validarCampos()) {
                email = binding.textEmail.text.toString()
                senha = binding.textSenha.text.toString()

                val authResult = viewModel.logarUsuario(this, email, senha)
                authResult
                    .addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            }
        }
    }

    private fun validarCampos(): Boolean {
        email = binding.textEmail.text.toString()
        senha = binding.textSenha.text.toString()

        return if (email.isNotEmpty()) {
            binding.textInputLayoutEmail.error = null
            if (senha.isNotEmpty()) {
                binding.textInputLayoutSenha.error = null
                true
            } else {
                binding.textInputLayoutSenha.error = "Preencha a senha"
                false
            }
        } else {
            binding.textInputLayoutEmail.error = "Preencha o e-mail"
            false
        }
    }
}