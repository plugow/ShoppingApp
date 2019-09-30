package com.plugow.shoppingapp.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.dao.ProductDao
import com.plugow.shoppingapp.db.dao.SearchItemDao
import com.plugow.shoppingapp.db.model.SearchItem
import com.plugow.shoppingapp.util.Event
import com.plugow.shoppingapp.event.BusEvent
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.event.SearchEvent
import com.plugow.shoppingapp.trait.RefreshableList
import com.plugow.shoppingapp.ui.adapter.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val itemsDao: SearchItemDao,private val repo: AppRepo, private val productDao: ProductDao, private val ctx:Context, private val rxBus: RxBus) : ViewModel(), RefreshableList<SearchItem> {
    override var items: MutableLiveData<List<SearchItem>> = MutableLiveData()
    override var isLoadingRefresh: MutableLiveData<Boolean> = MutableLiveData(false)
    var customItemVisibility = MutableLiveData(false)
    var isCustomItemSelected = MutableLiveData(false)
    var searchBar:MutableLiveData<String> = MutableLiveData()
    var selectedItems = arrayListOf<SearchItem>()
    var shoppingListId:Int = 0
    private val searchbarSubject = PublishSubject.create<String>()
    private val disposables = CompositeDisposable()
    lateinit var mItems:List<SearchItem>
    private val mEvent:MutableLiveData<Event<SearchEvent>> = MutableLiveData()
    val event : LiveData<Event<SearchEvent>>
        get() = mEvent

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    override fun loadItems() {
        itemsDao.getItems()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    mItems = it
                    items.value = mItems
                    isLoadingRefresh.value = false
                },
                onError = {
                    it.printStackTrace()
                }
            ).addTo(disposables)
    }

    override fun initValues(id: Int) {
        super.initValues(id)
        searchbarSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                filterList(it)
            }.addTo(disposables)
    }

    private fun filterList(searchText:String){
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
                    onComplete = {
                        mEvent.value = Event(SearchEvent.DISMISS)
                        rxBus.emitEvent(BusEvent.RefreshProducts)
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
        isCustomItemSelected.value = false
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