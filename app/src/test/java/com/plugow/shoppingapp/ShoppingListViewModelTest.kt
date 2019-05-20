package com.plugow.shoppingapp

import android.content.Context
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.plugow.shoppingapp.db.AppRepo
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.event.RxBus
import com.plugow.shoppingapp.event.ShoppingListEvent
import com.plugow.shoppingapp.util.Event
import com.plugow.shoppingapp.viewModel.ShoppingListViewModel
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import junit.framework.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.Rule
import java.util.concurrent.Executor


class ShoppingListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val immediateScheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    @Mock
    lateinit var repo: AppRepo

    @Mock
    lateinit var ctx: Context

    @Mock
    lateinit var rxBus: RxBus

    @Mock lateinit var observer:Observer<Event<ShoppingListEvent>>
    lateinit var viewModel: ShoppingListViewModel

    @Before
    fun setUp(){
        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        MockitoAnnotations.initMocks(this)
        viewModel = ShoppingListViewModel(repo, ctx, rxBus)
    }


    @Test
    fun `show error when getEpisodes() returning error`() {
        val error = "Test error"
        val single: Single<List<ShoppingList>> = Single.create {
            emitter ->
            emitter.onError(Exception(error))
        }

        whenever(repo.getShoppingList()).thenReturn(single)
        viewModel.event.observeForever(observer)
        viewModel.loadItems()
        assertEquals(viewModel.event.value?.peekContent(),ShoppingListEvent.ERROR)

    }

    @Test
    fun `validate loading data`(){
        val list = listOf(ShoppingList(name = "test"), ShoppingList(name = "test2"))
        val single: Single<List<ShoppingList>> = Single.create {
            emitter ->
            emitter.onSuccess(list)
        }
        whenever(repo.getShoppingList()).thenReturn(single)
        viewModel.loadItems()
        assertEquals(viewModel.items.value,list)
    }



    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }


}