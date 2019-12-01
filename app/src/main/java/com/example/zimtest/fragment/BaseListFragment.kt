package com.example.zimtest.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.zimtest.R
import com.example.zimtest.activity.DetailActivity
import com.example.zimtest.adapter.RecyclerAdapter
import com.example.zimtest.databinding.ListFragmentBinding
import com.example.zimtest.model.Data
import com.example.zimtest.model.DataResponse
import com.example.zimtest.viewModel.BaseFragmentViewModel


open class BaseListFragment : Fragment(), RecyclerAdapter.OnItemClickListener{
    private lateinit var viewModel: ViewModel
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

        setRecyclerViewAdapter()

        val query=getQueryFromBundle()
        if (query!=""){
            downloadData(query)
        }else{
            showEmptyDataMessage()
        }
    }

    private fun setRecyclerViewAdapter(){
        adapter = RecyclerAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun getQueryFromBundle():String{
        val bundle = this.arguments
        if (bundle != null) {
            val query = bundle.getString(resources.getString(R.string.query), "")
            if(query!=""){
                return query
            }
        }
        return ""
    }

    private fun downloadData(query: String){
        val baseViewModel = viewModel as BaseFragmentViewModel
        baseViewModel.getData(query).observe(this,
            Observer { setData(it) })
    }

    private fun setData(response: DataResponse) {
        adapter.setList(response.data)
    }

    private fun showEmptyDataMessage(){
        Toast.makeText(activity, resources.getString(R.string.empty_query), Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(data: Data) {
        startDetailActivity(data)
    }

    private fun startDetailActivity(data: Data){
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(resources.getString(R.string.data_key), data)
        startActivity(intent)
    }
}
