package com.animallovers.petfinder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.animallovers.petfinder.domain.model.animal.GetAnimalResponse
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import io.mockk.coEvery
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
class GetAnimalViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: GetAnimalViewModel
    private val mockGetAnimalUseCase: GetAnimalUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GetAnimalViewModel(mockGetAnimalUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be None`() = runTest {
        viewModel.getAnimalResultFlow.test {
            assertEquals(PetFinderResult.None, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAnimal should emit Loading then Success when useCase succeeds`() = runTest {
        // Given
        val testId = "123"
        val testToken = "test_token"
        val expectedResponse = GetAnimalResponse(GetAnimalResponse.Animal(name = "Fluffy"))

        coEvery { mockGetAnimalUseCase.getAnimal(testId, "Bearer $testToken") } returns
                PetFinderResult.Success(expectedResponse)

        viewModel.getAnimalResultFlow.test {
            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getAnimal(testId, testToken)


            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            assertEquals(PetFinderResult.Success(expectedResponse), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAnimal should emit Loading then Error when UseCase Fails`() = runTest {

        // Given
        val testId = "123"
        val testToken = "test_token"
        val expectedError = "Test Error"

        coEvery { mockGetAnimalUseCase.getAnimal(testId, "Bearer $testToken") } returns
                PetFinderResult.Failure(expectedError)

        viewModel.getAnimalResultFlow.test {
            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getAnimal(testId, testToken)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            val errorResult = awaitItem() as PetFinderResult.Failure
            assertEquals(expectedError, errorResult.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAnimal should properly format authorization header`() = runTest {

        // Given
        val testId = "123"
        val testToken = "test_token"
        val expectedResponse = GetAnimalResponse(GetAnimalResponse.Animal(name = "Fluffy"))
        var captureHeader = ""

        coEvery { mockGetAnimalUseCase.getAnimal(testId, any()) } coAnswers {
            captureHeader = secondArg()
            PetFinderResult.Success(expectedResponse)
        }

        // When
        viewModel.getAnimal(testId, testToken)

        // Then
        assertEquals("Bearer $testToken", captureHeader)
    }

    @Test
    fun `getAnimal should pass correct animal ID to UseCase`() = runTest {

        // Given
        val testId = "123"
        val testToken = "test_token"
        val expectedResponse = GetAnimalResponse(GetAnimalResponse.Animal(name = "Kurby"))
        var captureId = ""

        coEvery { mockGetAnimalUseCase.getAnimal(any(),any()) } coAnswers {
            captureId = firstArg()
            PetFinderResult.Success(expectedResponse)
        }

        // When
        viewModel.getAnimal(testId, testToken)

        // Then
        assertEquals(testId,captureId)
    }
}