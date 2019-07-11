package com.example.starwarsappwithmvvm.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard

class FullCardFragment:Fragment() {

    companion object {

        fun newInstance(key: String, card: FullInfoCard): FullCardFragment {
            val fullCardFragment = FullCardFragment()
            val bundle = Bundle()
            bundle.putSerializable(key, card)
            fullCardFragment.arguments = bundle
            return fullCardFragment
        }
    }
}