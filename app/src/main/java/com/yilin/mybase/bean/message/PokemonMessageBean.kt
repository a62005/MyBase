package com.yilin.mybase.bean.message

import com.yilin.mybase.MyApp
import com.yilin.mybase.R

class PokemonMessageBean(messageType: PokemonMessageEnum, name: String) :
    MessageBean(title = messageType.content.format(name)) {

    enum class PokemonMessageEnum(val content: String) {
        ADD_FAVORITE(MyApp.instance.getString(R.string.pokemon_add_to_favorite)),
        REMOVE_FAVORITE(MyApp.instance.getString(R.string.pokemon_remove_from_favorite))
    }
}