package com.lemur.api

class FoursquareRequest {
    var response: FoursquareResponse?=null
}

class  FoursquareResponse{
    var venues:ArrayList<Venue>? = null
}

class Venue{
    var categories:ArrayList<Category>?=null
    var name:String = ""
}
class Category{
    var id:String=""
    var name:String=""
    var icon:Icon?=null
}

class Icon{
    var prefix:String=""
    var suffix:String=""
}