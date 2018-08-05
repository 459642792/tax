package com.blueteam.base.lang;


import org.apache.commons.lang3.StringUtils;

public class HostPort {
    private String host;
    private int port;

    public HostPort(String hostPort) {
        this(hostPort, 80);
    }

    public HostPort(String hostPort, int defaultPort) {
        this(hostPort, "127.0.0.1", defaultPort);
    }

    public HostPort(String hostPort, String defaultHost, int defaultPort) {
        if (StringUtils.isEmpty(hostPort)) {
            host = defaultHost;
            port = defaultPort;
        } else {
            String[] redisConnArr = hostPort.split(":");
            host = redisConnArr != null && redisConnArr.length > 0 ? redisConnArr[0].trim() : defaultHost;
            port = redisConnArr != null && redisConnArr.length > 1 ? Integer.parseInt(redisConnArr[1].trim()) : defaultPort;

        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
