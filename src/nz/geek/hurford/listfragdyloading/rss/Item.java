package nz.geek.hurford.listfragdyloading.rss;

public interface Item {

    String getTitle();
    void setTitle(String string);

    String getLink();
    void setLink(String string);

    String getDescription();
    void setDescription(String string);
}
