package com.ta.smile.network

import com.google.gson.annotations.SerializedName

data class FecesUrineResponse(

	@field:SerializedName("result")
	val result: List<Double>,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("type")
	val type: Int
)
