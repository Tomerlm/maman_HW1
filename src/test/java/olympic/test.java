
package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class test extends AbstractTest {
//===============================================================================================//  
    //basic Athlete test section start//
    
    @Test
    public void addAthleteTest() {
        /*
        test stages:
            step 1: add valid athlete and expect OK return.
            step 2: add an athlete with id 0 and expect BAD_PARAMS return.
            step 3: add another athlete with the same id as in step 1, expect ALREADY_EXISTS return.
        step 4: add another athlete with NULL in the name,expect BAD_PARAMS return.
            step 5: add another athlete with NULL in the country,expect BAD_PARAMS return.
        */
        System.out.println("addAthleteTest...");
        System.out.println("step 1");
        ReturnValue res;
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        assertEquals(OK, res);
        
        System.out.println("step 2");
        Athlete a2 = new Athlete();
        a2.setId(0);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a2);
        assertEquals(BAD_PARAMS, ret);

        System.out.println("step 3");
        Athlete a3 = new Athlete();
        a3.setId(1);
        a3.setName("Zohar");
        a3.setCountry("Nesher");
        a3.setIsActive(false);
        ReturnValue ret4 = Solution.addAthlete(a3);
        assertEquals(ALREADY_EXISTS, ret4);
        
        System.out.println("step 4");
        Athlete a4 = new Athlete();
        a4.setId(2);
        //a4.setName("");
        a4.setCountry("Nesher");
        a4.setIsActive(true);
        ReturnValue ret2 = Solution.addAthlete(a4);
        assertEquals(BAD_PARAMS, ret2);
        
        System.out.println("step 5");
        Athlete a5 = new Athlete();
        a5.setId(2);
        a5.setName("Zohar");
        //a5.setCountry("");
        a5.setIsActive(true);
        ReturnValue ret3 = Solution.addAthlete(a5);
        assertEquals(BAD_PARAMS, ret3);

        System.out.println("addAthleteTest passed :)");
    }

    //===============================================================================================//

    @Test
    public void getAthleteProfileTest() {
        /*
        test stages:
            step 1: add 2 valid athletes.
            step 2: execute getAthleteProfile() on the first athlete id and excpect to be equal to it.
            and excpect to be different from the second.
            step 3: execute getAthleteProfile() on non existing id, BadAthlete should return;
        */
        System.out.println("getAthleteProfileTest...");
        System.out.println("step 1");
        ReturnValue res;
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
 
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        ReturnValue ret = Solution.addAthlete(a2);

        System.out.println("step 2");
        Athlete a3 = Solution.getAthleteProfile(1);
        assertEquals(a3,a1);
        assertNotEquals(a2, a3);

        System.out.println("step 3");
        a3 = Solution.getAthleteProfile(90);
        assertEquals(a3, Athlete.badAthlete() );
        Solution.deleteAthlete(a1);
        Athlete a4 = Solution.getAthleteProfile(1);
        assertEquals(a4, Athlete.badAthlete() );
        
        System.out.println("getAthleteProfileTest passed :)");
    }

    @Test
    public void addSportTest() {
        System.out.println("addSportTest...");
        ReturnValue res;

        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        res = Solution.addSport(s);
        assertEquals(OK, res);

        Sport s1 = new Sport();
        s1.setId(1);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        res = Solution.addSport(s1);
        assertEquals(ALREADY_EXISTS, res);

        Sport s2 = new Sport();
        s2.setId(-1);
        s2.setName("Basketball");
        s2.setCity("Tel Aviv");
        res = Solution.addSport(s2);
        assertEquals(BAD_PARAMS, res);

        Sport s3 = new Sport();
        s3.setId(-1);
        s3.setName("Basketball");
        res = Solution.addSport(s3);
        assertEquals(BAD_PARAMS, res);

        Sport s4 = new Sport();
        s4.setId(-1);
        s4.setName("Basketball");
        s4.setCity("Tel Aviv");
        res = Solution.addSport(s4);
        assertEquals(BAD_PARAMS, res);

        System.out.println("addSportTest passed :)");
    }

    @Test
    public void getSportTest() {
        System.out.println("getSportTest...");
        ReturnValue res;

        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        res = Solution.addSport(s);
        assertEquals(OK, res);

        Sport s2 = new Sport();
        s2.setId(2);
        s2.setName("soccer");
        s2.setCity("Tel Aviv");
        res = Solution.addSport(s2);
        assertEquals(OK, res);

        Sport test1 = Solution.getSport(1);
        Sport test2 = Solution.getSport(2);
        Sport test3 = Solution.getSport(-1);
        Sport test4 = Solution.getSport(99);

        assertEquals(test1, s);
        assertEquals(test2, s2);
        assertEquals(test3, Sport.badSport());
        assertEquals(test4, Sport.badSport());

        System.out.println("getSportTest passed :)");
    }
 
        @Test 
        public void deleteAthleteTest() {
        /*
        test stages:
            step 1: add 2 valid athletes and join them to sport.
            step 2: delete athlete 1 and assert he not  appears in Involved.
            step 3: delete athlete with non existing id and excpect NOT_EXISTS.
        */
        System.out.println("deleteAthleteTest...");
        System.out.println("athleteJoinSport test should run before deleteAthlete test");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        Solution.addAthlete(a2);
        
        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);
        Solution.addSport(s);
        Solution.athleteJoinSport(1, 1);
        
         System.out.println("step 2");
         res = Solution.deleteAthlete(a1);
         assertEquals(res,OK);
         res = Solution.deleteAthlete(a1);
         assertEquals(res, NOT_EXISTS);
         res = Solution.athleteJoinSport(1,1);
         assertEquals(res,NOT_EXISTS);
         
         System.out.println("step 3");
         res = Solution.deleteAthlete(a1);
         assertEquals(res,NOT_EXISTS);  
         res = Solution.deleteAthlete(a1);
         assertEquals(res,NOT_EXISTS);
         
        System.out.println("deleteAthleteTest passed :)");
        }
        // basic Athlete test section end//   
       // athleteJoinSport test should run before deleteAthlete test.
//===============================================================================================//

    @Test
    public void deleteSportTest() {
        System.out.println("deleteSport...");
        ReturnValue res;

        Sport s = new Sport();
        s.setId(1);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        res = Solution.addSport(s);
        assertEquals(OK, res);

        Sport s2 = new Sport();
        s2.setId(2);
        s2.setName("soccer");
        s2.setCity("Tel Aviv");
        res = Solution.addSport(s2);
        assertEquals(OK, res);

        ReturnValue res1 = Solution.deleteSport(Solution.getSport(1));
        ReturnValue res2 = Solution.deleteSport(s2);
        ReturnValue res3 = Solution.deleteSport(s2);
        assertEquals(res1, OK);
        assertEquals(res2, OK);
        assertEquals(res3, NOT_EXISTS);

        System.out.println("deleteSport passed :)");
    }

    // friendship test section start//
    @Test 
        public void makeFriendsTest() {
        /*
        test stages:
            step 1: add 2 valid athletes.
            step 2: make them friends and excpect Ok.
            step 3: make athlete a1 friend with a1, excpect BAD_PARAMS.
            step 4: make a1 and a2 friends, excpect ALREADY_EXISTS.
            step 5: verify that 2 rows of frindship were added to frindships table.
        */
        System.out.println("makeFriendsTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        Solution.addAthlete(a2);
        
        System.out.println("step 2");
        res = Solution.makeFriends(1,2);
        assertEquals(res,OK);
        
        System.out.println("step 3");
        res = Solution.makeFriends(1,1);
        assertEquals(res,BAD_PARAMS);
        
        System.out.println("step 4");
        res = Solution.makeFriends(2,1);
        assertEquals(res,ALREADY_EXISTS);
        
        /*System.out.println("step 5");
        assertEquals(res1,true);
        res1 = Solution.areFriends(2,1);
        assertEquals(res1,true);*/
        
        System.out.println("makeFriendsTest passed :)");
        }
    
    
        @Test 
        public void removeFriendshipTest() {
        /*
        test stages:
            step 1: add 3 valid athletes and make them friends.
            step 2: make them unfriends and excpect Ok.
            step 3: make athlete a1 unfriend with non-existing athlete, excpect NOT_EXISTS .
            step 4: make a1 and a3 un-friends, excpect NOT_EXISTS.
            step 5: verify that 2 rows of frindship were delete to frindships table(a1,a2).
        */
        
        System.out.println("removeFriendshipTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        Solution.addAthlete(a2);
        Athlete a3 = new Athlete();
        a3.setId(3);
        a3.setName("Ariel");
        a3.setCountry("Haifa");
        a3.setIsActive(false);
        Solution.addAthlete(a3);
        Solution.makeFriends(1,2);
        
        System.out.println("step 2");
        res = Solution.removeFriendship(1,2);
        assertEquals(res,OK);
        
        System.out.println("step 3");
        res = Solution.removeFriendship(1,4);
        assertEquals(res,NOT_EXISTS);
        
        System.out.println("step 4");
        res = Solution.removeFriendship(1,3);
        assertEquals(res,NOT_EXISTS);
        
        /*System.out.println("step 5");
        bool res1 = Solution.areFriends(1,2);
        assertEquals(res1,false);
        res1 = Solution.areFriends(2,1);
        assertEquals(res1,false);*/
        
        System.out.println("removeFriendshipTest passed :)");
        }
    
    // basic friendship test end//
//===============================================================================================//     
    
    
       @Test 
        public void changePaymentTest() {
        /*
        test stages:
            step 1: add 3 valid athletes(one active, one not) and valid sport, and join them.
            step 2: change a2 payment to negative, expect BAD_PARAMS, then change to positive,expect OK.
            step 3: change a1 payment expect NOT_EXISTS.
        */
        System.out.println("changePaymentTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        res = Solution.addAthlete(a2);
        assertEquals(res, OK);
        Athlete a3 = new Athlete();
        a3.setId(3);
        a3.setName("Ariel");
        a3.setCountry("Haifa");
        a3.setIsActive(false);
        Solution.addAthlete(a3);
        Sport s = new Sport();
        s.setId(10);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        Solution.addSport(s);
        Solution.athleteJoinSport(10,1);
        Solution.athleteJoinSport(10,2);
        
        System.out.println("step 2");
        res = Solution.changePayment(2,10,-1);
        assertEquals(BAD_PARAMS, res);
        System.out.println("step 2");
        res = Solution.changePayment(2,10,78);
        assertEquals(OK, res);
        res = Solution.changePayment(1,10,78);
        assertEquals(NOT_EXISTS, res);
        res = Solution.changePayment(3,10,78);
        assertEquals(NOT_EXISTS, res);
        
        System.out.println("changePaymentTest passed :)");
        }

    @Test
    public void involvedTest() {
        System.out.println("InvolvedTest(JoinSport, LeftSport...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        assertEquals(res, OK);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        res = Solution.addAthlete(a2);
        assertEquals(res, OK);
        Athlete a3 = new Athlete();
        a3.setId(3);
        a3.setName("Ariel");
        a3.setCountry("Haifa");
        a3.setIsActive(false);
        res = Solution.addAthlete(a3);
        assertEquals(res, OK);
        Sport s = new Sport();
        s.setId(10);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        res = Solution.addSport(s);
        assertEquals(res, OK);

        res = Solution.athleteJoinSport(90, 1);
        assertEquals(res, NOT_EXISTS);
        res = Solution.athleteJoinSport(10, 90);
        assertEquals(res, NOT_EXISTS);
        res = Solution.athleteJoinSport(10, 1);
        assertEquals(res, OK);
        res = Solution.athleteJoinSport(10, 2);
        assertEquals(res, OK);
        res = Solution.athleteJoinSport(10, 1);
        assertEquals(res, ALREADY_EXISTS);
        res = Solution.athleteJoinSport(10, 2);
        assertEquals(res, ALREADY_EXISTS);

        res = Solution.athleteLeftSport(90, 1);
        assertEquals(res, NOT_EXISTS);
        res = Solution.athleteLeftSport(10, 90);
        assertEquals(res, NOT_EXISTS);
        res = Solution.athleteLeftSport(10, 1);
        assertEquals(res, OK);
        res = Solution.athleteLeftSport(10, 2);
        assertEquals(res, OK);
        res = Solution.athleteLeftSport(10, 1);
        assertEquals(res, NOT_EXISTS);
        res = Solution.athleteLeftSport(10, 2);
        assertEquals(res, NOT_EXISTS);
    }


        @Test
        public void isAthletePopularTest() {
        /*
        test stages:
            step 1: add 4 valid athletes and 2 sports.
            step 2: make a1, a2 friend, a1,a3 friends, a2,a4 friends
            step 3: expect a1 to be popular and a2 not.
            step 4: check for non- existing athlete return false.
        */
        System.out.println("isAthletePopularTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Nesher");
        a2.setIsActive(false);
        Solution.addAthlete(a2);
        Athlete a3 = new Athlete();
        a3.setId(3);
        a3.setName("Ariel");
        a3.setCountry("Haifa");
        a3.setIsActive(false);
        Solution.addAthlete(a3);
        Athlete a4 = new Athlete();
        a4.setId(4);
        a4.setName("Ilya");
        a4.setCountry("Carmiel");
        a4.setIsActive(true);
        Solution.addAthlete(a4);
        Sport s = new Sport();
        s.setId(10);
        s.setName("Basketball");
        s.setCity("Tel Aviv");
        s.setAthletesCount(0);
        Solution.addSport(s);
        Sport s2 = new Sport();
        s2.setId(11);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        s2.setAthletesCount(0);
        Solution.addSport(s2);
        
        System.out.println("step 2");
        Solution.athleteJoinSport(10,1);
        Solution.athleteJoinSport(10,2);
        Solution.athleteJoinSport(10,3);
        Solution.athleteJoinSport(11,4);
        Solution.makeFriends(1,2);
        Solution.makeFriends(1,3);
        Solution.makeFriends(2,4);
        
        System.out.println("step 3");
        boolean is_pop1 = Solution.isAthletePopular(1);
        assertEquals(true, is_pop1);
        boolean is_pop2 = Solution.isAthletePopular(2);
        assertEquals(false, is_pop2);
        
        System.out.println("step 4");
        boolean is_pop3 = Solution.isAthletePopular(5);
        assertEquals(false, is_pop3);
        
        System.out.println("isAthletePopularTest passed");
    }
    
    
    
    
    @Test
    public void getTotalNumberOfMedalsFromCountryTest() {
        /*
        test stages:
            step 1: add 3 valid athletes and 3 sports.
            step 2: a2 won first in s1,s2,s3.
                    a3 won second in s3.
            step 3: expect a1 country to be with 0 medals.
                    and a2,a3 country to be with 4 medals.
                    and check for non- existing country return 0.
        */
        System.out.println("getTotalNumberOfMedalsFromCountryTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Athlete a2 = new Athlete();
        a2.setId(2);
        a2.setName("Zohar");
        a2.setCountry("Haifa");
        a2.setIsActive(true);
        Solution.addAthlete(a2);
        Athlete a3 = new Athlete();
        a3.setId(3);
        a3.setName("Ariel");
        a3.setCountry("Haifa");
        a3.setIsActive(true);
        Solution.addAthlete(a3);
        Sport s1 = new Sport();
        s1.setId(10);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        s1.setAthletesCount(0);
        res = Solution.addSport(s1);
        Sport s2 = new Sport();
        s2.setId(20);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        s2.setAthletesCount(0);
        res = Solution.addSport(s2);
        Sport s3 = new Sport();
        s3.setId(30);
        s3.setName("Tennis");
        s3.setCity("Ramat HaSharon");
        s3.setAthletesCount(0);
        res = Solution.addSport(s3);

        Solution.athleteJoinSport(10,1);
        Solution.athleteJoinSport(10,2);
        Solution.athleteJoinSport(10,3);
        Solution.athleteJoinSport(20,1);
        Solution.athleteJoinSport(20,2);
        Solution.athleteJoinSport(20,3);
        Solution.athleteJoinSport(30,1);
        Solution.athleteJoinSport(30,2);
        Solution.athleteJoinSport(30,3);

        System.out.println("step 2");
        Solution.confirmStanding(10,2,1);
        Solution.confirmStanding(20,2,1);
        Solution.confirmStanding(30,2,1);
        Solution.confirmStanding(30,3,2);
        
        System.out.println("step 3");
        int medals = Solution.getTotalNumberOfMedalsFromCountry("Herzliya");
        assertEquals("MSG", 0, medals);
        medals = Solution.getTotalNumberOfMedalsFromCountry("Haifa");
        assertEquals(4, medals);
        medals = Solution.getTotalNumberOfMedalsFromCountry("Nesher");
        assertEquals(0, medals);
        
        System.out.println("getTotalNumberOfMedalsFromCountryTest passed");
    }

    @Test
    public void friendsTest() {

        ReturnValue res;
        Athlete a = new Athlete();
        a.setId(4);
        a.setName("Neymar");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);


        a.setId(5);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(false);
        ret = Solution.addAthlete(a);
        assertEquals(OK, ret);

        res = Solution.makeFriends(4,5);
        assertEquals(OK, res);

        res = Solution.removeFriendship(2,5);
        assertEquals(NOT_EXISTS, res);

    }

    @Test
    public void getAthleteMedalsTest(){
        /*
        test stages:
            step 1: add 1 valid athlete and 6 sports.
            step 2: verify a1 won 0 gold, 0 silver, 0 bronze
            step 3: a1 won first in s1,s2 and second on s3.
            step 4: vector should be (2,1,0);
            step 5: a1 lost one gold, and won 3rd in s4,s5
            step 6: vector shold be (1,1,2);
        */
        System.out.println("getAthleteMedalsTest...");
        ReturnValue res;
        System.out.println("step 1");
        Athlete a1 = new Athlete();
        a1.setId(1);
        a1.setName("Avishay");
        a1.setCountry("Herzliya");
        a1.setIsActive(true);
        res = Solution.addAthlete(a1);
        Sport s1 = new Sport();
        s1.setId(10);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        res = Solution.addSport(s1);
        Sport s2 = new Sport();
        s2.setId(20);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        res = Solution.addSport(s2);
        Sport s3 = new Sport();
        s3.setId(30);
        s3.setName("Tennis");
        s3.setCity("Ramat HaSharon");
        res = Solution.addSport(s3);
        Sport s4 = new Sport();
        s4.setId(40);
        s4.setName("ping pong");
        s4.setCity("Ramat Gan");
        res = Solution.addSport(s4);
        Sport s5 = new Sport();
        s5.setId(50);
        s5.setName("running");
        s5.setCity("Metulla");
        res = Solution.addSport(s5);
        Solution.athleteJoinSport(10,1);
        Solution.athleteJoinSport(20,1);
        Solution.athleteJoinSport(30,1);
        Solution.athleteJoinSport(40,1);
        Solution.athleteJoinSport(50,1);

        System.out.println("step 2");
        ArrayList<Integer> vec = new ArrayList<Integer>();
        vec = Solution.getAthleteMedals(1);

        assertEquals(true, vec.get(0) == 0 && vec.get(1) == 0 && vec.get(2) == 0);

        System.out.println("step 3");
        Solution.confirmStanding(10,1,1);
        Solution.confirmStanding(20,1,1);
        Solution.confirmStanding(30,1,2);

        System.out.println("step 4");
        vec = Solution.getAthleteMedals(1);
        assertEquals(true, vec.get(0) == 2 && vec.get(1) == 1 && vec.get(2) == 0);

        System.out.println("step 5");
        Solution.confirmStanding(40,1,3);
        Solution.confirmStanding(50,1,3);

        System.out.println("step 6");
        vec = Solution.getAthleteMedals(1);
        assertEquals(true, vec.get(0) == 2 && vec.get(1) == 1 && vec.get(2) == 2);

        Solution.clearTables();
        System.out.println("getAthleteMedalsTest passed");
    }

    @Test
    public void getMostRatedAthletesTest(){
        /*
        test stages:
            step 1: add 15 valid athlete and 2 sports.
            step 2: make every modolo 2 win first place, every modolo 3 win second place and modolo 6 win second place.
            step 3: the result should be:
        */
        ReturnValue res;
        System.out.println("getMostRatedAthletesTest...");
        System.out.println("step 1");
        Sport s1 = new Sport();
        s1.setId(10);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        res = Solution.addSport(s1);
        Sport s2 = new Sport();
        s2.setId(20);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        res = Solution.addSport(s2);
        Sport s3 = new Sport();
        s3.setId(30);
        s3.setName("Tennis");
        s3.setCity("Ramat HaSharon");
        res = Solution.addSport(s3);
        Sport s4 = new Sport();
        s4.setId(40);
        s4.setName("ping pong");
        s4.setCity("Ramat Gan");
        res = Solution.addSport(s4);
        Sport s5 = new Sport();
        s5.setId(50);
        s5.setName("running");
        s5.setCity("Metulla");
        res = Solution.addSport(s5);

        ArrayList<Athlete> players = new ArrayList<Athlete>(15);
        for(int i = 0; i < 15; i++){
            Athlete a1 = new Athlete();
            a1.setId(i+1);
            a1.setName("Avishay");
            a1.setCountry("Herzliya");
            a1.setIsActive(true);
            Solution.addAthlete(a1);
            Solution.athleteJoinSport(10,i+1);
            Solution.athleteJoinSport(20,i+1);
            Solution.athleteJoinSport(30,i+1);
            Solution.athleteJoinSport(40,i+1);
            Solution.athleteJoinSport(50,i+1);
        }

        System.out.println("step 2");
        Solution.confirmStanding(10,2,1);
        Solution.confirmStanding(20,4,1);
        Solution.confirmStanding(30,6,1);
        Solution.confirmStanding(40,8,1);
        Solution.confirmStanding(50,10,1);
        Solution.confirmStanding(20,12,1);
        Solution.confirmStanding(10,14,1);

        Solution.confirmStanding(10,3,2);
        Solution.confirmStanding(20,6,2);
        Solution.confirmStanding(30,9,2);
        Solution.confirmStanding(40,12,2);
        Solution.confirmStanding(50,15,2);

        Solution.confirmStanding(10,6,3);
        Solution.confirmStanding(50,12,3);

        ArrayList<Integer> expected = new ArrayList<Integer>();
        ArrayList<Integer> result_vec = new ArrayList<Integer>();
        expected.add(6);
        expected.add(12);
        expected.add(2);
        expected.add(4);
        expected.add(8);
        expected.add(10);
        expected.add(14);
        expected.add(3);
        expected.add(9);
        expected.add(15);

        result_vec = Solution.getMostRatedAthletes();

        System.out.println("step 3");
        for(int i = 0; i < 10; i++){
            assertEquals(expected.get(i) == result_vec.get(i),true);
        }

        System.out.println("getMostRatedAthletesTest passed");
    }

    @Test
    public void getMostRatedAthletesTest2(){
        /*
        test stages:
            step 1: add 15 valid athlete and 2 sports.
            step 2: make every modolo 2 win first place, every modolo 3 win second place and modolo 6 win second place.
            step 3: the result should be:
        */
        ReturnValue res;
        System.out.println("getMostRatedAthletesTest...");
        System.out.println("step 1");
        Sport s1 = new Sport();
        s1.setId(10);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        res = Solution.addSport(s1);
        Sport s2 = new Sport();
        s2.setId(20);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        res = Solution.addSport(s2);
        Sport s3 = new Sport();
        s3.setId(30);
        s3.setName("Tennis");
        s3.setCity("Ramat HaSharon");
        res = Solution.addSport(s3);
        Sport s4 = new Sport();
        s4.setId(40);
        s4.setName("ping pong");
        s4.setCity("Ramat Gan");
        res = Solution.addSport(s4);
        Sport s5 = new Sport();
        s5.setId(50);
        s5.setName("running");
        s5.setCity("Metulla");
        res = Solution.addSport(s5);

        ArrayList<Athlete> players = new ArrayList<Athlete>(15);
        for(int i = 0; i < 5; i++){
            Athlete a1 = new Athlete();
            a1.setId(i+1);
            a1.setName("Avishay");
            a1.setCountry("Herzliya");
            a1.setIsActive(true);
            Solution.addAthlete(a1);
            Solution.athleteJoinSport(10,i+1);
            Solution.athleteJoinSport(20,i+1);
            Solution.athleteJoinSport(30,i+1);
            Solution.athleteJoinSport(40,i+1);
            Solution.athleteJoinSport(50,i+1);
        }

        System.out.println("step 2");
        Solution.confirmStanding(10,2,1);
        Solution.confirmStanding(20,4,1);

        Solution.confirmStanding(10,3,2);

        ArrayList<Integer> expected = new ArrayList<Integer>();
        ArrayList<Integer> result_vec = new ArrayList<Integer>();
        expected.add(2);
        expected.add(4);
        expected.add(3);

        result_vec = Solution.getMostRatedAthletes();

        System.out.println("step 3");
        for(int i = 0; i < 3; i++){
            System.out.println("List pos : "+ i + " value: " +  result_vec.get(i) );
            assertEquals(expected.get(i) == result_vec.get(i),true);
        }

        System.out.println("getMostRatedAthletesTest passed");
    }

    @Test
    public void getCloseAthletesShortTest(){
        /*
        test stages:
            step 1: add 6 valid athlete and 3 sports and make 1,2 take place in sport 10, athlete 3 in 10,20 and 4 in sport 10,20,30.
            athletes 5 and 6 are in no sport.
            step 2: make friends (1,2) (1,3) (3,4) (2,4)
            step 3: athlete 1 should get 2,3 as close, and 4 should have 3 as close.
            step 4: athlete 5 should be close to 6(in an empty way).
        */

        System.out.println("getCloseAthletesShortTest...");
        System.out.println("step 1");
        Sport s1 = new Sport();
        s1.setId(10);
        s1.setName("Basketball");
        s1.setCity("Tel Aviv");
        Solution.addSport(s1);
        Sport s2 = new Sport();
        s2.setId(20);
        s2.setName("Soccer");
        s2.setCity("Haifa");
        Solution.addSport(s2);
        Sport s3 = new Sport();
        s3.setId(30);
        s3.setName("Tennis");
        s3.setCity("Ramat HaSharon");
        Solution.addSport(s3);
        Athlete a1 = new Athlete();
        for(int i = 1; i <= 6; i++){
            a1.setId(i);
            a1.setName("Avishay");
            a1.setCountry("Herzliya");
            a1.setIsActive(true);
            Solution.addAthlete(a1);
        }
        Solution.athleteJoinSport(10,1);
        Solution.athleteJoinSport(10,2);
        Solution.athleteJoinSport(10,3);
        Solution.athleteJoinSport(10,4);
        Solution.athleteJoinSport(20,3);
        Solution.athleteJoinSport(20,4);
        Solution.athleteJoinSport(30,4);

        ReturnValue res;
        System.out.println("step 2");
        res = Solution.makeFriends(1,2);
        res = Solution.makeFriends(1,3);
        res = Solution.makeFriends(2,4);
        res = Solution.makeFriends(3,4);
        res = Solution.makeFriends(5,6);

        System.out.println("step 3");

        ArrayList<Integer> exp_1 = new ArrayList<Integer>();
        ArrayList<Integer> exp_4 = new ArrayList<Integer>();
        ArrayList<Integer> exp_5 = new ArrayList<Integer>();
        ArrayList<Integer> real_1 = new ArrayList<Integer>();
        ArrayList<Integer> real_4 = new ArrayList<Integer>();
        ArrayList<Integer> real_5 = new ArrayList<Integer>();

        exp_1.add(2);
        exp_1.add(3);
        exp_1.add(4);
        exp_4.add(3);
        exp_5.add(1);
        exp_5.add(2);
        exp_5.add(3);
        exp_5.add(4);
        exp_5.add(6);
        real_1 = Solution.getCloseAthletes(1);
        real_4 = Solution.getCloseAthletes(4);
        real_5 = Solution.getCloseAthletes(5);
        assertEquals(exp_1.size(),real_1.size());
        assertEquals(exp_4.size(),real_4.size());
        assertEquals(real_5.get(0) == 1, true);
        assertEquals(real_5.get(1) == 2, true);
        assertEquals(real_5.get(2) == 3, true);
        assertEquals(real_5.get(3) == 4, true);
        assertEquals(real_5.get(4) == 6, true);
        assertEquals(real_1.get(0) == 2, true);
        assertEquals(real_1.get(1) == 3, true);
        assertEquals(real_1.get(2) == 4, true);
        assertEquals(real_4.get(0) == 3, true);

        System.out.println("getCloseAthletesShortTest passed :)");
    }

    @Test
    public void getCloseAthletesShortTest2(){
        /*
        test stages:
            step 1: add 15 valid athlete and 15 sports and make each athlete i to take place in [1,i] sport.
            step 2: athlete 1 close are:{2,3,4,5,6,7},athlete 15 close are [1,14],
            athlete 9 close are {[1,8]} U {10,11}
            step 3: verify the above

        */

        System.out.println("getCloseAthletesLongTest...");
        System.out.println("step 1");
        for(int i = 1; i <=15; i++){
            Sport s1 = new Sport();
            s1.setId(10*i);
            s1.setName("Basketball");
            s1.setCity("Tel Aviv");
            Solution.addSport(s1);
        }
        Athlete a1 = new Athlete();
        for(int i = 1; i <= 15; i++){

            a1.setId(i);
            a1.setName("Avishay");
            a1.setCountry("Herzliya");
            a1.setIsActive(true);
            Solution.addAthlete(a1);
            for(int j = i; j <=15; j++){
                Solution.athleteJoinSport(10*j,i);
            }
        }

        for(int i = 1; i <=15; i++){
            for(int j = i+1; j <=15; j=j+2){
                Solution.makeFriends(i,j);
            }

        }

        System.out.println("step 2");

        ArrayList<Integer> exp_1 = new ArrayList<Integer>();
        ArrayList<Integer> exp_9 = new ArrayList<Integer>();
        ArrayList<Integer> exp_15 = new ArrayList<Integer>();
        ArrayList<Integer> real_1 = new ArrayList<Integer>();
        ArrayList<Integer> real_9 = new ArrayList<Integer>();
        ArrayList<Integer> real_15 = new ArrayList<Integer>();

        exp_1.add(2);
        exp_1.add(3);
        exp_1.add(4);
        exp_1.add(5);
        exp_1.add(6);
        exp_1.add(7);
        exp_1.add(8);

        exp_9.add(1);
        exp_9.add(2);
        exp_9.add(3);
        exp_9.add(4);
        exp_9.add(5);
        exp_9.add(6);
        exp_9.add(7);
        exp_9.add(8);
        exp_9.add(10);
        exp_9.add(11);

        for(int i = 1; i <=10; i++){
            exp_15.add(i);
        }

        real_1 = Solution.getCloseAthletes(1);
        real_9 = Solution.getCloseAthletes(9);
        real_15 = Solution.getCloseAthletes(15);

        assertEquals(exp_1.size(),real_1.size());
        assertEquals(exp_9.size(),real_9.size());
        assertEquals(exp_15.size(),real_15.size());

        for(int i = 0; i<real_1.size(); i++){
            assertEquals(real_1.get(i),exp_1.get(i));
        }

        for(int i = 0; i<real_9.size(); i++){
            assertEquals(real_9.get(i),exp_9.get(i));
        }

        for(int i = 0; i<real_15.size(); i++){
            assertEquals(real_15.get(i),exp_15.get(i));
        }

        System.out.println("getCloseAthletesLongTest passed :)");
        System.out.println("this one was long and hard ;)");
    }


    @Test
    public void getSportsRecommendationTest(){
        /*
        test stages:
            step 1: add 15 valid athlete and 15 sports and make eact athlete i to take place in [1,i] sport. make all the athletes
            friends.
            make athlete 16, with no friends.
            step 2: the recommendation need to be return from athlete 1 is empty
            the recommendation need to be return from athlete 2 is: {10}
            the recommendation need to be return from athlete 15 is:{140,130,120}
            the recommendation need to be return from athlete 16 is empty
            step 3: verify the above
        */
        /*System.out.println("getSportsRecommendationTest...");
        System.out.println("step 1");
        for(int i = 1; i <=15; i++){
            Sport s1 = new Sport();
            s1.setId(10*i);
            s1.setName("Basketball");
            s1.setCity("Tel Aviv");
            Solution.addSport(s1);
        }

        for(int i = 1; i <= 16; i++){
            Athlete a1;
            a1.setId(i);
            a1.setName("Avishay");
            a1.setCountry("Herzliya");
            a1.setIsActive(true);
            Solution.addAthlete(a1);
            for(int j = i; j <=15; j++){
                Solution.athleteJoinSport(10*j,i);
            }
        }

        for(int i = 1; i <=15; i++){
            for(int j = i+1; j <=15; j++){
                Solution.makeFriends(i,j);
            }

        }

        System.out.println("step 2");
        ArrayList<Integer> exp_1 = new ArrayList<Integer>();
        ArrayList<Integer> exp_2 = new ArrayList<Integer>();
        ArrayList<Integer> exp_15 = new ArrayList<Integer>();
        ArrayList<Integer> exp_16 = new ArrayList<Integer>();
        ArrayList<Integer> real_1 = new ArrayList<Integer>();
        ArrayList<Integer> real_2 = new ArrayList<Integer>();
        ArrayList<Integer> real_15 = new ArrayList<Integer>();
        ArrayList<Integer> real_16 = new ArrayList<Integer>();

        exp_2.add(10);
        exp_15.add(120);
        exp_15.add(130);
        exp_15.add(140);

        System.out.println("step 3");

        real_1 = Solution.getSportsRecommendation();
        real_2 = Solution.getSportsRecommendation();
        real_15 = Solution.getSportsRecommendation();
        real_16 = Solution.getSportsRecommendation();

        assertEquals(exp_1.size(),real_1.size());
        assertEquals(exp_2.size(),real_2.size());
        assertEquals(exp_15.size(),real_15.size());
        assertEquals(exp_16.size(),real_16.size());


        for(int i = 0; i<real_2.size(); i++){
            assertEquals(real_2.get(i),exp_2.get(i));
        }

        for(int i = 0; i<real_15.size(); i++){
            assertEquals(real_15.get(i),exp_15.get(i));
        }

        System.out.println("getSportsRecommendationTest passed");
        System.out.println("now we can submit it!");*/
    }

    @Test
    public void Test_1_Basic() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(1);
        s.setName("football");
        s.setCity("TLV");

        Athlete a = new Athlete();
        a.setId(1);
        a.setCountry("Israel");
        a.setName("gadi");

        Solution.addAthlete(a);
        Solution.addSport(s);

        if(Solution.getSport(s.getId()).equals(s) == Boolean.TRUE)
            System.out.println("1.1 OK");
        if(Solution.getAthleteProfile(a.getId()).equals(a) == Boolean.TRUE)
            System.out.println("1.2 OK");
        if(Solution.getSport(2).equals(Sport.badSport()) == Boolean.TRUE)
            System.out.println("1.3 OK");

        Solution.athleteJoinSport(s.getId(),a.getId());

        if(Solution.confirmStanding(s.getId(),a.getId(),5) == ReturnValue.NOT_EXISTS)
            System.out.println("1.4 OK");
        if (Solution.confirmStanding(2,a.getId(),5) == ReturnValue.NOT_EXISTS)
            System.out.println("1.5 OK");
        a.setId(2);
        a.setIsActive(true);
        Solution.addAthlete(a);
        Solution.athleteJoinSport(s.getId(),a.getId());
        if (Solution.confirmStanding(s.getId(),a.getId(),3) == ReturnValue.OK)
            System.out.println("1.6 OK");

        if(Solution.athleteLeftSport(s.getId(),3) == ReturnValue.NOT_EXISTS)
            System.out.println("1.7 OK");
        if(Solution.athleteLeftSport(3,a.getId()) == ReturnValue.NOT_EXISTS)
            System.out.println("1.8 OK");
        if(Solution.athleteLeftSport(s.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.9 OK");

        Athlete b = new Athlete();
        b.setId(11);
        b.setCountry("Israel");
        b.setName("g");
        b.setIsActive(true);

        Solution.addAthlete(b);
        Solution.athleteJoinSport(s.getId(),b.getId());
        if(Solution.makeFriends(a.getId(),b.getId()) == ReturnValue.OK)
            System.out.println("1.10 OK");
        if(Solution.makeFriends(a.getId(),b.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.11 OK");
        if(Solution.makeFriends(b.getId(),a.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.12 OK");
        if(Solution.makeFriends(a.getId(),a.getId()) == ReturnValue.BAD_PARAMS)
            System.out.println("1.13 OK");

        if(Solution.removeFriendship(a.getId(),a.getId()) == ReturnValue.NOT_EXISTS)
            System.out.println("1.14 OK");
        if(Solution.removeFriendship(b.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.15 OK");

        if(Solution.changePayment(b.getId(),s.getId(),200) == ReturnValue.NOT_EXISTS)
            System.out.println("1.16 OK");

        Athlete c = new Athlete();
        c.setId(22);
        c.setCountry("Canada");
        c.setName("g");

        Solution.addAthlete(c);
        // a && c not in Joined, b in Joined => a not popular
        if(Solution.makeFriends(b.getId(),a.getId()) == ReturnValue.OK)
            System.out.println("1.17 OK");
        if(Solution.makeFriends(a.getId(),c.getId()) == ReturnValue.OK)
            System.out.println("1.18 OK");
        if(!Solution.isAthletePopular(a.getId()))
            System.out.println("1.19 OK");

        if(Solution.athleteLeftSport(s.getId(),b.getId()) == ReturnValue.OK)
            System.out.println("1.20 OK");
        if(Solution.isAthletePopular(a.getId()))
            System.out.println("1.21 OK");
        Solution.clearTables();

        Solution.addAthlete(a);
        Solution.addAthlete(b);
        Solution.addAthlete(c);
        Solution.addSport(s);
        Solution.athleteJoinSport(s.getId(),a.getId());
        Solution.athleteJoinSport(s.getId(),b.getId());
        Solution.athleteJoinSport(s.getId(),c.getId());

        Solution.confirmStanding(s.getId(),a.getId(),1);
        Solution.confirmStanding(s.getId(),b.getId(),2);
        Solution.confirmStanding(s.getId(),c.getId(),2);

        if(Solution.getTotalNumberOfMedalsFromCountry("Israel") == 2)
            System.out.println("1.22 OK");
        if(Solution.getTotalNumberOfMedalsFromCountry("Canada") == 0)
            System.out.println("1.23 OK");
        Solution.athleteDisqualified(s.getId(),a.getId());
        if(Solution.getTotalNumberOfMedalsFromCountry("Israel") == 1)
            System.out.println("1.24 OK");
        Solution.changePayment(a.getId(),s.getId(),100);
        Solution.changePayment(b.getId(),s.getId(),100);
        Solution.changePayment(c.getId(),s.getId(),100);

        if(Solution.getIncomeFromSport(s.getId()) == 100)
            System.out.println("1.25 OK");

        Athlete d = new Athlete();
        d.setId(22);
        d.setCountry("France");
        d.setName("g");
        d.setIsActive(true);
        Solution.addAthlete(d);
        if(Solution.athleteJoinSport(s.getId(),d.getId()) == ReturnValue.ALREADY_EXISTS)
            System.out.println("1.26.0 OK");
        Solution.changePayment(d.getId(),s.getId(),300);
        if(Solution.getIncomeFromSport(s.getId()) == 100)
            System.out.println("1.26 OK");
        Solution.athleteLeftSport(s.getId(),a.getId());
        Solution.athleteJoinSport(s.getId(),a.getId());
        Solution.confirmStanding(s.getId(),d.getId(),2);
        Solution.confirmStanding(s.getId(),a.getId(),3);
        if(Solution.getBestCountry().contains("Israel"))  //  should by Israel
            System.out.println("1.27 OK");
    }

    @Test
    public void Test_2_Basic() {
        Solution.clearTables();
        Athlete a = new Athlete();
        Sport s = new Sport();
        a.setId(1);
        a.setName("yossi");
        a.setCountry("israel");
        a.setIsActive(true);
        Solution.addAthlete(a);
        a.setId(2);
        a.setName("gadi");
        a.setCountry("israel");
        a.setIsActive(false);
        Solution.addAthlete(a);
        a.setId(3);
        a.setName("moshe");
        a.setCountry("canada");
        a.setIsActive(true);
        Solution.addAthlete(a);
        s.setId(12);
        s.setName("tennis");
        s.setCity("tel-aviv");
        Solution.addSport(s);
        s.setId(1);
        s.setName("swim");
        s.setCity("tel-aviv");
        Solution.addSport(s);
        s.setId(3);
        s.setName("football");
        s.setCity("haifa");
        Solution.addSport(s);
        Solution.athleteJoinSport(12,1);
        Solution.athleteJoinSport(1,2);
        Solution.makeFriends(1,2);
        Solution.makeFriends(1,3);

        if(Solution.isAthletePopular(1))
            System.out.println("2.1 failed");
        if(Solution.isAthletePopular(2))
            System.out.println("2.2 failed");
        if(Solution.getIncomeFromSport(1) != 100)
            System.out.println("2.3 failed");
        if(Solution.confirmStanding(12,1,1) != ReturnValue.OK)
            System.out.println("2.4 failed");
        if(Solution.confirmStanding(1,2,1) != ReturnValue.NOT_EXISTS)
            System.out.println("2.5 failed");
        if(Solution.getTotalNumberOfMedalsFromCountry("israel") != 1)
            System.out.println("2.6 failed");
        if(!Solution.getBestCountry().contains("israel"))
            System.out.println("2.7 failed");

        if(Solution.athleteDisqualified(1,2)!= NOT_EXISTS)
            System.out.println("2.8 failed");
        if(!Solution.getBestCountry().contains("israel"))
            System.out.println("2.9 failed");
        if(Solution.athleteDisqualified(12,1)!= ReturnValue.OK)
            System.out.println("2.10 failed");
        if(!Solution.getBestCountry().equals(""))
            System.out.println("2.11 failed");
        if(Solution.confirmStanding(12,1,1) != ReturnValue.OK)   // disqualified athletes can get medals
            System.out.println("2.12 failed");
        Solution.athleteJoinSport(12,3);
        Solution.athleteJoinSport(2,3);
        Solution.confirmStanding(12,3,1);
        Solution.confirmStanding(2,3,2);
        if(Solution.getTotalNumberOfMedalsFromCountry("canada") != 1)
            System.out.println("2.13 failed");
        if(!Solution.getBestCountry().contains("canada"))
            System.out.println("2.14 failed");
        if(!Solution.getMostPopularCity().contains("tel-aviv"))
            System.out.println("2.15 failed");
        if(Solution.deleteAthlete(Solution.getAthleteProfile(1)) != ReturnValue.OK)
            System.out.println("2.16 failed");
        if(Solution.athleteLeftSport(12,1) != ReturnValue.NOT_EXISTS) // should have been deleted on previous test
            System.out.println("2.17 failed");
        Solution.athleteLeftSport(12,3);
        Solution.athleteLeftSport(2,3);
        Solution.athleteLeftSport(1,2);
        String str = Solution.getBestCountry();
        if(!Solution.getBestCountry().equals(""))
            System.out.println("2.16 failed");
        if(!Solution.getMostPopularCity().equals("tel-aviv"))
            System.out.println("2.17 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(2));
        Solution.deleteAthlete(Solution.getAthleteProfile(3));
        if(!Solution.getBestCountry().isEmpty())
            System.out.println("2.18 failed");
        if(!Solution.getMostPopularCity().equals("tel-aviv"))
            System.out.println("2.19 failed");
        Solution.deleteSport(Solution.getSport(12));
        Solution.deleteSport(Solution.getSport(1));
        Solution.deleteSport(Solution.getSport(3));
        if(!Solution.getMostPopularCity().isEmpty())   // no cities in DB
            System.out.println("2.19.1 failed");
        a.setId(1);
        a.setName("A");
        a.setCountry("A");
        a.setIsActive(true);
        Solution.addAthlete(a);
        a.setId(2);
        Solution.addAthlete(a);
        s.setId(11);
        s.setName("S");
        s.setCity("SA");
        Solution.addSport(s);
        s.setId(22);
        s.setName("S");
        s.setCity("SB");
        Solution.addSport(s);
        Solution.athleteJoinSport(11,1);
        Solution.athleteJoinSport(11,2);
        Solution.athleteJoinSport(22,1);
        Solution.athleteJoinSport(22,2);
        if(!Solution.getMostPopularCity().contains("SB"))   // same AVG for SA and SB. should choose by LEX sort
            System.out.println("2.20 failed");
    }
    public static void Test_3_getAthleteMedals() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(11);
        s.setName("tennis");
        s.setCity("TLV");
        Solution.addSport(s);
        s.setId(22);
        s.setName("golf");
        s.setCity("paris");
        Solution.addSport(s);
        Athlete atl=new Athlete();
        atl.setId(1);
        atl.setIsActive(true);
        atl.setName("name");
        atl.setCountry("country");
        Solution.addAthlete(atl);
        Solution.athleteJoinSport(11,1);
        Solution.athleteJoinSport(22,1);
        Solution.confirmStanding(11,1,1);
        Solution.confirmStanding(22,1,1);
        ArrayList<Integer> expected = new ArrayList<Integer>(Arrays.asList(2,0,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.1 failed");
        expected.clear();
        s.setId(33);
        Solution.addSport(s);
        Solution.athleteJoinSport(33,1);
        Solution.confirmStanding(33,1,2);
        expected.addAll(Arrays.asList(2,1,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.2 failed");
        Solution.athleteDisqualified(11,1);
        expected.clear();
        expected.addAll(Arrays.asList(1,1,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.3 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        expected.addAll(Arrays.asList(0,0,0));
        if(!expected.equals(Solution.getAthleteMedals(1)))
            System.out.println("3.4 failed");
    }
    public static void Test_4_getMostRatedAthletes() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setId(11);
        s.setCity("haifa");
        s.setName("t");
        Solution.addSport(s);
        Athlete atl = new Athlete();
        atl.setIsActive(true);
        atl.setName("n");
        atl.setCountry("c");
        for (int i : IntStream.range(1, 5).toArray()) {  // 1-4
            atl.setId(i);
            Solution.addAthlete(atl);
            Solution.athleteJoinSport(11, i);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>(IntStream.range(1, 5).boxed().collect(Collectors.toList()));
        if (!expected.equals(Solution.getMostRatedAthletes()))  // less than 10 elements
            System.out.println("4.1 failed");
        for (int i : IntStream.range(5, 12).toArray()) {  // 5-11
            atl.setId(i);
            Solution.addAthlete(atl);
            Solution.athleteJoinSport(11, i);
        }
        expected.clear();
        expected.addAll(IntStream.range(1, 11).boxed().collect(Collectors.toList()));
        if (!expected.equals(Solution.getMostRatedAthletes()))  // exactly 10 elements (but there're 11 that fit)
            System.out.println("4.1 failed");

        for (int i : IntStream.range(6, 12).toArray())  // 6-11
            Solution.confirmStanding(11, i, 1);
        expected.clear();
        expected.addAll(IntStream.range(6, 12).boxed().collect(Collectors.toList())); // add 6-11
        expected.addAll(IntStream.range(1, 5).boxed().collect(Collectors.toList())); // add 1-4
        if (!expected.equals(Solution.getMostRatedAthletes()))  // order has changed
            System.out.println("4.2 failed");
        for (int i : IntStream.range(1, 4).toArray()) { // 1-3
            Solution.confirmStanding(11, i, 1);
            Solution.confirmStanding(11, i, 2);
        }
        expected.clear();
        expected.addAll(IntStream.range(6, 12).boxed().collect(Collectors.toList())); // add 6-11
        expected.addAll(IntStream.range(1, 4).boxed().collect(Collectors.toList())); // add 1-3
        expected.add(4);
        if (!expected.equals(Solution.getMostRatedAthletes()))    // order has changed again
            System.out.println("4.3 failed");
    }
    public static void Test_5_getCloseAthletes() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setCity("haifa");
        s.setName("t");
        for (int i : IntStream.range(11, 14).toArray()) {  // 11-13
            s.setId(i);
            Solution.addSport(s);
        }
        Athlete at = new Athlete();
        at.setName("a");
        at.setCountry("a");
        for (int i : IntStream.range(1, 13).toArray()) {  // 1-5 observers, 6-12 participants
            if(i==6)
                at.setIsActive(true);
            at.setId(i);
            Solution.addAthlete(at);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>();
        expected.addAll(IntStream.range(2, 12).boxed().collect(Collectors.toList()));
        if(!expected.equals(Solution.getCloseAthletes(1)))  // no one joined any sport
            System.out.println("5.1 failed");
        Solution.athleteJoinSport(11,1);
        expected.clear();
        expected= new ArrayList<Integer>();
        if(!expected.equals(Solution.getCloseAthletes(1))) // cannot be close to himself
            System.out.println("5.2 failed");
        for (int i : IntStream.range(2,6).toArray())  // 2-5
            Solution.athleteJoinSport(11,i);
        expected.clear();
        expected.addAll(IntStream.range(2,6).boxed().collect(Collectors.toList()));
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are 100% fit
            System.out.println("5.3 failed");
        Solution.athleteJoinSport(12,1);
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are 50% fit
            System.out.println("5.4 failed");
        Solution.athleteJoinSport(13,1);
        expected.clear();
        if(!expected.equals(Solution.getCloseAthletes(1))) // 2-5 are only 33.3% fit (>50%)
            System.out.println("5.4 failed");
        Solution.athleteLeftSport(13,1);    // 1 is part of sportID in (11,12)
        for (int i : IntStream.range(6,13).toArray()) {
            Solution.athleteJoinSport(11, i);
            Solution.athleteJoinSport(12, i);
        }
        // now : 2-5 are 50% fit && 6-12 are 100% fit
        // notice expected list should be in ascending athleteID order
        expected.clear();
        expected.addAll(IntStream.range(2,6).boxed().collect(Collectors.toList())); // 2-5
        expected.addAll(IntStream.range(6,12).boxed().collect(Collectors.toList())); //6-11
        if(!expected.equals(Solution.getCloseAthletes(1)))
            System.out.println("5.5 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        if(!expected.equals(Solution.getCloseAthletes(1))) // athlete doesn't exist
            System.out.println("5.6 failed");
    }
    public static void Test_6_getSportsRecommendation() {
        Solution.clearTables();
        Sport s = new Sport();
        s.setCity("haifa");
        s.setName("t");
        for (int i : IntStream.range(11, 16).toArray()) {  // 11-15
            s.setId(i);
            Solution.addSport(s);
        }
        Athlete at = new Athlete();
        at.setName("a");
        at.setCountry("a");
        for (int i : IntStream.range(1, 13).toArray()) {  // 1-5 observers, 6-12 participants
            if(i==6)
                at.setIsActive(true);
            at.setId(i);
            Solution.addAthlete(at);
        }
        ArrayList<Integer> expected = new ArrayList<Integer>();
        if(!expected.equals(Solution.getSportsRecommendation(1)))  // no one joined any sport
            System.out.println("6.1 failed");
        Solution.athleteJoinSport(11,1);
        if(!expected.equals(Solution.getSportsRecommendation(1))) // cannot recommend to himself
            System.out.println("6.2 failed");
        for (int i : IntStream.range(2,6).toArray())  // now 2-5 are close to 1
            Solution.athleteJoinSport(11,i);
        if(!expected.equals(Solution.getSportsRecommendation(1))) // athleteID=1 already joined to "recommended" sports
            System.out.println("6.3 failed");
        Solution.athleteJoinSport(12,1);
        for (int i : IntStream.range(2,6).toArray()) {  // 2-5
            Solution.athleteJoinSport(12, i);
            Solution.athleteJoinSport(13, i);
            Solution.athleteJoinSport(14, i);
        }
        expected.clear();
        expected.addAll(Arrays.asList(13,14));
        if(!expected.equals(Solution.getSportsRecommendation(1)))  // 2-5 are 50% close to 1 && recommend 13,14
            System.out.println("6.4 failed");
        Solution.athleteLeftSport(11,1);
        Solution.athleteJoinSport(14,1);
        expected.clear();
        expected.addAll(Arrays.asList(11,13));
        if(!expected.equals(Solution.getSportsRecommendation(1)))   // 2-5 are 50% close to 1 && recommend 13,11
            System.out.println("6.5 failed");
        for (int i : IntStream.range(6,11).toArray()) {  // 6-10
            Solution.athleteJoinSport(12, i);
            Solution.athleteJoinSport(13, i);
            Solution.athleteJoinSport(14, i);
        }
        Solution.athleteJoinSport(14,1);
        expected.clear();
        expected.add(13);
        expected.add(11);
        if(!expected.equals(Solution.getSportsRecommendation(1)))   // only recommendation is 13
            System.out.println("6.6 failed");
        Solution.deleteAthlete(Solution.getAthleteProfile(1));
        expected.clear();
        if(!expected.equals(Solution.getSportsRecommendation(1))) // athleteID=1 doesn't exist
            System.out.println("6.7 failed");
    }

}


