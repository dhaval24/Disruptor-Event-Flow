package com.dhaval.eventtranslators;

import com.dhaval.datamodels.EmployeEvent;
import com.dhaval.datamodels.Employee;
import com.lmax.disruptor.EventTranslatorVararg;

/**
 * Populates the EmployeeEvent object in Ring buffer with Actual Object Published
 */
public enum EmployeEventTranslator implements EventTranslatorVararg<EmployeEvent> {
    INSTANCE;

    @Override
    public void translateTo(EmployeEvent employeEvent, long l, Object... objects) {

        Employee employee2 = (Employee) objects[0];
        employeEvent.setEmployee(employee2);
    }
}
