package com.cow.bridge.login.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
<<<<<<< HEAD
import android.view.ViewGroup
import android.widget.TextView
<<<<<<< HEAD
import com.cow.bridge.R
=======
>>>>>>> mypage
import java.util.ArrayList
=======
import com.cow.bridge.R
import com.cow.bridge.login.adapter.MypageAdapter
import com.cow.bridge.model.MypageItems
import java.util.*
>>>>>>> d4cf65aa9e142649185e1973542ac8b5ef726407


class MypageActivity : AppCompatActivity() {

<<<<<<< HEAD

    lateinit var toolbar: Toolbar
    lateinit var mypageItems : ArrayList<MypageItems>
    lateinit var myAdapter: MypageAdapter
=======
    lateinit var toolbar: Toolbar

>>>>>>> mypage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< HEAD
<<<<<<< HEAD
        setContentView(R.layout.mypage_activity)
=======
        setContentView(R.layout.activity_mypage)
>>>>>>> d4cf65aa9e142649185e1973542ac8b5ef726407
        toolbar = findViewById<View>(R.id.mypage_toolbar) as Toolbar //툴바설정

        val recyclerView = findViewById<View>(R.id.mypage_write) as RecyclerView

        mypageItems = ArrayList()
        mypageItems.add(MypageItems("[Requests] 번역 오역 요청", "2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 영상 문의", "2018.01.03"))
        mypageItems.add(MypageItems("[Requests] 오류 문의", "2018.07.05"))


        myAdapter = MypageAdapter(mypageItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
        recyclerView.addItemDecoration(ItemDecoration())

    }
<<<<<<< HEAD

    inner class MypageAdapter(var mypageItems: ArrayList<MypageItems>)
        : RecyclerView.Adapter<MypageViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewtype: Int): MypageViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.mypage_item, parent, false)
=======
        setContentView(R.layout.activity_mypage)
        toolbar = findViewById<View>(R.id.mypage_toolbar) as Toolbar //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#000000"))
        setSupportActionBar(toolbar)//액션바와 같게 만들어줌

        val recyclerView = findViewById<View>(R.id.mypage_write) as RecyclerView
        val list = ArrayList<String>()
        for (i in 0..19) {
            list.add("Item =$i")
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MypageAdapter(list)


    }

    private inner class MypageAdapter(private val list: List<String>) : RecyclerView.Adapter<MypageViewHolder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MypageViewHolder {
            val view = LayoutInflater.from(viewGroup.context).inflate(android.R.layout.simple_list_item_1, viewGroup, false)
>>>>>>> mypage
            return MypageViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: MypageViewHolder, position: Int) {
<<<<<<< HEAD

            viewHolder.mypageWriting.text = mypageItems[position].writing
            viewHolder.mypageDate.text = mypageItems[position].date
=======
            val text = list[position]
            viewHolder.title.text = text
>>>>>>> mypage
        }


        override fun getItemCount(): Int {
<<<<<<< HEAD
            return mypageItems.size
=======
            return list.size
>>>>>>> mypage
        }
    }

    inner class MypageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
<<<<<<< HEAD

        var mypageWriting : TextView = itemView!!.findViewById<TextView>(R.id.writing)
        var mypageDate : TextView = itemView!!.findViewById<TextView>(R.id.date)

    }
=======
>>>>>>> d4cf65aa9e142649185e1973542ac8b5ef726407
}

=======
        var title: TextView

        init {
            title = itemView.findViewById<View>(android.R.id.text1) as TextView


        }

    }
}
>>>>>>> mypage
