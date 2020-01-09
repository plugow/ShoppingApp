package com.plugow.shoppingapp

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.junit.Test

class MyTestsKotlin {

    @Test
    fun `check doOnSubscribe invoke thread`() {
        Observable.fromCallable {
            34556
        }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Observable.fromCallable {
                    128
                }
                    .doOnSubscribe {
                        println("doOnsubscribe            ${Thread.currentThread().name}")
                    }
                    .doFinally {
                        println("do finaly                    ${Thread.currentThread().name}")
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val a = 4
                    }
            }

    }

    @Test
    fun testSubjecta() {
        val s = BehaviorSubject.createDefault("default")
        s.subscribe {
            val a=it
        }

        s.onNext("Dupa")

        s.subscribe {
            val a = it
        }
    }

    @Test
    fun test(){
        val a = "coś gługiepo"
        print(a.reversed())
    }

    @Test
    fun withoutReversed(){
        val a = "coś gługiepo"
        for (i in a.length-1 downTo 0){
            print(a[i])
        }
    }

    @Test
    fun countsWords(){
        val a = "cos bardzo glupiego i coś bardzo dziwnego"
        val words = a.split(" ")
        val map = HashMap<String, Int>()
        for (word in words){
            if (map.containsKey(word)){
                val count = map[word]
                map[word] = count!!.plus(1)
            } else{
                map[word]=1
            }
        }

        print(map)
    }

    fun isPrime(a:Int):Boolean{
        if (a<=1) return false
        else{
            for (i in 2 until Math.sqrt(a.toDouble()).toInt()){
                if (a%i==0){
                    return false
                }
            }
            return true
        }
    }
}