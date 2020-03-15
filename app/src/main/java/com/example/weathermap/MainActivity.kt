package com.example.weathermap

import android.os.Bundle
import android.view.View
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.weathermap.ui.city.CityFragment
import com.example.weathermap.ui.map.MapFragment
import com.example.weathermap.ui.map.MapViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    //    val fab: FloatingActionButton = findViewById(R.id.fab)
    private var TAG_FRAGMENT: String? = "TAG_FRAGMENT"
    private lateinit var popupWindow: PopupWindow
    private val viewModel : MapViewModel by viewModel()
    private lateinit var mapFragment: MapFragment
    private lateinit var cityFragment: CityFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationView()
        setSelectFragment(MapFragment())
        mapFragment = MapFragment()
        cityFragment = CityFragment()
        fab.setOnClickListener(View.OnClickListener {

        })

    }

    private fun setupNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.n_map -> {
                    setSelectFragment(mapFragment)
                    true
                }
                R.id.n_city_search -> {
                    setSelectFragment(cityFragment)
                    true
                }
                R.id.n_profile -> {
                    setSelectFragment()
                    true
                }
                else -> true
            }
        }
    }

    private fun setSelectFragment(fr: Fragment? = null) {
        fr?.let { supportFragmentManager.beginTransaction().replace(R.id.main_fragment, it).addToBackStack(fr.tag).commit() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}
