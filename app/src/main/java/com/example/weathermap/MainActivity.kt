package com.example.weathermap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.example.weathermap.ui.map.MapFragment
import com.example.weathermap.ui.city.CityFragment
import com.example.weathermap.ui.map.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    //    val fab: FloatingActionButton = findViewById(R.id.fab)
    private lateinit var popupWindow: PopupWindow
    private lateinit var viewPopUp: View
    private val viewModel : MapViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationView()
        setSelectFragment(MapFragment())
//        popUpBuilder(R.layout.fragment_weather)
        fab.setOnClickListener(View.OnClickListener {
//            openDialog()
//            popupWindow.showAtLocation(viewPopUp, Gravity.CENTER, 0, 0)
        })


//        val close = viewPopUp.findViewById<ImageView>(R.id.ic_close)
//        close.setOnClickListener(View.OnClickListener {
//            popupWindow.dismiss()
//        })
    }

//    private fun popUpBuilder(layout: Int) {
//        val inflater: LayoutInflater =
//            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        viewPopUp = inflater.inflate(layout, null)
//        popupWindow = PopupWindow(
//            viewPopUp,
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        popupWindow.animationStyle = R.style.popUp_animation
//        popupWindow.isFocusable = true
//        popupWindow.isOutsideTouchable = true
//    }



    private fun setupNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.n_map -> {
                    setSelectFragment(MapFragment())
                    true
                }
                R.id.n_city_search -> {
                    setSelectFragment(CityFragment())
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
        fr?.let { supportFragmentManager.beginTransaction().replace(R.id.main_fragment, it).commit() }
    }

    override fun onBackPressed() {
        if (popupWindow.isShowing) popupWindow.dismiss()
       super.onBackPressed()
    }
}
