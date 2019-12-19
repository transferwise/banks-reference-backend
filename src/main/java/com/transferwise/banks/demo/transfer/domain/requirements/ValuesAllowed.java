package com.transferwise.banks.demo.transfer.domain.requirements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ValuesAllowed {
        private String key;
        private String name;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public ValuesAllowed(String key, String name, Map<String, Object> additionalProperties) {
            this.key = key;
            this.name = name;
            this.additionalProperties = additionalProperties;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public Map<String, Object> getAdditionalProperties() {
            return additionalProperties;
        }
    }