package com.racso.pokeapp.ui.pokemons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.racso.pokeapp.R
import com.racso.pokeapp.core.BaseViewHolder
import com.racso.pokeapp.data.model.PokemonEntry
import com.racso.pokeapp.data.model.getImageUrl
import com.racso.pokeapp.databinding.PokemonItemBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class PokemonsAdapter(
    private val pokemonList: ArrayList<PokemonEntry>,
    private val itemClickListener: OnPokemonClickListener ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnPokemonClickListener{
        fun onClick(pokemon: PokemonEntry)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        // Create a new view, which defines the UI of the list item
        val itemPokemonsBinding = PokemonItemBinding.inflate(LayoutInflater.from(viewGroup.context))
        val holder = ViewHolder(itemPokemonsBinding, viewGroup.context)
        itemPokemonsBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION} ?: return@setOnClickListener
            itemClickListener.onClick(pokemonList[position])
        }

        return holder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        //viewHolder.textView.text = dataSet[position]
        when(holder){
            is ViewHolder -> holder.bind(pokemonList[position])
        }

    }

    override fun getItemCount() = pokemonList.size

    class ViewHolder(private val binding: PokemonItemBinding,  val context: Context) : BaseViewHolder<PokemonEntry>(binding.root) {
        override fun bind(item: PokemonEntry) {
            binding.txtPokemonName.text =  item.name
            Glide.with(context).load(item.getImageUrl()).into(binding.imgPokemon)
        }

    }
}