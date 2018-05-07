package com.kwetter

import com.kwetter.models.Kweet
import junit.framework.Assert.assertEquals
import org.junit.Test
import java.time.Duration.ofMillis
import java.time.Duration.ofSeconds
import java.util.Date


class StressTest {

//    @Test
//    fun makeOneKweet() {
//        assertTimeout(ofMillis(1)) {
//            val kweets: MutableList<Kweet> = ArrayList()
//            for (i in 1..1) {
//                kweets.add(Kweet(null, "text",
//                                 Date(2018, 4, 8, 6, 55)))
//            }
//            assertEquals(1, kweets.size)
//        }
//    }
//
//    @Test
//    fun makeTwentyThousandKweets() {
//        assertTimeout(ofSeconds(3)) {
//            val kweets: MutableList<Kweet> = ArrayList()
//            for (i in 1..20000) {
//                kweets.add(Kweet(null, "text",
//                                 Date(2018, 4, 8, 6, 55)))
//            }
//            assertEquals(20000, kweets.size)
//        }
//    }
//
//    @Test
//    fun makeOneMillionKweets() {
//        assertTimeout(ofSeconds(5)) {
//            val kweets: MutableList<Kweet> = ArrayList()
//            for (i in 1..1000000) {
//                kweets.add(Kweet(null, "text",
//                                 Date(2018, 4, 8, 6, 55)))
//            }
//            assertEquals(1000000, kweets.size)
//        }
//    }
}