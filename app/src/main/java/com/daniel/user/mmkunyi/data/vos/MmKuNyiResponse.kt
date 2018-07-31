package com.daniel.user.mmkunyi.data.vos

import com.google.gson.annotations.SerializedName

class MmKuNyiResponse {
    var applicant: List<ApplicantVO>? = null
    var availablePostCount: Int? = 0
    var email: String? = ""
    var fullDesc: String? = ""

    @SerializedName("genderForJob")
    var gender: Int? = 0

    var images: List<String>? = null
    var importantNotes: List<String>? = null
    var interested: List<InterestedVO>? = null
    var isActive: String? = "false"
    var jobDuration: JobDetailsVO? = null

    @SerializedName("jobPostId")
    var jobId: String? = ""

    var jobTags: List<JobTagsVO>? = null
    var location: String? = "Kamaryut"

    @SerializedName("makeMoneyRating")
    var moneyRating: String? = ""

    var offerAmount: AmountVO? = null
    var phoneNo: String? = ""
    var postClosedDate: String? = ""
    var postedDate: String? = ""
    var relevant: List<RelevantVO>? = null
    var requiredSkill: List<RequiredSkillVO
            >? = null
    var shortDesc: String? = ""
    var viewed: List<ViewedVO>? = null
}