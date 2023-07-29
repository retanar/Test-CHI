package retanar.test.chi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import retanar.test.chi.database.UserEntity
import retanar.test.chi.databinding.UserListItemBinding

class UsersListAdapter(
    private val onItemClick: (Int) -> Unit,
    private val onUserChecked: (UserEntity) -> Unit,
    private val onItemLongClick: (Int) -> Unit,
) : ListAdapter<UserEntity, UsersListAdapter.UserItemViewHolder>(UsersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val entity = getItem(position)
        with(holder.binding) {
            userName.text = entity.name
            userDescription.text = entity.description
            userAge.text = entity.age.toString()
            isStudent.isChecked = entity.isStudent

            itemCard.setOnClickListener { onItemClick(position) }
            itemCard.setOnLongClickListener {
                onItemLongClick(position)
                true
            }
            isStudent.setOnClickListener {
                onUserChecked(entity.copy(isStudent = !entity.isStudent))
            }
        }
    }

    class UserItemViewHolder(val binding: UserListItemBinding) : ViewHolder(binding.root)

    class UsersComparator : ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) = oldItem == newItem
    }
}