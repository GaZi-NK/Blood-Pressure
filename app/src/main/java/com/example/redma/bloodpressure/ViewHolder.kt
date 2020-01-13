package com.example.redma.bloodpressure

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_result.view.*

//1データ分のビューの参照を保持するクラス
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var dateText: TextView? = null
    var minMaxText: TextView? = null
    var pulseText: TextView? = null

    //javaでいうコンストラクタ
    init {
        //ビューホルダーのプロパティとレイアウトのViewの対応
        dateText = itemView.dateText
        minMaxText = itemView.minMaxtText
        pulseText = itemView.pulseText

    }
}