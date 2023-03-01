package com.bigtoapp.notes.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.bigtoapp.notes.R
import com.bigtoapp.notes.main.sl.ProvideViewModel

class MainActivity : AppCompatActivity(), ProvideViewModel {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = provideViewModel(MainViewModel::class.java, this)
        viewModel.observe(this){ strategy ->
            strategy.navigate(supportFragmentManager, R.id.container)
        }
        viewModel.singleInit(savedInstanceState == null)
    }

    override fun <T : ViewModel> provideViewModel(clazz: Class<T>, owner: ViewModelStoreOwner): T =
        (application as ProvideViewModel).provideViewModel(clazz, owner)
}