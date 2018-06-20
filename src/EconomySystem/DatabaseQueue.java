package EconomySystem;

import java.util.ArrayList;

public class DatabaseQueue {
    
    static ArrayList<EconomySystemMain.Wallet> queuedWalletCreations;
    static ArrayList<EconomySystemMain.Wallet> queuedWalletChanges;
    static ArrayList<EconomySystemMain.Transaction> queuedTransactionCreations;
    static ArrayList<EconomySystemMain.CoD> queuedCoDCreations;
    static ArrayList<EconomySystemMain.CoD> queuedCoDDeletions;
    
    public void setQueuedWalletCreations(ArrayList<EconomySystemMain.Wallet> qwc){
        queuedWalletCreations = qwc;
    }
    public void setQueuedWalletChanges(ArrayList<EconomySystemMain.Wallet> qwc){
        queuedWalletChanges = qwc;
    }
    
    public void setQueuedTransactionCreations(ArrayList<EconomySystemMain.Transaction> qtc){
        queuedTransactionCreations = qtc;
    }
    
    public void setQueuedCoDCreations(ArrayList<EconomySystemMain.CoD> qcodc){
        queuedCoDCreations = qcodc;
    }
    
    public void setQueuedCoDDeletions(ArrayList<EconomySystemMain.CoD> qcodd){
        queuedCoDDeletions = qcodd;
    }
    
    public ArrayList<EconomySystemMain.Wallet> getQueuedWalletCreations(){
        return queuedWalletCreations;
    }
    public ArrayList<EconomySystemMain.Wallet> getQueuedWalletChanges(){
        return queuedWalletChanges;
    }
    
    public ArrayList<EconomySystemMain.Transaction> getQueuedTransactionCreations(){
        return queuedTransactionCreations;
    }
    
    public ArrayList<EconomySystemMain.CoD> getQueuedCoDCreations(){
        return queuedCoDCreations;
    }
    
    public ArrayList<EconomySystemMain.CoD> getQueuedCoDDeletions(){
        return queuedCoDDeletions;
    }
    
    public void clearQueue(){
        queuedWalletChanges = new ArrayList<>();
        queuedTransactionCreations = new ArrayList<>();
        queuedCoDCreations = new ArrayList<>();
        queuedCoDDeletions = new ArrayList<>();
    }
}
