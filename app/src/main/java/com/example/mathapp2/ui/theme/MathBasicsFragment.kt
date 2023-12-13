package com.example.mathapp2.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mathapp2.R

class MathBasicsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_math_basics, container, false)
        val imageView = view.findViewById<ImageView>(R.id.mathBasicsImageView)

        Glide.with(this)
            .load("https://images.unsplash.com/photo-1581090700227-1e37b190418e")
            .into(imageView)

        return view
    }
}
