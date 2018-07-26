package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteData{
    @JsonProperty("root")
    public BinaryNodeData Root;
    @JsonProperty("deleted")
    public Student DeletedNode;

    public DeleteData(BinaryNodeData root, Student deletedNode){
        this.Root=root;
        this.DeletedNode=deletedNode;
    }
}