package tn.esprit.wedding.models

import java.util.*

data class Guest (
    var _id : String ,
    var name : String? = null,
    var lastname : String?= null,
    var sexe : String? = null,
    var groupe : String? = null,
    var phone : Int? = null,
    var email : String? = null,
    var adresse : String? =null,
    var note : String? = null,
    var user_id: String? = null,
    var __v:Int=0
)