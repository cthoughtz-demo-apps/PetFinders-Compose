package com.animallovers.petfinder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.animallovers.petfinder.data.DataStoreRepo
import com.animallovers.petfinder.presentation.util.PetFinderResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: WelcomeViewModel
    private val mockRepository: DataStoreRepo = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WelcomeViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveOnBoardingState should call repository with true`() = runTest {
        // Given
        coEvery { mockRepository.saveOnBoardingState(any()) } returns Unit

        // When
        viewModel.saveOnBoardingState(true)

        // Then
        coVerify(exactly = 1) { mockRepository.saveOnBoardingState(completed = true) }
    }

    @Test
    fun `saveOnBoardingState should call repository with false`() = runTest {
        // Given
        coEvery { mockRepository.saveOnBoardingState(any()) } returns Unit

        // When
        viewModel.saveOnBoardingState(false)

        // Then
        coVerify(exactly = 1) { mockRepository.saveOnBoardingState(completed = false)}
    }

    @Test
    fun `saveOnBoardingState should use IO dispatcher`() = runTest {
        // Given
        coEvery { mockRepository.saveOnBoardingState(any()) } returns Unit

        // When
        viewModel.saveOnBoardingState(true)

        coVerify { mockRepository.saveOnBoardingState(completed = true) }
    }
}