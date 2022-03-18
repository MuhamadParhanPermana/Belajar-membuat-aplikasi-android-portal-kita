package com.lauwba.portalkita

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.lauwba.portalkita.databinding.ActivityDetailNewsBinding
import com.lauwba.portalkita.model.ResponseDetailBerita
import com.lauwba.portalkita.network.NetworkConfig
import retrofit2.Call
import retrofit2.Response


class DetailNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNewsBinding
    private var urlSeo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set title
        supportActionBar?.title = "Detail Berita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //receive incoming intent extras
        val idNews = intent.getStringExtra("idNews")
        urlSeo = intent.getStringExtra("judulSeo")
        getDetailNews(idNews)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add(0, 0, 0, "View On Browser")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            0 -> openInBrowser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openInBrowser() {
        val initialUrl = NetworkConfig().BASE_URL + "/reader/" + this.urlSeo
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(initialUrl))
    }

    private fun getDetailNews(idNews: String?) {
        NetworkConfig().getService()
            .getDetailNews(idNews)
            .enqueue(object : retrofit2.Callback<ResponseDetailBerita> {
                override fun onResponse(
                    call: Call<ResponseDetailBerita>,
                    response: Response<ResponseDetailBerita>
                ) {
                    this@DetailNewsActivity.binding.progressIndicator.visibility = View.GONE
                    if (response.isSuccessful) {
                        val detailData = response.body()
                        setToView(detailData)
                    }
                }

                override fun onFailure(call: Call<ResponseDetailBerita>, t: Throwable) {
                    this@DetailNewsActivity.binding.progressIndicator.visibility = View.GONE
                    t.printStackTrace()
                    Toast.makeText(this@DetailNewsActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun setToView(detailData: ResponseDetailBerita?) {
        this.binding.judul.text = detailData?.jdlNews
        this.binding.isi.text = detailData?.ketNews
        Glide.with(this)
            .load(detailData?.fotoNews)
            .into(this.binding.coverBerita)
    }
}
