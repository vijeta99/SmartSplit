package smart_split;
//Login and Register here
public  class User {
    private String name;
   private int id;
   public void setName(String s)
   {
       name=s;
   }
   public void setId(int n)
   {
       id=n;
   }
    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }

    public static void main(String[] args) {
        User u=new User();
    }
}
