package com.example.zimtest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.zimtest.R
import com.example.zimtest.databinding.ActivityMainBinding
import com.example.zimtest.fragment.BaseListFragment
import com.example.zimtest.viewModel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    private val catListFragment: BaseListFragment = BaseListFragment()
    private val dogListFragment: BaseListFragment = BaseListFragment()

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setDataBinding()
        switchTab()
        setTabSelectListener()
    }

    private fun setDataBinding(){
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        binding.tabLayout.getTabAt(viewModel.currentTabId)?.select()
    }

    private fun setTabSelectListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewModel.currentTabId = binding.tabLayout.selectedTabPosition
                switchTab()
            }
        })
    }

    private fun switchTab() {
        val catStringTag = resources.getString(R.string.cat_fragment_tag)
        val dogStringTag = resources.getString(R.string.dog_fragment_tag)
        when (viewModel.currentTabId) {
            0 -> showSelectedFragment(
                catListFragment,
                dogStringTag,
                catStringTag
            )
            1 -> showSelectedFragment(
                dogListFragment,
                catStringTag,
                dogStringTag
            )
        }
    }

    private fun showSelectedFragment(selectedFragment: Fragment, from: String, to: String) {
        setQueryToFragmentBundle(selectedFragment, to)
        val fragmentTo = supportFragmentManager.findFragmentByTag(to)
        val fragmentFrom = supportFragmentManager.findFragmentByTag(from)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragmentTo == null || !fragmentTo.isAdded) {
            fragmentTransaction.add(R.id.fragmentController, selectedFragment, to)
        } else {
            fragmentTransaction.show(fragmentTo)
        }
        if (fragmentFrom != null) {
            fragmentTransaction.hide(fragmentFrom)
        }
        fragmentTransaction.commit()
    }

    private fun setQueryToFragmentBundle(fragment:Fragment, query: String){
        val bundle = Bundle()
        bundle.putString(resources.getString(R.string.query), query)
        fragment.arguments = bundle
    }
}
