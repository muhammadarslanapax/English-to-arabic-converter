package com.language.arabictoenglish.VoiceInput

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
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
import com.language.arabictoenglish.R
import kotlinx.android.synthetic.main.activity_detect_gallery_text.*
import kotlinx.android.synthetic.main.activity_text_translation.*
import kotlinx.android.synthetic.main.activity_voice_translation.*
import java.util.*

class Voice_Translation : AppCompatActivity() {
    lateinit var T_t_S: TextToSpeech
    private val REQUEST_CODE = 1
    lateinit var options: FirebaseTranslatorOptions
    lateinit var englishTranslator: FirebaseTranslator
    lateinit var text :String
    var s_lang : Int? = null
    var t_lang : Int? = null
    var s_position :Int ?= null
    var t_position :Int ?= null
    lateinit var s_voice_lang: String
    lateinit var t_voice_lang: String
    var state =true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_translation)
       // supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ee2e44")))

        s_lang = AR
        t_lang = EN

        s_voice_lang = "ar-AR"
        t_voice_lang = "en-EN"

        val s_Array = resources.getStringArray(R.array.S_languages)
        val t_Araay = resources.getStringArray(R.array.T_languages)


        T_t_S = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                T_t_S.language = Locale.ENGLISH

            } }



        if (v_source_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, s_Array)
            v_source_spin.setAdapter(Adapter)
            v_source_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { s_lang = AR
                            s_position =0 }
                        1 -> { s_lang = EN
                            s_position =1}




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



        if (v_target_spin != null) {
            val Adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, t_Araay)
            v_target_spin.setAdapter(Adapter)
            v_target_spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    when (position) {
                        0 -> { t_lang = EN
                            t_position =0}
                        1 -> { t_lang = AR
                            t_position =1}


                    }
                    options = Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                    englishTranslator = getInstance().getTranslator(options)
                    downloadModal(input_box.text.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                } } }


        // On Source Mic click
        v_source_mic.setOnClickListener {


            when(s_position){
               // 0->{ s_voice_lang ="ur-UR"}
//                1->{ s_voice_lang ="af-AF"}
                  0->{s_voice_lang  ="ar-AR"}
//                3->{s_voice_lang  ="bg-BG"}
//                4->{s_voice_lang  ="bn-BN"}
//                5->{s_voice_lang  ="ca-CA"}
//                6->{s_voice_lang  ="cs-CS"}
//                7->{s_voice_lang  ="da-DA"}
//                8->{s_voice_lang  ="de-DE"}
//                9->{s_voice_lang  ="el-EL"}
//                10->{s_voice_lang ="en-EN"}
//                11->{s_voice_lang ="es-ES"}
//                12->{s_voice_lang ="fa-FA"}
//                13->{s_voice_lang ="fi-FI"}
//                14->{s_voice_lang ="fr-FR"}
//                15->{s_voice_lang ="gl-GL"}
//                16->{s_voice_lang ="gu-GU"}
//                17->{s_voice_lang ="he-HE"}
                1->{s_voice_lang ="en-EN"}
//                19->{s_voice_lang ="hr-HR"}
//                20->{s_voice_lang ="hu-HU"}
//                21->{s_voice_lang ="id-ID"}
//                22->{s_voice_lang ="is-IS"}
//                23->{s_voice_lang ="it-IT"}
//                24->{s_voice_lang ="ja-JA"}
//                25->{s_voice_lang ="ka-KA"}
//                26->{s_voice_lang ="kn-KN"}
//                27->{s_voice_lang ="ko-KO"}
//                28->{s_voice_lang ="lt-LT"}
//                29->{s_voice_lang ="lv-LV"}
//                30->{s_voice_lang ="mr-MR"}
//                31->{s_voice_lang ="ms-MS"}
//                32->{s_voice_lang ="nl-NL"}
//                33->{s_voice_lang ="no-NO"}
//                34->{s_voice_lang ="pl-PL"}
//                35->{s_voice_lang ="pt-PT"}
//                36->{s_voice_lang ="ro-RO"}
//                37->{s_voice_lang ="ru-RU"}
//                38->{s_voice_lang ="sk-SK"}
//                39->{s_voice_lang ="sl-SL"}
//                40->{s_voice_lang ="sv-SV"}
//                41->{s_voice_lang ="sw-SW"}
//                42->{s_voice_lang ="ta-TA"}
//                43->{s_voice_lang ="te-TE"}
//                44->{s_voice_lang ="th-TH"}
//                45->{s_voice_lang ="tr-TR"}
//                46->{s_voice_lang ="uk-UK"}
//                47->{s_voice_lang ="ur-UR"}
//                48->{s_voice_lang ="vi-VI"}
//                49->{s_voice_lang ="zh-ZH"}
            }
            state = true


            val intent1 = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, s_voice_lang)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, s_voice_lang)
            intent1.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, s_voice_lang)
            intent1.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, s_voice_lang)
            intent1.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, s_voice_lang)
            intent1.putExtra(RecognizerIntent.EXTRA_RESULTS, s_voice_lang)

            intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak hare")
            try {
                @Suppress("DEPRECATION")
                startActivityForResult(intent1, REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            }

        }


        //    On Target mic click
        v_target_mic.setOnClickListener {


            when(t_position){
                0-> {t_voice_lang ="en-EN"}
//                1-> {t_voice_lang ="af-AF"}
                  1-> {t_voice_lang ="ar-AR"}
//                3-> {t_voice_lang ="bg-BG"}
//                4-> {t_voice_lang ="bn-BN"}
//                5-> {t_voice_lang ="ca-CA"}
//                6-> {t_voice_lang ="cs-CS"}
//                7-> {t_voice_lang ="da-DA"}
//                8-> {t_voice_lang ="de-DE"}
//                9-> {t_voice_lang ="el-EL"}
//                10->{t_voice_lang ="en-EN"}
//                11->{t_voice_lang ="es-ES"}
//                12->{t_voice_lang ="fa-FA"}
//                13->{t_voice_lang ="fi-FI"}
//                14->{t_voice_lang ="fr-FR"}
//                15->{t_voice_lang ="gl-GL"}
//                16->{t_voice_lang ="gu-GU"}
//                17->{t_voice_lang ="he-HE"}
//                18->{t_voice_lang ="hi-HI"}
//                19->{t_voice_lang ="hr-HR"}
//                20->{t_voice_lang ="hu-HU"}
//                21->{t_voice_lang ="id-ID"}
//                22->{t_voice_lang ="is-IS"}
//                23->{t_voice_lang ="it-IT"}
//                24->{t_voice_lang ="ja-JA"}
//                25->{t_voice_lang ="ka-KA"}
//                26->{t_voice_lang ="kn-KN"}
//                27->{t_voice_lang ="ko-KO"}
//                28->{t_voice_lang ="lt-LT"}
//                29->{t_voice_lang ="lv-LV"}
//                30->{t_voice_lang ="mr-MR"}
//                31->{t_voice_lang ="ms-MS"}
//                32->{t_voice_lang ="nl-NL"}
//                33->{t_voice_lang ="no-NO"}
//                34->{t_voice_lang ="pl-PL"}
//                35->{t_voice_lang ="pt-PT"}
//                36->{t_voice_lang ="ro-RO"}
//                37->{t_voice_lang ="ru-RU"}
//                38->{t_voice_lang ="sk-SK"}
//                39->{t_voice_lang ="sl-SL"}
//                40->{t_voice_lang ="sv-SV"}
//                41->{t_voice_lang ="sw-SW"}
//                42->{t_voice_lang ="ta-TA"}
//                43->{t_voice_lang ="te-TE"}
//                44->{t_voice_lang ="th-TH"}
//                45->{t_voice_lang ="tr-TR"}
//                46->{t_voice_lang ="uk-UK"}
           //     1->{t_voice_lang ="ur-UR"}
//                48->{t_voice_lang ="vi-VI"}
//                49->{t_voice_lang ="zh-ZH"}
            }

            state = false




            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, t_voice_lang)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, t_voice_lang)
            intent.putExtra(RecognizerIntent.EXTRA_RESULTS, t_voice_lang)

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak hare")
            try {
                @Suppress("DEPRECATION")
                startActivityForResult(intent, REQUEST_CODE)
            } catch (e: Exception) {
                Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
            }
}



        input_box.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                options = if (state){
                    Builder()
                        .setSourceLanguage(s_lang!!)
                        .setTargetLanguage(t_lang!!)
                        .build()
                }else{
                    Builder()
                        .setSourceLanguage(t_lang!!)
                        .setTargetLanguage(s_lang!!)
                        .build()
                }

                state =true
                englishTranslator = getInstance().getTranslator(options)

                val rec_t = "" + input_box.text.toString()
                downloadModal(rec_t)
            }
        })


        vs_speaker_btn.setOnClickListener {

                if (input_box.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(input_box.text.toString())

                }
        }

        vt_speaker_btn.setOnClickListener {


                if (output_box.text.toString().isEmpty()){
                    Toast.makeText(this,"text no found",Toast.LENGTH_SHORT).show()

                }else{
                    speak(output_box.text.toString())

                }
        }


        sv_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", input_box.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }

        tv_copy_btn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val data = ClipData.newPlainText("Text", output_box.text.toString())
            clipboard.setPrimaryClip(data)
            Toast.makeText(this, "Coped", Toast.LENGTH_SHORT).show()
        }



    }


    // Set text in input textView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            @Suppress("DEPRECATION")
            if (resultCode == RESULT_OK && data != null) {

                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                 text = Objects.requireNonNull(result)[0]
                input_box.setText(text)

            }
        }
    }

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
            output_box.setText(s)
        }.addOnFailureListener {


        } }

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