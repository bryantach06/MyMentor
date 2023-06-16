package com.capstone.mymentor.data.response

import com.google.gson.annotations.SerializedName

data class MentorResultResponse(

	@field:SerializedName("MentorResultResponse")
	val mentorResultResponse: List<MentorResultResponseItem>
)

data class MentorResultResponseItem(

	@field:SerializedName("Experience")
	val experience: String,

	@field:SerializedName("Similarity")
	val similarity: Any,

	@field:SerializedName("Education")
	val education: String,

	@field:SerializedName("Rating")
	val rating: Any,

	@field:SerializedName("Work Position")
	val workPosition: String,

	@field:SerializedName("Name")
	val name: String
)
