This is a simple Micronaut application that reproduces a possible bug
in processing empty JSON streams by `RxStreamingHttpClient`

### Issue description

Fetching empty collections provided via HTTP JSON stream
produces exception in `RxStreamingHttpClient`

### Steps to Reproduce

1. Clone sample [git repository](https://github.com/paweusz/micronaut-empty-json-stream)
2. Execute `./gradlew clean test`

### Expected Behaviour

Empty JSON stream provided by `EmptyController#emptyIndex()` 
should be interpreted as empty `Flowable` 
by `RxStreamingHttpClient` in `DemoSpec`.

### Actual Behaviour

`RxStreamingHttpClient` in `DemoSpec` 
throws `com.fasterxml.jackson.core.io.JsonEOFException: Unexpected end-of-input`

Stack trace:

```java
com.fasterxml.jackson.core.io.JsonEOFException: Unexpected end-of-input
 at [Source: UNKNOWN; line: 1, column: 1]
	at io.micronaut.jackson.parser.JacksonProcessor.doOnComplete(JacksonProcessor.java:137)
	at io.micronaut.core.async.subscriber.SingleThreadedBufferingSubscriber.onComplete(SingleThreadedBufferingSubscriber.java:70)
	at io.reactivex.internal.util.HalfSerializer.onComplete(HalfSerializer.java:90)
	at io.reactivex.internal.subscribers.StrictSubscriber.onComplete(StrictSubscriber.java:109)
	at io.reactivex.internal.subscribers.BasicFuseableSubscriber.onComplete(BasicFuseableSubscriber.java:120)
	at io.micronaut.http.netty.reactive.HandlerPublisher.complete(HandlerPublisher.java:416)
	at io.micronaut.http.netty.reactive.HandlerPublisher.handlerRemoved(HandlerPublisher.java:403)
	at io.netty.channel.AbstractChannelHandlerContext.callHandlerRemoved(AbstractChannelHandlerContext.java:979)
	at io.netty.channel.DefaultChannelPipeline.callHandlerRemoved0(DefaultChannelPipeline.java:637)
	at io.netty.channel.DefaultChannelPipeline.remove(DefaultChannelPipeline.java:477)
	at io.netty.channel.DefaultChannelPipeline.remove(DefaultChannelPipeline.java:423)
	at io.micronaut.http.netty.stream.HttpStreamsHandler.removeHandlerIfActive(HttpStreamsHandler.java:388)
	at io.micronaut.http.netty.stream.HttpStreamsHandler.handleReadHttpContent(HttpStreamsHandler.java:253)
	at io.micronaut.http.netty.stream.HttpStreamsHandler.channelRead(HttpStreamsHandler.java:228)
	at io.micronaut.http.netty.stream.HttpStreamsClientHandler.channelRead(HttpStreamsClientHandler.java:189)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:436)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:321)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:295)
	at io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:251)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:357)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1410)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:379)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:365)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:919)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:714)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:650)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:576)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:493)
	at io.netty.util.concurrent.SingleThreadEventExecutor$4.run(SingleThreadEventExecutor.java:989)
	at io.netty.util.internal.ThreadExecutorMap$2.run(ThreadExecutorMap.java:74)
	at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
	at java.base/java.lang.Thread.run(Thread.java:834)
```

### Environment Information

- **Operating System**: Linux 5.7.16-200.fc32.x86_64
- **Micronaut Version:** 2.0.1
- **JDK Version:** 11.0.3

### Example Application

- https://github.com/paweusz/micronaut-empty-json-stream
