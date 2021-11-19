package com.edgarmarcopolo.neptune.products.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.edgarmarcopolo.neptune.application.di.Injectable
import com.edgarmarcopolo.neptune.databinding.FragmentProductsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ProductsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ProductsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding : FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        viewModel.showError.observe(viewLifecycleOwner, {
            if(it){
                binding.tvError.visibility = View.VISIBLE
                binding.btnReload.visibility = View.VISIBLE
                binding.ibSort.visibility = View.INVISIBLE
            }else{
                binding.tvError.visibility = View.INVISIBLE
                binding.btnReload.visibility = View.INVISIBLE
                binding.ibSort.visibility = View.VISIBLE
            }

        })

        viewModel.productList.observe(viewLifecycleOwner){
            if(it.isNullOrEmpty().not()){
                binding.rvProductsList.adapter = MallsAdapter(it, viewModel)
            }
        }

        viewModel.showLoading.observe(viewLifecycleOwner){
            if (it){
                if(binding.progressBar.visibility == View.INVISIBLE){
                    binding.progressBar.visibility = View.VISIBLE
                }
            }else{
                if(binding.progressBar.visibility == View.VISIBLE){
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}