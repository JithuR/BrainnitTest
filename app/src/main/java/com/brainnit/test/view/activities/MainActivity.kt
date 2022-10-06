package com.brainnit.test.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brainnit.test.R
import com.brainnit.test.network.APIs
import com.brainnit.test.network.APIEngine
import com.brainnit.test.databinding.ActivityMainBinding
import com.brainnit.test.model.ResponseModel
import com.brainnit.test.model.ResponseModelItem
import com.brainnit.test.utils.CheckNetworkConnection
import com.brainnit.test.view.adapters.ListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: ListAdapter
    private val data = ArrayList<ResponseModelItem>()
    private var isLoading: Boolean = false
    private var isInitLoading: Boolean = true
    private var count: Int = 0
    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = ListAdapter(this,data)
        layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = mAdapter

        binding.loadBtn.setOnClickListener {

            if(!CheckNetworkConnection.isInternetAvailable(this)){
                Toast.makeText(this,getString(R.string.no_network),Toast.LENGTH_SHORT).show()
            }else {
                binding.mainLayout.visibility = View.GONE
                binding.listLayout.visibility = View.VISIBLE
                binding.progressbar.visibility = View.VISIBLE

                loadData()
            }

        }


       /*
        * recyclerview scroll listener
        * */
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                    if (layoutManager.findLastCompletelyVisibleItemPosition() == data.size - 1) {

                        if (!isLoading) {

                            mAdapter.isLoadingFooter()
                            isLoading = true
                            if (count < 4) {
                                isInitLoading = false
                                loadData()
                            } else {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    mAdapter.removeLoadingFooter()
                                    isLoading = false
                                }, 2000)

                            }

                        }

                    }
            }
        })

        mAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                layoutManager.scrollToPositionWithOffset(positionStart, 0)
            }
        })

    }


    /*
    *  Api Call for loading Media files
    * */
    private fun loadData() {
        isLoading = true
        count++
        var mApiService: APIs = APIEngine.getClient().create(APIs::class.java)
        val call = mApiService.getMediaList()
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                val mediaResponse = response.body()
                isLoading = false
                data.addAll(mediaResponse!!)
                if (!isInitLoading){
                    mAdapter.removeLoadingFooter()
                }
                binding.progressbar.visibility = View.GONE
                mAdapter.notifyDataSetChanged()

            }
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                 Log.e("ResponseData", t.message.toString())
            }
        })

    }

}