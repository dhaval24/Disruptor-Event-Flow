package com.dhaval.eventhandler;

import com.dhaval.datamodels.AbstractEmployee;
import com.dhaval.datamodels.EmployeEvent;
import com.dhaval.datamodels.Employee;
import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * This EventHandler simulates heavy computation work on each Employee by creating
 * a random address based on UUIDs.
 */
public class EmployeAddressAppenderHandler implements EventHandler<EmployeEvent> {

    private int count = 0;

    public void onEvent(EmployeEvent employeEvent, long l, boolean b) throws Exception {
        Employee employe = employeEvent.getEmployee();
        AbstractEmployee.Address address = employe.getAddress();
        address.setHouseNumber(1000);
        for (int i = 0; i < 2; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                UUID uuid = UUID.randomUUID();
                sb.append(uuid);
            }
            switch (i) {
                case 0:
                    address.setCity(sb.toString());
                    break;
                case 1:
                    address.setStreetName(sb.toString());
                default:
                    break;
            }
        }
        count++;
        if ((count >= 400000) && (count % 400000 == 0)) {
            System.out.println("Timestamp when last item processed by Address handler " +  System.currentTimeMillis());
        }

    }
}
