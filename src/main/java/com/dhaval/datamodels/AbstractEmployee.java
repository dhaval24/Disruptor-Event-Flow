package com.dhaval.datamodels;

/**
 * Abstract class to represent an Employee
 */
public abstract class AbstractEmployee implements Employee {

    /**
     * Name of the employee
     */
    private String name;

    /**
     * Company name of the employee
     */
    private String companyName;

    /**
     * Field to represent if Employee is sampled
     */
    private boolean sampled;

    /**
     * Represents Address of the Employee
     */
    private Address address;

    AbstractEmployee() {}

    public AbstractEmployee(String name, String companyName, Address address) {
        this.name = name;
        this.companyName = companyName;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String worksFor() {
        return companyName;
    }

    public boolean isSampled() {
        return sampled;
    }

    public void setSampled(boolean sampled) {
        this.sampled = sampled;
    }

    /**
     * Static class representing the Address of Employee
     */
    public static class Address {

        private String city;
        private String streetName;
        private int houseNumber;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public int getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(int houseNumber) {
            this.houseNumber = houseNumber;
        }

        @Override
        public String toString() {
            return houseNumber + " " + streetName + " " + city;
        }
    }

}
