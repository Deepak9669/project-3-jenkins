package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.project_3.dto.BusDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.BusModelHibImpl;
import in.co.rays.project_3.model.BusModelInt;
//import in.co.rays.project_3.model.BusModelJDBCImpl;

public class BusModelTest {

    public static BusModelInt model = new BusModelHibImpl();
    // public static BusModelInt model = new BusModelJDBCImpl();

    public static void main(String[] args) throws Exception {

        addTest();
        // updateTest();
        // deleteTest();
        // findByPKTest();
        // listTest();
        // searchTest();
    }

    public static void addTest() throws Exception {

        BusDTO dto = new BusDTO();

        dto.setBusNumber("MP09-1234");
        dto.setBusType("AC Sleeper");
        dto.setTotalSeats("45");
        dto.setSource("Indore");
        dto.setDestination("Bhopal");

        dto.setCreatedBy("admin");
        dto.setModifiedBy("admin");
        dto.setCreatedDatetime(new Timestamp(new Date().getTime()));
        dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

        System.out.println("Add Bus Test...");
        long pk = model.add(dto);
        System.out.println(pk + " Bus record successfully inserted");
    }

    public static void updateTest() throws Exception {

        BusDTO dto = new BusDTO();

        dto.setId(1L); // jis id ko update karna ho
        dto.setBusNumber("MP09-9999");
        dto.setBusType("Non-AC");
        dto.setTotalSeats("40");
        dto.setSource("Ujjain");
        dto.setDestination("Indore");

        dto.setModifiedBy("admin");
        dto.setModifiedDatetime(new Timestamp(new Date().getTime()));

        model.update(dto);
        System.out.println("Bus record updated successfully");
    }

    public static void deleteTest() throws ApplicationException {

        BusDTO dto = new BusDTO();
        dto.setId(1L);

        try {
			model.delete(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Bus record deleted successfully");
    }

   

  
   
    }

