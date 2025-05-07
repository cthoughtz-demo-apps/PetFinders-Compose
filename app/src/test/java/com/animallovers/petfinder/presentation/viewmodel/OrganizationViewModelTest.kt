package com.animallovers.petfinder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.animallovers.petfinder.domain.model.organization.GetOrganizationResponse
import com.animallovers.petfinder.domain.usecase.organization.GetOrganizationUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class OrganizationViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: OrganizationViewModel
    private val mockGetOrganizationUseCase: GetOrganizationUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OrganizationViewModel(mockGetOrganizationUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be None`() = runTest {
        viewModel.organizationResultFlow.test {
            assertEquals(PetFinderResult.None, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getOrganization should emit Loading then Success when useCase succeeds`() = runTest {
        // Given
        val expectedResponse = GetOrganizationResponse(
            GetOrganizationResponse.Organization(
                distance = "12.5",
                id = "ID243"
            )
        )

        coEvery {
            mockGetOrganizationUseCase.getOrganization(
                TEST_ID,
                "Bearer $TEST_TOKEN"
            )
        } returns
                PetFinderResult.Success(expectedResponse)

        viewModel.organizationResultFlow.test {
            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getOrganization(TEST_ID, TEST_TOKEN)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            val successResult = awaitItem() as PetFinderResult.Success
            assertEquals(expectedResponse, successResult.data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getOrganization should emit Loading then Error when useCase fails`() = runTest {

        // Given
        coEvery {
            mockGetOrganizationUseCase.getOrganization(
                TEST_ID,
                "Bearer $TEST_TOKEN"
            )
        } returns
                PetFinderResult.Failure(EXPECTED_ERROR)

        viewModel.organizationResultFlow.test {
            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getOrganization(TEST_ID, TEST_TOKEN)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            val errorResult = awaitItem() as PetFinderResult.Failure
            assertEquals(EXPECTED_ERROR, errorResult.errorMessage)
        }
    }

    @Test
    fun `getOrganization should pass correct parameters to useCase`() = runTest {
        // Given
        var captureId = ""
        var captureToken = ""

        coEvery { mockGetOrganizationUseCase.getOrganization(any(), any()) } coAnswers {
            captureId = firstArg()
            captureToken = secondArg()
            PetFinderResult.Success(GetOrganizationResponse(GetOrganizationResponse.Organization(id = "23353")))
        }

        // When
        viewModel.getOrganization(TEST_ID, TEST_TOKEN)

        // Then
        assertEquals(TEST_ID, captureId)
        assertEquals("Bearer $TEST_TOKEN", captureToken)
    }

    @Test
    fun `should handle empty organization response`() = runTest {
        // Given
        val emptyResponse = GetOrganizationResponse()

        coEvery { mockGetOrganizationUseCase.getOrganization(TEST_ID, any()) } returns
                PetFinderResult.Success(emptyResponse)

        viewModel.organizationResultFlow.test {
            // When
            viewModel.getOrganization(TEST_ID, TEST_TOKEN)

            // Then
            skipItems(2) // Skip None and Loading
            val successResult = awaitItem() as PetFinderResult.Success
            assertNull(successResult.data.organization?.id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should maintain Loading state while fetching data`() = runTest {
        // Given
        val expectedResponse =
            GetOrganizationResponse(GetOrganizationResponse.Organization(name = "JayMoney"))

        coEvery { mockGetOrganizationUseCase.getOrganization(TEST_ID,any()) } coAnswers  {
            delay(100) // Simulate network day
            PetFinderResult.Success(expectedResponse)
        }

        viewModel.organizationResultFlow.test {
            // When
            viewModel.getOrganization(TEST_ID, TEST_TOKEN)

            // Then
            assertEquals(PetFinderResult.None, awaitItem())
            assertEquals(PetFinderResult.Loading, awaitItem())

            // Loading state should persist until result arrives
            val result = awaitItem()
            assertTrue(result is PetFinderResult.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        const val TEST_ID = "org123"
        const val TEST_TOKEN = "test_token"
        const val EXPECTED_ERROR = "Test Error"
    }
}