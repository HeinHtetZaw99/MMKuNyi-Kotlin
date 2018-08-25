package com.daniel.user.mmkunyi.data.vos

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.daniel.user.mmkunyi.persistence.typeconverter.*

@Entity(tableName = "jobList")
@TypeConverters(ImagesTypeConverter::class, ApplicantTypeConverter::class, RelevantTypeConverter::class,
		ViewedTypeConverter::class, RequiredSkillTypeConverter::class, InterestedTypeConverter::class,
		JobTagTypeConverter::class , LikeTypeConverter::class)
data class MMKunyiResponse(
		var images: List<String>? = ArrayList<String>(),

		@NonNull
		@PrimaryKey
		var jobPostId: Int? = null,
		var genderForJob: Int? = null,
		@Embedded
		var jobDuration: JobDurationVO? = JobDurationVO(),

		var availablePostCount: Int? = null,
		var fullDesc: String? = null,
		var phoneNo: String? = null,
		var applicant: List<ApplicantVO?>? = ArrayList<ApplicantVO>(),
		var postedDate: String? = null,
		var relevant: List<RelevantVO?>? = ArrayList<RelevantVO>(),
		var makeMoneyRating: Int? = null,
		var importantNotes: List<String?>? = ArrayList<String>(),
		var viewed: List<ViewedVO?>? = ArrayList<ViewedVO>(),
		@Embedded
		var offerAmount: OfferAmountVO? = OfferAmountVO(),
		var location: String? = null,
		var requiredSkill: List<RequiredSkillVO?>? = ArrayList<RequiredSkillVO>(),
		var shortDesc: String? = null,
		var interested: List<InterestedVO?>? = ArrayList<InterestedVO>(),
		var jobTags: List<JobTagsVO?>? = ArrayList<JobTagsVO>(),
		var postClosedDate: String? = null,
		var email: String? = null,
		var like: List<LikeVO>? = ArrayList<LikeVO>()
) {
	var isActive: Boolean = true
		get() = field
		set(value) { field = value }

}
