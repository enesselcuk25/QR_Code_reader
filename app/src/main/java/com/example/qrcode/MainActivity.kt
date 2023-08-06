package com.example.qrcode

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var qrCodeReader : ((content:String) -> Unit)? = {}



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        result?.let {
            it.contents?.let {reader ->
                Toast.makeText(this, "scanned" + result.contents, Toast.LENGTH_LONG).show()

                qrCodeReader?.invoke(reader)

                val replyIntent = Intent()
                replyIntent.putExtra(QrReadFragment.EXTRA_REPLY, result.contents)
                setResult(Activity.RESULT_OK, replyIntent)


            } ?: run {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun qrReader(content: (String) -> Unit = {}){
        qrCodeReader = content
    }
}