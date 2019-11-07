package com.transferwise.banks.demo.credentials.persistence.twuser;

import com.transferwise.banks.demo.credentials.domain.TWUser;

public interface TWUserPersistence {

    TWUser save(TWUser twUser);

}
