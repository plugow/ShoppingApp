package com.plugow.shoppingapp.domain

import android.content.Context
import com.plugow.shoppingapp.R
import com.plugow.shoppingapp.db.LocalRepository
import com.plugow.shoppingapp.db.model.ShoppingList
import com.plugow.shoppingapp.domain.base.CompletableUseCase
import com.plugow.shoppingapp.util.SchedulerProvider
import io.reactivex.Completable
import javax.inject.Inject

class AddListUseCase @Inject constructor(
    private val ctx: Context,
    private val localRepository: LocalRepository,
    schedulerProvider: SchedulerProvider
) : CompletableUseCase<String>(schedulerProvider) {
    override fun buildUseCaseCompletable(params: String?): Completable {
        checkNotNull(params)
        val name = if (params == "") ctx.getString(R.string.new_list) else params
        val newList = ShoppingList(name = name)
        return localRepository.insertList(newList)
    }
}
