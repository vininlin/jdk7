/**
 * 
 */
package concurrent.test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Àà/½Ó¿Ú×¢ÊÍ
 * 
 * @author linwn@ucweb.com
 * @createDate 2015-10-29
 * 
 */
public class IncreamentTask extends RecursiveAction {
    
    private static final int THRESHOLD = 100;
    private long[] array;
    final int lo;
    final int hi;
    
    public IncreamentTask(long[] array,int lo,int hi){
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }
    
    @Override
    protected void compute() {
       if((hi - lo) < THRESHOLD){
           for(int i = lo; i < hi; ++i)
               array[i]++;
       }else{
           int mid = (hi + lo) >>> 1;
           invokeAll(new IncreamentTask(array,lo,mid),new IncreamentTask(array,lo,mid));
       }
    }
    
    public static void main(String[] args){
        int length = 1000;
        long[] array = new long[length];
        for(int i = 0 ; i < length; i++){
            array[i] = i;
        }
        IncreamentTask task = new IncreamentTask(array,0,length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(task);
        
    }

}
