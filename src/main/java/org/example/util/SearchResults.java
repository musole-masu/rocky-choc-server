package org.example.util;

import java.util.List;

public class SearchResults {
    private String search_term;
    private Object knowledge_panel;
    private List<SearchResult> results;

    public SearchResults() {}

    public String getSearch_term() {
        return search_term;
    }

    public void setSearch_term(String search_term) {
        this.search_term = search_term;
    }

    public Object getKnowledge_panel() {
        return knowledge_panel;
    }

    public void setKnowledge_panel(Object knowledge_panel) {
        this.knowledge_panel = knowledge_panel;
    }

    public List<SearchResult> getResults() {
        return results;
    }

    public void setResults(List<SearchResult> results) {
        this.results = results;
    }
}
