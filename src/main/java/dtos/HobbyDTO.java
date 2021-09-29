package dtos;

public class HobbyDTO {
    private String name;
    private String category;
    private String wikiLink;
    private String type;

    public HobbyDTO(String name, String category, String wikiLink, String type) {
        this.name = name;
        this.category = category;
        this.wikiLink = wikiLink;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
