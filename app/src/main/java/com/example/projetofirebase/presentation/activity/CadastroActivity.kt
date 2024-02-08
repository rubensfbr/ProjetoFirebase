package com.example.projetofirebase.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.projetofirebase.databinding.ActivityCadastroBinding
import com.example.projetofirebase.presentation.viewmodels.CadastroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CadastroActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<CadastroViewModel>()

    private lateinit var email: String
    private lateinit var senha: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        eventosClick()
    }

    private fun eventosClick() {
        binding.buttonCadstrar.setOnClickListener {
            if (validarCampos()) {
                viewModel.cadastrarUsuario(this, email, senha)
            }
        }
    }

    private fun validarCampos(): Boolean {
        email = binding.textCadastroEmail.text.toString()
        senha = binding.textCadastroSenha.text.toString()

        return if (email.isNotEmpty()) {
            binding.textInputCadastroEmail.error = null
            if (senha.isNotEmpty()) {
                binding.textInputCadastroSenha.error = null
                true
            } else {
                binding.textInputCadastroSenha.error = "Preencha a senha"
                false
            }
        } else {
            binding.textInputCadastroEmail.error = "Preencha o e-mail"
            false
        }
    }
}