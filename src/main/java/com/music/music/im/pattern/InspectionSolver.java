package com.music.music.im.pattern;

import java.util.List;

/**
 * spring - 策略模式演示
 *
 * @author yzx
 * @date 2019-8-23
 */

public interface InspectionSolver {


    enum EventType {
        /**
         * a
         */
        TYPE_A,

        /**
         * b
         */
        TYPE_B,
    }

    /**
     * 解析器
     *
     * @param orderId  流水单编号
     * @param evenType 时间类型
     */
    void solve(String orderId, String evenType);

    /**
     * 支持事件类型
     *
     * @return r
     */
    List<String> supports();
}
