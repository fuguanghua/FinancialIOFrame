package com.fuguanghua.remoting.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;

import com.alipay.remoting.rpc.RpcServer;

public class RemotingServer {

	public static void main(String[] args) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
		final RpcServer rpcServer = new RpcServer(1111);
		fixedThreadPool.execute(new Runnable() {
			public void run() {
				//RpcServer rpcServer = new RpcServer(1111);
		        //rpcServer.start();
				try {
					Assert.assertTrue(rpcServer.start());
		            Assert.fail("Should not reach here!");
				} catch (Exception e) {
					e.printStackTrace();
				} /*finally {
					rpcServer.stop();
					System.out.println("finally release...");
				}*/
			}
		});
		rpcServer.stop();
	}
}
