package com.example.fakestore.ui.productDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fakestore.R
import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.RepositoryCallback
import com.example.fakestore.server.handlers.RequestProduct
import com.example.fakestore.server.handlers.ResultProduct
import com.example.fakestore.server.repository.ProductRepository
import com.example.fakestore.ui.MainActivityViewModel
import com.example.fakestore.utils.UserAlertClient


class FragmentProductDetails : Fragment() {
    private lateinit var mViewModel: MainActivityViewModel
    lateinit var userAlertClient: UserAlertClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        userAlertClient = UserAlertClient(requireActivity())
        if (mViewModel.selectedProduct != null) {
            view.findViewById<TextView>(R.id.productDetailsTitle).text =
                mViewModel.selectedProduct!!.title
            view.findViewById<TextView>(R.id.productDetailsPrice).text =
                requireContext().getString(R.string.Rs) + " " + mViewModel.selectedProduct!!.price.toString()
            view.findViewById<RatingBar>(R.id.productDetailRating).rating =
                mViewModel.selectedProduct!!.rating?.rate?.toFloat() ?: 0f
            view.findViewById<TextView>(R.id.productDetailRatingCount).text =
                mViewModel.selectedProduct!!.rating?.count.toString()
            view.findViewById<TextView>(R.id.productDetailsDescription).text =
                mViewModel.selectedProduct!!.description
            Glide.with(requireContext()).load(mViewModel.selectedProduct?.image)
                .into(view.findViewById(R.id.productDetailImage))
        }
        view.findViewById<Button>(R.id.delete).setOnClickListener {
            mViewModel.deleteProduct(
                mViewModel.selectedProduct?.id ?: 0,
                addProductRepositoryCallback
            )
        }

        view.findViewById<Button>(R.id.update).setOnClickListener {
            val requestProduct = RequestProduct().apply {
                val selectedProduct = mViewModel.selectedProduct
                if (selectedProduct != null) {
                    title = selectedProduct.title
                    price = selectedProduct.price
                    image = selectedProduct.image
                    category = selectedProduct.category
                    description = selectedProduct.description
                }
            }
            mViewModel.updateProduct(
                mViewModel.selectedProduct?.id ?: 0,
                requestProduct,
                updateProductRepositoryCallback
            )
        }
    }

    companion object {
        fun newInstance(): FragmentProductDetails {
            return FragmentProductDetails()
        }
    }

    var addProductRepositoryCallback = object : RepositoryCallback<Product> {
        override fun onComplete(result: Result<Product>) {
            result.onSuccess {
                Log.i("addProduct", "onComplete: " + it.id)
                //refresh list
                mViewModel.fetchProducts()
                userAlertClient.showDialogMessage("Success", it.title + " is deleted", false)

            }
        }

        override fun onError(errorMessage: String) {
            Log.e("addProduct", "onError: " + errorMessage)
            userAlertClient.showDialogMessage("Error", errorMessage, false)
        }

    }
    var updateProductRepositoryCallback = object : RepositoryCallback<Product> {
        override fun onComplete(result: Result<Product>) {
            result.onSuccess {
                Log.i("updateProduct", "onComplete: " + it.id)
//                    refresh list
                mViewModel.fetchProducts()
                userAlertClient.showDialogMessage(
                    "Success",
                    it.title + " is Update,Manual change to product is left to implement",
                    false
                )

            }
        }

        override fun onError(errorMessage: String) {
            Log.e("addProduct", "onError: " + errorMessage)
            userAlertClient.showDialogMessage("Error", errorMessage, false)
        }

    }
}