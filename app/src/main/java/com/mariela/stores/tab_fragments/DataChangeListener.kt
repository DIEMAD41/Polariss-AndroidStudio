package com.mariela.stores.tab_fragments

interface DataChangeListener {
    fun onDataLoaded()
    fun onDataLoadError(errorMessage: String)
}