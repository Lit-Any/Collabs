package MultiPlayerBankGame.MultiPlayerBankGameSupport;

public class Leaderboard {
    
    // 🏆 Leaderboard (includes lottery awards)
    public static void showLeaderboard(Player[] players) {
        System.out.println("\n===== 🏆 Leaderboard =====");
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

        System.out.println("🎲 Most Gambles Played: " + mostGambles.name + " (" + mostGambles.gamblesPlayed + ")");
        System.out.println("🏅 Most Wins (gambling): " + mostWins.name + " (" + mostWins.gamblesWon + ")");
        System.out.println("💀 Most Losses (gambling): " + mostLosses.name + " (" + mostLosses.gamblesLost + ")");
        System.out.println("💰 Most Money Won (gambling): " + mostMoneyWon.name + " ($" + mostMoneyWon.moneyWon + ")");
        System.out.println("📉 Most Money Lost (gambling): " + mostMoneyLost.name + " ($" + mostMoneyLost.moneyLost + ")");
        System.out.println("👑 Richest at End: " + richest.name + " ($" + richest.balance + ")");
        System.out.println("🔄 Most Money Handled (ALL flows): " + highestLifetime.name + " ($" + highestLifetime.lifetimeTotal + ")");
        System.out.println("🎰 Biggest Lottery Win: " + biggestLotteryWinner.name + " ($" + biggestLotteryWinner.biggestLotteryWin + ")");
        System.out.println("🏦 Top Lottery Contributor: " + topLotteryContributor.name + " ($" + topLotteryContributor.lotteryContribution + ")");
    }
}
