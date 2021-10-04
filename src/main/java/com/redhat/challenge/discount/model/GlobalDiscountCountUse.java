package com.redhat.challenge.discount.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@RegisterForReflection
public class GlobalDiscountCountUse {

    private DiscountCodeType type;
    private long count;

    @ProtoFactory
    public GlobalDiscountCountUse(DiscountCodeType type, long count) {
        this.type = type;
        this.count = count;
    }

    @ProtoField(number = 1)
    public DiscountCodeType getType() {
        return type;
    }

    public void setType(DiscountCodeType type) {
        this.type = type;
    }

    @ProtoField(number = 2, defaultValue = "0" )
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
