package com.example.weathermap.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.weathermap.R
import com.example.weathermap.WeatherApp
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text
import kotlin.math.roundToInt


class MapFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener,GoogleMap.OnMapClickListener{
    private lateinit var mMap: GoogleMap
    private val viewModel : MapViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_map, container, false)
        setupMap()
        return view
    }
    private fun setupMap() {
        val mapFragment = SupportMapFragment.newInstance()
        fragmentManager?.beginTransaction()?.add(R.id.map_container, mapFragment)?.commit()
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {
        }
        mMap.setOnMapLongClickListener {
            viewModel.getWeatherData("metric",it.latitude.toString(),it.longitude.toString())
            openWeatherDialog()
        }
    }

    private fun openWeatherDialog(){
        val view: View = layoutInflater.inflate(R.layout.fragment_weather,null)
        val dialog = BottomSheetDialog(this.requireContext())
        val temp: TextView = view.findViewById(R.id.temp_tv)
        val tempValue: TextView = view.findViewById(R.id.weather_tv)
        val weatherIc: ImageView = view.findViewById(R.id.weather_ic)
        val humidity: TextView = view.findViewById(R.id.humidity)
        val city_name: TextView = view.findViewById(R.id.city_title_tv)

        viewModel.liveData.observe(this, Observer {
            temp.text = it.main?.temp?.roundToInt().toString().plus(" °")
            city_name.text = it.name
            tempValue.text = it.weather[0].description
            humidity.text = it.main?.humidity.toString().plus(" % humidity")
            Glide.with(requireContext()).load("http://openweathermap.org/img/wn/"
                    + it.weather[0].icon + "@2x.png").into(weatherIc)
        })
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




}


