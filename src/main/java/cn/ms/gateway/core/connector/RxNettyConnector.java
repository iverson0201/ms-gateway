package cn.ms.gateway.core.connector;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.client.HttpClient.HttpClientConfig.Builder;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import cn.ms.gateway.base.adapter.ICallback;
import cn.ms.gateway.base.connector.IConnector;
import cn.ms.gateway.entity.GatewayREQ;
import cn.ms.gateway.entity.GatewayRES;

public class RxNettyConnector implements IConnector<GatewayREQ, GatewayRES> {

	@Override
	public void init() throws Exception {
	}

	@Override
	public void start() throws Exception {

	}
	
	@Override
	public void connector(GatewayREQ req, ICallback<GatewayREQ, GatewayRES> callback, Object... args) throws Exception {
		HttpClientRequest<ByteBuf> request=HttpClientRequest.create(HttpVersion.HTTP_1_1, HttpMethod.POST, req.getRemoteURI());
		request=request.withContent(req.getClientContent());
		
		Builder builder=Builder.fromDefaultConfig().readTimeout(60, TimeUnit.SECONDS).responseSubscriptionTimeout(60, TimeUnit.SECONDS);
		Observable<HttpClientResponse<ByteBuf>> observable=RxNetty.createHttpRequest(request, builder.build());
		
		observable.flatMap(new Func1<HttpClientResponse<ByteBuf>, Observable<ByteBuf>>() {
        	@Override
        	public Observable<ByteBuf> call(HttpClientResponse<ByteBuf> response) {
        		return response.getContent();
        	}
		}).forEach(new Action1<ByteBuf>() {
			@Override
			public void call(ByteBuf data) {
				System.out.println("----"+data.toString(Charset.defaultCharset()));
			}
		});
	}
	
	@Override
	public void shutdown() throws Exception {
	}

}
