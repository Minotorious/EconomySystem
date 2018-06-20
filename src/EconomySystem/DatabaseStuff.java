package EconomySystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Date;

import net.risingworld.api.database.Database;

public class DatabaseStuff {
    
    public static Database mainDB;
    public static Database productsDB;
    
    public void setmainDB(Database db){
        mainDB = db;
    }
    
    public void initmainDB(){
        mainDB.execute("CREATE TABLE IF NOT EXISTS 'Wallets' ("
                + "'idNo' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "'PlayerUserID' LONG, "
                + "'Balance' FLOAT "
                + ")");
        
        mainDB.execute("CREATE TABLE IF NOT EXISTS 'Transactions' ("
                + "'idNo' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "'SenderUserID' LONG, "
                + "'ReceiverUserID' LONG, "
                + "'ItemName' VARCHAR(64), "
                + "'Quantity' INTEGER, "
                + "'OldBalance' FLOAT, "
                + "'NewBalance' FLOAT, "
                + "'Cost' FLOAT, "
                + "'Timestamp' TIMESTAMP "
                + ")");
        
        mainDB.execute("CREATE TABLE IF NOT EXISTS 'CoD' ("
                + "'idNo' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "'SenderUserID' LONG, "
                + "'ReceiverUserID' LONG, "
                + "'ProductType' STRING, "
                + "'ProductIDNo' INTEGER, "
                + "'ProductTextureID' INTEGER, "
                + "'Quantity' INTEGER, "
                + "'Cost' FLOAT "
                + ")");
    }
    
    public ArrayList<EconomySystemMain.Wallet> getAllWallets(){
        Connection con = mainDB.getConnection();
        
        ArrayList<EconomySystemMain.Wallet> wallets = new ArrayList<>();
        EconomySystemMain ESM = new EconomySystemMain();
        
        try{
            PreparedStatement prep = con.prepareStatement("SELECT * FROM Wallets");
            ResultSet result = prep.executeQuery();
            while (result.next()){
                EconomySystemMain.Wallet W = ESM.new Wallet();
                W.playerUserID = result.getLong("PlayerUserID");
                W.balance = result.getFloat("Balance");
                
                wallets.add(W);
            }
        }
        catch(SQLException e){
        }
        
        return wallets;
    }
    
    public ArrayList<EconomySystemMain.Transaction> getAllTransactions(){
        Connection con = mainDB.getConnection();
        
        ArrayList<EconomySystemMain.Transaction> transactions = new ArrayList<>();
        EconomySystemMain ESM = new EconomySystemMain();
        
        try{
            PreparedStatement prep = con.prepareStatement("SELECT * FROM Transactions");
            ResultSet result = prep.executeQuery();
            while (result.next()){
                EconomySystemMain.Transaction T = ESM.new Transaction();
                
                T.senderUserID = result.getLong("SenderUserID");
                T.receiverUserID = result.getLong("ReceiverUserID");
                T.itemName = result.getString("ItemName");
                T.quantity = result.getInt("Quantity");
                T.oldBalance = result.getFloat("Oldbalance");
                T.newBalance = result.getFloat("Newbalance");
                T.cost = result.getFloat("Cost");
                T.timestamp = result.getTimestamp("Timestamp");
                
                transactions.add(T);
            }
        }
        catch(SQLException e){
        }
        
        return transactions;
    }
    
    public ArrayList<EconomySystemMain.CoD> getAllCoDs(){
        Connection con = mainDB.getConnection();
        
        ArrayList<EconomySystemMain.CoD> cods = new ArrayList<>();
        EconomySystemMain ESM = new EconomySystemMain();
        
        try{
            PreparedStatement prep = con.prepareStatement("SELECT * FROM CoD");
            ResultSet result = prep.executeQuery();
            while (result.next()){
                EconomySystemMain.CoD C = ESM.new CoD();
                
                C.idNo = result.getInt("idNo");
                C.senderUserID = result.getLong("SenderUserID");
                C.receiverUserID = result.getLong("ReceiverUserID");
                C.productType = result.getString("ProductType");
                C.productIDNo = result.getInt("ProductIDNo");
                C.productTextureID = result.getInt("ProductTextureID");
                C.quantity = result.getInt("Quantity");
                C.cost = result.getFloat("Cost");
                
                cods.add(C);
            }
        }
        catch(SQLException e){
        }
        
        return cods;
    }

    public void monthlyClear(){
        DataBank DB = new DataBank();
        
        if (Integer.parseInt(DB.SettingbyName("TransactionDeletionPeriod")) > 0){
            Connection con = mainDB.getConnection();
            
            try{
                PreparedStatement prep = con.prepareStatement("SELECT * FROM Transactions");
                ResultSet result = prep.executeQuery();
                Date date = new Date();

                while (result.next()){
                    Timestamp olddate = result.getTimestamp("Timestamp");
                    long difference = (date.getTime() - olddate.getTime())/(1000*60*60*24);

                    if (difference >= Integer.parseInt(DB.SettingbyName("TransactionDeletionPeriod"))){
                        PreparedStatement prep2 = con.prepareStatement("DELETE FROM Transactions WHERE Timestamp LIKE " + Long.toString(olddate.getTime()));
                        prep2.executeUpdate();
                    }
                }
            }
            catch(SQLException e){
            }
        }
    }
    
    public void queueExecution(){
        Connection con = mainDB.getConnection();
        DatabaseQueue DQ = new DatabaseQueue();
        DataBank DB = new DataBank();
        
        ArrayList<EconomySystemMain.Wallet> queuedWalletCreations = DQ.getQueuedWalletCreations();
        ArrayList<EconomySystemMain.Wallet> queuedWalletChanges = DQ.getQueuedWalletChanges();
        ArrayList<EconomySystemMain.Transaction> queuedTransactionCreations = DQ.getQueuedTransactionCreations();
        ArrayList<EconomySystemMain.CoD> queuedCoDCreations = DQ.getQueuedCoDCreations();
        ArrayList<EconomySystemMain.CoD> queuedCoDDeletions = DQ.getQueuedCoDDeletions();
        
        for (EconomySystemMain.Wallet wallet : queuedWalletCreations){
            try{
                PreparedStatement prep = con.prepareStatement("INSERT INTO Wallets (PlayerUserID,Balance) VALUES (?,?)");
                prep.setLong(1, wallet.playerUserID);
                prep.setFloat(2, Float.parseFloat(DB.SettingbyName("initialAmount")));
                prep.executeUpdate();
            }
            catch(SQLException e){
            }
        }
        
        for (EconomySystemMain.Wallet wallet : queuedWalletChanges){
            try{
                PreparedStatement prep = con.prepareStatement("UPDATE Wallets SET Balance = ? WHERE PlayerUserID = ?");
                prep.setFloat(1, wallet.balance);
                prep.setLong(2, wallet.playerUserID);
                prep.executeUpdate();
            }
            catch(SQLException e){
            }
        }
        
        for (EconomySystemMain.Transaction transaction : queuedTransactionCreations){
            try{
                PreparedStatement prep = con.prepareStatement("INSERT INTO Transactions ("
                    + "SenderUserID, "
                    + "ReceiverUserID, "
                    + "ItemName, "
                    + "Quantity, "
                    + "OldBalance, "
                    + "NewBalance, "
                    + "Cost, "
                    + "Timestamp) VALUES (?,?,?,?,?,?,?,?)");
                prep.setLong(1, transaction.senderUserID);
                prep.setLong(2, transaction.receiverUserID);
                prep.setString(3, transaction.itemName);
                prep.setInt(4, transaction.quantity);
                prep.setFloat(5, transaction.oldBalance);
                prep.setFloat(6, transaction.newBalance);
                prep.setFloat(7, transaction.cost);
                prep.setTimestamp(8, transaction.timestamp);

                prep.executeUpdate();
            }
            catch(SQLException e){
            }
        }
        
        for (EconomySystemMain.CoD cod : queuedCoDCreations){
            try{
                PreparedStatement prep = con.prepareStatement("INSERT INTO CoD ("
                    + "SenderUserID, "
                    + "ReceiverUserID, "
                    + "ProductType, "
                    + "ProductIDNo, "
                    + "ProductTextureID, "
                    + "Quantity, "
                    + "Cost) VALUES (?,?,?,?,?,?,?)");
                prep.setLong(1, cod.senderUserID);
                prep.setLong(2, cod.receiverUserID);
                prep.setString(3, cod.productType);
                prep.setInt(4, cod.productIDNo);
                prep.setInt(5, cod.productTextureID);
                prep.setInt(6, cod.quantity);
                prep.setFloat(7, cod.cost);

                prep.executeUpdate();
            }
            catch(SQLException e){
            }
        }
        
        for (EconomySystemMain.CoD cod : queuedCoDDeletions){
            try{
                PreparedStatement prep4 = con.prepareStatement("DELETE FROM CoD WHERE idNo LIKE ?");
                
                prep4.setLong(1, cod.idNo); 
                prep4.executeUpdate();
            }
            catch (SQLException e){
            }
        }
        
    }
    
    public void setproductsDB(Database db){
        productsDB = db;
    }
    
    public void initproductsDB(){
        productsDB.execute("CREATE TABLE IF NOT EXISTS 'Products' ("
                + "'idNo' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "'ItemID' SHORT, "
                + "'Type' STRING, "
                + "'ItemID' SHORT, "
                + "'TextureID' INTEGER, "
                + "'ServerBuyPrice' FLOAT, "
                + "'ServerSellPrice' FLOAT, "
                + "'ServerBuying' BOOLEAN, "
                + "'ServerSelling' BOOLEAN "
                + ")");
    }
    
    public ArrayList<EconomySystemMain.Product> getAllProducts(){
        Connection con = productsDB.getConnection();
        
        ArrayList<EconomySystemMain.Product> products = new ArrayList<>();
        EconomySystemMain ESM = new EconomySystemMain();
        
        try{
            PreparedStatement prep = con.prepareStatement("SELECT * FROM Products");
            ResultSet result = prep.executeQuery();
            while (result.next()){
                EconomySystemMain.Product P = ESM.new Product();
                P.idNo = result.getInt("idNo");
                P.itemID = result.getShort("ItemID");
                P.type = result.getString("Type");
                P.serverBuyPrice = result.getFloat("ServerBuyPrice");
                P.serverSellPrice = result.getFloat("ServerSellPrice");
                P.serverBuying = result.getBoolean("ServerBuying");
                P.serverSelling = result.getBoolean("ServerSelling");
                
                products.add(P);
            }
        }
        catch(SQLException e){
        }
        
        return products;
    }
}