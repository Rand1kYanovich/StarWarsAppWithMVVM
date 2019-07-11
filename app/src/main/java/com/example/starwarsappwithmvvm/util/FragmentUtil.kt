package com.example.starwarsappwithmvvm.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


class FragmentUtil {
    companion object {
        fun replace(supportFragmentManager: FragmentManager, layout: Int, fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .replace(layout, fragment)
                .commit()

        }

        fun replaceWithBackStack(supportFragmentManager: FragmentManager, layout: Int, fragment: Fragment) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(layout, fragment)
                .commit()

        }
    }
}