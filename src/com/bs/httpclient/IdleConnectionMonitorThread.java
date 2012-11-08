package com.bs.httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.conn.ClientConnectionManager;

public class IdleConnectionMonitorThread extends Thread {
	private final ClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					// �رչ�������
					connMgr.closeExpiredConnections();
					// ��ѡ�أ��رտ��г���30�������
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {
			// ��ֹ
		}
	}

	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
