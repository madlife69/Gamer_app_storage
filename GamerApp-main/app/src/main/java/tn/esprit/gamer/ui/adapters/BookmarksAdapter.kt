package tn.esprit.gamer.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tn.esprit.gamer.databinding.SingleItemBookmarksBinding
import tn.esprit.gamer.model.Product
import tn.esprit.gamer.utils.AppDatabase

class BookmarksAdapter(val products: MutableList<Product>) : RecyclerView.Adapter<BookmarksAdapter.BookmarksHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksHolder {
        val binding = SingleItemBookmarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarksHolder, position: Int) {
        with(holder){
            with(products[position]){
                binding.itemImage.setImageResource(imageRes)
                binding.itemTitle.text = title
                binding.itemType.text = type
                binding.itemPrice.text = "$price $"
                binding.btnAddToCart.setOnClickListener {
                    val event = products[position]
                    AppDatabase.getDatabase().GameDao()?.deleteEvent(event)
                    products.removeAt(position)
                    notifyDataSetChanged()
                    val snackbar =
                        Snackbar.make(binding.root, " BookMark deleted successfully  ", Snackbar.LENGTH_LONG)
                            .setAction("action", null).show()

                }

            }
        }
    }

    override fun getItemCount() = products.size

    inner class BookmarksHolder(val binding: SingleItemBookmarksBinding) : RecyclerView.ViewHolder(binding.root)
}