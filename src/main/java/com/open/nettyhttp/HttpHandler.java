package com.open.nettyhttp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends SimpleChannelInboundHandler<Object> {

    private static final byte[] CONTENT = {'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd'};
    private boolean keepAlive;
    private String uri;

    public HttpHandler() {
        super();
        System.out.printf("控制器 %s 被创建.\n", this.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        System.out.printf("控制器 %s 销毁.\n", this.toString());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        System.out.printf("控制器 %s 读取一个包.\n", this.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("控制器 %s 出现异常.\n", this.toString());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            if (request.getMethod() != HttpMethod.GET) {
                throw new IllegalStateException("请求不是GET请求.");
            }

            if (HttpHeaders.is100ContinueExpected(request)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            uri = request.uri();
            keepAlive = HttpHeaders.isKeepAlive(request);
        }

        if(msg instanceof LastHttpContent){

            // 模拟事务处理
            TimeUnit.SECONDS.sleep(1);
            String content = "你访问的是： " + uri;
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content.getBytes()));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            if (!keepAlive) {
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                ctx.writeAndFlush(response);
            }
        }
    }
}