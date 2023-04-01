/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hijawicampany;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */


public class functions {
    
   public String returnvalue(String TableName, String search , String Searchword){
       String found="no";
       Connection connection=null;
       String sql= String.format("SELECT status FROM %s WHERE  name=?",TableName,Searchword);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,search);
                ResultSet s1 = ps.executeQuery();/*if(ss.next()){
               status =ss.getInt(1);*/
                
                if(s1.next()) {int x = s1.getInt(1);found=Integer.toString(x);return found;}//exists
                else{return found;}//doesnot exist
        } catch (SQLException ex) {
            Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
        }
       try {
           connection.close();
       } catch (SQLException ex) {
           Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
       }
       return found;
       
   }

   public static boolean DoesItExist(String TableName, String search , String Searchword) {//type is the Table name
       boolean x= false;
       Connection connection=null;
       String sql= String.format("SELECT * FROM %s WHERE  %s=?",TableName,Searchword);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,search);
                ResultSet s1 = ps.executeQuery();
                if(s1.next()) {x=true;System.out.print("existes");return x;}//exists
                else{System.out.print("no existes");return x;}//doesnot exist
        } catch (SQLException ex) {
            Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
        }
       try {
           connection.close();
       } catch (SQLException ex) {
           Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
       }
      return x;
   }
   
   public ArrayList<String> get (String TableName, String search,String Searchword){
       ArrayList<String> list =new ArrayList<String>();
       boolean x=DoesItExist(TableName,search, "name");
       if(x){
           String name,type,sector,supplier;
           int size,area,status,isle,carierno,colorno,orderno;
           
           Connection connection=null;
           String sql= String.format("SELECT * FROM %s WHERE  name=?",TableName);
           try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement ps = connection.prepareStatement(sql);
               ps.setString(1,search);
               ResultSet s1 = ps.executeQuery();
               if(s1.next()){
                   if(TableName.equals("dicut")){
                   list.add(s1.getString(1));
                   list.add(s1.getString(2));
                   list.add(s1.getString(3));
                   size=s1.getInt(4);list.add(Integer.toString(size));
                   area=s1.getInt(5);list.add(Integer.toString(area));
                   status=s1.getInt(6);list.add(Integer.toString(status));
                   list.add(s1.getString(9));
                   isle=s1.getInt(10);list.add(Integer.toString(isle));
                   carierno=s1.getInt(11);list.add(Integer.toString(carierno));
                   return list;
                   }
                   else if (TableName.equals("iplate")){
                   list.add(s1.getString(1));
                   list.add(s1.getString(2));
                   list.add(s1.getString(3));
                   size=s1.getInt(4);list.add(Integer.toString(size));
                   area=s1.getInt(5);list.add(Integer.toString(area));
                   status=s1.getInt(6);list.add(Integer.toString(status));
                   isle=s1.getInt(9);list.add(Integer.toString(isle));
                   carierno=s1.getInt(10);list.add(Integer.toString(carierno)); 
                   colorno=s1.getInt(11);list.add(Integer.toString(colorno)); 
                   return list;
                   }
                   else if (TableName.equals("iclasheh")){
                   list.add(s1.getString(1));
                   list.add(s1.getString(2));
                   list.add(s1.getString(3));
                   size=s1.getInt(4);list.add(Integer.toString(size));
                   area=s1.getInt(5);list.add(Integer.toString(area));
                   status=s1.getInt(6);list.add(Integer.toString(status));
                   isle=s1.getInt(9);list.add(Integer.toString(isle));
                   carierno=s1.getInt(10);list.add(Integer.toString(carierno));
                   orderno=s1.getInt(11);list.add(Integer.toString(orderno));
                   colorno=s1.getInt(12);list.add(Integer.toString(colorno)); 
                   return list;
                   }
               }
               else{return list;}
           } catch (SQLException ex) {
               Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       else{
       
       }
   return list;
   }
   
   public int Delete(String TableName,String Search){// 0: do not exist 1: deleted 2:error
      boolean x;
      int deleted=0;//is it deleted or not
      x = DoesItExist( TableName, Search,"name");
      System.out.println(x);
      if(x){//exixt in DB
          Connection connection;
          String sql= String.format("Delete FROM %s WHERE  name=?",TableName); 
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              PreparedStatement ps1 = connection.prepareStatement(sql);
              ps1.setString(1,Search);
              System.out.println("taher");
              boolean rs1 = ps1.execute();
              System.out.println("taher");
              if(!rs1) {deleted=1;System.out.println("deleted");return deleted;}//exist and deleted
              else     {deleted = 2;System.out.println("not deleted");return deleted;} // error
              } catch (SQLException ex) { 
              Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
             } 
          }
      else {//Doesnot exist in DB
          deleted = 0;System.out.println("3333333333333333");return deleted;
      }
       return deleted;
     
    }
    
 public int UpdateTool (String TableName,String Search,int status){// 0: do not exist 1: deleted 2:error
        boolean x= false;
        int updateed=0;//is it deleted or not
        x = DoesItExist( TableName, Search,"name");
        if(x){//exixt in DB
          Connection connection;
          String sql= String.format("UPDATE %s SET status=? WHERE name=? ",TableName); 
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              PreparedStatement ps1 = connection.prepareStatement(sql);  
              ps1.setInt(1 ,status);
              ps1.setString(2, Search);
              
              boolean rs1 = ps1.execute();
              if(!rs1) {updateed =1;}//exist and updated
              else     {updateed = 2;} // error
              } catch (SQLException ex) { 
              Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
             } 
          }
      else {//Doesnot exist in DB
          updateed = 0;
      }
       return updateed;
   }
 
     int getSectorno(String Sector){//for dicut and clasheh standered sizes
        int sectorno=0;
         if(Sector.equals("مطاعم")){sectorno=1;}
         else if(Sector.equals("الادوية")){sectorno=2;}
         else if(Sector.equals("بنوك")){sectorno=3;}
         else if(Sector.equals("تعليم")){sectorno=4;}
         else{sectorno=5;}
        return sectorno;
    }
    
    int getSectorno1(String Sector){//for plates standered sizes and dicut clicheh other sizes
        int sectorno=0;
         if(Sector.equals("مطاعم")){sectorno=1;}
         else if(Sector.equals("الادوية")){sectorno=2;}
         else if(Sector.equals("بنوك")){sectorno=1;}
         else if(Sector.equals("تعليم")){sectorno=2;}
         else{sectorno=3;}
        return sectorno;
    }

   public ArrayList<String> SearchTool ( String search){
       ArrayList<String> list =new ArrayList<String>();
               Vector data = new Vector();

        String TypeOfTool1="";
        String size1="";
        String JobOfTool1="";
        String supplier1="";
        String status1="";
        int status;
        int sectorno=0;
        int size=0;
        int flag=0;
        int isle=0;
        int carierNo =0;
        int ordernumber1=0;
        int colornumber1=0;
        int area=0;
         //see if search is empty there 
            char TypeOfTool=search.charAt(0);
            Connection connection;
            PreparedStatement ps,ps1,ps2;

            switch (TypeOfTool) {
                case 'D': case 'd':
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps = connection.prepareStatement("select * from dicut where name= ?");
                    ps.setString(1,search );
                    ResultSet rs = ps.executeQuery();
                   
                    if(rs.next())
                    {
                        TypeOfTool1=rs.getString(2);
                        JobOfTool1=rs.getString(3);
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        supplier1=rs.getString(9);
                        isle=rs.getInt(10);
                        carierNo =rs.getInt(11);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }
                        

                    }

                    else  list.add("notFound");
                }
                catch (HeadlessException | SQLException ex ) {
                    list.add("error");
                }       break;
                case 'P': case 'p':
                try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iplate where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        supplier1="لا يوجد";
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        area=rs1.getInt(5);
                        status=rs1.getInt(6);
                        isle=rs1.getInt(9);
                        carierNo =rs1.getInt(10);
                        colornumber1=rs1.getInt(11);
                        ps2 = connection.prepareStatement("select * from color where platename= ?");
                        ps2.setString(1,search );
                        ResultSet rs2 = ps2.executeQuery();
                        while(rs2.next()){data.addElement(rs2.getString(2));}
                        //this.Colors1.setListData(data);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            case 2:
                            size1="50×30";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }
                    }
                    else  list.add("not found");
                }
                catch (HeadlessException | SQLException ex ) {
                    list.add("error");
                }       break;
                case 'C': case 'c':
                try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iclasheh where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        supplier1="لا يوجد";
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        status=rs1.getInt(6);
                        area=rs1.getInt(5);
                        isle=rs1.getInt(9);
                        carierNo=rs1.getInt(10);
                        colornumber1=rs1.getInt(11);
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        switch (size) {
                            case 1:
                            size1="70×100";
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 2:
                            sectorno=getSectorno(JobOfTool1);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(JobOfTool1);
                            break;
                            default:
                            break;
                        }

                    }
                    else  list.add("not found");
                }
                catch (HeadlessException | SQLException ex ) {
                    list.add("error");
                }       break;
                default:
                list.add("not found");
                break;
            }
        list.add(TypeOfTool1);
         list.add(JobOfTool1);
          list.add(Integer.toString(size));
        list.add(status1);
          list.add(Integer.toString(area));
          list.add(Integer.toString(isle));
          list.add(Integer.toString(carierNo));
          list.add(Integer.toString(colornumber1));
          list.add(supplier1);
          list.add(Integer.toString(sectorno));



       
       return list;
    }
   
   
        public static void main(String[] args) {

    }

    
}
