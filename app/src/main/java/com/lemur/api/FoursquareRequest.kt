package com.lemur.api

class FoursquareRequest {
    var response: FoursquareResponse?=null
}

class  FoursquareResponse{
    var venues:ArrayList<Venue>? = null
}

class Venue{
    var categories:ArrayList<Category>?=null
    var location:Locations?=null
    var name:String = ""

}
class Category{
    var id:String=""
    var name:String=""
    var icon:Icon?=null
}

class Locations{

    var lat: Float = 0.0f
    var lng: Float = 0.0f
    var address: String = ""
}

class Icon{
    var prefix:String=""
    var suffix:String=""
}