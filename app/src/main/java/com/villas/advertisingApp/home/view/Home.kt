package com.villas.advertisingApp.home.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.villas.advertisingApp.home.interfaces.IHome
import com.polimentes.utilitieskotlin.Util
import com.polimentes.utilitieskotlin.pager.IPagerView
import com.polimentes.utilitieskotlin.pager.PagerAdapter
import com.villas.advertisingApp.R
import com.villas.advertisingApp.bluetoothConnection.BluetoothActivity
import com.villas.advertisingApp.constants.App
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*


class Home: AppCompatActivity(),IHome.View, IPagerView {

    private var bundle:Bundle? = Bundle()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setData()
        if (getBundle().containsKey(App.TAG_FRAGMENT_MAC)){
            bundle!!.putString(App.TAG_FRAGMENT_MAC,getBundle().getString(App.TAG_FRAGMENT_MAC))
        }

    }

    override fun setData() {
        buildPager()
        setListeners()

        //changeFragment(FragmentListScanner.newInstance(),false, App.TAG_FRAGMENT_SCANNER_BLE)
    }

    override fun setListeners() {
        btnAddBle.setOnClickListener {
            redirectTo(BluetoothActivity::class.java,null,null,true)
        }
    }

    override fun getBundle(): Bundle {
        return intent.extras ?: Bundle()
    }

    override fun buildPager() {
        tabs.setupWithViewPager(viewPager)
        val position = tabs.selectedTabPosition
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addPage(FragmentConnection.newInstance())
        pagerAdapter.addPage(FragmentMessage.newInstance(bundle))
        pagerAdapter.addPage(FragmentHistory.newInstance())
        viewPager.adapter = pagerAdapter
        setTabLayout()
        if (getBundle().containsKey(App.TAG_FRAGMENT_MESSAGE))
        {
            viewPager?.currentItem = 1

        }
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }


    private fun setTabLayout() {

        val tabText = resources.getStringArray(R.array.array_tablayout_items)

        for (i in 0 until tabs.tabCount) {
            val tab = tabs.getTabAt(i)
            if (tab != null) {
                val view = layoutInflater.inflate(R.layout.tab_text, null)
                val tabTitle = view.findViewById<TextView>(R.id.tabHome)
                tabTitle.text = tabText[i % tabs.tabCount]
                tab.customView = view
            }
        }
    }

    override fun showMessage(messageResource: Any) {
    }

    override fun setVisibilityProgressBar(isVisible: Boolean) {
    }

    override fun redirectTo(classToRedirect: Class<*>, flags: Array<Int>?, params: Bundle?, finish: Boolean?) {
        Util.redirectTo(this,classToRedirect,flags,params,finish)
    }

    override fun changeFragment(fragment: Fragment, addToStack: Boolean, tag: String) {
        //Util.callFragment(supportFragmentManager, R.id.generalContainer, fragment, addToStack, tag)
    }

}