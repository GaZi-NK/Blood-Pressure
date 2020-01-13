package com.example.redma.bloodpressure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {
    private val fag = "BloodPressure"
    private lateinit var  realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        //レルムインスタンスを取得⇒値を保存するため
        realm = Realm.getDefaultInstance()

        //intentの付加情報を取得⇒デフォルト値は0に設定
        val bpId = intent.getLongExtra("id", 0L)
        //0以上であればすでに記録されている情報をEditTextに表示
        if (bpId > 0L) {
            //intentから送られた同じidを持つフィールドを取得
            val bloodPress = realm.where<BloodPress>()
                .equalTo("id", bpId).findFirst()
            maxEdit.setText(bloodPress?.max.toString()) //値が入ってなかった時のためboloodPress?にする
            minEdit.setText(bloodPress?.min.toString())
            pulseEdit.setText(bloodPress?.pulse.toString())
            //削除ボタンを表示させる処理
            deleteBtn.visibility = View.VISIBLE
        }else{
            deleteBtn.visibility = View.INVISIBLE
        }


        //保存ボタンクリック時の動作
        saveBtn.setOnClickListener{
            //まず各項目の変数を用意し初期化
            var max: Long = 0
            var min: Long = 0
            var pulse : Long = 0

            //各EditTextの値が空でないときの処理
            if (!maxEdit.text.isNotEmpty()){
                //初期化しておいた変数に値を代入以下のif分の処理も同様
                max = maxEdit.text.toString().toLong()
            }
            if (!minEdit.text.isNotEmpty()){
                min = minEdit.text.toString().toLong()
            }
            if (!pulseEdit.text.isNotEmpty()){
                pulse = pulseEdit.text.toString().toLong()
            }


            when(bpId){
                0L -> {

                    //レルムでは更新(追加、変更、削除)はか鳴らすトランザクションの中で行なわなければならない
                    //とりあえずexecutionTranzaction使えばいろいろ一発でできるみたい
                    realm.executeTransaction {
                        //whereメソッドでクエリを作成⇒idの最大を取得して格納
                        val maxId = realm.where<BloodPress>().max("id")
                        //新規レコードIDを取得
                        val nextId = (maxId?.toLong() ?: 0L) + 1L
                        /*
                モデルクラスとプライマリキーを指定してインスタンス生成
                一番最初に登録したらモデル＜Blood Press＞の（1）のインスタンス
                二番目に登録したらモデル＜Blood Press＞の（2）のインスタンス
                という風にレコードの数ごとにモデルのインスタンスが作成されていく
                 */
                        val bloodPress = realm.createObject<BloodPress>(nextId)
                        bloodPress.dateTime = Date()
                        bloodPress.max = max
                        bloodPress.min = min
                        bloodPress.pulse = pulse
                    }
                }

                else -> {
                    realm.executeTransaction{
                        val bloodPress = realm.where<BloodPress>()
                            .equalTo("id", bpId).findFirst()
                        bloodPress?.max = max
                        bloodPress?.min = min
                        bloodPress?.pulse = pulse
                    }
                }
            }
            //3秒間ほどポップアップで”保存しました”と表示されだんだん薄くなってきえていくやつの処理⇒なくてもいいけどあったら親切
            Toast.makeText(applicationContext,"保存しました",Toast.LENGTH_SHORT).show()
            finish()
        }

        //削除ボタンクリック時の動作
        deleteBtn.setOnClickListener{
            realm.executeTransaction{
                val bloodPress = realm.where<BloodPress>()
                    .equalTo("id", bpId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }
            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //アプリ終了時にはレルムのインスタンスを閉じる
        realm.close()
    }

}
