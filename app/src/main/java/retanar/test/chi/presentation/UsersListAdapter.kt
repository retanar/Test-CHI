package retanar.test.chi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import retanar.test.chi.database.UserEntity
import retanar.test.chi.databinding.UserListItemBinding
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class UsersListAdapter : ListAdapter<UserEntity, UsersListAdapter.UserItemViewHolder>(UsersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.userName.text = item.name
        val age = Period.between(
            LocalDate.parse(item.dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            LocalDate.now(),
        ).years
        holder.binding.userAge.text = age.toString()
    }

    class UserItemViewHolder(val binding: UserListItemBinding) : ViewHolder(binding.root)

    class UsersComparator : ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem === newItem

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem == newItem
    }
}