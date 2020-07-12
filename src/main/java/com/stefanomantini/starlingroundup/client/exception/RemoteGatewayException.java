package com.stefanomantini.starlingroundup.client.exception;

// TODO tidy up exception handling intra-layer, abstract exception should be extended by Business
// and Technical, which would in turn have sub-classifications i.e. remotegateway etc
public class RemoteGatewayException extends Throwable {
  public RemoteGatewayException(final String exception) {
    super(exception);
  }
}
