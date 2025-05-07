package com.animallovers.petfinder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalsUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetAnimalsViewModelTest {

    // InstantTaskExecutorRule helps testing LiveData and StateFlow
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: GetAnimalsViewModel
    private val mockGetAnimalsUseCase: GetAnimalsUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GetAnimalsViewModel(mockGetAnimalsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be None`() = runTest {
        viewModel.getAnimalsResultFlow.test {
            assertEquals(PetFinderResult.None, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAnimals should emit Loading then Success when useCase succeeds`() = runTest {

        // Given
        val testToken = "test_token"
        val expectedResponse = GetAnimalsResponse(emptyList())
        coEvery { mockGetAnimalsUseCase.getAnimals("Bearer $testToken") } returns
                PetFinderResult.Success(expectedResponse)

        viewModel.getAnimalsResultFlow.test {
            // Initial state
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getAnimals(testToken)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            assertEquals(PetFinderResult.Success(expectedResponse), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAnimals should emit Loading then Error when useCase fails`() = runTest {

        // Given
        val testToken = "test_token"
        val expectedError ="Test error"
        coEvery { mockGetAnimalsUseCase.getAnimals("Bearer $testToken") } returns
                PetFinderResult.Failure(expectedError)

        viewModel.getAnimalsResultFlow.test {

            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getAnimals(testToken)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            val errorResult = awaitItem() as PetFinderResult.Failure
            assertEquals(expectedError, errorResult.errorMessage)
        }
    }

    @Test
    fun `getAnimals should properly format authorization header`() = runTest {

        // Given
        val testToken = "test_token"
        val expectedResponse = GetAnimalsResponse(emptyList())
        var captureHeader = ""

        coEvery { mockGetAnimalsUseCase.getAnimals(any()) } coAnswers {
            captureHeader = firstArg()
            PetFinderResult.Success(expectedResponse)
        }

        // When
        viewModel.getAnimals(testToken)

        // Then
        assertEquals("Bearer $testToken", captureHeader)
    }
}