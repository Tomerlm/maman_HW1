package olympic;

import olympic.business.Athlete;
import olympic.business.ReturnValue;
import olympic.business.Sport;
import org.junit.Test;

import static olympic.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;

public class AthleteBasicTest extends AbstractTest {
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
        Athlete b = new Athlete();
        b.setId(11);
        b.setName("Eli");
        b.setCountry("Argentina");
        b.setIsActive(true);
        ReturnValue ret = Solution.addAthlete(a);
        Solution.addAthlete(b);
        assertEquals(OK , ret);
        Athlete c = Solution.getAthleteProfile(10);
        assertEquals(c.getId() , a.getId());
        assertEquals(c.getName() , a.getName());
    }

    @Test
    public void aJoinS(){
        Athlete a = new Athlete();
        a.setId(10);
        a.setName("Eli");
        a.setCountry("Argentina");
        a.setIsActive(true);
        Sport s = new Sport();
        s.setId(10);
        s.setCity("haifa");
        s.setName("soccer");
        ReturnValue ret = Solution.addAthlete(a);
        assertEquals(OK , ret);
        ret = Solution.addSport(s);
        assertEquals(OK , ret);
        ret = Solution.athleteJoinSport(10, 10);
        assertEquals(OK , ret);
        Sport test = Solution.getSport(10);
        assertEquals(1, test.getAthletesCount());
        ret = Solution.athleteLeftSport(10, 10);
        assertEquals(OK, ret);
        test = Solution.getSport(10);
        assertEquals(0, test.getAthletesCount());
    }
}
