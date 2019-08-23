package com.music.music.im.pattern.handler;

import com.google.common.collect.ImmutableList;
import com.music.music.im.pattern.InspectionSolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yzx
 * @date 2019-8-23
 */
@Component
@Slf4j
public class SolverA implements InspectionSolver {

    @Override
    public void solve(String orderId, String evenType) {
        log.info("订单[{}]开始发货了", orderId);
    }

    @Override
    public List<String> supports() {
        return ImmutableList.of(EventType.TYPE_A.name());
    }

}
