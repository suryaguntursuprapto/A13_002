package com.example.tugasakhirpam.repository

import com.example.tugasakhirpam.model.Event
import com.example.tugasakhirpam.service_api.EventService
import okio.IOException

interface EventRepository {

    suspend fun insertEvent(event: Event)

    suspend fun getAllEvents(): List<Event>

    suspend fun updateEvent(idEvent: String, event: Event)

    suspend fun deleteEvent(idEvent: String)

    suspend fun getEventById(idEvent: String): Event
}

class NetworkEventRepository(
    private val eventApiService: EventService
) : EventRepository {

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(idEvent: String, event: Event) {
        eventApiService.updateEvent(idEvent, event)
    }

    override suspend fun deleteEvent(idEvent: String) {
        try {
            val response = eventApiService.deleteEvent(idEvent)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete event. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllEvents(): List<Event> =
        eventApiService.getAllEvent()

    override suspend fun getEventById(idEvent: String): Event {
        return eventApiService.getEventById(idEvent)
    }
}