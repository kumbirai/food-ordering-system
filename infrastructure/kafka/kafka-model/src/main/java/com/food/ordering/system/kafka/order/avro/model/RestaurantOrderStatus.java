/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package com.food.ordering.system.kafka.order.avro.model;

@org.apache.avro.specific.AvroGenerated
public enum RestaurantOrderStatus implements org.apache.avro.generic.GenericEnumSymbol<RestaurantOrderStatus>
{
    PAID;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"RestaurantOrderStatus\",\"namespace\":\"com.food.ordering.system.kafka.order.avro.model\",\"symbols\":[\"PAID\"]}");

    public static org.apache.avro.Schema getClassSchema()
    {
        return SCHEMA$;
    }

    @Override
    public org.apache.avro.Schema getSchema()
    {
        return SCHEMA$;
    }
}
