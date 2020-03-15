package com.example.weathermap.ui.city

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weathermap.R
import com.example.weathermap.core.CoreFragment
import com.example.weathermap.model.countriesModel.Countries
import kotlinx.android.synthetic.main.fragment_city.*
import kotlinx.android.synthetic.main.fragment_city.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment : CoreFragment(R.layout.fragment_city), CityAdapter.OnItemClickListener {
    private lateinit var search: EditText
    private val cViewModel: CityViewModel by viewModel()
    private var listCountries: ArrayList<Countries> = ArrayList()
    private lateinit var adapter: CityAdapter

    override fun initViews(view: View) {
        adapter = CityAdapter(this)
        search = view.findViewById(R.id.search_field)
        cViewModel.getAllCountries()
        cViewModel.allCountries.observe(this, Observer {
            listCountries = ArrayList(it)
            view.rv_cities.adapter = adapter
            adapter.updateList(it)
        })
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (listCountries.isNotEmpty()) searchItem(s.toString(), listCountries)
            }
        })
    }


    override fun onItemClicked(item: Countries) {
        Toast.makeText(activity, "Clicked" + item.capital, Toast.LENGTH_LONG).show()
    }


    private fun searchItem(textToSearch: String, list: ArrayList<Countries>) {
        val changedList = list.groupBy {
            it.name.contains(textToSearch)
        }
        if (changedList[true] != null) {
            adapter.updateList(changedList.getValue(true))
        } else {
            Toast.makeText(activity, "Not Found", Toast.LENGTH_LONG).show()
        }
    }
}

//timer = object : CountDownTimer(2000, 1000) {


//}
//    }
//        Toast.makeText(activity, millisUntilFinished.toString(), Toast.LENGTH_LONG).show()
//    override fun onTick(millisUntilFinished: Long) {
//
//    }
//        updateRecycler()
//    override fun onFinish() {
//    private fun updateRecycler() {
//        if (search.text.isNotEmpty()) cViewModel.getCityInfo(search.text.toString())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({ result ->
//                if (!result.isNullOrEmpty()) rv_cities.adapter = CityAdapter(result)
//            }, { error ->
//                error.printStackTrace()
//            })
//    }

