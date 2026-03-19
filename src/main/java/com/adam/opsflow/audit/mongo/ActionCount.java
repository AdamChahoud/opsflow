package com.adam.opsflow.audit.mongo;

import org.springframework.beans.factory.annotation.Value;

public interface ActionCount {
    @Value("#{target._id}")
    String getAction();
    long getCount();
}
