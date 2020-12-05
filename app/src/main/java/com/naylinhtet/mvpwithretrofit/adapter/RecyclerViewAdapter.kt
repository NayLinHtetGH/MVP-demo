package com.naylinhtet.mvpwithretrofit.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.naylinhtet.mvpwithretrofit.R
import com.naylinhtet.mvpwithretrofit.data.model.DataModel
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = ArrayList<DataModel>()
    private var isLoadingAdded = false
    private val ITEM = 1
    private val LOADING = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ITEM ->
                viewHolder = getViewHolder(parent, inflater)
            LOADING -> {
                val v2 = inflater.inflate(R.layout.loading, parent, false)
                viewHolder = LoadingViewHolder(v2)
            }
        }
        return viewHolder!!
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val v1 = inflater.inflate(R.layout.data_layout, parent, false)
        viewHolder = ViewHolder(v1)
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            ITEM -> {
                holder as ViewHolder
                holder.titleText.text = "- "+dataList[position].author
                holder.bodyTest.text = "- "+dataList[position].description
                Picasso.get()
                    .load(dataList[position].urlToImage)
                    .fit()
                    .centerCrop()
                    //.placeholder(R.drawable.material_green_background)
                    //.error(R.drawable.material_green_background)
                    .into(holder.imageView)
            }
        }
    }

    private fun add(order: DataModel) {
        dataList.add(order)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAll(supplierList: List<DataModel>) {
        for (supplier in supplierList) {
            add(supplier)
        }
    }

    fun clear() {
        isLoadingAdded = false
        dataList.clear()
        this.notifyDataSetChanged()
    }

    fun isLoading(): Boolean {
        return isLoadingAdded
    }

    fun removeLoading() {
        isLoadingAdded = false
        val position = dataList.size - 1
        if (position != RecyclerView.NO_POSITION) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun addLoading() {
        isLoadingAdded = true
        add(
            DataModel(
                "", "","",null
            )
        )
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleText: TextView = view.findViewById(R.id.titleText)
        val bodyTest: TextView = view.findViewById(R.id.bodyTest)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        init {
            view.setOnClickListener{
                Toast.makeText(context, "Clicked ${dataList[adapterPosition].author}", Toast.LENGTH_SHORT).show()
            }

        }
    }

    internal inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loading: ProgressBar = itemView.findViewById(R.id.loading_progress)
    }
}