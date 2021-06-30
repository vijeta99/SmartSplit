package smart_split;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class HomePage extends JFrame {
JFrame f;
JButton add,showResult;
JTextField username,amount;
String curruser="p2";
int currid=3;



  public HomePage()
  {
      super("SmartSplit");
      initialise();
  }
    public HomePage(String u,int n)
    {

        curruser=u;
        currid=n;
    }
    public  void initialise()
    {
        setBackground(Color.cyan);
       add=new JButton("Add");

       username =new JTextField(16);
       amount=new JTextField(16);
        showResult=new JButton("Result");
       add(add);
       add(username);
       add(amount);
       add.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {
               try {
               String u=username.getText();
               String a=amount.getText();
               double amt=Double.parseDouble(a);
               int id=0;

                   conn c = new conn();
                   ResultSet rs = c.statement.executeQuery("select * from user");
                   int u1,u2;
                   while(rs.next())
                   {
                       String uname=rs.getString("name");
                       if(uname.equalsIgnoreCase(u))
                       {
                           id=rs.getInt("id");
                           break;
                       }
                   }
                   c.insertInTable(currid,id,amt,"owesto");

               }
               catch (Exception e)
               {
                   System.out.println(e);
               }

           }
       });

       add(showResult);

       showResult.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent ae) {

                   Compute compute = new Compute();
                   compute.driver();

                   JLabel heading2 = new JLabel("Lended");
                   JLabel heading1 = new JLabel("Borrowed");
                   heading1.setForeground(Color.RED);
                   ArrayList<String> usBorrowed = new ArrayList<>();
                   ArrayList<String> amtBorrowed = new ArrayList<>();
                   ArrayList<String> usLended = new ArrayList<>();
                   ArrayList<String> amtLended = new ArrayList<>();

               try {
                   conn c = new conn();

                   ResultSet rs = c.statement.executeQuery("select * from result");


                   while (rs.next()) {
                       String s = "";
                       int u1 = rs.getInt("user1");
                       int u2 = rs.getInt("user2");
                       double amt = rs.getDouble("amount");
                       if (u1 == currid) {
                           usBorrowed.add(c.getUserName(u2));
                           amtBorrowed.add(String.valueOf(amt));

                       }
                       if (u2 == currid) {
                           usLended.add(c.getUserName(u1));
                           amtLended.add(String.valueOf(amt));
                       }


                   }
               }
               catch(Exception e)
               {
                   System.out.println(e);
               }




               add(heading1);
                   for(int i=0;i<usBorrowed.size();i++)
                   {
                       String val= usBorrowed.get(i) +" "+ amtBorrowed.get(i);
                       JLabel br=new JLabel(val);
                       // br.setText(val);
                       br.setForeground(Color.BLACK);
                       add(br);
                   }
                   add(heading2);
                   for(int i=0;i<usLended.size();i++)
                   {
                       String val= usLended.get(i) +" "+ amtLended.get(i);
                       JLabel br=new JLabel(val);
                       // br.setText(val);
                       br.setForeground(Color.BLACK);
                       add(br);
                   }


               }



       });
       setSize(400,500);
       setLayout(new GridBagLayout());
       setVisible(true);
       setDefaultCloseOperation(EXIT_ON_CLOSE);






    }
    public static void main(String[] args) {

        new HomePage().setVisible(true);
        new HomePage("p0",1);
    }


}
