package com.example.qrcode


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.qrcode.databinding.FragmentQrReadBinding
import com.google.zxing.integration.android.IntentIntegrator


class QrReadFragment : Fragment() {

    private var _binding: FragmentQrReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrReadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setScannerProperties()

        (requireActivity() as MainActivity).qrReader{
            binding.webView.webViewClient = WebViewClient()
            binding.webView.loadUrl(it)
        }


    }

    private fun setScannerProperties() {
        binding.button.setOnClickListener {
            IntentIntegrator(requireActivity()).apply {
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setPrompt("")
                setCameraId(0)
                setOrientationLocked(false)
                initiateScan()
            }
        }
    }


    companion object {
        const val EXTRA_REPLY = "com.example.android.qrreader.REPLY"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}