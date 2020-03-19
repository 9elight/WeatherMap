package com.example.weathermap.ui.map

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weathermap.R
import com.example.weathermap.core.CoreFragment
import com.example.weathermap.db.WeatherDb
import com.example.weathermap.model.weatherModel.WeatherMainModel
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext
import kotlin.math.roundToInt


class MapFragment : CoreFragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener,GoogleMap.OnMapClickListener,
    GoogleMap.OnMarkerClickListener,CoroutineScope{
    private lateinit var mMap: GoogleMap
    private val viewModel : MapViewModel by viewModel()
    private var markerList: MutableList<Marker> = mutableListOf()
    private lateinit var currentCityName: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMap()
        viewModel.getWeather()
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
            launch {
                viewModel.getWeatherData("metric",it.latitude.toString(),it.longitude.toString())
            }
            addMarkerToMap(it)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,6.0f))
        }

        loadMarkers()
    }

    private fun loadMarkers(){
        viewModel.weatherLiveData.observe(this, Observer {
            mMap.clear()
            for (item in it){
                addMarkerToMap(LatLng(item.lat.toDouble(),item.lng.toDouble()))
            }
        })
    }

    private fun deleteMarker(){
        mMap.setOnInfoWindowLongClickListener {
            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle("Delete")
                .setPositiveButton("Yes"){dialog, which ->
                    Toast.makeText(activity,it.id,Toast.LENGTH_LONG).show()
                    viewModel.deleteWeather(it.id)
                }
                .setNegativeButton("No"){dialog, which ->
                    dialog.cancel()
                }
                .show()
        }
    }
    private fun addMarkerToMap(it: LatLng) {
        val markerOptions = MarkerOptions()
            .position(it)
            .title("check weather")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
        val marker = mMap.addMarker(markerOptions)
        markerList.add(marker)
        marker.showInfoWindow()
        mMap.setOnInfoWindowClickListener {
            openWeatherDialog(it.id)
        }
        deleteMarker()
        mMap.setOnMarkerClickListener(this)
    }

    private fun openWeatherDialog(id: String) {
        val view: View = layoutInflater.inflate(R.layout.fragment_weather,null)
        val dialog = BottomSheetDialog(this.requireContext())
        val temp: TextView = view.findViewById(R.id.temp_tv)
        val tempValue: TextView = view.findViewById(R.id.weather_tv)
        val weatherIc: ImageView = view.findViewById(R.id.weather_ic)
        val humidity: TextView = view.findViewById(R.id.humidity)
        val cityName: TextView = view.findViewById(R.id.city_title_tv)
        val saveBtn: Button = view.findViewById(R.id.btn_weather_fragment)
        lateinit var weatherModel:WeatherMainModel
        viewModel.liveData.observe(this, Observer {
            weatherModel = it
            currentCityName = it.name.toString()
            temp.text = it.main?.temp?.roundToInt().toString().plus(" °")
            cityName.text = it.name
            tempValue.text = it.weather[0].description
            humidity.text = it.main?.humidity.toString().plus(" % humidity")
            Glide.with(requireContext()).load("http://openweathermap.org/img/wn/"
                    + it.weather[0].icon + "@2x.png").into(weatherIc)

        })
        saveBtn.setOnClickListener(View.OnClickListener {
            viewModel.saveWeatherInDb(WeatherDb(id,weatherModel.coord.lat.toString(),
                weatherModel.coord.lon.toString(),
                temp.text.toString(),
                humidity.text.toString(),
                tempValue.text.toString(),
                cityName.text.toString(),
                cityName.text.toString()))
            Toast.makeText(activity,viewModel.weatherLiveData.value.toString(),Toast.LENGTH_LONG).show()
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


}


