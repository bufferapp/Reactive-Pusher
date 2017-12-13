package org.buffer.android.reactivepusher.model

import com.pusher.client.channel.User

class PresenceChannelEvent(val channelName: String, val eventName: String? = null,
                           val data: String ? = null, val user : User? = null)