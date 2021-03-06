package com.github.fabriciolfj.stream.inventory;

import com.github.fabriciolfj.domain.integration.broker.model.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class InventoryThreeStream {

    //@Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var inventorySerde = new JsonSerde<>(InventoryMessage.class);
        var longSerde = Serdes.Long();

        var inventoryStream = builder.stream("t.commodity.inventory", Consumed.with(stringSerde, inventorySerde));

        inventoryStream.print(Printed.toSysOut());

        inventoryStream.mapValues((k, v) ->
                v.getType().equalsIgnoreCase("ADD") ? v.getQuantity() : -1 * v.getQuantity()).groupByKey()
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde)).toStream()
                .to("t.commodity.inventory-total-three", Produced.with(stringSerde, longSerde));

        return inventoryStream;
    }
}
