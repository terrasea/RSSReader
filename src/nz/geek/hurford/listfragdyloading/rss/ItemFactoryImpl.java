package nz.geek.hurford.listfragdyloading.rss;

public class ItemFactoryImpl extends ItemFactory {

    @Override
    public Item newItem() {
        // TODO Auto-generated method stub
        return new RSSItem();
    }

}
