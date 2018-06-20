package EconomySystem;

import EconomySystem.Listeners.EconomySystemCommandListener;
import java.io.File;
import java.util.ArrayList;
import java.sql.Timestamp;

import net.risingworld.api.Plugin;
import net.risingworld.api.database.Database;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.objects.Player;
import net.risingworld.api.utils.Utils;
import net.risingworld.api.Timer;

public class EconomySystemMain extends Plugin implements Listener {
    
    @Override
    public void onEnable(){
        
        EconomySystemCommandListener ESComL = new EconomySystemCommandListener();
        
        registerEventListener(this);
        registerEventListener(ESComL);
        
        readSettings();
        
        Database mainSQLite = getSQLiteConnection(getPath() + "/assets/" + getWorld().getName() + "/Economy.db");
        Database productsSQLite = getSQLiteConnection(getPath() + "/assets/" + getWorld().getName() + "/Products.db");
        
        DatabaseStuff DbS = new DatabaseStuff();
        
        DbS.setmainDB(mainSQLite);
        DbS.setproductsDB(productsSQLite);
        
        DbS.initmainDB();
        DbS.initproductsDB();
        
        DbS.monthlyClear();
        
        DataBank DB = new DataBank();
        
        DB.setProducts(DbS.getAllProducts());
        DB.setWallets(DbS.getAllWallets());
        DB.setTransactions(DbS.getAllTransactions());
        DB.setCoDs(DbS.getAllCoDs());
        
        Timer queueExecutionTimer = new Timer(3f, 300f, -1, () -> {
            DbS.queueExecution();
        });
        queueExecutionTimer.start();
    }
    
    @Override
    public void onDisable(){
        DatabaseStuff DbS = new DatabaseStuff();
        DbS.queueExecution();
    }
    
    @EventMethod
    public void onSpawn(PlayerSpawnEvent event){
        
        Player player = event.getPlayer();
        long PlayerUserID = player.getUID();
        
        DatabaseStuff DbS = new DatabaseStuff();
        DataBank DB = new DataBank();
        DatabaseQueue DQ = new DatabaseQueue();
        
        if (!DB.WalletCheck(PlayerUserID)){
            Wallet w = new Wallet();
            w.playerUserID = player.getUID();
            w.balance = Float.parseFloat(DB.SettingbyName("initialAmount"));
            DB.getWallets().add(w);
            DQ.getQueuedWalletCreations().add(w);
        }
    }

    public void readSettings(){
        File SettingsTxt = new File(getPath() + "/assets/settings.txt");
        DataBank DB = new DataBank();
        if (SettingsTxt.exists()){
            String content = Utils.FileUtils.readStringFromFile(SettingsTxt);
            if(content != null && !content.isEmpty()){
                String[] lines = content.split("\r\n|\n|\r");
                String[][] settings = new String[lines.length][2];
                int linecount = 0;
                for (String line : lines) {
                    String[] cline = line.split(":", 2);
                    if (cline.length == 2){
                        settings[linecount][0] = cline[0];
                        settings[linecount][1] = cline[1];
                        linecount += 1;
                    }
                }
                DB.setSettings(settings);
            }
            else{
                String[][] settings = new String[0][0];
                DB.setSettings(settings);
            }
        }
        else{
            String[][] settings = new String[0][0];
            DB.setSettings(settings);
        }
    }
    
    public class Product{
        public int idNo;
        public short itemID;
        public String type; //item, object, clothing
        public float serverBuyPrice;
        public float serverSellPrice;
        public boolean serverBuying;
        public boolean serverSelling;
    }
    
    public class Wallet{
        public long playerUserID;
        public float balance;
    }
    
    public class Transaction{
        public long senderUserID;
        public long receiverUserID;
        public String itemName;
        public int quantity;
        public float oldBalance;
        public float newBalance;
        public float cost;
        public Timestamp timestamp;
    }
    
    public class CoD{
        public int idNo;
        public long senderUserID;
        public long receiverUserID;
        public String productType;
        public int productIDNo;
        public int productTextureID;
        public int quantity;
        public float cost;
    }
}