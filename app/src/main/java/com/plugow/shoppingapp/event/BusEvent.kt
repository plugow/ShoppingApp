package com.plugow.shoppingapp.event

sealed class BusEvent {
    object RefreshProducts : BusEvent()
    object RefreshShoppingLists : BusEvent()
    object RefreshArchivedLists : BusEvent()
    class RefreshShoppingList(val shoppingListId:Int) : BusEvent()
}