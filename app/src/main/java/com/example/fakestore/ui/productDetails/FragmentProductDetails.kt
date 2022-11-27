package com.example.fakestore.ui.productDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fakestore.R
import com.example.fakestore.ui.MainActivityViewModel


class FragmentProductDetails : Fragment() {
    private lateinit var mViewModel: MainActivityViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        if (mViewModel.selectedProduct!=null) {
            view.findViewById<TextView>(R.id.productDetailsTitle).text =
                mViewModel.selectedProduct!!.title
            view.findViewById<TextView>(R.id.productDetailsPrice).text =
                requireContext().getString(R.string.Rs) + " " +mViewModel.selectedProduct!!.price.toString()
            view.findViewById<RatingBar>(R.id.productDetailRating).rating =
                mViewModel.selectedProduct!!.rating?.rate?.toFloat() ?: 0f
            view.findViewById<TextView>(R.id.productDetailRatingCount).text =
                mViewModel.selectedProduct!!.rating?.count.toString()
            view.findViewById<TextView>(R.id.productDetailsDescription).text =
                mViewModel.selectedProduct!!.description
            Glide.with(requireContext()).load(mViewModel.selectedProduct?.image)
                .into(view.findViewById(R.id.productDetailImage))
        }
    }

    companion object {
        fun newInstance(): FragmentProductDetails {
            return FragmentProductDetails()
        }
    }
}