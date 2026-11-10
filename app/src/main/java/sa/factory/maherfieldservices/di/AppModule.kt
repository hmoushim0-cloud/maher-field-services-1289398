package sa.factory.maherfieldservices.di

import sa.factory.maherfieldservices.data.repository.MaherFieldRepositoryImpl
import sa.factory.maherfieldservices.domain.repository.MaherFieldRepository
import sa.factory.maherfieldservices.domain.usecase.*
import sa.factory.maherfieldservices.hardware.HardwareSensorDriver
import sa.factory.maherfieldservices.ui.viewmodel.MaherFieldViewModel

class DiContainer {
    val sensorDriver = HardwareSensorDriver()
    val repository: MaherFieldRepository = MaherFieldRepositoryImpl(sensorDriver)

    val getCatalogUseCase = GetCatalogUseCase(repository)
    val manageCartUseCase = ManageCartUseCase(repository)
    val processCheckoutUseCase = ProcessCheckoutUseCase(repository)
    val observeHardwareTelemetryUseCase = ObserveHardwareTelemetryUseCase(repository)

    fun provideViewModel(): MaherFieldViewModel = MaherFieldViewModel(
        getCatalogUseCase,
        manageCartUseCase,
        processCheckoutUseCase,
        observeHardwareTelemetryUseCase
    )
}
