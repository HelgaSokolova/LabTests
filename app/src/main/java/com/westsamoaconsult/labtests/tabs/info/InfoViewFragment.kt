package com.westsamoaconsult.labtests.tabs.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*


class InfoViewFragment: BaseFragment(), RadioGroup.OnCheckedChangeListener, InfoAdapter.OnItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onForeground()

        recyclerView.layoutManager = LinearLayoutManager(activity)

        val defaultRangeId = when (Utils.loadData<String>(Constants.REFERENCE_RANGE)) {
            "SI" -> R.id.buttonSI
            "US" -> R.id.buttonUS
            else -> R.id.buttonSI
        }
        val defaultTextId = when (Utils.loadData<Int>(Constants.GLOBAL_TEXT_SIZE)) {
            14 -> R.id.buttonSmall
            16 -> R.id.buttonMedium
            19 -> R.id.buttonLarge
            else -> R.id.buttonMedium
        }

        val listInfoItem = mutableListOf<InfoItem>()
        listInfoItem.add(SectionInfoItem("GENERAL"))
        listInfoItem.add(RangeInfoItem(defaultRangeId, this))
        listInfoItem.add(TextSizeInfoItem(defaultTextId, this))
        listInfoItem.add(DefaultInfoItem("Send Feedback", this))
        listInfoItem.add(DefaultInfoItem("Disclaimer", this))
        listInfoItem.add(DefaultInfoItem("Reset tube colors", this))

        listInfoItem.add(SectionInfoItem("IN-APP PURCHASE"))
        listInfoItem.add(DefaultInfoItem("Restore in-app purchases", this))

        listInfoItem.add(SectionInfoItem("APP DETAILS"))

        recyclerView.adapter = InfoAdapter(listInfoItem)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).setTitle("Info")
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.buttonSI -> Utils.saveData(Constants.REFERENCE_RANGE, "SI")
            R.id.buttonUS -> Utils.saveData(Constants.REFERENCE_RANGE, "US")
            R.id.buttonSmall -> Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 14)
            R.id.buttonMedium -> Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 16)
            R.id.buttonLarge -> Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 19)
        }
    }

    override fun onClick(description: String) {
        when (description) {
            "Send Feedback" -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "mail@mediconapps.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on LabTests")
                startActivity(Intent.createChooser(emailIntent, "Feedback on LabTests"))
            }
            "Disclaimer" -> {}
            "Reset tube colors" -> {}
            "Restore in-app purchases" -> {}
        }
    }
}
