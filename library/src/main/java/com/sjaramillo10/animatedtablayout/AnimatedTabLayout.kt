package com.sjaramillo10.animatedtablayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView

class AnimatedTabLayout(context: Context, attrs: AttributeSet) : TabLayout(context, attrs), TabLayout.OnTabSelectedListener {

    /** Text sizes used for the tab text animation */
    private var mSmallTextSize: Float = spToPx(16f, context)
    private var mBigTextSize: Float = spToPx(20f, context)

    /** Colors used for the selected and unselected tab text animation */
    private var mSelectedTabTextColor: Int = Color.parseColor("#ffffff") // white
    private var mUnselectedTabTextColor: Int = Color.parseColor("#88ffffff") // semi transparent

    /** Tab select/unselect animation duration in ms */
    private var mAnimationDuration: Int = 500

    /**
     * Equal spacing between tabs and start-to-first-tab and last-tab-to-end
     */
    private var mTabSpacing: Float = dpToPx(32f, context)

    /** Helper method to convert from SP (Scale-independent Pixels) to PX (Pixels) */
    private fun spToPx(sp: Float, context: Context) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)

    /** Helper method to convert from DP (Density-independent Pixels) to PX (Pixels) */
    private fun dpToPx(sp: Float, context: Context) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, context.resources.displayMetrics)

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimatedTabLayout,
                0, 0)

        try {
            // Get selected/unselected text size
            mSmallTextSize = array.getDimensionPixelSize(R.styleable.AnimatedTabLayout_smallTextSize,
                    mSmallTextSize.toInt()).toFloat()
            mBigTextSize = array.getDimensionPixelSize(R.styleable.AnimatedTabLayout_bigTextSize,
                    mBigTextSize.toInt()).toFloat()

            // Get selected/unselected colors
            mSelectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_selectedTabTextColor,
                    mSelectedTabTextColor)
            mUnselectedTabTextColor = array.getColor(R.styleable.AnimatedTabLayout_unselectedTabTextColor,
                    mUnselectedTabTextColor)

            // Get selected/unselected animation duration
            mAnimationDuration = array.getInt(R.styleable.AnimatedTabLayout_animationDuration,
                    mAnimationDuration)

            // Get start and end tab additional padding
            mTabSpacing = array.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tabSpacing,
                    mTabSpacing.toInt()).toFloat()

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

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSmallTextSize)
        textView.setTextColor(mUnselectedTabTextColor)

        textView.setPadding(0,0,0,0)

        if(position == 0)
            textView.setPadding(mTabSpacing.toInt(), 0,
                mTabSpacing.toInt(), 0)
        else
            textView.setPadding(0, 0,
                    mTabSpacing.toInt(), 0)

        tab.customView = textView
    }

    /**
     * Creates animation to Tab, from unselected to selected state
     */
    override fun onTabUnselected(tab: Tab?) {
        val textView = tab!!.customView as TextView?

        textView!!.setTextColor(mUnselectedTabTextColor)

        val animator = ValueAnimator.ofFloat(mBigTextSize, mSmallTextSize)
        animator.duration = mAnimationDuration.toLong()

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

        val animator = ValueAnimator.ofFloat(mSmallTextSize, mBigTextSize)
        animator.duration = mAnimationDuration.toLong()

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