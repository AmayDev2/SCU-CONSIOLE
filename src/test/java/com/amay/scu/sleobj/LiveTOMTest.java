package com.amay.scu.sleobj;

import org.junit.jupiter.api.Test;
import org.postgresql.core.ServerVersion;

import static org.junit.jupiter.api.Assertions.*;

class LiveTOMTest {

    @Test
    public void liveTomPropChange(){
        LiveTOM liveTOM = new LiveTOM();
        liveTOM.addPropertyChangeListener(event -> {

            System.out.println("Property " + event.getPropertyName() + " changed from " +
                    event.getOldValue() + " to " + event.getNewValue());

        });

        liveTOM.addPropertyChangeListener(event->{
            System.out.println("2Property " + event.getPropertyName() + " changed from " +
                    event.getOldValue() + " to " + event.getNewValue());
        });

         // Update a property to trigger the listener
        liveTOM.setEquipName("New Equipment Name");

    }

}