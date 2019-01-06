package com.dhaval.datamodels;

/**
 * A container responsible for storing the employee object.
 * This helps to initialize the disruptor queue.
 */
public class EmployeEvent implements Clearable {

    /**
     * The associated Employee
     */
    private Employee employee;

    /**
     * Sets the underlying Employee object to null, hence ensuring gc happens.
     */
    public void clear() {
        employee = null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
