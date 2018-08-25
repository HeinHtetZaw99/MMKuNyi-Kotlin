package com.daniel.user.mmkunyi.activities

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.*
import com.daniel.user.mmkunyi.mvp.presenters.FormPresenter
import com.daniel.user.mmkunyi.mvp.views.FormView
import com.daniel.user.mmkunyi.utils.AppConstants
import kotlinx.android.synthetic.main.activity_application_form.*
import kotlinx.android.synthetic.main.content_application_form.*

class ApplicationFormActivity : AppCompatActivity(), FormView {

    lateinit var mPresenter: FormPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application_form)
        mPresenter = ViewModelProviders.of(this)[FormPresenter::class.java]
        mPresenter.initPresenter(this)
//        mPresenter.onUIReady()


        val array = arrayListOf(formShortDesc, formFullDesc, formTitle
                , formJobLocation, formOfferAmount, formHour, formDays, formWeeks, formStartDate, formEndDate, formJobEmail, formJobPhNo)

        postForm.setOnClickListener {
            Log.e("MMKUNYI" , "Reached in post button task ")
            if (checkAllPlaceHolders(array)) {
                val data = prepareData()
                postToFireBaseDB(data)
                Toast.makeText(this, "Uploading New Job is Done ", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please Fill in all Required Fields", Toast.LENGTH_LONG).show()

            }
        }


//        prepareData()


    }

    companion object {
         fun checkAllPlaceHolders(array : List<EditText>): Boolean {

             val array = array

            var isAllDataFilled = false
            for (editText in array) {
                if (TextUtils.isEmpty(editText.text.toString())) {
                    editText.error = "This is a required field"
                    isAllDataFilled = false

                } else
                    isAllDataFilled = true
            }


            return isAllDataFilled
        }
    }


    private fun prepareData() : MMKunyiResponse{
        val shortDesc = formShortDesc.text
        val fullDescc = formFullDesc.text
        val title = formTitle.text
        val location = formJobLocation.text
        val amount = formOfferAmount.text
        val email = formJobEmail.text
        val phno = formJobPhNo.text
        val active = true
        var gender = 1
        if (formGender.checkedRadioButtonId == R.id.male) {
            gender = 1
        } else
            gender = 0

        val hours = Integer.parseInt(formHour.text.toString())
        val days = Integer.parseInt(formDays.text.toString())
        val weeks = Integer.parseInt(formWeeks.text.toString())
        val startDate = formStartDate.text
        val endDate = formEndDate.text
        val importantNotes = importantNotesItem.text
        val requiredSkill = requireSkillsIteem.text
        val tag = tagItem.text
        val postCount = availablePostCount.text

        var newData = MMKunyiResponse()
        newData.availablePostCount = Integer.parseInt(postCount.trim().toString())
        newData.email = MMKuNyiModel.getInstance().mFirebaseUser!!.email
        newData.genderForJob = gender
        newData.email = email.toString()
        newData.phoneNo = phno.toString()
        newData.fullDesc = fullDescc.toString()
        newData.shortDesc = shortDesc.toString()
        newData.location = location.toString()
        newData.jobPostId = (System.currentTimeMillis() / 1000 ).toString().toInt()
        newData.isActive = active


        val jobDetails = JobDurationVO()
        jobDetails.jobStartDate = startDate.toString()
        jobDetails.jobEndDate = endDate.toString()
        jobDetails.totalWorkingDays = (weeks * days)
        jobDetails.workingHoursPerDay = hours
        newData.jobDuration = jobDetails

        val offerAmount = OfferAmountVO()
        offerAmount.amount = amount.toString().toInt()
        offerAmount.duration = "per day"
        newData.offerAmount = offerAmount

        val listJobTags = ArrayList<JobTagsVO>()
        val jobTagsVO = JobTagsVO()
        jobTagsVO.desc = title.toString()
        jobTagsVO.jobCountWithTag = 122
        jobTagsVO.tag = tag.toString()
        jobTagsVO.tagId = 122
        listJobTags.add(jobTagsVO)
        newData.jobTags = listJobTags

        val listNotes = ArrayList<String>()
        listNotes.add(importantNotes.toString())
        newData.importantNotes = listNotes

        val listREquiredSkills = ArrayList<RequiredSkillVO>()
        val Rskills = RequiredSkillVO()
        Rskills.skillId = 122
        Rskills.skillName = requiredSkill.toString()
        listREquiredSkills.add(Rskills)
        newData.requiredSkill = listREquiredSkills

        return newData
    }

    override fun postToFireBaseDB( data : MMKunyiResponse) {
        MMKuNyiModel.getInstance().addNewJob(data)

    }

}
