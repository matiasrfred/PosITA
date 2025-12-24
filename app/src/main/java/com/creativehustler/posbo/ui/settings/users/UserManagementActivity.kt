package com.creativehustler.posbo.ui.settings.users

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.UserEntity
import com.creativehustler.posbo.ui.settings.users.UserFormDialog
import com.creativehustler.posbo.ui.settings.users.adapter.UserAdapter
import com.creativehustler.posbo.utils.ImmersiveHelper


class UserManagementActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        recycler = findViewById<RecyclerView>(R.id.rvUsers)
        recycler.layoutManager = LinearLayoutManager(this)

        refreshUsers()

        supportFragmentManager.setFragmentResultListener(
            UserFormDialog.REQUEST_KEY_USER_SAVED,
            this
        ) { _, _ ->
            refreshUsers()
        }

        findViewById<ImageButton>(R.id.btnAddUser).setOnClickListener {
            openUserForm(null)
        }
    }

    private fun refreshUsers() {
        val users = AppDatabase
            .getInstance(this)
            .userDao()
            .getAllUsers()

        recycler.adapter = UserAdapter(users) { user ->
            openUserForm(user)
        }
    }

    private fun openUserForm(user: UserEntity?) {
        UserFormDialog.newInstance(user).show(supportFragmentManager, "UserFormDialog")
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }
}

