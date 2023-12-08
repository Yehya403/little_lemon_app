package com.example.littlelemon

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val database: AppDatabase by lazy {
        Room.databaseBuilder(application, AppDatabase::class.java, "database")
            .addMigrations(AppDatabase.migration1to2)
            .build()
    }

    fun getAllDatabaseMenuItems(): LiveData<List<MenuItemRoom>> {
        return database.menuItemDao().getAll()
    }

    fun fetchMenuDataIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                saveMenuToDatabase(
                    database,
                    fetchMenu("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                )
            }
        }
    }

    val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    suspend fun fetchMenu(url: String): List<MenuItemNetwork> {
        val res: HttpResponse = httpClient.get(url)
        val menuData: MenuNetworkData = res.body()
        val menuItems: List<MenuItemNetwork> = menuData.menu
        return menuItems
    }

    private fun saveMenuToDatabase(
        database: AppDatabase,
        MenuItemNetwork: List<MenuItemNetwork>
    ) {
        val menuItemsRoom = MenuItemNetwork.map {
            it.toMenuItemRoom()
        }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }

    override fun onCleared() {
        super.onCleared()
        database.close()
    }
}
