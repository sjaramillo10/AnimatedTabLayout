package com.sjaramillo10.animatedtablayout

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView

class AnimatedTabLayout(context: Context, attrs: AttributeSet) : TabLayout(context, attrs) {

    /** Text sizes used for the tab text animation */
    private var mSmallText: Float = 16f
    private var mBigText: Float = 20f

    init {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimatedTabLayout,
                0, 0)

        try {
            mSmallText = array.getDimension(R.styleable.AnimatedTabLayout_smallText, mSmallText)
            mBigText = array.getDimension(R.styleable.AnimatedTabLayout_bigText, mBigText)
        } finally {
            array.recycle()
        }
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)

        //white = ContextCompat.getColor(this, R.color.white)
        //semitransparentWhite = ContextCompat.getColor(this, R.color.semiTransparentWhite)

        // Initialize tabs with custom views
        var i = 0
        val count = this.tabCount
        while (i < count) {
            val textView = LayoutInflater.from(context).inflate(R.layout.custom_tab, null) as TextView
            textView.text = this.getTabAt(i)!!.text

            if (i == 0) {
                textView.textSize = mBigText
                //textView.setTextColor(white)
            } else {
                textView.textSize = mSmallText
                //textView.setTextColor(semitransparentWhite)
            }

            this.getTabAt(i)!!.customView = textView
            i++
        }
    }
}