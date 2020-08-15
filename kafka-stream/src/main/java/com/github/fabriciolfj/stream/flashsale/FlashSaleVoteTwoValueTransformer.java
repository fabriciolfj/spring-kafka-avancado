package com.github.fabriciolfj.stream.flashsale;

import com.github.fabriciolfj.domain.integration.broker.model.FlashSaleVoteMessage;

import com.github.fabriciolfj.domain.integration.broker.model.FlashSaleVoteMessageNew;
import com.github.fabriciolfj.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessageNew> {

    private final long voteStartTime;
    private final long voteEndTime;
    private ProcessorContext context;

    public FlashSaleVoteTwoValueTransformer(final LocalDateTime voteStart, final LocalDateTime voteEnd) {
        this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(voteStart);
        this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(voteEnd);
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public FlashSaleVoteMessageNew transform(FlashSaleVoteMessage value) {
        var recordTime = this.context.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? toNew(value) : null;
    }

    private FlashSaleVoteMessageNew toNew(FlashSaleVoteMessage value) {
        var flash = new FlashSaleVoteMessageNew();
        flash.setCustomerId(value.getCustomerId());
        flash.setItemName(value.getItemName());
        return flash;
    }

    @Override
    public void close() {

    }
}
