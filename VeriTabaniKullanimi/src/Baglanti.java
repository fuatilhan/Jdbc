import java.sql.*;
import java.util.Scanner;

public class Baglanti {
    private String kullaniciAdi="root";
    private String parola="";
    private String dbIsmi="demo";
    private String host="localhost";
    private int port=3306;

    private Connection con=null;

    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    public  void preparedCalisanlariGetir(int id){
        String sorgu="SELECT *FROM calisanlar where id>? and ad LIKE ?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,"g%");
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String email=rs.getString("email");
                System.out.println("Ad:"+ad+" Soyad: "+soyad+" E-Mail: "+email);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void calisanEkle(){
        try {
            statement=con.createStatement();
            String ad="Semih";
            String soyad="Sepuhi";
            String email="Sepuhi@gmail.com";
            String sorgu="Insert into calisanlar(ad,soyad,email) VALUES(" + "'" + ad+ "',"+"'" + soyad+ "',"+"'" + email+ "')";
            statement.executeUpdate(sorgu);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void calisanSil(){
        try {
            statement=con.createStatement();
            String sorgu="DELETE FROM calisanlar WHERE id>3";
           int deger= statement.executeUpdate(sorgu);
            System.out.println(deger+" kadar veri etkilendi...");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void commitveRollback(){
        Scanner scan=new Scanner(System.in);
        try {
            con.setAutoCommit(false);
            String sorgu1="DELETE FROM calisanlar WHERE id=3";
            String sorgu2="UPDATE calisanlar SET email='Seeeeepuuuhi@gmail.com' WHERE id=8";
            System.out.println("Güncellenmeden önce");
            calisanlariGetir();
            statement=con.createStatement();
            statement.executeUpdate(sorgu1);
            statement.executeUpdate(sorgu2);
            System.out.println("İşlemleriniz kaydedilsin mi?");
            String cevap=scan.nextLine();
            if(cevap.equals("Y")){
                con.commit();
                calisanlariGetir();
                System.out.println("Veri tabanı güncellendi");
            }
            else{
                con.rollback();
                System.out.println("Veri tabanı güncellenmesi iptal edildi...");
                calisanlariGetir();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void preparedCalisanSil(int id){
        String sorgu="Delete from calisanlar where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void calisanGüncelle(){
        try {
            statement=con.createStatement();
            String sorgu="Update calisanlar Set email= 'fuatilhan@gmail.com' WHEre id=1";
            statement.executeUpdate(sorgu);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void calisanlariGetir(){

        String sorgu="Select * From calisanlar";

        try {
            statement=con.createStatement();
           ResultSet rs= statement.executeQuery(sorgu);

           while(rs.next()){
               int id=rs.getInt("id");
               String ad=rs.getString("ad");
               String soyad=rs.getString("soyad");
               String email=rs.getString("email");
               System.out.println("ID: "+id+" Ad: "+ad+" Soyad: "+soyad+" E-Mail: "+email);
           }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public Baglanti(){
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbIsmi+"?useUnicode=true&characterEncoding=utf8";
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException ex){
            System.out.println("Driver Bulunamadı...");

        }
        try {
            con=DriverManager.getConnection(url,kullaniciAdi,parola);
            System.out.println("Bağlantı başarılı...");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Baglanti baglanti=new Baglanti();
       /* System.out.println("************Silinmeden önce***********");
        baglanti.calisanlariGetir();
        System.out.println("******************************");
        baglanti.calisanEkle();
        baglanti.calisanlariGetir();
        baglanti.calisanGüncelle();
        baglanti.calisanSil();
        baglanti.calisanlariGetir();
        // baglanti.preparedCalisanlariGetir(1);
       // baglanti.calisanlariGetir();
        baglanti.preparedCalisanSil(1);
        System.out.println("****************");
        baglanti.calisanlariGetir();*/
        baglanti.commitveRollback();
    }


}
