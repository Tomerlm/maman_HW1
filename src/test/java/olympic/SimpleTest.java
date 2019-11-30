package olympic;

import org.junit.Test;
import olympic.business.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertFalse;

public class SimpleTest extends AbstractTest{

    @Test
    public void simpleTestCreateAthlete()
    {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
    }

    @Test
    public void testDeleteUserNotExist(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);

        ReturnValue ret = Solution.deleteAthlete(a);
        assertEquals(NOT_EXISTS , ret);

    }

    @Test
    public void testDeleteUser(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        ret = Solution.deleteAthlete(a);
        assertEquals(OK , ret);

    }

    @Test
    public void simpleTestCreateAthleteTwice() {
        Athlete a = new Athlete();
        a.setId(1);
        a.setName("Artur");
        a.setCountry("Brazil");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK, ret);
        ret = Solution.addAthlete(a);
        assertEquals(ALREADY_EXISTS, ret);
    }

    @Test
    public void getAthleteTest(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK , ret);
        Athlete b = Solution.getAthleteProfile(11);
        assertEquals(b.getId() , -1);
    }

    @Test
    public void confirmStandAndDisqua(){
        addAtheleteSport(10, true , 10); // aid = 10 sid = 10
        ReturnValue ret = Solution.confirmStanding(10 , 10, 4);
        assertEquals(BAD_PARAMS, ret);
        ret = Solution.confirmStanding(10 , 10, 3);
        assertEquals(OK, ret);
        ret = Solution.athleteDisqualified(10, 10);
        assertEquals(OK, ret);
    }

    @Test
    public void nothing(){

    }

    @Test
    public void addFriends(){
        ReturnValue ret = Solution.makeFriends(10 , 11);
        assertEquals(NOT_EXISTS, ret);
        addAtheleteSport(10, true ,10);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(NOT_EXISTS, ret);
        addAtheleteSport(11, true ,11);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(OK, ret);
        ret = Solution.makeFriends(10 , 11);
        assertEquals(ALREADY_EXISTS, ret);
        ret = Solution.removeFriendship(12, 13);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.removeFriendship(10, 13);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.removeFriendship(10, 11);
        assertEquals(OK, ret);
        ret = Solution.removeFriendship(10, 11);
        assertEquals(NOT_EXISTS, ret);


    }

    @Test
    public void setPayment(){
        addAtheleteSport(10, false ,10);
        ReturnValue ret = Solution.changePayment(11, 10, 101);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.changePayment(10, 11, 101);
        assertEquals(NOT_EXISTS, ret);
        ret = Solution.changePayment(10, 10, -1);
        assertEquals(BAD_PARAMS, ret);
        ret = Solution.changePayment(10, 10, 80);
        assertEquals(OK, ret);


    }

    @Test
    public void isPop(){
        addAtheleteSport(10, true ,10);
        addAtheleteSport(11, true ,11);
        addAtheleteSport(12, true ,12);
        addAtheleteSport(13, false ,13);
        addAtheleteSport(14, false ,14);
        Solution.makeFriends(11, 10);
        Solution.makeFriends(10, 12);
        Solution.makeFriends(10, 13);
        Solution.athleteJoinSport(11, 10);
        Solution.athleteJoinSport(12, 10);
        Solution.athleteJoinSport(13, 10);
        boolean ret = Solution.isAthletePopular(10);
        assertTrue(ret);
        Solution.makeFriends(10, 14);
        ret = Solution.isAthletePopular(10);
        assertFalse(ret);
        Solution.athleteJoinSport(14, 10);
        ret = Solution.isAthletePopular(10);
        assertTrue(ret);
    }

    @Test
    public void totalMedals(){
        //check for one athelte
        addAtheleteSport(10, true, 10);
        addAtheleteSport(11, true, 11);
        int ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(0, ret);
        Solution.confirmStanding(10, 10, 2);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(1, ret);
        Solution.athleteDisqualified(10 , 10);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(0, ret);
        Solution.confirmStanding(10, 10, 2);
        Solution.confirmStanding(11, 11, 3);
        ret = Solution.getTotalNumberOfMedalsFromCountry("Argentina");
        assertEquals(2, ret);
        Solution.athleteDisqualified(10 , 10);
        createAndAddAthlete(12, "bjorn", false, "Sweden");
        Solution.athleteJoinSport(10, 12);
        Solution.confirmStanding(10, 12, 3);
        createAndAddAthlete(13, "bjorn", false, "Sweden");
        Solution.athleteJoinSport(10, 13);
        Solution.confirmStanding(10, 13, 3);
        String retStr = Solution.getBestCountry();
        assertEquals("Sweden", retStr);



    }

    @Test
    public void totalPayment(){
        addAtheleteSport(10, false, 10);
        addAtheleteSport(11, false, 11);
        Solution.athleteJoinSport(10, 11);
        int ret = Solution.getIncomeFromSport(10);
        assertEquals(200, ret);
        Solution.changePayment(10, 10, 200);
        ret = Solution.getIncomeFromSport(10);
        assertEquals(300, ret);

    }

    public void addAtheleteSport(int aid, boolean isActive, int sid){
        Athlete a = new Athlete();
        a.setId(aid);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(isActive);
        Sport s = new Sport();
        s.setId(sid);
        s.setCity("haifa");
        s.setName("soccer");
        ReturnValue ret = Solution.addAthlete(a);
        ret = Solution.addSport(s);
        Solution.athleteJoinSport(sid,aid);
    }
    public void createAndAddAthlete(int id, String name, boolean isActive, String country){
        Athlete bjorn = new Athlete();
        bjorn.setId(12);
        bjorn.setIsActive(isActive);
        bjorn.setName(name);
        bjorn.setCountry(country);
        Solution.addAthlete(bjorn);
    }

}