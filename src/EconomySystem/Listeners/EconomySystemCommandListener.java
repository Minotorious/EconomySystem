package EconomySystem.Listeners;

import EconomySystem.EconomySystemMain;
import EconomySystem.DatabaseStuff;
import EconomySystem.DatabaseQueue;
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

public class EconomySystemCommandListener implements  Listener{
    
    @EventMethod
    public void onCommand(PlayerCommandEvent event){
        Player player = event.getPlayer();
        String command = event.getCommand();
        
        String[] cmd = command.split(" ");
        if (cmd[0].equals("/es")){
            if (cmd.length >= 2){
                DatabaseStuff DbS = new DatabaseStuff();
                DatabaseQueue DbQ = new DatabaseQueue();
                DataBank DB = new DataBank();
                EconomySystemMain ESM = new EconomySystemMain();
                switch (cmd[1]){
                    case "buy":
                        if (cmd.length == 4){
                            
                        }
                        else if (cmd.length == 5){
                            
                        }
                        break;
                    case "sell":
                        if (cmd.length == 2){
                            
                        }
                        else if (cmd.length >= 4){
                            
                        }
                        break;
                    case "send":
                        if (cmd.length >= 4){
                            
                        }
                        break;
                    case "price":
                        if (cmd.length == 3){
                            
                        }
                        break;
                    case "balance":
                        if (cmd.length == 2){
                            float Balance = DB.getBalance(player.getUID());
                            player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": Your Current Balance is: " + Float.toString(Balance) + DB.SettingbyName("currencySymbol"));
                        }
                        break;
                    case "history":
                        if (cmd.length == 2){
                            
                        }
                        break;
                    case "productlist":
                        if (cmd.length == 2){
                            
                        }
                        break;
                    case "orders":
                        if (cmd.length == 2){
                            
                        }
                        break;
                    case "accept":
                        if (cmd.length == 2){
                            
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
                                
                            }
                        }
                        break;
                    case "makeproduct":
                        if (player.isAdmin()){
                            if (cmd.length == 4){
                                
                            }
                        }
                        break;
                    case "setprice":
                        if (player.isAdmin()){
                            if (cmd.length == 5){
                                
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
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es makeproduct ServerBuyingPrce ServerSellingPrice, defines the currently held item as a product for the players to buy and sell at the selected prices. Aprice of -1 means the server is not buying or selling the selected item but players can still trade it to each other");
                                player.sendTextMessage(DB.SettingbyName("currencyBotChatColour") + DB.SettingbyName("currencyBotName") + ": /es grant Amount PlayerName, grants the selected Amount to the selected Player. Can also deduct money if a negative Amount is entered");
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
    
    
}