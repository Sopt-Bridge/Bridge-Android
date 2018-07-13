package com.cow.bridge.home.activity.type


import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cow.bridge.R
import com.cow.bridge.home.adapter.OtherAdapter
import com.cow.bridge.network.ApplicationController
import com.cow.bridge.network.Network
import com.cow.bridge.network.ServerInterface
import com.cow.bridge.util.UtilController
import com.google.gson.Gson
import com.skydoves.powermenu.MenuAnimation
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.android.synthetic.main.fragment_other.*
import kotlinx.android.synthetic.main.fragment_other.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 * Use the [OtherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtherFragment : Fragment() {

    private var pageName: String? = null
    var api : ServerInterface? = null
    var otherAdapter : OtherAdapter? = null
    var powerMenu : PowerMenu? = null
    var orderBy : String = "Upload date"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            pageName = arguments!!.getString(pageNameParam)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val convertView = inflater!!.inflate(R.layout.fragment_other, container, false)
        api = ApplicationController.instance?.buildServerInterface()

        powerMenu = PowerMenu.Builder(context)
                .addItem(PowerMenuItem("Upload date", false))
                .addItem(PowerMenuItem("View count", false))
                .addItem(PowerMenuItem("Rating", false))
                .setDividerHeight(UtilController.convertDpToPixel(1f, this!!.context!!).toInt())
                .setDivider(resources.getDrawable(R.drawable.line_rect_1dp_e4e4e4))
                .setAnimation(MenuAnimation.SHOWUP_TOP_RIGHT)
                .setWith(UtilController.convertDpToPixel(150f, this!!.context!!).toInt())
                .setMenuRadius(16f)
                .setMenuShadow(8f)
                .setTextColor(Color.parseColor("#333333"))
                .setMenuColor(Color.parseColor("#FFFFFF"))
                .setOnMenuItemClickListener(onMenuItemClickListener())
                .build()

        with(convertView){
            other_layout_orderby.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    powerMenu?.showAsDropDown(other_layout_orderby, UtilController.convertDpToPixel(15f, context).toInt(), 0)

                }

            })

            otherAdapter = OtherAdapter(context)

            val llm : RecyclerView.LayoutManager = GridLayoutManager(context, 2)
            other_recycler.layoutManager = llm
            other_recycler.adapter = otherAdapter
            orderBy = "Upload date"
            getContentsList(pageName,"Upload date")

            var scrollFlag = 0
            var page= 1
            other_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
                override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    Log.v("test", "${v?.getChildAt(0)?.height!!- v?.height!! - scrollY} ")
                    if(v?.getChildAt(0)?.height!!- v?.height!! - scrollY <=200){
                        if(scrollFlag!=2){
                            scrollFlag = 1
                        }else{

                        }
                        if(scrollFlag==1){
                            scrollFlag=2
                            getContentsList(pageName, orderBy, page)
                            page++

                        }else{

                        }
                    }
                }
            })
        }

        return convertView
    }

    private fun onMenuItemClickListener() = OnMenuItemClickListener<PowerMenuItem>(){ position: Int, powerMenuItem: PowerMenuItem ->
        if(position==0){
            other_text_orderby.text = "Upload date"
            orderBy = "Upload date"
            getContentsList(pageName, "Upload date")
        }else if(position==1){
            other_text_orderby.text = "View count"
            orderBy = "View count"
            getContentsList(pageName, "View count")
        }else if(position==2){
            other_text_orderby.text = "Rating"
            orderBy = "Rating"
            getContentsList(pageName, "Rating")
        }

        powerMenu?.dismiss()

    }

    companion object {
        private var pageNameParam : String? = null

        fun newInstance(param1: String): OtherFragment {
            val fragment = OtherFragment()
            val args = Bundle()
            args.putString(pageNameParam, param1)
            fragment.arguments = args
            return fragment
        }
    }

    fun getContentsList(type : String?, orderby : String?, page : Int = 0){
        var messagesCall : Call<Network>? = null
        var category : Int = 0
        if(type.equals("k-content")){
            category = 1
        }else if(type.equals("k-pop")){
            category = 2
        }else if(type.equals("fun")){
            category = 3
        }else if(type.equals("culture")){
            category = 4
        }else{
            category = 0
        }

        if(orderby.equals("Upload date")){
            messagesCall = api?.recentContentsList(category, page)
        }else if(orderby.equals("View count")){
            messagesCall = api?.hitsortContentsList(category, page)
        }else if(orderby.equals("Rating")){
            messagesCall = api?.likesortContentsList(category, page)
        }else{
            messagesCall = api?.recentContentsList(category, page)
        }


        messagesCall?.enqueue(object : Callback<Network> {
            override fun onResponse(call: Call<Network>?, response: Response<Network>?) {
                var network = response!!.body()
                if(network?.message.equals("ok")){
                    network.data?.get(0)?.contents_list?.let {
                        if(it.size!=0){
                            if(page==0){
                                otherAdapter?.clear()
                            }
                            otherAdapter?.addAll(it)
                            otherAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<Network>?, t: Throwable?) {

            }
        })
    }

}// Required empty public constructor
