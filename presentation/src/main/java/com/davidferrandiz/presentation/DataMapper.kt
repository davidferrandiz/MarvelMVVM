package com.davidferrandiz.presentation

import com.davidferrandiz.domain.entities.MarvelCharacter
import com.davidferrandiz.presentation.uidata.CharacterListItem

fun MarvelCharacter.toUI() = CharacterListItem(id, name, thumbnail)