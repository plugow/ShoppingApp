package com.plugow.shoppingapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.plugow.shoppingapp.viewModel.ArchivedListViewModel
import com.plugow.shoppingapp.viewModel.SearchViewModel
import com.plugow.shoppingapp.viewModel.ShoppingListDetailViewModel
import com.plugow.shoppingapp.viewModel.ShoppingListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ArchivedListViewModel::class)
    internal abstract fun bindArchivedListViewModel(viewModel: ArchivedListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListViewModel::class)
    internal abstract fun bindShoppingListViewModel(viewModel: ShoppingListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingListDetailViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: ShoppingListDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}
