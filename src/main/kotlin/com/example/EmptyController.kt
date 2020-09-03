package com.example

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.reactivex.Flowable

@Controller
class EmptyController {

    @Get("/empty")
    @Produces(MediaType.APPLICATION_JSON_STREAM)
    fun emptyIndex(): Flowable<Foo> {
        return Flowable.empty()
    }

}
