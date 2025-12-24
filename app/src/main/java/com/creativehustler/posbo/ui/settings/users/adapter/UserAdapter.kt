package com.creativehustler.posbo.ui.settings.users.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.entity.UserEntity

class UserAdapter(
    private val users: List<UserEntity>,
    private val onUserClick: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtUserId: TextView = view.findViewById(R.id.txtUserId)
        val txtUsername: TextView = view.findViewById(R.id.txtUsername)
        val txtRole: TextView = view.findViewById(R.id.txtRole)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.txtUserId.text = "ID: ${user.id}"
        val fullName = listOf(
            user.firstName.takeIf { it.isNotBlank() },
            user.lastName.takeIf { it.isNotBlank() }
        ).filterNotNull().joinToString(" ")
        holder.txtUsername.text = fullName.ifBlank { user.username }
        holder.txtRole.text = user.role.uppercase()
        holder.txtStatus.text = if (user.active) "Activo" else "Inactivo"

        val color = when (user.role.lowercase()) {
            "superadmin", "admin" -> android.R.color.holo_red_dark
            "supervisor" -> android.R.color.holo_orange_dark
            "cajero" -> android.R.color.holo_green_dark
            else -> android.R.color.darker_gray
        }

        holder.txtRole.setBackgroundColor(
            ContextCompat.getColor(holder.itemView.context, color)
        )

        holder.itemView.setOnClickListener {
            onUserClick(user)
        }
    }

    override fun getItemCount(): Int = users.size
}
