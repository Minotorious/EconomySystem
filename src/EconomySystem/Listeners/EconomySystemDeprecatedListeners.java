package EconomySystem.Listeners;

import EconomySystem.EconomySystemMain;
import EconomySystem.DatabaseStuff;
import EconomySystem.DataBank;

import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.objects.Inventory;
import net.risingworld.api.objects.Player;
import net.risingworld.api.objects.Item;
import net.risingworld.api.utils.Definitions;

public class EconomySystemDeprecatedListeners implements  Listener{
    /*
    @EventMethod
    public void onCommand(PlayerCommandEvent event){
        Player player = event.getPlayer();
        String command = event.getCommand();
        
        String[] cmd = command.split(" ");
        if (cmd[0].equals("/es")){
            if (cmd.length >= 2){
                DatabaseStuff DbS = new DatabaseStuff();
                DataBank DB = new DataBank();
                EconomySystemMain ESM = new EconomySystemMain();
                switch (cmd[1]){
                    case "buy":
                        if (cmd.length == 4){
                            String ItemName = cmd[2];
                            try{
                                int Amount = Integer.parseInt(cmd[3]);
                                EconomySystemMain.Product product = DB.getProductByName(ItemName);
                                String ItemDefinitionName = ItemName;
                                if (product != null){
                                    if ("dirt".equals(ItemName) ||
                                        "grass".equals(ItemName) ||
                                        "drydirt".equals(ItemName) ||
                                        "clay".equals(ItemName) ||
                                        "sand".equals(ItemName) ||
                                        "mud".equals(ItemName) ||
                                        "snow".equals(ItemName) ||
                                        "coal".equals(ItemName) ||
                                        "gravel".equals(ItemName) ||
                                        "sandstone".equals(ItemName) ||
                                        "hellstone".equals(ItemName) ||
                                        "stone".equals(ItemName) ||
                                        "ironore".equals(ItemName) ||
                                        "copperore".equals(ItemName) ||
                                        "aluminiumore".equals(ItemName) ||
                                        "sulfurore".equals(ItemName) ||
                                        "tungstenore".equals(ItemName) ||
                                        "silverore".equals(ItemName) ||
                                        "goldore".equals(ItemName) ||
                                        "mithrilore".equals(ItemName)
                                        ){
                                        ItemDefinitionName = "ore";
                                    }
                                    if (product.textureID != -1){
                                        if (Amount <= Definitions.getItemDefinition(ItemDefinitionName).getMaxStacksize()){
                                            float Cost = product.price * Amount;
                                            float currentBalance = DB.getBalance(player.getUID());
                                            float newBalance = currentBalance - Cost;

                                            if (newBalance >= 0.0f){
                                                Item item = player.getInventory().insertNewItem(product.itemID, product.textureID, Amount);

                                                if (item != null){
                                                    DbS.setBalance(player.getUID(), newBalance);
                                                    player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully bought " + Integer.toString(Amount) + " " + product.name + " for " + Float.toString(Cost) + DB.SettingbyName("currencySymbol"));
                                                    Date date = new Date();
                                                    Timestamp timestamp = new Timestamp(date.getTime());

                                                    EconomySystemMain.Transaction transaction = ESM.new Transaction();
                                                    transaction.senderUserID = 00000000000000000;
                                                    transaction.receiverUserID = player.getUID();
                                                    transaction.itemName = ItemName;
                                                    transaction.quantity = Amount;
                                                    transaction.oldBalance = currentBalance;
                                                    transaction.newBalance = newBalance;
                                                    transaction.cost = -Cost;
                                                    transaction.timestamp = timestamp;

                                                    DbS.createTransaction(transaction);
                                                }
                                                else{
                                                    player.sendTextMessage("[#FF0000]You do not have enough space in your inventory to buy the requested items!");
                                                }
                                            }
                                            else{
                                                player.sendTextMessage("[#FF0000]You do not have enough money to buy the requested items!");
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]You cannot buy more than one stack of items at a time! Maximum stack size for your selected product is: " + Integer.toString(Definitions.getItemDefinition(ItemDefinitionName).getMaxStacksize()));
                                        }
                                    }
                                    else{
                                        player.sendTextMessage("[#FF0000]Your selected product requires texture specification to be obtained!");
                                    }
                                }
                                else{
                                    player.sendTextMessage("[#FF0000]You have to provide a valid product name!");
                                }
                            }
                            catch (NumberFormatException e){
                                player.sendTextMessage("[#FF0000]You have to provide a valid quantity!");
                            }
                        }
                        else if (cmd.length == 5){
                            String ItemName = cmd[2];
                            try{
                                int Texture = Integer.parseInt(cmd[3]);
                                if (Texture >= 21 && Texture <= 218){
                                    try{
                                        int Amount = Integer.parseInt(cmd[4]);
                                        EconomySystemMain.Product product = DB.getProductByName(ItemName);
                                        
                                        if (product != null){
                                            if (product.textureID == -1){
                                                if (Amount <= Definitions.getItemDefinition(ItemName).getMaxStacksize()){
                                                    float Cost = product.price * Amount;
                                                    float currentBalance = DbS.getBalance(player.getUID());
                                                    float newBalance = currentBalance - Cost;

                                                    if (newBalance >= 0.0f){
                                                        Item item = player.getInventory().insertNewItem(product.itemID, Texture, Amount);

                                                        if (item != null){
                                                            DbS.setBalance(player.getUID(), newBalance);
                                                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully bought " + Integer.toString(Amount) + " " + product.name + " for " + Float.toString(Cost) + DB.SettingbyName("currencySymbol"));
                                                            Date date = new Date();
                                                            Timestamp timestamp = new Timestamp(date.getTime());

                                                            EconomySystemMain.Transaction transaction = ESM.new Transaction();
                                                            transaction.senderUserID = 00000000000000000;
                                                            transaction.receiverUserID = player.getUID();
                                                            transaction.itemName = ItemName + " " + Integer.toString(Texture + 21);
                                                            transaction.quantity = Amount;
                                                            transaction.oldBalance = currentBalance;
                                                            transaction.newBalance = newBalance;
                                                            transaction.cost = -Cost;
                                                            transaction.timestamp = timestamp;

                                                            DbS.createTransaction(transaction);
                                                        }
                                                        else{
                                                            player.sendTextMessage("[#FF0000]You do not have enough space in your inventory to buy the requested items!");
                                                        }
                                                    }
                                                    else{
                                                        player.sendTextMessage("[#FF0000]You do not have enough money to buy the requested items!");
                                                    }
                                                }
                                                else{
                                                    player.sendTextMessage("[#FF0000]You cannot buy more than one stack of items at a time! Maximum stack size for your selected product is: " + Integer.toString(Definitions.getItemDefinition(ItemName).getMaxStacksize()));
                                                }
                                            }
                                            else{
                                                player.sendTextMessage("[#FF0000]Your selected product does not support texture specification!");
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]You have to provide a valid product name!");
                                        }
                                    }
                                    catch (NumberFormatException e){
                                        player.sendTextMessage("[#FF0000]You have to provide a valid quantity!");
                                    }
                                }
                                else{
                                    player.sendTextMessage("[#FF0000]You have to provide a valid texture number!");
                                }
                            }
                            catch (NumberFormatException e){
                                player.sendTextMessage("[#FF0000]You have to provide a valid texture number!");
                            }
                        }
                        break;
                    case "sell":
                        if (cmd.length == 2){
                            Item item = player.getEquippedItem();
                            if (item != null){
                                String ItemName = item.getName();
                                EconomySystemMain.Product product = null;
                                if ("ore".equals(ItemName)){
                                    int texture = item.getVariation();
                                    product = DB.getProductByTexture(texture);
                                }
                                else{
                                    product = DB.getProductByName(ItemName);
                                }
                                if (product != null){
                                    int ItemNumber = item.getStacksize();
                                    float Cost = product.price * ItemNumber;
                                    float currentBalance = DbS.getBalance(player.getUID());
                                    DbS.setBalance(player.getUID(), currentBalance + Cost);
                                    player.getInventory().removeItem(player.getInventory().getQuickslotFocus(), Inventory.SlotType.Quickslot);
                                    player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully sold " + Integer.toString(ItemNumber) + " " + product.name + " for " + Float.toString(Cost) + DB.SettingbyName("currencySymbol") + " to the server");
                                    Date date = new Date();
                                    Timestamp timestamp = new Timestamp(date.getTime());
                                    
                                    EconomySystemMain.Transaction transaction = ESM.new Transaction();
                                    transaction.senderUserID = player.getUID();
                                    transaction.receiverUserID = 00000000000000000;
                                    transaction.itemName = ItemName;
                                    transaction.quantity = ItemNumber;
                                    transaction.oldBalance = currentBalance;
                                    transaction.newBalance = currentBalance + Cost;
                                    transaction.cost = Cost;
                                    transaction.timestamp = timestamp;
                                    
                                    DbS.createTransaction(transaction);
                                }
                                else{
                                    player.sendTextMessage("[#FF0000]The item you are holding does not correspond to a valid product to sell!");
                                }
                            }
                            else{
                                player.sendTextMessage("[#FF0000]You are not currently holding any item to sell!");
                            }
                        }
                        else if (cmd.length >= 4){
                            Item item = player.getEquippedItem();
                            try{
                                float Cost = Float.parseFloat(cmd[2]);
                                String PlayerName = cmd[3];
                                if (cmd.length >= 5){
                                    for (int i=4;i<cmd.length;i++){
                                        PlayerName = PlayerName + " " + cmd[i];
                                    }
                                }
                                Player targetPlayer = ESM.getServer().getPlayer(PlayerName);
                                if (targetPlayer != null){
                                    if (item != null){
                                        String ItemName = item.getName();
                                        EconomySystemMain.Product product = null;
                                        if ("ore".equals(ItemName)){
                                            int texture = item.getVariation();
                                            product = DB.getProductByTexture(texture);
                                        }
                                        else{
                                            product = DB.getProductByName(ItemName);
                                        }
                                        if (product != null){
                                            if (product.textureID != -1){
                                                int ItemNumber = item.getStacksize();
                                                player.getInventory().removeItem(player.getInventory().getQuickslotFocus(), Inventory.SlotType.Quickslot);

                                                EconomySystemMain.CoD cod = ESM.new CoD();
                                                cod.itemName = ItemName;
                                                cod.cost = Cost;
                                                cod.quantity = ItemNumber;
                                                cod.senderUserID = player.getUID();
                                                cod.receiverUserID = targetPlayer.getUID();

                                                DbS.createCoD(cod);
                                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully sent a sell order for " + Integer.toString(ItemNumber) + " " + ItemName + " for " + Float.toString(Cost) + DB.SettingbyName("currencySymbol") + " to " + targetPlayer);
                                                targetPlayer.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have received a new buy order, use /es orders to see it or /es accept to accept it and get billed for the items in it");
                                            }
                                            else{
                                                player.sendTextMessage("[#FF0000]The item you are attempting to sell doesn't support player to player transactions! Please sell the primary resources required to make this item instead.");
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]The item you are holding does not correspond to a valid product to sell!");
                                        }
                                    }
                                    else{
                                        player.sendTextMessage("[#FF0000]You are not currently holding any item to sell!");
                                    }
                                }
                            }
                            catch(NumberFormatException e){
                                String PlayerName = cmd[2];
                                if (cmd.length >= 4){
                                    for (int i=3;i<cmd.length;i++){
                                        PlayerName = PlayerName + " " + cmd[i];
                                    }
                                }
                                Player targetPlayer = ESM.getServer().getPlayer(PlayerName);
                                if (targetPlayer != null){
                                    if (item != null){
                                        String ItemName = item.getName();
                                        EconomySystemMain.Product product = DB.getProductByName(ItemName);
                                        if (product != null){
                                            if (product.textureID != -1){
                                                int ItemNumber = item.getStacksize();
                                                float Cost = ItemNumber * product.price;
                                                player.getInventory().removeItem(player.getInventory().getQuickslotFocus(), Inventory.SlotType.Quickslot);
                                                
                                                EconomySystemMain.CoD cod = ESM.new CoD();
                                                cod.itemName = ItemName;
                                                cod.cost = Cost;
                                                cod.quantity = ItemNumber;
                                                cod.senderUserID = player.getUID();
                                                cod.receiverUserID = targetPlayer.getUID();

                                                DbS.createCoD(cod);
                                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully sent a sell order for " + Integer.toString(ItemNumber) + " " + ItemName + " for " + Float.toString(Cost) + DB.SettingbyName("currencySymbol") + " to " + targetPlayer);
                                                targetPlayer.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have received a new buy order, use /es orders to see it or /es accept to accept it and get billed for the items in it");
                                            }
                                            else{
                                                player.sendTextMessage("[#FF0000]The item you are attempting to sell doesn't support player to player transactions! Please sell the primary resources required to make this item instead.");
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]The item you are holding does not correspond to a valid product to sell!");
                                        }
                                    }
                                    else{
                                        player.sendTextMessage("[#FF0000]You are not currently holding any item to sell!");
                                    }
                                }
                            }
                        }
                        break;
                    case "send":
                        if (cmd.length >= 4){
                            try{
                                float Amount = Float.parseFloat(cmd[2]);
                                String PlayerName = cmd[3];
                                if (cmd.length >= 5){
                                    for (int i=4;i<cmd.length;i++){
                                        PlayerName = PlayerName + " " + cmd[i];
                                    }
                                }
                                Player targetPlayer = ESM.getServer().getPlayer(PlayerName);
                                if (targetPlayer != null){
                                    float currentBalance = DbS.getBalance(player.getUID());
                                    float newBalance = currentBalance - Amount;

                                    if (newBalance >= 0.0f){
                                        DbS.setBalance(player.getUID(), newBalance);
                                        float targetCurrentBalance = DbS.getBalance(targetPlayer.getUID());
                                        DbS.setBalance(targetPlayer.getUID(), targetCurrentBalance + Amount);
                                        player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have successfully transfered " + Float.toString(Amount) + DB.SettingbyName("currencySymbol") + " to " + targetPlayer.getName());
                                        Date date = new Date();
                                        Timestamp timestamp = new Timestamp(date.getTime());

                                        EconomySystemMain.Transaction transaction1 = ESM.new Transaction();
                                        transaction1.senderUserID = player.getUID();
                                        transaction1.receiverUserID = targetPlayer.getUID();
                                        transaction1.itemName = "Money Transfer";
                                        transaction1.quantity = 1;
                                        transaction1.oldBalance = currentBalance;
                                        transaction1.newBalance = newBalance;
                                        transaction1.cost = -Amount;
                                        transaction1.timestamp = timestamp;

                                        DbS.createTransaction(transaction1);
                                        
                                        EconomySystemMain.Transaction transaction2 = ESM.new Transaction();
                                        transaction2.senderUserID = targetPlayer.getUID();
                                        transaction2.receiverUserID = player.getUID();
                                        transaction2.itemName = "Money Transfer";
                                        transaction2.quantity = 1;
                                        transaction2.oldBalance = targetCurrentBalance;
                                        transaction2.newBalance = targetCurrentBalance + Amount;
                                        transaction2.cost = Amount;
                                        transaction2.timestamp = timestamp;

                                        DbS.createTransaction(transaction2);
                                    }
                                    else{
                                        player.sendTextMessage("[#FF0000]You do not have enough money to complete this transaction!");
                                    }
                                }
                                else{
                                    player.sendTextMessage("[#FF0000]You have to provide a valid player name!");
                                }
                            }
                            catch (NumberFormatException e){
                                player.sendTextMessage("[#FF0000]You have to provide a valid amount!");
                            }
                        }
                        break;
                    case "price":
                        if (cmd.length == 3){
                            String productName = cmd[2];
                            float buyprice = DB.getServerBuyPrice(productName);
                            float sellprice = DB.getServerSellPrice(productName);
                            if (buyprice < 0 || sellprice < 0){
                                player.sendTextMessage("[#FF0000]Price not found! Please make sure you have spelt the name of the item correctly");
                            }
                            else{
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Current Server Prices for " + productName + ":");
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Buying Price: " + Float.toString(buyprice) + DB.SettingbyName("currencySymbol"));
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Selling Price: " + Float.toString(sellprice) + DB.SettingbyName("currencySymbol"));
                            }
                        }
                        break;
                    case "balance":
                        if (cmd.length == 2){
                            float Balance = DbS.getBalance(player.getUID());
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Your Current Balance is: " + Float.toString(Balance) + DB.SettingbyName("currencySymbol"));
                        }
                        break;
                    case "grant":
                        if (player.isAdmin()){
                            if (cmd.length >= 4){
                                String PlayerName = cmd[3];
                                if (cmd.length >= 5){
                                    for (int i=4;i<cmd.length;i++){
                                        PlayerName = PlayerName + " " + cmd[i];
                                    }
                                }
                                Player targetPlayer = ESM.getServer().getPlayer(PlayerName);
                                if (targetPlayer != null){
                                    long targetPlayerUserID = targetPlayer.getUID();
                                    try{
                                        float Amount = Float.parseFloat(cmd[2]);
                                        float OldBalance = DbS.getBalance(targetPlayerUserID);
                                        if (OldBalance >= 0){
                                            float NewBalance = OldBalance + Amount;

                                            if (NewBalance >= 0){
                                                DbS.setBalance(targetPlayerUserID, NewBalance);
                                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have altered " + PlayerName + "'s balance by: " + Float.toString(Amount) + DB.SettingbyName("currencySymbol") + ". New balance: " + Float.toString(NewBalance) + DB.SettingbyName("currencySymbol"));
                                                Date date = new Date();
                                                Timestamp timestamp = new Timestamp(date.getTime());

                                                EconomySystemMain.Transaction transaction = ESM.new Transaction();
                                                transaction.senderUserID = player.getUID();
                                                transaction.receiverUserID = targetPlayerUserID;
                                                transaction.itemName = "Grant";
                                                transaction.quantity = 1;
                                                transaction.oldBalance = OldBalance;
                                                transaction.newBalance = NewBalance;
                                                transaction.cost = Amount;
                                                transaction.timestamp = timestamp;

                                                DbS.createTransaction(transaction);
                                            }
                                            else{
                                                DbS.setBalance(targetPlayerUserID, 0.0f);
                                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have subtracted more than the amount in " + PlayerName + "'s account. Balance has been set to 0 (zero) " + DB.SettingbyName("currencySymbol") + ".");
                                                Date date = new Date();
                                                Timestamp timestamp = new Timestamp(date.getTime());

                                                EconomySystemMain.Transaction transaction = ESM.new Transaction();
                                                transaction.senderUserID = player.getUID();
                                                transaction.receiverUserID = targetPlayerUserID;
                                                transaction.itemName = "Grant";
                                                transaction.quantity = 1;
                                                transaction.oldBalance = OldBalance;
                                                transaction.newBalance = 0.0f;
                                                transaction.cost = -OldBalance;
                                                transaction.timestamp = timestamp;

                                                DbS.createTransaction(transaction);
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]Player name not found! Please check the spelling of the name and try again.");
                                        }
                                    }
                                    catch (NumberFormatException e){
                                        player.sendTextMessage("[#FF0000]Please insert a valid positive or negative number!");
                                        player.sendTextMessage("[#FF0000]For more information type: /es help");
                                    }
                                }
                                else{
                                    player.sendTextMessage("[#FF0000]You have to provide a valid player name!");
                                }
                            }
                        }
                        break;
                    case "history":
                        if (cmd.length == 2){
                            ArrayList<EconomySystemMain.Transaction> transactions = DbS.getTransactions(player.getUID());
                            
                            if (transactions.isEmpty()){
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": You have no recent transactions to view!");
                            }
                            else{
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Recent Transactions:");
                                for (EconomySystemMain.Transaction transaction : transactions){
                                    String formattedDate = new SimpleDateFormat("dd/MM/yy").format(transaction.timestamp);
                                    player.sendTextMessage(formattedDate + " " + transaction.senderUserID
                                                                         + " " + transaction.receiverUserID
                                                                         + " " + transaction.itemName 
                                                                         + " " + Integer.toString(transaction.quantity)
                                                                         + " " + Float.toString(transaction.cost)
                                                                         + " " + Float.toString(transaction.oldBalance)
                                                                         + " " + Float.toString(transaction.newBalance)
                                                           );
                                }
                                //GUI display window for transactions & World Database SQL query for Player Names
                            }
                        }
                        break;
                    case "productlist":
                        if (cmd.length == 2){
                            ArrayList<EconomySystemMain.Product> products = DB.getProducts();
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Available Products:");
                            for (EconomySystemMain.Product product : products){
                                player.sendTextMessage(product.name + ", price: " + product.price + DB.SettingbyName("currencySymbol"));
                            }
                        }
                        break;
                    case "orders":
                        if (cmd.length == 2){
                            ArrayList<EconomySystemMain.CoD> buyCoDs = DbS.getBuyCoDs(player.getUID());
                            ArrayList<EconomySystemMain.CoD> sellCoDs = DbS.getSellCoDs(player.getUID());
                            
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Current Buy Orders:");
                            for (EconomySystemMain.CoD cod : buyCoDs){
                                player.sendTextMessage(cod.senderUserID + " " + cod.receiverUserID + " " + cod.itemName + " " + Integer.toString(cod.quantity) + " " + Float.toString(cod.cost));
                            }
                            
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Current Sell Orders:");
                            for (EconomySystemMain.CoD cod : sellCoDs){
                                player.sendTextMessage(cod.senderUserID + " " + cod.receiverUserID + " " + cod.itemName + " " + Integer.toString(cod.quantity) + " " + Float.toString(cod.cost));
                            }
                            //GUI display window for CoDs
                        }
                        else if (cmd.length == 3){
                            switch (cmd[2]) {
                                case "buy":
                                    ArrayList<EconomySystemMain.CoD> buyCoDs = DbS.getBuyCoDs(player.getUID());
                                    player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Current Buy Orders:");
                                    for (EconomySystemMain.CoD cod : buyCoDs){
                                        player.sendTextMessage(cod.senderUserID + " " + cod.receiverUserID + " " + cod.itemName + " " + Integer.toString(cod.quantity) + " " + Float.toString(cod.cost));
                                    }
                                    //GUI display window for CoDs
                                    break;
                                case "sell":
                                    ArrayList<EconomySystemMain.CoD> sellCoDs = DbS.getSellCoDs(player.getUID());
                                    player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Current Sell Orders:");
                                    for (EconomySystemMain.CoD cod : sellCoDs){
                                        player.sendTextMessage(cod.senderUserID + " " + cod.receiverUserID + " " + cod.itemName + " " + Integer.toString(cod.quantity) + " " + Float.toString(cod.cost));
                                    }
                                    //GUI display window for CoDs
                                    break;
                                default:
                                    player.sendTextMessage("[#FF0000]You have to provide a valid order type! Valid inputs are \"buy\" and \"sell\".");
                                    break;
                            }
                        }
                        break;
                    case "accept":
                        if (cmd.length == 2){
                            ArrayList<EconomySystemMain.CoD> buyCoDs = DbS.getBuyCoDs(player.getUID());
                            if (!buyCoDs.isEmpty()){
                                for (EconomySystemMain.CoD cod : buyCoDs){
                                    EconomySystemMain.Product product = DB.getProductByName(cod.itemName);
                                    if (product != null){
                                        Item item = player.getInventory().insertNewItem(product.itemID, product.textureID, cod.quantity);
                                        if (item != null){
                                            float receiverBalance = DbS.getBalance(player.getUID());
                                            float senderBalance = DbS.getBalance(cod.senderUserID);
                                            
                                            if (receiverBalance - cod.cost >= 0.0f){
                                                DbS.setBalance(player.getUID(), receiverBalance - cod.cost);
                                                DbS.setBalance(cod.senderUserID, senderBalance + cod.cost);

                                                Date date = new Date();
                                                Timestamp timestamp = new Timestamp(date.getTime());
                                                EconomySystemMain.Transaction transactionReceiver = ESM.new Transaction();

                                                transactionReceiver.itemName = product.name;
                                                transactionReceiver.quantity = cod.quantity;
                                                transactionReceiver.cost = -cod.cost;
                                                transactionReceiver.oldBalance = receiverBalance;
                                                transactionReceiver.newBalance = receiverBalance - cod.cost;
                                                transactionReceiver.receiverUserID = player.getUID();
                                                transactionReceiver.senderUserID = cod.senderUserID;
                                                transactionReceiver.timestamp = timestamp;

                                                EconomySystemMain.Transaction transactionSender = ESM.new Transaction();

                                                transactionSender.itemName = product.name;
                                                transactionSender.quantity = cod.quantity;
                                                transactionSender.cost = cod.cost;
                                                transactionSender.oldBalance = senderBalance;
                                                transactionSender.newBalance = senderBalance + cod.cost;
                                                transactionSender.receiverUserID = cod.senderUserID;
                                                transactionSender.senderUserID = player.getUID();
                                                transactionSender.timestamp = timestamp;

                                                DbS.createTransaction(transactionReceiver);
                                                DbS.createTransaction(transactionSender);
                                                DbS.deleteCoD(cod.idNo);
                                            }
                                            else{
                                                player.sendTextMessage("[#FF0000]You do not have enough money to complete this transaction!");
                                                break;
                                            }
                                        }
                                        else{
                                            player.sendTextMessage("[#FF0000]You do not have enough space in your inventory to buy the requested items!");
                                            break;
                                        }
                                    }
                                    else{
                                        DbS.deleteCoD(cod.idNo);
                                    }
                                }
                            }
                            else{
                                player.sendTextMessage("[#FF0000]You have currently no pending orders to accept!");
                            }
                        }
                        break;
                    case "help":
                        if (cmd.length == 2){
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Available Economy System Commands:");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es buy ItemName Amount, buys the requested item in the requested amount");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es buy ItemName Texture Amount, buys the requested item in the requested texture and amount");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es sell, sells all items in the currently held item stack to the server");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es sell Price PlayerName, sells all items in the currently held item stack to the specified player for the selected price");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es send Amount PlayerName, sends the specified amount to the specified player for the selected price");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es price itemName, gives the price of the named item");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es balance,  displays your current account balance");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es history, displays your recent transaction history");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es productlist, displays a list of all available products");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es orders, displays a list of all your pending buy and sell orders");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es orders buy/sell, displays a list of all your pending specified orders");
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es accept, accepts all your pending buy orders");
                            if (player.isAdmin()){
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es grant Amount PlayerName, grants the selected Amount to the selected Player. Can also deduct money if a negative Amount is entered.");
                            }
                        }
                        break;
                    default:
                        player.sendTextMessage("[#FF0000]Please enter a valid EconomySystem command!");
                        player.sendTextMessage("[#FF0000]For more information type: /es help");
                        break;
                }
            }
        }
    }
    */
    
}