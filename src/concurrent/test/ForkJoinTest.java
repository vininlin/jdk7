/**
 * 
 */
package concurrent.test;

import java.util.concurrent.ExecutionException;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;



/**
 * Àà/½Ó¿Ú×¢ÊÍ
 * 
 * @author linwn@ucweb.com
 * @createDate 2015-10-26
 * 
 */
public class ForkJoinTest {

    public static void main(String[] args){
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println("submit,,current thread is " + Thread.currentThread().getName());
        Future<Integer> result = pool.submit(new Calculator(0,1000));
        try {
            System.out.println(result.get());
            System.out.println("get Result,current thread is " + Thread.currentThread().getName());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("serial")
    static class Calculator extends RecursiveTask<Integer>{

        private static final int THRESHOLD = 100;
        private int start;
        private int end;
        
        public Calculator(int start,int end){
            this.start = start;
            this.end = end;
        }
        @Override
        protected Integer compute() {
            int sum = 0;
            if((end - start) <= THRESHOLD){
                for(int i = start ; i < end; i++){
                    sum += i;
                }
                System.out.println("compute,sum="+sum+",current thread is " + Thread.currentThread().getName() + ",task is " + this);
            }else{
                int middle = (start + end) /2;
                Calculator left = new Calculator(start,middle);
                Calculator right = new Calculator(middle + 1,end);
                System.out.println("left fork,current thread is " + Thread.currentThread().getName()+",task is " + left);
                left.fork();
                System.out.println("right fork,current thread is " + Thread.currentThread().getName()+",task is " + right);
                right.fork();
                System.out.println("leftJoin" + ",current thread is " + Thread.currentThread().getName()+",task is " + left);
                int leftSum = left.join();
                System.out.println("rightJoin" + ",current thread is " + Thread.currentThread().getName()+",task is " + right);
                int rightSum = right.join();
                sum = leftSum + rightSum;
            }
            return sum;
        }
        
    }
}
