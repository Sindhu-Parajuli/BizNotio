package com.example.biznoti0.Model


class ProfileUser {

private var ACType:String = ""

    private var FName:String = ""

    private var LName:String = ""

    private var usersID:String = ""

    private var Image:String = ""

    private var Profession: String = ""

    private var Education: String = ""

    private var BizNotioGoals: String = ""

    private var Interests: String = ""

    constructor()

    constructor(ACType:String, FName:String, LName:String, usersID:String, Image:String, Profession:String, Education:String, BizNotioGoals:String, Interests:String)
    {
        this.ACType = ACType
        this.FName = FName
        this.LName= LName
        this.usersID=usersID
        this.Image=Image
        this.BizNotioGoals=BizNotioGoals
        this.Education = Education
        this.Interests=Interests
        this.Profession= Profession

    }

    fun getACType():String {
        return ACType
    }

    fun getProfession():String {
        return Profession
    }

    fun getEducation():String {
        return Education
    }

    fun getInterests():String {
        return Interests
    }

    fun getBizNotioGoals():String {
        return BizNotioGoals
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

    fun setProfession(Profession: String) {
        this.Profession = Profession
    }

    fun setBizNotioGoals(BizNotioGoals: String) {
        this.BizNotioGoals = BizNotioGoals
    }

    fun setEducation(Education: String) {
        this.Education = Education

    }

    fun setInterests(Interests: String) {
        this.Interests = Interests
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