package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxStreamingHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class DemoSpec extends Specification {

    @Inject
    @Client('/')
    RxStreamingHttpClient client

    def "fetching empty stream"() {
        when:
        def foos = client.jsonStream(HttpRequest.GET("/empty"), Foo)

        then:
        foos.blockingIterable().iterator().toList() == []
    }

}