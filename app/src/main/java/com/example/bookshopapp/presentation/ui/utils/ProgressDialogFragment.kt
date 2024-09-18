package com.example.bookshopapp.presentation.ui.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.bookshopapp.R
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressIndicator: CircularProgressIndicator =
            view.findViewById(R.id.circular_progress_indicator)
        progressIndicator.show()
    }
}