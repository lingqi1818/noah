package com.fangcloud.noah.service.processor.impl;

import org.springframework.stereotype.Service;

import com.fangcloud.noah.service.processor.EventProcessor;

import java.util.Map;

/**
 * Created by chenke on 16-8-23.
 */
@Service
public class DefaultEventProcessor extends EventProcessor {

    @Override
    protected void fillEventMap(Map<String, Object> eventMap) {

    }
}
