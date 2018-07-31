package com.daniel.user.mmkunyi.data.vos

class ApplicantVO {
    var appliedDate: String? = ""
    var canLowerOfferAmount: Boolean = false
    var seekerId: Int? = 1
    var seekerName: String? = ""
    var seekerProfilePicUrl: String? = ""
    var seekerSkill: List<SeekerSkillVO>? = null
    var whyShouldHire: List<WhyShouldHireVO>? = null
}
