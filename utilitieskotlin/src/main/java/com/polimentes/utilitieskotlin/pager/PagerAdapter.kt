package com.polimentes.utilitieskotlin.pager

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.ArrayList

/**
 * @author Edgar GrupoBECM
 * Description:
 * created on 05/05/2017
 */

class PagerAdapter : FragmentStatePagerAdapter {

    private var pages: ArrayList<Fragment>? = null
    private var fragmentManager: FragmentManager? = null

    constructor(fragmentManager: FragmentManager) : super(fragmentManager) {
        pages = ArrayList()
        this.fragmentManager = fragmentManager
    }

    constructor(fragmentManager: FragmentManager, pages: ArrayList<Fragment>) : super(fragmentManager) {
        this.pages = pages
        this.fragmentManager = fragmentManager
    }

    fun addPage(page: Fragment) {
        if (pages != null) {
            Log.d("logApp", (pages?.size ?: 0).toString())
            pages!!.add(page)
        }

    }

    override fun getItem(position: Int): Fragment {
        return pages!![position]
    }

    override fun getCount(): Int {
        return pages!!.size
    }
}