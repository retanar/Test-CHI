package retanar.test.chi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import retanar.test.chi.database.UserEntity
import retanar.test.chi.databinding.UserListItemBinding

class UsersListAdapter : ListAdapter<UserEntity, UsersListAdapter.UserItemViewHolder>(UsersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        holder.binding.userName.text = getItem(position).name
        // TODO: calculate age from current date
        holder.binding.userAge.text = getItem(position).dateOfBirth
    }

    class UserItemViewHolder(val binding: UserListItemBinding) : ViewHolder(binding.root)

    class UsersComparator : ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem === newItem

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem == newItem
    }
}