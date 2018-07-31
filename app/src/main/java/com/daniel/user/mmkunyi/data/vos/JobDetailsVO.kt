package com.daniel.user.mmkunyi.data.vos

class JobDetailsVO(var jobEndDate: String? = "",
                   var jobStartDate: String? = "",
                   var totalWorkingDays: Int = 10,
                   var workingDaysPerWeek: Int = 3,
                   var workingHoursPerDay: Int = 2)
