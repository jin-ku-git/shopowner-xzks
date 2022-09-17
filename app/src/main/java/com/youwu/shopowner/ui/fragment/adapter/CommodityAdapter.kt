package com.youwu.shopowner.ui.fragment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.youwu.shopowner.R
import com.youwu.shopowner.ui.fragment.bean.XXCOrderBean


/**
 * 订单商品适配器
 * @author qdafengzi
 */
class CommodityAdapter(private val mContext: Context, private val mList: List<XXCOrderBean.GoodsListBean>?, private var mPosition: Int) : RecyclerView.Adapter<CommodityAdapter.ViewHolder>() {

    /**
     * 新增方法
     *
     * @param position 位置
     */
    fun setPosition(position: Int) {
        mPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shangpin, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.CommodityName.text = mList!![position].goods_name
        holder.CommodityPrice.text = ""+mList!![position].goods_price
        holder.CommodityNum.text = "x"+mList!![position].goods_quantity


    }


    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var CommodityName: TextView
        var CommodityPrice: TextView
        var CommodityNum: TextView



        init {
            CommodityName = view.findViewById(R.id.CommodityName)
            CommodityPrice = view.findViewById(R.id.CommodityPrice)
            CommodityNum = view.findViewById(R.id.CommodityNum)


//            ButterKnife.inject(this, view)

        }
    }


    /**
     * 设置item的监听事件的接口
     */
    interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        fun OnItemClick(view: View?, data: XXCOrderBean.GoodsListBean?)
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


}