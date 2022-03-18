package com.lauwba.portalkita.model

import com.google.gson.annotations.SerializedName

data class ResponseDetailBerita(

	@field:SerializedName("post_on")
	val postOn: String? = null,

	@field:SerializedName("jdl_news")
	val jdlNews: String? = null,

	@field:SerializedName("judul_seo")
	val judulSeo: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("ket_news")
	val ketNews: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Int? = null,

	@field:SerializedName("nama_kategori")
	val namaKategori: String? = null,

	@field:SerializedName("foto_news")
	val fotoNews: String? = null
)
