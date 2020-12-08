package com.example.brandmarktask.data

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}