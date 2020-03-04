package com.example.weathermap

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.example.weathermap.ui.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //    val fab: FloatingActionButton = findViewById(R.id.fab)
    private lateinit var popupWindow: PopupWindow
    private lateinit var viewPopUp: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationView()
        setSelectFragment(MapFragment())
        fab.setOnClickListener(View.OnClickListener {
            popUpBuilder(R.layout.fragment_weather)
            val close = viewPopUp.findViewById<ImageView>(R.id.ic_close)
            close.setOnClickListener(View.OnClickListener {
                popupWindow.dismiss()
            })
        })
    }

    private fun popUpBuilder(fragment: Int) {
        val inflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        viewPopUp = inflater.inflate(fragment, null)
        popupWindow = PopupWindow(
            viewPopUp,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.animationStyle = R.style.popUp_animation
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.showAtLocation(viewPopUp, Gravity.CENTER, 0, 0)
    }

    private fun setupNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.n_map -> {
                    setSelectFragment(MapFragment())
                    true
                }
                R.id.n_city_search -> {
                    setSelectFragment()
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
        fr?.let { supportFragmentManager.beginTransaction().add(R.id.main_fragment, it).commit() }
    }

    override fun onBackPressed() {
        if (popupWindow.isShowing) popupWindow.dismiss()
        else super.onBackPressed()
    }
}
