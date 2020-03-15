package com.example.weathermap.ui.map

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weathermap.R
import com.example.weathermap.core.CoreFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class MapFragment : CoreFragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener,GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{
    private lateinit var mMap: GoogleMap
    private val viewModel : MapViewModel by viewModel()
    private var markerList: MutableList<Marker> = mutableListOf()
    private lateinit var currentCityName: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
    }
    override fun initViews(view: View) {
    }

    private fun setupMap() {
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager?.beginTransaction()?.add(R.id.map_container, mapFragment)?.commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraMoveListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnMapClickListener {
            viewModel.getWeatherData("metric",it.latitude.toString(),it.longitude.toString())
            addMarkerToMap(it)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,6.0f))
        }
    }

    private fun addMarkerToMap(it: LatLng) {
        mMap.clear()
        val markerOptions = MarkerOptions()
            .position(it)
            .title("check weather")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
        val marker = mMap.addMarker(markerOptions)
        markerList.add(marker)
        marker.showInfoWindow()
        mMap.setOnInfoWindowClickListener {
            openWeatherDialog()
        }
        mMap.setOnMarkerClickListener(this)
    }

    private fun openWeatherDialog(){
        val view: View = layoutInflater.inflate(R.layout.fragment_weather,null)
        val dialog = BottomSheetDialog(this.requireContext())
        val temp: TextView = view.findViewById(R.id.temp_tv)
        val tempValue: TextView = view.findViewById(R.id.weather_tv)
        val weatherIc: ImageView = view.findViewById(R.id.weather_ic)
        val humidity: TextView = view.findViewById(R.id.humidity)
        val cityName: TextView = view.findViewById(R.id.city_title_tv)

        viewModel.liveData.observe(this, Observer {
            currentCityName = it.name.toString()
            temp.text = it.main?.temp?.roundToInt().toString().plus(" °")
            cityName.text = it.name
            tempValue.text = it.weather[0].description
            humidity.text = it.main?.humidity.toString().plus(" % humidity")
            Glide.with(requireContext()).load("http://openweathermap.org/img/wn/"
                    + it.weather[0].icon + "@2x.png").into(weatherIc)
        })

        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.setContentView(view,
            ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT))
        dialog.show()
    }

    override fun onCameraMove() {
    }

    override fun onCameraIdle() {
    }

    override fun onMapClick(p0: LatLng?) {
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
       return false
    }


}


