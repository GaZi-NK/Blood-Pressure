package com.example.redma.bloodpressure

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import android.text.format.DateFormat

//1つのデータをone_result.xmlでから生成したビューに設定
class CustomRecyclerViewAdapter(realmResults: RealmResults<BloodPress>) : RecyclerView.Adapter<ViewHolder>() {
    private val rResults: RealmResults<BloodPress> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //ビューを作成
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.one_result, parent, false)
        //ビューホルダーを作成
        val viewholder = ViewHolder(view)
        //ビューホルダーを返す
        return viewholder
    }

    override fun getItemCount(): Int {
        return rResults.size
    }

    //one_result.xmlに現在のレコードを表示させる処理
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //ここで現在のレコードの情報を取得
        val bloodPress = rResults[position]
        //フォーマットを整形して表示
        holder.dateText?.text = DateFormat.format("yyyy/MM/dd kk:mm" , bloodPress?.dateTime)
        holder.minMaxText?.text = "${bloodPress?.max.toString()}/${bloodPress?.min.toString()}"
        holder.pulseText?.text = bloodPress?.pulse.toString()
        holder.itemView.setBackgroundColor(if (position % 2 == 0) Color.LTGRAY else Color.WHITE)
        //レコードがタップされたら編集画面に行くよう処理
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, EditActivity::class.java)
            intent.putExtra("id", bloodPress?.id) //情報としてidを追加
        }

    }

}