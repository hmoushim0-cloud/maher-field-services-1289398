package sa.factory.maherfieldservices.domain.usecase

import sa.factory.maherfieldservices.domain.repository.MaherFieldRepository
import sa.factory.maherfieldservices.model.MaherFieldItem
import sa.factory.maherfieldservices.model.MaherFieldOrder
import sa.factory.maherfieldservices.model.MaherFieldTelemetry
import kotlinx.coroutines.flow.Flow

class GetCatalogUseCase(private val repository: MaherFieldRepository) {
    operator fun invoke(): Flow<List<MaherFieldItem>> = repository.catalogFlow
}

class ManageCartUseCase(private val repository: MaherFieldRepository) {
    val cart = repository.activeCart
    fun add(item: MaherFieldItem) = repository.addToCart(item)
    fun remove(item: MaherFieldItem) = repository.removeFromCart(item)
    fun clear() = repository.clearCart()
}

class ProcessCheckoutUseCase(private val repository: MaherFieldRepository) {
    suspend operator fun invoke(paymentMethod: String = "Mada Contactless EMV"): MaherFieldOrder =
        repository.checkout(paymentMethod)
}

class ObserveHardwareTelemetryUseCase(private val repository: MaherFieldRepository) {
    operator fun invoke(): Flow<MaherFieldTelemetry> = repository.telemetryFlow
}
