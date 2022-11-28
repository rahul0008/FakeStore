package com.example.fakestore.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakestore.R
import com.example.fakestore.adapter.CategoryAdapter
import com.example.fakestore.server.entity.Product
import com.example.fakestore.ui.MainActivityViewModel
import com.example.fakestore.utils.CategoryListener
import com.example.fakestore.utils.UserAlertClient
import com.google.gson.Gson

class FragmentCategory : Fragment() {
    private lateinit var mViewModel:MainActivityViewModel
    lateinit var categoryRecyclerView: RecyclerView
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var userAlertClient:UserAlertClient
    var catagories :ArrayList<String>?=null
    var products: ArrayList<Product>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userAlertClient = UserAlertClient(requireActivity())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val view = inflater.inflate(R.layout.fragment_fragment_category, container, false)
        categoryRecyclerView= view.findViewById(R.id.categoryRecycler)
        return view
    }

    override fun onResume() {
        super.onResume()
        categoryAdapter = CategoryAdapter(categoryListener, catagories)
        categoryRecyclerView.layoutManager= GridLayoutManager(requireContext(),2)
        categoryRecyclerView.adapter = categoryAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        mViewModel?.products?.observe(viewLifecycleOwner, productObserver)
        mViewModel?.message?.observe(viewLifecycleOwner, messageObserver)
        mViewModel.fetchProducts()
        userAlertClient.showWaitDialog("Fetching Products...")


    }
    private var messageObserver : Observer<String> = Observer{
        userAlertClient.showDialogMessage("Error",it,false)
    }
    private var productObserver: Observer<ArrayList<Product>> = Observer {
        val products = it as ArrayList<Product>
        this.products = products
        userAlertClient.closeWaitDialog()
        Log.i("CategoryObserver", "onChanged: " + Gson().toJson(products))
       val categories = mViewModel?.getCategory(products)
        if (categories != null) {
            if (categories.isNotEmpty()) {
                categoryAdapter.categories = categories
                categoryAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        fun newInstance(): FragmentCategory {
            return FragmentCategory()
        }
    }

    private var categoryListener = object : CategoryListener {
        override fun isSelected(category: String) {
            mViewModel.selectedCategory(category,products)
            findNavController().navigate(R.id.action_fragmentCategory_to_fragmentProducts)

        }
    }
}