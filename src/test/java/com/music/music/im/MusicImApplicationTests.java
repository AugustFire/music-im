package com.music.music.im;

import com.music.music.im.pattern.InspectionSolver;
import com.music.music.im.pattern.InspectionSolverChooser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.function.BiConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MusicImApplicationTests {

    @Autowired
    private InspectionSolverChooser chooser;

    @Test
    public void contextLoads() {
        Map<String, InspectionSolver> chooseMap = chooser.getChooseMap();
        chooseMap.forEach((k, v) -> log.info(k+"---------->"+v));
        InspectionSolver inspectionSolver = chooseMap.get(InspectionSolver.EventType.TYPE_A.name());
        log.info(inspectionSolver.toString());
        inspectionSolver.solve("wx2018", null);
    }

}
