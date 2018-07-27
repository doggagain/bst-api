package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.cache.interceptor.NameMatchCacheOperationSource;

public class BinaryNodeData {
    @JsonProperty("name")
    public String Name;
    @JsonProperty("attributes")
    public Student Attributes;
    @JsonProperty("children")
    public BinaryNodeData[] Children;

    public BinaryNodeData(String name,Student attributes,BinaryNodeData[] children){
        this.Name=name;
        this.Attributes=attributes;
        this.Children=children;
    }
    public BinaryNodeData(){
        
    }

}