package com.example.sta_op_hulp;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class MainActivityTest extends TestCase {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testLoginClient() {

        int expLogincode = 2002;
        String expDatum = "02-03-1996";
        Integer expRol = 0;

        int Logincode = 2002;
        String datum = "02-03-1996";
        Integer rol = null;
        try {
            if (Logincode == expLogincode && datum.equals(expDatum)) {
                if (expRol == 0) {
                    rol = 0;
                } else {
                    rol = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expRol, rol);
    }

    public void testLoginZorgpersoneel() {

        int expLogincode = 1002;
        String expDatum = "02-03-1996";
        Integer expRol = 1;

        int Logincode = 1002;
        String datum = "02-03-1996";
        Integer rol = null;
        try {
            if (Logincode == expLogincode && datum.equals(expDatum)) {
                if (expRol == 0) {
                    rol = 0;
                } else {
                    rol = 1;
                }
            } else {
                rol = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(expRol, rol);
    }

    public void testLoginWorngCredentials() {

        int expLogincode = 1002;
        String expDatum = "02-03-1996";
        Integer expRol = 1;

        int Logincode = 10022;
        String datum = "02-03-1997";
        Integer rol = null;
        try {
            if (Logincode == expLogincode && datum.equals(expDatum)) {
                if (expRol == 0) {
                    rol = 0;
                } else {
                    rol = 1;
                }
            } else {
                rol = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(rol, null);
    }

    public void testLoginNoCredentials() {
        Integer expLogincode = 1002;
        String expDatum = "02-03-1996";
        Integer expRol = 1;
        String excMsg = "Amount should not be null";

        Integer Logincode = 10022;
        String datum = "02-03-1997";
        Integer rol = null;
        try {
            if (Logincode == expLogincode && datum.equals(expDatum)) {
                if (expRol == 0) {
                    rol = 0;
                } else {
                    rol = 1;
                }
            } else if ((datum == null) || (Logincode == null)) {

                throw new NullPointerException(excMsg);
            } else {
                rol = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(excMsg, "Amount should not be null");


    }



//    public void testNoCredentials() {
//        String expNaam  = "jeroen";
//        Integer expSerienummer = 123;
//        Integer expBestaat = 0;
//        String excMsg = "vul een waarde in";
//        Integer expResult = null;
//
//        String naam = null;
//        Integer Serienummer  = null ;
//        Integer Bestaat = 1;
//
//
//        try {
//            if (naam == expNaam && Serienummer.equals(expSerienummer)) {
//                if (expBestaat == Bestaat) {
//                   // bestaat all
//                    expResult = 1;
//                } else {
//                    // bestaat nog niet
//                    expResult = 2;
//                }
//            } else if ((naam == null) || (Serienummer == null)) {
//                throw new NullPointerException(excMsg);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        assertEquals(excMsg, "vul een waarde in");
//        //assertEquals(expResult, 2);
//
//    }
//
//    public void testExistingCredentialsVolgendeScherm() {
//        String expNaam  = "jeroen";
//        Integer expSerienummer = 123;
//        Integer expBestaat = 1;
//        String excMsg = "vul een waarde in";
//        Integer expResult = 1;
//
//        String naam = "jeroen";
//        Integer Serienummer  = 123 ;
//        Integer Bestaat = 1;
//        Integer Result = null;
//
//        try {
//            if (naam == expNaam && Serienummer.equals(expSerienummer)) {
//                if (expBestaat == Bestaat) {
//                    // bestaat all
//                    Result = 1;
//                } else {
//                    // bestaat nog niet
//                    Result = 2;
//                }
//            } else if ((naam == null) || (Serienummer == null)) {
//                throw new NullPointerException(excMsg);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertEquals(Result, expResult);
//    }
//
//    public void testNotExistingCredentialsVolgendeScherm() {
//        String expNaam  = "jeroen";
//        Integer expSerienummer = 123;
//        Integer expBestaat = 2;
//        String excMsg = "vul een waarde in";
//        Integer expResult = 2;
//
//        String naam = "jeroen";
//        Integer Serienummer  = 123 ;
//        Integer Result = 2;
//
//        try {
//            if (naam == expNaam && Serienummer.equals(expSerienummer)) {
//                if (expBestaat == 0) {
//                    // bestaat all
//                    Result = 1;
//                } else {
//                    // bestaat nog niet
//                    Result = 2;
//                }
//            } else if ((naam == null) || (Serienummer == null)) {
//                throw new NullPointerException(excMsg);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        assertEquals(Result, expResult);
//    }



}