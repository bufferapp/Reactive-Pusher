# Reactive-Pusher
This project us a simple RxJava wrapper for the [Pusher](https://pusher.com/) Java Library, allowing you to use Pusher in your Android applications keeping your reactive workflow in place 🙌🏻

# Functionality

Reactive-Pusher currently supports operations related to the subscription to public and private channels.

## Connecting to pusher

You can use the [observeConnection()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L34) method to observe the connection 

    reactivePusher.observeConnection().subscribe({
        when (it) {
            ConnectionStatus.CONNECTED -> { }
            ConnectionStatus.CONNECTING -> { }
            ConnectionStatus.DISCONNECTING -> { }
            ConnectionStatus.DISCONNECTED -> { }
            ConnectionStatus.RECONNECTING -> { }
            ConnectionStatus.UNKNOWN -> { }
        }
    }))
    
There is also an [observeConnection(varargs filter: String)](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L48) that allows you to pass a collection of ConnectionEvents which you wish to exclude the callback being triggered.

## Getting channels

You can retrieve channels using either the [getChannel()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L62), [getPrivateChannel()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L78) or [getPresenceChannel()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L94) methods.

    reactivePusher.getChannel("some channel name")
        .subscribe({ // do something with the channel })

## Checking channel subscription state

You can also use the [isChannelSubscribed()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L110), [isPrivateChannelSubscribed()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L126) and [isPresenceChannelSubscribed()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L142) methods to check the subscription state of a channel.

    reactivePusher.isChannelSubscribed("some channel name")
        .subscribe({ // do something with the channel subscription result })

## Subscribing to channels

Using the [subscribeToChannel()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L142), [subscribeToPrivateChannel()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L182) or [bindToPrivateChannelEvent()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L208) methods allow you to subscribe to events from a given channel.

When subscribed to a channel you need to pass a collection of events that you wish to subscribe to, when binding you only need to pass a single event that you wish to bind to.

When an event is received you will get an instance of a [ChannelEvent](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/model/ChannelEvent.kt) from the callback.

    reactivePusher.subscribeToChannel("some channel name")
        .subscribe({ // do something with the channel event })
        
## Triggering events

You can also trigger events from this library by using the [triggerEvent()](https://github.com/bufferapp/Reactive-Pusher/blob/master/src/main/java/org/buffer/android/reactivepusher/ReactivePusher.kt#L158) method. 

    reactivePusher.triggerEvent("some channel name", "some event name", "some data")
        .subscribe({ // do something with the trigger completion })

# Using the library

The library is still in development, so please use it as provided. Currently you need to use jitpack in order to use this library, which can be done by following the instructions at [Jitpack](https://github.com/jitpack/jitpack.io)
