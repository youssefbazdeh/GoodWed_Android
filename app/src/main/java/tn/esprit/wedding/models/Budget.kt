package tn.esprit.wedding.models

data class Budget (

    var _id : String ,
    var nom : String ,
    var categorie : String,
    var montant : Int,
    var note : String,
    var user_id: String,
    var __v:Int=0

)