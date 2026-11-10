package sa.factory.maherfieldservices.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import sa.factory.maherfieldservices.domain.usecase.*
import sa.factory.maherfieldservices.model.MaherFieldItem
import sa.factory.maherfieldservices.model.MaherFieldOrder
import sa.factory.maherfieldservices.model.MaherFieldTelemetry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MaherFieldUiState(
    val catalog: List<MaherFieldItem> = emptyList(),
    val filteredCatalog: List<MaherFieldItem> = emptyList(),
    val selectedCategory: String = "الكل",
    val cart: List<MaherFieldItem> = emptyList(),
    val telemetry: MaherFieldTelemetry? = null,
    val lastOrder: MaherFieldOrder? = null,
    val isCheckingOut: Boolean = false,
    val activeTab: String = "home"
)

class MaherFieldViewModel(
    private val getCatalogUseCase: GetCatalogUseCase,
    private val manageCartUseCase: ManageCartUseCase,
    private val processCheckoutUseCase: ProcessCheckoutUseCase,
    private val observeHardwareTelemetryUseCase: ObserveHardwareTelemetryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MaherFieldUiState())
    val uiState: StateFlow<MaherFieldUiState> = _uiState.asStateFlow()

    init {
        // Observe Catalog
        getCatalogUseCase().onEach { list ->
            _uiState.update { it.copy(catalog = list, filteredCatalog = list) }
        }.launchIn(viewModelScope)

        // Observe Cart
        manageCartUseCase.cart.onEach { cartItems ->
            _uiState.update { it.copy(cart = cartItems) }
        }.launchIn(viewModelScope)

        // Observe Telemetry
        observeHardwareTelemetryUseCase().onEach { telem ->
            _uiState.update { it.copy(telemetry = telem) }
        }.launchIn(viewModelScope)
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            val filtered = if (category == "الكل") state.catalog else state.catalog.filter { it.category == category }
            state.copy(selectedCategory = category, filteredCatalog = filtered)
        }
    }

    fun switchTab(tab: String) {
        _uiState.update { it.copy(activeTab = tab) }
    }

    fun addToCart(item: MaherFieldItem) = manageCartUseCase.add(item)
    fun removeFromCart(item: MaherFieldItem) = manageCartUseCase.remove(item)

    fun checkout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isCheckingOut = true) }
            val order = processCheckoutUseCase()
            _uiState.update { it.copy(isCheckingOut = false, lastOrder = order, activeTab = "checkout") }
        }
    }
}
