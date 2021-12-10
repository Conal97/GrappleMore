package com.example.grapplemore.data.model.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.grapplemore.data.model.AppDatabase
import com.example.grapplemore.data.model.daos.TrainingEventDao
import com.example.grapplemore.data.model.entities.TrainingEvent
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
class TrainingEventDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var trainingEventDao: TrainingEventDao

    @Before
    fun setup() {
        hiltRule.inject()
        trainingEventDao = database.getTrainingEventDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertTrainingEvent() = runBlocking {

        val trainingEvent = TrainingEvent(1, "testOne", 10, 20,
            1, "test", "test", "test", "testOne")

        trainingEventDao.upsertTrainingEvent(trainingEvent)
        val allEvents = trainingEventDao.getAllTrainingEvents("testOne").getOrAwaitValue()

        assertThat(allEvents).contains(trainingEvent)
    }

    @Test
    fun updateTrainingEvent() = runBlocking {

        val trainingEvent = TrainingEvent(1, "testOne", 10, 20,
            1, "test", "test", "test", "testOne")

        trainingEventDao.upsertTrainingEvent(trainingEvent)


        val updatedEvent = TrainingEvent(1, "udpated", 10, 20, 1,
        "updated", "updated", "updated", "testOne")

        trainingEventDao.upsertTrainingEvent(updatedEvent)
        val allEvents = trainingEventDao.getAllTrainingEvents("testOne").getOrAwaitValue()
        val retrievedUpdated = allEvents[0]

        assertThat(retrievedUpdated).isEqualTo(updatedEvent)
    }

    @Test
    fun deleteTrainingEvent() = runBlocking {

        val trainingEvent = TrainingEvent(1, "testOne", 10, 20,
            1, "test", "test", "test", "testOne")

        trainingEventDao.upsertTrainingEvent(trainingEvent)
        trainingEventDao.deleteTrainingEvent(trainingEvent)
        val allEvents = trainingEventDao.getAllTrainingEvents("testOne").getOrAwaitValue()

        assertThat(allEvents).doesNotContain(trainingEvent)

    }

    @Test
    fun eventPerUser() = runBlocking {

        val eventOne = TrainingEvent(1, "testOne", 10, 20,
        1, "testOne", "testOne", "testOne", "testOne")

        val eventTwo = TrainingEvent(2, "testTwo", 10, 20,
        2, "testTwo", "testTwo", "testTwo", "testTwo")

        trainingEventDao.upsertTrainingEvent(eventOne)
        trainingEventDao.upsertTrainingEvent(eventTwo)

        val userOneEvent = trainingEventDao.getAllTrainingEvents("testOne").getOrAwaitValue()
        val userTwoEvent = trainingEventDao.getAllTrainingEvents("testTwo").getOrAwaitValue()

        assertThat(userOneEvent).contains(eventOne)
        assertThat(userTwoEvent).contains(eventTwo)

        assertThat(userOneEvent).doesNotContain(eventTwo)
        assertThat(userTwoEvent).doesNotContain(eventOne)
    }

    @Test
    fun getUpcoming() = runBlocking {

        val eventOne = TrainingEvent(1, "testOne", 10, 20,
            1, "testOne", "testOne", "testOne", "testOne")

        val eventTwo = TrainingEvent(2, "testTwo", 8, 20,
            2, "testTwo", "testTwo", "testTwo", "testOne")

        trainingEventDao.upsertTrainingEvent(eventOne)
        trainingEventDao.upsertTrainingEvent(eventTwo)

        val upcomingEvents = trainingEventDao.getUpcomingTrainingEvents("testOne", 9)
            .getOrAwaitValue()

        assertThat(upcomingEvents).contains(eventOne)
        assertThat(upcomingEvents).doesNotContain(eventTwo)
    }

    @Test
    fun getPrevious() = runBlocking {

        val eventOne = TrainingEvent(1, "testOne", 10, 20,
            1, "testOne", "testOne", "testOne", "testOne")

        val eventTwo = TrainingEvent(2, "testTwo", 8, 20,
            2, "testTwo", "testTwo", "testTwo", "testOne")

        trainingEventDao.upsertTrainingEvent(eventOne)
        trainingEventDao.upsertTrainingEvent(eventTwo)

        val upcomingEvents = trainingEventDao.getPreviousTrainingEvents("testOne", 9)
            .getOrAwaitValue()

        assertThat(upcomingEvents).contains(eventTwo)
        assertThat(upcomingEvents).doesNotContain(eventOne)
    }
}