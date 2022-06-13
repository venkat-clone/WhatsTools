package com.android.whatstools.screen.status

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.whatstools.databinding.FragmentStatusListBinding
import java.io.File


class StatusList : Fragment() {
    lateinit var binding : FragmentStatusListBinding;
    lateinit var statusViewModel: StatusViewModel;

    private lateinit var viewModel: StatusListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatusListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[StatusListViewModel::class.java]
        statusViewModel = ViewModelProvider(requireActivity())[StatusViewModel::class.java]
        Observers()
    }


    fun Observers(){
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        viewModel.bitmapList.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                val adapter = statusAdapter(requireContext(), it,viewModel)
                binding.recycler.adapter = adapter
            }
        })
        viewModel.status.observe(viewLifecycleOwner) {
            if (it != null) {
                statusViewModel.status.postValue(it.path)
//                requireFragmentManager().beginTransaction().add(MediaFragment(),"").commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }





}