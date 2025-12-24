package com.creativehustler.posbo.ui.settings.users

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.UserEntity
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText

class UserFormDialog : DialogFragment() {

    companion object {
        const val REQUEST_KEY_USER_SAVED = "USER_SAVED"
        private const val ARG_USER_ID = "arg_user_id"
        private const val ARG_FIRST_NAME = "arg_first_name"
        private const val ARG_LAST_NAME = "arg_last_name"
        private const val ARG_USERNAME = "arg_username"
        private const val ARG_PASSWORD = "arg_password"
        private const val ARG_ROLE = "arg_role"
        private const val ARG_ACTIVE = "arg_active"

        fun newInstance(user: UserEntity?): UserFormDialog {
            val fragment = UserFormDialog()
            fragment.arguments = user?.let {
                bundleOf(
                    ARG_USER_ID to it.id,
                    ARG_FIRST_NAME to it.firstName,
                    ARG_LAST_NAME to it.lastName,
                    ARG_USERNAME to it.username,
                    ARG_PASSWORD to it.password,
                    ARG_ROLE to it.role,
                    ARG_ACTIVE to it.active
                )
            }
            return fragment
        }
    }

    private lateinit var spRole: MaterialAutoCompleteTextView
    private var editingUserId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_user_form, container, false)

        spRole = view.findViewById(R.id.spRole)
        val tvDialogTitle = view.findViewById<TextView>(R.id.tvDialogTitle)

        setupRoleCombo()

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener { dismiss() }

        arguments?.let {
            editingUserId = it.getInt(ARG_USER_ID).takeIf { id -> id > 0 }
            tvDialogTitle.text = getString(R.string.editar_usuario)
            view.findViewById<TextInputEditText>(R.id.etFirstName).setText(it.getString(ARG_FIRST_NAME, ""))
            view.findViewById<TextInputEditText>(R.id.etLastName).setText(it.getString(ARG_LAST_NAME, ""))
            view.findViewById<TextInputEditText>(R.id.etUsername).setText(it.getString(ARG_USERNAME, ""))
            view.findViewById<TextInputEditText>(R.id.etPassword).setText(it.getString(ARG_PASSWORD, ""))
            val roleFromArgs = it.getString(ARG_ROLE).orEmpty()
            if (roleFromArgs.isNotEmpty()) {
                spRole.setText(roleFromArgs, false)
            }
            view.findViewById<SwitchMaterial>(R.id.swActive).isChecked = it.getBoolean(ARG_ACTIVE, true)
        } ?: run {
            tvDialogTitle.text = getString(R.string.nuevo_usuario)
        }

        view.findViewById<Button>(R.id.btnSave).setOnClickListener {
            val firstName = view.findViewById<TextInputEditText>(R.id.etFirstName).text?.toString()?.trim().orEmpty()
            val lastName = view.findViewById<TextInputEditText>(R.id.etLastName).text?.toString()?.trim().orEmpty()
            val username = view.findViewById<TextInputEditText>(R.id.etUsername).text?.toString()?.trim().orEmpty()
            val password = view.findViewById<TextInputEditText>(R.id.etPassword).text?.toString()?.trim().orEmpty()
            val role = spRole.text.toString()
            val active = view.findViewById<SwitchMaterial>(R.id.swActive).isChecked

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.faltan_campos_obligatorios), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UserEntity(
                id = editingUserId ?: 0,
                firstName = firstName,
                lastName = lastName,
                username = username,
                password = password,
                role = role.ifEmpty { getString(R.string.cajero) },
                active = active
            )

            val dao = AppDatabase.getInstance(requireContext()).userDao()
            if (editingUserId != null) {
                dao.update(user)
            } else {
                dao.insert(user)
            }
            setFragmentResult(REQUEST_KEY_USER_SAVED, bundleOf())
            Toast.makeText(requireContext(), getString(R.string.usuario_guardado), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }

    private fun setupRoleCombo() {
        val roles = listOf("Superadmin", "Admin", "Supervisor", "Cajero")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roles)
        spRole.setAdapter(adapter)
        spRole.setText(roles.last(), false) // Valor por defecto
        spRole.threshold = 0
        spRole.setOnClickListener {
            spRole.showDropDown()
        }
        spRole.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                spRole.showDropDown()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.6).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        ImmersiveHelper.apply(requireActivity())
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        ImmersiveHelper.apply(requireActivity())
    }
}
