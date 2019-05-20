package com.plugow.shoppingapp.event

sealed class BusEvent {
    object RefreshProducts : BusEvent()
    class RefreshShoppingList(val shoppingListId:Int) : BusEvent()
}