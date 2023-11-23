package com.cafezin.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cafezin.calculadoraimc.databinding.ActivityMainBinding
import com.cafezin.calculadoraimc.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoVoltar.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        val resultado = intent.getStringExtra("imc").toString().toDouble()
        binding.resultadoIMC.text = "%.1f".format(resultado)

        if(resultado < 16.0){
            binding.imageMedidor.setImageResource(R.drawable.magreza_grave)
        }else if(resultado >= 16.0 && resultado < 17.0){
            binding.imageMedidor.setImageResource(R.drawable.magreza_moderada)
        }else if(resultado >= 17.0 && resultado < 18.5){
            binding.imageMedidor.setImageResource(R.drawable.abaixo_do_peso)
        }else if(resultado >= 18.5 && resultado < 25.0){
            binding.imageMedidor.setImageResource(R.drawable.saudavel)
        }else if(resultado >= 25.0 && resultado < 30.0){
            binding.imageMedidor.setImageResource(R.drawable.sobrepeso)
        }else if(resultado >= 30.0 && resultado < 35.0){
            binding.imageMedidor.setImageResource(R.drawable.obesidade_grau_i)
        }else if(resultado >= 35.0 && resultado < 40.0){
            binding.imageMedidor.setImageResource(R.drawable.obesidade_grau_ii)
        }else{
            binding.imageMedidor.setImageResource(R.drawable.obesidade_grau_iii)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_listar){
            val intent = Intent(this, ListaCalculoActivity::class.java)
            intent.putExtra("tipo", "imc")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}