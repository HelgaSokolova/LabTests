package com.westsamoaconsult.labtests.tabs.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.westsamoaconsult.labtests.BuildConfig
import com.westsamoaconsult.labtests.DisclaimerActivity
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.utils.Constants
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.android.synthetic.main.main_activity.*


class InfoViewFragment: BaseFragment(), RadioGroup.OnCheckedChangeListener, InfoAdapter.OnItemClickListener {
    private val listInfoItem = mutableListOf<InfoItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onForeground()
        recyclerView.layoutManager = LinearLayoutManager(activity)

        buildList()
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).apply {
            setPanelVisible(true, right = false)
            setTitle("More")
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.buttonSI -> Utils.saveData(Constants.REFERENCE_RANGE, "SI")
            R.id.buttonUS -> Utils.saveData(Constants.REFERENCE_RANGE, "US")
            R.id.buttonSmall -> {
                Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 14)
                buildList()
            }
            R.id.buttonMedium -> {
                Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 16)
                buildList()
            }
            R.id.buttonLarge -> {
                Utils.saveData(Constants.GLOBAL_TEXT_SIZE, 19)
                buildList()
            }
        }
    }

    override fun onClick(description: String) {
        when (description) {
            "Send Feedback" -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "mail@mediconapps.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on LabTests")
                startActivity(Intent.createChooser(emailIntent, "Feedback on LabTests"))
            }
            "Disclaimer" -> {
                if (isLocked) return
                setLockScreen()
                startActivity(Intent(activity!!, DisclaimerActivity::class.java))
            }
            "Reset tube colors" -> {
                Utils.removeData("customDots")
                Utils.showAlertDialog(activity!!, "Reset completed", "The color of the tubes have been reset")
            }
            "Restore in-app purchases" -> {}
        }
    }

    private fun buildList() {
        listInfoItem.clear()

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

        val strVersion = String.format("%s (%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)

        listInfoItem.add(SectionInfoItem("GENERAL"))
        listInfoItem.add(RangeInfoItem(defaultRangeId, this))
        listInfoItem.add(TextSizeInfoItem(defaultTextId, this))
        listInfoItem.add(DefaultInfoItem("Send Feedback", this))
        listInfoItem.add(DefaultInfoItem("Disclaimer", this))
        listInfoItem.add(DefaultInfoItem("Reset tube colors", this, true))

        listInfoItem.add(SectionInfoItem("IN-APP PURCHASE"))
        listInfoItem.add(DefaultInfoItem("Restore in-app purchases", this, true))

        listInfoItem.add(SectionInfoItem("APP DETAILS"))
        listInfoItem.add(DefaultInfoItem("Version", null, true, strVersion))

        recyclerView.adapter = InfoAdapter(listInfoItem)
    }
}
