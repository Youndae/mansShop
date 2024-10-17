package org.shop.service;

import java.security.Principal;

public interface PrincipalService {

    String getUserIdToPrincipal(Principal principal);
}
