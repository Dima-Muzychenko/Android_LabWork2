package com.example.test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test.databinding.FragmentFirstBinding


class FirstFragment : Fragment(), ObserveInterface {

private lateinit var binding: FragmentFirstBinding
lateinit var obs:Observe//спостерігач

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.buttonFirst.setOnClickListener { backToParmeters() }
        retainInstance=true//зберігання інформації при повороті екрану
        obs = activity?.applicationContext as Observe

        //дуже сумнівно. Може в activity переробити
        var intent:Intent = Intent(activity, SaveIntoFileService::class.java)
        intent.setAction(SaveIntoFileService.ACTION_RESTORE_FROM_FILE)
        activity?.startService(intent)



      return binding.root
    }

    override fun onReadFromFile(str: String) {
        super.onReadFromFile(str)
        binding.textviewFirst.setText(str)
    }

    override fun onStart() {
        super.onStart()
        obs.addListener(this)


    }

    override fun onStop() {
        super.onStop()
        obs.removeListener(this)
    }


    private fun backToParmeters() {
        activity?.finish()
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