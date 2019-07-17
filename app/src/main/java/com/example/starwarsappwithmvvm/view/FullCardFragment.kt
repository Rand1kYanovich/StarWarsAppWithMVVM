package com.example.starwarsappwithmvvm.view

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.viewmodel.DatabaseViewModel

class FullCardFragment : Fragment() {


    private val viewModel: DatabaseViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DatabaseViewModel::class.java)
    }

    var fullCardObject: FullInfoCard
    lateinit var clCard: ConstraintLayout
    lateinit var btnFavorite: ImageButton
    lateinit var tvName: TextView
    lateinit var tvHeight: TextView
    lateinit var tvMass: TextView
    lateinit var tvHairColor: TextView
    lateinit var tvSkinColor: TextView
    lateinit var tvEyeColor: TextView
    lateinit var tvBirthYear: TextView
    lateinit var tvGender: TextView


    companion object {

        fun newInstance(key: String, card: FullInfoCard): FullCardFragment {
            val fullCardFragment = FullCardFragment()
            val bundle = Bundle()
            bundle.putSerializable(key, card)
            fullCardFragment.arguments = bundle
            return fullCardFragment
        }
    }

    init {
        fullCardObject = FullInfoCard()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_full_card, container, false)
        setHasOptionsMenu(true)
        fullCardObject = arguments!!.getSerializable(getString(R.string.bundle_argument_name)) as FullInfoCard

        clCard = rootView.findViewById(R.id.clCard)
        clCard.setBackgroundColor(Color.parseColor(fullCardObject.color))

        btnFavorite = rootView.findViewById(R.id.btnFavorite)
        btnFavorite.setBackgroundColor(Color.parseColor(fullCardObject.color))

        if (fullCardObject.isFavorite) btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                context!!,
                R.drawable.ic_favorite_true
            )
        )
        else btnFavorite.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_favorite_false))

        btnFavorite = viewModel.getClickListener(btnFavorite, fullCardObject,context!!)



        tvName = rootView.findViewById(R.id.tvName)
        tvName.text = String.format(getString(R.string.tvName), fullCardObject.name)

        tvHeight = rootView.findViewById(R.id.tvHeight)
        tvHeight.text = String.format(getString(R.string.tvHeigh), fullCardObject.height)

        tvMass = rootView.findViewById(R.id.tvMass)
        tvMass.text = String.format(getString(R.string.tvWeight), fullCardObject.mass)

        tvHairColor = rootView.findViewById(R.id.tvHairColor)
        tvHairColor.text = String.format(getString(R.string.tvHairColor), fullCardObject.hairColor)

        tvSkinColor = rootView.findViewById(R.id.tvSkinColor)
        tvSkinColor.text = String.format(getString(R.string.tvSkinColor), fullCardObject.skinColor)

        tvEyeColor = rootView.findViewById(R.id.tvEyeColor)
        tvEyeColor.text = String.format(getString(R.string.tvEyeColor), fullCardObject.eyeColor)

        tvBirthYear = rootView.findViewById(R.id.tvBirthYear)
        tvBirthYear.text = String.format(getString(R.string.tvBirthday), fullCardObject.birthYear)

        tvGender = rootView.findViewById(R.id.tvGender)
        tvGender.text = String.format(getString(R.string.tvGender), fullCardObject.gender)

        return rootView
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val item = menu!!.findItem(R.id.action_favorite)
        item.isVisible = false

    }


}