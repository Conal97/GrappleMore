package com.example.grapplemore.data.model.daos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.grapplemore.data.model.AppDatabase
import com.example.grapplemore.data.model.entities.ArchiveEntry
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ArchiveEntryDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var archiveEntryDao: ArchiveEntryDao

    @Before
    fun setup() {
        hiltRule.inject()
        archiveEntryDao = database.getArchiveEntryDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    /*
    Tests to confirm the functionality of the database and archive entry dao
     */

    @Test
    fun insertArchiveEntry() = runBlockingTest {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)
        val allArchiveEntries = archiveEntryDao.getAllUserArchiveEntries("TestOne").getOrAwaitValue()

        assertThat(allArchiveEntries).contains(archiveEntry)
    }

    @Test
    fun updateArchiveEntry() = runBlocking {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)

        val updatedEntry = ArchiveEntry(1, "updated", "updated",
            "updated", "updated", "TestOne")

        archiveEntryDao.upsertArchiveEntry(updatedEntry)
        val retrieveUpdated = archiveEntryDao.getAllUserArchiveEntries("TestOne").getOrAwaitValue()
        val retrieved = retrieveUpdated[0]

        assertThat(retrieved).isEqualTo(updatedEntry)

    }

    @Test
    fun getEntriesByID() = runBlocking {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)
        val allArchiveEntries = archiveEntryDao.getAllUserArchiveEntries("TestTwo").getOrAwaitValue()

        assertThat(allArchiveEntries).doesNotContain(archiveEntry)
    }

    @Test
    fun deleteEntry() = runBlocking {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)
        archiveEntryDao.deleteArchiveEntry(archiveEntry)
        val allArchiveEntries = archiveEntryDao.getAllUserArchiveEntries("TestOne").getOrAwaitValue()

        assertThat(allArchiveEntries).doesNotContain(archiveEntry)
    }

    @Test
    fun getByTitle() = runBlocking {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)

        val correctEntries = archiveEntryDao.getByTitle("TestOne", "Test").getOrAwaitValue()
        val incorrectEntries = archiveEntryDao.getByTitle("TestOne", "TestTwo").getOrAwaitValue()

        assertThat(correctEntries).contains(archiveEntry)
        assertThat(incorrectEntries).doesNotContain(archiveEntry)
    }

    @Test
    fun getByCategory() = runBlocking{

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)

        val correctEntries = archiveEntryDao.getByCategory("TestOne", "TestOne").getOrAwaitValue()
        val incorrectEntries = archiveEntryDao.getByCategory("TestOne", "TestTwo").getOrAwaitValue()

        assertThat(correctEntries).contains(archiveEntry)
        assertThat(incorrectEntries).doesNotContain(archiveEntry)
    }

    @Test
    fun getByCategoryAndTitle() = runBlocking {

        val archiveEntry = ArchiveEntry(1, "TestOne", "TestOne", "TestOne",
            "TestOne", "TestOne")
        archiveEntryDao.upsertArchiveEntry(archiveEntry)

        val correctEntries = archiveEntryDao.getByCategoryAndTitle("TestOne", "TestOne", "TestOne").getOrAwaitValue()
        val incorrectTitle = archiveEntryDao.getByCategoryAndTitle("TestOne", "TestTwo", "TestOne").getOrAwaitValue()
        val incorrectCategory = archiveEntryDao.getByCategoryAndTitle("TestOne", "TestOne", "TestTwo").getOrAwaitValue()

        assertThat(correctEntries).contains(archiveEntry)
        assertThat(incorrectTitle).doesNotContain(archiveEntry)
        assertThat(incorrectCategory).doesNotContain(archiveEntry)
    }
}