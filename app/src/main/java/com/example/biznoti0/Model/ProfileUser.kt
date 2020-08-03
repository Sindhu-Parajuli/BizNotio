package com.example.biznoti0.Model


class ProfileUser {

    private var ACType:String = ""

    private var FName:String = ""

    private var LName:String = ""

    private var usersID:String = ""

    private var Image:String = ""

    private var profileImageUrl: String = ""

    private var Email: String = ""

    private var MName: String = ""



    constructor()

    constructor(ACType:String, FName:String, LName:String, usersID:String, Image:String, profileImageUrl: String, Email: String, MName: String)
    {
        this.ACType = ACType
        this.FName = FName
        this.LName= LName
        this.usersID=usersID
        this.Image=Image
        this.profileImageUrl = profileImageUrl
        this.Email = Email
        this.MName = MName
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

    fun getProfileImage(): String {
        return profileImageUrl.toString()
    }

    fun getMName(): String {
        return MName
    }

    fun getEmail(): String {
        return Email
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

    fun setProfileImage(profileImageUrl: String) {
        this.profileImageUrl = profileImageUrl
    }

    fun setEmail(email: String) {
        this.Email = email
    }
    fun setMName(mName: String) {
        this.MName = mName
    }

    override fun toString(): String{
        return "Email=$Email, LName=$LName, usersID=$usersID, MName=$MName, Image=$Image, profileImageUrl=$profileImageUrl, FName=$FName, ACType=$ACType"
    }



}