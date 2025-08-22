package MultiplayerCasino.MultiplayerCasinoSupport;

import utility.*;

public class Leaderboard {
    
    // 🏆 Leaderboard (includes lottery awards)
    public static void showLeaderboard(Player[] players) {
        PrintMethods.pln("\n===== 🏆 Leaderboard =====");
        Player mostGambles = players[0], mostWins = players[0], mostLosses = players[0];
        Player mostMoneyWon = players[0], mostMoneyLost = players[0], richest = players[0], highestLifetime = players[0];
        Player biggestLotteryWinner = players[0], topLotteryContributor = players[0];

        for (Player p : players) {
            if (p.gamblesPlayed > mostGambles.gamblesPlayed) mostGambles = p;
            if (p.gamblesWon > mostWins.gamblesWon) mostWins = p;
            if (p.gamblesLost > mostLosses.gamblesLost) mostLosses = p;
            if (p.moneyWon > mostMoneyWon.moneyWon) mostMoneyWon = p;
            if (p.moneyLost > mostMoneyLost.moneyLost) mostMoneyLost = p;
            if (p.balance > richest.balance) richest = p;
            if (p.lifetimeTotal > highestLifetime.lifetimeTotal) highestLifetime = p;
            if (p.biggestLotteryWin > biggestLotteryWinner.biggestLotteryWin) biggestLotteryWinner = p;
            if (p.lotteryContribution > topLotteryContributor.lotteryContribution) topLotteryContributor = p;
        }

        PrintMethods.pln("🎲 Most Gambles Played: " + mostGambles.name + " (" + mostGambles.gamblesPlayed + ")");
        PrintMethods.pln("🏅 Most Wins (gambling): " + mostWins.name + " (" + mostWins.gamblesWon + ")");
        PrintMethods.pln("💀 Most Losses (gambling): " + mostLosses.name + " (" + mostLosses.gamblesLost + ")");
        PrintMethods.pln("💰 Most Money Won (gambling): " + mostMoneyWon.name + " ($" + mostMoneyWon.moneyWon + ")");
        PrintMethods.pln("📉 Most Money Lost (gambling): " + mostMoneyLost.name + " ($" + mostMoneyLost.moneyLost + ")");
        PrintMethods.pln("👑 Richest at End: " + richest.name + " ($" + richest.balance + ")");
        PrintMethods.pln("🔄 Most Money Handled (ALL flows): " + highestLifetime.name + " ($" + highestLifetime.lifetimeTotal + ")");
        PrintMethods.pln("🎰 Biggest Lottery Win: " + biggestLotteryWinner.name + " ($" + biggestLotteryWinner.biggestLotteryWin + ")");
        PrintMethods.pln("🏦 Top Lottery Contributor: " + topLotteryContributor.name + " ($" + topLotteryContributor.lotteryContribution + ")");
    }
}
