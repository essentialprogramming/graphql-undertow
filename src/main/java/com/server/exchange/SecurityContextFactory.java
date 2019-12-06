package com.server.exchange;

import java.security.AccessController;
import java.security.PrivilegedAction;

final class SecurityContextFactory {

  private SecurityContextFactory() {
    throw new IllegalStateException("Utility class");
  }

  static void setSecurityContext(final HttpExchange exchange,
      final SecurityContext securityContext) {
    if (System.getSecurityManager() == null) {
      exchange.setSecurityContext(securityContext);
    } else {
      AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
        exchange.setSecurityContext(securityContext);
        return null;
      });
    }
  }
}
