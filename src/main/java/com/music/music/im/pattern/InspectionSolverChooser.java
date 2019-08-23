package com.music.music.im.pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * spring - 策略模式演示
 *
 * @author yzx
 * @date 2019-8-23
 */
@Slf4j
@Component
public class InspectionSolverChooser {


    private final ApplicationContext context;

    @Autowired
    public InspectionSolverChooser(ApplicationContext context) {
        this.context = context;
    }

    @Getter
    @Setter
    private Map<String, InspectionSolver> chooseMap = new HashMap<>(4);


    @PostConstruct
    public void register() {
        Assert.notNull(context, "empty_context");
        Map<String, InspectionSolver> solveMap = context.getBeansOfType(InspectionSolver.class);
        solveMap.values().forEach(i -> i.supports().forEach(ii -> {
            chooseMap.put(ii, i);
        }));
    }
}
