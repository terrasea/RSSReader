package nz.geek.hurford.listfragdyloading.rss;

public abstract class ItemFactory {
    
    
    public static ItemFactory newInstance() {
        return new ItemFactoryImpl();
    }
    
    
    public abstract Item newItem();
}
