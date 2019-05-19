package com.plugow.shoppingapp.ui.adapter

enum class RecyclerClickType : ClickType {
    MAIN,
    REMOVE,
    ARCHIVE
}

enum class ProductClickType : ClickType {
    ADD,
    SUBSTRACT,
    CHECK
}

enum class SearchClickType : ClickType {
    ADD,
    REMOVE
}

interface ClickType