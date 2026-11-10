package sa.factory.maherfieldservices.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import sa.factory.maherfieldservices.model.MaherFieldItem

@Entity(tableName = "services_items_cache")
data class MaherFieldEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priceSar: Double,
    val badge: String,
    val category: String,
    val status: String
) {
    fun toDomain(): MaherFieldItem = MaherFieldItem(
        id = id,
        title = title,
        description = description,
        priceSar = priceSar,
        badge = badge,
        category = category,
        status = status
    )
}
