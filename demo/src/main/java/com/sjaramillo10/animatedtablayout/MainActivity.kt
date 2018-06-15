package com.sjaramillo10.animatedtablayout

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainActivity : AppCompatActivity() {

    // Colors used for the selected and unselected tab text
    internal var white: Int = 0
    internal var semitransparentWhite: Int = 0

    // Text sizes used for the tab text animation
    internal var smallText: Float = 0f
    internal var bigText: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = PagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        smallText = 16f
        bigText = 20f

        setupDynamicTabLayout()
    }

    private fun setupDynamicTabLayout() {
        white = ContextCompat.getColor(this, R.color.white)
        semitransparentWhite = ContextCompat.getColor(this, R.color.semiTransparentWhite)

        // Initialize tabs with custom views
        var i = 0
        val count = tabLayout.tabCount
        while (i < count) {
            val textView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
            textView.text = tabLayout.getTabAt(i)!!.text

            if (i == 0) {
                textView.textSize = bigText
                textView.setTextColor(white)
            } else {
                textView.textSize = smallText
                textView.setTextColor(semitransparentWhite)
            }

            tabLayout.getTabAt(i)!!.customView = textView
            i++
        }

        // Change selected/unselected  text color and nice animation to change the text size
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val textView = tab.customView as TextView?

                textView!!.setTextColor(white)

                val animationDuration = 500 // Animation duration in ms

                val animator = ValueAnimator.ofFloat(smallText, bigText)
                animator.duration = animationDuration.toLong()

                animator.addUpdateListener { valueAnimator ->
                    val animatedValue = valueAnimator.animatedValue as Float
                    textView.textSize = animatedValue
                }

                animator.start()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val textView = tab.customView as TextView?

                textView!!.setTextColor(semitransparentWhite)

                val animationDuration = 500 // Animation duration in ms

                val animator = ValueAnimator.ofFloat(bigText, smallText)
                animator.duration = animationDuration.toLong()

                animator.addUpdateListener { valueAnimator ->
                    val animatedValue = valueAnimator.animatedValue as Float
                    textView.textSize = animatedValue
                }

                animator.start()
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private inner class PagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return getString(R.string.tab_title, position + 1)
        }

        override fun getCount(): Int {
            return 5
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}