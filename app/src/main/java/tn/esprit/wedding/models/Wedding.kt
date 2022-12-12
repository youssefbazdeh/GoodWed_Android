package tn.esprit.wedding.models

import java.util.*

data class Wedding(
    var _id:String?=null,
    var fullname: String? = null,
    var partner_fullname: String?= null,
    var genre: String? = null,
    var partner_sexe: String? = null,
    var partner_email: String? = null,
    var wedding_name: String? = null,
    var date_ceremonie : Date,
    var heure_ceremonie: Date,
    var budget: Int=0,
    var image: String? = null,
    var __v:Int=0
)
data class ResponseWedding (
    var _id:String?=null,
    var fullname: String? = null,
    var partner_fullname: String?= null,
    var genre: String? = null,
    var partner_sexe: String? = null,
    var partner_email: String? = null,
    var wedding_name: String? = null,
    var date_ceremonie : Date,
    var heure_ceremonie: Date,
    var budget: Int,
    var image: String? = null,
    var __v:Int
)
data class addResponse (var message: String? = null,
                          var wedding:ResponseUser?=null)