package com.example.fakestore.ui.products


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.adapter.ProductAdapter
import com.example.fakestore.server.entity.Product
import com.example.fakestore.ui.MainActivityViewModel
import com.example.fakestore.utils.ProductListener
import com.google.gson.Gson

class FragmentProducts : Fragment() {
    private lateinit var mViewModel: MainActivityViewModel
    lateinit var productRecyclerView: RecyclerView
    lateinit var productAdapter: ProductAdapter
    var products:ArrayList<Product> ?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        mViewModel?.productCategory?.observe(viewLifecycleOwner, productObserver)
        productRecyclerView= view.findViewById(R.id.productRecycler)

        productAdapter = ProductAdapter(requireContext(),productListener,products)
        productRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        productRecyclerView.adapter = productAdapter

    }
    private var productObserver:Observer<ArrayList<Product>> = Observer {
        val products = it as ArrayList<Product>
        this.products =products
        Log.i("productObserver", "onChanged: " + Gson().toJson(products))
        productAdapter.products = products
        productAdapter.notifyDataSetChanged()

    }


    companion object {
        fun newInstance(): FragmentProducts {
            return FragmentProducts()
        }
    }

    private var productListener = object : ProductListener {
        override fun isSelected(product: Product) {
            mViewModel.selectedProduct = product
            findNavController().navigate(R.id.action_fragmentProducts_to_fragmentProductDetails)
        }
    }
}