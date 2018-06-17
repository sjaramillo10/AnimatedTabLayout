package com.sjaramillo10.animatedtablayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView

class AnimatedTabLayout(context: Context, attrs: AttributeSet) : TabLayout(context, attrs), TabLayout.OnTabSelectedListener {

    /** Text sizes used for the tab text animation */
    private var mSmallText: Float = 16f
    private var mBigText: Float = 20f

    /** Colors used for the selected and unselected tab text animation */
    private var mSelectedTabTextColor: Int = Color.parseColor("#ffffff") // white
    private var mUnselectedTabTextColor: Int = Color.parseColor("#88ffffff") // semi transparent

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimatedTabLayout,
                0, 0)

        try {
            mSmallText = array.getDimension(R.styleable.AnimatedTabLayout_smallText, mSmallText)
            mBigText = array.getDimension(R.styleable.AnimatedTabLayout_bigText, mBigText)

            mSelectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_selectedTabTextColor,
                    mSelectedTabTextColor)
            mUnselectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_unselectedTabTextColor,
                    mUnselectedTabTextColor)

        } finally {
            array.recycle()
        }

        // Attaches the listeners to animate select/unselect tab
        addOnTabSelectedListener(this)

        // Tab indicator is not needed, as the selected text clearly indicates the selected tab
        setSelectedTabIndicatorHeight(0)
    }

    /**
     * Adds a TextView as the Tab's custom view with predefined unselected values
     */
    override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)

        val textView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
        textView.text = tab.text

        textView.textSize = mSmallText
        textView.setTextColor(mUnselectedTabTextColor)

        tab.customView = textView
    }

    /**
     * Creates animation to Tab, from unselected to selected state
     */
    override fun onTabUnselected(tab: Tab?) {
        val textView = tab!!.customView as TextView?

        textView!!.setTextColor(mUnselectedTabTextColor)

        val animationDuration = 500 // Animation duration in ms

        val animator = ValueAnimator.ofFloat(mBigText, mSmallText)
        animator.duration = animationDuration.toLong()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            textView.textSize = animatedValue
        }

        animator.start()
    }

    /**
     * Creates animation to Tab, from selected to unselected state
     */
    override fun onTabSelected(tab: Tab?) {
        val textView = tab!!.customView as TextView?

        textView!!.setTextColor(mSelectedTabTextColor)

        val animationDuration = 500 // Animation duration in ms

        val animator = ValueAnimator.ofFloat(mSmallText, mBigText)
        animator.duration = animationDuration.toLong()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            textView.textSize = animatedValue
        }

        animator.start()
    }

    override fun onTabReselected(tab: Tab?) {
        // Not used
    }
}