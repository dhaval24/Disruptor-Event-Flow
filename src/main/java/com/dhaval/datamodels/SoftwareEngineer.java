package com.dhaval.datamodels;

/**
 * Class that represents software engineer
 */
public class SoftwareEngineer extends AbstractEmployee {

    private final String ROLE_DESCRIPTION = "Software Engineer writes code";

    public SoftwareEngineer() {}

    private EmployeType employeetype = EmployeType.SOFTWARE_ENGINEER;

    public SoftwareEngineer(String name, String companyName, AbstractEmployee.Address address,
                            EmployeType employeetype) {
        super(name, companyName, address);
        assert employeetype == EmployeType.SOFTWARE_ENGINEER;
        this.employeetype = employeetype;
    }

    public EmployeType getEmployeetype() {
        return employeetype;
    }

    public String getRoleDescription() {
        return ROLE_DESCRIPTION;
    }

    public String toString() {
        return "Name: " + getName() + " " + "CompanyName: " + getCompanyName() + "Address: " + getAddress()
                + "Role: " + employeetype;
    }
}
