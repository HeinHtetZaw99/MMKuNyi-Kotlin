package com.daniel.user.mmkunyi.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.daniel.user.mmkunyi.R
import com.daniel.user.mmkunyi.data.MMKuNyiModel
import com.daniel.user.mmkunyi.data.vos.ApplicantVO
import com.daniel.user.mmkunyi.data.vos.SeekerSkillVO
import com.daniel.user.mmkunyi.data.vos.WhyShouldHireVO
import com.daniel.user.mmkunyi.utils.AppConstants
import kotlinx.android.synthetic.main.activity_apply_form.*
import java.time.format.DateTimeFormatter

class ApplyFormActivity : AppCompatActivity() {

    lateinit var userName : String
    lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_form)

        userName = MMKuNyiModel.getInstance().mFirebaseUser!!.displayName.toString()
        val jobId = intent.getStringExtra(AppConstants.JOB_ID)
        applyName.setText(userName )
        val array = arrayListOf( whyRevelant)
        applyFormButton.setOnClickListener{
            userId = MMKuNyiModel.getInstance().mFirebaseUser!!.uid
            Log.e("MMKUNYI" , "userId " + userId)
            if (ApplicationFormActivity.checkAllPlaceHolders(array)){
                val londMilis = System.currentTimeMillis()
                val date = java.sql.Date(londMilis)
                MMKuNyiModel.getInstance().addNewApplicant(jobId.toInt() , prepareData(date.toString()))
                Toast.makeText(this , " Successfully Applied", Toast.LENGTH_SHORT).show()
                finish()
            }
            else
                Toast.makeText(this, "Error in Applying this job . Please Try Again" , Toast.LENGTH_LONG).show()

        }
    }

    private fun prepareData(timeStamp : String): ApplicantVO {
        val revalant = whyRevelant.text.toString()
        val skill = SeekerSkillVO.initSeekerSkillVO(userName , 123)
        val whyShouldHire = WhyShouldHireVO.initWhyShouldHire(revalant,123,timeStamp)
        val skillList = ArrayList<SeekerSkillVO>()
        skillList.add(skill)
        val whyHireList = ArrayList<WhyShouldHireVO>()
        whyHireList.add(whyShouldHire)
        var canLower = false
        canLower = applyCanLower.checkedRadioButtonId == R.id.yesLower
        val data = ApplicantVO.initApplicantVO(userName ,canLower ,timeStamp,MMKuNyiModel.fakeUserId,skillList,whyHireList)
        return data
    }


}
