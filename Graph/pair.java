package Graph;

public class pair{
    int a;
    int b;
    pair(int p,int q){
        a=p;
        b=q;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof pair) {
            pair p = (pair)o;
            return p.a == a && p.b == b;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return new Integer(a).hashCode() * 31 + new Integer(b).hashCode();
    }
}
