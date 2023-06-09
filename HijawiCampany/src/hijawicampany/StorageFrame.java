/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hijawicampany;

import static hijawicampany.functions.DoesItExist;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS
 */
public class StorageFrame extends javax.swing.JFrame {

    /**
     * Creates new form StorageFrame
     */
    private final String username;
    private CardLayout cardLayout;
    int CNo;
    int PNo;
    int DNo;
    public functions f=new functions();

    public StorageFrame(String UserID) throws IOException {
        initComponents();
        cardLayout = (CardLayout) StorageCards.getLayout();
        setColor(this.ExpiredListOfTools);
        cardLayout.show(StorageCards, "card5");
        toolexpired();
        tabelcontent();
        this.username=UserID;
        this.jLabel2.setText(username);
        
        CNo=f.NoOfTool("iclasheh");
        PNo=f.NoOfTool("iplate");
        DNo=f.NoOfTool("dicut");
        this.NoOfClasheh.setText(Integer.toString(CNo));
        this.NoOfDicut.setText(Integer.toString(PNo));
        this.NoOfPlate.setText(Integer.toString(DNo));
        f.ToolTables("iplate",PlateTable);
        f.ToolTables("iclasheh",ClashehTable);
        f.ToolTables("dicut",DicutTable);
        
        Image icon;
        icon = new ImageIcon(this.getClass().getResource("/Images/cc.png")).getImage();
        this.setIconImage(icon);
        
        Timer timer = new Timer(0, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
             toolexpired();
            tabelcontent();
        }
});

timer.setDelay(10000);
timer.start();
    }
    
    
    /*public void ToolTables(String ToolName,JTable table){
             String[] columnNames = {"اسم الاداة", "القطاع","رقم المنطقة" ,"الممر", "الحجم"," رقم الحاملة","الملفات"};
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    DefaultTableModel model = new DefaultTableModel();
                     model.setColumnIdentifiers(columnNames);
                    table.setModel(model);
                    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    table.setFillsViewportHeight(true);
                    table.setRowHeight(40);
                    
                    String tname;
                    String ttype;
                     String sector;
                    String size="";
                    int size1;
                    int area=0;
                    int isle=0;
                    String supplier;
                    int cno=0;
                    String path="";
                    
                    String sql= String.format("SELECT * FROM %s",ToolName);
                    PreparedStatement ps1 = connection.prepareStatement(sql);//dicut
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next())
                    {
                        tname=rs1.getString(1);
                     // ttype=rs1.getString(2);
                      sector=rs1.getString(3);
                      size1=rs1.getInt(4);
                      area=rs1.getInt(5);
                      
                    if(ToolName.equals("dicut")){
                      isle=rs1.getInt(11);
                       cno=rs1.getInt(12);
                       path=rs1.getString(13);
                       if(size1==1)size="70*100";
                       else if(size1==2)size="30*50";
                       else size="غير ذلك";
                      model.addRow(new Object[]{tname, sector, area,isle,size,cno,path});
                       
                    }
                     if(ToolName.equals("iplate")|| ToolName.equals("iclasheh")){
                                               isle=rs1.getInt(9);
                       cno=rs1.getInt(10);
                       path=rs1.getString(13);
                       if(size1==1)size="70*100";
                       else if(size1==2)size="30*50";
                       else size="غير ذلك";
                      model.addRow(new Object[]{tname, sector, area,isle,size,cno,path});
                     }
                    }



                    
        } catch (SQLException ex) {
            Logger.getLogger(PlannerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
                    
    }*/
    
    
    
    
    
public void toolexpired(){
             String[] columnNames = {"اسم الاداة", "نوع الاداة", "القطاع","رقم المنطقة" ,"الممر", "الحجم"," رقم الحاملة","الملفات"};
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    DefaultTableModel model = new DefaultTableModel();
                     model.setColumnIdentifiers(columnNames);
                    jTable1.setModel(model);
                    jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    jTable1.setFillsViewportHeight(true);
                    jTable1.setRowHeight(40);
                    
                    String tname;
                    String ttype;
                     String sector;
                    String size="";
                    int size1;
                    int area=0;
                    int isle=0;
                    String supplier;
                    int cno=0;
                    String path="";
                    
                    PreparedStatement ps1 = connection.prepareStatement("SELECT * FROM dicut WHERE status=2");
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next())
                    {
                        tname=rs1.getString(1);
                      ttype=rs1.getString(2);
                      sector=rs1.getString(3);
                      size1=rs1.getInt(4);
                      area=rs1.getInt(5);
                      isle=rs1.getInt(11);
                       cno=rs1.getInt(12);
                       char x = tname.charAt(0);
                       if(x=='D'||x=='d'||x=='P'||x=='p'){
                       path=rs1.getString(13);
                       }
                       else if(x=='C'||x=='c'){path=rs1.getString(13);}
                       if(size1==1)size="70*100";
                       else if(size1==2)size="30*50";
                       else size="غير ذلك";
                      model.addRow(new Object[]{tname, ttype, sector, area,isle,size,cno,path});
                       
                    }
                    PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM iplate WHERE status=2 ");
                    ResultSet rs2 = ps2.executeQuery();
                    while(rs2.next())
                    {
                        tname=rs2.getString(1);
                      ttype=rs2.getString(2);
                      sector=rs2.getString(3);
                      size1=rs2.getInt(4);
                      area=rs2.getInt(5);
                      isle=rs2.getInt(9);
                       cno=rs2.getInt(10);
                       if(size1==1)size="70*100";
                       else if(size1==2)size="30*50";
                       else size="غير ذلك";
                      model.addRow(new Object[]{tname, ttype, sector, area,isle,size,cno});
                    }
                     PreparedStatement ps = connection.prepareStatement("SELECT * FROM iclasheh WHERE status=2 ");
                    ResultSet rs = ps.executeQuery();
                    while(rs.next())
                    {
                        tname=rs.getString(1);
                      ttype=rs.getString(2);
                      sector=rs.getString(3);
                      size1=rs.getInt(4);
                      area=rs.getInt(5);
                      isle=rs.getInt(9);
                       cno=rs.getInt(10);
                       if(size1==1)size="70*100";
                       else if(size1==2)size="30*50";
                       else size="غير ذلك";
                      model.addRow(new Object[]{tname, ttype, sector, area,isle,size,cno});
                    }


                    
        } catch (SQLException ex) {
            Logger.getLogger(PlannerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
                    
    }

        
     public void tabelcontent(){
             String[] columnNames = {"رقم الطلبية", "تاريخ الطلبية", "تاريخ التسليم", "اسم الاداة","الملفات"};
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    DefaultTableModel model = new DefaultTableModel();
                     model.setColumnIdentifiers(columnNames);
                    jTable2.setModel(model);
                    jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    jTable2.setFillsViewportHeight(true);
                    jTable2.setRowHeight(40);
                    String id="";
                    Date orderdate;
                     String Finishdate;
                    String toolname;
                    String fileUrl;
                   PreparedStatement ps1 = connection.prepareStatement("select * from orders where OrderStatus=0 ORDER BY orderDate DESC");//not ready 
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next())
                    {
                      id=rs1.getString(1);
                      orderdate=rs1.getDate(2);
                      Finishdate=rs1.getString(3);
                      toolname=rs1.getString(4);
                      fileUrl=rs1.getString(5);
                       model.addRow(new Object[]{id, orderdate, Finishdate, toolname,fileUrl});
                    }
               
        } catch (SQLException ex) {
            Logger.getLogger(PlannerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
              
    }
     
    void deleteTool(String TableName,String ToolName){
     int x = f.Delete(TableName, ToolName);
     if(TableName.equals("iplate")){
     
          Connection connection;
          String sql= String.format("Delete FROM color WHERE  platename=?"); 
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              PreparedStatement ps1 = connection.prepareStatement(sql);
              ps1.setString(1,ToolName);
              boolean rs1 = ps1.execute();
              if(!rs1) {}
              else{}
                 } catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );}
     }
     
     if (x==1){//deleted
         JOptionPane.showMessageDialog(this, "تم حذف" + ToolName +"بنجاح");
     }
     else if (x==0){// does not exist
         JOptionPane.showMessageDialog(this, "هذه الأداة غير موجودة");
     }
     else if (x==2){// error db
         JOptionPane.showMessageDialog(this, "لقد حدث خطأ");
     }
     else{
        JOptionPane.showMessageDialog(this, "لقد حدث خط1أ");
     }
     }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */


    void setColor(JPanel panel) {
       // panel.setBackground(new Color(20, 63, 111));
       panel.setBackground(new Color(0, 43, 91));
    }
    
    void resetColor(JPanel panel) {
       // panel.setBackground(new Color(0,43,91));
        panel.setBackground(new Color(20,63,111));
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
    
    String SChange(int status,String search,String Table,String OType){
        String sl="";
               String Status1 = f.returnvalue(Table, search, "name");
               status= Integer.valueOf(Status1);
            if(OType.equals("expired")){
               if(status==1){
               int upx=f.UpdateTool(Table, search, 2);
                  if(upx==1){
                     JOptionPane.showMessageDialog(this, " تمت العملية بنجاح");
                     sl="تالفة";}
                  else {JOptionPane.showMessageDialog(this, "لقد حدث خطأ");}
               
               }
               else if(status==2){JOptionPane.showMessageDialog(this, "لقد تم اتلافها من قبل");sl="تالفة";}
              else {JOptionPane.showMessageDialog(this, " الاداة غير متوفرة");sl="غير متوفرة";}
               return sl;
            }
            else if(OType.equals("return")){
               if(status==0){
               int upx=f.UpdateTool(Table, search, 1);
               if(upx==1){
               JOptionPane.showMessageDialog(this, " تمت العملية بنجاح");
               sl=" متوفرة";}
               else {JOptionPane.showMessageDialog(this, "لقد حدث خطأ");}
               }
               else if(status==2){JOptionPane.showMessageDialog(this, "لقد تم اتلاف الأداة");sl="تالفة";}
              else {JOptionPane.showMessageDialog(this, " الاداة موجودة");sl="متوفرة";}
               return sl;
            }
            
            else if(OType.equals("borrow")){
               if(status==1){ 
                int upx=f.UpdateTool(Table, search, 0);
                if(upx==1){
               JOptionPane.showMessageDialog(this, " تمت العملية بنجاح");
               sl="غير متوفرة";}
                else {JOptionPane.showMessageDialog(this, "لقد حدث خطأ");}
               }
               else if(status==2){JOptionPane.showMessageDialog(this, "لقد تم اتلاف الأداة");sl="تالفة";}
              else {JOptionPane.showMessageDialog(this, " الأداة تمت استعارتها ");sl="غير متوفرة";}
               return sl;
            }
            return sl;
    }
    
     boolean SearchForTool(String ToolType,String search, String rowname ,String callee){
         boolean c=false;
        Vector data = new Vector();
        String Path;
        this.Colors1.setListData(data);
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
        String Sectors="";
        String s =ToolType;
        Connection connection;
        PreparedStatement ps,ps2;
        try {
           String sql= String.format("Select * FROM %s WHERE  %s=?",ToolType,rowname);        
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps = connection.prepareStatement(sql);
                    ps.setString(1,search );
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        TypeOfTool1=rs.getString(2);
                        JobOfTool1=rs.getString(3);
                        if(JobOfTool1.equals("Restaurant")){Sectors="مطاعم";}
                        else if(JobOfTool1.equals("Medicine")){Sectors="الادوية";}
                        else if(JobOfTool1.equals("Others")){Sectors="اخرى";}
                        else if(JobOfTool1.equals("Education")){Sectors="تعليم";}
                        else if(JobOfTool1.equals("Banks")){Sectors="بنوك";}
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        Path=rs.getString(13);
                        
                        if(s.equals("dicut")){
                        supplier1=rs.getString(10);
                        isle=rs.getInt(11);
                        carierNo =rs.getInt(12);
                        }
                        
                        if(s.equals("iplate")|| s.equals("iclasheh")){
                        isle=rs.getInt(10);
                        carierNo =rs.getInt(11);
                        colornumber1=rs.getInt(12);
                        }
                        
                        if(s.equals("iplate") && callee.equals("s2")){
                         ps2 = connection.prepareStatement("select * from color where platename= ?");
                         ps2.setString(1,search );
                         ResultSet rs2 = ps2.executeQuery();
                         while(rs2.next()){data.addElement(rs2.getString(2));}
                         this.Colors1.setListData(data);
                        }
                        
                        if (callee.equals("S1") && s.equals("iplate")){
                         ps2 = connection.prepareStatement("select * from color where platename= ?");
                         ps2.setString(1,search );
                         ResultSet rs2 = ps2.executeQuery();
                         while(rs2.next()){
                             if(rs2.getString(2).equals("Black")){this.jCheckBox1.setSelected(true);}
                             if(rs2.getString(2).equals("Red")){this.jCheckBox4.setSelected(true);}
                             if(rs2.getString(2).equals("Yellow")){this.jCheckBox5.setSelected(true);}
                             if(rs2.getString(2).equals("Blue")){this.jCheckBox2.setSelected(true);}
                         }
                        }
                      
                        if(status==1)status1="متوفر";
                        if(status==0) status1="غير متوفر";
                        if(status==2) status1="تالفة";
                        if(s.equals("dicut")|| s.equals("iclasheh")){
                        switch (size) {  
                            case 1:
                            size1="70*100";
                            sectorno=getSectorno(Sectors);
                            break;
                            case 2:
                            size1="50*30";
                            sectorno=getSectorno(Sectors);
                            break;
                            case 3:
                            size1="غير ذلك";
                            sectorno=getSectorno1(Sectors);
                            break;
                            default:
                            break;
                        }
                        }
                        else if (s.equals("iplate")){
                            switch (size) {  
                            case 1:
                            size1="70*100";
                            sectorno=getSectorno1(Sectors);
                            break;
                            case 2:
                            size1="50*30";
                            sectorno=getSectorno1(Sectors);
                            break;
                            default:
                            break;
                        
                        }
                        }
                        try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    this.Tool1.setSelectedItem((TypeOfTool1));
                    c=true;//found

                    }

                    else  {c=false;}
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } 

        if (callee.equals("S1") && rowname.equals("name")){
            this.sectorno1.setText(Integer.toString(sectorno));
            this.Tool1.setSelectedItem(TypeOfTool1);
            this.suplier.setSelectedItem(supplier1);
            this.Size2.setSelectedItem(size1);
            this.Sector.setSelectedItem(Sectors);
            this.Status1.setText(status1);
            this.Area3.setText(Integer.toString(area));
            this.aisle3.setText(Integer.toString(isle));
            this.CarierNo2.setText(Integer.toString(carierNo));
            this.colorNo3.setText(Integer.toString(colornumber1));
        }
        if (callee.equals("s2") && rowname.equals("name")){
            this.Tool2.setText(TypeOfTool1);
            this.Supplier1.setText(supplier1);
            this.Size1.setText(size1);
            this.sector1.setText(Sectors);
            this.Sectorno.setText(Integer.toString(sectorno));
            this.Status.setText(status1);
            this.area.setText(Integer.toString(area));
            this.isle.setText(Integer.toString(isle));
            this.CarierNo1.setText(Integer.toString(carierNo));
           this.colorNo1.setText(Integer.toString(colornumber1));
        }
        
        return c;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SidePannel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        ExpiredListOfTools = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        LogOut = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        AddOrder_Change = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        OP_Orders = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        ToolList = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        Order_List = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        StorageCards = new javax.swing.JPanel();
        names = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        toolsTap = new javax.swing.JTabbedPane();
        clasheh = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        NoOfClasheh = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        ClashehTable = new javax.swing.JTable();
        plate = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        NoOfPlate = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PlateTable = new javax.swing.JTable();
        dicut = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        NoOfDicut = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        DicutTable = new javax.swing.JTable();
        ExpiredToolsList = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AddChangeTool = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        searchKey2 = new javax.swing.JTextField();
        SearchBTN = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        Tool1 = new javax.swing.JComboBox<>();
        jPanel18 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        suplier = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        Size2 = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        Area3 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        aisle3 = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        CarierNo2 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        Sector = new javax.swing.JComboBox<>();
        sectorno1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        colorNo3 = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        Status1 = new javax.swing.JLabel();
        OK = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        Attach1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        FileUrl = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        OPOnTools = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        searchKey12 = new javax.swing.JTextField();
        search1 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        Tool2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        Supplier1 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        Size1 = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        area = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        isle = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        CarierNo1 = new javax.swing.JTextField();
        jPanel27 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        sector1 = new javax.swing.JTextField();
        Sectorno = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        colorNo1 = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Colors1 = new javax.swing.JList<>();
        Status = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        OPTool = new javax.swing.JComboBox<>();
        Ok = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        OrderList1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel39 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Storage");
        setResizable(false);

        jPanel1.setLayout(null);

        SidePannel.setBackground(new java.awt.Color(20, 63, 111));

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("اسم المستخدم");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );

        ExpiredListOfTools.setBackground(new java.awt.Color(20, 63, 111));
        ExpiredListOfTools.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExpiredListOfToolsMouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("لائحة الأدوات التالفة");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("__________________________");

        javax.swing.GroupLayout ExpiredListOfToolsLayout = new javax.swing.GroupLayout(ExpiredListOfTools);
        ExpiredListOfTools.setLayout(ExpiredListOfToolsLayout);
        ExpiredListOfToolsLayout.setHorizontalGroup(
            ExpiredListOfToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpiredListOfToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExpiredListOfToolsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        ExpiredListOfToolsLayout.setVerticalGroup(
            ExpiredListOfToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpiredListOfToolsLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("__________________________");

        LogOut.setPreferredSize(new java.awt.Dimension(183, 55));
        LogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogOutMouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(204, 0, 0));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("تسجيل الخروج");

        javax.swing.GroupLayout LogOutLayout = new javax.swing.GroupLayout(LogOut);
        LogOut.setLayout(LogOutLayout);
        LogOutLayout.setHorizontalGroup(
            LogOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LogOutLayout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jLabel42)
                .addGap(57, 57, 57))
        );
        LogOutLayout.setVerticalGroup(
            LogOutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogOutLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(20, 63, 111));

        AddOrder_Change.setBackground(new java.awt.Color(20, 63, 111));
        AddOrder_Change.setPreferredSize(new java.awt.Dimension(190, 35));
        AddOrder_Change.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddOrder_ChangeMouseClicked(evt);
            }
        });
        AddOrder_Change.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("اضافة و البحث عن أداة");
        AddOrder_Change.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 150, 35));

        OP_Orders.setBackground(new java.awt.Color(20, 63, 111));
        OP_Orders.setPreferredSize(new java.awt.Dimension(190, 35));
        OP_Orders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OP_OrdersMouseClicked(evt);
            }
        });
        OP_Orders.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("عمليات على الأدوات");
        jLabel1.setPreferredSize(new java.awt.Dimension(70, 35));
        OP_Orders.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(44, 0, 130, 35));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("الأدوات");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("__________________________");

        ToolList.setBackground(new java.awt.Color(20, 63, 111));
        ToolList.setPreferredSize(new java.awt.Dimension(190, 35));
        ToolList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ToolListMouseClicked(evt);
            }
        });
        ToolList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("لائحة الأدوات");
        ToolList.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 90, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OP_Orders, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(AddOrder_Change, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(ToolList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ToolList, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(OP_Orders, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AddOrder_Change, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        Order_List.setBackground(new java.awt.Color(20, 63, 111));
        Order_List.setPreferredSize(new java.awt.Dimension(190, 35));
        Order_List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Order_ListMouseClicked(evt);
            }
        });
        Order_List.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("لائحة الطلبيات");
        jLabel4.setPreferredSize(new java.awt.Dimension(82, 35));
        Order_List.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, 100, -1));

        javax.swing.GroupLayout SidePannelLayout = new javax.swing.GroupLayout(SidePannel);
        SidePannel.setLayout(SidePannelLayout);
        SidePannelLayout.setHorizontalGroup(
            SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidePannelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(SidePannelLayout.createSequentialGroup()
                .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ExpiredListOfTools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(SidePannelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SidePannelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(SidePannelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Order_List, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        SidePannelLayout.setVerticalGroup(
            SidePannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SidePannelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(ExpiredListOfTools, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Order_List, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                .addComponent(LogOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        jPanel1.add(SidePannel);
        SidePannel.setBounds(790, 0, 210, 750);

        StorageCards.setBackground(new java.awt.Color(255, 255, 255));
        StorageCards.setLayout(new java.awt.CardLayout());

        names.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        clasheh.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(250, 250, 250));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 43, 91));
        jLabel13.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfClasheh, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfClasheh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        ClashehTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(ClashehTable);

        javax.swing.GroupLayout clashehLayout = new javax.swing.GroupLayout(clasheh);
        clasheh.setLayout(clashehLayout);
        clashehLayout.setHorizontalGroup(
            clashehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, clashehLayout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(clashehLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        clashehLayout.setVerticalGroup(
            clashehLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clashehLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Clasheh   ", clasheh);

        plate.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(250, 250, 250));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 43, 91));
        jLabel7.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfPlate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfPlate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        PlateTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(PlateTable);

        javax.swing.GroupLayout plateLayout = new javax.swing.GroupLayout(plate);
        plate.setLayout(plateLayout);
        plateLayout.setHorizontalGroup(
            plateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, plateLayout.createSequentialGroup()
                .addContainerGap(430, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(plateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        plateLayout.setVerticalGroup(
            plateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(plateLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Plate   ", plate);

        dicut.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(250, 250, 250));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 43, 91));
        jLabel6.setText("عدد الأدوات");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(NoOfDicut, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(NoOfDicut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        DicutTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(DicutTable);

        javax.swing.GroupLayout dicutLayout = new javax.swing.GroupLayout(dicut);
        dicut.setLayout(dicutLayout);
        dicutLayout.setHorizontalGroup(
            dicutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dicutLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
            .addGroup(dicutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                .addContainerGap())
        );
        dicutLayout.setVerticalGroup(
            dicutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dicutLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                .addContainerGap())
        );

        toolsTap.addTab("   Dicut   ", dicut);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(toolsTap, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(toolsTap, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout namesLayout = new javax.swing.GroupLayout(names);
        names.setLayout(namesLayout);
        namesLayout.setHorizontalGroup(
            namesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        namesLayout.setVerticalGroup(
            namesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, namesLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        StorageCards.add(names, "card6");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 43, 91));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("لائحة الأدوات تالفة");

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "null", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setEnabled(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(573, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addGap(68, 68, 68))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel38)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                .addGap(107, 107, 107))
        );

        javax.swing.GroupLayout ExpiredToolsListLayout = new javax.swing.GroupLayout(ExpiredToolsList);
        ExpiredToolsList.setLayout(ExpiredToolsListLayout);
        ExpiredToolsListLayout.setHorizontalGroup(
            ExpiredToolsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ExpiredToolsListLayout.setVerticalGroup(
            ExpiredToolsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        StorageCards.add(ExpiredToolsList, "card5");

        AddChangeTool.setBackground(new java.awt.Color(255, 255, 255));
        AddChangeTool.setPreferredSize(new java.awt.Dimension(790, 750));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(250, 250, 250));

        jLabel27.setForeground(new java.awt.Color(0, 43, 91));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("اسم الأداة");

        SearchBTN.setBackground(new java.awt.Color(0, 43, 91));
        SearchBTN.setForeground(new java.awt.Color(51, 51, 51));
        SearchBTN.setPreferredSize(new java.awt.Dimension(80, 42));
        SearchBTN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchBTNMouseClicked(evt);
            }
        });

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(255, 255, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("بحث");

        javax.swing.GroupLayout SearchBTNLayout = new javax.swing.GroupLayout(SearchBTN);
        SearchBTN.setLayout(SearchBTNLayout);
        SearchBTNLayout.setHorizontalGroup(
            SearchBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SearchBTNLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        SearchBTNLayout.setVerticalGroup(
            SearchBTNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchKey2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(SearchBTN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchKey2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setForeground(new java.awt.Color(0, 43, 91));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("نوع الأداة");
        jPanel17.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Tool1.setForeground(new java.awt.Color(0, 43, 91));
        Tool1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dicut", "IClasheh", "Plate" }));
        Tool1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tool1ActionPerformed(evt);
            }
        });
        jPanel17.add(Tool1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setForeground(new java.awt.Color(0, 43, 91));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("المورد ");
        jPanel18.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        suplier.setForeground(new java.awt.Color(0, 43, 91));
        suplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nablus", "Ramallah" }));
        jPanel18.add(suplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setForeground(new java.awt.Color(0, 43, 91));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("الحجم");
        jPanel23.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Size2.setForeground(new java.awt.Color(0, 43, 91));
        Size2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "70*100", "50*30", "غير ذلك" }));
        jPanel23.add(Size2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel31.setBackground(new java.awt.Color(250, 250, 250));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 43, 91));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("موقع التخرين");

        jPanel32.setBackground(new java.awt.Color(250, 250, 250));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setForeground(new java.awt.Color(0, 43, 91));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("المنطقة");
        jPanel32.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Area3.setEditable(false);
        Area3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.add(Area3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel33.setBackground(new java.awt.Color(250, 250, 250));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setForeground(new java.awt.Color(0, 43, 91));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("الممر");
        jPanel33.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        aisle3.setEditable(false);
        aisle3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.add(aisle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel34.setBackground(new java.awt.Color(250, 250, 250));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setBackground(new java.awt.Color(250, 250, 250));
        jLabel50.setForeground(new java.awt.Color(0, 43, 91));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("الحاملة");
        jPanel34.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        CarierNo2.setEditable(false);
        CarierNo2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.add(CarierNo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel35.setBackground(new java.awt.Color(250, 250, 250));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setForeground(new java.awt.Color(0, 43, 91));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("القطاع");
        jPanel35.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Sector.setForeground(new java.awt.Color(0, 43, 91));
        Sector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "مطاعم", "الادوية", "بنوك", "تعليم", "اخرى" }));
        jPanel35.add(Sector, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 42));

        sectorno1.setEditable(false);
        sectorno1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.add(sectorno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 42));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 43, 91));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("الألوان");

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setForeground(new java.awt.Color(0, 43, 91));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("عدد الألوان");
        jPanel36.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 60, 40));
        jPanel36.add(colorNo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setForeground(new java.awt.Color(0, 43, 91));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("الألوان");
        jPanel37.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 75, 40));

        jCheckBox1.setBackground(new java.awt.Color(250, 250, 250));
        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox1.setText("أسود");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });
        jPanel37.add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, -1, -1));

        jCheckBox2.setBackground(new java.awt.Color(250, 250, 250));
        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(0, 43, 91));
        jCheckBox2.setText("أزرق");
        jPanel37.add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jCheckBox4.setBackground(new java.awt.Color(250, 250, 250));
        jCheckBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(204, 0, 0));
        jCheckBox4.setText("أحمر");
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });
        jPanel37.add(jCheckBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jCheckBox5.setBackground(new java.awt.Color(250, 250, 250));
        jCheckBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(204, 204, 0));
        jCheckBox5.setText("أصفر");
        jPanel37.add(jCheckBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        Status1.setForeground(new java.awt.Color(204, 0, 0));
        Status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Status1.setText("Status");

        OK.setBackground(new java.awt.Color(255, 255, 255));
        OK.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 43, 91), 1, true));
        OK.setForeground(new java.awt.Color(51, 51, 51));
        OK.setPreferredSize(new java.awt.Dimension(147, 42));
        OK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OKMouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 43, 91));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("اضافة ");

        javax.swing.GroupLayout OKLayout = new javax.swing.GroupLayout(OK);
        OK.setLayout(OKLayout);
        OKLayout.setHorizontalGroup(
            OKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OKLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        OKLayout.setVerticalGroup(
            OKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        Attach1.setBackground(new java.awt.Color(0, 43, 91));
        Attach1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Attach1MouseClicked(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("ارفاق ملف");

        javax.swing.GroupLayout Attach1Layout = new javax.swing.GroupLayout(Attach1);
        Attach1.setLayout(Attach1Layout);
        Attach1Layout.setHorizontalGroup(
            Attach1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Attach1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(30, 30, 30))
        );
        Attach1Layout.setVerticalGroup(
            Attach1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(OK, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(FileUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Attach1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Status1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FileUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Status1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(OK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Attach1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 43, 91));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setText("اضافة والبحث عن أداه");

        javax.swing.GroupLayout AddChangeToolLayout = new javax.swing.GroupLayout(AddChangeTool);
        AddChangeTool.setLayout(AddChangeToolLayout);
        AddChangeToolLayout.setHorizontalGroup(
            AddChangeToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddChangeToolLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(69, 69, 69))
            .addGroup(AddChangeToolLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        AddChangeToolLayout.setVerticalGroup(
            AddChangeToolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddChangeToolLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel56)
                .addGap(29, 29, 29)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        StorageCards.add(AddChangeTool, "card4");

        OPOnTools.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(250, 250, 250));

        jLabel25.setForeground(new java.awt.Color(0, 43, 91));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("اسم الأداة");

        search1.setBackground(new java.awt.Color(0, 43, 91));
        search1.setForeground(new java.awt.Color(51, 51, 51));
        search1.setPreferredSize(new java.awt.Dimension(147, 42));
        search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                search1MouseEntered(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("بحث");

        javax.swing.GroupLayout search1Layout = new javax.swing.GroupLayout(search1);
        search1.setLayout(search1Layout);
        search1Layout.setHorizontalGroup(
            search1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );
        search1Layout.setVerticalGroup(
            search1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchKey12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchKey12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 634, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setForeground(new java.awt.Color(0, 43, 91));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("نوع الأداة");
        jPanel8.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Tool2.setEditable(false);
        Tool2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel8.add(Tool2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setForeground(new java.awt.Color(0, 43, 91));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("المورد ");
        jPanel9.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Supplier1.setEditable(false);
        Supplier1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel9.add(Supplier1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setForeground(new java.awt.Color(0, 43, 91));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("الحجم");
        jPanel21.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        Size1.setEditable(false);
        Size1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel21.add(Size1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel6.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, -1, -1));

        jPanel22.setBackground(new java.awt.Color(250, 250, 250));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 43, 91));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("موقع التخرين");

        jPanel24.setBackground(new java.awt.Color(250, 250, 250));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel31.setForeground(new java.awt.Color(0, 43, 91));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("المنطقة");
        jPanel24.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        area.setEditable(false);
        area.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.add(area, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel25.setBackground(new java.awt.Color(250, 250, 250));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setForeground(new java.awt.Color(0, 43, 91));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("الممر");
        jPanel25.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        isle.setEditable(false);
        isle.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.add(isle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel26.setBackground(new java.awt.Color(250, 250, 250));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setBackground(new java.awt.Color(250, 250, 250));
        jLabel33.setForeground(new java.awt.Color(0, 43, 91));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("الحاملة");
        jPanel26.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        CarierNo1.setEditable(false);
        CarierNo1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.add(CarierNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel27.setBackground(new java.awt.Color(250, 250, 250));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setForeground(new java.awt.Color(0, 43, 91));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("القطاع");
        jPanel27.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        sector1.setEditable(false);
        sector1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.add(sector1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 42));

        Sectorno.setEditable(false);
        Sectorno.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.add(Sectorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 40, 42));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 43, 91));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("الألوان");
        jPanel6.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, 90, 32));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setForeground(new java.awt.Color(0, 43, 91));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("عدد الألوان");
        jPanel28.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 60, 40));

        colorNo1.setEditable(false);
        colorNo1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel28.add(colorNo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 42));

        jPanel6.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 540, 280, -1));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setForeground(new java.awt.Color(0, 43, 91));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("الألوان");
        jPanel29.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        jScrollPane1.setViewportView(Colors1);

        jPanel29.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 100));

        jPanel6.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 260, -1));

        Status.setForeground(new java.awt.Color(204, 0, 0));
        Status.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Status.setText("Status");
        jPanel6.add(Status, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 151, 186, 39));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setForeground(new java.awt.Color(0, 43, 91));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("العملية");
        jPanel10.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 75, 40));

        OPTool.setForeground(new java.awt.Color(0, 43, 91));
        OPTool.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "استعارة", "ارجاع", "حذف", "تالفة" }));
        OPTool.setPreferredSize(new java.awt.Dimension(190, 42));
        jPanel10.add(OPTool, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, -1, -1));

        Ok.setBackground(new java.awt.Color(255, 255, 255));
        Ok.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 43, 91), 1, true));
        Ok.setForeground(new java.awt.Color(51, 51, 51));
        Ok.setPreferredSize(new java.awt.Dimension(190, 42));
        Ok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OkMouseClicked(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(0, 43, 91));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("موافق");

        javax.swing.GroupLayout OkLayout = new javax.swing.GroupLayout(Ok);
        Ok.setLayout(OkLayout);
        OkLayout.setHorizontalGroup(
            OkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OkLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );
        OkLayout.setVerticalGroup(
            OkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel6.add(Ok, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 610, 200, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 43, 91));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("عمليات على الأدوات");

        javax.swing.GroupLayout OPOnToolsLayout = new javax.swing.GroupLayout(OPOnTools);
        OPOnTools.setLayout(OPOnToolsLayout);
        OPOnToolsLayout.setHorizontalGroup(
            OPOnToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OPOnToolsLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(OPOnToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 702, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        OPOnToolsLayout.setVerticalGroup(
            OPOnToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OPOnToolsLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel59)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 659, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        StorageCards.add(OPOnTools, "card3");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "null", "Title 2", "Title 3", "Title 4"
            }
        )
        {
            public boolean isCellEditable (int row ,int column){
                return false;
            }

        }
    );
    jTable2.setEnabled(false);
    jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            jTable2MouseClicked(evt);
        }
    });
    jScrollPane3.setViewportView(jTable2);

    jLabel39.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
    jLabel39.setForeground(new java.awt.Color(0, 43, 91));
    jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel39.setText("لائحة الطلبيات");

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
            .addContainerGap(589, Short.MAX_VALUE)
            .addComponent(jLabel39)
            .addGap(84, 84, 84))
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(79, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE)))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addGap(49, 49, 49)
            .addComponent(jLabel39)
            .addContainerGap(676, Short.MAX_VALUE))
        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE)))
    );

    javax.swing.GroupLayout OrderList1Layout = new javax.swing.GroupLayout(OrderList1);
    OrderList1.setLayout(OrderList1Layout);
    OrderList1Layout.setHorizontalGroup(
        OrderList1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    OrderList1Layout.setVerticalGroup(
        OrderList1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    StorageCards.add(OrderList1, "card2");

    jPanel1.add(StorageCards);
    StorageCards.setBounds(0, 0, 790, 750);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ExpiredListOfToolsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpiredListOfToolsMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder_Change);
        setColor(this.ExpiredListOfTools);
        resetColor(this.OP_Orders);
        resetColor(this.Order_List);
        resetColor(this.ToolList);
        cardLayout.show(StorageCards, "card5");
    }//GEN-LAST:event_ExpiredListOfToolsMouseClicked

    private void LogOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogOutMouseClicked
        // TODO add your handling code here:
        LogIn login=new LogIn ();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_LogOutMouseClicked

    private void AddOrder_ChangeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddOrder_ChangeMouseClicked
        // TODO add your handling code here:
        setColor(this.AddOrder_Change);
        resetColor(this.ExpiredListOfTools);
        resetColor(this.OP_Orders);
        resetColor(this.Order_List);
        resetColor(this.ToolList);
        cardLayout.show(StorageCards, "card4");
    }//GEN-LAST:event_AddOrder_ChangeMouseClicked

    private void OP_OrdersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OP_OrdersMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder_Change);
        resetColor(this.ExpiredListOfTools);
        setColor(this.OP_Orders);
        resetColor(this.Order_List);
        resetColor(this.ToolList);
        cardLayout.show(StorageCards, "card3");
    }//GEN-LAST:event_OP_OrdersMouseClicked

    private void Order_ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Order_ListMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder_Change);
        resetColor(this.ExpiredListOfTools);
        resetColor(this.OP_Orders);
        setColor(this.Order_List);
        resetColor(this.ToolList);
        cardLayout.show(StorageCards, "card2");
    }//GEN-LAST:event_Order_ListMouseClicked

    private void OKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OKMouseClicked
        // TODO add your handling code here:
               String FolderPath;
        ArrayList<String> color=new ArrayList<String>();  
        int no=0;
        //String otype=(String) this.OP.getSelectedItem();
        String path=this.FileUrl.getText();
        if(path.isEmpty()){JOptionPane.showMessageDialog(this, "اختر مجلد للأداه");}
        else{
        //if(otype=="اضافة"){
           StringBuilder Jname = new StringBuilder();
        if(this.jCheckBox1.isSelected()){color.add("Black");no++;}
        if(this.jCheckBox2.isSelected()){color.add("Blue");no++;}
        if(this.jCheckBox4.isSelected()){color.add("Yellow");no++;}
        if(this.jCheckBox5.isSelected()){color.add("Red");no++;}
        String tooltype=(String) this.Tool1.getSelectedItem();
        String supplier=(String) this.suplier.getSelectedItem();
        String size=(String) this.Size2.getSelectedItem();
        String sector=(String) this.Sector.getSelectedItem();
        StringBuilder toolname = new StringBuilder();
        Connection connection;
        long millis=System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);  
        PreparedStatement ps,ps1,ps2;
        int size1=1;
        int isle=0;
        char s;
        String Sector2="";
        char jobOfTool='R';
        int cNo=0;
        int area=0;
             if(size=="70*100")size1=1;
             else if(size=="50*30")size1=2; 
             else size1=3; 
             
             if("Ramallah".equals(supplier))s='R';
             else s='N';
              
             if(null!=sector)switch (sector) {
            case "مطاعم":
                jobOfTool='R';
                Sector2="Restaurant";
                break;
            case "الادوية":
                jobOfTool='M';
                Sector2="Medicine";
                break;
            case "اخرى":
                jobOfTool='O';
                Sector2="Others";
                break;
            case "تعليم":
                jobOfTool='E';
                Sector2="Education";
                break;
            case "بنوك":
                jobOfTool='B';
                Sector2="Banks";
                break;
            default:
                break;
        }
         
        
                if("Dicut"==tooltype ){
           try { 
               if(size1==1)isle=1;
               else if(size1==2)isle=2;
               else if(size1==3 && jobOfTool=='R' ||jobOfTool=='M' )isle=3;
               else isle=4;
               
               area=1;
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
              ps1 = connection.prepareStatement("SELECT MAX(carierNo) AS number FROM dicut WHERE   isle=?");
               ps1.setInt(1,isle);
               ResultSet s1 = ps1.executeQuery();
                if(s1.next()){
                     cNo=s1.getInt(1)+1;
                }
                ps1 = connection.prepareStatement("SELECT * FROM emptycarier  WHERE isle=? AND tooltype='Dicut' ORDER BY carierNo ASC LIMIT 1");
               ps1.setInt(1,isle);
               ResultSet s5 = ps1.executeQuery();
                if(s5.next()){
                      ps = connection.prepareStatement("INSERT INTO dicut(name,type,sector,size,area,status,DateOfAttachment,supplier,isle,carierNo,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
             toolname.append('D');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);//sector
             toolname.append(s5.getInt(4));//carier number
             toolname.append(s);//supplier
             
             Jname.append('D');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(s);//supplier
             

             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setString(8,supplier);
             ps.setInt(9,isle);
             ps.setInt(10,s5.getInt(4));
             ps.setString(11,path);
             ps.setString(12,Jname.toString());
             boolean rs = ps.execute();
             if(!rs){
                 JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString());
                 this.FileUrl.setText("");
                 ps = connection.prepareStatement("DELETE FROM emptycarier WHERE isle=? AND carierNo=?");
                 ps.setInt(1,s5.getInt(3));
                 ps.setInt(2,s5.getInt(4));
                 ps.execute();
             }
             else  JOptionPane.showMessageDialog(this, "Erorr");  
                }
                else{
             ps = connection.prepareStatement("INSERT INTO dicut(name,type,sector,size,area,status,DateOfAttachment,supplier,isle,carierNo,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
             toolname.append('D');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);//sector
             toolname.append(cNo);//carier number
             toolname.append(s);//supplier
             
             Jname.append('D');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(s);//supplier
             

             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setString(8,supplier);
             ps.setInt(9,isle);
             ps.setInt(10,cNo);
             ps.setString(11,path);
             ps.setString(12,Jname.toString());

             boolean rs = ps.execute();
             if(!rs){
                 JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString())
                         ;this.FileUrl.setText("");
                              
             }
             else  JOptionPane.showMessageDialog(this, "Erorr");  
                }
             } catch (HeadlessException | SQLException ex ) {
             JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
             }  
                }
              else if("IClasheh"==tooltype && !this.colorNo3.getText().isEmpty()  ){
               try { 
               int colorno=Integer.parseInt(this.colorNo3.getText());
               if(size1==1)isle=1;
               else if(size1==2)isle=2;
               else if(size1==3 && jobOfTool=='R' ||jobOfTool=='M' )isle=3;
               else isle=4;
               
               area=3;
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               ps1 = connection.prepareStatement("SELECT MAX(carierNo) AS number FROM iclasheh  WHERE   isle=?");
               ps1.setInt(1,isle);
               ResultSet s1 = ps1.executeQuery();
               if(s1.next()){
                     cNo=s1.getInt(1)+1;
                }
               PreparedStatement p = connection.prepareStatement("SELECT * FROM emptycarier  WHERE isle=? AND tooltype='IClasheh' ORDER BY carierNo ASC LIMIT 1");
               p.setInt(1,isle);
               ResultSet s5 = p.executeQuery();
                if(s5.next()){
             ps = connection.prepareStatement("INSERT INTO iclasheh(name,type,sector,size,area,status,DateOfAttachment,isle,carierNo,colornumber,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
             toolname.append('C');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);
             toolname.append(s5.getInt(4));//????????????????????????????car
             toolname.append(colorno);
             
             Jname.append('C');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(colorno);

             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setInt(8,isle);
             ps.setInt(9,s5.getInt(4));//?????????????????????????????????cariire
             ps.setInt(10,colorno);
             ps.setString(11,path);
             ps.setString(12, Jname.toString());
             boolean rs = ps.execute();
             if(!rs){JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString());
             this.FileUrl.setText("");
              ps1 = connection.prepareStatement("DELETE FROM emptycarier WHERE isle=? AND carierNo=?");
              ps1.setInt(1,s5.getInt(3));
              ps1.setInt(2,s5.getInt(4));
              ps1.execute();}
             else { JOptionPane.showMessageDialog(this, "ادخل الالوان");  }  
                
                }   
                 else{
             ps = connection.prepareStatement("INSERT INTO iclasheh(name,type,sector,size,area,status,DateOfAttachment,isle,carierNo,colornumber,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
             toolname.append('C');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);
             toolname.append(cNo);//????????????????????????????car
             toolname.append(colorno);
             
             Jname.append('C');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(colorno);

             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setInt(8,isle);
             ps.setInt(9,cNo);//?????????????????????????????????cariire
             ps.setInt(10,colorno);
             ps.setString(11,path);
             ps.setString(12, Jname.toString());
             boolean rs = ps.execute();
             if(!rs){
              JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString());
              this.FileUrl.setText("");
             
             }
             else { JOptionPane.showMessageDialog(this, "ادخل الالوان");  }  
                
                }   
             } catch (HeadlessException | SQLException ex ) {
             JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
             }    
             }  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////              
               else if("Plate"==tooltype && no!=0  ){
                   int flage = 0;//if size others then equals 1 
               try { 
               if(size1==1 && jobOfTool=='R')isle=1;
               else if(size1==1 && jobOfTool=='M')isle=1;
               
               else if(size1==1 && jobOfTool=='B')isle=2;
               else if(size1==1 )isle=2;
               
               else if(size1==2 && jobOfTool=='R')isle=3;
               else if(size1==2 && jobOfTool=='M')isle=3;
               else if(size1==2 && jobOfTool=='B')isle=4;
               else if(size1==2 && jobOfTool=='E')isle=4;
               else if(size1==2)isle=4;
               else if (size1==3){flage=1;}
               if(flage ==1){JOptionPane.showMessageDialog(this, "اختر حجم صحيح ");}
               else{
               area=2;
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               ps1 = connection.prepareStatement("SELECT MAX(carierNo) AS number FROM iplate  WHERE   isle=?");
               ps1.setInt(1,isle);
               ResultSet s1 = ps1.executeQuery();
               if(s1.next()){
                     cNo=s1.getInt(1)+1;
                }
            
                      PreparedStatement p = connection.prepareStatement("SELECT * FROM emptycarier  WHERE isle=? AND tooltype='IPlate' ORDER BY carierNo ASC LIMIT 1");
               p.setInt(1,isle);
               ResultSet s5 = p.executeQuery();
                if(s5.next()){
                
                   
                String txt = "";
                txt = Size2.getSelectedItem().toString();
              /*  if(txt.equals("غير ذلك")){
                   JOptionPane.showMessageDialog(this, "ادخل الالوان"); 
                }
                else{*/
             toolname.append('P');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);
             toolname.append(s5.getInt(4));//????????????????????????????car
             toolname.append(no);//color
             
             
             
             Jname.append('P');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(no);

             ps = connection.prepareStatement("INSERT INTO iplate(name,type,sector,size,area,status,DateOfAttachment,isle,carierNo,colornumber,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
           
             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setInt(8,isle);
             ps.setInt(9,s5.getInt(4));//?????????????????????????????????cariire
             ps.setInt(10,no);
             ps.setString(11,path);
             ps.setString(12, Jname.toString());
             boolean rs = ps.execute();
             if(!rs){JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString());
             this.FileUrl.setText("");
             ps1 = connection.prepareStatement("DELETE FROM emptycarier WHERE isle=? AND carierNo=?");
              ps1.setInt(1,s5.getInt(3));
              ps1.setInt(2,s5.getInt(4));
              ps1.execute();
             }
                
             else  JOptionPane.showMessageDialog(this, "اختر الالوان"); 
               // }
               }
                else{
                
                      
                
             toolname.append('P');//type
             toolname.append(size1);//size
             toolname.append(jobOfTool);
             toolname.append(cNo);//????????????????????????????car
             toolname.append(no);//color
             
             Jname.append('P');//type
             Jname.append(size1);//size
             Jname.append(jobOfTool);//sector
             Jname.append(no);

             ps = connection.prepareStatement("INSERT INTO iplate(name,type,sector,size,area,status,DateOfAttachment,isle,carierNo,colornumber,path,Jname) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
           
             ps.setString(1,toolname.toString());
             ps.setString(2,tooltype);
             ps.setString(3,Sector2);
             ps.setInt(4,size1);
             ps.setInt(5,area);
             ps.setInt(6,1);//status
             ps.setDate(7,date);
             ps.setInt(8,isle);
             ps.setInt(9,cNo);//?????????????????????????????????cariire
             ps.setInt(10,no);
             ps.setString(11,path);
             ps.setString(12, Jname.toString());
             boolean rs = ps.execute();
             if(!rs){JOptionPane.showMessageDialog(this, "تم الاضافة بنجاح\n Tool name="+toolname.toString());
             this.FileUrl.setText("");
            
             }
                
             else  JOptionPane.showMessageDialog(this, "اختر الالوان"); 
                
                }
                
              for (String color1 : color) {
                   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                   ps1 = connection.prepareStatement("INSERT INTO color(platename,color) VALUES (?,?)");
                   ps1.setString(1,toolname.toString());
                   ps1.setString(2, color1);
                   ps1.execute();
               }
               }
             } catch (HeadlessException | SQLException ex ) {
             JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
             }    
             }
             else  JOptionPane.showMessageDialog(this, "Erorr");    
       //}
        
         /*if(otype=="تعديل"){
             String search=this.searchKey2.getText();
             String path1 = FileUrl.getText();
             char TypeOfTool=search.charAt(0);
             switch (TypeOfTool) {
             case 'D':case  'd':
                f.UpdateToolUrl("dicut", search, path1);
                break;
             case 'P':case  'p':
                f.UpdateToolUrl("iplate", search, path1);
                break;
             case 'C':case  'c':
                f.UpdateToolUrl("iclasheh", search, path1);
                break;
             }
             JOptionPane.showMessageDialog(this, "تم تعديل الأداة");
         }*/
        }
    }//GEN-LAST:event_OKMouseClicked

    private void SearchBTNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchBTNMouseClicked
        // TODO add your handling code here:
        /*String Path;
        Vector data = new Vector();
        this.Colors1.setListData(data);
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
        int area=0;*/
        
        jCheckBox1.setSelected(false);
        jCheckBox2.setSelected(false);
        jCheckBox4.setSelected(false);
        jCheckBox5.setSelected(false);
        
        String Callee="S1";
        String search=this.searchKey2.getText();
        if(search.isEmpty()){JOptionPane.showMessageDialog(this,"Empty Search Field" );}
        else{
            boolean x,w;
            char TypeOfTool=search.charAt(0);
            //Connection connection;
           // PreparedStatement ps,ps1,ps2;

            switch (TypeOfTool) {
                case 'D':case  'd':
                   x= SearchForTool("dicut", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("dicut",search,"Jname");
                       if(w){
                           Table T = new Table(search,"dicut");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
                   
                       

               /* try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps = connection.prepareStatement("select * from dicut where name= ?");
                    ps.setString(1,search );
                    ResultSet rs = ps.executeQuery();
                    if(rs.next())
                    {
                        this.Tool1.setSelectedItem("Dicut");
                        TypeOfTool1=rs.getString(2);
                        JobOfTool1=rs.getString(3);
                        size=rs.getInt(4);
                        area=rs.getInt(5);
                        status=rs.getInt(6);
                        supplier1=rs.getString(10);
                        isle=rs.getInt(11);
                        carierNo =rs.getInt(12);
                        Path=rs.getString(13);
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
                     try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    }

                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } */      break;
                case 'P':case  'p':
                   x= SearchForTool("iplate", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("iplate",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iplate");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iplate where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        this.Tool1.setSelectedItem("Plate");
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        area=rs1.getInt(5);
                        status=rs1.getInt(6);
                        isle=rs1.getInt(10);
                        carierNo =rs1.getInt(11);
                        colornumber1=rs1.getInt(12);
                        Path=rs1.getString(13);
                        ps2 = connection.prepareStatement("select * from color where platename= ?");
                        ps2.setString(1,search );
                        ResultSet rs2 = ps2.executeQuery();
                        while(rs2.next()){data.addElement(rs2.getString(2));}
                        this.Colors1.setListData(data);
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
                         try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                }  */     break;
                case 'C': case  'c':
                    x= SearchForTool("iclasheh", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("iclasheh",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iclasheh");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
                /*try{
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                    ps1 = connection.prepareStatement("select * from iclasheh where name= ?");
                    ps1.setString(1,search );
                    ResultSet rs1 = ps1.executeQuery();
                    if(rs1.next())
                    {
                        this.Tool1.setSelectedItem("IClasheh");
                        TypeOfTool1=rs1.getString(2);
                        JobOfTool1=rs1.getString(3);
                        size=rs1.getInt(4);
                        status=rs1.getInt(6);
                        area=rs1.getInt(5);
                        isle=rs1.getInt(10);
                        carierNo=rs1.getInt(11);
                        colornumber1=rs1.getInt(12);
                        Path=rs1.getString(13);
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
                                             try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 

                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } */      break;
                default:
                JOptionPane.showMessageDialog(this,"Not Found" );
                break;
            }
        }

       /* this.sectorno1.setText(Integer.toString(sectorno));
        this.Tool1.setSelectedItem(TypeOfTool1);
        this.Supplier1.setText(supplier1);
        this.Size2.setSelectedItem(size1);
        this.sector.setText(JobOfTool1);
        this.Status1.setText(status1);
        this.Area3.setText(Integer.toString(area));
        this.aisle3.setText(Integer.toString(isle));
        this.CarierNo2.setText(Integer.toString(carierNo));
        this.colorNo3.setText(Integer.toString(colornumber1));*/
    
    
    }//GEN-LAST:event_SearchBTNMouseClicked

    private void search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search1MouseClicked
        // TODO add your handling code here:
        String Path;
        Vector data = new Vector();
        this.Sectorno.setText("");
        this.Tool2.setText("");
        this.Supplier1.setText("");
        this.Size1.setText("");
        this.sector1.setText("");
        this.area.setText("");
        this.isle.setText("");
        this.CarierNo1.setText("");
        this.colorNo1.setText("");
        this.Colors1.setListData(data);
        ArrayList<String> list =new ArrayList<String>();
        String Callee="s2";
       /* String search=this.searchKey12.getText();
        if(search.isEmpty()){JOptionPane.showMessageDialog(this,"Empty Search Field" );System.out.print(search);}
        else{
            list=f.SearchTool(search);
        }*/
      /*  String TypeOfTool1="";
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
        int colornumber1=0;*/
        int area=0;
        String search=this.searchKey12.getText();
        if(search.isEmpty()){JOptionPane.showMessageDialog(this,"Empty Search Field" );}
        else{
            char TypeOfTool=search.charAt(0);
            Connection connection;
            PreparedStatement ps,ps1,ps2;
            boolean x,w;
            switch (TypeOfTool) {
                case 'D': case 'd':
                   x= SearchForTool("dicut", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("dicut",search,"Jname");
                       if(w){
                           Table T = new Table(search,"dicut");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try {
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
                        Path=rs.getString(12);
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
                                             try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    

                    }

                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                }  */     break;
                case 'P': case 'p':
                   x= SearchForTool("iplate", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("iplate",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iplate");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try{
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
                        Path=rs1.getString(12);
                        ps2 = connection.prepareStatement("select * from color where platename= ?");
                        ps2.setString(1,search );
                        ResultSet rs2 = ps2.executeQuery();
                        while(rs2.next()){data.addElement(rs2.getString(2));}
                        this.Colors1.setListData(data);
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
                                                                     try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 
                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                }   */    break;
                case 'C': case 'c':
                   x= SearchForTool("iclasheh", search,"name",Callee);
                   if(!x){
                       w= DoesItExist("iclasheh",search,"Jname");
                       if(w){
                           Table T = new Table(search,"iclasheh");
                           T.setVisible(true);
                       }
                       else if(!w){
                          JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                       }
                   }
               /* try{
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
                        Path=rs1.getString(12);
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
                                                                     try{
                                           
                        Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  Path);
                     }
                     catch(Exception e){JOptionPane.showMessageDialog(this, "Erorr image");} 

                    }
                    else  JOptionPane.showMessageDialog(this,"Not Found6666" );
                }
                catch (HeadlessException | SQLException ex ) {
                    JOptionPane.showMessageDialog(this,"Wrong \n"+ex );
                } */      break;
                default:
                JOptionPane.showMessageDialog(this,"لم يتم العثور على الأداة" );
                break;
            }
        }

        /*this.Tool2.setText(TypeOfTool1);
        this.Supplier1.setText(supplier1);
        this.Size1.setText(size1);
        this.sector.setText(JobOfTool1);
        this.Sectorno.setText(Integer.toString(sectorno));
        this.Status.setText(status1);
        this.area.setText(Integer.toString(area));
        this.isle.setText(Integer.toString(isle));
        this.CarierNo1.setText(Integer.toString(carierNo));
        this.colorNo1.setText(Integer.toString(colornumber1));*/

        
    }//GEN-LAST:event_search1MouseClicked

    private void OkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OkMouseClicked
        // TODO add your handling code here:
    String otype=(String) this.OPTool.getSelectedItem(); 
    boolean empty=this.searchKey12.getText().isEmpty();
    
    if(empty){JOptionPane.showMessageDialog(this, "اذخل اسم الأداة");}
    else{

      if(otype=="استعارة"){
      String search=this.searchKey12.getText();
      String ss;
      char type=search.charAt(0);
      int status=0;
      if(type=='D'||type=='d'){
         ss= SChange(status, search,"dicut","borrow" );
         Status.setText(ss);
         /* Connection connection;
          try {
              
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
              ps.setString(1,search);
                ResultSet s1 = ps.executeQuery();
                if(s1.next()) status =s1.getInt(1);
                if(status==1){
               PreparedStatement ps1 = connection.prepareStatement("UPDATE dicut SET status=? WHERE name=?");
               ps1.setInt(1,0);
                 ps1.setString(2,search);
                    boolean rs = ps1.execute();
             if(!rs)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة غير متوفرة تمت استعارتها");
                
         
          }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          } */
      }
      else  if(type=='P'||type=='p'){
          ss=SChange(status, search,"iplate","borrow" );
          Status.setText(ss);
          /*Connection connection;
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                  PreparedStatement p = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              ps.setString(1,search);
                ResultSet s2 = ps.executeQuery();
                if(s2.next()) status =s2.getInt(1);
                if(status==1){
               PreparedStatement ps2 = connection.prepareStatement("UPDATE iplate SET status=? WHERE name=?");
               ps2.setInt(1,0);
                 ps2.setString(2,search);
                    boolean rs2 = ps2.execute();
             if(!rs2)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة غير متوفرة تمت استعارتها");
                }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      } 
            else  if(type=='C'||type=='c'){
               ss= SChange(status, search,"iclasheh","borrow" );
               Status.setText(ss);
        /*  Connection connection;
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM iclasheh WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM iclasheh WHERE  name=?");
              ps.setString(1,search);
                ResultSet s3 = ps.executeQuery();
                if(s3.next()) status =s3.getInt(1);
                if(status==1){
               PreparedStatement ps3 = connection.prepareStatement("UPDATE iclasheh SET status=? WHERE name=?");
               ps3.setInt(1,0);
               ps3.setString(2,search);
                boolean rs3 = ps3.execute();
             if(!rs3)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة غير متوفرة تمت استعارتها");
                 }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      } 
       else  JOptionPane.showMessageDialog(this,"Not Found" );
  }
    if(otype=="ارجاع" ){
        String ss;
      String search=this.searchKey12.getText();
      char type=search.charAt(0);
      int status=0;
      if(type=='D'||type=='d'){
         ss= SChange(status, search,"dicut","return" );
         Status.setText(ss);
         /* Connection connection;
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
              ps.setString(1,search);
                ResultSet s1 = ps.executeQuery();
                if(s1.next()) status =s1.getInt(1);
                if(status==0){
               PreparedStatement ps1 = connection.prepareStatement("UPDATE dicut SET status=? WHERE name=?");
               ps1.setInt(1,1);
                 ps1.setString(2,search);
                    boolean rs = ps1.execute();
             if(!rs)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة  متوفرة ");
                                 }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      }
      else  if(type=='P'||type=='p'){
          ss=SChange(status, search,"iplate","return" );
          Status.setText(ss);
        /*  Connection connection;
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                PreparedStatement p = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              ps.setString(1,search);
                ResultSet s2 = ps.executeQuery();
                if(s2.next()) status =s2.getInt(1);
                if(status==0){
               PreparedStatement ps2 = connection.prepareStatement("UPDATE iplate SET status=? WHERE name=?");
               ps2.setInt(1,1);
                 ps2.setString(2,search);
                    boolean rs2 = ps2.execute();
             if(!rs2)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة متوفرة");
                 }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      } 
            else  if(type=='C' ||type=='c'){
               ss= SChange(status, search,"iclasheh","return" );
               Status.setText(ss);
        /*Connection connection;
          try {
              connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
                PreparedStatement p = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
              p.setString(1,search);
                ResultSet ss = p.executeQuery();
                if(ss.next()){
              PreparedStatement ps = connection.prepareStatement("SELECT status FROM iclasheh WHERE  name=?");
              ps.setString(1,search);
                ResultSet s3 = ps.executeQuery();
                if(s3.next()) status =s3.getInt(1);
                if(status==0){
               PreparedStatement ps3 = connection.prepareStatement("UPDATE iclasheh SET status=? WHERE name=?");
               ps3.setInt(1,1);
               ps3.setString(2,search);
                boolean rs3 = ps3.execute();
             if(!rs3)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                else JOptionPane.showMessageDialog(this, "الاداة متوفرة");
                   }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
         
     */ } 
       else  JOptionPane.showMessageDialog(this,"Not Found" );
  }
        if(otype=="حذف" ){
      String search=this.searchKey12.getText();
      char type=search.charAt(0);
      int status=0;
      if(type=='D'||type=='d'){
           this.deleteTool("dicut",searchKey12.getText()); 
      }
      else  if(type=='P'||type=='p'){
          this.deleteTool("iplate",searchKey12.getText());
      } 
      else  if(type=='C'||type=='c'){
                this.deleteTool("iclasheh",searchKey12.getText());
      } 
      else  JOptionPane.showMessageDialog(this,"Not Found" );
        }
           if(otype=="تالفة" ){
               String ss;
      String search=this.searchKey12.getText();
      char type=search.charAt(0);
      int status=0;
      if(type=='D'||type=='d'){
         ss= SChange(status, search,"dicut","expired" );
         Status.setText(ss);
          /*Connection connection;
          try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM dicut WHERE  name=?");
               p.setString(1,search);
               ResultSet ss = p.executeQuery();
               if(ss.next()){
               status =ss.getInt(1);
               if(status==1){
               PreparedStatement ps1 = connection.prepareStatement("UPDATE dicut SET status=? WHERE name=?");
               ps1.setInt(1,2);
                 ps1.setString(2,search);
                    boolean rs = ps1.execute();
             if(!rs)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                   else if(status==2)JOptionPane.showMessageDialog(this, "لقد تم اتلافها من قبل");
                else JOptionPane.showMessageDialog(this, " الاداة غير متوفرة");
             }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      }
      else  if(type=='P'||type=='p'){
          ss=SChange(status, search,"iplate","expired" );
          Status.setText(ss);
           /*Connection connection;
          try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM iplate WHERE  name=?");
               p.setString(1,search);
               ResultSet ss = p.executeQuery();
               if(ss.next()){
               status =ss.getInt(1);
               if(status==1){
               PreparedStatement ps1 = connection.prepareStatement("UPDATE iplate SET status=? WHERE name=?");
               ps1.setInt(1,2);
                 ps1.setString(2,search);
                    boolean rs = ps1.execute();
             if(!rs)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); 
               
                }
                   else if(status==2)JOptionPane.showMessageDialog(this, "لقد تم اتلافها من قبل");
                else JOptionPane.showMessageDialog(this, " الاداة غير متوفرة");
             }
                  else  JOptionPane.showMessageDialog(this,"Not Found" );
          } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
         
      } 
            else  if(type=='C'||type=='c'){
                ss=SChange(status, search,"iclasheh" ,"expired");
                Status.setText(ss);
            //Connection connection;
         // try {
               /*connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compony","root","");
               PreparedStatement p = connection.prepareStatement("SELECT status FROM iclasheh WHERE  name=?");
               p.setString(1,search);
               ResultSet ss = p.executeQuery();
               if(ss.next()){
               status =ss.getInt(1);*/
              /* PreparedStatement ps1 = connection.prepareStatement("UPDATE iclasheh SET status=? WHERE name=?");
               ps1.setInt(1,2);
                 ps1.setString(2,search);
                    boolean rs = ps1.execute();
             if(!rs)JOptionPane.showMessageDialog(this, "تم ىنجاح");
             else  JOptionPane.showMessageDialog(this, "Erorr"); */
                  
        /*  } catch (SQLException ex) {
              Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
          }*/
          } 
       else  JOptionPane.showMessageDialog(this,"Not Found" );
  //}
           }
    }
    }//GEN-LAST:event_OkMouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
        int row=jTable2.rowAtPoint(evt.getPoint());
        DefaultTableModel model= (DefaultTableModel)jTable2.getModel();
        String path = model.getValueAt(row, 4).toString();  
        try {
           Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  path);
        } catch (IOException ex) {
            Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void search1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_search1MouseEntered

    private void Tool1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tool1ActionPerformed
        // TODO add your handling code here:
        String selected = Tool1.getSelectedItem().toString();
        if (selected.equals("Dicut")){
           suplier.setEnabled(true);          colorNo3.setEnabled(false);
           jCheckBox1.setEnabled(false);           jCheckBox2.setEnabled(false);
           jCheckBox4.setEnabled(false);
           jCheckBox5.setEnabled(false);

        }
        else if (selected.equals("IClasheh")){
           suplier.setEnabled(false);           colorNo3.setEnabled(true);
           jCheckBox1.setEnabled(false);           jCheckBox2.setEnabled(false);
           jCheckBox4.setEnabled(false);
           jCheckBox5.setEnabled(false);
        }
        else if (selected.equals("Plate")){
           suplier.setEnabled(false);           colorNo3.setEnabled(false);
           jCheckBox1.setEnabled(true);           jCheckBox2.setEnabled(true);
           jCheckBox4.setEnabled(true);
           jCheckBox5.setEnabled(true);
        }
    }//GEN-LAST:event_Tool1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void Attach1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Attach1MouseClicked
        // TODO add your handling code here:
        JFileChooser chooser;
        String choosertitle="";
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            FileUrl.setText(chooser.getSelectedFile().toString());
                  /*System.out.println("getSelectedFile() : " 
         +  chooser.getSelectedFile());*/
        }
        else {
            
        }
    }//GEN-LAST:event_Attach1MouseClicked

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1KeyPressed

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row=jTable1.rowAtPoint(evt.getPoint());
        DefaultTableModel model= (DefaultTableModel)jTable1.getModel();
        String path = model.getValueAt(row, 7).toString();  
        System.out.println(path);
        try {
           Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " +  path);
        } catch (IOException ex) {
            Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void ToolListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ToolListMouseClicked
        // TODO add your handling code here:
        resetColor(this.AddOrder_Change);
        resetColor(this.ExpiredListOfTools);
        resetColor(this.OP_Orders);
        resetColor(this.Order_List);
        setColor(this.ToolList);
        cardLayout.show(StorageCards, "card6");
    }//GEN-LAST:event_ToolListMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StorageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StorageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StorageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StorageFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new StorageFrame("").setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(StorageFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddChangeTool;
    private javax.swing.JPanel AddOrder_Change;
    private javax.swing.JTextField Area3;
    private javax.swing.JPanel Attach1;
    private javax.swing.JTextField CarierNo1;
    private javax.swing.JTextField CarierNo2;
    private javax.swing.JTable ClashehTable;
    private javax.swing.JList<String> Colors1;
    private javax.swing.JTable DicutTable;
    private javax.swing.JPanel ExpiredListOfTools;
    private javax.swing.JPanel ExpiredToolsList;
    private javax.swing.JTextField FileUrl;
    private javax.swing.JPanel LogOut;
    private javax.swing.JLabel NoOfClasheh;
    private javax.swing.JLabel NoOfDicut;
    private javax.swing.JLabel NoOfPlate;
    private javax.swing.JPanel OK;
    private javax.swing.JPanel OPOnTools;
    private javax.swing.JComboBox<String> OPTool;
    private javax.swing.JPanel OP_Orders;
    private javax.swing.JPanel Ok;
    private javax.swing.JPanel OrderList1;
    private javax.swing.JPanel Order_List;
    private javax.swing.JTable PlateTable;
    private javax.swing.JPanel SearchBTN;
    private javax.swing.JComboBox<String> Sector;
    private javax.swing.JTextField Sectorno;
    private javax.swing.JPanel SidePannel;
    private javax.swing.JTextField Size1;
    private javax.swing.JComboBox<String> Size2;
    private javax.swing.JLabel Status;
    private javax.swing.JLabel Status1;
    private javax.swing.JPanel StorageCards;
    private javax.swing.JTextField Supplier1;
    private javax.swing.JComboBox<String> Tool1;
    private javax.swing.JTextField Tool2;
    private javax.swing.JPanel ToolList;
    private javax.swing.JTextField aisle3;
    private javax.swing.JTextField area;
    private javax.swing.JPanel clasheh;
    private javax.swing.JTextField colorNo1;
    private javax.swing.JTextField colorNo3;
    private javax.swing.JPanel dicut;
    private javax.swing.JTextField isle;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel names;
    private javax.swing.JPanel plate;
    private javax.swing.JPanel search1;
    private javax.swing.JTextField searchKey12;
    private javax.swing.JTextField searchKey2;
    private javax.swing.JTextField sector1;
    private javax.swing.JTextField sectorno1;
    private javax.swing.JComboBox<String> suplier;
    private javax.swing.JTabbedPane toolsTap;
    // End of variables declaration//GEN-END:variables
}
