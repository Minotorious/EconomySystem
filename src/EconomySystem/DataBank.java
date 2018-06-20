package EconomySystem;

import java.util.ArrayList;

public class DataBank {
    
    static String[][] settings;
    static ArrayList<EconomySystemMain.Product> products;
    static ArrayList<EconomySystemMain.Wallet> wallets;
    static ArrayList<EconomySystemMain.Transaction> transactions;
    static ArrayList<EconomySystemMain.CoD> cods;
    
    public String[][] getSettings(){
        return settings;
    }
    
    public void setSettings(String[][] S){
        settings = S;
    }
    
    public ArrayList<EconomySystemMain.Product> getProducts(){
        return products;
    }
    
    public void setProducts(ArrayList<EconomySystemMain.Product> P){
        products = P;
    }
    
    public ArrayList<EconomySystemMain.Wallet> getWallets(){
        return wallets;
    }
    
    public void setWallets(ArrayList<EconomySystemMain.Wallet> W){
        wallets = W;
    }
    
    public ArrayList<EconomySystemMain.Transaction> getTransactions(){
        return transactions;
    }
    
    public void setTransactions(ArrayList<EconomySystemMain.Transaction> T){
        transactions = T;
    }
    
    public ArrayList<EconomySystemMain.CoD> getCoDs(){
        return cods;
    }
    
    public void setCoDs(ArrayList<EconomySystemMain.CoD> C){
        cods = C;
    }
    
    public float getBalance(long PlayerUserID){
        float balance = -1;
        
        for (EconomySystemMain.Wallet wallet : wallets){
            if (wallet.playerUserID == PlayerUserID){
                balance = wallet.balance;
            }
        }
        
        return balance;
    }
    
    public float getServerBuyPrice(short productID){
        float buyprice = -1.0f;
        
        for (EconomySystemMain.Product product : products){
            if (product.itemID == productID){
                buyprice = product.serverBuyPrice;
            }
        }
        
        return buyprice;
    }
    
    public float getServerSellPrice(short productID){
        float sellprice = -1.0f;
        
        for (EconomySystemMain.Product product : products){
            if (product.itemID == productID){
                sellprice = product.serverBuyPrice;
            }
        }
        
        return sellprice;
    }
    
    public EconomySystemMain.Product getProductByID(short productID){
        EconomySystemMain.Product product;
        
        for (EconomySystemMain.Product cproduct : products){
            if (cproduct.itemID == productID){
                product = cproduct;
                return product;
            }
        }
        
        return null;
    }
    
    public ArrayList<EconomySystemMain.Transaction> getPlayerTransactions(long PlayerUserID){
        ArrayList<EconomySystemMain.Transaction> playerTransactions = new ArrayList<>();
        
        for (EconomySystemMain.Transaction transaction : transactions){
            if (transaction.receiverUserID == PlayerUserID || transaction.senderUserID == PlayerUserID){
                playerTransactions.add(transaction);
            }
        }
        
        return playerTransactions;
    }
    
    public ArrayList<EconomySystemMain.CoD> getBuyCoDs(long PlayerUserID){
        EconomySystemMain ESM = new EconomySystemMain();
        ArrayList<EconomySystemMain.CoD> buyCoDs = new ArrayList<>();
        
        for (EconomySystemMain.CoD cod : cods){
            if (cod.receiverUserID == PlayerUserID){
                buyCoDs.add(cod);
            }
        }
        
        return buyCoDs;
    }
    
    public ArrayList<EconomySystemMain.CoD> getSellCoDs(long PlayerUserID){
        EconomySystemMain ESM = new EconomySystemMain();
        ArrayList<EconomySystemMain.CoD> sellCoDs = new ArrayList<>();
        
        for (EconomySystemMain.CoD cod : cods){
            if (cod.senderUserID == PlayerUserID){
                sellCoDs.add(cod);
            }
        }
        
        return sellCoDs;
    }
    
    public boolean WalletCheck(long PlayerUserID){
        boolean hasWallet = false;
        
        for (EconomySystemMain.Wallet wallet : wallets){
            if (wallet.playerUserID == PlayerUserID){
                hasWallet = true;
            }
        }
        
        return hasWallet;
    }
    
    public String SettingbyName(String input){
        String output = "";
        
        for (String[] setting : settings){
            if (setting[0].equals(input)){
                output = setting[1];
            }
        }
        
        return output;
    }
}
