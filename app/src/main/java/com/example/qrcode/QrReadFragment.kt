package com.example.qrcode

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.qrcode.databinding.FragmentQrReadBinding
import com.google.zxing.BarcodeFormat


class QrReadFragment : Fragment() {

    private var _binding: FragmentQrReadBinding? = null
    private val binding get() = _binding!!




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


    }

    private fun setScannerProperties() {
        binding.apply {
            qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
            qrCodeScanner.setAutoFocus(true)
            qrCodeScanner.setLaserColor(R.color.white)
            qrCodeScanner.setMaskColor(R.color.black)
            if (Build.MANUFACTURER.equals(HUAWEI, ignoreCase = true))
                qrCodeScanner.setAspectTolerance(0.5f)
        }

    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION),
                    MY_CAMERA_REQUEST_CODE)
                return
            }
        }
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}