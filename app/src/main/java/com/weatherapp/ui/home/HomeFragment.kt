package com.weatherapp.ui.home

import android.Manifest
import androidx.appcompat.app.AppCompatDelegate
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.weatherapp.R
import com.weatherapp.databinding.FragmentHomeBinding
import com.weatherapp.utils.Constants
import com.weatherapp.utils.Resource
import com.weatherapp.utils.gone
import com.weatherapp.utils.loadWeatherIcon
import com.weatherapp.utils.toFormattedDate
import com.weatherapp.utils.toTempString
import com.weatherapp.utils.toWindDirection
import com.weatherapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import androidx.recyclerview.widget.LinearLayoutManager

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val forecastAdapter = ForecastAdapter()

    private lateinit var fusedLocation: FusedLocationProviderClient

    private val locationPermLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { perms ->

            if (
                perms[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {

                fetchLocationAndLoad()

            } else {

                viewModel.loadWeatherByCity(
                    Constants.DEFAULT_CITY
                )
            }
        }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentHomeBinding.inflate(
                inflater,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        val editCity = view.findViewById<EditText>(R.id.editCity)

        editCity.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE) {

                val city = editCity.text.toString().trim()

                if (city.isNotEmpty()) {
                    viewModel.loadWeatherByCity(city)
                }

                true
            } else {
                false
            }
        }

        val themeSwitch =
            view.findViewById<SwitchMaterial>(R.id.themeSwitch)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        fusedLocation =
            LocationServices.getFusedLocationProviderClient(
                requireActivity()
            )

        setupRecyclerView()
        setupObservers()
        setupListeners()

        viewModel.city.value?.let {
            viewModel.loadWeatherByCity(it)
        } ?: checkLocationPermAndLoad()
    }

    private fun setupRecyclerView() {
        binding.rvForecast.adapter = forecastAdapter

        binding.rvForecast.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {

        viewModel.weather.observe(viewLifecycleOwner) { resource ->

            when (resource) {

                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    bindWeatherData(resource.data)
                }

                is Resource.Error -> {
                    showLoading(false)

                    Snackbar.make(
                        binding.root,
                        resource.message,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.forecast.observe(viewLifecycleOwner) { resource ->

            if (resource is Resource.Success) {

                forecastAdapter.submitList(
                    resource.data.list.filterIndexed { index, _ -> index % 8 == 0 }
                )
            }
        }
    }

    private fun setupListeners() {

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.btnLocation.setOnClickListener {
            checkLocationPermAndLoad()
        }
    }

    private fun bindWeatherData(
        data: com.weatherapp.data.model.WeatherResponse
    ) = with(binding) {

        tvCityName.text =
            "${data.name}, ${data.sys.country}"

        tvTemperature.text =
            data.main.temp.toTempString()

        tvDescription.text =
            data.weather.firstOrNull()
                ?.description
                ?.replaceFirstChar { it.uppercase() }
                ?: ""

        tvFeelsLike.text =
            "Cảm giác như ${data.main.feelsLike.toTempString()}"

        tvHumidity.text =
            "${data.main.humidity}%"

        tvWind.text =
            "${data.wind.speed} m/s ${data.wind.deg.toWindDirection()}"

        tvPressure.text =
            "${data.main.pressure} hPa"

        tvVisibility.text =
            "${data.visibility / 1000} km"

        tvSunrise.text =
            data.sys.sunrise.toFormattedDate("HH:mm")

        tvSunset.text =
            data.sys.sunset.toFormattedDate("HH:mm")

        tvUpdated.text =
            "Cập nhật: ${data.dt.toFormattedDate()}"

        data.weather.firstOrNull()
            ?.icon
            ?.let {
                ivWeatherIcon.loadWeatherIcon(it)
            }
    }

    private fun showLoading(
        loading: Boolean
    ) {

        binding.swipeRefresh.isRefreshing = loading

        if (loading) {
            binding.progressBar.visible()
        } else {
            binding.progressBar.gone()
        }
    }

    private fun checkLocationPermAndLoad() {

        val fine =
            Manifest.permission.ACCESS_FINE_LOCATION

        val coarse =
            Manifest.permission.ACCESS_COARSE_LOCATION

        if (
            ContextCompat.checkSelfPermission(
                requireContext(),
                fine
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            fetchLocationAndLoad()

        } else {

            locationPermLauncher.launch(
                arrayOf(fine, coarse)
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocationAndLoad() {

        fusedLocation.lastLocation
            .addOnSuccessListener { loc ->

                if (loc != null) {

                    viewModel.loadWeatherByLocation(
                        loc.latitude,
                        loc.longitude
                    )

                } else {

                    viewModel.loadWeatherByCity(
                        Constants.DEFAULT_CITY
                    )
                }
            }
            .addOnFailureListener {

                viewModel.loadWeatherByCity(
                    Constants.DEFAULT_CITY
                )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
