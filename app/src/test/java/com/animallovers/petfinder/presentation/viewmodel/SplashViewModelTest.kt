package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.data.DataStoreRepo
import com.animallovers.petfinder.presentation.navigation.Pages
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: SplashViewModel
    private val mockRepository: DataStoreRepo = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should navigate to welcome when onboarding not completed`() = runTest {
        // Given
        coEvery { mockRepository.readOnBoardingState() } returns flowOf(false)

        // When
        viewModel = SplashViewModel(mockRepository)

        // Wait for initialization to complete
        viewModel.viewModelScope.launch {  }.join()

        // Then
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(Pages.Welcome.route, viewModel.startDestination.value)
    }

    @Test
    fun `should navigate  to home when onboarding is completed` () = runTest {
        // Given
        coEvery { mockRepository.readOnBoardingState() } returns flowOf(true)

        // When
        viewModel = SplashViewModel(mockRepository)

        // Wait for initialization to complete
        viewModel.viewModelScope.launch {  }.join()

        // Then
        assertEquals(false, viewModel.isLoading.value)
        assertEquals(Pages.Home.route, viewModel.startDestination.value)
    }
}