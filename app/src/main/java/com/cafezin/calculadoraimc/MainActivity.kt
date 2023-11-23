package com.cafezin.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cafezin.calculadoraimc.databinding.ActivityMainBinding
import com.cafezin.calculadoraimc.databinding.DialogInfoBinding
import com.cafezin.calculadoraimc.databinding.DialogInfoImcBinding
import com.cafezin.calculadoraimc.model.Calculo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoVoltar.setOnClickListener{
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        binding.botaoCalcular.setOnClickListener{
            calcular()
        }

        binding.botaoInfo.setOnClickListener {
            dialogInfo()
        }
    }

    private fun calcular(){
        val peso = binding.campoPeso.text.toString().replace(",", ".").toDoubleOrNull()
        val altura = binding.campoAltura.text.toString().replace(",", ".").toDoubleOrNull()
        val imc: Double

        if(peso  == null || altura == null){
            Toast.makeText(this, "Por favor preencha todos os campos.", Toast.LENGTH_SHORT).show()
        }else{
            val imc = peso / (altura * altura)

            Thread{
                val app = application as App
                val dao = app.db.calculoDao()

                val atualizaId = intent.extras?.getInt("atualizaId")

                if(atualizaId != null){
                    dao.atualizar(Calculo(atualizaId, tipo = "imc", resultado = imc))
                }else{
                    dao.inserir(Calculo(tipo = "imc", resultado = imc))
                }

                runOnUiThread{
                    Toast.makeText(this, "IMC salvo com sucesso!", Toast.LENGTH_LONG).show()
                }

            }.start()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("imc", imc.toString())

            startActivity(intent)
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

    fun dialogInfo(){
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogInfoImcBinding.inflate(LayoutInflater.from(this))

        builder.setView(dialogBinding.root)

        dialogBinding.buttonInfo.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }
}