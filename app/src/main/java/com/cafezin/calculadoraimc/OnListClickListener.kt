package com.cafezin.calculadoraimc

import com.cafezin.calculadoraimc.model.Calculo

interface OnListClickListener {
    fun onClick(id: Int, tipo: String)
    fun onLongClick(position: Int, calculo: Calculo)
}