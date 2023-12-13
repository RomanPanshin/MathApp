package com.example.mathapp2.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mathapp2.R

class AlgebraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_algebra, container, false)
        val imageView = view.findViewById<ImageView>(R.id.algebraImageView)

        Glide.with(this)
            .load("https://images.unsplash.com/photo-1518133910546-b6c2fb7d79e3")
            .into(imageView)

        return view
    }
}
