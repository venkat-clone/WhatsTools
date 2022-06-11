package com.android.whatstools.screen.status

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.android.whatstools.R
import com.android.whatstools.databinding.FragmentMediaBinding
import com.android.whatstools.screen.StatusActivity
import java.io.File


class MediaFragment : Fragment() {
    lateinit var statusViewModel:StatusViewModel;
    lateinit var binding:FragmentMediaBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        statusViewModel = ViewModelProvider(requireActivity())[StatusViewModel::class.java]
        binding = FragmentMediaBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.activity = requireActivity() as StatusActivity?
        setMediaFile()
        binding.toolbar.setNavigationOnClickListener {
            statusViewModel.removeFragment.postValue(true)
        }

        return binding.root
    }

    private fun setMediaFile(){
        if(statusViewModel.status.value!!.contains(".mp4")) {
            binding.video.visibility = View.VISIBLE
            binding.video.setVideoPath(statusViewModel.status.value!!)
            binding.video.setOnPreparedListener {
                it.isLooping = true
            }
            binding.video.start()
        }
        else {
            binding.image.visibility = View.VISIBLE

            binding.image.setImageURI(File(statusViewModel.status.value!!).toUri())
        }
    }




}