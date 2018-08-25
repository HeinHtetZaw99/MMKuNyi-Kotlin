package com.daniel.user.mmkunyi.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.MMKunyiResponse
import com.daniel.user.mmkunyi.utils.AppConstants
import kotlinx.android.synthetic.main.card_view_important_notes.*
import kotlinx.android.synthetic.main.card_view_job_details.*
import kotlinx.android.synthetic.main.card_view_job_duration.*
import kotlinx.android.synthetic.main.card_view_required_skills.*
import kotlinx.android.synthetic.main.content_job_deatils.*
import kotlinx.android.synthetic.main.job_details_list.*

class JobDeatilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_deatils)
//        setSupportActionBar(toolbar)
        val jobId = intent.getStringExtra(AppConstants.JOB_ID)
        Log.d("MM" , "jobid = $jobId")

        var mData  : MMKunyiResponse = MMKuNyiModel.getInstance().getJobById(jobId.toInt())
        displayData(mData)
//        Log.d("MM" , mData.applicant!![0].toString())
        applyButton.setOnClickListener {
            val intent = Intent(this , ApplyFormActivity::class.java)
            intent.putExtra(AppConstants.JOB_ID ,jobId )
            startActivity(intent)
        }


    }

    private fun displayData(mData : MMKunyiResponse){


        jobDetailsTitle.text = mData.shortDesc
        jobDetailsDescription.text = mData.fullDesc
        jobDetailsAmount.text = mData.offerAmount!!.amount.toString() + " kyats"
        jobDetailsLocation.text = mData.location
        jobDetailsEmailAddress.text = mData.email
        jobDetailsPhone.text = mData.phoneNo
        jobDetailsAvailablePost.text =""+mData.availablePostCount+" posts available"

        callPnone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL , Uri.fromParts("tel",mData.phoneNo,null))
            startActivity(intent)
        }

        var gender : String
        if(mData.genderForJob == 1){
            gender = "Male"
        }else
            gender = "Female"
        jobDetailsGender.text = gender

        var importantNotes : String = ""
        for(notes in mData.importantNotes!!){
            importantNotes = importantNotes.plus(notes + "\n")
        }
        tvImportantNotes.text = importantNotes


        var skills : String = ""
        for(skill in mData.requiredSkill!!){
            skills = skills.plus("* "+ skill!!.skillName +" \n")
        }
        tvRequiredSkills.text = skills

        jobDetailsDate.text = "${mData.jobDuration!!.jobStartDate} to ${mData.jobDuration!!.jobEndDate}"
        jobDetailsDuration.text = "${mData.jobDuration!!.workingDaysPerWeek} days"
        jobDetailsHours.text = "${mData.jobDuration!!.workingHoursPerDay} hours"
    }

}
