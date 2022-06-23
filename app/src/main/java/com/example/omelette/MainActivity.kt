package com.example.omelette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val OMELLETE_STATUS ="OMELLETE_STATUS"
    private val OMELLETE_SIZE = "OMELLETE_SIZE"
    private val QUEBRAR_CONTAGEM = "QUEBRAR_CONTAGEM"

    private val SELECIONE = "selecione"
    private val QUEBRE = "quebre"
    private val FRITE =  "frite"
    private val COMER = "comer"
    private val REFAZER = "rafazer"
    private var omelleteStatus = "selecione"
    private var omelleteSize= -1
    private var quebrarContagem = -1

    private var quebrando = Quebrando()
    private var omellteImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            omelleteStatus = savedInstanceState.getString(OMELLETE_STATUS,"select")
            omelleteSize = savedInstanceState.getInt(OMELLETE_SIZE,-1)
            quebrarContagem = savedInstanceState.getInt(QUEBRAR_CONTAGEM,-1)
        }
        omellteImage = findViewById(R.id.image_Omellte_state)
        setViewElements()

        omellteImage!!.setOnClickListener(){
            clickOmelleteImage()
        }

        omellteImage!!.setOnLongClickListener(){
            showSnackBar()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(OMELLETE_STATUS, omelleteStatus)
        outState.putInt(OMELLETE_SIZE, omelleteSize)
        outState.putInt(QUEBRAR_CONTAGEM, quebrarContagem)
        super.onSaveInstanceState(outState)
    }

    private fun clickOmelleteImage(){
        when(omelleteStatus){
            SELECIONE ->{
                omelleteSize = quebrando.pick()
                quebrarContagem = -1
                omelleteStatus = QUEBRE
            }
            QUEBRE->{
                quebrarContagem++
                omelleteSize--
                if(omelleteSize == 0){
                    omelleteStatus = FRITE
                    omelleteSize =-1
                }
            }
            FRITE ->{
                omelleteStatus = COMER
            }
            COMER ->{
                omelleteStatus = REFAZER
            }
            else->{
                omelleteStatus = SELECIONE
            }
        }
        setViewElements()
    }

     private fun setViewElements(){
        val textAction: TextView = findViewById(R.id.textView)
         when(omelleteStatus){
             SELECIONE->{
                 omellteImage?.setImageResource((R.drawable.galinheiro))
                 textAction.text = getString(R.string.Galinheiro_msg)
             }
             QUEBRE->{
                 omellteImage?.setImageResource(R.drawable.ovo)
                 textAction.text = getString(R.string.ovo_msg)
             }
             FRITE->{
                 omellteImage?.setImageResource(R.drawable.frigideira)
                 textAction.text = getString(R.string.frigideira_msg)
             }
             COMER->{
                 omellteImage?.setImageResource(R.drawable.omellete)
                 textAction.text = getString(R.string.omllete_msg)
             }
             REFAZER->{
                 omellteImage?.setImageResource(R.drawable.prato)
                 textAction.text = getString(R.string.prato_msg)
             }
         }
    }

    private fun showSnackBar():Boolean{
    if(omelleteStatus != QUEBRE){
        return false
    }
        val contagemText =getString(R.string.squeeze_count,quebrarContagem)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),contagemText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true

    }
    class Quebrando{
        fun pick():Int{
            return (2..9).random()
        }
    }
}