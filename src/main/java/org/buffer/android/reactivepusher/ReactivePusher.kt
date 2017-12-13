package org.buffer.android.reactivepusher

import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.*
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import com.pusher.client.util.HttpAuthorizer
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.buffer.android.reactivepusher.ErrorMessages.ERROR_TRIGGERING_EVENT
import org.buffer.android.reactivepusher.model.*

class ReactivePusher {

    private var pusher: Pusher

    constructor(apiKey: String) {
        pusher = Pusher(apiKey)
        this.channels = mutableListOf<String>()
    }

    constructor(apiKey: String, authorizationUrl: String) {
        val options = PusherOptions().setAuthorizer(HttpAuthorizer(authorizationUrl))
        pusher = Pusher(apiKey, options)
        this.channels = mutableListOf<String>()
    }

    private val channels: MutableList<String>

    fun observeConnection(): Flowable<ConnectionStatus> {
        return Flowable.create({
            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(connectionStateChange: ConnectionStateChange) {
                    it.onNext(mapConnectionState(connectionStateChange.currentState))
                }

                override fun onError(message: String, code: String?, exception: Exception) {
                    it.onError(ConnectionError(message, code, exception))
                }
            })
        }, BackpressureStrategy.LATEST)
    }

    fun observeConnection(vararg filterBy: ConnectionState): Flowable<ConnectionStatus> {
        return Flowable.create({
            pusher.connect(object : ConnectionEventListener {
                override fun onConnectionStateChange(connectionStateChange: ConnectionStateChange) {
                    it.onNext(mapConnectionState(connectionStateChange.currentState))
                }

                override fun onError(message: String, code: String, exception: Exception) {
                    it.onError(ConnectionError(message, code, exception))
                }
            }, *filterBy)
        }, BackpressureStrategy.LATEST)
    }

    fun getChannel(channelName: String): Single<Channel> {
        return Single.create({
            var channel: Channel? = null
            try {
                channel = pusher.getChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel)
            } else {
                it.onError(ChannelError(ErrorMessages.CHANNEL_NOT_FOUND))
            }
        })
    }

    fun getPrivateChannel(channelName: String): Single<PrivateChannel> {
        return Single.create({
            var channel: PrivateChannel? = null
            try {
                channel = pusher.getPrivateChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel)
            } else {
                it.onError(ChannelError("That channel couldn't be found, please try another!"))
            }
        })
    }

    fun getPresenceChannel(channelName: String): Single<PresenceChannel> {
        return Single.create({
            var channel: PresenceChannel? = null
            try {
                channel = pusher.getPresenceChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel)
            } else {
                it.onError(ChannelError("That channel couldn't be found, please try another!"))
            }
        })
    }

    fun isChannelSubscribed(channelName: String): Single<Boolean> {
        return Single.create({
            var channel: Channel? = null
            try {
                channel = pusher.getChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel.isSubscribed)
            } else {
                it.onError(ChannelError("That channel couldn't be found, please try another!"))
            }
        })
    }

    fun isPrivateChannelSubscribed(channelName: String): Single<Boolean> {
        return Single.create({
            var channel: Channel? = null
            try {
                channel = pusher.getPrivateChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel.isSubscribed)
            } else {
                it.onError(ChannelError("That channel couldn't be found, please try another!"))
            }
        })
    }

    fun isPresenceChannelSubscribed(channelName: String): Single<Boolean> {
        return Single.create({
            var channel: Channel? = null
            try {
                channel = pusher.getPresenceChannel(channelName)
            } catch (e: IllegalArgumentException) {
                it.onError(ChannelError(e.message))
            }
            if (channel != null) {
                it.onSuccess(channel.isSubscribed)
            } else {
                it.onError(ChannelError("That channel couldn't be found, please try another!"))
            }
        })
    }

    fun triggerEvent(channelName: String, eventName: String, data: String): Completable {
        return Completable.create({
            val channel = pusher.getChannel(channelName) as PrivateChannel
            if (channel.isSubscribed) {
                channel.trigger(eventName, data)
                it.onComplete()
            } else {
                it.onError(TriggerEventError(ERROR_TRIGGERING_EVENT))
            }
        })
    }

    fun subscribeToChannel(channelName: String, vararg events: String): Flowable<ChannelEvent> {
        return Flowable.create({
            pusher.subscribe(channelName, object : ChannelEventListener {
                override fun onEvent(channelName: String, eventName: String, data: String) {
                    it.onNext(ChannelEvent(channelName, eventName, data))
                }

                override fun onSubscriptionSucceeded(p0: String) {}
            }, *events)
        }, BackpressureStrategy.LATEST)
    }

    fun subscribePrivateChannel(channelName: String, vararg events: String):
            Flowable<ChannelEvent> {
        return Flowable.create({
            if (!channels.contains(channelName)) {
                channels.add(channelName)

                pusher.subscribePrivate(channelName, object : PrivateChannelEventListener {

                    override fun onAuthenticationFailure(p0: String?, p1: java.lang.Exception?) {

                    }

                    override fun onSubscriptionSucceeded(p0: String?) {

                    }

                    override fun onEvent(channelName: String, eventName: String, data: String) {
                        it.onNext(ChannelEvent(channelName, eventName, data))
                    }

                }, *events)

            }
        }, BackpressureStrategy.LATEST)
    }

    fun bindPrivateChannelEvent(channelName: String, event: String): Flowable<ChannelEvent> {
        return Flowable.create({
            if (!channels.contains(channelName)) {
                channels.add(channelName)
                pusher.subscribePrivate(channelName).bind(event,
                        object : PrivateChannelEventListener {

                            override fun onAuthenticationFailure(message: String,
                                                                 exception: java.lang.Exception) {
                                it.onError(AuthenticationError(message, exception))
                            }

                            override fun onSubscriptionSucceeded(p0: String?) {

                            }

                            override fun onEvent(channelName: String,
                                                 eventName: String,
                                                 data: String) {
                                it.onNext(ChannelEvent(channelName, eventName, data))
                            }

                        })

            }
        }, BackpressureStrategy.LATEST)
    }

    private fun mapConnectionState(connectionState: ConnectionState): ConnectionStatus {
        when (connectionState) {
            ConnectionState.CONNECTING -> {
                return ConnectionStatus.CONNECTING
            }
            ConnectionState.CONNECTED -> {
                return ConnectionStatus.CONNECTED
            }
            ConnectionState.DISCONNECTING -> {
                return ConnectionStatus.DISCONNECTING
            }
            ConnectionState.DISCONNECTED -> {
                return ConnectionStatus.DISCONNECTED
            }
            ConnectionState.RECONNECTING -> {
                return ConnectionStatus.RECONNECTING
            }
            else -> {
                return ConnectionStatus.ALL
            }
        }
    }

}