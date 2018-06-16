package com.sjaramillo10.animatedtablayout

import android.content.Context
import android.support.design.widget.TabLayout
import android.util.AttributeSet

class AnimatedTabLayout(context: Context?, attrs: AttributeSet?) : TabLayout(context, attrs) {

    /** Text sizes used for the tab text animation */
    internal var mSmallText: Float = 0f
    internal var mBigText: Float = 0f

    
}