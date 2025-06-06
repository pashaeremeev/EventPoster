package com.example.eventposter.app.ui.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.eventposter.R
import com.example.eventposter.app.DialogManager
import com.example.eventposter.app.ui.viewmodel.ProfileViewModel
import com.example.eventposter.databinding.FragmentProfileBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var vm: ProfileViewModel
    private var permissionRequested = false
    private var permissionDenied = false

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showPermissionDeniedDialog()
        }
    }

    private val binding get() = _binding!!

    companion object {
        private var fragment: ProfileFragment? = null
        fun getInstance(): ProfileFragment {
            if (fragment == null) {
                fragment = ProfileFragment()
            }
            return fragment!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vm =
            ViewModelProvider(this)[ProfileViewModel::class.java]
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toAuthButton.setOnClickListener {
            navigateTo(R.id.action_navigation_profile_to_navigation_auth)
        }

        binding.profileLocationText.setOnClickListener {
            checkLocation()
        }

        binding.toEventsView.setOnClickListener {
            navigateTo(R.id.action_navigation_profile_to_navigation_user_events)
        }

        binding.toManageEventsView.setOnClickListener {
            navigateTo(R.id.action_navigation_profile_to_navigation_stat)
        }

        vm.location.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.profileLocationText.text = it.latitude.toString() + " " + it.longitude.toString()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (!permissionDenied) {
            checkLocation()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateTo(resourceId: Int) {
        val navController = requireActivity()
            .findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(resourceId)
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            DialogManager.toLocationSettingsDialog(requireContext(),
                object: DialogManager.ClickListener {
                    override fun onClick() {
                        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                })
        }
    }

    private fun isLocationEnabled(): Boolean {
        val lm = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestLocationPermission() {
        if (permissionRequested) {
            permissionDenied = true
            showPermissionDeniedDialog()
        } else {
            permissionRequested = true
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun showPermissionDeniedDialog() {
        DialogManager.toPermissionGpsMessage(requireContext(), object: DialogManager.ClickListener {
            override fun onClick() {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        })
    }

    private fun getLocation() {
        val cToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cToken.token)
            .addOnCompleteListener {
                vm.location.value = it.result
            }
    }

}
