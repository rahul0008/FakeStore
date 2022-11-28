package com.example.fakestore.ui.addProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fakestore.R
import com.example.fakestore.server.entity.Product
import com.example.fakestore.server.handlers.RepositoryCallback
import com.example.fakestore.server.handlers.RequestProduct
import com.example.fakestore.server.handlers.ResultProduct
import com.example.fakestore.ui.MainActivityViewModel


class AddProductFragment : DialogFragment() {
    private lateinit var viewModel: MainActivityViewModel
    lateinit var spinner: AppCompatSpinner
    lateinit var addTitle: TextView
    lateinit var addPrice: TextView
    lateinit var addDescription: TextView
    lateinit var addImageUrl: TextView
    lateinit var addButton: Button
    lateinit var catagories :ArrayList<String>
    var selectedCategory =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        init(view)
        return view
    }

    companion object {
        fun newInstance(param1: String?, param2: String?): AddProductFragment {
            return AddProductFragment()
        }
    }


    private fun init(view: View) {
        spinner = view.findViewById(R.id.addCategory)
        addTitle = view.findViewById(R.id.addTitle)
        addPrice = view.findViewById(R.id.addPrice)
        addDescription = view.findViewById(R.id.addDescription)
        addImageUrl = view.findViewById(R.id.addImage)
        addButton = view.findViewById(R.id.addButtton)
        addButton.setOnClickListener {
            val requestProduct = RequestProduct().apply {
                title = addTitle.text.toString()
                price = addPrice.text.toString().toDouble()
                image = addImageUrl.text.toString()
                category = selectedCategory
                description = addTitle.text.toString()
            }
            viewModel.addProduct(requestProduct,addProductRepositoryCallback)
        }
        spinner.onItemSelectedListener = spinnerListener

        viewModel.products.observe(viewLifecycleOwner,productObserver)

    }

    var addProductRepositoryCallback = object : RepositoryCallback<ResultProduct> {
        override fun onComplete(result: Result<ResultProduct>) {
            result.onSuccess {
                Log.i("addProduct", "onComplete: "+it.id)
                Toast.makeText(requireContext(),"id "+it.id+" is Successfully added",Toast.LENGTH_LONG).show()
                dismiss()
            }
        }

        override fun onError(errorMessage: String) {
            Log.e("addProduct", "onError: "+errorMessage)
            Toast.makeText(requireContext(),"onError: "+errorMessage,Toast.LENGTH_LONG).show()
        }

    }

    var productObserver: Observer<ArrayList<Product>> = Observer{
        val product = it as ArrayList<Product>
        catagories = viewModel.getCategory(product)
        var adapter =ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item,catagories)
        spinner.adapter = adapter
    }

    private var spinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
            selectedCategory = adapterView.getItemAtPosition(i).toString()
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }




}