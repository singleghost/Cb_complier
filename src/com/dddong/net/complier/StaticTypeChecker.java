package com.dddong.net.complier;

import com.dddong.net.type.Type;
import com.dddong.net.type.TypeTable;
import com.dddong.net.utils.ErrorHandler;

/**
 * Created by dddong on 2017/5/27.
 */
public class StaticTypeChecker {
    ErrorHandler errorHandler;
    TypeTable table;

    public StaticTypeChecker(ErrorHandler errorHandler, TypeTable table) {
        this.errorHandler = errorHandler;
        this.table = table;
    }

    public void semanticCheck() {
        table.semanticCheck(errorHandler);
    }
}
