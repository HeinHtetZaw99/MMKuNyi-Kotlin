package com.daniel.user.mmkunyi.data.vos

import com.google.gson.annotations.SerializedName

class InterestedVO {
    @SerializedName("interestedTimeStamp")
    var timestamp: String? = ""
    var seekerId: String? = ""
    var seekerName: String? = ""
    var seekerProfliePicUrl: String? = ""
    var seekerSkill: List<SeekerSkillVO>? = null

}
