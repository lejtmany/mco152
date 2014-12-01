package pong;
/*
 * Ben Forgy
 * Nov 8, 2014
 */

class Degree {
    
    private int degree;
    public Degree(int degIn){
        degree = convertTodegree(degIn);
    }
    public void set(int degIn){
        degree = convertTodegree(degIn);
    }
    
    private static int convertTodegree(int crazyNum){
        int result = crazyNum % 360;
        if(result < 0){
            result = result + 360;
        }
        return result;
    }
    
    public int get(){
        return degree;
    }

    

}
