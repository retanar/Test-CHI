package retanar.test.chi.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import retanar.test.chi.databinding.ShibeListItemBinding

class ShibeListAdapter : ListAdapter<String, ShibeListAdapter.ShibeViewHolder>(ShibeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShibeViewHolder {
        return ShibeViewHolder(ShibeListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ShibeViewHolder, position: Int) {
        val url = getItem(position)
        Picasso.get()
            .load(url)
            .into(holder.binding.imageView)
    }

    class ShibeViewHolder(val binding: ShibeListItemBinding) : ViewHolder(binding.root)

    class ShibeComparator : ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}