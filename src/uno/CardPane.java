package uno;

import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.Iterator;

public class CardPane extends Pane {
    private int cardsPerRow;
    private double height,width;
    private double planeWidth;
    private double vGap;
    public CardPane(int cardsPerRow,double height,double width)
    {
        this.cardsPerRow=cardsPerRow;
        this.height=height;
        this.width=width;
        this.planeWidth= (cardsPerRow*0.65+1)*width;
        FlowPane fp = new FlowPane(height,planeWidth);
        fp.setHgap(-width*0.65);
        getChildren().add(fp);
        this.vGap = height * 0.65;
    }
    public void add(UnoCardView uc)
    {
        FlowPane fp;
        uc.setFitWidth(width);
        uc.setFitHeight(height);
        for(Node n : getChildren())
        {
            fp = (FlowPane) n;
            if(fp.getChildren().size()<cardsPerRow)
            {
                fp.getChildren().add(uc);
                return;
            }
        }
        fp = new FlowPane(height,planeWidth);
        fp.setHgap(-width*0.65);
        fp.setLayoutY(getChildren().size()*vGap);
        fp.getChildren().add(uc);
        getChildren().add(fp);
    }
    public void addAll(Collection<? extends UnoCardView> ucs)
    {
        ucs.forEach(this::add);
    }
    public boolean remove(UnoCard uc)
    {
        FlowPane fp;
        int i=0;
        Iterator<Node> n = getChildren().iterator();
        while(n.hasNext())
        {
            fp = (FlowPane) n.next();
            Iterator<Node> it = fp.getChildren().iterator();
            while(it.hasNext())
            {
                if(((UnoCardView)it.next()).getCard().equals(uc))
                {
                    it.remove();
                    if(fp.getChildren().size()==0)
                    {
                        n.remove();
                        while(n.hasNext())
                        {
                            n.next().setLayoutY(i*vGap);
                        }

                    }
                    return true;
                }
            }
            ++i;
        }
        return false;
    }

}
