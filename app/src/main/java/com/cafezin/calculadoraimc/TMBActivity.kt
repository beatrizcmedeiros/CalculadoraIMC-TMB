package com.cafezin.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cafezin.calculadoraimc.databinding.ActivityTmbactivityBinding
import com.cafezin.calculadoraimc.databinding.DialogInfoBinding
import com.cafezin.calculadoraimc.databinding.DialogTmbBinding
import com.cafezin.calculadoraimc.model.Calculo
import java.text.DecimalFormat

class TMBActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTmbactivityBinding

    private lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTmbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoVoltar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        var idade = 0
        var altura = 0.0
        var peso = 0.0

        binding.seekBarIdade.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                idade = progress.toInt()
                binding.textViewIdade.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    idade = seekBar.progress
                }
            }
        })

        binding.seekBarAltura.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                altura = (progress/100.00)
                binding.textViewAltura.text = altura.toString().replace(".", ",")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    altura = seekBar.progress.toDouble()
                }
            }
        })

        binding.seekBarPeso.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                peso = progress / 100.00
                binding.textViewPeso.text = peso.toString().replace(".", ",")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    peso = seekBar.progress.toDouble()
                }
            }
        })

        binding.botaoCalcular.setOnClickListener {
            calcularTMB(idade, altura, peso)
        }

        binding.botaoInfo.setOnClickListener {
            dialogInfo()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_listar){
            val intent = Intent(this, ListaCalculoActivity::class.java)
            intent.putExtra("tipo", "tmb")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun calcularTMB(idade: Int, altura: Double, peso: Double){
        var tmb = 0.0
        when{
            binding.chipMasculino.isCheckable -> {
                tmb = 66.47 + (13.75 * peso) + (5.0 * altura) - (6.76 * idade)
//                binding.textViewTeste.text = DecimalFormat("#.##").format(tmb)
                dialogTMB(tmb)
            }
            binding.chipFeminino.isCheckable -> {
                tmb = 655.1 + (9.56 * peso) + (1.85 * altura) - (4.68 * idade)
//                binding.textViewTeste.text = DecimalFormat("#.##").format(tmb)
                dialogTMB(tmb)
            }
        }
    }

    fun dialogTMB(tmb: Double) {
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogTmbBinding.inflate(LayoutInflater.from(this))
        builder.setView(dialogBinding.root)

        (DecimalFormat("#.##").format(tmb).replace(".",",") + " calorias").also { dialogBinding.textViewTMB.text = it }

        dialogBinding.buttonTMB.setOnClickListener {

            Thread{
                val app = application as App
                val dao = app.db.calculoDao()

                val atualizaId = intent.extras?.getInt("atualizaId")

                if(atualizaId != null){
                    dao.atualizar(Calculo(atualizaId, tipo = "tmb", resultado = tmb))
                }else{
                    dao.inserir(Calculo(tipo = "tmb", resultado = tmb))
                }

                runOnUiThread{
                    Toast.makeText(this@TMBActivity, "Medição salva com sucesso!", Toast.LENGTH_LONG).show()
                }

            }.start()
            alertDialog.dismiss()
        }

        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dialogInfo(){
        val builder = AlertDialog.Builder(this, R.style.Theme_Dialog)
        val dialogBinding = DialogInfoBinding.inflate(LayoutInflater.from(this))

        builder.setView(dialogBinding.root)

        dialogBinding.buttonInfo.setOnClickListener { alertDialog.dismiss() }

        alertDialog = builder.create()
        alertDialog.show()
    }
}