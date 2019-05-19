package com.plugow.shoppingapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.di.util.Event
import com.plugow.shoppingapp.event.BusEvent
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.event.SearchEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(val repo: AppRepo, val ctx:Context, val rxBus: RxBus): ViewModel(), RefreshableList<SearchItem> {
    val mEvent:MutableLiveData<Event<SearchEvent>> = MutableLiveData()
    val event : LiveData<Event<SearchEvent>>
        get() = mEvent
    var selectedItems = arrayListOf<SearchItem>()
    var shoppingListId:Int = 0
    val searchbarSubject by lazy {
        PublishSubject.create<String>()
    }
    var searchBar:MutableLiveData<String> = MutableLiveData()

    lateinit var mItems:List<SearchItem>
    override var items: MutableLiveData<List<SearchItem>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var customItemVisibility = MutableLiveData(false)
    var isCustomItemSelected = MutableLiveData(false)

    private val disposables= CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun loadItems() {
        repo.getSearchItems()
            .subscribeBy(
                onSuccess = {
                    mItems = it
                    items.value = mItems
                }
            ).addTo(disposables)
    }

    fun initValues(){
        loadItems()
        searchbarSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                filterList(it)
            }.addTo(disposables)
    }

    fun filterList(searchText:String){
        val temp = mItems.filter { it.name.toLowerCase().contains(searchText as CharSequence, true) }
        items.postValue(temp)
    }


    override fun onRecyclerClick(type:ClickType, pos:Int){
        when(type){
            SearchClickType.ADD -> {
                items.value?.get(pos)?.let { selectedItems.add(it) }
            }
            SearchClickType.REMOVE -> {
                items.value?.get(pos)?.let { selectedItems.remove(it) }
            }
        }
    }

    fun onReadyClick(){
        if (selectedItems.size!=0){
            repo.addProducts(selectedItems, shoppingListId)
                .subscribeBy(
                    onSuccess = {
                        mEvent.value = Event(SearchEvent.DISMISS)
                        rxBus.emitEvent(BusEvent.REFRESH_PRODUCTS)
                    },
                    onError = {
                        ctx.toast(R.string.wrong)
                        mEvent.value = Event(SearchEvent.DISMISS)
                    }
                )
        } else {
            mEvent.value = Event(SearchEvent.DISMISS)
        }
    }

    fun onSearchTextChanged(){
        searchBar.value?.let {
            searchbarSubject.onNext(it)
            customItemVisibility.value = it.trim() != ""
        }
    }

    fun onCustomItemClicked(){
        isCustomItemSelected.value = !isCustomItemSelected.value!!
        if (isCustomItemSelected.value!!){
            selectedItems.add(SearchItem(name = searchBar.value!!, isChosen = true))
        } else {
            selectedItems.removeAll {it.name == searchBar.value!!}
        }
    }


}