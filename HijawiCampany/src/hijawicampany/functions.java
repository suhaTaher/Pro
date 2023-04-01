/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hijawicampany;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */


public class functions {

   public static boolean DoesItExist(String TableName, String search) {//type is the Table name
       boolean x= false;
       Connection connection=null;
       String sql= String.format("SELECT * FROM %s WHERE  name=?",TableName);
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
   
   public ArrayList<String> get (String TableName, String search){
       ArrayList<String> list =new ArrayList<String>();
       boolean x=DoesItExist(TableName,search);
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
      boolean x= false;
      int deleted=0;//is it deleted or not
      x = DoesItExist( TableName, Search);
      if(x){//exixt in DB
          Connection connection;
          String sql= String.format("Delete FROM %s WHERE  name=?",TableName); 
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              PreparedStatement ps1 = connection.prepareStatement(sql);  
              boolean rs1 = ps1.execute();
              if(!rs1) {deleted=1;return deleted;}//exist and deleted
              else     {deleted = 2;return deleted;} // error
              } catch (SQLException ex) { 
              Logger.getLogger(functions.class.getName()).log(Level.SEVERE, null, ex);
             } 
          }
      else {//Doesnot exist in DB
          deleted = 0;return deleted;
      }
       return deleted;
     
    }
    
   public int UpdateTool (String TableName,String Search){// 0: do not exist 1: deleted 2:error
        boolean x= false;
        int updateed=0;//is it deleted or not
        x = DoesItExist( TableName, Search);
        if(x){//exixt in DB
          Connection connection;
          String sql= String.format("UPDATE %s WHERE  status=?",TableName); 
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              PreparedStatement ps1 = connection.prepareStatement(sql);  
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
   
   
        public static void main(String[] args) {

    }

    
}
