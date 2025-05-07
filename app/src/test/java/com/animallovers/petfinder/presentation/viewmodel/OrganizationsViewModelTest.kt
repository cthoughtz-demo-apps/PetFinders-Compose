package com.animallovers.petfinder.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
import com.animallovers.petfinder.domain.usecase.organization.GetOrganizationsUseCase
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
class OrganizationsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: OrganizationsViewModel
    private val mockGetOrganizationsUseCase: GetOrganizationsUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = OrganizationsViewModel(mockGetOrganizationsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be None`() = runTest {
        viewModel.getOrganizationsResultFlow.test {
            assertEquals(PetFinderResult.None, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getOrganizations should emit Loading then Success when useCase Succeeds`() = runTest {

        // Given
        val testToken = "test_token"
        val expectedResponse = GetOrganizationsResponse(listOf())

        coEvery { mockGetOrganizationsUseCase.getOrganizations("Bearer $testToken") } returns
                PetFinderResult.Success(expectedResponse)

        viewModel.getOrganizationsResultFlow.test {

            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getOrganizations(testToken)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            assertEquals(PetFinderResult.Success(expectedResponse), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getOrganizations should emit Loading then Error when useCase fails`() = runTest {

        // Given
        coEvery { mockGetOrganizationsUseCase.getOrganizations(BEARER_TOKEN) } returns
                PetFinderResult.Failure(TEST_ERROR)

        viewModel.getOrganizationsResultFlow.test {

            // Initial State
            assertEquals(PetFinderResult.None, awaitItem())

            // When
            viewModel.getOrganizations(TEST_TOKEN)

            // Then
            assertEquals(PetFinderResult.Loading, awaitItem())
            val errorResult = awaitItem() as PetFinderResult.Failure
            assertEquals(TEST_ERROR, errorResult.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

        @Test
        fun `getOrganizations should properly format authorization header`() = runTest {

            val expectedResponse = GetOrganizationsResponse(listOf())

            var capturedHeader = ""

            coEvery { mockGetOrganizationsUseCase.getOrganizations(any()) } coAnswers {
                capturedHeader = firstArg()
                PetFinderResult.Success(expectedResponse)
            }

            // When
            viewModel.getOrganizations(TEST_TOKEN)

            // Then
            assertEquals("Bearer $TEST_TOKEN", capturedHeader)
        }

    @Test
    fun `should handle empty organization list response`() = runTest {
        // Given
        val emptyResponse = GetOrganizationsResponse(emptyList())

        coEvery { mockGetOrganizationsUseCase.getOrganizations(any()) } returns
                PetFinderResult.Success(emptyResponse)

        viewModel.getOrganizationsResultFlow.test {

            // When
            viewModel.getOrganizations(TEST_TOKEN)

            // Then
            skipItems(2) // Skip None and Loading
            val successResult = awaitItem() as PetFinderResult.Success
            assertEquals(0, successResult.data.organizations?.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle non-empty organization list response`() = runTest {
        // Given
        val orgList = listOf(
            GetOrganizationsResponse.Organization(
                address = GetOrganizationsResponse.Organization.Address(
                    address1 = "123 ABC Lane"
                )
            ),
            GetOrganizationsResponse.Organization(
                address = GetOrganizationsResponse.Organization.Address(
                    address1 = "987 XYZ Lane"
                )
            )
        )

        val response = GetOrganizationsResponse(orgList)

        coEvery { mockGetOrganizationsUseCase.getOrganizations(any()) } returns
                PetFinderResult.Success(response)

        viewModel.getOrganizationsResultFlow.test {
            // When
            viewModel.getOrganizations(TEST_TOKEN)

            // Then
            skipItems(2) // Skip None and Loading
            val successResult = awaitItem() as PetFinderResult.Success
            assertEquals(2, successResult.data.organizations?.size)
            assertEquals("123 ABC Lane", successResult.data.organizations?.get(0)?.address?.address1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        const val TEST_TOKEN = "test_token"
        const val TEST_ERROR = "Test Error"
        const val BEARER_TOKEN = "Bearer $TEST_TOKEN"
    }
}