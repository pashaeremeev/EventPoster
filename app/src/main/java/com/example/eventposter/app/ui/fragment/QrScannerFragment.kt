package com.example.eventposter.app.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.eventposter.R
import com.example.eventposter.databinding.FragmentQrScannerBinding

class QrScannerFragment : Fragment() {

    private var _binding: FragmentQrScannerBinding? = null
    private val binding get() = _binding!!

    private lateinit var codeScanner: CodeScanner

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQrScannerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerPermissionListener()
        checkCameraPermission()

        codeScanner = CodeScanner(requireContext(), binding.scannerView).apply {
            scanMode = ScanMode.SINGLE
            camera = CodeScanner.CAMERA_BACK
        }
        codeScanner.camera
        codeScanner.decodeCallback = DecodeCallback {
            requireActivity().runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
                toLastFragment()
            }
        }

        binding.scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        binding.ivBackEventTickets.setOnClickListener {
            toLastFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (hasCameraPermission()) {
            codeScanner.startPreview()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(
                    context,
                    "Camera is run",
                    Toast.LENGTH_SHORT
                ).show()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(
                    context,
                    "Need camera permission",
                    Toast.LENGTH_SHORT
                ).show()
                toLastFragment()
            }
            else -> {
                registerPermissionListener()
            }
        }
    }

    private fun registerPermissionListener() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(
                    context,
                    "Camera is run",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Camera permission is denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun toLastFragment() {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack()
    }
}