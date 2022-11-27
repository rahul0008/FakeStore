package com.example.fakestore.adapter


import android.content.Context
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakestore.R
import com.example.fakestore.server.entity.Product
import com.example.fakestore.utils.ProductListener

class ProductAdapter(var context: Context,private var productListener: ProductListener, var products: ArrayList<Product>?) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false)
        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.productTitle.text = products?.get(position)?.title ?:"NO Product Found"
        holder.productRating.rating = products?.get(position)?.rating?.rate?.toFloat() ?: 0f
        holder.productRatingCount.text = products?.get(position)?.rating?.count.toString()
        holder.productPrice.text = context.getString(R.string.Rs) + " " + (products?.get(position)?.price
            ?: 0)
        Glide.with(context).load(products?.get(position)?.image).into(holder.productImageContainer)
        holder.productCard.setOnClickListener{
            productListener.isSelected(products?.get(position) ?: Product())
        }
    }

    override fun getItemCount(): Int {
        return products?.size ?: 0
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImageContainer: ImageView = itemView.findViewById(R.id.productImageContainer)
        var productTitle: TextView = itemView.findViewById(R.id.productTitle)
        var productRating: RatingBar = itemView.findViewById(R.id.productRating)
        var productRatingCount: TextView = itemView.findViewById(R.id.productRatingCount)
        var productPrice: TextView = itemView.findViewById(R.id.productPrice)
        var productCard: CardView = itemView.findViewById(R.id.productCard)

    }

}