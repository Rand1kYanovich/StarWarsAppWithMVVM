package com.example.starwarsappwithmvvm.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.example.starwarsappwithmvvm.DataAdapter
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.listeners.OnCardClickListener
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.util.FragmentUtil
import com.example.starwarsappwithmvvm.viewmodel.ApiViewModel
import com.example.starwarsappwithmvvm.viewmodel.DatabaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class FavoriteCardsFragment : Fragment() {


    private val viewModelApi: ApiViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ApiViewModel::class.java)
    }

    private val viewModelDatabase: DatabaseViewModel by lazy {
        ViewModelProviders.of(activity!!).get(DatabaseViewModel::class.java)
    }

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdapter
    lateinit var layoutManager: LinearLayoutManager


    companion object {
        fun newInstance(): FavoriteCardsFragment = FavoriteCardsFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_favorite_cards, container, false)
        setHasOptionsMenu(true)

        viewModelDatabase.getDao()
            .getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                adapter.setList(ArrayList(t))
            }

        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.isNestedScrollingEnabled = false
        recyclerView.setHasFixedSize(true)

        adapter = DataAdapter(ArrayList(), context!!)
        adapter.setColorArray(resources.getStringArray(R.array.card_color))

        adapter.setClickListener(object : OnCardClickListener {
            override fun onCardClickListener(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
                FragmentUtil.replaceWithBackStack(
                    activity!!.supportFragmentManager,
                    R.id.container,
                    FullCardFragment.newInstance(getString(R.string.bundle_argument_name), cardsList.get(position))
                )
            }
        })

        adapter.setFavoriteListener(viewModelApi.getFavoriteListener())
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val item = menu!!.findItem(R.id.action_favorite)
        item.isVisible = false

    }
}