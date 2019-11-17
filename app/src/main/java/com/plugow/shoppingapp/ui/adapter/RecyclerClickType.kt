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

enum class ArchiveClickType : ClickType {
    RESTORE,
    REMOVE
}

interface ClickType
