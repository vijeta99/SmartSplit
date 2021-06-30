package smart_split;
import java.io.*;
import java.sql.*;
import  java.util.*;
public class Compute {

    static int getMin(double arr[])
    {
        int minInd = 0;
        int n=arr.length;
        for (int i = 1; i < n; i++)
            if (arr[i] < arr[minInd])
                minInd = i;
        return minInd;
    }
    static int getMax(double arr[])
    {
        int maxInd = 0;
        int N=arr.length;
        for (int i = 1; i < N; i++)
            if (arr[i] > arr[maxInd])
                maxInd = i;
        return maxInd;
    }
    static double minOf2(double x, double y)
    {
        return (x < y) ? x: y;
    }


    static void minCashFlowRec(double amount[]) throws SQLException {
        // Find the indexes of minimum and
        // maximum values in amount[]
        // amount[mxCredit] indicates the maximum amount
        // to be given (or credited) to any person .
        // And amount[mxDebit] indicates the maximum amount
        // to be taken(or debited) from any person.
        // So if there is a positive value in amount[],
        // then there must be a negative value
        int mxCredit = getMax(amount), mxDebit = getMin(amount);

        // If both amounts are 0, then
        // all amounts are settled
        if (amount[mxCredit] == 0 && amount[mxDebit] == 0)
            return;

        // Find the minimum of two amounts
        double min = minOf2(-amount[mxDebit], amount[mxCredit]);
        amount[mxCredit] -= min;
        amount[mxDebit] += min;
        int id=0;

        // If minimum is the maximum amount to be

        conn c=new conn();
       c.insertInTable(mxDebit,mxCredit,min,"result");
        // Recur for the amount array.
        // Note that it is guaranteed that
        // the recursion would terminate
        // as either amount[mxCredit]  or
        // amount[mxDebit] becomes 0
        minCashFlowRec(amount);
    }
    static void minCashFlow(double[][] graph) throws SQLException {
        // Create an array amount[],
        // initialize all value in it as 0.
        int N= graph.length;
        double amount[]=new double[N];

        // Calculate the net amount to
        // be paid to person 'p', and
        // stores it in amount[p]. The
        // value of amount[p] can be
        // calculated by subtracting
        // debts of 'p' from credits of 'p'
        for (int p = 0; p < N; p++)
            for (int i = 0; i < N; i++)
                amount[p] += (graph[i][p] - graph[p][i]);



        minCashFlowRec(amount);
    }
    public void driver()
    {
        try {
            conn c = new conn();
            int V = c.getTotalUsers("user");
            V+=1;
            double g[][] = new double[V][V];
            for(int i=0;i<V;i++)
            {
                for(int j=0;j<V;j++)
                    g[i][j]=0;
            }

            g=c.getGraphVal();
            c.freeTableResult();

            minCashFlow(g);

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public Compute()
    {
        driver();
    }

    public static void main(String[] args) {
       //TODO CALCULATE TOTAL NUMBER OF USERS
       // Compute compute=new Compute();

    }

}
