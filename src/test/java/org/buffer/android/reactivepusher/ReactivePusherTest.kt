package org.buffer.android.reactivepusher

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pusher.client.Pusher
import com.pusher.client.channel.Channel
import com.pusher.client.channel.PresenceChannel
import com.pusher.client.channel.PrivateChannel
import org.buffer.android.reactivepusher.ErrorMessages.ERROR_TRIGGERING_EVENT
import org.buffer.android.reactivepusher.model.ChannelError
import org.buffer.android.reactivepusher.model.TriggerEventError
import org.buffer.android.reactivepusher.test.ChannelDataFactory
import org.buffer.android.reactivepusher.test.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ReactivePusherTest {

    private lateinit var mockPusher: Pusher

    private lateinit var reactivePusher: ReactivePusher

    @Before
    fun setup() {
        mockPusher = mock()
        reactivePusher = ReactivePusher(DataFactory.randomUuid())
    }

    //<editor-fold desc="Get Channel">
    @Test
    fun getChannelCompletes() {
        stubPusherGetChannel(ChannelDataFactory.makeChannel())

        val testObserver = reactivePusher.getChannel(DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getChannelReturnsChannel() {
        val channel = ChannelDataFactory.makeChannel()
        stubPusherGetChannel(channel)

        val testObserver = reactivePusher.getChannel(DataFactory.randomUuid()).test()
        testObserver.assertValue(channel)
    }

    @Test
    fun getChannelThrowsChannelErrorWhenChannelIsNull() {
        stubPusherGetChannel(null)

        val testObserver = reactivePusher.getChannel(DataFactory.randomUuid()).test()
        testObserver.assertError(ChannelError(ErrorMessages.CHANNEL_NOT_FOUND))
    }
    //</editor-fold>

    //<editor-fold desc="Get Private Channel">
    @Test
    fun getPrivateChannelCompletes() {
        stubPusherGetPrivateChannel(ChannelDataFactory.makePrivateCustomChannel())

        val testObserver = reactivePusher.getPrivateChannel(DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPrivateChannelReturnsChannel() {
        val channel = ChannelDataFactory.makePrivateCustomChannel()
        stubPusherGetPrivateChannel(channel)

        val testObserver = reactivePusher.getPrivateChannel(DataFactory.randomUuid()).test()
        testObserver.assertValue(channel)
    }

    @Test
    fun getPrivateChannelThrowsChannelErrorWhenChannelIsNull() {
        stubPusherGetPrivateChannel(null)

        val testObserver = reactivePusher.getPrivateChannel(DataFactory.randomUuid()).test()
        testObserver.assertError(ChannelError(ErrorMessages.CHANNEL_NOT_FOUND))
    }
    //</editor-fold>

    //<editor-fold desc="Get Presence Channel">
    @Test
    fun getPresenceChannelCompletes() {
        stubPusherGetPresenceChannel(ChannelDataFactory.makePresenceCustomChannel())

        val testObserver = reactivePusher.getPresenceChannel(DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPresenceChannelReturnsChannel() {
        val channel = ChannelDataFactory.makePresenceCustomChannel()
        stubPusherGetPresenceChannel(channel)

        val testObserver = reactivePusher.getPresenceChannel(DataFactory.randomUuid()).test()
        testObserver.assertValue(channel)
    }

    @Test
    fun getPresenceChannelThrowsChannelErrorWhenChannelIsNull() {
        stubPusherGetPresenceChannel(null)

        val testObserver = reactivePusher.getPrivateChannel(DataFactory.randomUuid()).test()
        testObserver.assertError(ChannelError(ErrorMessages.CHANNEL_NOT_FOUND))
    }
    //</editor-fold>

    //<editor-fold desc="Is Channel Subscribed">
    @Test
    fun isChannelSubscribedCompletes() {
        stubPusherGetChannel(ChannelDataFactory.makeChannel())

        val testObserver = reactivePusher.isChannelSubscribed(DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun isChannelSubscribedReturnsNotSubscribed() {
        stubPusherGetChannel(ChannelDataFactory.makeChannel())

        val testObserver = reactivePusher.isChannelSubscribed(DataFactory.randomUuid()).test()
        testObserver.assertValue(false)
    }

    @Test
    fun isChannelSubscribedReturnsSubscribed() {
        stubPusherGetChannel(ChannelDataFactory.makeSubscribedChannel())

        val testObserver = reactivePusher.isChannelSubscribed(DataFactory.randomUuid()).test()
        testObserver.assertValue(true)
    }
    //</editor-fold>

    //<editor-fold desc="Is Private Channel Subscribed">
    @Test
    fun isPrivateChannelSubscribedCompletes() {
        stubPusherGetPrivateChannel(ChannelDataFactory.makePrivateCustomChannel())

        val testObserver = reactivePusher.isPrivateChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun isPrivateChannelSubscribedReturnsNotSubscribed() {
        stubPusherGetPrivateChannel(ChannelDataFactory.makePrivateCustomChannel())

        val testObserver = reactivePusher.isPrivateChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertValue(false)
    }

    @Test
    fun isPrivateChannelSubscribedReturnsSubscribed() {
        stubPusherGetPrivateChannel(ChannelDataFactory.makeSubscribedPrivateCustomChannel())

        val testObserver = reactivePusher.isPrivateChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertValue(true)
    }
    //</editor-fold>

    //<editor-fold desc="Is Presence Channel Subscribed">
    @Test
    fun isPresenceChannelSubscribedCompletes() {
        stubPusherGetPresenceChannel(ChannelDataFactory.makePresenceCustomChannel())

        val testObserver = reactivePusher.isPresenceChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun isPresenceChannelSubscribedReturnsNotSubscribed() {
        stubPusherGetPresenceChannel(ChannelDataFactory.makePresenceCustomChannel())

        val testObserver = reactivePusher.isPresenceChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertValue(false)
    }

    @Test
    fun isPresenceChannelSubscribedReturnsSubscribed() {
        stubPusherGetPresenceChannel(ChannelDataFactory.makeSubscribedPresenceCustomChannel())

        val testObserver = reactivePusher.isPresenceChannelSubscribed(
                DataFactory.randomUuid()).test()
        testObserver.assertValue(true)
    }
    //</editor-fold>

    //<editor-fold desc="Trigger Event">
    @Test
    fun triggerEventCompletes() {
        stubPusherGetChannel(ChannelDataFactory.makeSubscribedPrivateCustomChannel())

        val testObserver = reactivePusher.triggerEvent(DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid()).test()
        testObserver.assertComplete()
    }

    @Test
    fun triggerEventThrowsErrorWhenNotSubscribed() {
        stubPusherGetChannel(ChannelDataFactory.makePrivateCustomChannel())

        val testObserver = reactivePusher.triggerEvent(DataFactory.randomUuid(),
                DataFactory.randomUuid(), DataFactory.randomUuid()).test()
        testObserver.assertError(TriggerEventError(ERROR_TRIGGERING_EVENT))
    }
    //</editor-fold>

    private fun stubPusherGetChannel(channel: Channel?) {
        whenever(mockPusher.getChannel(any()))
                .thenReturn(channel)
    }

    private fun stubPusherGetPrivateChannel(channel: PrivateChannel?) {
        whenever(mockPusher.getPrivateChannel(any()))
                .thenReturn(channel)
    }

    private fun stubPusherGetPresenceChannel(channel: PresenceChannel?) {
        whenever(mockPusher.getPresenceChannel(any()))
                .thenReturn(channel)
    }

}