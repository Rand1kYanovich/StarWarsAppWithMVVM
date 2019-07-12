package com.example.starwarsappwithmvvm.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import com.example.starwarsappwithmvvm.DataAdapter
import com.example.starwarsappwithmvvm.R
import com.example.starwarsappwithmvvm.listeners.OnCardClickListener
import com.example.starwarsappwithmvvm.model.entity.FullInfoCard
import com.example.starwarsappwithmvvm.util.FragmentUtil
import com.example.starwarsappwithmvvm.viewmodel.allCards.AllCardsViewModel
import com.example.starwarsappwithmvvm.viewmodel.allCards.PaginationScrollListener


class AllCardsFragment: Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var colorArray: Array<String>
    lateinit var etSearch: EditText
    lateinit var btnSearch: ImageButton

    private val viewModel:AllCardsViewModel by lazy {
        ViewModelProviders.of(activity!!).get(AllCardsViewModel::class.java)
    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val rootView:View  = inflater.inflate(R.layout.fragment_all_cards,container,false)

        etSearch = rootView.findViewById(R.id.etSearch)
        btnSearch = rootView.findViewById(R.id.btnSearch)
        btnSearch = viewModel.addSearchListener(btnSearch,etSearch)
        colorArray = resources.getStringArray(R.array.card_color)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView = viewModel.getScrollListener(recyclerView,layoutManager)

        val cardList = viewModel.getCardList()

            cardList.observe(viewLifecycleOwner,
                Observer<ArrayList<FullInfoCard>> { t -> adapter.setList(t!!) })

        val favoriteListener = viewModel.getFavoriteListener()
        adapter = DataAdapter(ArrayList(), activity!!.applicationContext)
        adapter.setColorArray(colorArray)
        adapter.setClickListener(object:OnCardClickListener{
            override fun onCardClickListener(view: View, position: Int, cardsList: ArrayList<FullInfoCard>) {
                FragmentUtil.replaceWithBackStack(
                    activity!!.supportFragmentManager,
                    R.id.container,
                    FullCardFragment.newInstance(getString(R.string.bundle_argument_name), cardsList.get(position))
                )

            }
        })
        adapter.setFavoriteListener(favoriteListener)

        recyclerView.setAdapter(adapter)
        recyclerView.setNestedScrollingEnabled(false)
        recyclerView.setHasFixedSize(true)

        return rootView

    }
}