package com.daniel.user.mmkunyi.data.vos

data class MMKunyiResponse(
		val images: List<String?>? = null,
		val jobPostId: Int? = null,
		val genderForJob: Int? = null,
		val jobDuration: JobDuration? = null,
		val isActive: Boolean? = null,
		val availablePostCount: Int? = null,
		val fullDesc: String? = null,
		val phoneNo: String? = null,
		val applicant: List<ApplicantItem?>? = null,
		val postedDate: String? = null,
		val relevant: List<RelevantItem?>? = null,
		val makeMoneyRating: Int? = null,
		val importantNotes: List<String?>? = null,
		val viewed: List<ViewedItem?>? = null,
		val offerAmount: OfferAmount? = null,
		val location: String? = null,
		val requiredSkill: List<RequiredSkillItem?>? = null,
		val shortDesc: String? = null,
		val interested: List<InterestedItem?>? = null,
		val jobTags: List<JobTagsItem?>? = null,
		val postClosedDate: String? = null,
		val email: String? = null
)
