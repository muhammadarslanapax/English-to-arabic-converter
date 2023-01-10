package com.language.arabictoenglish

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage.*
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions.*
import kotlinx.android.synthetic.main.activity_text_translation.*
import java.util.*


class Text_Translation : AppCompatActivity() {
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator
    lateinit var T_t_S: TextToSpeech
    lateinit var text :String
    var s_lang : Int? = null
    var t_lang : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_translation)
        //supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ee2e44")))

        val s_Array = resources.getStringArray(R.array.S_languages)
        val t_Araay = resources.getStringArray(R.array.T_languages)
        s_lang = AR
        t_lang = EN

        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }



        if (t_source_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, s_Array)
            t_source_spin.setAdapter(Adapter)
            t_source_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { s_lang =  AR }
                        1->{s_lang =  EN}



                    }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }




        if (t_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, t_Araay)

            t_target_spin.setAdapter(Adapter)
            t_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> {  t_lang = EN }
                        1 -> {  t_lang = AR}



                    }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                    downloadModal(input_et.text.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }



        options = Builder()
            .setSourceLanguage(s_lang!!)
            .setTargetLanguage(t_lang!!)
            .build()
        englishTranslator = getInstance().getTranslator(options)


        // Text change Listener
        input_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val rec_t: String = "" + input_et.text.toString()
                downloadModal(rec_t)
            }
        })


        // English Text speaker
        st_speaker_btn.setOnClickListener {
            if (s_lang == EN){
                if (input_et.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()
                }else{
                    speak(input_et.text.toString())
                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show() }
        }


        tt_speaker_btn.setOnClickListener {
            if (t_lang == EN){
                if (output_et.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()
                }else{
                    speak(output_et.text.toString())
                }
            } else{
                Toast.makeText(this,"language not supported",Toast.LENGTH_SHORT).show() }
        }


        // Copy text to Clipboard
        st_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", input_et.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }

        tt_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", output_et.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
        }



    }
    // Download language Pack
    private fun downloadModal(input: String) {
        val conditions = FirebaseModelDownloadConditions.Builder().requireWifi().build()

        englishTranslator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translateLanguage(input)

            }.addOnFailureListener {
                Toast.makeText(applicationContext, "language is Downloading please wait", Toast.LENGTH_SHORT).show()
            }
    }

    private fun translateLanguage(input: String) {

        englishTranslator.translate(input).addOnSuccessListener { s ->
            output_et.setText(s)
        }.addOnFailureListener {

            if (checkInternet(this)) {
                Toast.makeText(this, "language Downloading", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Please connect to wifi", Toast.LENGTH_SHORT).show()
            }


        } }
    private fun checkInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    // Text speaker method
    fun speak(toSpeak: String) {
        @Suppress("DEPRECATION")
        T_t_S.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
    }

    override fun onPause() {
        if (T_t_S.isSpeaking){
            T_t_S.stop()
        }
        super.onPause()
    }


}