package com.project.entity.windowing;

import com.project.entity.core.Tuple;
import com.project.use_cases.core.game.GameInteractor;

public interface WindowInformationGetter {
    default Tuple<Integer, Integer> getActualWindowSize() {
        return GameInteractor.getInstance().getPrimaryWindow().getActualWindowSize();
    }
}
