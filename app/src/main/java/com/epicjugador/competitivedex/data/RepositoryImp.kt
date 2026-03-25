package com.epicjugador.competitivedex.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.epicjugador.competitivedex.data.local.ShowdownCache
import com.epicjugador.competitivedex.data.network.PokeApiService
import com.epicjugador.competitivedex.data.network.ShowdownApiService
import com.epicjugador.competitivedex.domain.Repository
import com.epicjugador.competitivedex.domain.model.PokeDetailsModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImp @Inject constructor(
    private val apiService: PokeApiService,
    @ApplicationContext private val context: Context
    //private val showdownApiService: ShowdownApiService
) : Repository {

    var itemSpiteSheet: Bitmap? = null

    /*private fun parseItems(js: String): Map<String, ItemData> {
        val map = HashMap<String, ItemData>(700)
        val entries = js.split("},")
        for (entry in entries) {
            val idEnd = entry.indexOf(":")
            if (idEnd == -1) continue
            val id = entry.take(idEnd)
                .trim()
                .replace("\"", "")
            val nameStart = entry.indexOf("name:\"")
            if (nameStart == -1) continue
            val nameEnd = entry.indexOf("\"", nameStart + 6)
            val name = entry.substring(nameStart + 6, nameEnd)
            val spriteIndex = entry.indexOf("spritenum:")
            val spriteNum = if (spriteIndex != -1) {
                val end = entry.indexOf(",", spriteIndex)
                entry.substring(spriteIndex + 10, end).toInt()
            } else null
            map[id] = ItemData(name, spriteNum)
        }
        return map
    }

    private fun parseMoves(js: String): Map<String, ItemData> {
        val map = HashMap<String, ItemData>(1000)
        val entries = js.split("},")
        for (entry in entries)  {
            val idEnd = entry.indexOf(":")
            if (idEnd == -1) continue
            val id = entry.take(idEnd).trim().replace("\"", "")
            val nameStart = entry.indexOf("name:\"")
            if (nameStart == -1) continue
            val nameEnd = entry.indexOf("\"", nameStart + 6)
            val name = entry.substring(nameStart + 6, nameEnd)
            map[id] = ItemData(name, null)
        }
        return map
    }

    private fun parseAbilities(js: String): Map<String,ItemData> {
        val map = HashMap<String, ItemData>(300)
        val entries = js.split("},")
        for (entry in entries) {
            val idEnd = entry.indexOf(":")
            if (idEnd == -1) continue
            val id = entry.take(idEnd).trim().replace("\"", "")
            val nameStart = entry.indexOf("name:\"")
            if (nameStart == -1) continue
            val nameEnd = entry.indexOf("\"", nameStart + 6)
            val name = entry.substring(nameStart + 6, nameEnd)
            map[id] = ItemData(name, null)
        }
        return map
    }*/

    private suspend fun ensureLoaded() {
        if (!ShowdownCache.loaded) {
            loadShowdownData()
        }
    }

    suspend fun loadItemSpriteSheet() = withContext(Dispatchers.IO)  {
        if (itemSpiteSheet != null) return@withContext
        val url = URL("https://play.pokemonshowdown.com/sprites/itemicons-sheet.png")
        val connection = url.openConnection()
        connection.connect()
        val stream = connection.getInputStream()
        itemSpiteSheet = BitmapFactory.decodeStream(stream)
    }

    /*suspend fun loadShowdownData() = withContext(Dispatchers.IO) {
        if (ShowdownCache.loaded) return@withContext

        coroutineScope {
            val itemDeferred = async { showdownApiService.getItemsJS() }
            val movesDeferred = async { showdownApiService.getMovesJS() }
            val abilitiesDeferred = async { showdownApiService.getAbilitiesJS() }

            val items = parseItems(itemDeferred.await())
            val moves = parseMoves(movesDeferred.await())
            val abilities = parseAbilities(abilitiesDeferred.await())

            ShowdownCache.items.putAll(items)
            ShowdownCache.moves.putAll(moves)
            ShowdownCache.abilities.putAll(abilities)

            ShowdownCache.loaded = true
        }


    }*/

    suspend fun loadShowdownData() = withContext(Dispatchers.IO) {

        if (ShowdownCache.loaded) return@withContext

        val gson = Gson()

        val itemsJson = context.assets.open("items.json")
            .bufferedReader()
            .use { it.readText() }

        val movesJson = context.assets.open("moves.json")
            .bufferedReader()
            .use { it.readText() }

        val abilitiesJson = context.assets.open("abilities.json")
            .bufferedReader()
            .use { it.readText() }

        val itemsType = object : TypeToken<Map<String, ItemData>>() {}.type
        val movesType = object : TypeToken<Map<String, ItemData>>() {}.type
        val abilitiesType = object : TypeToken<Map<String, ItemData>>() {}.type

        val items: Map<String, ItemData> = gson.fromJson(itemsJson, itemsType)
        val moves: Map<String, ItemData> = gson.fromJson(movesJson, movesType)
        val abilities: Map<String, ItemData> = gson.fromJson(abilitiesJson, abilitiesType)

        ShowdownCache.items.putAll(items)
        ShowdownCache.moves.putAll(moves)
        ShowdownCache.abilities.putAll(abilities)

        ShowdownCache.loaded = true

        Log.i("SHOWDOWN", "Items loaded = ${ShowdownCache.items.size}")
    }

    //PROBAR CAMBIAR  A  SHOWDOWNCACHE
    fun getItemSpriteNum(id: String): Int? {
        return ShowdownCache.items[id]?.spriteNum
    }

    fun getMoveName(id: String): String {
        return ShowdownCache.moves[id]?.name ?: id
    }

    fun getAbilityName(id: String): String {
        return ShowdownCache.abilities[id]?.name ?: id
    }

    fun getItemDisplayName(itemName: String): String {
        return ShowdownCache.items[itemName]?.name ?: itemName
    }

    override suspend fun getPokeDetails(id: Int): PokeDetailsModel? {
        runCatching { apiService.getPokeDetails(id) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("err", "Error ${it.message}") }

        return null
    }

    override suspend fun getAbilityDetails(name: String): PokeDetailsModel? {
        runCatching { apiService.getAbilityDetails(name) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("err", "Error ${it.message}") }

        return null
    }

    /*private fun parseShowdownData(js: String): Map<String, ShowdownEntry> {

        val start = js.indexOf("{")
        val end = js.lastIndexOf("}")
        val cleaned = js.substring(start, end + 1)

        val regex = Regex("""(\w+):\s*\{[^}]*?name:\s*"([^"]+)"[^}]*?\}""")

        val map = mutableMapOf<String, ShowdownEntry>()

        regex.findAll(cleaned).forEach { match ->

            val id = match.groupValues[1]
                .lowercase()
                .replace("-", "")
                .replace(" ", "")
            val name = match.groupValues[2]

            val spriteMatch = Regex("""spritenum:\s*(\d+)""").find(match.value)
            val spriteNum = spriteMatch?.groupValues?.get(1)?.toInt()

            map[id] = ShowdownEntry(
                name = name,
                spriteNum = spriteNum
            )
        }

        return map
    }*/

}

data class ShowdownEntry(
    val name: String,
    val spriteNum: Int? = null
)

data class ItemData(
    val name: String,
    @SerializedName("spritenum")
    val spriteNum: Int? = null
)