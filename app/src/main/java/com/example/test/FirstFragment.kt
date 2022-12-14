package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.databinding.FragmentFirstBinding
import com.example.test.databinding.FragmentFirstBinding.*


class FirstFragment : Fragment() {

private lateinit var binding: FragmentFirstBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = inflate(inflater, container, false)
        binding.buttonFirst.setOnClickListener { backToParmeters() }
        retainInstance=true//зберігання інформації при повороті екрану

      return binding.root
    }

    private fun backToParmeters() {
        parentFragmentManager
            .beginTransaction()
            .remove(this)
            .commit()
    }

    companion object{
        
        @JvmStatic
        fun newInstance(): FirstFragment{//якщо передаємо дані, то тут довше розписувати
            return FirstFragment()
        }
    }
}