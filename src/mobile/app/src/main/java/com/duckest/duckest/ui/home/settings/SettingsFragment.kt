package com.duckest.duckest.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.duckest.duckest.Utils
import com.duckest.duckest.Utils.checkName
import com.duckest.duckest.Utils.setTextChangeListener
import com.duckest.duckest.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextChangeListener(binding.nameEdit, binding.name)
        setTextChangeListener(binding.surnameEdit, binding.surname)
        setTextChangeListener(binding.patronymicEdit, binding.patronymic)
        binding.saveProfileSettings.setOnClickListener {
            if (checkFields()) {
                return@setOnClickListener
            }
            // TODO: 15.04.2021
        }
    }

    private fun checkFields(): Boolean =
        (Utils.isEmptyField(binding.nameEdit, binding.name) ||
                checkName(binding.nameEdit, binding.name)) or
                (Utils.isEmptyField(binding.surnameEdit, binding.surname) ||
                        checkName(binding.surnameEdit, binding.surname)) or
                (Utils.isEmptyField(binding.patronymicEdit, binding.patronymic) ||
                checkName(binding.patronymicEdit, binding.patronymic))


}