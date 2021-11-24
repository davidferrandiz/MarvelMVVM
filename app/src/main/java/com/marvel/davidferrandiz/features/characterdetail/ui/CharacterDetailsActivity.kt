package com.marvel.davidferrandiz.features.characterdetail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.presentation.vm.CharacterDetailsViewModel
import com.marvel.davidferrandiz.R
import com.marvel.davidferrandiz.databinding.ActivityCharacterDetailsBinding
import com.marvel.davidferrandiz.utils.images.setImageFromUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private lateinit var item: MenuItem

    companion object {
        private const val CHARACTER = "CHARACTER"

        fun getIntent(context: Context, character: MarvelCharacter): Intent {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra(CHARACTER, character)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setView()
        viewModel.character = intent.extras!!.getSerializable(CHARACTER) as MarvelCharacter
        populateViews()
    }

    private fun populateViews() {
        with(binding) {
            titleTv.text = viewModel.character.name
            contentTv.text = viewModel.character.description

            characterImage.setImageFromUrl(viewModel.character.thumbnail.replace("standard",
                "landscape"))
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = viewModel.character.name

        viewModel.checkFavoriteCharacter()
    }

    private fun setView() {
        setContentView(R.layout.activity_character_details)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.character_detail_menu, menu)
        item = menu.findItem(R.id.favoriteEvent)

        viewModel.isFavoriteLiveData.observe(this, {
            changeIcon(it)
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.favoriteEvent -> {
                viewModel.pressFavButton()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun changeIcon(isFavorite: Boolean) {
        if (isFavorite)
            item.icon = getDrawable(R.drawable.ic_favorite_white)
        else
            item.icon = getDrawable(R.drawable.ic_favorite_border_white)
    }

}