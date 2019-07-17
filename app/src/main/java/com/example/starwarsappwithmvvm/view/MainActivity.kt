package com.example.starwarsappwithmvvm.view

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.util.FragmentUtil


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val allCardsFragment = AllCardsFragment()
            FragmentUtil.replace(supportFragmentManager, R.id.container, allCardsFragment)
        }

        val actionBar = supportActionBar



        actionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setCustomView(R.layout.actionbar)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {
        R.id.action_favorite -> {
            FragmentUtil.replaceWithBackStack(
                supportFragmentManager,
                R.id.container,
                FavoriteCardsFragment.newInstance()
            )
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }


    }


}