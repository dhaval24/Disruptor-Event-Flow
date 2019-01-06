package com.dhaval.datamodels;

/**
 * Interface which represents common functionality for an employee
 */
public interface Employee {

    /**
     * Method which returns the name of the organization employee works for
     * @return
     */
    String worksFor();

    /**
     * Returns the name of the employee
     * @return
     */
    String getName();

    /**
     * Returns the address of the employee
     * @return Address
     */
    AbstractEmployee.Address getAddress();

    /**
     * Returns the company name where employee works
     * @return
     */
    String getCompanyName();

    /**
     * Sets the name of the employee
     * @param name
     */
    void setName(String name);

    /**
     * Sets the company name of the employee
     * @param name
     */
    void setCompanyName(String name);

    /**
     * Sets the address of the employee
     * @param address
     */
    void setAddress(AbstractEmployee.Address address);

    /**
     * Sets the parameter to decide if this employee should be sampled
     * @param sampled
     */
    void setSampled(boolean sampled);

    /**
     * Returns wether this employee is sampled or not
     * @return
     */
    boolean isSampled();

}
