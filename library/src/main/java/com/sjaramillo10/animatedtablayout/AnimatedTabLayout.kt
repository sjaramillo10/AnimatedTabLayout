package com.sjaramillo10.animatedtablayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView

class AnimatedTabLayout(context: Context, attrs: AttributeSet) : TabLayout(context, attrs), TabLayout.OnTabSelectedListener {

    /** Identifies if text should be bold */
    private var mTabBoldText: Boolean = false

    /** Text sizes used for the tab text animation */
    private var mTabSmallTextSize: Float = spToPx(16f, context)
    private var mTabBigTextSize: Float = spToPx(20f, context)

    /** Colors used for the selected and unselected tab text animation */
    private var mSelectedTabTextColor: Int = Color.parseColor("#ffffff") // white
    private var mUnselectedTabTextColor: Int = Color.parseColor("#88ffffff") // semi transparent

    /** Tab select/unselect animation duration in ms */
    private var mTabAnimationDuration: Int = 500

    /** Helper method to convert from SP (Scale-independent Pixels) to PX (Pixels) */
    private fun spToPx(sp: Float, context: Context) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimatedTabLayout,
                0, 0)

        try {
            // Get if text should be bold
            mTabBoldText = array.getBoolean(R.styleable.AnimatedTabLayout_tabBoldText, mTabBoldText)

            // Get selected/unselected text size
            mTabSmallTextSize = array.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tabSmallTextSize,
                    mTabSmallTextSize.toInt()).toFloat()
            mTabBigTextSize = array.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tabBigTextSize,
                    mTabBigTextSize.toInt()).toFloat()

            // Get selected/unselected colors
            mSelectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_selectedTabTextColor,
                    mSelectedTabTextColor)
            mUnselectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_unselectedTabTextColor,
                    mUnselectedTabTextColor)

            // Get selected/unselected animation duration
            mTabAnimationDuration = array.getInt(R.styleable.AnimatedTabLayout_tabAnimationDuration,
                    mTabAnimationDuration)

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

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabSmallTextSize)
        textView.setTextColor(mUnselectedTabTextColor)

        if(mTabBoldText)
            textView.setTypeface(textView.typeface, Typeface.BOLD)

        tab.customView = textView
    }

    /**
     * Creates animation to Tab, from unselected to selected state
     */
    override fun onTabUnselected(tab: Tab?) {
        val textView = tab!!.customView as TextView?

        textView!!.setTextColor(mUnselectedTabTextColor)

        val animator = ValueAnimator.ofFloat(mTabBigTextSize, mTabSmallTextSize)
        animator.duration = mTabAnimationDuration.toLong()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue)
        }

        animator.start()
    }

    /**
     * Creates animation to Tab, from selected to unselected state
     */
    override fun onTabSelected(tab: Tab?) {
        val textView = tab!!.customView as TextView?

        textView!!.setTextColor(mSelectedTabTextColor)

        val animator = ValueAnimator.ofFloat(mTabSmallTextSize, mTabBigTextSize)
        animator.duration = mTabAnimationDuration.toLong()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, animatedValue)
        }

        animator.start()
    }

    override fun onTabReselected(tab: Tab?) {
        // Not used
    }
}