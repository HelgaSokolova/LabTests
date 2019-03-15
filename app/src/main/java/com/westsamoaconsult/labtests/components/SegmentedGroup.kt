package com.westsamoaconsult.labtests.components

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.StateSet
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.westsamoaconsult.labtests.R
import java.util.*

class SegmentedGroup : RadioGroup {

    private var mMarginDp: Int
    private var mTintColor: Int = 0
    private var mUnCheckedTintColor: Int = 0
    private var mCheckedTextColor = Color.WHITE
    private var mLayoutSelector: LayoutSelector? = null
    private var mCornerRadius: Float? = null
    private var mCheckedChangeListener: RadioGroup.OnCheckedChangeListener? = null
    private var mDrawableMap: HashMap<Int, TransitionDrawable>? = null
    private var mLastCheckId: Int = 0

    constructor(context: Context) : super(context) {
        mTintColor = ContextCompat.getColor(context, R.color.radio_button_selected_color)
        mUnCheckedTintColor = ContextCompat.getColor(context, R.color.radio_button_unselected_color)
        mMarginDp = getResources().getDimension(R.dimen.radio_button_stroke_border).toInt()
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius)
        mLayoutSelector = LayoutSelector(mCornerRadius!!)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mTintColor = ContextCompat.getColor(context, R.color.radio_button_selected_color)
        mUnCheckedTintColor = ContextCompat.getColor(context, R.color.radio_button_unselected_color)
        mMarginDp = getResources().getDimension(R.dimen.radio_button_stroke_border).toInt()
        mCornerRadius = getResources().getDimension(R.dimen.radio_button_conner_radius)
        initAttrs(attrs)
        mLayoutSelector = LayoutSelector(mCornerRadius!!)
    }

    /* Reads the attributes from the layout */
    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SegmentedGroup,
            0, 0
        )

        try {
            mMarginDp = typedArray.getDimension(
                R.styleable.SegmentedGroup_sc_border_width,
                getResources().getDimension(R.dimen.radio_button_stroke_border)
            ).toInt()

            mCornerRadius = typedArray.getDimension(
                R.styleable.SegmentedGroup_sc_corner_radius,
                getResources().getDimension(R.dimen.radio_button_conner_radius)
            )

            mTintColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_tint_color,
                ContextCompat.getColor(context, R.color.radio_button_selected_color)
            )

            mCheckedTextColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_checked_text_color,
                ContextCompat.getColor(context, android.R.color.white)
            )

            mUnCheckedTintColor = typedArray.getColor(
                R.styleable.SegmentedGroup_sc_unchecked_tint_color,
                ContextCompat.getColor(context, R.color.radio_button_unselected_color)
            )
        } finally {
            typedArray.recycle()
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //Use holo light for default
        updateBackground()
    }

    fun setTintColor(tintColor: Int) {
        mTintColor = tintColor
        updateBackground()
    }

    fun setTintColor(tintColor: Int, checkedTextColor: Int) {
        mTintColor = tintColor
        mCheckedTextColor = checkedTextColor
        updateBackground()
    }

    fun setUnCheckedTintColor(unCheckedTintColor: Int, unCheckedTextColor: Int) {
        mUnCheckedTintColor = unCheckedTintColor
        updateBackground()
    }

    fun updateBackground() {
        mDrawableMap = HashMap()
        val count = super.getChildCount()
        for (i in 0 until count) {
            val child = getChildAt(i)
            updateBackground(child)

            // If this is the last view, don't set LayoutParams
            if (i == count - 1) break

            val initParams = child.layoutParams as RadioGroup.LayoutParams
            val params = RadioGroup.LayoutParams(initParams.width, initParams.height, initParams.weight)
            // Check orientation for proper margins
            if (orientation == LinearLayout.HORIZONTAL) {
                params.setMargins(0, 0, -mMarginDp, 0)
            } else {
                params.setMargins(0, 0, 0, -mMarginDp)
            }
            child.layoutParams = params
        }
    }

    private fun updateBackground(view: View) {
        val checked = mLayoutSelector!!.selected
        val unchecked = mLayoutSelector!!.unselected
        //Set text color
        val colorStateList = ColorStateList(
            arrayOf(intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)),
            intArrayOf(mTintColor, mCheckedTextColor)
        )
        (view as Button).setTextColor(colorStateList)

        //Redraw with tint color
        val checkedDrawable = ContextCompat.getDrawable(context, checked)!!.mutate()
        val uncheckedDrawable = ContextCompat.getDrawable(context, unchecked)!!.mutate()
        (checkedDrawable as GradientDrawable).setColor(mTintColor)
        checkedDrawable.setStroke(mMarginDp, mTintColor)
        (uncheckedDrawable as GradientDrawable).setStroke(mMarginDp, mTintColor)
        uncheckedDrawable.setColor(mUnCheckedTintColor)
        //Set proper radius
        checkedDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)
        uncheckedDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)

        val maskDrawable = ContextCompat.getDrawable(context, unchecked)!!.mutate() as GradientDrawable
        maskDrawable.setStroke(mMarginDp, mTintColor)
        maskDrawable.setColor(mUnCheckedTintColor)
        maskDrawable.cornerRadii = mLayoutSelector!!.getChildRadii(view)
        val maskColor = Color.argb(50, Color.red(mTintColor), Color.green(mTintColor), Color.blue(mTintColor))
        maskDrawable.setColor(maskColor)
        val pressedDrawable = LayerDrawable(arrayOf<Drawable>(uncheckedDrawable, maskDrawable))

        val drawables = arrayOf<Drawable>(uncheckedDrawable, checkedDrawable)
        val transitionDrawable = TransitionDrawable(drawables)
        if ((view as RadioButton).isChecked) {
            transitionDrawable.reverseTransition(0)
        }

        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(
            intArrayOf(-android.R.attr.state_checked, android.R.attr.state_pressed),
            pressedDrawable
        )
        stateListDrawable.addState(StateSet.WILD_CARD, transitionDrawable)

        mDrawableMap!![view.getId()] = transitionDrawable

        view.setBackground(stateListDrawable)

        super.setOnCheckedChangeListener { group, checkedId ->
            val current = mDrawableMap!![checkedId]
            current!!.reverseTransition(0)
            if (mLastCheckId != 0) {
                val last = mDrawableMap!![mLastCheckId]
                last?.reverseTransition(0)
            }
            mLastCheckId = checkedId

            if (mCheckedChangeListener != null) {
                mCheckedChangeListener!!.onCheckedChanged(group, checkedId)
            }
        }
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        mDrawableMap!!.remove(child.id)
    }

    override fun setOnCheckedChangeListener(listener: RadioGroup.OnCheckedChangeListener) {
        mCheckedChangeListener = listener
    }

    /*
         * This class is used to provide the proper layout based on the view.
         * Also provides the proper radius for corners.
         * The layout is the same for each selected left/top middle or right/bottom button.
         * float tables for setting the radius via Gradient.setCornerRadii are used instead
         * of multiple xml drawables.
         */
    private inner class LayoutSelector(
        private val r: Float    //this is the radios read by attributes or xml dimens
    ) {

        private var children: Int = 0
        private var child: Int = 0
        /* Returns the selected layout id based on view */
        val selected = R.drawable.segment_checked
        /* Returns the unselected layout id based on view */
        val unselected = R.drawable.segment_unchecked
        private val r1 = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            0.1f,
            getResources().displayMetrics
        )    //0.1 dp to px
        private val rLeft: FloatArray    // left radio button
        private val rRight: FloatArray   // right radio button
        private val rMiddle: FloatArray  // middle radio button
        private val rDefault: FloatArray // default radio button
        private val rTop: FloatArray     // top radio button
        private val rBot: FloatArray     // bot radio button
        private var radii: FloatArray? = null          // result radii float table

        init {
            children = -1 // Init this to force setChildRadii() to enter for the first time.
            child = -1 // Init this to force setChildRadii() to enter for the first time
            rLeft = floatArrayOf(r, r, r1, r1, r1, r1, r, r)
            rRight = floatArrayOf(r1, r1, r, r, r, r, r1, r1)
            rMiddle = floatArrayOf(r1, r1, r1, r1, r1, r1, r1, r1)
            rDefault = floatArrayOf(r, r, r, r, r, r, r, r)
            rTop = floatArrayOf(r, r, r, r, r1, r1, r1, r1)
            rBot = floatArrayOf(r1, r1, r1, r1, r, r, r, r)
        }

        private fun getChildren(): Int {
            return this@SegmentedGroup.childCount
        }

        private fun getChildIndex(view: View): Int {
            return this@SegmentedGroup.indexOfChild(view)
        }

        private fun setChildRadii(newChildren: Int, newChild: Int) {

            // If same values are passed, just return. No need to update anything
            if (children == newChildren && child == newChild)
                return

            // Set the new values
            children = newChildren
            child = newChild

            // if there is only one child provide the default radio button
            if (children == 1) {
                radii = rDefault
            } else if (child == 0) { //left or top
                radii = if (orientation == LinearLayout.HORIZONTAL) rLeft else rTop
            } else if (child == children - 1) {  //right or bottom
                radii = if (orientation == LinearLayout.HORIZONTAL) rRight else rBot
            } else {  //middle
                radii = rMiddle
            }
        }

        /* Returns the radii float table based on view for Gradient.setRadii()*/
        fun getChildRadii(view: View): FloatArray? {
            val newChildren = getChildren()
            val newChild = getChildIndex(view)
            setChildRadii(newChildren, newChild)
            return radii
        }
    }
}