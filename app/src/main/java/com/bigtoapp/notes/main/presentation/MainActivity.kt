package com.bigtoapp.notes.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bigtoapp.notes.R
import com.bigtoapp.notes.notes.presentation.NotesFragment

class MainActivity : AppCompatActivity(), ShowFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null)
            show(NotesFragment(), false)

    }

    override fun show(fragment: Fragment){
        show(fragment, true)
    }

    private fun show(fragment: Fragment, add: Boolean){
        // todo make navigation strategy
        val transaction = supportFragmentManager.beginTransaction()
        val container = R.id.container
        if (add)
            transaction.replace(container, fragment)
                .addToBackStack(fragment.javaClass.simpleName)
        else
            transaction.replace(container, fragment)
        transaction.commit()
    }
}

interface ShowFragment{
    fun show(fragment: Fragment)

    class Empty: ShowFragment{
        override fun show(fragment: Fragment) = Unit
    }
}