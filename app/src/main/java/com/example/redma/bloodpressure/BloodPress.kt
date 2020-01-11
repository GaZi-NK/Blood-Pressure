package com.example.redma.bloodpressure

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
//モデルクラス⇒ここにデータの項目とかを定義する(Realmのテーブル)⇒モデルクラスはRealmObjectaを継承
open class BloodPress : RealmObject(){
    //@Primarykeyの後にカラムを定義
    @PrimaryKey
    var id: Long = 0
    var dateTime: Date = Date()
    var max: Long = 0
    var min: Long = 0
    var pulse: Long = 0
}