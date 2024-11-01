package com.pluralsight.persistence;

import com.pluralsight.model.Dealership;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DealershipFileManagerTest {

    @Test
    public void dealershipFileManager_GetDealership_Success(){
        DealershipFileManager manager = new DealershipFileManager();
        Dealership dealership = manager.getDealership();
        assertNotEquals(null,dealership);
    }

    @Test
    public void dealershipFileManager_SaveDealership_Success(){
        DealershipFileManager manager = new DealershipFileManager();
        Dealership dealership_Original = manager.getDealership();
        Dealership dealership_Empty = new Dealership("Name","123 Fake Street","8675309");
        manager.saveDealership(dealership_Empty);
        Dealership dealership_Testing = manager.getDealership();
        manager.saveDealership(dealership_Original);
        assertEquals(dealership_Testing.toString(),dealership_Empty.toString());
    }
}