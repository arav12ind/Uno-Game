package uno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

record Player(PrintWriter out, BufferedReader in, Socket socket,
              ArrayList<UnoCard> cards) {

    public void sendCards() {
        StringBuilder msg = new StringBuilder("draw\n");
        for(UnoCard uc: cards)
        {
            msg.append(uc);
            msg.append("\n");
        }
        msg.append("-EOF-");
        out.println(msg);
        System.out.println("\n"+msg);
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }


    public int cardCount() {
        return cards.size();
    }

    public void drawCards(ArrayList<UnoCard> ucs) {
        StringBuilder msg = new StringBuilder("draw\n");
        for(UnoCard uc : ucs)
        {
            msg.append(uc);
            msg.append("\n");
        }
        msg.append("-EOF-");
        out.println(msg);
        cards.addAll(ucs);
    }

    public void drawCard(UnoCard uc) {
        StringBuilder msg = new StringBuilder("draw\n");
        msg.append(uc);
        msg.append("\n-EOF-");
        out.println(msg);
        cards.add(uc);
    }

    public UnoCard getCard(UnoCard topCard) throws Exception {
        UnoCard card;
        String msg = in.readLine();
        Iterator<UnoCard> it;
        card = new UnoCard(msg);
        System.out.println("getcard : {  "+card+" , "+ topCard+" }"+" "+card.matches(topCard));
        if (card.matches(topCard)) {
            it= cards.iterator();
            while(it.hasNext())
            {
                if(((UnoCard)it.next()).equals(card))
                {
                    it.remove();
                    out.println("ok\n" + card);
                    return card;
                }
            }
        }
        out.println("wrong card");
        return null;
    }
    public String readLine() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }
}
