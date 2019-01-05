package com.itis.itisservice.di.module

import com.itis.itisservice.db.CoursesRepository
import com.itis.itisservice.db.CoursesRepositoryImpl
import com.itis.itisservice.db.ProfileRepository
import com.itis.itisservice.db.ProfileRepositoryImpl
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
}