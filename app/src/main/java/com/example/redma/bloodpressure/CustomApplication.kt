package com.example.redma.bloodpressure

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

//Realmを初期化するクラス⇒この処理はアプリ起動時に実行する必要がある
//ApplicationクラスのOnCreateメソッドはメインアクティビティより先に呼び出される⇒マニュフェストファイルにnameタグの設定が必要
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //Realmライブラリの初期化⇒アプリ起動時に実行する必要がある
        Realm.init(this)
        //Realmの構成を作成
        val config = RealmConfiguration.Builder().build()
        //作成した構成をデフォルトとして設定
        Realm.setDefaultConfiguration(config)

    }
}