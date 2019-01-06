package com.dhaval.datamodels;

/**
 * Class that represents the CustomOfficer type Employee
 */
public class CustomOfficer extends AbstractEmployee {

    private final String ROLE_DESCRIPTION = "Custom officer is responsible for border protection";

    private EmployeType employeetype = EmployeType.CIVIL_OFFICER;

    public CustomOfficer() {}

    public CustomOfficer(String name, String companyName, Address address, EmployeType employeetype) {
        super(name, companyName, address);
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
