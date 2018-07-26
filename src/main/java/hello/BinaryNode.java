package hello;
public class BinaryNode<T extends HaveKey> extends HaveKey {
	private BinaryNode<T> Parent;
	private T Info;
	private BinaryNode<T> Left;
	private BinaryNode<T> Right;
	
	public BinaryNode(T info,BinaryNode<T> parent,BinaryNode<T> left,BinaryNode<T> right){
		this.Info=info;
		this.Parent=parent;
		this.Left=left;
		this.Right=right;
	}

	public BinaryNode(T info){
		this.Info=info;
	}
	
	public T GetInfo(){
		return this.Info;
	}
	
	public void SetInfo(T info){
		this.Info=info;
	}
	
	public BinaryNode<T> GetParent(){
		return this.Parent;
	}
	
	public void SetParent(BinaryNode<T> parent){
		this.Parent=parent;
	}
	
	public BinaryNode<T> GetLeft(){
		return this.Left;
	}
	
	public void SetLeft(BinaryNode<T> left){
		this.Left=left;
	}
	
	public BinaryNode<T> GetRight(){
		return this.Right;
	}
	
	public void SetRight(BinaryNode<T> right){
		this.Right=right;
	}

	public int GetKey() {
		return this.GetInfo().GetKey();
	}
	
	public boolean IsLeaf(){
		return !
				((this.Right != null && this.Right.GetParent()==this) || //condition for not being a leaf
			   (this.Left!= null && this.Left.GetParent()==this)); 
	}
	
	public boolean IsMaximumLeaf(){
		return this.Right == null;
	}
	
	public boolean IsLeftChild(){
		if(this.GetParent()==null){
			return false;
		}
		return this.GetParent().GetLeft()==this;
	}
	
	public boolean IsRightChild(){
		if(this.GetParent()==null){
			return false;
		}
		return this.GetParent().GetRight()==this;
	}
	
	
	public boolean IsParentOfRight(){
		if(this.Right==null){
			return false;
		}
		return this.Right.GetParent()==this;
	}
	
	public boolean IsParentOfLeft(){
		if(this.Left==null){
			return false;
		}
		return this.Left.GetParent()==this;
	}
	
	public  BinaryNode<T> GetPredecessor(){
		if(!this.IsParentOfLeft()){
			return this.Left;
		}
		if(this.IsParentOfLeft()){
			return this.Left.GetMaximum();
		}
		return null; //then we are the minimum node;
	}
	
	public  BinaryNode<T> GetSuccessor(){
		if(!this.IsParentOfRight()){
			return this.Right;
		}
		if(this.IsParentOfRight()){
			return this.Right.GetMinium();
		}
		return null; //then we are the maximum node;
	}
	
	public BinaryNode<T> GetMinium(){
		BinaryNode<T> currentRoot=this;
		while(currentRoot.IsParentOfLeft()){
			currentRoot=currentRoot.GetLeft();
		}
		return currentRoot;
	}
	
	public BinaryNode<T> GetMaximum(){
		BinaryNode<T> currentRoot=this;
		while(currentRoot.IsParentOfRight()){
			currentRoot=currentRoot.GetRight();
		}
		return currentRoot;
	}
	
}
