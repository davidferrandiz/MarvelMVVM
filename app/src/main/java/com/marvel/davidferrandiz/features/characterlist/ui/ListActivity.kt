package com.marvel.davidferrandiz.features.characterlist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.davidferrandiz.presentation.uidata.utils.Resource
import com.davidferrandiz.presentation.vm.CharactersViewModel
import com.marvel.davidferrandiz.R
import com.marvel.davidferrandiz.databinding.ActivityListBinding
import com.marvel.davidferrandiz.features.characterdetail.ui.CharacterDetailsActivity
import com.marvel.davidferrandiz.features.characterlist.ui.adapter.CharactersAdapter
import com.marvel.davidferrandiz.utils.views.GenericFeedbackView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list.*

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private val viewModel by viewModels<CharactersViewModel>()
    private lateinit var charactersAdapter: CharactersAdapter

    private val genericFeedbackView: GenericFeedbackView by lazy {
        binding.genericFeedbackView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()

        setRecyclerView()
        setListeners()
        addObservers()

        viewModel.getAllCharacters()
    }

    private fun setView() {
        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.character_list_toolbar_title)
    }

    private fun setRecyclerView() {
        with(binding.recycler) {
            layoutManager = GridLayoutManager(this@ListActivity, 2)

            charactersAdapter = CharactersAdapter { onCharacterClicked(it) }
            adapter = charactersAdapter
        }
    }

    private fun onCharacterClicked(id: Int) {
        viewModel.goToCharacterDetails(id)
    }

    private fun setListeners() {
        binding.refresh.setOnRefreshListener {
            viewModel.refreshCharacters()
            binding.refresh.isRefreshing = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favoriteListMenuItem) {
            viewModel.pressFavButton()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.character_list_menu, menu)
        val favoriteMenuItem = menu.findItem(R.id.favoriteListMenuItem)

        viewModel.favoriteStateLiveData.observe(this@ListActivity, {
            if (it) favoriteMenuItem.title = getString(R.string.all_characters_favorite_menu_item)
            else favoriteMenuItem.title = getString(R.string.favorite_menu_item)
        })

        return true
    }

    private fun addObservers() {

        with(viewModel) {
            charactersLiveData.observe(this@ListActivity, {
                when(it) {
                    is Resource.Success -> {
                        refresh.isRefreshing = false
                        genericFeedbackView.isVisible = false
                        charactersAdapter.characters = it.data!!
                    }
                    is Resource.Error -> {
                        refresh.isRefreshing = false
                        showErrorView(it.message!!)
                        genericFeedbackView.isVisible = true
                    }
                    is Resource.Loading -> refresh.isRefreshing = true
                }
            })

            characterDetailsLiveData.observe(this@ListActivity, {
                startActivity(CharacterDetailsActivity.getIntent(this@ListActivity, it))
            })
        }
    }

    private fun showErrorView(feedbackInfo: Pair<String, () -> Unit>) {
        val (message, action) = feedbackInfo

        if (viewModel.favoriteStateLiveData.value == true)
            genericFeedbackView.populate(
                message,
                action,
                getString(R.string.see_all_characters_button)
            )
        else
            genericFeedbackView.populate(message, action)
    }
}