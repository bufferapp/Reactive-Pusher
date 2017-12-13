package org.buffer.android.reactivepusher.test

import java.util.*

object DataFactory {

    private val sRandom = Random()

    fun randomUuid(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return sRandom.nextBoolean()
    }

    fun randomInt(): Int {
        return sRandom.nextInt(200) + Integer.SIZE - 1
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }

    fun randomStringList(count: Int): List<String> {
        val strings = mutableListOf<String>()
        repeat(count){
            strings.add(randomUuid())
        }
        return strings
    }

}