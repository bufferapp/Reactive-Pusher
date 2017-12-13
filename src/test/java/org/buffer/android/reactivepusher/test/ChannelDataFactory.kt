package org.buffer.android.reactivepusher.test

import com.pusher.client.channel.*
import com.pusher.client.channel.impl.InternalChannel

object ChannelDataFactory {

    fun makeChannel(): InternalChannel {
        return PublicChannel()
    }

    fun makeSubscribedChannel(): InternalChannel {
        return PublicChannel(true)
    }

    fun makePrivateCustomChannel(): PrivateChannel {
        return PrivateCustomChannel()
    }

    fun makeSubscribedPrivateCustomChannel(): PrivateChannel {
        return PrivateCustomChannel(true)
    }

    fun makePresenceCustomChannel(): PresenceChannel {
        return PresenceCustomChannel()
    }

    fun makeSubscribedPresenceCustomChannel(): PresenceChannel {
        return PresenceCustomChannel(true)
    }

    class PublicChannel(private var isSubscribed: Boolean = false) : InternalChannel {

        override fun getEventListener(): ChannelEventListener {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getName(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun compareTo(other: InternalChannel?): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun unbind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setEventListener(p0: ChannelEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun bind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun updateState(p0: ChannelState?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onMessage(p0: String?, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toUnsubscribeMessage(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun toSubscribeMessage(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isSubscribed(): Boolean {
            return isSubscribed
        }
    }

    class PrivateCustomChannel(private var isSubscribed: Boolean = false) : PrivateChannel {

        override fun bind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getName(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun unbind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isSubscribed(): Boolean {
            return isSubscribed
        }

        override fun trigger(p0: String?, p1: String?) {

        }

    }

    class PresenceCustomChannel(private var isSubscribed: Boolean = false) : PresenceChannel {

        override fun bind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getName(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getMe(): User {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getUsers(): MutableSet<User> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun unbind(p0: String?, p1: SubscriptionEventListener?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isSubscribed(): Boolean {
            return isSubscribed
        }

        override fun trigger(p0: String?, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

}