package utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
    
    public static EntityManagerFactory getEmf(){
        return Persistence.createEntityManagerFactory("SagaPU");
    }
}
