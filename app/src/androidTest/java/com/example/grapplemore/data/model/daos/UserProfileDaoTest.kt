package com.example.grapplemore.data.model.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.grapplemore.data.model.AppDatabase
import com.example.grapplemore.data.model.entities.UserProfileEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class UserProfileDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var userProfileDao: UserProfileDao

    @Before
    fun setup() {
        hiltRule.inject()
        userProfileDao = database.getUserProfileDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertProfile() = runBlocking {

        val profile = UserProfileEntity("testOne", "testOne", "testOne",
            "testOne","testOne", 10, 10, 10, 10, 10)

        userProfileDao.upsertProfile(profile)

        val retrievedCorrectProfile = userProfileDao.getUserProfile("testOne")
        val retrievedIncorrectProfile = userProfileDao.getUserProfile("testTwo")

        assertThat(retrievedCorrectProfile).isEqualTo(profile)
        assertThat(retrievedIncorrectProfile).isNull()
    }

    @Test
    fun updateProfile() = runBlocking {

        val profile = UserProfileEntity("testOne", "testOne", "testOne",
            "testOne","testOne", 10, 10, 10, 10, 10)

        userProfileDao.upsertProfile(profile)

        val updatedProfile = UserProfileEntity("testOne", "updated", "updated",
            "updated","updated", 10, 10, 10, 10, 10)

        userProfileDao.upsertProfile(updatedProfile)

        val retrievedProfile = userProfileDao.getUserProfile("testOne")
        assertThat(retrievedProfile).isEqualTo(updatedProfile)

    }




}