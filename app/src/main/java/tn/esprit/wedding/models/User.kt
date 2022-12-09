package tn.esprit.wedding.models

import java.util.Date

data class User(
    var _id:String?=null,
    var username: String? = null,
    var fullname: String?= null,
    var email: String? = null,
    var role: String? = null,
    var datedenaissance: Date,
    var password: String? = null,
    var __v:Int=0
)
data class ResponseUser (
    var _id:String?=null,
    var fullname: String?= null,
    var username: String? = null,
    var email: String? = null,
    var role: String? = null,
    var datedenaissance: Date,
    var password: String? = null,
    var __v:Int
)
data class loginResponse (var message: String? = null,
                          var user:ResponseUser?=null)