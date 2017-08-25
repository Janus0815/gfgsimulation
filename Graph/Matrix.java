package Graph;
import java.util.HashMap;

public class Matrix {
	HashMap<Integer, HashMap<Integer, Boolean>> data = new HashMap<Integer, HashMap<Integer,Boolean>>();
	   
	   
    public void put(int x, int y, Boolean value){
        HashMap<Integer,Boolean> m = data.get(x);
        if(m==null)
            m = new HashMap<Integer,Boolean>();
        m.put(y, value);
        data.put(x, m);
    }
   
    public Boolean get(int x, int y){
        HashMap<Integer,Boolean> m  =data.get(x);
        if(m==null)
            return null;			
        return m.get(y);
    }
}
