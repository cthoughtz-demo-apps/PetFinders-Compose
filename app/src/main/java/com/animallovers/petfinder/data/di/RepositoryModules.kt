package com.animallovers.petfinder.data.di

import com.animallovers.petfinder.domain.repository.animal.GetASingleAnimalTypeRepository
import com.animallovers.petfinder.domain.repository.animal.GetASingleAnimalTypeRepositoryImpl
import com.animallovers.petfinder.domain.repository.animal.GetAnimalBreedsRepository
import com.animallovers.petfinder.domain.repository.animal.GetAnimalBreedsRepositoryImpl
import com.animallovers.petfinder.domain.repository.animal.GetAnimalRepository
import com.animallovers.petfinder.domain.repository.animal.GetAnimalRepositoryImpl
import com.animallovers.petfinder.domain.repository.animal.GetAnimalTypesRepository
import com.animallovers.petfinder.domain.repository.animal.GetAnimalTypesRepositoryImpl
import com.animallovers.petfinder.domain.repository.animal.GetAnimalsRepository
import com.animallovers.petfinder.domain.repository.animal.GetAnimalsRepositoryImpl
import com.animallovers.petfinder.domain.repository.organization.GetOrganizationRepository
import com.animallovers.petfinder.domain.repository.organization.GetOrganizationRepositoryImpl
import com.animallovers.petfinder.domain.repository.organization.GetOrganizationsRepository
import com.animallovers.petfinder.domain.repository.organization.GetOrganizationsRepositoryImpl
import com.animallovers.petfinder.domain.repository.token.GetAccessTokenImpl
import com.animallovers.petfinder.domain.repository.token.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {

    @Singleton
    @Binds
    abstract fun provideTokenRepository(tokenRepositoryImpl: GetAccessTokenImpl) : TokenRepository

    @Singleton
    @Binds
    abstract fun provideGetAnimalsRepository(animalsRepositoryImpl: GetAnimalsRepositoryImpl) : GetAnimalsRepository

    @Singleton
    @Binds
    abstract fun provideGetBreedsRepository(getAnimalBreedsRepositoryImpl: GetAnimalBreedsRepositoryImpl) : GetAnimalBreedsRepository

    @Singleton
    @Binds
    abstract fun provideGetAnimalRepository(getAnimalRepositoryImpl: GetAnimalRepositoryImpl) : GetAnimalRepository

    @Singleton
    @Binds
    abstract fun provideGetAnimalTypesRepository(getAnimalTypeRepositoryImpl: GetAnimalTypesRepositoryImpl) : GetAnimalTypesRepository

    @Singleton
    @Binds
    abstract fun provideGetASingleAnimalTypeRepository(getASingleAnimalTypeRepositoryImpl: GetASingleAnimalTypeRepositoryImpl) : GetASingleAnimalTypeRepository

    @Singleton
    @Binds
    abstract fun provideGetOrganizationRepository(getOrganizationRepositoryImpl: GetOrganizationRepositoryImpl): GetOrganizationRepository

    @Singleton
    @Binds
    abstract fun provideGetOrganizationsRepository(getOrganizationsRepositoryImpl: GetOrganizationsRepositoryImpl) : GetOrganizationsRepository
}