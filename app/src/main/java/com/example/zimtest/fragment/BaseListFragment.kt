package com.example.zimtest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.zimtest.R
import com.example.zimtest.activity.DetailActivity
import com.example.zimtest.adapter.RecyclerAdapter
import com.example.zimtest.databinding.ListFragmentBinding
import com.example.zimtest.getStringFromResources
import com.example.zimtest.model.Data
import com.example.zimtest.model.DataResponse
import com.example.zimtest.startActivityWithExtras
import com.example.zimtest.showToast
import com.example.zimtest.viewModel.BaseFragmentViewModel


class BaseListFragment : Fragment(), RecyclerAdapter.OnItemClickListener {
    private lateinit var viewModel: BaseFragmentViewModel
    private lateinit var adapter: RecyclerAdapter
    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BaseFragmentViewModel::class.java)
        binding.viewModel = viewModel
        setRecyclerViewAdapter()

        val query = getQueryFromBundle()
        if (query != "") {
            downloadData(query)
        } else {
            showEmptyDataMessage()
        }
    }

    private fun setRecyclerViewAdapter() {
        adapter = RecyclerAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun getQueryFromBundle(): String {
        val bundle = this.arguments
        if (bundle != null) {
            val query = bundle.getString(getStringFromResources(R.string.query), "")
            if (query != "") {
                return query
            }
        }
        return ""
    }

    private fun downloadData(query: String) {
        viewModel.getData(query).observe(this,
            Observer { setData(it) })
    }

    private fun setData(response: DataResponse) {
        adapter.setList(response.data)
    }

    private fun showEmptyDataMessage() {
        activity!!.showToast(getStringFromResources(R.string.empty_query))
    }

    override fun onItemClick(data: Data) {
        startDetailActivity(data)
    }

    private fun startDetailActivity(data: Data) {
        activity!!.startActivityWithExtras(
            DetailActivity::class.java,
            getStringFromResources(R.string.data_key),
            data
        )
    }
}
