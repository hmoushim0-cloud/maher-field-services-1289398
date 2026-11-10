package sa.factory.maherfieldservices.domain.repository

import sa.factory.maherfieldservices.model.MaherFieldItem
import sa.factory.maherfieldservices.model.MaherFieldOrder
import sa.factory.maherfieldservices.model.MaherFieldTelemetry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MaherFieldRepository {
    val catalogFlow: Flow<List<MaherFieldItem>>
    val activeCart: StateFlow<List<MaherFieldItem>>
    val telemetryFlow: Flow<MaherFieldTelemetry>
    
    fun addToCart(item: MaherFieldItem)
    fun removeFromCart(item: MaherFieldItem)
    fun clearCart()
    suspend fun checkout(paymentMethod: String): MaherFieldOrder
    suspend fun refreshCatalog()
}
