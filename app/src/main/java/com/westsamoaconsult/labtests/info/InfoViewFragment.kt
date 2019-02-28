package com.westsamoaconsult.labtests.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment_view.*

class InfoViewFragment: Fragment(), RadioGroup.OnCheckedChangeListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val defaultRangeId = when (Utils.getReferenceRange()) {
            "SI" -> R.id.buttonSI
            else -> R.id.buttonUS
        }
        val defaultTextId = when (Utils.getGlobalTextSize()) {
            14 -> R.id.buttonSmall
            16 -> R.id.buttonMedium
            else -> R.id.buttonLarge
        }

        val listInfoItem = mutableListOf<InfoItem>()
        listInfoItem.add(SectionInfoItem("GENERAL"))
        listInfoItem.add(RangeInfoItem(defaultRangeId, this))
        listInfoItem.add(TextSizeInfoItem(defaultTextId, this))

        listInfoItem.add(SectionInfoItem("IN-APP PURCHASE"))
        listInfoItem.add(SectionInfoItem("APP DETAILS"))

        recyclerView.adapter = InfoAdapter(listInfoItem)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.buttonSI -> Utils.setReferenceRange("SI")
            R.id.buttonUS -> Utils.setReferenceRange("US")
            R.id.buttonSmall -> Utils.setGlobalTextSize(14)
            R.id.buttonMedium -> Utils.setGlobalTextSize(16)
            R.id.buttonLarge -> Utils.setGlobalTextSize(19)
        }
    }
}