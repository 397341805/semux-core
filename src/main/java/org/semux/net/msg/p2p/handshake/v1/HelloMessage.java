/**
 * Copyright (c) 2017-2020 The Semux Developers
 *
 * Distributed under the MIT software license, see the accompanying file
 * LICENSE or https://opensource.org/licenses/mit-license.php
 */
package org.semux.net.msg.p2p.handshake.v1;

import org.semux.Network;
import org.semux.crypto.Key;
import org.semux.net.msg.MessageCode;

public class HelloMessage extends HandshakeMessage {

    /**
     * Create a HELLO message.
     */
    public HelloMessage(Network network, short networkVersion, String peerId, String ip, int port,
            String clientId, long latestBlockNumber, Key coinbase) {
        super(MessageCode.HELLO, WorldMessage.class,
                network, networkVersion, peerId, ip, port, clientId,
                latestBlockNumber, coinbase);
    }

    /**
     * Parse a HELLO message from byte array.
     *
     * @param body
     */
    public HelloMessage(byte[] body) {
        super(MessageCode.HELLO, WorldMessage.class, body);
    }

    @Override
    public String toString() {
        return "WorldMessage [peer=" + peer + "]";
    }
}