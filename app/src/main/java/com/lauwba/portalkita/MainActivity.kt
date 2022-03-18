package com.lauwba.portalkita

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.lauwba.portalkita.databinding.ActivityMainBinding
import com.lauwba.portalkita.model.DataItem
import com.lauwba.portalkita.model.ResponseListBerita
import com.lauwba.portalkita.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLatestNews()
    }

    private fun getLatestNews() {
        //panggil Retrofit nya ya
        NetworkConfig().getService()
            .getLatestNews()
            .enqueue(object : Callback<ResponseListBerita> {
                override fun onResponse(
                    call: Call<ResponseListBerita>,
                    response: Response<ResponseListBerita>
                ) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val receivedDatas = response.body()?.data
                        setToAdapter(receivedDatas)
                    }
                }

                override fun onFailure(call: Call<ResponseListBerita>, t: Throwable) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
                }
            })
    }

    private fun setToAdapter(receivedDatas: List<DataItem?>?) {
        //reset adapter first
        this.binding.newsList.adapter = null

        val adapter = NewsListAdapter(receivedDatas) {
            val detailNewsIntent = Intent(this@MainActivity, DetailNewsActivity::class.java)
            detailNewsIntent.putExtra("idNews", it?.id)
            detailNewsIntent.putExtra("judulSeo", it?.judulSeo)
            startActivity(detailNewsIntent)
        }
        val lm = LinearLayoutManager(this)
        this.binding.newsList.layoutManager = lm
        this.binding.newsList.itemAnimator = DefaultItemAnimator()
        this.binding.newsList.adapter = adapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchNews(query)
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        return true
    }

    private fun searchNews(query: String?) {
        //show progressbar first
        this.binding.progressIndicator.visibility = View.VISIBLE
        NetworkConfig()
            .getService()
            .searchNews(query)
            .enqueue(object : Callback<ResponseListBerita> {
                override fun onResponse(
                    call: Call<ResponseListBerita>,
                    response: Response<ResponseListBerita>
                ) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val receivedDatas = response.body()?.data
                        setToAdapter(receivedDatas)
                    }
                }

                override fun onFailure(call: Call<ResponseListBerita>, t: Throwable) {
                    this@MainActivity.binding.progressIndicator.visibility = View.GONE
                    Log.d("Retrofit: onFailure: ", "onFailure: ${t.stackTrace}")
                }
            })
    }
}
