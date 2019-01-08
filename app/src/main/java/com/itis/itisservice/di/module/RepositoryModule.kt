package com.itis.itisservice.di.module

import com.itis.itisservice.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideCoursesRepository(): CoursesRepository {
        return CoursesRepositoryImpl()
    }

    @Singleton
    @Provides
    fun providePointsRepository(): PointsRepository {
        return PointsRepositoryImpl()
    }
}