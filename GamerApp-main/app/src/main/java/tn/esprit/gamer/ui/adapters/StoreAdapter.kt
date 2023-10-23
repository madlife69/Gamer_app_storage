package tn.esprit.gamer.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tn.esprit.gamer.databinding.SingleItemStoreBinding
import tn.esprit.gamer.model.Product
import tn.esprit.gamer.utils.AppDatabase

class StoreAdapter(val products: MutableList<Product>) : RecyclerView.Adapter<StoreAdapter.StoreHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreHolder {
        val binding = SingleItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        with(holder){
            with(products[position]){
                binding.itemImage.setImageResource(imageRes)
                binding.itemTitle.text = title
                binding.itemType.text = type
                binding.itemPrice.text = "$price $"
                binding.btnAddToCart.setOnClickListener {
                    val product = products[position]

                    if (isproductInDatabase(product)) {
                        // Event is already in the database, show a Snackbar
                        val snackbar =
                            Snackbar.make(binding.root, " Product is already in the data base  ", Snackbar.LENGTH_LONG)
                                .setAction("action", null).show()
                    } else {
                        // Event is not in the database, add it
                        addToDatabase(product)
                        val snackbar =
                            Snackbar.make(binding.root, "  Product is added  ", Snackbar.LENGTH_LONG)
                                .setAction("action", null).show()
                    }
                }

            }
        }
    }
    private fun isproductInDatabase(product: Product): Boolean {
        val productId = product.id
        val count = AppDatabase.getDatabase().GameDao()?.doesproductExist(productId) ?: 0

        return count > 0
    }

    private fun addToDatabase(product: Product) {

            AppDatabase.getDatabase().GameDao()?.insertProduct(product)



        }


    override fun getItemCount() = products.size

    inner class StoreHolder(val binding: SingleItemStoreBinding) : RecyclerView.ViewHolder(binding.root)
}