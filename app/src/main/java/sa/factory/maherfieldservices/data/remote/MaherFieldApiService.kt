package sa.factory.maherfieldservices.data.remote

import sa.factory.maherfieldservices.model.MaherFieldItem
import sa.factory.maherfieldservices.model.MaherFieldOrder

interface MaherFieldApiService {
    suspend fun fetchCatalog(): List<MaherFieldItem>
    suspend fun submitOrder(order: MaherFieldOrder): Boolean
}
