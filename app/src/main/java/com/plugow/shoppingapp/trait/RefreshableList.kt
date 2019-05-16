package com.plugow.shoppingapp.trait

import androidx.lifecycle.MutableLiveData

interface RefreshableList<T> {
    var items:MutableLiveData<List<T>>
    var isLoadingRefresh:MutableLiveData<Boolean>


    fun loadItems()
    fun getItems(){
        if(items.value==null){
            isLoadingRefresh.value=true
            loadItems()
        }
    }

    fun onRefreshItems(){
        loadItems()
    }


}