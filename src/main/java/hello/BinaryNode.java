package hello;
public class BinaryNode<T extends HaveKey> extends HaveKey {
	public BinaryNode<T> Left;
	public BinaryNode<T> Right;
	public T Info;

	// True if left pointer points to predecessor
	// in Inorder Traversal
	boolean IsLeftThread;

	// True if right pointer points to predecessor
	// in Inorder Traversal
	boolean IsRightThread;
	
	public BinaryNode(T info,BinaryNode<T> left,BinaryNode<T> right){
		this.Info=info;
		this.Left=left;
		this.Right=right;
	}

	public BinaryNode(T info){
		this.Info=info;
	}

	public BinaryNode(T info,boolean isLeftThread,boolean isRightThread){
		this.Info=info;
		this.IsLeftThread=isLeftThread;
		this.IsRightThread=isRightThread;
	}
	
	public T GetInfo(){
		return this.Info;
	}
	
	public void SetInfo(T info){
		this.Info=info;
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

	
	public boolean GetIsLeftThread(){
		return this.IsLeftThread;
	}
	
	public void SetIsLeftThread(boolean isLeftThread){
		this.IsLeftThread=isLeftThread;
	}
	
	public boolean GetIsRightThread(){
		return this.IsRightThread;
	}
	
	public void SetIsRightThread(boolean isRightThread){
		this.IsRightThread=isRightThread;
	}

	public int GetKey() {
		return this.GetInfo().GetKey();
	}

	public boolean IsParentOfRight(){
		return this.GetRight()!=null && !this.GetIsRightThread();
	}
	
	public boolean IsParentOfLeft(){
		return this.GetLeft()!=null && !this.GetIsLeftThread();
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
