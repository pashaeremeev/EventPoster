package com.example.eventposter.app.ui.fragment

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.ui.viewmodel.TicketViewModel
import com.example.eventposter.databinding.FragmentTicketBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class TicketFragment : Fragment() {

    companion object {
        fun newInstance() = TicketFragment()
    }

    private var _binding: FragmentTicketBinding? = null
    private val binding get() = _binding!!

    private val widthDpQr = 80
    private val heightDpQr = 80

    private lateinit var vm: TicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm = ViewModelProvider(this)[TicketViewModel::class.java]
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val widthPxQr = (widthDpQr * requireContext().resources.displayMetrics.density).toInt()
        val heightPxQr = (heightDpQr * requireContext().resources.displayMetrics.density).toInt()

        val qrCodeBmp = generateQrCode(
            text = "Hello, world!",
            widthCode = widthPxQr,
            heightCode = heightPxQr
        )
        qrCodeBmp?.let { binding.ivTicketQrCode.setImageBitmap(it) }

        binding.ivBackTicket.setOnClickListener {
            toLastFragment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generateQrCode(text: String, widthCode: Int, heightCode: Int): Bitmap? {
        return try {
            val matrix = MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                widthCode, heightCode
            )
            Bitmap.createBitmap(widthCode, heightCode, Bitmap.Config.RGB_565).apply {
                for (x in 0 until widthCode) {
                    for (y in 0 until heightCode) {
                        setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Не удалось создать QR-код.",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Qr", e.toString())
            null
        }
    }

    private fun toLastFragment() {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.popBackStack()
    }
}