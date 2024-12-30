package com.taibasharif.crafty.Models.Repositories

import com.taibasharif.crafty.CloudinaryHelper

class StorageRepository {
    fun uploadFile(filePath:String,onComplete: (Boolean,String?) -> Unit){
        CloudinaryHelper().uploadFile(filePath,onComplete)
    }
}