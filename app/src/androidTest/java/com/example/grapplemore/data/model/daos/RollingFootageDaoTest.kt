package com.example.grapplemore.data.model.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.grapplemore.data.model.AppDatabase
import com.example.grapplemore.data.model.entities.RollingFootage
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import getOrAwaitValue
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
class RollingFootageDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var rollingFootageDao: RollingFootageDao

    @Before
    fun setup() {
        hiltRule.inject()
        rollingFootageDao = database.getRollingFootageDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    /*
    Tests to confirm the functionality of the database and rolling footage dao
     */

    @Test
    fun insertRollingFootage() = runBlocking {

        val testFootage = RollingFootage(1, "testOne","testOne",
            "testOne","testOne")

        rollingFootageDao.insertRollingFootage(testFootage)
        val allRollingFootage = rollingFootageDao.getAll("testOne").getOrAwaitValue()

        assertThat(allRollingFootage).contains(testFootage)
    }

    @Test
    fun updateRollingFootage() = runBlocking {

        val testFootage = RollingFootage(1, "testOne","testOne",
            "testOne","testOne")

        rollingFootageDao.insertRollingFootage(testFootage)
        val updatedFootage = RollingFootage(1, "updated", "updated",
            "updated", "testOne")

        rollingFootageDao.insertRollingFootage(updatedFootage)
        val retrieveUpdated = rollingFootageDao.getAll("testOne").getOrAwaitValue()
        val retrieved = retrieveUpdated[0]

        assertThat(retrieved).isEqualTo(updatedFootage)

    }

    @Test
    fun deleteRollingFootage() = runBlocking {

        val testFootage = RollingFootage(1, "testOne","testOne",
            "testOne","testOne")

        rollingFootageDao.insertRollingFootage(testFootage)
        rollingFootageDao.deleteRollingFootage(testFootage)
        val allRollingFootage = rollingFootageDao.getAll("testOne").getOrAwaitValue()

        assertThat(allRollingFootage).doesNotContain(testFootage)
    }

    @Test
    fun footagePerUser() = runBlocking {

        val testFootageOne = RollingFootage(1, "testOne","testOne",
            "testOne","testOne")

        val testFootageTwo = RollingFootage(2, "testTwo", "testTwo",
        "testTwo", "testTwo")

        rollingFootageDao.insertRollingFootage(testFootageOne)
        rollingFootageDao.insertRollingFootage(testFootageTwo)

        val userOneFootage = rollingFootageDao.getAll("testOne").getOrAwaitValue()
        val userTwoFootage = rollingFootageDao.getAll("testTwo").getOrAwaitValue()

        assertThat(userOneFootage).contains(testFootageOne)
        assertThat(userTwoFootage).contains(testFootageTwo)

        assertThat(userOneFootage).doesNotContain(testFootageTwo)
        assertThat(userTwoFootage).doesNotContain(testFootageOne)

    }

    @Test
    fun footageByTitle() = runBlocking {

        val testFootageOne = RollingFootage(1, "testOne","testOne",
            "testOne","testOne")

        val testFootageTwo = RollingFootage(2, "testTwo", "testTwo",
            "testTwo", "testOne")

        rollingFootageDao.insertRollingFootage(testFootageOne)
        rollingFootageDao.insertRollingFootage(testFootageTwo)

        val titleFirst = rollingFootageDao.getFootageByTitle("testOne", "test").getOrAwaitValue()
        val titleSecond = rollingFootageDao.getFootageByTitle("testOne", "testTwo").getOrAwaitValue()

        assertThat(titleFirst).contains(testFootageOne)
        assertThat(titleFirst).contains(testFootageTwo)
        assertThat(titleSecond).doesNotContain(testFootageOne)
        assertThat(titleSecond).contains(testFootageTwo)
    }
}