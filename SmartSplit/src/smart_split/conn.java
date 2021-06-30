package smart_split;

import java.sql.*;

public  class conn {
    Connection connection;
    Statement statement;
    public conn() {
        try {
          //  Class.forName("com.mysql.jdbc.driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/smartsplit", "root", "vijeta");
            statement = connection.createStatement();
            /*ResultSet resultSet = statement.executeQuery("select * from user_data");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }*/
        } catch (Exception e) {
            System.out.println(e);
        }

        // string jdbc_url="jdbc:mysql://localhost:3306/mysql";

    }
    public void insertInTable(int u1,int u2,double amt,String table) throws SQLException {
        System.out.println("insertion in result");
        int id=0;
        String q="select * from "+table;
        ResultSet resultSet = statement.executeQuery(q);
        while(resultSet.next())
        {

                id=resultSet.getInt("id");
        }
        id++;
        q="INSERT INTO "+table+"   VALUES (?,?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(q);

        ps.setInt(1,id);
        ps.setInt(2, u1);
        ps.setInt(3,u2);
        ps.setDouble(4,amt);
        ps.executeUpdate();
    }
    public void InsertInTable(String uname,String pswd,String table) throws SQLException {
        int id=0;
        String q="select * from "+table;
        ResultSet resultSet = statement.executeQuery(q);
        while(resultSet.next())
        {

            id=resultSet.getInt("id");
        }
        id++;
        q="INSERT INTO "+table+"   VALUES (?,?, ?)";
        PreparedStatement ps = connection.prepareStatement(q);

        ps.setInt(1,id);
        ps.setString(2, uname);
        ps.setString(3,pswd);
        ps.executeUpdate();


    }
    public double[][] getGraphVal() throws SQLException {
        conn c=new conn();
        int N= c.getTotalUsers("user");
        double g[][]=new double[N+1][N+1];
        ResultSet resultSet = statement.executeQuery("select * from owesto");
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                g[i][j]=0;
            }
        }
        while (resultSet.next()) {
            int u1=resultSet.getInt("user1");
            int u2=resultSet.getInt("user2");
            double amt=resultSet.getDouble("amount");
            g[u1][u2]=amt;
            System.out.println(u1+ " "+ u2+" "+amt);
        }
        return g;

    }
    int getTotalUsers(String table) throws SQLException {
        String s="select * from "+table;
        ResultSet rs=statement.executeQuery(s);
        int totalUsers=0;
        while (rs.next())
        {
            totalUsers++;
        }
        return totalUsers;
    }
    void freeTableResult() throws SQLException
    {
        int id=1;
        int n=getTotalUsers("result");
        while(id<=n) {
            String i=String.valueOf(id);
            String q="delete from result where id="+i;
             statement.executeUpdate(q);
            id++;
        }
    }
    void showResult() throws SQLException {
        freeTableResult();
        Compute compute=new Compute();
        conn c=new conn();
        compute.driver();
       // ResultSet rs=c.statement.executeQuery();

    }

    String getUserName(int id) throws SQLException {
        ResultSet rs=statement.executeQuery("select * from user");
        String s="";
        while(rs.next())
        {
            if(rs.getInt("id")==id)
            {
                s= rs.getString("name");
                break;
            }
        }
        return s;
    }

    void deleteRowFromTable(int a,int b,String table) throws SQLException {
        String u1=String.valueOf(a);
        String u2=String.valueOf(b);
        String q="delete from +table "+ "where user1="+u1+" and user2="+u2;
        ResultSet rs=statement.executeQuery(q);

    }


    public static void main(String[] args) {
        conn c=new conn();
    }
}
