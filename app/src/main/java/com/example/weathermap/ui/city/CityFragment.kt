package com.example.weathermap.ui.city

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.weathermap.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_city.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityFragment : Fragment() {
    private lateinit var search: EditText
    private lateinit var timer: CountDownTimer
    private lateinit var  adapter: CityAdapter
    private val cViewModel: CityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_city, container, false)
        search = view.findViewById(R.id.search_field)
        timer = object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                updateRecycler()
            }

            override fun onTick(millisUntilFinished: Long) {
                Toast.makeText(activity, millisUntilFinished.toString(), Toast.LENGTH_LONG).show()
            }
        }
        search()
        return view
    }

    private fun updateRecycler() {
        if (search.text.isNotEmpty()) cViewModel.getCityInfo(search.text.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                if (!result.isNullOrEmpty()) rv_cities.adapter = CityAdapter(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun search() {
        search.run {
            this.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    timer.start()
                }
            })
            this.setOnEditorActionListener { v, actionId, event ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        timer.onFinish()
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
