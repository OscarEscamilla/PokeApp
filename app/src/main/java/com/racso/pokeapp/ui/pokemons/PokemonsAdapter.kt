package com.racso.pokeapp.ui.pokemons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.racso.pokeapp.R
import com.racso.pokeapp.core.BaseViewHolder
import com.racso.pokeapp.data.model.Pokemon
import com.racso.pokeapp.databinding.PokemonItemBinding
import java.util.*
import kotlin.collections.ArrayList

class PokemonsAdapter(
    private val itemClickListener: OnPokemonClickListener ): RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val pokemonList = mutableListOf<Pokemon>()


    interface OnPokemonClickListener{
        fun onClick(pokemon: Pokemon)
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

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is ViewHolder -> holder.bind(pokemonList[position])
        }
    }

    fun addItems(newItems: List<Pokemon>){
        pokemonList.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemCount() = pokemonList.size

    class ViewHolder(private val binding: PokemonItemBinding,  val context: Context) : BaseViewHolder<Pokemon>(binding.root) {
        override fun bind(item: Pokemon) {
            binding.txtPokemonName.text = item.name.capitalize()
            Glide.with(context).load(item.image_list)
                .placeholder(R.drawable.pokeball)
                .error(R.drawable.pokeball)
                .centerCrop()
                .into(binding.imgPokemon)

        }

    }
}