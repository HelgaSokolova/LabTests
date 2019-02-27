package com.westsamoaconsult.labtests.information

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.fragment_info_view.*

class InfoViewFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val listInfoItem = mutableListOf<InfoItem>()
        listInfoItem.add(SectionInfoItem("GENERAL"))
        listInfoItem.add(SectionInfoItem("IN-APP PURCHASE"))
        listInfoItem.add(SectionInfoItem("APP DETAILS"))

        recyclerView.adapter = InfoAdapter(listInfoItem)
    }
}