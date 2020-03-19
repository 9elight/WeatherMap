package com.example.weathermap.ui.city

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weathermap.R
import com.example.weathermap.core.CoreFragment
import com.example.weathermap.model.countriesModel.Countries
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.fragment_city.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class CityFragment : CoreFragment(R.layout.fragment_city), CityAdapter.OnItemClickListener,CoroutineScope {
    private lateinit var search: EditText
    private val cViewModel: CityViewModel by viewModel()
    private var listCountries: ArrayList<Countries> = ArrayList()
    private lateinit var adapter: CityAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchItem()
    }
    override fun initViews(view: View) {
        adapter = CityAdapter(this)
        search = view.findViewById(R.id.search_field)
        launch (){
            cViewModel.getAllCountries()
        }
        cViewModel.allCountries.observe(this, Observer {
            listCountries = ArrayList(it)
            view.rv_cities.adapter = adapter
            adapter.updateList(it)
        })
    }

    private fun searchItem() {
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (listCountries.isNotEmpty()) cViewModel.filterTheList(s.toString(), listCountries)
            }
        })

        cViewModel.filteredList.observe(this, Observer {
            adapter.updateList(it)
        })
        cViewModel.notFoundException.observe(this, Observer {
            Toast.makeText(activity, "Not Found", Toast.LENGTH_LONG).show()
        })
    }

    override fun onItemClicked(item: Countries) {
        Toast.makeText(activity, "Clicked" + item.capital, Toast.LENGTH_LONG).show()
    }

    override fun loadImage(imageUrl: Uri, imageView: ImageView) {
        GlideToVectorYou.justLoadImage(activity,imageUrl,imageView)
    }
}


