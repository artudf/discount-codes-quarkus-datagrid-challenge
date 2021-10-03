package com.redhat.challenge.discount.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;


@AutoProtoSchemaBuilder(includeClasses = { DiscountCode.class, DiscountCodeType.class },schemaPackageName = "discounts")
interface DiscountCodeSchema extends  GeneratedSchema{

}
