package MultiplayerCasino.MultiplayerCasinoSupport;

import java.util.*;
import utility.*;

public class RummyModule {

    static PrintMethods PrintMethods = new PrintMethods();  

    // ===== Card Model =====
    enum Suit { H, D, C, S, JOKER }

    static final String[] SUIT_NAMES = {"Hearts", "Diamonds", "Clubs", "Spades"};
    static final String[] RANK_CODES = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

    static final Map<String,Integer> RANK_VAL = new HashMap<String,Integer>();
    static {
        for (int i=0;i<RANK_CODES.length;i++) RANK_VAL.put(RANK_CODES[i], i+1);
    }

    static class Card {
        Suit suit;
        String rank;
        String code;

        Card(Suit suit, String rank) {
            this.suit = suit;
            this.rank = rank;
            if (suit == Suit.JOKER) this.code = "JOKER";
            else this.code = suit.name() + rank;
        }

        String pretty() {
            if (suit == Suit.JOKER) return "Printed Joker";
            String suitName = SUIT_NAMES[suit.ordinal()];
            String r = rank;
            if (rank.equals("A")) r = "Ace";
            else if (rank.equals("J")) r = "Jack";
            else if (rank.equals("Q")) r = "Queen";
            else if (rank.equals("K")) r = "King";
            return r + " of " + suitName;
        }

        boolean isPrintedJoker() { return suit == Suit.JOKER; }
        boolean isWildJoker(String wild) { return (rank != null && rank.equals(wild)); }
        public String toString() { return code; }
    }

    // ===== Deck =====
    static class Deck {
        List<Card> stock = new ArrayList<Card>();
        Deque<Card> discard = new ArrayDeque<Card>();
        String wildRank;

        Deck(int numDecks, int printedJokers) {
            for (int d=0; d<numDecks; d++) {
                for (Suit s : new Suit[]{Suit.H, Suit.D, Suit.C, Suit.S}) {
                    for (int i=0;i<RANK_CODES.length;i++) {
                        stock.add(new Card(s, RANK_CODES[i]));
                    }
                }
                for (int pj=0; pj<printedJokers; pj++) stock.add(new Card(Suit.JOKER, null));
            }
            Collections.shuffle(stock);
            Card open = drawNonJokerTop();
            wildRank = open.rank;
            discard.push(open);
        }

        private Card drawNonJokerTop() {
            while (!stock.isEmpty()) {
                Card c = stock.remove(stock.size()-1);
                if (!c.isPrintedJoker()) return c;
            }
            return null;
        }

        Card drawFromStock() {
            if (stock.isEmpty()) {
                if (discard.size() <= 1) return null;
                Card top = discard.pop();
                List<Card> rest = new ArrayList<Card>(discard);
                discard.clear();
                discard.push(top);
                Collections.shuffle(rest);
                stock.addAll(rest);
            }
            return stock.remove(stock.size()-1);
        }

        Card peekDiscard() { return discard.peek(); }
        Card drawFromDiscard() { return discard.isEmpty() ? null : discard.pop(); }
        void placeToDiscard(Card c) { discard.push(c); }
    }

    // ===== RummyPlayer (distinct from MultiPlayerBankGame.Player) =====
    static class RummyPlayer {
        String name;
        List<Card> hand = new ArrayList<Card>();
        RummyPlayer(String name) { this.name = name; }

        void add(Card c) { hand.add(c); }
        void remove(Card c) { hand.remove(c); }

        void showPrivateHand(String wild) {
            PrintMethods.pln("[" + name + " — Your Hand]");
            for (Card c : hand) {
                String tag = "";
                if (c.isPrintedJoker()) tag = " (Printed Joker)";
                else if (c.isWildJoker(wild)) tag = " (Wild Joker)";
                PrintMethods.pln("  " + c.code + "  →  " + c.pretty() + tag);
            }
        }

        void showPublicCodes() {
            PrintMethods.p(name + "'s cards: ");
            for (Card c : hand) PrintMethods.p(c.code + " ");;
        }
    }

    // ===== Validator (copied from Code1) =====
    static boolean isValidRummyHand(List<Card> hand, String wild) {
        if (hand.size() != 13) return false;

        // Separate jokers
        List<Card> jokers = new ArrayList<Card>();
        List<Card> normal = new ArrayList<Card>();
        for (Card c : hand) {
            if (c.isPrintedJoker() || c.isWildJoker(wild)) jokers.add(c);
            else normal.add(c);
        }

        // Try all groupings recursively
        return canFormValidHand(normal, jokers, false, 0);
    }

    // Recursive function to try splitting into melds
    static boolean canFormValidHand(List<Card> normal, List<Card> jokers, boolean hasPure, int seqCount) {
        if (normal.isEmpty() && jokers.isEmpty()) {
            // must have 2 sequences including 1 pure
            return hasPure && seqCount >= 2;
        }

        // Try to make a pure sequence from normal
        for (Suit s : Suit.values()) {
            if (s == Suit.JOKER) continue;
            List<Card> suited = new ArrayList<Card>();
            for (Card c : normal) if (c.suit == s) suited.add(c);
            if (suited.size() < 3) continue;
            Collections.sort(suited, new Comparator<Card>() {
                public int compare(Card a, Card b) { return RANK_VAL.get(a.rank) - RANK_VAL.get(b.rank); }
            });

            for (int i=0;i<suited.size()-2;i++) {
                List<Card> seq = new ArrayList<Card>();
                seq.add(suited.get(i));
                int expected = RANK_VAL.get(suited.get(i).rank);
                for (int j=i+1;j<suited.size();j++) {
                    int val = RANK_VAL.get(suited.get(j).rank);
                    if (val == expected+1) {
                        seq.add(suited.get(j));
                        expected=val;
                    } else if (val > expected+1) break;
                    if (seq.size()>=3) {
                        List<Card> newNorm = new ArrayList<Card>(normal);
                        newNorm.removeAll(seq);
                        if (canFormValidHand(newNorm, jokers, true, seqCount+1)) return true;
                    }
                }
            }
        }

        // Try sequence using jokers
        for (Suit s : Suit.values()) {
            if (s == Suit.JOKER) continue;
            List<Card> suited = new ArrayList<Card>();
            for (Card c : normal) if (c.suit == s) suited.add(c);
            Collections.sort(suited, new Comparator<Card>() {
                public int compare(Card a, Card b) { return RANK_VAL.get(a.rank) - RANK_VAL.get(b.rank); }
            });

            // use jokers to fill gaps
            if (suited.size() >= 2 && jokers.size() >= 1) {
                List<Card> newNorm = new ArrayList<Card>(normal);
                List<Card> newJok = new ArrayList<Card>(jokers);
                // remove first 2 + 1 joker
                newNorm.remove(suited.get(0));
                newNorm.remove(suited.get(1));
                newJok.remove(0);
                if (canFormValidHand(newNorm, newJok, hasPure, seqCount+1)) return true;
            }
        }

        // Try set
        Map<String, List<Card>> rankGroups = new HashMap<String,List<Card>>();
        for (Card c : normal) {
            if (!rankGroups.containsKey(c.rank)) rankGroups.put(c.rank, new ArrayList<Card>());
            rankGroups.get(c.rank).add(c);
        }
        for (String r : rankGroups.keySet()) {
            List<Card> group = rankGroups.get(r);
            if (group.size() >= 3) {
                List<Card> newNorm = new ArrayList<Card>(normal);
                newNorm.removeAll(group.subList(0,3));
                if (canFormValidHand(newNorm, jokers, hasPure, seqCount)) return true;
            }
            if (group.size() == 2 && jokers.size() >= 1) {
                List<Card> newNorm = new ArrayList<Card>(normal);
                newNorm.removeAll(group);
                List<Card> newJok = new ArrayList<Card>(jokers);
                newJok.remove(0);
                if (canFormValidHand(newNorm, newJok, hasPure, seqCount)) return true;
            }
        }

        return false;
    }

    // Score computation (dynamic): Ace=1, 2-10 numeric, J/Q/K=10, Joker=0
    static int computeScore(List<Card> hand) {
        int sum = 0;
        for (Card c : hand) {
            if (c.isPrintedJoker()) continue;
            String r = c.rank;
            if (r == null) continue;
            if (r.equals("A")) sum += 1;
            else if (r.equals("J") || r.equals("Q") || r.equals("K")) sum += 10;
            else {
                try {
                    sum += Integer.parseInt(r);
                } catch (NumberFormatException e) {
                    // fallback, use rank value
                    Integer v = RANK_VAL.get(r);
                    if (v != null) sum += v;
                }
            }
        }
        return sum;
    }

    /**
     * Run interactive Rummy among the provided electionPlayers.
     * Returns int[]{winnerIndexInElectionPlayersArray, pointsEarned}
     * or {-1,0} if no game/winner.
     */
    public static int[] runRummyGame(Scanner sc, Player[] electionPlayers) {
        if (electionPlayers == null || electionPlayers.length < 2) {
            PrintMethods.pln("Need at least 2 consenting players to play Rummy.");
            return new int[]{-1,0};
        }

        // Map election players to RummyPlayers (preserve order)
        RummyPlayer[] rPlayers = new RummyPlayer[electionPlayers.length];
        for (int i=0;i<electionPlayers.length;i++) {
            rPlayers[i] = new RummyPlayer(electionPlayers[i].name);
        }

        // Use same scanner. Clear newline (safe guard) if any leftover
        try { if (sc.hasNextLine()) sc.nextLine(); } catch (NoSuchElementException e) {}

        Deck deck = new Deck(2,2);
        PrintMethods.pln("\n=== Starting 13-Card Rummy with players: ===");
        for (RummyPlayer rp : rPlayers) PrintMethods.pln(" - " + rp.name);
        PrintMethods.pln("Wild Joker Rank: " + deck.wildRank);
        PrintMethods.pln("Discard starts with: " + deck.peekDiscard().pretty());

        // deal 13 each
        for (int i=0;i<13;i++) for (RummyPlayer p: rPlayers) p.add(deck.drawFromStock());
        for (RummyPlayer p: rPlayers) p.showPublicCodes();

        int turn = 0;
        boolean over = false;
        int winnerIdx = -1;
        int winnerPoints = 0;

        while (!over) {
            RummyPlayer cur = rPlayers[turn];
            PrintMethods.pln("\n--- " + cur.name + "'s TURN ---");
            cur.showPrivateHand(deck.wildRank);
            PrintMethods.pln("Top of discard: " + deck.peekDiscard().pretty());

            PrintMethods.p("Draw from (1) Stock or (2) Discard? ");
            String choiceLine = sc.nextLine().trim();
            int choice = 1;
            try { choice = Integer.parseInt(choiceLine); } catch (Exception e) { choice = 1; }

            Card drawn = (choice==2?deck.drawFromDiscard():deck.drawFromStock());
            if (drawn==null) { PrintMethods.pln("No card to draw! (skipping)"); turn=(turn+1)%rPlayers.length; continue; }
            cur.add(drawn);
            PrintMethods.pln("You drew: " + drawn.pretty());

            PrintMethods.p("Enter code to discard (or DECLARE to show hand): ");
            String code = sc.nextLine().trim().toUpperCase();

            if (code.equals("DECLARE")) {
                if (isValidRummyHand(cur.hand, deck.wildRank)) {
                    PrintMethods.pln(cur.name+" declared and WON!");
                    over = true;
                    winnerIdx = turn;
                    winnerPoints = computeScore(cur.hand);
                    break;
                } else {
                    PrintMethods.pln("Invalid declaration. You lose your turn.");
                }
            } else {
                Card discardCard = null;
                for (Card c : new ArrayList<Card>(cur.hand)) if (c.code.equalsIgnoreCase(code)) discardCard = c;
                if (discardCard == null) { PrintMethods.pln("Invalid code. Auto discard drawn."); discardCard = drawn; }
                cur.remove(discardCard);
                deck.placeToDiscard(discardCard);
                PrintMethods.pln(cur.name+" discarded "+discardCard.pretty());
            }

            turn = (turn+1) % rPlayers.length;
        }

        if (winnerIdx >= 0) {
            PrintMethods.pln("\n🏆 Rummy Winner: " + rPlayers[winnerIdx].name + " (points: " + winnerPoints + ")");
            return new int[]{winnerIdx, winnerPoints};
        } else {
            PrintMethods.pln("No winner for Rummy.");
            return new int[]{-1,0};
        }
    }
} // end RummyModule