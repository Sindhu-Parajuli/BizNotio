package com.example.biznoti0.Model


class ProfileUser {

private var ACType:String = ""

    private var FName:String = ""

    private var LName:String = ""

    private var usersID:String = ""

    private var Image:String = ""

    constructor()

    constructor(ACType:String, FName:String, LName:String, usersID:String, Image:String)
    {
        this.ACType = ACType
        this.FName = FName
        this.LName= LName
        this.usersID=usersID
        this.Image=Image

    }

    fun getACType():String {
        return ACType
    }


    fun getFNAME():String {
        return FName
    }

    fun getLName():String {
        return LName
    }

    fun getusersID():String {
        return usersID
    }

    fun getImage():String {
        return Image
    }



    fun setACType(ACType: String) {
        this.ACType = ACType
    }

    fun setFName(FName: String) {
        this.FName = FName
    }

    fun setLName(LName: String) {
        this.LName = LName
    }

    fun setusersID(usersID: String) {
        this.usersID = usersID
    }

    fun setImage(Image: String) {
        this.Image = Image
    }


}