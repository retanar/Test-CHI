package retanar.test.chi.presentation

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import retanar.test.chi.R
import retanar.test.chi.data.ShibeEntity
import retanar.test.chi.databinding.ShibeListItemBinding

class ShibeListAdapter(
    private val onChangeFavorite: (String) -> Unit,
    private val onReachedBottom: (() -> Unit)? = null,
) : ListAdapter<ShibeEntity, ShibeListAdapter.ShibeViewHolder>(ShibeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShibeViewHolder {
        return ShibeViewHolder(ShibeListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ShibeViewHolder, position: Int) {
        Log.d("Recycler", "Update on position $position triggered")

        // Load more data
        if (position + 1 >= itemCount) {
            onReachedBottom?.invoke()
        }

        val shibe = getItem(position)
        with(holder.binding) {
            Picasso.get()
                .load(shibe.url)
                .into(imageView)

            if (shibe.isFavorite) {
                addToFavorites.setImageResource(R.drawable.star_filled)
                addToFavorites.setColorFilter(Color.YELLOW)
            } else {
                addToFavorites.setImageResource(R.drawable.star_outline)
                addToFavorites.setColorFilter(Color.WHITE)
            }

            addToFavorites.setOnClickListener {
                onChangeFavorite(shibe.url)
            }
        }
    }

    class ShibeViewHolder(val binding: ShibeListItemBinding) : ViewHolder(binding.root)

    class ShibeComparator : ItemCallback<ShibeEntity>() {
        override fun areItemsTheSame(oldItem: ShibeEntity, newItem: ShibeEntity) = oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ShibeEntity, newItem: ShibeEntity) = oldItem == newItem
    }
}