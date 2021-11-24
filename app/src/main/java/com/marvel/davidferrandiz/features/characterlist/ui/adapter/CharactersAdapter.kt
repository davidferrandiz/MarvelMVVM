package com.marvel.davidferrandiz.features.characterlist.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.davidferrandiz.presentation.uidata.CharacterListItem
import com.marvel.davidferrandiz.R
import com.marvel.davidferrandiz.databinding.CharacterRowBinding
import com.marvel.davidferrandiz.utils.images.setImageFromUrl

class CharactersAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<CharactersAdapter.AdapterViewHolder>() {

    var characters: List<CharacterListItem> = emptyList()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.character_row, parent, false)
        return AdapterViewHolder(view)
    }

    override fun getItemCount(): Int = characters.size


    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character)
        holder.itemView.setOnClickListener { listener(character.id) }
        setItemHighlightedWhenPressed(holder.itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setItemHighlightedWhenPressed(itemView: View) {
        itemView.setOnTouchListener { view, event ->

            val image: ConstraintLayout = view as ConstraintLayout

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    image.alpha = 0.8f
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    image.alpha = 1f
                }
            }

            false
        }
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CharacterRowBinding.bind(view)

        fun bind(character: CharacterListItem) = with(binding) {
            characterNameTv.text = character.name
            characterImageIv.setImageFromUrl(character.thumbnail)
        }
    }
}